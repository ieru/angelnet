<%-- 
    Document   : updateFacebookFriendsOnline
    Created on : 12-jul-2014, 13:36:06
    Author     : josejavierblecuadepedro1
--%>

<%@page import="es.uah.cc.ie.snsangelguardfb.exception.InterEmailException"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.exception.InterProcessException"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.exception.InterDataBaseException"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.sources.jspcontroler.entity.UpdateFacebookFriendsOnlineJSPControler"%>

<%
        UpdateFacebookFriendsOnlineJSPControler jspControler = null;
        try {
            jspControler = new UpdateFacebookFriendsOnlineJSPControler(request, response);
            
            // Lanzamos la accion
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
