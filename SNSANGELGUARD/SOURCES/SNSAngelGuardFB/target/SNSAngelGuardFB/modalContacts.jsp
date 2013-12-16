<%-- 
    Document   : modalContacts
    Created on : 13-abr-2011, 0:28:57
    Author     : tote
--%>


<%@page import="java.net.URLDecoder"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.SNSAngelGuardFBManager"%>
<%@page import="java.net.URLDecoder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
            request.setCharacterEncoding("utf-8");
            
    
            //Obtenemos la conexión a Facebook
            SNSAngelGuardFBManager snsObject = SNSAngelGuardFBManager.getSessionInstance(request);
            snsObject.logSession(request, response);
            
            // Obtenemos los mensajes de espera del loader
            String[] arrayWarnings = snsObject.getLocaleSettingsDaoManager().getLocaleSettingsDao().getWarnings().split(";");
            String menSave = arrayWarnings[3];
            String menWait = arrayWarnings[4];
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title><%=snsObject.getLocaleSettingsDaoManager().getLocaleSettingsDao().getTitleGoogleCont()%></title>

        <link type="text/css" rel="stylesheet" href="Styles/facebook.css" />
        <link rel="stylesheet" type="text/css" href="Styles/jquery.loader.css" />
        
        <script type="text/javascript" src="js/jQuery-1.8.2.js"></script>
        <script type="text/javascript" src="js/jquery.loader.js"></script>
        <script type='text/javascript' src='js/utilidadesFormularios.js'></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.23/ui/jquery-ui.js"></script>
        <script type="text/javascript" src="https://apis.google.com/js/client.js"></script>
        <script type='text/javascript' src="js/googleContactsFunctionality.js"></script>
        
    </head>
    <body onload="iniciarModal();deshabilitarBotonQuery('#btnAceptarModal');">
        <form id="frModalContacts" action="" method="GET">
            <input type="hidden" id="hdAngelsGoogleSelectedModal" name="hdAngelsGoogleSelectedModal" value="" />
            <input type="hidden" id="hdAngelsGoogleSelected" name="hdAngelsGoogleSelected" value="" />
            <input type="hidden" id="hdMenSave" name="hdMenSave" value="<%= menSave %>" />
            <input type="hidden" id="hdMenWait" name="hdMenWait" value="<%= menWait %>" />

            <div id='picker_container' style='width: 100%; margin: auto'>
                <table width="100%">
                    <tr>
                        <td width="60%">
                            <h1 class="tituloMed"><%=snsObject.getLocaleSettingsDaoManager().getLocaleSettingsDao().getTitleContGoogleCont()%></h1>
                        </td>
                        <td width="40%" align="right">
                            <input type="button" class="boton" id="btnLoginGoogle" value="<%=snsObject.getLocaleSettingsDaoManager().getLocaleSettingsDao().getBtnLogGoogleCont()%>"
                                   onclick="auth() "/>
                        </td>
                    </tr>
                </table>
                <hr class="linea" />
                <div id="contactContainer" style="margin:10px;">
                    <table width="630px">
                        <tr class="cabecera">
                            <td width="27px"></td>
                            <td width="291px"><%=snsObject.getLocaleSettingsDaoManager().getLocaleSettingsDao().getTitleNameContactGoogleCont()%></td>
                            <td width="292px"><%=snsObject.getLocaleSettingsDaoManager().getLocaleSettingsDao().getTitleEmailContactGoogleCont()%></td>
                            <td width="13px"></td>
                        </tr>
                    </table>
                    <div id="contactContent" class="contactContent" style="border:1px solid #CCC;">
                        <table id="tabContactsModal">
                            <tr class="pijama1">
                                <td width="25px"></td>
                                <td width="286px"></td>
                                <td width="286px"></td>
                            </tr>
                            <tr class="pijama1">
                                <td width="25px"></td>
                                <td width="286px"></td>
                                <td width="286px"></td>
                            </tr>
                            <tr class="pijama1">
                                <td width="25px"></td>
                                <td width="286px"></td>
                                <td width="286px"></td>
                            </tr>
                            <tr class="pijama1">
                                <td width="25px"></td>
                                <td width="286px"></td>
                                <td width="286px"></td>
                            </tr>
                            <tr class="pijama1">
                                <td width="25px"></td>
                                <td width="286px"></td>
                                <td width="286px"></td>
                            </tr>
                            <tr class="pijama1">
                                <td width="25px"></td>
                                <td width="286px"></td>
                                <td width="286px"></td>
                            </tr>
                            <tr class="pijama1">
                                <td width="25px"></td>
                                <td width="286px"></td>
                                <td width="286px"></td>
                            </tr>
                            <tr class="pijama1">
                                <td width="25px"></td>
                                <td width="286px"></td>
                                <td width="286px"></td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div id="botoneraInferior" style="width:100%;" align="left" margin="15px">
                    <hr class="linea" />
                    <table>
                        <!-- <tr height="25px" > -->
                        <tr>
                            <td>
                                <input type="button" class="boton" id="btnAceptarModal" value="<%=snsObject.getLocaleSettingsDaoManager().getLocaleSettingsDao().getBtnAceptGoogleCont()%>" />
                            </td>
                            <td>
                                <input type="button" class="boton" id="btnCancelar" value="<%=snsObject.getLocaleSettingsDaoManager().getLocaleSettingsDao().getBtnCancelGoogleCont()%>"
                                       onclick="salirModalCancelar();"/>
                            </td>
                            <td width="100%" align="right">
                                <img src="../SNSAngelGuardFB/resources/gmail.gif" align="middle" alt="">
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </form>
    </body>
</html>