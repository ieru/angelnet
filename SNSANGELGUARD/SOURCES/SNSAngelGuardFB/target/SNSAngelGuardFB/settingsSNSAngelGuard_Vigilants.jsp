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

        <script type="text/javascript" charset="UTF-8" src="js/jquery-1.5.1.min.js"></script>
        <script type="text/javascript" charset="UTF-8" src="js/jquery-ui-1.8.12.custom.min.js"></script>
        <script type="text/javascript" charset="UTF-8" src="js/settingsSNSAngelGuard_Vigilants.js"></script>
        <script type="text/javascript" charset="UTF-8" src="js/utilidadesFormularios.js"></script>
        <script type="text/javascript" charset="UTF-8" src="js/AjaxFlagMenu-1.0.2.min.js"></script>
        <script type="text/javascript" charset="UTF-8" src="js/jquery.loader.js"></script>
        <script type="text/javascript" charset="UTF-8" src="js/fcbklistselectionVig.js"></script>

        <script type="text/javascript" >
          $(function() {
                createHiddenFilters("#frSNSVigilants", '<%= controler.getListActiveFilters() %>');
          
                loadInicioDatesVigilants('<%= controler.getHdAngels() %>','<%= controler.getHdAngelsEd() %>','<%= controler.getHdAngelsGoogleSelected() %>',
                    '<%= controler.getJsonFiltersInfo() %>','<%= controler.getHdAngelsAux() %>');

                loadTurnOnOffBottons('<%= controler.getJspResources().getTitleAltVigOn() %>', '<%= controler.getJspResources().getTitleAltVigOff() %>');

                loadVigResources('<%= controler.getJspResources().getArrayVig() %>','<%= controler.getJspResources().getTitleAlarmNoAngelsSelect() %>');    
                    
                loadTitleFcbList('<%= controler.getJspResources().getTitleFbList() %>','<%= controler.getJspResources().getTitleAngelSettAng() %>');
                setArrayAngels('<%= controler.getSnsObject().getStringUtilities().arrayToString(controler.getArrayAngels())%>');
            });  

            $(function(){
                loadTitleFcbList('<%= controler.getJspResources().getTitleFbList() %>','<%= controler.getJspResources().getTitleAngelSettAng() %>');
                setArrayAngels('<%= controler.getSnsObject().getStringUtilities().arrayToString(controler.getArrayAngels())%>');

                loadStateFiltro('FltWall');
                loadStateFiltro('FltFriends');
                loadStateFiltro('FltPriv');
                loadStateFiltro('FltVist');

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
            <input type="hidden" id="hdAlarmNotVig1" name="hdAlarmNotVig1" value="" />
            <input type="hidden" id="hdAlarmNotVig2" name="hdAlarmNotVig2" value="" />
            <input type="hidden" id="hdAlarmNotVig3" name="hdAlarmNotVig3" value="" />
            <input type="hidden" id="hdAlarmNotVig4" name="hdAlarmNotVig4" value="" />
            <input type="hidden" id="hdNameVig1" name="hdNameVig1" value="" />
            <input type="hidden" id="hdNameVig2" name="hdNameVig2" value="" />
            <input type="hidden" id="hdNameVig3" name="hdNameVig3" value="" />
            <input type="hidden" id="hdNameVig4" name="hdNameVig4" value="" />

            <div id="vigilants" class="vigilants">
                <table width="700px">
                    <tr>
                        <td>
                            <table width="695px">
                                <tr>
                                    <td> <!--imagen del usuario del usuario--> 
                                        <div id="vigilantContainer1" class="vigilantContainer" onmouseover="this.className='vigilantContainerOver'" onmouseout="this.className='vigilantContainer'"
                                             onclick="seleccionVig('vigilantContainer1');loadEstadoFiltro('FltWall','../SNSAngelGuardFB/resources/robots/robot1.png');">
                                            <table width="698px">
                                                <tr>
                                                    <td>
                                                        <figure class="user"> 
                                                            <img id="imgContRobot1" src="../SNSAngelGuardFB/resources/robots/robot1.png" WIDTH="50" HEIGHT="72" alt="" />
                                                        </figure> 
                                                        <blockquote class="description arrowLeft"> <!--informacion del usuario--> 
                                                            <h1 class="vigilantDescription"><%= controler.getJspResources().getArrayDes()[1]%></h1>
                                                        </blockquote> 
                                                    </td>
                                                    <td width="35px" class="botonBox">
                                                        <table>
                                                            <tr>
                                                                <td>
                                                                    <img id="imgTurnOnOff1" src="../SNSAngelGuardFB/resources/turnOn.png" WIDTH="20" HEIGHT="20" alt="" />
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td>
                                                                    <img id="imgAlertNotAngels1" src="../SNSAngelGuardFB/resources/alertNotAngels.png" WIDTH="20" HEIGHT="20" alt="" />
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                            </table>
                                        </div>
                                    </td>
                                    <tr>
                                        <td> <!--imagen del usuario del usuario--> 
                                            <div id="vigilantContainer2" class="vigilantContainer" onmouseover="this.className='vigilantContainerOver'" onmouseout="this.className='vigilantContainer'"
                                                 onclick="seleccionVig('vigilantContainer2');loadEstadoFiltro('FltFriends','../SNSAngelGuardFB/resources/robots/robot2.png');">
                                                <table width="698px">
                                                    <tr>
                                                        <td>
                                                            <figure class="user"> 
                                                                <img id="imgContRobot2" src="../SNSAngelGuardFB/resources/robots/robot2.png" WIDTH="50" HEIGHT="72" alt="" />
                                                            </figure> 
                                                            <blockquote class="description arrowLeft"> <!--informacion del usuario--> 
                                                                <h1 class="vigilantDescription"><%= controler.getJspResources().getArrayDes()[2]%></h1>
                                                            </blockquote> 
                                                        </td>
                                                        <td width="35px" class="botonBox">
                                                            <table>
                                                                <tr>
                                                                    <td>
                                                                        <img id="imgTurnOnOff2" src="../SNSAngelGuardFB/resources/turnOn.png" WIDTH="20" HEIGHT="20" alt="" />
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td>
                                                                        <img id="imgAlertNotAngels2" src="../SNSAngelGuardFB/resources/alertNotAngels.png" WIDTH="20" HEIGHT="20" alt="" />
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td> <!--imagen del usuario del usuario--> 
                                            <div id="vigilantContainer3" class="vigilantContainer" onmouseover="this.className='vigilantContainerOver'" onmouseout="this.className='vigilantContainer'"
                                                 onclick="seleccionVig('vigilantContainer3');loadEstadoFiltro('FltPriv','../SNSAngelGuardFB/resources/robots/robot3.png');">
                                                <table width="698px">
                                                    <tr>
                                                        <td>
                                                            <figure class="user"> 
                                                                <img id="imgContRobot3" src="../SNSAngelGuardFB/resources/robots/robot3.png" WIDTH="50" HEIGHT="72" alt="" />
                                                            </figure> 
                                                            <blockquote class="description arrowLeft"> <!--informacion del usuario--> 
                                                                <h1 class="vigilantDescription"><%= controler.getJspResources().getArrayDes()[3]%></h1>
                                                            </blockquote> 
                                                        </td>
                                                        <td width="35px" class="botonBox">
                                                            <table>
                                                                <tr>
                                                                    <td>
                                                                        <img id="imgTurnOnOff3" src="../SNSAngelGuardFB/resources/turnOn.png" WIDTH="20" HEIGHT="20" alt="" />
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td>
                                                                        <img id="imgAlertNotAngels3" style="display:none" src="../SNSAngelGuardFB/resources/alertNotAngels.png" WIDTH="20" HEIGHT="20" alt="" />
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td> <!--imagen del usuario del usuario--> 
                                            <div id="vigilantContainer4" class="vigilantContainer" onmouseover="this.className='vigilantContainerOver'" onmouseout="this.className='vigilantContainer'"
                                                 onclick="seleccionVig('vigilantContainer4');loadEstadoFiltro('FltVist','../SNSAngelGuardFB/resources/robots/robot4.png');">
                                                <table width="698px">
                                                    <tr>
                                                        <td>
                                                            <figure class="user"> 
                                                                <img id="imgContRobot4" src="../SNSAngelGuardFB/resources/robots/robot4.png" WIDTH="50" HEIGHT="72" alt="" />
                                                            </figure> 
                                                            <blockquote class="description arrowLeft"> <!--informacion del usuario--> 
                                                                <h1 class="vigilantDescription"><%= controler.getJspResources().getArrayDes()[4]%></h1>
                                                            </blockquote> 
                                                        </td>
                                                        <td width="35px" class="botonBox">
                                                            <table>
                                                                <tr>
                                                                    <td>
                                                                        <img id="imgTurnOnOff4" src="../SNSAngelGuardFB/resources/turnOn.png" WIDTH="20" HEIGHT="20" alt="" />
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td>
                                                                        <img id="imgAlertNotAngels4" src="../SNSAngelGuardFB/resources/alertNotAngels.png" WIDTH="20" HEIGHT="20" alt="" />
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
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div id="vigilantSettings" class="vigilantSettingsNone">
                                <table width="100%">
                                    <tr>
                                        <td width="175px" class="botonBoxMiddle">
                                            <img id="imgCurrentVig" src="../SNSAngelGuardFB/resources/robots/robot1.png" WIDTH="160" HEIGHT="200" alt="" />
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
