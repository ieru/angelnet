<%--
    Document   : index
    Created on : 20-dic-2010, 13:36:31
    Author     : tote
--%>

<%@page import="es.uah.cc.ie.snsangelguardfb.exception.InterEmailException"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.exception.InterProcessException"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.exception.InterDataBaseException"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.sources.jspcontroler.entity.IndexJSPControler"%>
<%@page import="es.uah.cc.ie.snsangelguardfb.SNSAngelGuardFBManager"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
<%@page import="java.lang.Class"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>



<%
    IndexJSPControler controler = null;
    try {
        request.getSession(true);
        controler = new IndexJSPControler(request, response);

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
        <title>SNSAngelGuard</title>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        
        <!--import jquery lib -->
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
        <script type="text/javascript" charset="ISO-8859-1" src="js/utilidadesFormularios.js"></script>
        
        
    </head>
    <body>
        <form id="frIndex" action="" method="">
            <input type="hidden" id="uid" name="uid" value="" />
            <input type="hidden" id="accessToken" name="accessToken" value="" />
            
            <div id="fb-root">
                <script>
                      window.fbAsyncInit = function() {
                        // init the FB JS SDK
                        FB.init({
                           appId      : '<%= controler.getSnsObject().getConfigurationManager().getApiKey() %>',                        // App ID from the app dashboard
                          channelUrl : '//' + '<%= controler.getSnsObject().getConfigurationManager().getConfigHostApplicationSSL() %>' + 'SNSAngelGuardFB/index.jsp', // Channel file for x-domain comms
                          status     : true,                                 // Check Facebook Login status
                          xfbml      : true                                  // Look for social plugins on the page
                        });

                        FB.getLoginStatus(function(response) {
                        if (response.status === 'connected') {
                            var uid = response.authResponse.userID;
                            var accessToken = response.authResponse.accessToken;
                            
                            redirectToScheduler(uid, accessToken);
                        }
                        });
                        // Additional initialization code such as adding Event Listeners goes here
                      };

                      // Load the SDK asynchronously
                      (function(d, s, id){
                         var js, fjs = d.getElementsByTagName(s)[0];
                         if (d.getElementById(id)) {return;}
                         js = d.createElement(s); js.id = id;
                         js.src = "//connect.facebook.net/en_US/all.js";
                         fjs.parentNode.insertBefore(js, fjs);
                       }(document, 'script', 'facebook-jssdk'));
                </script>
            </div>
        </form>
    </body>
</html>
