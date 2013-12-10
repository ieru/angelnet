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
    $(function() {
        var contacts = '';
        var cont = parseInt(0);
        var hdAngelsGoogleSelected = $("#hdAngelsGoogleSelectedModal").val().split(";");

        var authParams = gapi.auth.getToken() // from Google oAuth

        authParams.alt = 'json';

        $.ajax({
            url: 'https://www.google.com/m8/feeds/contacts/default/full?max-results=1000',
            dataType: 'jsonp',
            async: false,
            data: authParams,
            success: function(data) {
                var xmlJson = data.feed.entry;
                $(xmlJson).each(function() {
                    var entry = $(this);
                    var nameObject = entry[0].title;
                    var name = $(nameObject).attr('$t');

                    if (entry[0].gd$email != null) {
                        var emailAddresses = entry[0].gd$email;
                        if (emailAddresses.length > 0) {
                            for (var j = 0; j < emailAddresses.length; j++) {
                                var email = emailAddresses[j].address;

                                var isSelectedContact = false;

                                if (hdAngelsGoogleSelected != '') {
                                    for (var i = 0; i < hdAngelsGoogleSelected.length; i++) {

                                        if (hdAngelsGoogleSelected[i] != '') {
                                            var emailAngel = getJsonIdValue(hdAngelsGoogleSelected[i], 'email');
                                            var nameAngel = getJsonIdValue(hdAngelsGoogleSelected[i], 'name');
                                            if (email == emailAngel && name == nameAngel) {
                                                isSelectedContact = true;
                                                break;
                                            }
                                        }
                                    }
                                }

                                if (isSelectedContact == false) {
                                    contacts = contacts + '<tr id="contactModal' + cont + '" class="pijama1">'
                                            + '<td width="25px">'
                                            + '<input type="radio" id="rdContactModal' + cont + '" '
                                            + 'onclick="seleccionarFila(\'#contactModal' + cont + '\',\'#rdContactModal' + cont + '\',\'' + name + '\',\'' + email + '\');" />'
                                            + '</td>'
                                            + '<td width="286px">' + name + '</td>'
                                            + '<td width="286px">' + email + '</td>'
                                            + '</tr>';

                                    cont = parseInt(cont) + parseInt(1);
                                }
                            }
                        }
                    }
                });

                // Insert empty rows
                if (xmlJson.length < MAX) {
                    rellenarVaciosContactGoogle(emailAddresses.length, contacts);
                } else {
                    $("#tabContactsModal tbody").html(contacts);
                }
                
                deshabilitarBotonQuery("#btnLoginGoogle");
                deshabilitarBotonQuery("#btnAceptarModal");
            }
        });
    });
}

