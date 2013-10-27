<%-- 
    Document   : saveEmailFacebookContact
    Created on : 14-oct-2013, 19:58:06
    Author     : josejavierblecuadepedro1
--%>

<%@page import="es.uah.cc.ie.snsangelguardfb.exception.InterEmailException"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.exception.InterProcessException"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.exception.InterDataBaseException"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.sources.jspcontroler.entity.SaveEmailFacebookContactJSPControler"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
        SaveEmailFacebookContactJSPControler jspControler = null;

        try {
            jspControler = new SaveEmailFacebookContactJSPControler(request, response);
            jspControler.process();
        } catch (InterDataBaseException e) {
            String exceptionAsString = jspControler.getSnsObject().getExceptionManager().exceptionToString(e.getException());

            response.sendRedirect(request.getContextPath() + "/infoError.jsp?errorMessage=" + e.getMessageException() + "&exception=" + exceptionAsString);
        } catch (InterProcessException e) {
            String exceptionAsString = jspControler.getSnsObject().getExceptionManager().exceptionToString(e.getException());

            response.sendRedirect(request.getContextPath() + "/infoError.jsp?errorMessage=" + e.getMessageException() + "&exception=" + exceptionAsString);
        } catch (InterEmailException e) {
            String exceptionAsString = jspControler.getSnsObject().getExceptionManager().exceptionToString(e.getException());

            response.sendRedirect(request.getContextPath() + "/infoError.jsp?errorMessage=" + e.getMessageException() + "&exception=" + exceptionAsString);
        }
%>
<!DOCTYPE html>
<html>
    <head>

        <title><%= jspControler.getSnsObject().getLocaleSettingsDaoManager().getLocaleSettingsDao().getTitleAngConfirm() %></title>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />

        <link type="text/css" rel="stylesheet" href="Styles/facebook.css" />
        <link rel="stylesheet" type="text/css" href="Styles/jquery.loader.css" />

        <!--import jquery lib -->
        <script type="text/javascript" src="js/jQuery-1.8.2.js"></script>
        <script type="text/javascript" charset="UTF-8" src="js/jquery.loader.js"></script>
        <script type="text/javascript" charset="ISO-8859-1" src="js/utilidadesFormularios.js"></script>
        <script>
            jQuery(document).ready(function(){
                $(function(){
                    $('#par1').val('<%= jspControler.getUidPublicUser()%>');
                    $('#par2').val('<%= jspControler.getUidAngel()%>');
                });
            });
        </script>
    </head>
    <body>
        <form id="frSaveEmailFacebookContact" action="angelConfirmation.jsp" method="">
            <input type="hidden" id="par1" name="par1" value="0" />
            <input type="hidden" id="par2" name="par2" value="0" />
            <input type="hidden" id="par3" name="par3" value="0" />
            <input type="hidden" id="par4" name="par4" value="0" />
            <input type="hidden" id="par5" name="par5" value="0" />
            
            <div id="divAngelUserContainer" class="divAngelUserContainer">
                <center>
                    <table width="95%">
                        <tr>
                            <td>
                                <div id="divAngelUserTitle">
                                    <table width="100%">
                                        <tr>
                                            <td width="90%">
                                                <h1 class="tituloMed"><%= jspControler.getSnsObject().getLocaleSettingsDaoManager().getLocaleSettingsDao().getTitleAngConfirm() %></h1>
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
                                        <table id="tabUserInformation" class="tabCenterAngel">
                                            <tr>
                                                <td width="95%">
                                                    <h1 class="textAngelSave"><%= jspControler.getNotificationMsg() %></h1>
                                                </td>
                                            </tr>
                                            
                                        </table>
                                        <div id="friendsImportContainer" class="angelDatesContainer" style="margin:5px;">
                                            <table id="tabUserDates" class="tabEmailAngel">
                                                <tr class="angelDatesTr">
                                                    <td width="50%">
                                                        <label for="txtNameContact"><%= jspControler.getSnsObject().getLocaleSettingsDaoManager().getLocaleSettingsDao().getTxtNameTutorSettAng() %></label>
                                                        <input type="text" disabled="disabled" name="txtNameContact" id="txtNameContact" size="25" maxlength="70" onchange="" value="<%= jspControler.getNameAngelValue() %>" />
                                                    </td>
                                                    <td width="50%">
                                                        <label for="txtEmailContact"><%= jspControler.getSnsObject().getLocaleSettingsDaoManager().getLocaleSettingsDao().getTxtEmailTutorSettAng() %></label>
                                                        <input type="text" name="txtEmailContact" id="txtEmailContact" size="25" maxlength="70" onchange="" value="" />
                                                    </td>
                                                </tr>
                                            </table>
                                        </div>
                                    </center>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div id="botoneraInferior" align="right" margin="15px">
                                    <table width="100%">
                                        <tr>
                                            <td width="98%">
                                                <hr class="linea" />
                                                <table width="25%" align="right">
                                                    <tr>
                                                        <td >
                                                            <input type="button" class="boton" name="btnAceptar" value="<%=jspControler.getSnsObject().getLocaleSettingsDaoManager().getLocaleSettingsDao().getBtnAgreeAT()%>"
                                                                   onclick="validarEmail('<%= jspControler.getEmptyEmailMsg() %>','<%= jspControler.getNotValidEmailMsg() %>', 
                                                                                         '<%= jspControler.getLoaderSave() %>','<%= jspControler.getLoaderWait() %>');"/>
                                                        </td>
                                                        <td align="right">
                                                            <input type="button" class="boton" name="btnCancelar" value="<%=jspControler.getSnsObject().getLocaleSettingsDaoManager().getLocaleSettingsDao().getBtnCancelAT()%>"
                                                                   onclick="deleteContactInEmailPage('<%= jspControler.getLoaderSave() %>','<%= jspControler.getLoaderWait() %>');"/>
                                                        </td>
                                                        <td width="10%">
                                                            <img src="<%= jspControler.PATH_IMAGE_LOADING%>" style="display: none" />
                                                        </td>
                                                    </tr>
                                                </table>
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
