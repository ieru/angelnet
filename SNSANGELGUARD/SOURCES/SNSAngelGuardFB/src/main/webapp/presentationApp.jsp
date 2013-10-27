<%-- 
    Document   : finalError
    Created on : 23-oct-2013, 20:25:00
    Author     : josejavierblecuadepedro1
--%>

<%@page import="es.uah.cc.ie.snsangelguardfb.exception.InterEmailException"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.exception.InterProcessException"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.exception.InterDataBaseException"%>
<%@page import="org.codehaus.jettison.json.JSONObject"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.SNSAngelGuardFBManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
        SNSAngelGuardFBManager snsObject = null;
        String nameUser = null;
        String srcImg = null;
        String appLink = null;
        
        try {
            //Obtenemos la seson de la aplicacion
            snsObject = SNSAngelGuardFBManager.getSessionInstance(request);

            String uidPublic = request.getParameter("par1");

            // Obtener informaci√≥n de userSettings por su uidPublic
            JSONObject jsonUser = snsObject.getUserSettingsDaoManager().getUserSettingsDAO().getJsonUserByUidPublic(snsObject, uidPublic);

            snsObject.getUserSettingsDaoManager().loadUserConnected(jsonUser);
            snsObject.getLocaleSettingsDaoManager().loadLocaleSettingsOffLine();
            
            //Obtenemos la conexión a Facebook
            snsObject.getLoginAppOffline(request, response);

            nameUser = "SNSAngelGuardFB";
            srcImg = snsObject.getConfigurationManager().getConfigHostApplicationSSL() + "SNSAngelGuardFB/resources/logo75.gif";
            appLink = "http://www.facebook.com/apps/application.php?id=179105958774916";
        } catch (InterDataBaseException e) {
            String exceptionAsString = snsObject.getExceptionManager().exceptionToString(e.getException());

            response.sendRedirect(request.getContextPath() + "/infoError.jsp?errorMessage=" + e.getMessageException() + "&exception=" + exceptionAsString);
        } catch (InterProcessException e) {
            String exceptionAsString = snsObject.getExceptionManager().exceptionToString(e.getException());

            response.sendRedirect(request.getContextPath() + "/infoError.jsp?errorMessage=" + e.getMessageException() + "&exception=" + exceptionAsString);
        } catch (InterEmailException e) {
            String exceptionAsString = snsObject.getExceptionManager().exceptionToString(e.getException());

            response.sendRedirect(request.getContextPath() + "/infoError.jsp?errorMessage=" + e.getMessageException() + "&exception=" + exceptionAsString);
        }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>

        <title><%= nameUser %></title>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />

        <link type="text/css" rel="stylesheet" href="Styles/facebook.css" />

        <script type="text/javascript" charset="ISO-8859-1" src="js/utilidadesFormularios.js"></script>
    </head>
    <body >
        <form id="frPresentationApp" action="">
            <div id="divAngelUserContainer" class="divAngelUserContainer">
                <center>
                    <table width="95%">
                        <tr>
                            <td>
                                <div id="divAngelUserTitle">
                                    <table width="100%">
                                        <tr>
                                            <td width="90%">
                                                <h1 class="tituloMed"><%= nameUser %></h1>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width="90%">
                                                <hr class="linea" />
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div id="divAngelUser" class="divAngelUser">
                                    <center>
                                        <table id="tabUserInformation" class="tabCenter">
                                            <tr>
                                                <td width="35%" align="center">
                                                    <img src="<%= srcImg %>" alt="">
                                                </td>
                                                <td width="65%">
                                                    <h1 class="aceptinTerms"> <%= nameUser%>
                                                    </h1>
                                                </td>
                                            </tr>
                                        </table>
                                    </center>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div id="botoneraInferior" align="right">
                                    <table width="100%">
                                        <tr>
                                            <td>
                                                <hr class="linea" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td align="left">
                                                <A class='letraNorm' href='<%= appLink %>'>SNSAngelGuard</A><%= snsObject.getLocaleSettingsDaoManager().getLocaleSettingsDao().getInfoAngGuard() %>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </td>
                        </tr>
                    </table>
                </center>
            </div>
        </form>
    </body>
</html>