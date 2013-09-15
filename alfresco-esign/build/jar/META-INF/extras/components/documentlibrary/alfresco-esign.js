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
    * Preferences
    */
   var PREFERENCES_DASHLET = "org.alfresco.share.documentlibrary.actions",
      PREF_SCRIPT_REF = PREFERENCES_DASHLET + ".executeScriptRef";
   
   /**
    * Execute a script against a document.
    *
    * @method onActionExecuteScript
    * @param asset {object} Object literal representing one or more file(s) or folder(s) to be actioned
    */
   YAHOO.Bubbling.fire("registerAction",
   {
      actionName: "onActionAlfrescoEsign",
      fn: function DL_onActionGeotag(asset)
      {
          // We could also call alfresco/api/action/script/formprocessor with JSON params alf_destination and prop_script-ref
          var nodeRef = asset.nodeRef,
             displayName = asset.displayName,
             actionUrl = Alfresco.constants.PROXY_URI + $combine("slingshot/doclib/action/esign-action/node", nodeRef.replace(":/", ""));

          // Preferences service
          this.services.preferences = new Alfresco.service.Preferences();
          
          // Always create a new instance
          this.modules.executeScript = new Alfresco.module.SimpleDialog(this.id + "-esignAction").setOptions(
          {
             width: "30em",
             templateUrl: Alfresco.constants.URL_SERVICECONTEXT + "extras/modules/documentlibrary/esign-action",
             actionUrl: actionUrl,
             firstFocus: this.id + "-esign-action",
             onSuccess:
             {
                fn: function dlA_onActionExecuteScript_success(response)
                {
                   var select = Dom.get(this.id + "-esign-action");
                   // Persist script to prefs service
                   this.services.preferences.set(PREF_SCRIPT_REF, select.options[select.selectedIndex].value);
                   YAHOO.Bubbling.fire("metadataRefresh",
                   {
                      highlightFile: displayName
                   });
                   Alfresco.util.PopupManager.displayMessage(
                   {
                      text: this.msg("message.esign-action.success", displayName)
                   });
                },
                scope: this
             },
             onFailure:
             {
                fn: function dlA_onActionExecuteScript_failure(response)
                {
                   Alfresco.util.PopupManager.displayMessage(
                   {
                      text: this.msg("message.esign-action.failure", displayName)
                   });
                },
                scope: this
             },
             doSetupFormsValidation:
             {
                fn: function dlA_onActionExecuteScript_doSetupFormsValidation(p_form)
                {
                   // Validation
                   p_form.addValidation(this.id + "-esign-action", function fnValidateType(field, args, event, form, silent, message)
                   {
                      return field.options[field.selectedIndex].value !== "-";
                   }, null, "change");
                   p_form.setShowSubmitStateDynamically(true, false);
                },
                scope: this
             }
          });
          this.modules.executeScript.show();
      }
   });
})();
