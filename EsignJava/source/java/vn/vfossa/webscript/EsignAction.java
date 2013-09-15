package vn.vfossa.webscript;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.extensions.webscripts.AbstractWebScript;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;

public class EsignAction extends AbstractWebScript {
	private Logger logger = Logger.getLogger(EsignAction.class);
	@Override
	public void execute(WebScriptRequest req, WebScriptResponse res)
			throws IOException {				
		try {
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
				logger.error("This is not a file");
			}
			else if( certpass.length() == 0){
				obj.put("message", "Require Certificate Password!");
				logger.error("Where is certificate password?");
			}
			else if( prkeypass.length() == 0){
				obj.put("message", "Require Privatekey Password!");
				logger.error("Where is certificate password?");
			}
			else{
				obj.put("success", true);
				obj.put("message", "Accept Request");
			}
			
			// build a JSON string and send it back
			String jsonString = obj.toString();
			logger.error(jsonString);
			System.out.println("I was called!");
			res.setContentType(res.JSON_FORMAT);
			res.getWriter().write(jsonString);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
