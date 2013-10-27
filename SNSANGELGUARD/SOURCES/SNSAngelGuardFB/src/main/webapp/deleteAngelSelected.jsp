<%-- 
    Document   : deleteAngelSelected
    Created on : 21-oct-2013, 20:18:15
    Author     : josejavierblecuadepedro1
--%>

<%@page import="es.uah.cc.ie.snsangelguardfb.exception.InterEmailException"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.exception.InterProcessException"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.exception.InterDataBaseException"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.sources.jspcontroler.entity.DeleteAngelSelectedJSPControler"%>

<%
            DeleteAngelSelectedJSPControler jspControler = null;
            
            try {
                    jspControler = new DeleteAngelSelectedJSPControler(request, response);
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
