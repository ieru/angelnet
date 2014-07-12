<%-- 
    Document   : helpMe
    Created on : 18-may-2011, 13:51:11
    Author     : josej
--%>


<%@page import="org.codehaus.jettison.json.JSONObject"%>
<%@page import="java.io.FileNotFoundException"%>
<%@page import="java.io.IOException"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.SNSAngelGuardFBManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
        SNSAngelGuardFBManager snsObject = null;
        String[] localeHelp = null;
        boolean ifOffline = false;
        String wait = "";
        String menWait = "";
        
        try {
            //Obtenemos la conexión a Facebook
            snsObject = SNSAngelGuardFBManager.getSessionInstance(request);
            
            String uidPublic = request.getParameter("par1");
            
            // Si estamos en una conexion offline, obtenemos el idioma del usuario a traves de su uid publico.
            if (uidPublic != null) {
                // Obtener informaci√≥n de userSettings por su uidPublic
                JSONObject jsonUser = snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getJsonUserByUidPublic(snsObject, uidPublic);

                snsObject.getUserSettingsDaoManager().loadUserConnected(jsonUser);
                snsObject.getLocaleSettingsDaoManager().loadLocaleSettingsOffLine();
                
                ifOffline = true;
            }
            
            localeHelp = snsObject.getStringUtilities().stringToArray(snsObject.getLocaleSettingsDaoManager().getLocaleSettingsDao().getHelpMe());
            wait = snsObject.getStringUtilities().stringToArray(snsObject.getLocaleSettingsDaoManager().getLocaleSettingsDao().getWarnings())[3];
            menWait = snsObject.getStringUtilities().stringToArray(snsObject.getLocaleSettingsDaoManager().getLocaleSettingsDao().getWarnings())[4];
        } catch (FileNotFoundException e) {
            String exceptionAsString = snsObject.getExceptionManager().exceptionToString(e);

            response.sendRedirect(request.getContextPath() + "/infoError.jsp?errorMessage=" + e.getMessage() + "&exception=" + exceptionAsString);
        } catch (IOException e) {
            String exceptionAsString = snsObject.getExceptionManager().exceptionToString(e);

            response.sendRedirect(request.getContextPath() + "/infoError.jsp?errorMessage=" + e.getMessage() + "&exception=" + exceptionAsString);
        }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <title><%= localeHelp[1] %></title>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />

        <link type="text/css" rel="stylesheet" href="Styles/facebook.css" />

        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.23/ui/jquery-ui.js"></script>
        <script type="text/javascript" charset="ISO-8859-1" src="js/utilidadesFormularios.js"></script>
        
        <script type="text/javascript">
            // Si estamos online, muestra el link para acceder de nuevo al tutorial
            $(document).ready(function() {
                showLinkTutorial(<%= ifOffline %>, '<%= localeHelp[8] %>', '<%= wait %>', '<%= menWait %>');
            });
        </script>
    </head>
    <body >
        <form id="frAngelNotification" action="">
            <div id="divAngelNotificationContainer" class="divAngelNotificationContainer">
                <table width="100%">
                    <tr>
                        <td width="95%">
                            <div id="idShowTutorial" class="showTutorial"></div>
                        </td>
                    </tr>
                    <tr>
                        <td width="95%">
                            <h1 class="tituloMed"><%= localeHelp[2] %></h1>
                            <hr class="linea" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <h1 class="acuerdoLegal"><%= localeHelp[3] %></h1>
                        </td>
                    </tr>
                    <tr>
                        <td width="95%">
                            <h1 class="tituloMed"><%= localeHelp[4] %></h1>
                            <hr class="linea" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <h1 class="acuerdoLegal"><%= localeHelp[5] %></h1>
                        </td>
                    </tr>
                    <tr>
                        <td width="95%">
                            <h1 class="tituloMed"><%= localeHelp[6] %></h1>
                            <hr class="linea" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <h1 class="acuerdoLegal"><%= localeHelp[7] %></h1>
                        </td>
                    </tr>
                </table>
            </div>
        </form>
    </body>
</html>

