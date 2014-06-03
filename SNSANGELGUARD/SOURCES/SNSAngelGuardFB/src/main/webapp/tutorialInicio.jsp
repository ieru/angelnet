<%-- 
    Document   : tutorialInicio
    Created on : 03-jun-2014, 19:22:37
    Author     : josejavierblecuadepedro1
--%>

<%@page import="es.uah.cc.ie.snsangelguardfb.exception.InterEmailException"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.exception.InterProcessException"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.exception.InterDataBaseException"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.sources.jspcontroler.entity.TutorialInicioJSPControler"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    TutorialInicioJSPControler jspControler = null;
    String exceptionAsString = null;
    
    try {
            jspControler = new TutorialInicioJSPControler(request, response);
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
        <title>JSP Page</title>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

        <link type="text/css" rel="stylesheet" href="Styles/facebook.css" />

        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript" charset="ISO-8859-1" src="js/tutorialInicio.js" ></script>
        <script type="text/javascript" >
          $(function() {
              getFirst();
          });
          </script>
    </head>
    <body>
        <form id="tutorialInicio">
            <input type="hidden" id="hdLocaleValue" name="hdLocaleValue" value="<%= jspControler.getLocale() %>" />
            <input type="hidden" id="hdTitleFirst" name="hdTitleFirst" value="<%= jspControler.getResources().getTitleFirstPage() %>" />
            <input type="hidden" id="hdTitlePrevious" name="hdTitlePrevious" value="<%= jspControler.getResources().getTitlePreviousPage() %>" />
            <input type="hidden" id="hdTitleNext" name="hdTitleNext" value="<%= jspControler.getResources().getTitleNextPage() %>" />
            <input type="hidden" id="hdTitleLast" name="hdTitleLast" value="<%= jspControler.getResources().getTitleLastPage() %>" />
            <input type="hidden" id="hdDesTutInitHelp" name="hdDesTutInitHelp" value="<%= jspControler.getResources().getDescPagesTutorial() %>" />
            <div id="tutInitial">
                <center>
                    <div id="contenido" style="width:97%;" >
                        <h1 class="titulo">Tutorial Inicial</h1>
                        <table>
                            <tr>
                            <div class="centrar-imagen">
                                <td width="95%" id="idTdDescription">
                                    <h1 class="letraNormTut">Aqui iria una descripción de la paginaAqui iria una descripción de la paginaAqui iria una descripción de la paginaAqui iria una descripción de la paginaAqui iria una descripción de la paginaAqui iria una descripción de la paginaAqui iria una descripción de la pagina</h1>
                                </td>
                            </div>
                            </tr>
                            <tr>
                                <td>
                                    <div class="centrar-imagen">
                                        <img class="currentImgTut" id="currentImgTut" src="../SNSAngelGuardFB/resources/fondoBienvenida.png" /> 
                                    </div>
                                </td>
                            </tr>
                        </table>
                        <div id="botoneraInferior">
                            <table>
                                <tr>
                                    <td>
                                        <img id="imgFirstPag" src="../SNSAngelGuardFB/resources/pagination/first_des.png" class="paginationImg" />
                                    </td>
                                    <td>
                                        <img id="imgPreviousPag" src="../SNSAngelGuardFB/resources/pagination/previous_des.png" class="paginationImg" />
                                    </td>
                                    <td>
                                        <img id="imgNextPag" src="../SNSAngelGuardFB/resources/pagination/next_des.png" class="paginationImg" />
                                    </td>
                                    <td>
                                        <img id="imgLastPag" src="../SNSAngelGuardFB/resources/pagination/last_des.png" class="paginationImg" />
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </center>
            </div>
        </form>
    </body>
</html>
