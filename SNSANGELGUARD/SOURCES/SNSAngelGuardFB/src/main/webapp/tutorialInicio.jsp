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
        <title>JSP Page</title>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

        <link type="text/css" rel="stylesheet" href="Styles/facebook.css" />

        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript" charset="ISO-8859-1" src="js/tutorialInicio.js" ></script>
        <script type="text/javascript" >
          $(function() {
              initLoadValues("<%= jspControler.getLocale() %>", 
              "<%= jspControler.getResources().getTitleFirstPage() %>", 
              "<%= jspControler.getResources().getTitlePreviousPage() %>", 
              "<%= jspControler.getResources().getTitleNextPage() %>", 
              "<%= jspControler.getResources().getTitleLastPage() %>", 
              "<%= jspControler.getResources().getDescPagesTutorial() %>");
              
              getFirst();
          });
          </script>
    </head>
    <body>
        <form id="tutorialInicio" onload="">
            <input type="hidden" id="hdLocaleValue" name="hdLocaleValue" value="" />
            <input type="hidden" id="hdTitleFirst" name="hdTitleFirst" value="" />
            <input type="hidden" id="hdTitlePrevious" name="hdTitlePrevious" value="" />
            <input type="hidden" id="hdTitleNext" name="hdTitleNext" value="" />
            <input type="hidden" id="hdTitleLast" name="hdTitleLast" value="" />
            <input type="hidden" id="hdDesTutInitHelp" name="hdDesTutInitHelp" value="" />
            <div id="tutInitial">
                <center>
                    <div id="contenido" style="width:97%;" >

                        <div class="desTut centrar-imagen">
                            <table>
                                <tr>
                                    <td id="idTdDescription">
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div class="imgTut centrar-imagen">
                            <table>
                                <tr>
                                    <td>
                                        <img class="currentImgTut" id="currentImgTut" src="" /> 
                                    </td>
                                </tr>
                            </table>
                        </div>
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
