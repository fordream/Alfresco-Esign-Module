/****************************************************************************** * 
* Copyright (C) 2013   Nguyen Khanh Thinh, Nguyen Dinh Nien Contributors
* This program is free software; you can redistribute it and/or 
* modify it under the terms of the GNU General Public License 
* as published by the Free Software Foundation; either version 2 
* of the License, or (at your option) any later version. 
* This program is distributed in the hope that it will be useful, 
* but WITHOUT ANY WARRANTY; without even the implied warranty of 
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
* GNU General Public License for more details.  
* You should have received a copy of the GNU General Public License 
* along with this program; if not, write to the Free Software 
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA
* Any further request, feel freely to contactmhst1304@googlegroups.com or 
* visit https://www.hostedredmine.com/projects/mhst1304 or
* participate online daily meeting via IRC channel #mhst1304 server freenode.net at 9PM - GMT+7.

*************************************************************************************************/

package vn.vfossa.webscript;

import vn.vfossa.signature.*;
import vn.vfossa.cert.*;
import vn.vfossa.utils.*;

import java.lang.Exception;
import java.io.IOException;
import java.io.Reader;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;
import java.lang.Thread;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.extensions.webscripts.AbstractWebScript;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.alfresco.jlan.server.filesys.FileName;
import org.alfresco.repo.security.authentication.MutableAuthenticationDao;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.cmr.security.MutableAuthenticationService;
import org.alfresco.service.cmr.usage.ContentUsageService;
import org.alfresco.repo.action.executer.MailActionExecuter;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ActionService;
import org.springframework.extensions.webscripts.ScriptMessage;

public class EsignAction extends AbstractWebScript {
	private Logger logger = Logger.getLogger(EsignAction.class);	
	private ActionService actionService;
	public void setActionService(ActionService actionService){
		this.actionService = actionService;
	}
	/**
     * node service object
     */
    public NodeService nodeService;
    /**
     * content service object
     */
    public ContentService contentService;
    public ContentReader actionedUponContentReader;
    protected ServiceRegistry serviceRegistry;
    protected String newPassword;

    public void setServiceRegistry(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    public void setNodeService(NodeService nodeService) {
        this.nodeService = nodeService;
    }

   public void setContentService(ContentService contentService) {
        this.contentService = contentService;
    }

	@Override
	public void execute(WebScriptRequest req, WebScriptResponse res)
			throws IOException {				
		try {
			logger.setLevel(Level.ALL);
			String certpass = null, prkeypass = null, field = null;
			File certificate = null;
			// Create response json object
			JSONObject obj = new JSONObject();
			obj.put("success", false);// make success false as default
			obj.put("message", "Failed Request");
			
			// parse request json object
			JSONObject json = null;
			Object jsonO = req.parseContent();
			if (jsonO instanceof JSONObject && jsonO != null) {
				json = (JSONObject) jsonO;
			}

			// Get data from Request
			// Get all the field in json object request
			String params[] = JSONObject.getNames(json);

			for (int i = 0; i < params.length; i++) {
				field = params[i];
				if (field.indexOf("certpassword") != -1) {
					certpass = json.getString(field);
				} else if (field.indexOf("prkeypassword") != -1) {
					prkeypass = json.getString(field);
				} else if (field.indexOf("certificate") != -1) {
						certificate = (File) json.get("certificate");
				}
			}
			
			if(!certificate.isFile()){
				obj.put("message", "Require Certificate!");
				logger.info("This is not a file");
			}
			else if( certpass.length() == 0){
				obj.put("message", "Require Certificate Password!");
				logger.info("Where is certificate password?");
			}
			else if( prkeypass.length() == 0){
				obj.put("message", "Require Privatekey Password!");
				logger.info("Where is certificate password?");
			}
			else{
				obj.put("success", true);
				obj.put("message", "File is singed");
				VnCertificate vncert = new VnCertificate(certificate, certpass, prkeypass);
				String nodeRefString = req.getParameter("nodeRef");
				NodeRef nodeRef = new NodeRef(nodeRefString);
				byte[] data = getNodeContent(nodeRefer);
				File signFile = BytesToFile.bytesToFile(data);
				String fileName = signFile.getName();
				if(fileName.isOOXML()){
					OOXMLContent ooxmlContent = new OOXMLContent(signFile.getAbsoluteFile());
					ooxmlContent.addSignature(vncert.getCert(), vncert.getPrivateKey());
				}
				else if( filenName.isPdf()){
					PDFContent pdfContent = new OOXMLContent(signFile.getAbsoluteFile());
					pdfContent.addSignature(vncert.getCert(), vncert.getPrivateKey());
				}				
			}
			
			// build a JSON string and send it back
			Action mailAction = this.actionService.createAction(MailActionExecuter.NAME);
			mailAction.setParameterValue(MailActionExecuter.PARAM_SUBJECT, "OK");        
			mailAction.setParameterValue(MailActionExecuter.PARAM_TO, "khanhthinh.45a4@gmail.com");
			mailAction.setParameterValue(MailActionExecuter.PARAM_TEXT, "OK");        
			this.actionService.executeAction(mailAction, null);
			
			String jsonString = obj.toString();
			logger.info(jsonString);
			System.out.println("I was called!");
			res.setContentType("application/json");
			res.getWriter().write(jsonString);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public byte[] getNodeContent(NodeRef nodeRefer) {
        byte[] data = new byte[bytesize];

        // Reading the node content
        ContentReader contentReader = serviceRegistry.getContentService().getReader(
                nodeRefer, ContentModel.PROP_CONTENT);

        actionedUponContentReader = contentReader;
        InputStream is = contentReader.getContentInputStream();

        // Conver input stream to bytes
        try {
            data = this.converToByteArray(is);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return data;
    }

}
