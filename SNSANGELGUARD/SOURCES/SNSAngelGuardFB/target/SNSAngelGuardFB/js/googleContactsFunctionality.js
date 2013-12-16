/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var clientId = '541921444258.apps.googleusercontent.com';
var apiKey = 'iU3fBdbX_2PFSTwsGpVQe-Dm';
var scopes = 'https://www.googleapis.com/auth/plus.me';
var MAX = 8;

function auth() {
    
    var config = {
        'client_id': clientId,
        'scope': 'https://www.google.com/m8/feeds/'
    };
    
    gapi.auth.authorize(config, function() {
        
        gapi.auth.getToken();
         
        getMyContacts();
        
    });
}

function rellenarVaciosContactGoogle(longitud, codigo) {
    $(function() {
        var faltan = MAX - longitud;
        var vacias = codigo;

        for (var i = 0; i < faltan; i++) {
            vacias = vacias + '<tr id="contact' + longitud + '" class="pijama1">'
                    + '<td width="25px"></td>'
                    + '<td width="286px"></td>'
                    + '<td width="286px"></td>'
                    + '</tr>';

            longitud = longitud + 1;
        }

        $("#tabContactsModal tbody").append(vacias);
    });
}

function getMyContacts() {
    muestraLoaderWithoutMsgBlue();
    $(function() {
        var contacts = '';
        var cont = parseInt(0);
        var hdAngelsGoogleSelected = $("#hdAngelsGoogleSelectedModal").val();

        var myToken = gapi.auth.getToken();
        myToken.alt = 'json';
        
        $.ajax({
            type: 'GET',
            url: '../SNSAngelGuardFB/GoogleContactsServlet?access_token=' + encodeURIComponent(myToken.access_token) + '&hdAngelsGoogleSelected=' + encodeURIComponent(hdAngelsGoogleSelected),
            dataType: "text",
            success: function(data) {
                
                var xmlJson = JSON.parse(data);
                $(xmlJson).each(function(index, value) {
                    var email = value.emailContact;
                    var name = value.nameContact;
                    var linkPhoto = value.linkPhoto;

                    contacts = contacts + '<tr id="contactModal' + cont + '" class="pijama1">'
                            + '<td width="25px">'
                            + '<input type="radio" id="rdContactModal' + cont + '" '
                            + 'onclick="seleccionarFila(\'#contactModal' + cont + '\',\'#rdContactModal' + cont + '\',\'' + name + '\',\'' + email + '\');" />'
                            + '</td>'
                            + '<td width="286px">' + name + '</td>'
                            + '<td width="286px">' + email + '</td>'
                            + '</tr>';

                    cont = parseInt(cont) + parseInt(1);
                });


                // Insert empty rows
                if (xmlJson.length < MAX) {
                    rellenarVaciosContactGoogle(emailAddresses.length, contacts);
                } else {
                    $("#tabContactsModal tbody").html(contacts);
                }
                
                deshabilitarBotonQuery("#btnLoginGoogle");
                deshabilitarBotonQuery("#btnAceptarModal");
                
                cerrarLoaderMod();
            }
        });
        
        
    });
}


