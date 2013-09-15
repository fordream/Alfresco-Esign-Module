<#assign el=args.htmlid?html>
<div id="${el}-dialog" class="esign-action-dialog">
   
   <div id="${el}-dialogTitle" class="hd">${msg("title")}
   </div>
   
   <div class="bd">
      <form id="${el}-form" action="" method="post">
         
         <div class="yui-gd">
            <div class="yui-u first">
            	<label for="${el}-privatekey">${msg("label.esign")}:</label>
            </div>
            <div class="yui-u">
               <input name ="privatekey" type="password" maxlength="255" id="${el}-privatekey" /> <br/>
			   <input name ="timestamp" type="checkbox" id="${el}-timestamp" />${msg("checkbox.timestamp")}<br/>
			</div>
		</div>
		
         <div class="bdft">
            <input type="button" id="${el}-ok" value="${msg("button.ok")}" tabindex="0" />
            <input type="button" id="${el}-cancel" value="${msg("button.cancel")}" tabindex="0" />
         </div>
      </form>
   </div>
     
</div>