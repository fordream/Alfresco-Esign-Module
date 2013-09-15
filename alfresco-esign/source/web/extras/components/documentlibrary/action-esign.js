/**
 * Document library Execute Script action
 * 
 * @namespace Alfresco
 * @class Alfresco.doclib.Actions
 */
(function()
{
   /**
    * YUI Library aliases
    */
   var Dom = YAHOO.util.Dom,
      Event = YAHOO.util.Event;

   /**
    * Alfresco Slingshot aliases
    */
   var $html = Alfresco.util.encodeHTML,
      $combine = Alfresco.util.combinePaths;

   
   /**
    * Execute a script against a document.
    *
    * @method onActionExecuteScript
    * @param asset {object} Object literal representing one or more file(s) or folder(s) to be actioned
    */
   YAHOO.Bubbling.fire("registerAction",
   {
      actionName: "onActionEsign",
      fn: function DL_onActionGeotag(asset)
      {
          // We could also call alfresco/api/action/script/formprocessor with JSON params alf_destination and prop_script-ref
          var nodeRef = asset.nodeRef,
             displayName = asset.displayName,
             actionUrl = Alfresco.constants.PROXY_URI + $combine("slingshot/doclib/action/esign-action/node", nodeRef.replace(":/", ""));

          
          // Always create a new instance
          this.modules.executeScript = new Alfresco.module.SimpleDialog(this.id + "-executeScript").setOptions(
          {
             width: "40em",
             templateUrl: Alfresco.constants.URL_SERVICECONTEXT + "extras/modules/documentlibrary/alfresco-esign",
             actionUrl: actionUrl,
             firstFocus: this.id + "-executeScript-script",
             onSuccess:
             {
                fn: function dlA_onActionExecuteScript_success(response)
                {
                	if (response && response.json){
                		if(response.json.success){
                			Alfresco.util.PopupManager.displayMessage(
                	        {
                	        	text: "1"
                	        });
                		}
                		else{
                			Alfresco.util.PopupManager.displayPrompt(
                		    {
                		    	text: "2"
                		    });
                		}
                	}
                	else{
                		Alfresco.util.PopupManager.displayPrompt(
                    	{
                    		text: response.toString()
                    	});
                	}

                   
                },
                scope: this
             },
             onFailure:
             {
                fn: function dlA_onActionExecuteScript_failure(response)
                {
                   Alfresco.util.PopupManager.displayMessage(
                   {
                      text: this.msg("message.execute-esign.error", displayName)
                   });
                },
                scope: this
             },
             doSetupFormsValidation:
             {
                fn: function dlA_onActionExecuteScript_doSetupFormsValidation(p_form)
                {
                   
                },
                scope: this
             }
          });
          this.modules.executeScript.show();
      }
   });
})();
