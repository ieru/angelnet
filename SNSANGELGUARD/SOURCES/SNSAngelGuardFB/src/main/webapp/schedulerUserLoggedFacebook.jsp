<%-- 
    Document   : schedulerUserLoggedFacebook
    Created on : 22-oct-2013, 20:28:24
    Author     : josejavierblecuadepedro1
--%>
<%@page import="es.uah.cc.ie.snsangelguardfb.exception.InterEmailException"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.exception.InterProcessException"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.exception.InterDataBaseException"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.sources.jspcontroler.entity.SchedulerUserLoggedFacebookJSPControler"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    SchedulerUserLoggedFacebookJSPControler controler = null;
    try {
        request.getSession(false);
        controler = new SchedulerUserLoggedFacebookJSPControler(request, response);

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