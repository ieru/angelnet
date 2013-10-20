<%-- 
    Document   : saveNewAngel
    Created on : 13-oct-2013, 21:03:10
    Author     : josejavierblecuadepedro1
--%>
<%@page import="es.uah.cc.ie.snsangelguardfb.exception.InterEmailException"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.exception.InterProcessException"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.exception.InterDataBaseException"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.sources.jspcontroler.entity.SaveNewAngelJSPControler"%>

<%
            SaveNewAngelJSPControler jspControler = null;
            
            try {
                    jspControler = new SaveNewAngelJSPControler(request, response);
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

