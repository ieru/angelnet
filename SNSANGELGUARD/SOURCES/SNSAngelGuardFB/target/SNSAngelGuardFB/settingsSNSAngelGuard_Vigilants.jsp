<%-- 
    Document   : settingsSNSAngelGuard_Vigilants
    Created on : 31-mar-2011, 23:00:34
    Author     : tote
--%>


<%@page import="es.uah.cc.ie.snsangelguardfb.exception.InterEmailException"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.exception.InterProcessException"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.exception.InterDataBaseException"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.sources.jspcontroler.entity.SettingsSNSAngelGuardJSPControler_Vigilants"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.SNSAngelGuardFBManager"%>
<%@page import="java.net.URLDecoder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%

    SettingsSNSAngelGuardJSPControler_Vigilants controler = null;

    try {
        controler = new SettingsSNSAngelGuardJSPControler_Vigilants(request, response);
        controler.process();
    } catch (InterDataBaseException e) {
        String exceptionAsString = controler.getSnsObject().getExceptionManager().exceptionToString(e.getException());

        response.sendRedirect(request.getContextPath() + "/infoError.jsp?errorMessage=" + e.getMessageException() + "&exception=" + exceptionAsString);
    } catch (InterProcessException e) {
        String exceptionAsString = controler.getSnsObject().getExceptionManager().exceptionToString(e.getException());

        response.sendRedirect(request.getContextPath() + "/infoError.jsp?errorMessage=" + e.getMessageException() + "&exception=" + exceptionAsString);
    } catch (InterEmailException e) {
        String exceptionAsString = controler.getSnsObject().getExceptionManager().exceptionToString(e.getException());

        response.sendRedirect(request.getContextPath() + "/infoError.jsp?errorMessage=" + e.getMessageException() + "&exception=" + exceptionAsString);
    }

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"lang="es" xml:lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title><%=controler.getSnsObject().getLocaleSettingsDaoManager().getLocaleSettingsDao().getTitleSettVig()%></title>

        <link type="text/css" rel="stylesheet" href="Styles/facebook.css" />
        <link type="text/css" rel="stylesheet" href="Styles/jquery.loader.css" />
        <link type="text/css" rel="stylesheet" href="Styles/fcbklistselectionVig.css" />
        <link type="text/css" rel="stylesheet" href="Styles/styleSwitchVig.css" />

        <script type="text/javascript" charset="UTF-8" src="js/jquery-1.5.1.min.js"></script>
        <script type="text/javascript" charset="UTF-8" src="js/jquery-ui-1.8.12.custom.min.js"></script>
        <script type="text/javascript" charset="UTF-8" src="js/settingsSNSAngelGuard_Vigilants.js"></script>
        <script type="text/javascript" charset="UTF-8" src="js/utilidadesFormularios.js"></script>
        <script type="text/javascript" charset="UTF-8" src="js/AjaxFlagMenu-1.0.2.min.js"></script>
        <script type="text/javascript" charset="UTF-8" src="js/jquery.loader.js"></script>
        <script type="text/javascript" charset="UTF-8" src="js/fcbklistselectionVig.js"></script>

        <script type="text/javascript" >
            $(function() {
                createHiddenFilters("#frSNSVigilants", '<%= controler.getListActiveFilters()%>');

                loadInicioDatesVigilants('<%= controler.getHdAngels()%>', '<%= controler.getHdAngelsEd()%>', '<%= controler.getHdAngelsGoogleSelected()%>',
                        '<%= controler.getJsonFiltersInfo()%>', '<%= controler.getListActiveFilters()%>', '<%= controler.getHdAngelsAux()%>');
                        
                loadHTMLFilters('<%= controler.getJspResources().getArrayDes() %>');

                loadTurnOnOffBottons('<%= controler.getJspResources().getTitleAltVigOn()%>', '<%= controler.getJspResources().getTitleAltVigOff()%>');

                loadVigResources('<%= controler.getJspResources().getArrayVig()%>', '<%= controler.getJspResources().getTitleAlarmNoAngelsSelect()%>');

                loadTitleFcbList('<%= controler.getJspResources().getTitleFbList()%>', '<%= controler.getJspResources().getTitleAngelSettAng()%>');
                setArrayAngels('<%= controler.getSnsObject().getStringUtilities().arrayToString(controler.getArrayAngels())%>');
            });

            $(function() {
                var keysFilterArray = $("#hdArrayKeysFilter").val().split(";");
                for (var i = 0; i < keysFilterArray.length; i++) {
                    loadStateFiltro(keysFilterArray[i]);
                }

                habilitarGuardar();
            });
        </script>
    </head>
    <body>
        <form id="frSNSVigilants" action="" method="">
            <input type="hidden" id="hdAngels" name="hdAngels" value="" />
            <input type="hidden" id="hdAngelsEd" name="hdTxtNameAngel" value="" />
            <input type="hidden" id="hdAngelsGoogleSelected" name="hdAngelsGoogleSelected" value="" />
            <input type="hidden" id="hdAngelsAux" name="hdAngelsAux" value="" />
            <input type="hidden" id="hdFiltroActual" name="hdFiltroActual" value="" />

            <!-- Variables de idioma  -->
            <input type="hidden" id="hdTitleFbList" name="hdTitleFbList" value="0" />
            <input type="hidden" id="hdTitleAngelSettAng" name="hdTitleAngelSettAng" value="0" />
            <input type="hidden" id="hdTitleAltVigOn" name="hdTitleAltVigOn" value="" />
            <input type="hidden" id="hdTitleAltVigOff" name="hdTitleAltVigOff" value="" />
            <input type="hidden" id="hdJsonFilterInfo" name="hdJsonFilterInfo" value="" />
            <input type="hidden" id="hdArrayKeysFilter" name="hdArrayKeysFilter" value="" />

            <div id="vigilants" class="vigilants">
                <table width="650px">
                    <tr>
                        <td>
                            <div id="wrapperVigilantDefinition" class="wrapperVigilantDefinition">
                                <div id="vigilantDefinition" class="vigilantDefinition"></div>                            
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div id="vigilantSettings" class="vigilantSettingsNone">
                                <table width="100%">
                                    <tr>
                                        <td width="175px" class="botonBoxMiddle">
                                            <img id="imgCurrentVig" src="" WIDTH="160" HEIGHT="200" alt="" />
                                        </td>
                                        <td width="425px">
                                            <table width="100%">
                                                <tr>
                                                    <td>
                                                        <div id="vigilantFrec" class="vigilantFrec">
                                                            <table width="100%">
                                                                <tr>
                                                                    <td width="60%" align="left">
                                                                        <h1 class="letraNorm"><%= controler.getJspResources().getTitleVigFrecSettVig()%> </h1>
                                                                    </td>
                                                                    <td width="40%" align="right">
                                                                        <select id="slcFrecuency" disabled="disabled" class="slcFrecuency"
                                                                                onchange="obtenerFrecuencia(this.id);">
                                                                            <option value="0"><%= controler.getJspResources().getArrayFrc()[0]%></option>
                                                                            <option value="1"><%= controler.getJspResources().getArrayFrc()[1]%></option>
                                                                            <option value="2"><%= controler.getJspResources().getArrayFrc()[2]%></option>
                                                                            <option selected value="3"><%= controler.getJspResources().getArrayFrc()[3]%></option>
                                                                            <option value="4"><%= controler.getJspResources().getArrayFrc()[4]%></option>
                                                                            <option value="5"><%= controler.getJspResources().getArrayFrc()[5]%></option>
                                                                            <option value="6"><%= controler.getJspResources().getArrayFrc()[6]%></option>
                                                                        </select>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <h1 class="letraNorm"><%=controler.getJspResources().getTitleVigAngSettVig()%></h1>
                                                        <div id="vigilantAngels" class="vigilantAngels">
                                                        </div>
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
            </div>
        </form>
    </body>
</html>
