<%-- 
    Document   : doOperationWithOtherContact
    Created on : 24-nov-2013, 10:40:30
    Author     : josejavierblecuadepedro1
--%>

<%@page import="es.uah.cc.ie.snsangelguardfb.exception.InterEmailException"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.exception.InterProcessException"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.exception.InterDataBaseException"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.sources.jspcontroler.entity.DoOperationWithOtherContactJSPControler"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
        DoOperationWithOtherContactJSPControler controler = null;

        try {
            request.getSession(false);
            controler = new DoOperationWithOtherContactJSPControler(request, response);

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
