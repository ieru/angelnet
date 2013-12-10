<%--
    Document   : userDatesBackUpSNS
    Created on : 15-dic-2010, 13:40:16
    Author     : tote
--%>

<%@page import="es.uah.cc.ie.snsangelguardfb.exception.InterEmailException"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.exception.InterProcessException"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.exception.InterDataBaseException"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.sources.jspcontroler.entity.SettingsSNSAngelGuardJSPControler"%>
<%@page import="java.util.Date"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.sources.dao.entity.UserSettings_SettingsFilterDAO"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.SNSAngelGuardFBManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
           SettingsSNSAngelGuardJSPControler jspControler = null;
           String exceptionAsString = null;
           String typeAngel = null;
           String idAngel = null;
                  
                try {
                    //Obtenemos la conexión a Facebook
                    jspControler = new SettingsSNSAngelGuardJSPControler(request, response);

                    jspControler.process();
                } catch (InterDataBaseException e) {
                    exceptionAsString = jspControler.getSnsObject().getExceptionManager().exceptionToString(e.getException());

                    response.sendRedirect(request.getContextPath() + "/infoError.jsp?errorMessage=" + e.getMessageException() + "&exception=" + exceptionAsString);
                    
                } catch (InterProcessException e) {
                    exceptionAsString = jspControler.getSnsObject().getExceptionManager().exceptionToString(e.getException());

                    response.sendRedirect(request.getContextPath() + "/infoError.jsp?errorMessage=" + e.getMessageException() + "&exception=" + exceptionAsString);
                    
                } catch (InterEmailException e) {
                    exceptionAsString = jspControler.getSnsObject().getExceptionManager().exceptionToString(e.getException());

                    response.sendRedirect(request.getContextPath() + "/infoError.jsp?errorMessage=" + e.getMessageException() + "&exception=" + exceptionAsString);
                }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"lang="es" xml:lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta property="og:image" content="https://snsangelguard.com/SNSAngelGuardFB/resources/logo200.png" />
        <meta property="og:image" content="https://snsangelguard.com/SNSAngelGuardFB/resources/load.gif" />

        <title><%= jspControler.getJspResources().getTitleSettings() %></title>

        <!--import css -->
        <link rel="stylesheet" type="text/css" href="Styles/Style.css" />
        <link rel="stylesheet" type="text/css" href="Styles/facebook.css" />
        <link rel="stylesheet" type="text/css" href="Styles/fcbklistselection.css" />
        <link rel="stylesheet" type="text/css" href="Styles/jquery.loader.css" />
        <link rel="stylesheet" type="text/css" href="Styles/jquery.fancybox.css" />
        <link rel="stylesheet" type="text/css" href="js/jquery-ui-1.8.23/themes/base/jquery.ui.all.css"

        <!--import jquery lib -->
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript" src="js/jquery.corner.js"></script>
        <script type="text/javascript" src="js/jquery.loader.js"></script>
        <script type="text/javascript" src="js/AjaxFlagMenu-1.0.2.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.23/ui/jquery-ui.js" </script>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>

        <script type="text/javascript" src="js/fcbklistselection.js"></script>
        <script type="text/javascript" src="js/utilidadesFormularios.js"></script>
        

        <script type="text/javascript">
            //jQuery.noConflict();
            function saveNewAngelAjax(idAngel){
                saveNewAngelSelected('<%= jspControler.getJspResources().getMenSave() %>',
                                     '<%= jspControler.getJspResources().getMenWait() %>', 
                                     idAngel);
            }
            
            function deleteAngelAjaxByErrorPostingWall(idAngel){
                deleteAngelSelectedByErrorPostingWall('<%= jspControler.getJspResources().getMenSave() %>',
                                     '<%= jspControler.getJspResources().getMenWait() %>', 
                                     idAngel);
            }
            
            function deleteAngelAjax(idAngel){
                deleteAngelSelected('<%= jspControler.getJspResources().getMenSave() %>',
                                     '<%= jspControler.getJspResources().getMenWait() %>', 
                                     idAngel);
            }
            
            function loadPostWallFriend(){
                jQuery(document).ready(function(){
                    if('<%= typeAngel%>' == 'F' && '<%= idAngel%>' != null){
                        loadFeedDialog();
                    }
                });
            }
            
            function muestraError(error, urlError){
                    if(error == true){
                        alert("error");
                    
                        // creamos el objeto 
                        oXMLHttp = new XMLHttpRequest(); 
                        // pedimos la página en modo síncrono 
                        oXMLHttp.open('get', 'index.htm', false); 
                        // enviamos los datos 
                        oXMLHttp.send(); 
                    }
                    alert("error 2");
            }
            
            function cargaMenu(){
                    jQuery(document).ready(function(){

                    var Menu = jQuery("#Menu").AjaxFlagMenu({
                        Caption:'<%= jspControler.getJspResources().getJspResourcesJQueryMenu().getTitle()%>',
                        CaptionClass:'<%= jspControler.getJspResources().getJspResourcesJQueryMenu().CAPTION_CLASS%>',
                        onOutClass:'<%= jspControler.getJspResources().getJspResourcesJQueryMenu().ON_OUT_CLASS%>',
                        onOverClass:'<%= jspControler.getJspResources().getJspResourcesJQueryMenu().ON_OVER_CLASS%>',
                        onClickClass:'<%= jspControler.getJspResources().getJspResourcesJQueryMenu().ON_CLICK_CLASS%>',
                        hscOutClass:'<%= jspControler.getJspResources().getJspResourcesJQueryMenu().HS_OUT_CLASS%>',
                        hscClickClass:'<%= jspControler.getJspResources().getJspResourcesJQueryMenu().HS_CLICK_CLASS%>',
                        Loading_gif:'<%= jspControler.getJspResources().getJspResourcesJQueryMenu().PATH_IMAGE_LOADING_MENU%>',
                        ajaxContent:'<%= jspControler.getJspResources().getJspResourcesJQueryMenu().AJAX_CONTENT%>'
                    });
                    //Angels
                    var Angels = Menu.add({
                        Title:'<%= jspControler.getJspResources().getJspResourcesAngelsMenu().getTitle()%>',
                        onOutIcon:'<%= jspControler.getJspResources().getJspResourcesAngelsMenu().ON_OUT_ICON%>',
                        onClickIcon:'<%= jspControler.getJspResources().getJspResourcesAngelsMenu().ON_CLICK_ICON%>',
                        HtmlSatusContent:'<%= jspControler.getJspResources().getJspResourcesAngelsMenu().HTML_STATUS_CONTENT%>',
                        url:'<%= jspControler.getJspResources().getJspResourcesAngelsMenu().DESTINY_URL_JSP%>',
                        data:'<%= jspControler.getJspResources().getJspResourcesAngelsMenu().getDataAngels()%>'
                    });
                    Angels.sethscCorner();
                    //Vigilants
                    var Vigilants = Menu.add({
                        Title:'<%= jspControler.getJspResources().getJspResourcesVigilantsMenu().getTitle()%>',
                        onOutIcon:'<%= jspControler.getJspResources().getJspResourcesVigilantsMenu().ON_OUT_ICON%>',
                        onClickIcon:'<%= jspControler.getJspResources().getJspResourcesVigilantsMenu().ON_CLICK_ICON%>',
                        HtmlSatusContent:'<%= jspControler.getJspResources().getJspResourcesVigilantsMenu().HTML_STATUS_CONTENT%>',
                        url:'<%= jspControler.getJspResources().getJspResourcesVigilantsMenu().DESTINY_URL_JSP%>',
                        data:'<%= jspControler.getJspResources().getJspResourcesVigilantsMenu().DATA%>'
                    });
                    Vigilants.sethscCorner();
                    jQuery("#Menu").corner("4px");
                    jQuery("#ajaxContent").corner("4px");

                    Angels.cargaInicio(
                    '<%= jspControler.getSnsObject().getConfigurationManager().getConfigHostApplicationSSL()%>',
                    '<%= jspControler.getJspResources().DESTINY_JSP_INIT%>',
                    '<%= jspControler.getJspResources().getTitleFbList()%>',
                    '<%= jspControler.getJspResources().getTitleAngelSettAng()%>',
                    '<%= jspControler.getJspResources().getNameTutor()%>',
                    '<%= jspControler.getJspResources().getEmailTutor()%>',
                    '<%= jspControler.getJspResources().getStrMenLoader()%>',
                    '<%= jspControler.getJspResources().getMenLoader()[3]%>',
                    '<%= jspControler.getJspResources().getMenLoader()[4]%>',
                    '<%= jspControler.getJspResources().getMenLoader()[0]%>',
                    '<%= jspControler.getJspResources().getMenLoader()[1]%>',
                    '<%= jspControler.getResultSave()%>',
                    '<%= jspControler.getModal()%>',
                    '<%= jspControler.getTypeAngel()%>',
                    '<%= jspControler.getIdFacebookAngel()%>',
                    '<%= jspControler.getUidAngel()%>',
                    '<%= jspControler.getUidPublic()%>',
                    '<%= jspControler.getJspResources().getTitlePostFacebook()%>',
                    '<%= jspControler.getJspResources().getSubtitlePostFacebook()%>',
                    '<%= jspControler.getJspResources().getBodyPostFacebook()%>');
                });
            }
            
        </script>
    </head>
    <body onload="cargaMenu();">
        <form id="frBackUp" accept-charset="utf-8" method="" action="">
            <input type="hidden" id="h1" name="h1" value="" />
            <input type="hidden" id="h2" name="h2" value="" />
            <input type="hidden" id="h3" name="h3" value="" />
            <input type="hidden" id="h4" name="h4" value="" />
            <input type="hidden" id="h5" name="h5" value="" />
            <input type="hidden" id="h6" name="h6" value="" />
            <input type="hidden" id="h7" name="h7" value="" />
            <input type="hidden" id="h8" name="h8" value="" />
            <input type="hidden" id="h9" name="h9" value="" />
            <input type="hidden" id="h10" name="h10" value="" />
            <input type="hidden" id="h11" name="h11" value="" />
            <input type="hidden" id="h12" name="h12" value="" />
            <input type="hidden" id="h13" name="h13" value="" />
            <input type="hidden" id="h14" name="h14" value="" />
            <input type="hidden" id="h15" name="h15" value="" />
            <input type="hidden" id="h16" name="h16" value="" />

            <div id="fb-root"></div>
            <script>
              window.fbAsyncInit = function() {
                // init the FB JS SDK
                FB.init({
                  appId      : '179105958774916',                        // App ID from the app dashboard
                  channelUrl : '//localhost/SNSAngelGuardFB/index.jsp', // Channel file for x-domain comms
                  status     : true,                                 // Check Facebook Login status
                  xfbml      : true                                  // Look for social plugins on the page
                });

                // Additional initialization code such as adding Event Listeners goes here
              };

              // Load the SDK asynchronously
              (function(d, s, id){
                 var js, fjs = d.getElementsByTagName(s)[0];
                 if (d.getElementById(id)) {return;}
                 js = d.createElement(s); js.id = id;
                 js.src = "//connect.facebook.net/en_US/all.js";
                 fjs.parentNode.insertBefore(js, fjs);
               }(document, 'script', 'facebook-jssdk'));
            </script>
            
            <div id="settings" style="width:1100px;">

                <div id="container">
                    <table style="width:950px;height: 620px;">
                        <tr>
                            <td></td>
                        </tr>
                        <tr>
                            <td width="210px" style="vertical-align:top;">
                                <div id="Menu">
                                </div>
                                <A class="linkHelp" onclick="lanzarModalAyuda('<%= jspControler.getJspResources().DESTINY_JSP_HELP%>',700,420);">
                                    <h1 class="letraNorm"><%= jspControler.getJspResources().getTitleHelp()[0]%></h1>
                                </A>
                            </td>
                            <td width="740px" style="vertical-align:top;">
                                <div id="ajaxContent">
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
                <div id="botoneraInferior" style="width:98%;height: 48px;" align="left" margin="15px">
                    <hr class="linea" />
                    <table>
                        <tr>
                            <td></td>
                        </tr>
                        <tr>
                            <td>
                                <input type="button" class="boton" id="btnSave" name="btnSave" value="<%= jspControler.getJspResources().getBtnSaveSettings()%>"
                                       onclick="saveSettings('<%= jspControler.getJspResources().getMenSave()%>',
                                               '<%= jspControler.getJspResources().getMenWait()%>');"/>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div id="modalContainer">
               <div id="modalDialog"></div>
            </div>
        </form>
    </body>
</html>
