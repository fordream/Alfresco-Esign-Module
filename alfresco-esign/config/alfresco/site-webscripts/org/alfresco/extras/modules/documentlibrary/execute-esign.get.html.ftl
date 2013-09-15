<#assign el=args.htmlid?html>
<div id="${el}-dialog" class="execute-script-dialog">
   <div id="${el}-dialogTitle" class="hd">${msg("title")}</div>
   <div class="bd">
      <form id="${el}-form" action="" method="post">
         <div class="yui-gd">
            <div class="yui-u first"><label for="${el}-script">${msg("label.certpassword")}:</label></div>
            <div class="yui-u">
            	<input id="${el}-password" type="password"maxlength="255" name="password"/>
	    </div>
         </div>
         <div class="bdft">
            <input type="button" id="${el}-ok" value="${msg("button.ok")}" tabindex="0" />
            <input type="button" id="${el}-cancel" value="${msg("button.cancel")}" tabindex="0" />
         </div>
      </form>
   </div>
</div>
