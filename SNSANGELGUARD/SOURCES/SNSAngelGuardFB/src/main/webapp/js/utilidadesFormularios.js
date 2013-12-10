/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var arrayAngelsVig = '';

function enviarFormularioIndex(destino)
{
    document.getElementById('frIndice').setAttribute("action", destino);
    document.getElementById('frIndice').submit();
}

function getDataLegalAccepted(){
    var paramSal = '';

    paramSal = 'newConection=' + $('#newConection').val() +
               '&ok=' + $('#ok').val();

    return paramSal;
}

function enviarFormularioLegalA(destino, menSave, menWait){
    muestraLoader(menSave,menWait);
    
    if(destino == "settingsSNSAngelGuard.jsp"){
        document.getElementById('frLegalAccepted').setAttribute("action", destino);
        document.getElementById('frLegalAccepted').setAttribute('method','post');
        document.getElementById('frLegalAccepted').submit();
    }
    else{
        document.getElementById('frLegalAccepted').setAttribute("action", "final.jsp");
        document.getElementById('frLegalAccepted').submit();
    }
}

function mostrarMenOk(newConection, ok, mensaje){

    
}

function enviarFormularioUserDates(){

    if(document.getElementById('chkCtrAmigos').checked)
        chkFriendsAct = "1";
    else
        chkFriendsAct = "0";

    if(document.getElementById('chkCtrMuro').checked)
        chkWallAct = "1";
    else
        chkWallAct = "0";
    
    txtNameTutor = document.getElementById('txtNomTutor').value;
    txtEmailTutor = document.getElementById('txtEmailTutor').value;
    txtTimeRefresh = document.getElementById('lstTimeRefresh').value;

    if(document.getElementById('chkAppActiva').checked)
        chkAppAct = "1";
    else
        chkAppAct = "0";
    
    window.location= "../SNSAngelGuardFB/checkNow.jsp"
    +"?chkFriendsAct="+chkFriendsAct
    +"&chkCtrMuro="+chkWallAct
    +"&txtNomTutor="+txtNameTutor
    +"&txtEmailTutor="+txtEmailTutor
    +"&lstTimeRefresh="+txtTimeRefresh
    +"&chkAppActiva="+chkAppAct;
}

function activarChkAppAct(){
    if(document.getElementById('chkCtrAmigos').checked || document.getElementById('chkCtrMuro').checked){
        document.getElementById('appAct').className = 'h1v';
        document.getElementById('chkAppActiva').checked = true;
    }

    else
    {
        document.getElementById('appAct').className = 'h1r';
        document.getElementById('chkAppActiva').checked = false;
    }
}

function accentCorrect(strCadena){
    
}

function remove_accent(str){

    var from = "??????????????????????????????????????????????",
    to   = "AAAAAEEEEIIIIOOOOUUUUaaaaaeeeeiiiioooouuuunncc",
    mapping = {};

    for(var i = 0, j = from.length; i < j; i++ )
        mapping[ from.charAt( i ) ] = to.charAt( i );

    var ret = [];
    for( var i = 0, j = str.length; i < j; i++ ) {
        var c = str.charAt( i );
        if( mapping.hasOwnProperty( str.charAt( i ) ) )
            ret.push( mapping[ c ] );
        else
            ret.push( c );
    }
    return ret.join( '' );
  
}

function removeApp(){
    window.location="http://www.facebook.com";
}

function cargarListaAngels(arrayAngels,strSubAng){
    var strLista;
    if((arrayAngels.toString().substring(0,1) == "[") &&
        (arrayAngels.toString().substring(arrayAngels.toString().length - 1,arrayAngels.toString().length) == "]")) {
        var auxArray = arrayAngels.toString().substring(1,arrayAngels.toString().length - 1);
        arrayAngels = auxArray;

        var angels = arrayAngels.split(';');
        for(var i=0;i<angels.length;i++){
            var strAngels = angels[i].toString();
            if((strAngels.substring(0,1) == "[") &&
                (strAngels.substring(strAngels.length - 1,strAngels.length) == "]")) {
                var auxAngels = strAngels.substring(1,strAngels.length - 1);
                //alert("VALOR ANGEL: " +  auxAngels);
                var angelDates = auxAngels.split(',');

                var li = document.createElement("li");
                li.className = "";
                
                strLista =
                '<table><tr><td width="30%" style="vertical-align:top;"><img src="' + angelDates[2] + '" \/></td>' +
                '<td width="70%" style="vertical-align:top;"><strong>' + angelDates[1] +' </strong><br \/>'+
                '<span class="fcbkitem_text">' + strSubAng + '</span></td></tr>' +
                '</table><input type="hidden" id="fcbklist_value[]" value="' + angelDates[0]+ '" \/>';

                li.innerHTML = strLista;
                var ul = document.getElementById("fcbklist");
                ul.appendChild(li);
            }
        }
    }
}

function cargarListaVig(strFbList){
    var array = strFbList.split(";");

    $(document).ready(function() {
        //id(ul id),width,height(element height),row(elements in row)
        $.fcbkListSelection("#fcbklist","450","50","2",array[0],array[1],array[2]);
    });
}

var vent = null;
var miVentana;

function showDialog(url, ancho, alto) {

    var dialogDiv = $('#modalDialog');
    
    dialogDiv.html('<iframe style="border: 0px; " src="' + url + '" width="100%" height="100%"></iframe>')
            .dialog({
        height: alto,
        maxheight: alto, 
        width: ancho,
        maxWidth: ancho,
        resizeable: false,
        draggable: false,
        modal: true,
        close: function() {
            dialogDiv = $('<div id=\"modalDialog\"></div>').appendTo('#modalContainer');
            $("#modalDialog").dialog("destroy").remove();
        }
    });
        
    dialogDiv.dialog("open");
}


function lanzarModal(url, ancho, alto) {
    $(function() {
        url = url + "&isModal=1";
        showDialog(url, ancho, alto);
    });
}


function lanzarModalGoogleContacts(url, ancho, alto) {
    $(function() {
        var par = "hdAngelsGoogleSelected=" + encodeURIComponent($("#hdAngelsGoogleSelected").val());
        url = url + '?' + par;
        
        showDialog(url, ancho, alto);
    });
}

function lanzarModalAyuda(url, ancho, alto) {
    $(function() {
        showDialog(url, ancho, alto);
    });
}

function controlModal()
{
    if (window.showModalDialog){
        window.opener.focus = false;
        self.focus();
    }
}

function parentDisable(){
    window.opener.document.body.disabled=true;
}

function parentAble(){
    window.opener.document.body.disabled=false;
}

function deshabilitarBoton(idBoton){
    document.getElementById(idBoton).className = "botonDisabled";
}

function habilitarBoton(idBoton){
    document.getElementById(idBoton).className = "boton";
}

function deshabilitarBotonQuery(idBoton){
    $(function(){
        $(idBoton).attr("class", "botonDisabled");
    });
}

function habilitarBotonQuery(idBoton){
    $(function(){
        $(idBoton).attr("class", "boton");
    });
}

function limpiarLista(longitud){
    for(var i = 0;i < longitud; i++){
        var elemento = document.getElementById('contact'+i);
        if(elemento){
            document.getElementById('contact'+i).className = 'pijama1';
            document.getElementById('rdContact'+i).checked = false;
        }
    }
}

function activaFila(pos){
    if(document.getElementById('contact'+ pos)){
        document.getElementById('contact'+ pos).className = 'pijama2';
        document.getElementById('rdContact'+ pos).checked = true;
    }
}

function loadLstModal(arrayAngels){
    var tabla = document.getElementById('tabContacts');
    var nameElement = '';
    var emailElement = '';
    var angel = '';
    var nameAngel = '';
    var emailAngel = '';

    for(var i=0;i<tabla.rows.length;i++){
        nameElement = tabla.rows[i].cells[1].innerHTML;
        emailElement = tabla.rows[i].cells[2].innerHTML;

        for(var j=0;j<arrayAngels.length - 1;j++){
            angel = arrayAngels[j].split(",");
            if(angel[0].split(":") == false){
                formatNameSp(arrayAngels[i]);
            }
            nameAngel = angel[0].split(":");
            emailAngel = angel[1].split(":");

            if((formatName(nameAngel[1]).indexOf(nameElement) != -1) && (formatName(emailAngel[1]).indexOf(emailElement) != -1)){
                activaFila(i);
            }
        }
    }
}

function cargarInicioModal(){
    var arrayAngels = '';
    
    document.getElementById('hdAngelsGoogleSelected').value = window.opener.document.getElementById('hdAngelsGoogleSelected').value;
    if(document.getElementById('hdAngelsGoogleSelected').value != ''){
        arrayAngels = (document.getElementById('hdAngelsGoogleSelected').value).split(";");
        loadLstModal(arrayAngels);
    }
}

function getJsonIdValue(jsonObject,type){
    var jsonObjectArray = jsonObject.split(",");
    var jsonValue;
    
    if(type=='id'){
        jsonValue = (jsonObjectArray[0]).split(":");
    } else if(type=='name'){
        jsonValue = (jsonObjectArray[1]).split(":");
    } else if(type=='email'){
        jsonValue = (jsonObjectArray[2]).split(":");
    } 

    return formatName(jsonValue[1]);
}

function getJsonObject(idContact) {
    var angelsEd = $('#hdAngelsEd').val();

    if (angelsEd != '') {
        var arrayContactsEd = angelsEd.split(";");
        var longitud = parseInt(arrayContactsEd.length) - parseInt(1);

        if (longitud > 0) {
            for (var i = 0; i < longitud; i++) {
                if (getJsonIdValue(arrayContactsEd[i], 'id') == idContact) {
                    return arrayContactsEd[i];
                }
            }
        }
    }
}

function delToJsonAngelsNameEmail(nameAddress, emailAddress, des) {
    var angelsSelected = ($('#hdAngels' + des).val()).split(";");
    var auxAngelSelected = '';
    var angel = '';
    var nameAngel = '';
    var auxName = '';
    var emailAngel = '';
    var auxEmail = '';

    for (var i = 0; i < angelsSelected.length - 1; i++) {
        angel = angelsSelected[i].split(",");
        nameAngel = angel[1].split(":");
        auxName = formatName(nameAngel[1]);

        emailAngel = angel[2].split(":");
        auxEmail = formatName(emailAngel[1]);

        if (auxName.indexOf(nameAddress) == -1 && auxEmail.indexOf(emailAddress) == -1) {
            if (auxAngelSelected == '') {
                auxAngelSelected = angelsSelected[i] + ';';
            } else {
                auxAngelSelected += angelsSelected[i] + ';';
            }
        }
    }
    $('#hdAngels' + des).val(auxAngelSelected);
}

function delToJsonAngels(nameAddress,des){
    $(function() {
        var angelsSelected = ($('#hdAngels' + des).val()).split(";");
        var auxAngelSelected = '';
        var angel = '';
        var nameAngel = '';
        var auxName = '';

        for (var i = 0; i < angelsSelected.length - 1; i++) {
            angel = angelsSelected[i].split(",");
            nameAngel = angel[1].split(":");

            auxName = formatName(nameAngel[1]);
            if (auxName.indexOf(nameAddress) == -1) {
                if (auxAngelSelected == '') {
                    auxAngelSelected = angelsSelected[i] + ';';
                } else {
                    auxAngelSelected += angelsSelected[i] + ';';
                }
            }
        }
        $('#hdAngels' + des).val(auxAngelSelected);
    });
}

function delToJsonAngelsById(idAngelDel,des){
    $(function() {
        var angelsSelected = ($('#hdAngels' + des).val()).split(";");
        var auxAngelSelected = '';
        var angel = '';
        var idAngel = '';
        var auxId = '';

        for (var i = 0; i < angelsSelected.length - 1; i++) {
            angel = angelsSelected[i].split(",");
            idAngel = angel[0].split(":");

            auxId = formatName(idAngel[1]);
            if (auxId.indexOf(idAngelDel) == -1) {
                if (auxAngelSelected == '') {
                    auxAngelSelected = angelsSelected[i] + ';';
                } else {
                    auxAngelSelected += angelsSelected[i] + ';';
                }
            }
        }
        $('#hdAngels' + des).val(auxAngelSelected);
    });
}

function addToJsonAngels(idAngel, nameAddress, emailAddress, des) {
    var angelsSelected = $('#hdAngels' + des).val();
    var toJSONvalue = '{' + '\"idContact' + des + '\":' + '\"' + idAngel + '\",' + '\"nameAngel' + des + '\":' + '\"' + formatNameComa(nameAddress) + '\", ' + '\"emailAngel' + des + '\":' + '\"' + emailAddress + '\"' + '}';

    if (angelsSelected == '') {
        angelsSelected = toJSONvalue + ';';
    } else {
        angelsSelected += toJSONvalue + ';';
    }
    $('#hdAngels' + des).val(angelsSelected);
}

function delItemLstJsonAngels(){
    $(function(){
        var tabAngelsEd = $('#tabFriendsImport');
        var nameAngel = '';
        var emailAngel = '';

        // Se guarda la lista de angeles anterior
        $('#hdAngelsEdAux').attr("value", $('#hdAngelsEd').val());
        // Se inicializa la lista de angeles.
        $('#hdAngelsEd').attr("value", "");
        // Se empiezan a introducir aquellos angeles distintos de vac??o.
        for(var i=0;i<tabAngelsEd.attr('rows').length;i++){

            if($('#txtNameTutorEd' + i) && $('#txtEmailTutorEd' + i)){
                    nameAngel = $('#txtNameTutorEd' + i).val();
                    emailAngel = $('#txtEmailTutorEd' + i).val();
                    
                    if((nameAngel != '' && emailAngel != '') && (typeof nameAngel != 'undefined' && typeof emailAngel != 'undefined')){
                        addToJsonAngels(nameAngel,emailAngel,'Ed');
                    }
                }
        }
        $('#hdAngelsEdAux').attr("value","");
        $('#tabFriendsImport').attr("innerHTML","");
    });
}

function buscarBorrados(desFiltro){
    var encontrado = false;
    var angelsEdAux = (document.getElementById('hdAngels'+ desFiltro +'Aux').value).split(";");
    var angelsEd = (document.getElementById('hdAngels' + desFiltro).value).split(";");
    var aux = "";
    var angel = "";
    var addresAngel = "";
    var addres = "";
    var angelEd = "";
    var addresAngelEd = "";
    var addresEd = "";

    for(var i = 0;i < angelsEdAux.length - 1; i++){
        encontrado = false;
        angel = angelsEdAux[i].split(",");
        addresAngel = angel[1].split(":");
        addres = formatName(addresAngel[1]);
        for(var j = 0; j < angelsEd.length - 1; j++){
            angelEd = angelsEd[j].split(",");
            addresAngelEd = angelEd[1].split(":");
            addresEd = formatName(addresAngelEd[1]);
            if(addresEd.indexOf(addres) != -1){
                encontrado = true;
                break;
            }
        }

        if(encontrado == false){
            if(aux == ""){
                aux = angelsEdAux[i] + ";";
            }else{
                aux = aux + angelsEdAux[i] + ";";
            }
        }
    }

    document.getElementById('hdAngels'+ desFiltro +'Aux').value = aux;
}

function borrarContactGoogle(){
    var deshabilitar = true;

    buscarBorrados('GoogleSelected');
    borrarItems('GoogleSelected');

    pintarTablaConGoogle(document.getElementById('hdAngelsGoogleSelected').value,'');

    if(deshabilitar){
        deshabilitarBoton('btnDelContact');
    }
    else{
        habilitarBoton('btnDelContact');
    }
    habilitarGuardar();
}

function habilitarBorrar(){
    var tabla = document.getElementById('tabContacts');
    var deshabilitar = true;

    if(tabla.innerHTML != ''){
        for(var i=0;i<tabla.rows.length;i++){
            if(document.getElementById('rdContact'+ i)){
                if(document.getElementById('rdContact'+ i).checked == true){
                    deshabilitar = false;
                    break;
                }
            }
        }
    }
    
    if(deshabilitar){
        deshabilitarBoton('btnDelContact');
    }else{
        habilitarBoton('btnDelContact');
    }

}

function seleccionarFilaGoogle(idAngel, elemento, radio, nameAddress, emailAddress){
    // Test if the element is selected
    $(function() {
        if ($(elemento).attr("class") == 'pijama2') {
            delToJsonAngelsById(idAngel, 'GoogleSelectedDel');

            $(radio).attr("checked", false);
            $(elemento).attr("class", "pijama1");
        } else {
            addToJsonAngels(idAngel, nameAddress, emailAddress, 'GoogleSelectedDel');
            $(radio).attr("checked", true);
            $(elemento).attr("class", "pijama2");

            habilitarBorrar();
        }

        habilitarBorrar();
    });
}

function borrarItemEdFiltro(idElemento, desFiltro){
    if((document.getElementById('hdAngels' + desFiltro).value != '') && (document.getElementById('hdAngels' + desFiltro).value != 'null')){
        var lstFiltro = (document.getElementById('hdAngels' + desFiltro).value).split(";");
        var aux = '';

        for(var i = 0 ; i < lstFiltro.length - 1; i++){
            if(lstFiltro[i] == idElemento){
                if(i == 0){
                    aux = lstFiltro[i] + ";";
                }else{
                    aux = aux + lstFiltro[i] + ";";
                }
            }
        }

        document.getElementById('hdAngels' + desFiltro).value = aux;
    }
}

function comprobarAceptar(){
    if(document.getElementById('hdAngelsGoogleSelected').value == ''){
        deshabilitarBoton('btnAceptar');
    }else{
        habilitarBoton('btnAceptar');
    }
}

function seleccionarFila(elemento,radio,nameAddress,emailAddress){
        // Test if the element is selected
        if ($(elemento).attr("class") == 'pijama1') {          
            addToJsonAngels(null, nameAddress, emailAddress, 'GoogleSelected');

            $(radio).attr("checked", true);
            $(elemento).attr("class", "pijama2");

            habilitarBotonQuery('#btnAceptarModal');
        } else {
            delToJsonAngelsNameEmail(nameAddress, emailAddress, 'GoogleSelected');

            $(radio).attr("checked", false);
            $(elemento).attr("class", "pijama1");
            
            if($('#hdAngelsGoogleSelected').val() === '' || $('#hdAngelsGoogleSelected').val() === null){
                deshabilitarBotonQuery('#btnAceptarModal');
            }
}
}

function formatName(nameContact){
    if(nameContact.indexOf('{') != -1){
        nameContact = nameContact.replace(/{/g,"");
    }

    if(nameContact.indexOf('}') != -1){
        nameContact = nameContact.replace(/}/g,"");
    }

    if(nameContact.indexOf('"') != -1){
        nameContact =nameContact.replace(/"/g,"");
    }

    if(nameContact.indexOf('&3') != -1){
        nameContact =nameContact.replace(/&3/g,",");
    }

    if(nameContact.indexOf('=') != -1){
        nameContact =nameContact.replace(/=/g,"");
    }

    return nameContact;
}

function formatNameComa(nameContact){
    if(nameContact.indexOf(',') != -1){
        nameContact = nameContact.replace(/,/g,"&3");
    }
    return nameContact;
}

var MAX_FILAS_GOOGLE = 3;

function rellenarVacio(longitud,codigo,modo) {
    var faltan = parseInt(MAX_FILAS_GOOGLE) - parseInt(longitud);
    var vacias = codigo;

    for(var i=0;i<faltan;i++){
        vacias = vacias + '<tr id="contact'+ longitud +'" class="pijama1">'
        + '<td width="25px"></td>'
        + '<td width="230px"></td>'
        + '<td width="230px"></td>'
        + '</tr>';

        longitud = longitud + 1;
    }

    if(modo == 'modal'){
        window.opener.document.getElementById('tabContacts').innerHTML = vacias;
    }else{
        document.getElementById('tabContacts').innerHTML = vacias;
        habilitarGuardar();
    }

}

function formatNameSp(elemArray){

}

function pintarTablaConGoogle(jsonContacts,modo){
    var contacts = '';
    var arrayJson = jsonContacts.split(";");
    var angel = '';
    var nameAngel = '';
    var emailAngel = '';
    var idAngel = '';
    
    if(modo == 'modal'){
        window.opener.document.getElementById('tabContacts').innerHTML = '';
    }else{
        document.getElementById('tabContacts').innerHTML = '';
    }

    var total = parseInt(arrayJson.length) - parseInt(1);

    for(var i=0;i<total;i++){
        angel = arrayJson[i].split(",");
        if(angel[0].split(":") == false){
            formatNameSp(arrayJson[i]);
        }
        
        idAngel = angel[0].split(":");
        nameAngel = angel[1].split(":");
        emailAngel = angel[2].split(":");

        contacts = contacts + '<tr id="contact'+ i +'" class="pijama1">'
        + '<td width="25px">'
        + '<input type="radio" id="rdContact'+ i +'" '
        + 'onclick="seleccionarFilaGoogle(' + formatName(idAngel[1])  + ', \'#contact'+i+'\',\'#rdContact'+i+'\',\''+formatName(nameAngel[1])+'\',\''+formatName(emailAngel[1])+'\');" />'
        + '</td>'
        + '<td width="230px">' + formatName(nameAngel[1]) + '</td>'
        + '<td width="230px">' + formatName(emailAngel[1]) + '</td>'
        + '</tr>';
    }

    // Insert empty rows
    if(total < MAX_FILAS_GOOGLE){
        rellenarVacio(total, contacts, modo);
    }else{
        if(modo == 'modal'){
            window.opener.document.getElementById('tabContacts').innerHTML = contacts;
        }else{
            document.getElementById('tabContacts').innerHTML = contacts;
        }
    }
}

function loadAngelSelects(angels){
                if (typeof angels === "undefined") {
                    return;
                }
                
                var arrayAngels = angels.split(';');

                for(var i=0;i<arrayAngels.length; i++){
                    $.each($("#fcbklist").children("li").children(".fcbklist_item"),function(index,object){
                        object=$(object);
                        if(object.find("[type=hidden]").val() == arrayAngels[i]){
                            $("#view_selected_count").text(parseInt($("#view_selected_count").text(),10)+1);
                            object.parents("li").attr("addedid","tester");
                            object.toggleClass("itemselected");
                            object.parents("li").toggleClass("liselected");
                        }
                    });
                }
            }

function enviarFormularioContacts(menSave, menWait, aceptar){
    $(function(){
        var angelsGoogleSelected = escape($('#hdAngelsGoogleSelected').val());
        
        salirModal(menSave, menWait);
        launchDoOperationWithGmailContact("0", angelsGoogleSelected, "true");
    });

}

function deleteOfFilterAngelGoogleSelected(angelsGoogleSelected){
    var arrayAngels = angelsGoogleSelected.split(";");
    
    for(var i = 0; i< arrayAngels.length; i++){
        var angel = arrayAngels[i].split(",");
        
        if(angel[0] != ''){
            var emailItem = angel[2].split(":");
            emailItem = formatName(emailItem[1]);
            
            deleteAngelOfAllFilters(emailItem);
        }
    }
}

function enviarFormularioBorrarContacts(menSave, menWait){
    $(function(){
        // Borramos los angeles seleccionados de todos los filtros
        deleteOfFilterAngelGoogleSelected($('#hdAngelsGoogleSelectedDel').val());
        
        // Codificamos la variable para ser enviada via HTTP
        var angelsGoogleSelected = escape($('#hdAngelsGoogleSelectedDel').val());
        
        // Mostramos el loader de carga
        muestraLoader(menSave, menWait);
        
        // Lanzamos la operacion de servidor
        launchDoOperationWithGmailContact("2", angelsGoogleSelected, "false");
    });

}


function cerrarInfoModal(){
    if($('#hdIsModal').val() === '1'){
        window.parent.salirModalSinDatos();
    }else{
        window.close();
    }
}

function cerrarInfoError(uidPublic){
    $(function(){
        var url = '';
        
        if(uidPublic != null){
            url = '../SNSAngelGuardFB/presentationApp.jsp?par1=' + uidPublic;
        } else{
            url = '../SNSAngelGuardFB/presentationApp.jsp';
        }
        
        $(location).attr('href', url);
    });
}

function loadContactsEd(menSave, menWait){
    $(function(){
        var angelsEd = $('#hdAngelsEd').val();
        if(angelsEd != ''){
            var arrayContactsEd = angelsEd.split(";");
            var longitud = parseInt(arrayContactsEd.length) - parseInt(1);

            if(longitud > 0){
                for(var i=0;i<longitud;i++){
                    pintarNuevoContacto(menSave, menWait, i, getJsonIdValue(arrayContactsEd[i],'id'));
                    $('#txtNameTutorEd' + i).attr("value",getJsonIdValue(arrayContactsEd[i],'name'));
                    $('#txtEmailTutorEd' + i).attr("value",getJsonIdValue(arrayContactsEd[i],'email'));
                    habilitarBotonBorrarContacto(menSave, menWait, i, getJsonIdValue(arrayContactsEd[i],'id'));
                }
            }else{
                pintarNuevoContacto(menSave, menWait, 0);
                $('#txtNameTutorEd0').attr("value","");
                $('#txtEmailTutorEd0').attr("value","");
            }
        }
        else{
            pintarNuevoContacto(menSave, menWait, 0);
            $('#txtNameTutorEd0').attr("value","");
            $('#txtEmailTutorEd0').attr("value","");
        }
    });
}

function existAngelEd(nameAngel, emailAngel){
    var arrayAngels = ($('#hdAngelsEd').val()).split(";");
    
    for(var i=0;i<arrayAngels.length;i++){
        if(arrayAngels[i] != ''){
            var jsonAngel = $.parseJSON(arrayAngels[i]);
        
            if(jsonAngel.nameAngelEd == nameAngel && jsonAngel.emailAngelEd == emailAngel){
                return true;
            }
        }else{
            return false
        }

    }
    return false;
}

function limpiarFiltro(desFiltro){
    var cont = parseInt(0);
    var idFiltro = '';

    for(var i=0;i<5;i++){
        cont = parseInt(i) + parseInt(1);
        idFiltro = "txtAngel" + cont + desFiltro;
        document.getElementById(idFiltro).value = '';
        document.getElementById(idFiltro).className = '';
    }
}

function ordenarFiltro(desFiltro,angeles){
    var cont = parseInt(0);
    var idFiltro = '';
    var arrayAngeles = angeles.split(";");

    limpiarFiltro(desFiltro);
    for(var i=0;i<arrayAngeles.length;i++){
        cont = parseInt(i) + parseInt(1);
        idFiltro = "txtAngel" + cont + desFiltro;
        document.getElementById(idFiltro).value = arrayAngeles[i];
    }
}

function deleteListAngel(desFiltro){
    var objFiltro = document.getElementById('hdLstAngels' + desFiltro);
    var idFiltro;
    var cont = parseInt(0);

    objFiltro.value = '';

    for(var i=0;i<5;i++){
        cont = parseInt(i) + parseInt(1);
        idFiltro = "txtAngel" + cont + desFiltro;
        if(document.getElementById(idFiltro).value != '' && document.getElementById(idFiltro).className == ''
            && document.getElementById(idFiltro).value != null){
            if(objFiltro.value == ''){
                objFiltro.value = document.getElementById(idFiltro).value + ";";
            }else{
                objFiltro.value += document.getElementById(idFiltro).value + ";";
            }
        }
    }

    ordenarFiltro(desFiltro,objFiltro.value);

    document.getElementById('hdLstAngels' + desFiltro).value = objFiltro.value;
}

function saveAngelEd(nameAngelEd,emailAngelEd){
    //addToJsonAngels(nameAddress,emailAddress,des)

    if(nameAngelEd != '' && emailAngelEd != ''){
        addToJsonAngels(nameAngelEd,emailAngelEd,'Ed');
        var menAviso = $("#hdTitleAngelConfirm").val();
        lanzarModal('../SNSAngelGuardFB/infoMessage.jsp?typeInfo=0&infoMessage=' + menAviso,750,350);
    }else if(nameAngelEd == '' && emailAngelEd == ''){
        delItemLstJsonAngels();
        buscarBorrados('Ed');
        borrarItems('Ed');
    }
}

function getIdFiltro(desFiltro){
    var idCheck;

    if(desFiltro == 'FltWall'){
        idCheck = 'chkVig0';
    }else if(desFiltro == 'FltFriends'){
        idCheck = 'chkVig1';
    }else if(desFiltro == 'FltPriv'){
        idCheck = 'chkVig2';
    }else if(desFiltro == 'FltVist'){
        idCheck = 'chkVig3';
    }
    
    return idCheck;
}

function loadCheckFiltro(desFiltro){
    var idCheck = getIdFiltro(desFiltro);

    if(document.getElementById('hdActive' + desFiltro).value == '1'){
        document.getElementById(idCheck).checked = true;
    }
    else{
        document.getElementById(idCheck).checked = false;
    }
}

function loadAngelSelects(angels){
    var arrayAngels = angels.split(';');

    for(var i=0;i<arrayAngels.length; i++){
        $.each($("#fcbklist").children("li").children(".fcbklist_item"),function(index,object){
            object=$(object);
            if(object.find("[type=hidden]").val() == arrayAngels[i]){
                $("#view_selected_count").text(parseInt($("#view_selected_count").text(),10)+1);
                object.parents("li").attr("addedid","tester");
                object.toggleClass("itemselected");
                object.parents("li").toggleClass("liselected");
            }
        });
    }
}

function loadEstadoFiltro(desFiltro){
    if(document.getElementById('hdActive' + desFiltro).value == '1'){
        if(document.getElementById('hdLstAngels' + desFiltro).value != "null"){
            document.getElementById('hdAngelsAux').value = document.getElementById('hdLstAngels' + desFiltro).value;
        }
        document.getElementById('slcFrecuency').value = document.getElementById('hdFrec' + desFiltro).value;
        loadAngelSelects(document.getElementById('hdAngelsAux').value);
    }
    else{
        deshabilitarAngelesFiltro(desFiltro);
    }
}

function inicializarHiddenSalidaFiltro(desFiltro){
    // Se borra la lista de Angeles seleccionados
    document.getElementById('hdLstAngels' + desFiltro).value = '';

    // Se inicializa el valor de la frecuencia de informes al valor por defecto
    document.getElementById('hdFrec' + desFiltro).value = '0';

    // Se desactiva el filtro
    document.getElementById('hdActive' + desFiltro).value = '0';
}

function cargarOpcionesFiltros(descripcion,desFiltro){
    document.getElementById('vigilantDescription').innerHTML = '<h1 class="vigilantDescription">' + descripcion + '</h1>';
    loadEstadoFiltro(desFiltro);
}

function habilitarEdicion(idCheck,desFiltro){
    if(document.getElementById(idCheck).checked){
        habilitarAngelesFiltro(desFiltro);
    }else{
        //inicializarHiddenSalidaFiltro(desFiltro);
        deshabilitarAngelesFiltro(desFiltro);
    }
}

function habilitarAngelesFiltro(desFiltro){
    //var listAngels = document.getElementById('hdAngels').value;
    var strSubAng = document.getElementById('hdTitleFbList').value;
    var titleAngelSettAng = document.getElementById('hdTitleAngelSettAng').value;

    document.getElementById('slcFrecuency').disabled = "";
    document.getElementById('vigilantAngels').innerHTML = "<ul id=\"fcbklist\"></ul>";
    cargarListaAngels(arrayAngelsVig,titleAngelSettAng);
    cargarListaVig(strSubAng);
}

function deshabilitarAngelesFiltro(desFiltro){
    $('#hdActive' + desFiltro).val('0');
    document.getElementById('slcFrecuency').disabled = "disabled";
    if(!document.getElementById('vigilantAngelsDisabled')){
        $(function(){
            $(document.createElement('div'))
            .attr('id','vigilantAngelsDisabled')
            .attr('className','vigilantAngelsDisabled')
            .width($('#vigilantAngels').width())
            .height($('#vigilantAngels').height())
            .prependTo($('#vigilantAngels'));
            $('#vigilantAngels').css('position','relative');
        });
    }
    
    document.getElementById('hdLstAngels' + desFiltro).value = '';
    document.getElementById('hdAngelsAux').value = '';
    loadAngelSelects(document.getElementById('hdAngelsAux').value);
}

function disableAllChecks(){
    for(var i=0;i<4;i++){
        deshabilitarCheck("chkVig" + i);
    }
}

function habilitarCheck(idCheck,desFiltro){
    disableAllChecks();

    document.getElementById(idCheck).disabled = "";
    document.getElementById(idCheck).onclick = function() {
        checkAllFilters();
        habilitarEdicion(idCheck, desFiltro);
        activarFiltro(idCheck);
        habilitarGuardar();

        if (document.getElementById(idCheck).checked) {
            activarFiltro(idCheck);
            habilitarAngelesFiltro(desFiltro);
            loadEstadoFiltro(desFiltro);
            //desactivarFiltro(idCheck);
        } else {
            deshabilitarAngelesFiltro(desFiltro);
            setCheckActual(idCheck);
        }
    }

    if (document.getElementById(idCheck).checked) {
        activarFiltro(idCheck);
        habilitarAngelesFiltro(desFiltro);
        loadEstadoFiltro(desFiltro);
        //desactivarFiltro(idCheck);
    } else {
        deshabilitarAngelesFiltro(desFiltro);
        setCheckActual(idCheck);
    }
}

function setCheckActual(idCheck){
    if(idCheck == 'chkVig0'){
        document.getElementById('hdFiltroActual').value = '0';
    }else if(idCheck == 'chkVig1'){
        document.getElementById('hdFiltroActual').value = '1';
    }else if(idCheck == 'chkVig2'){
        document.getElementById('hdFiltroActual').value = '2';
    }else if(idCheck == 'chkVig3'){
        document.getElementById('hdFiltroActual').value = '3';
    }
}

function activarFiltro(idCheck){
    if(idCheck == 'chkVig0'){
        document.getElementById('hdActiveFltWall').value = '1';
        document.getElementById('hdFiltroActual').value = '0';
    }else if(idCheck == 'chkVig1'){
        document.getElementById('hdActiveFltFriends').value = '1';
        document.getElementById('hdFiltroActual').value = '1';
    }else if(idCheck == 'chkVig2'){
        document.getElementById('hdActiveFltPriv').value = '1';
        document.getElementById('hdFiltroActual').value = '2';
    }else if(idCheck == 'chkVig3'){
        document.getElementById('hdActiveFltVist').value = '1';
        document.getElementById('hdFiltroActual').value = '3';
    }
}

function desactivarFltr(desFiltro){

    document.getElementById('hdActive' + desFiltro).value = '0';
    document.getElementById('hdFrec' + desFiltro).value = '3';
}

function desactivarFiltro(idCheck){
    if(idCheck == 'chkVig0'){
        desactivar('FltWall');
    }else if(idCheck == 'chkVig1'){
        desactivar('FltFriends');
    }else if(idCheck == 'chkVig2'){
        desactivar('FltPriv');
    }else if(idCheck == 'chkVig3'){
        desactivar('FltVist');
    }
}

function deshabilitarCheck(idCheck){
    document.getElementById(idCheck).disabled = "disabled";
    document.getElementById(idCheck).onclick = "";

    document.getElementById('slcFrecuency').disabled = "disabled";
}

function seleccionVig(idVigilante){
    //saveSelectionAngels();

    limpiarListaVigilantes();

    document.getElementById(idVigilante).className = 'pijama2';
}

function limpiarListaVigilantes(){
    for(var i = 0;i < 4; i++){
        var elemento = document.getElementById('vigilant'+i);
        if(elemento){
            document.getElementById('vigilant'+i).className = 'pijama1';
        }
    }
}

function checkAllFilters(){
    for(var i = 0;i < 4; i++){
        if(!document.getElementById('chkVig'+i).checked){
            document.getElementById('chkAllFilters').checked = false;
            break;
        }else if(i == 3){
            document.getElementById('chkAllFilters').checked = true;
        }
    }
}

function setError(idFiltro){
    idFiltro.attr("class","inputTextError");
    deshabilitarBtnGuardar();
}

function unsetError(idFiltro){
    idFiltro.attr("class", "");
    habilitarBtnGuardar();
}

function setErrorFilter(idFiltro){
    document.getElementById(idFiltro).className = 'inputTextError';
    habilitarGuardar();
}

function unsetErrorFilter(idFiltro){
    document.getElementById(idFiltro).className = '';
    habilitarGuardar();
}

function formatLstAngelNames(){
    var auxArrayAngels = document.getElementById('hdAvailableTags').value;

    if(auxArrayAngels.indexOf(',') == -1){
        auxArrayAngels = auxArrayAngels + ',';
    }
    return auxArrayAngels;
}

function selectAllFilters(){
    for(var i = 0;i < 4; i++){
        var elemento = document.getElementById('chkVig'+i);
        if(elemento){
            if(document.getElementById('chkAllFilters').checked){
                document.getElementById('chkVig'+i).checked = true;
            }
            else{
                document.getElementById('chkVig'+i).checked = false;
            }
        }
    }
}

function getDatesAngelFacebook(idAngel){
    var paramAngel = '';
    
    paramAngel = 'idAngel=' + encodeURIComponent(idAngel) 
               + '&typeAngel=' + 'F';
    
    return paramAngel;
}

function reloadAngels(){
    var paramSal = '';

    paramSal = 'hdAngels=' + encodeURIComponent(document.getElementById('hdAngels').value)
    + '&hdAngelsEd=' + encodeURIComponent(document.getElementById('hdAngelsEd').value)
    + '&hdAngelsGoogleSelected=' + encodeURIComponent(document.getElementById('hdAngelsGoogleSelected').value)
    + '&hdLstAngelsFltWall=' + encodeURIComponent(document.getElementById('hdLstAngelsFltWall').value)
    + '&hdLstAngelsFltFriends=' + encodeURIComponent(document.getElementById('hdLstAngelsFltFriends').value)
    + '&hdLstAngelsFltPriv=' + encodeURIComponent(document.getElementById('hdLstAngelsFltPriv').value)
    + '&hdLstAngelsFltVist=' + encodeURIComponent(document.getElementById('hdLstAngelsFltVist').value)
    + '&hdActiveFltWall=' + document.getElementById('hdActiveFltWall').value
    + '&hdActiveFltFriends=' + document.getElementById('hdActiveFltFriends').value
    + '&hdActiveFltPriv=' + document.getElementById('hdActiveFltPriv').value
    + '&hdActiveFltVist=' + document.getElementById('hdActiveFltVist').value
    + '&hdFrecFltWall=' + encodeURIComponent(document.getElementById('hdFrecFltWall').value)
    + '&hdFrecFltFriends=' + encodeURIComponent(document.getElementById('hdFrecFltFriends').value)
    + '&hdFrecFltPriv=' + encodeURIComponent(document.getElementById('hdFrecFltPriv').value)
    + '&hdFrecFltVist=' + encodeURIComponent(document.getElementById('hdFrecFltVist').value)
    + '&hdAngelsAux=' + encodeURIComponent(document.getElementById('hdAngelsAux').value);

    return paramSal;
}

function loadAngels(){
    var paramSal;
    paramSal = 'hdAngels=' + encodeURIComponent(document.getElementById('hdAngels').value)
    + '&hdAngelsEd=' + encodeURIComponent(document.getElementById('hdAngelsEd').value)
    + '&hdAngelsGoogleSelected=' + encodeURIComponent(document.getElementById('hdAngelsGoogleSelected').value)
    + '&hdLstAngelsFltWall=' + encodeURIComponent(document.getElementById('hdLstAngelsFltWall').value)
    + '&hdLstAngelsFltFriends=' + encodeURIComponent(document.getElementById('hdLstAngelsFltFriends').value)
    + '&hdLstAngelsFltPriv=' + encodeURIComponent(document.getElementById('hdLstAngelsFltPriv').value)
    + '&hdLstAngelsFltVist=' + encodeURIComponent(document.getElementById('hdLstAngelsFltVist').value)
    + '&hdActiveFltWall=' + document.getElementById('hdActiveFltWall').value
    + '&hdActiveFltFriends=' + document.getElementById('hdActiveFltFriends').value
    + '&hdActiveFltPriv=' + document.getElementById('hdActiveFltPriv').value
    + '&hdActiveFltVist=' + document.getElementById('hdActiveFltVist').value
    + '&hdFrecFltWall=' + encodeURIComponent(document.getElementById('hdFrecFltWall').value)
    + '&hdFrecFltFriends=' + encodeURIComponent(document.getElementById('hdFrecFltFriends').value)
    + '&hdFrecFltPriv=' + encodeURIComponent(document.getElementById('hdFrecFltPriv').value)
    + '&hdFrecFltVist=' + encodeURIComponent(document.getElementById('hdFrecFltVist').value)
    + '&hdAngelsAux=' + encodeURIComponent(document.getElementById('hdAngelsAux').value)
    return paramSal;
}

function obtenerFrecuencia(idSelect){
    var objectFrec = document.getElementById(idSelect);

    if(document.getElementById('vigilant0').className == 'pijama2'){
        document.getElementById('hdFrecFltWall').value = objectFrec.value;
    } else if(document.getElementById('vigilant1').className == 'pijama2'){
        document.getElementById('hdFrecFltFriends').value = objectFrec.value;
    } else if(document.getElementById('vigilant2').className == 'pijama2'){
        document.getElementById('hdFrecFltPriv').value = objectFrec.value;
    } else if(document.getElementById('vigilant3').className == 'pijama2'){
        document.getElementById('hdFrecFltVist').value = objectFrec.value;
    }
}

function lanzarPopup(url, ancho, alto) {
    muestraLoaderSin();
    if(navigator.userAgent.toLowerCase().indexOf('chrome') != -1){
      alto = alto + 40;
    }

    var value = 'no';
    var settings;

    settings = "";
    settings += "toolbar=" + value + ",";
    settings += "scrollbars=" + value + ",";
    settings += "location=" + value +  ",";
    settings += "status=" + value + ",";
    settings += "menubar=" + value + ",";
    settings += "resizeable=" + value + ",";
    settings += "width=" + ancho + ",";
    settings += "height=" + alto;
    settings += "modal=" + "yes";
    var top = (screen.height - alto)/2;
    var center = (screen.width - ancho)/2;

    miVentana = window.open(url,"popup",settings);

    miVentana.moveTo(center,top);
    miVentana.focus();

    return miVentana;
}


function obtenerUsuario(idUsuario){
    lanzarPopup('../SNSAngelGuardFB/angelUser.jsp?' + idUsuario, 450, 400);
}

function cerrarVentana(){
    window.close();
}

function habilitarBtnGuardar(){
    $(function(){
        $("#btnSave").attr('class','boton');
    });
}

function deshabilitarBtnGuardar(){
    $(function(){
        $("#btnSave").attr('class','botonDisabled');
    });
}

function formatTxt(txtNameAngel){
    var acentos = "??;??;??;??;??;??;??;??;??;??;";
    var sinAcentos = "a;e;i;o;u;A;E;I;O;U;";
    var aMinus = txtNameAngel.toLowerCase();
    var arrayAcentos = acentos.split(";");
    var arraySinAcentos = sinAcentos.split(";");

    for(var i=0;i<arrayAcentos.length;i++){
        aMinus = aMinus.replace(arrayAcentos[i],arraySinAcentos[i]);
    }
    return aMinus;
}

function isNameAngelValidOnKeyUp(idFiltro){
    var contenido = false;
    var nameAngel = document.getElementById(idFiltro).value;

    var arrayAngels = formatLstAngelNames().split(",");

    for(var i=0;i<arrayAngels.length;i++){
        if(formatTxt(arrayAngels[i]).lastIndexOf(formatTxt(nameAngel)) != -1){
            document.getElementById(idFiltro).className = '';
            contenido = true;
            break;
        }else{
            document.getElementById(idFiltro).className = 'inputTextError';
            deshabilitarBtnGuardar();
            contenido = false;
        }

    }
    return contenido;
}

function isNameAngelValid(idFiltro){
    var contenido = false;
    var nameAngel = document.getElementById(idFiltro).value;

    var arrayAngels = formatLstAngelNames().split(",");

    if(document.getElementById(idFiltro).className == ''){
        if(nameAngel != ''){
            for(var i=0;i<arrayAngels.length;i++){
                if((arrayAngels[i] == nameAngel) || (formatTxt(arrayAngels[i]) == formatTxt(nameAngel))){
                    document.getElementById(idFiltro).value = arrayAngels[i];
                    unsetError(idFiltro);
                    contenido = true;
                    break;
                }else{
                    setError(idFiltro);
                    contenido = false;
                }
            }
        } else{
            unsetError(idFiltro);
            contenido = false;
        }
    }
    return contenido;
}

function replaceNameIsDupEmail(emailTutor,newNameTutor){
    var lstAngelsGoogle = document.getElementById('hdAngelsEd').value;

    var arrayAngelsEd = '';
    var componentJson = '';
    var arrayEmail = '';
    var arrayName = '';
    var emailAngel = '';
    var nameAngel = '';
    var longitud = parseInt(0);
    var reemplazado = false;

    if(lstAngelsGoogle != ''){
        arrayAngelsEd = lstAngelsGoogle.split(";");
        longitud = parseInt(arrayAngelsEd.length) - parseInt(1);
        for(var i=0;i<longitud;i++){  
            if(arrayAngelsEd[i] != ''){
                var jsonAngel = $.parseJSON(arrayAngelsEd[i]);
                if(jsonAngel.emailAngelEd == emailTutor){
                    delToJsonAngels(jsonAngel.nameAngelEd,'Ed');
                    addToJsonAngels(newNameTutor,jsonAngel.emailAngelEd,"Ed");
                    reemplazado = true;
                }
            }
        }
    }

    return reemplazado;
}


function isDupEmail(emailTutor){
    var lstAngelsGoogle = document.getElementById('hdAngelsEd').value;
    var arrayAngelsGoogle = '';
    var componentJson = '';
    var arrayEmail = '';
    var emailAngel = '';
    var repetido = false;
    var longitud = parseInt(0);

    if(lstAngelsGoogle != ''){
        arrayAngelsGoogle = lstAngelsGoogle.split(";");
        longitud = parseInt(arrayAngelsGoogle.length) - parseInt(1);
        for(var i=0;i<longitud;i++){
            if(arrayAngelsGoogle[i] != ''){
                componentJson = arrayAngelsGoogle[i].split(',');
                arrayEmail = componentJson[1].split(":");
                emailAngel = arrayEmail[1];
                if(formatName(emailAngel).indexOf(emailTutor) != -1){
                    repetido = true;
                    break;
                }
            }
        }
    }
    return repetido;
}

function isFilterActive(idFilter, idAngelsSelected){;
    if ($(idFilter).val() == '1' && (($(idAngelsSelected).val() != '') && ($(idAngelsSelected).val() != 'null') && ($(idAngelsSelected).val() != 'undefined'))) {
        return true;
    } else {
        return false;
    }
}

function isAnyFiltroActivo() {

    var result = false;

    if (isFilterActive('#hdActiveFltWall', '#hdLstAngelsFltWall')) {
        result = true;
    } else if (isFilterActive('#hdActiveFltFriends', '#hdLstAngelsFltFriends')) {
        result = true;
    } else if (isFilterActive('#hdActiveFltPriv', '#hdLstAngelsFltPriv')) {
        result = true;
    } else if (isFilterActive('#hdActiveFltVist', '#hdLstAngelsFltVist')) {
        result = true;
    }

    return result;

}

function isActiveFilter(idFiltro){
    if(document.getElementById(idFiltro).value == '0'){
        return false;
    }else{
        return true;
    }
}

function isAnyAngelSelected(){
    if(document.getElementById('hdAngels').value != '' ||
        document.getElementById('hdAngelsGoogleSelected').value != '' ||
        document.getElementById('hdAngelsEd').value != ''){
        if(document.getElementById('hdLstAngelsFltWall').value == '' &&
            document.getElementById('hdLstAngelsFltFriends').value == '' &&
            document.getElementById('hdLstAngelsFltPriv').value == '' &&
            document.getElementById('hdLstAngelsFltVist').value == '' &&
            document.getElementById('hdAngelsAux').value == ''){
            return false;
        }else{
            return true;
        }
    }else{
        return false;
    }
}

function getDesFiltro(){
    var desFiltro;
    var numFiltro = parseInt(100);

    for(var i=0;i<4;i++){
        numFiltro = parseInt(i);
        if(document.getElementById('vigilant' + i).className == 'pijama2'){
            break;
        }
    }

    switch (numFiltro) {
        case 0:
            desFiltro = 'FltWall';
            break;
        case 1:
            desFiltro = 'FltFriends';
            break;
        case 2:
            desFiltro = 'FltPriv';
            break;
        case 3:
            desFiltro = 'FltVist';
            break;
        default:
            desFiltro = '';
    }

    return desFiltro;
}

function getFiltro(id){
    var desFiltro = '';

    switch (id) {
        case 0:
            desFiltro = 'FltWall';
            break;
        case 1:
            desFiltro = 'FltFriends';
            break;
        case 2:
            desFiltro = 'FltPriv';
            break;
        case 3:
            desFiltro = 'FltVist';
            break;
        default:
            desFiltro = '';
    }

    return desFiltro;
}

function isAnyErrorId(idActual){
    var isError = false;

    if(document.getElementById(idActual).className == 'inputTextError'){
        isError = true;
    }

    return isError;
}

function isAnyError(){
    var error = false;
    
    if(document.getElementById('tabFriendsImport')){
        var tabla = document.getElementById('tabFriendsImport');
        for(var i=0;i<tabla.rows.length;i++){
            if(document.getElementById('txtNameTutorEd' + i) && document.getElementById('txtEmailTutorEd' + i)){
                if(isAnyErrorId('txtNameTutorEd' + i) || isAnyErrorId('txtEmailTutorEd' + i)){
                    error = true;
                    break;
                }
            }
        }
    }

    return error;
}

function habilitarGuardar(){
    var valido = true;
    if(isAnyFiltroActivo()){
        habilitarBtnGuardar();
    }else{
        deshabilitarBtnGuardar();
        valido = false;
    }

    return valido;
}

function saveNewAngelSelected(menSave, menWait, idAngel){
    muestraLoader(menSave,menWait);
    var data = '../SNSAngelGuardFB/saveNewAngel.jsp' + '?' + getDatesAngelFacebook(idAngel);
    
    document.getElementById('frSNSAngels').setAttribute('action', data);
    document.getElementById('frSNSAngels').setAttribute('method','post');
    document.getElementById('frSNSAngels').submit();
}

function deleteAngelSelected(menSave, menWait, idAngel){
    muestraLoader(menSave,menWait);
    var data = '../SNSAngelGuardFB/deleteAngelSelected.jsp' + '?' + getDatesAngelFacebook(idAngel);
    
    document.getElementById('frSNSAngels').setAttribute('action', data);
    document.getElementById('frSNSAngels').setAttribute('method','post');
    document.getElementById('frSNSAngels').submit();
}

function deleteAngelSelectedByErrorPostingWall(menSave, menWait, idAngel){
    muestraLoader(menSave,menWait);
    var data = '../SNSAngelGuardFB/deleteAngelSelected.jsp' + '?' + "deleteByErrorPosting=1" + "&" + getDatesAngelFacebook(idAngel);
    
    document.getElementById('frSNSAngels').setAttribute('action', data);
    document.getElementById('frSNSAngels').setAttribute('method','post');
    document.getElementById('frSNSAngels').submit();
}

function saveSettings(menSave,menWait){
    muestraLoader(menSave,menWait);
    
    if(document.getElementById('frSNSVigilants')){
        saveSelectionAngels();
    }

    document.getElementById('h1').value = encodeURIComponent(document.getElementById('hdAngels').value);
    document.getElementById('h2').value = encodeURIComponent(document.getElementById('hdAngelsGoogleSelected').value);
    document.getElementById('h3').value = encodeURIComponent(document.getElementById('hdAngelsEd').value);
    document.getElementById('h4').value = encodeURIComponent(document.getElementById('hdLstAngelsFltWall').value);
    document.getElementById('h5').value = encodeURIComponent(document.getElementById('hdLstAngelsFltFriends').value);
    //document.getElementById('h6').value = encodeURIComponent(document.getElementById('hdLstAngelsFltPriv').value);
    document.getElementById('h7').value = encodeURIComponent(document.getElementById('hdLstAngelsFltVist').value);
    document.getElementById('h8').value = encodeURIComponent(document.getElementById('hdActiveFltWall').value);
    document.getElementById('h9').value = encodeURIComponent(document.getElementById('hdActiveFltFriends').value);
    document.getElementById('h10').value = encodeURIComponent(document.getElementById('hdActiveFltPriv').value);
    document.getElementById('h11').value = encodeURIComponent(document.getElementById('hdActiveFltVist').value);
    document.getElementById('h12').value = encodeURIComponent(document.getElementById('hdFrecFltWall').value);
    document.getElementById('h13').value = encodeURIComponent(document.getElementById('hdFrecFltFriends').value);
    document.getElementById('h14').value = encodeURIComponent(document.getElementById('hdFrecFltPriv').value);
    document.getElementById('h15').value = encodeURIComponent(document.getElementById('hdFrecFltVist').value);

    if(document.getElementById('frSNSVigilants')){
        document.getElementById('frSNSVigilants').setAttribute('action','checkNow.jsp');
        document.getElementById('frSNSVigilants').setAttribute('method','post');
        document.getElementById('frSNSVigilants').submit();
    }else{
        document.getElementById('frSNSAngels').setAttribute('action','checkNow.jsp');
        document.getElementById('frSNSAngels').setAttribute('method','post');
        document.getElementById('frSNSAngels').submit();
    }
}

function loadInicioDatesAngels(angels,hdAngelsEd,hdAngelsGoogleSelected,
    hdLstAngelsFltWall,hdLstAngelsFltFriends,hdLstAngelsFltPriv,hdLstAngelsFltVist,
    hdActiveFltWall,hdActiveFltFriends,hdActiveFltPriv,hdActiveFltVist,
    hdFrecFltWall,hdFrecFltFriends,hdFrecFltPriv,hdFrecFltVist,hdAngelsAux, arrayAltAngels, nameContact, emailContact, hdConfirm, hdDelete){
  
    document.getElementById('hdAngels').value = decodeURIComponent(angels);
    document.getElementById('hdAngelsEd').value = decodeURIComponent(hdAngelsEd);
    document.getElementById('hdAngelsGoogleSelected').value = decodeURIComponent(hdAngelsGoogleSelected);
    document.getElementById('hdLstAngelsFltWall').value = decodeURIComponent(hdLstAngelsFltWall);
    document.getElementById('hdLstAngelsFltFriends').value = decodeURIComponent(hdLstAngelsFltFriends);
    document.getElementById('hdLstAngelsFltPriv').value = decodeURIComponent(hdLstAngelsFltPriv);
    document.getElementById('hdLstAngelsFltVist').value = decodeURIComponent(hdLstAngelsFltVist);
    document.getElementById('hdActiveFltWall').value = decodeURIComponent(hdActiveFltWall);
    document.getElementById('hdActiveFltFriends').value = decodeURIComponent(hdActiveFltFriends);
    document.getElementById('hdActiveFltPriv').value = decodeURIComponent(hdActiveFltPriv);
    document.getElementById('hdActiveFltVist').value = decodeURIComponent(hdActiveFltVist);
    document.getElementById('hdFrecFltWall').value = decodeURIComponent(hdFrecFltWall);
    document.getElementById('hdFrecFltFriends').value = decodeURIComponent(hdFrecFltFriends);
    document.getElementById('hdFrecFltPriv').value = decodeURIComponent(hdFrecFltPriv);
    document.getElementById('hdFrecFltVist').value = decodeURIComponent(hdFrecFltVist);
    document.getElementById('hdAngelsAux').value = decodeURIComponent(hdAngelsAux);

    var arrayAlt = arrayAltAngels.split(";");
    document.getElementById('hdAnadirContacto').value = arrayAlt[0];
    document.getElementById('hdEditarContacto').value = arrayAlt[1];
    document.getElementById('hdCancelarContacto').value = arrayAlt[2];
    document.getElementById('hdEliminarContacto').value = arrayAlt[3];
    document.getElementById('hdGuardarContacto').value = arrayAlt[4];
    document.getElementById('hdNameContact').value = nameContact;
    document.getElementById('hdEmailContact').value = emailContact;
    document.getElementById('hdTitleAngelConfirm').value = hdConfirm;
    document.getElementById('hdTitleAngelDelete').value = hdDelete;
}

function loadInicioDatesVigilants(angels,hdAngelsEd,hdAngelsGoogleSelected,
    hdLstAngelsFltWall,hdLstAngelsFltFriends,hdLstAngelsFltPriv,hdLstAngelsFltVist,
    hdActiveFltWall,hdActiveFltFriends,hdActiveFltPriv,hdActiveFltVist,
    hdFrecFltWall,hdFrecFltFriends,hdFrecFltPriv,hdFrecFltVist,hdAngelsAux){

    document.getElementById('hdAngels').value = decodeURIComponent(angels);
    document.getElementById('hdAngelsEd').value = decodeURIComponent(hdAngelsEd);
    document.getElementById('hdAngelsGoogleSelected').value = decodeURIComponent(hdAngelsGoogleSelected);
    document.getElementById('hdLstAngelsFltWall').value = decodeURIComponent(hdLstAngelsFltWall);
    document.getElementById('hdLstAngelsFltFriends').value = decodeURIComponent(hdLstAngelsFltFriends);
    document.getElementById('hdLstAngelsFltPriv').value = decodeURIComponent(hdLstAngelsFltPriv);
    document.getElementById('hdLstAngelsFltVist').value = decodeURIComponent(hdLstAngelsFltVist);
    document.getElementById('hdActiveFltWall').value = decodeURIComponent(hdActiveFltWall);
    document.getElementById('hdActiveFltFriends').value = decodeURIComponent(hdActiveFltFriends);
    document.getElementById('hdActiveFltPriv').value = decodeURIComponent(hdActiveFltPriv);
    document.getElementById('hdActiveFltVist').value = decodeURIComponent(hdActiveFltVist);
    document.getElementById('hdFrecFltWall').value = decodeURIComponent(hdFrecFltWall);
    document.getElementById('hdFrecFltFriends').value = decodeURIComponent(hdFrecFltFriends);
    document.getElementById('hdFrecFltPriv').value = decodeURIComponent(hdFrecFltPriv);
    document.getElementById('hdFrecFltVist').value = decodeURIComponent(hdFrecFltVist);
    document.getElementById('hdAngelsAux').value = decodeURIComponent(hdAngelsAux);
}

function isEmailValid(emailTutor){
    if(emailTutor != ''){
        if(validarEmailTest(emailTutor)){
            return true;
        }
    }
    return false;
}

function isContactValid(numRow){
    var email = $('#txtEmailTutorEd' + numRow).val();

    if(isEmailValid(email)){
        $('#txtEmailTutorEd' + numRow).attr('class','');
        return true;
    }else{
        $('#txtEmailTutorEd' + numRow).attr('class','inputTextError');
        return false;
    }
}

function isDatesValid(menSave, menWait, idName, idEmail, numRow, idContact){
    $(function(){
        if($(idName).val() != '' && $(idEmail).val() != '' && isContactValid(numRow)){
            $('#guardar' + numRow).attr("class", "cabOtrosContactos");
            $('#guardar' + numRow).attr("title", $('#hdGuardarContacto').val());
            $('#guardar' + numRow).attr("src", "../SNSAngelGuardFB/resources/saveContact.gif");

            if($('guardar' + numRow).attr('onclick')){
                $('#guardar' + numRow).removeAttr('onclick').click(function(){
                    saveDatesTutor(menSave, menWait, numRow, idContact);
                });
            }else{
                $('#guardar' + numRow).unbind('click');
                $('#guardar' + numRow).click(function(){
                    saveDatesTutor(menSave, menWait, numRow, idContact);
                });
            }
        }else{
            $('#guardar' + numRow).attr("class", "cabOtrosContactosDisabled");
            $('#guardar' + numRow).attr("title", "");
            $('#guardar' + numRow).attr("src", "../SNSAngelGuardFB/resources/desSaveContact.gif");

            if($('guardar' + numRow).attr('onclick')){
                $('#guardar' + numRow).removeAttr('onclick').click(function(){});
            }else{
                $('#guardar' + numRow).unbind('click');
                $('#guardar' + numRow).click(function(){});
            }
        }
    });
}

function saveDatesTutor(menSave, menWait, numRow, idContact){
    var email = escape($('#txtEmailTutorEd' + numRow).val());
    var name = escape($('#txtNameTutorEd' + numRow).val());
    var typeOperation = $('#typeOperationOtherContacts').val();
    
    if(idContact){
        // Remplazamos el nuevo nombre del angel por el antiguo
        launchDoOperationWithOtherContact(menSave, menWait, typeOperation,name, email, idContact);
    }else{
        // Guardamos los datos del contacto
        launchDoOperationWithOtherContact(menSave, menWait, typeOperation,name, email);
    }
}

function quitarAcentos(id){
    var nameTutor = '#txtNameTutorEd' + id;
    var idJquery = $(nameTutor).val();
    var valueName = remove_accent(idJquery);
    $(nameTutor).attr("value",valueName);
}

function isDatesTutorValid(menSave, menWait, numAngel, idContact){
    
    var nameTutor = '#txtNameTutorEd' + numAngel;
    var emailTutor = '#txtEmailTutorEd' + numAngel;
    
    isDatesValid(menSave, menWait, nameTutor,emailTutor,numAngel, idContact);

}

function muestraLoader(menCarga, menWait){   
    $(function() {
            $.loader({
                content: "<div><table width=\"100%\"><tr><td width=\"100%\">"
                        + "<h1 class=\"tituloMed\">" + menCarga + "</h1>"
                        + "</td></tr>"
                        + "<tr><td>"
                        + "<table width=\"100%\"><tr><td width=\"80%\">" + menWait + "</td>"
                        + "</td></tr></table></td></tr></table></div>"
            });
    });
}

function cerrarLoader(){
    $(function(){
        $.loader('close');
    });
}

function cerrarLoaderMod(){
    $(function(){
        if($("#jquery-loader")){
            $.loader('close');
        }
    });
}

function muestraLoaderSin(){
    $(function(){
        $.loader({
            className:"blue-with-image-2",
            content:''
        });
    });
}

function setLoader(){
    $(function(){
        $("#jquery-loader-background", window.opener.document).remove();
        $("#jquery-loader", window.opener.document).remove();
    });
}

function loadTitleFcbList(titleFbList,titleAngelSettAng){
    document.getElementById('hdTitleFbList').value = titleFbList;
    document.getElementById('hdTitleAngelSettAng').value = titleAngelSettAng;
}

function setArrayAngels(arrayAngels){
    arrayAngelsVig = arrayAngels;
}

var nameTitle;
var emailTitle;

function loadTitles(titleName, titleEmail){
    nameTitle = titleName;
    emailTitle = titleEmail;
}

function launchDoOperationWithGmailContact(typeOperation, hdAngelsGoogleSelected, modal){
    $(function() {
        var data = 'doOperationWithGoogleContacts.jsp' + '?' + "typeOperation=" + typeOperation + "&hdAngelsGoogleSelected=" + hdAngelsGoogleSelected;
        var formParent = $('#frSNSAngels');
        
        if(modal == "true"){
            var formParent = window.parent.document.getElementById('frSNSAngels');
            
            formParent.setAttribute('action', data);
            formParent.setAttribute('method','post');
            formParent.submit();
        } else{
           var mainForm = $('#frSNSAngels');
            
            mainForm.attr('action', data);
            mainForm.attr('method','post');
            mainForm.submit(); 
        }
        
    });
}

function launchDoOperationWithOtherContact(menSave, menWait, typeOperation, nameContact, emailContact, idContact){
    // Mostramos el loader de la operacion
    muestraLoader(menSave, menWait);
    
    var data = 'doOperationWithOtherContact.jsp' + '?' + "typeOperation=" + typeOperation + "&nameContact=" + nameContact + "&emailContact=" + emailContact + "&idContact=" + idContact;
    
    document.getElementById('frSNSAngels').setAttribute('action', data);
    document.getElementById('frSNSAngels').setAttribute('method','post');
    document.getElementById('frSNSAngels').submit();
}

function pintarNuevoContacto(menSave, menWait, numNewRow, idContact){
    
    var tabla = document.getElementById('tabFriendsImport');
    var siguiente = parseInt(numNewRow) + parseInt(1);

    if(tabla.rows[numNewRow]){
        tabla.deleteRow(numNewRow);
    }
    
    tabla.insertRow(numNewRow);
    tabla.rows[numNewRow].className = "imgContent";
    tabla.rows[numNewRow].innerHTML = "<td width=\"32px\"><h1 class=\"tituloMedCabOtrosContactos\">" + siguiente + "</h1>"
    + "</td><td width=\"247px\" align=\"right\"><label for=\"txtNameTutorEd" + numNewRow + "\">" + $('#hdNameContact').val() + " </label>"
    + "<input type=\"text\" disabled=\"disabled\" name=\"txtNameTutorEd" + numNewRow + "\" id=\"txtNameTutorEd" + numNewRow + "\" size=\"25\" maxlength=\"70\""
    + "onchange=\"isDatesTutorValid('" + menSave + "', '" + menWait + "'," + numNewRow + ", " + idContact + ");\" onKeyUp=\"isDatesTutorValid('" + menSave + "', '" + menWait + "'," + numNewRow + ", " + idContact + ");\" onblur=\"isDatesTutorValid('" + menSave + "', '" + menWait + "'," + numNewRow + ", " + idContact + ");\" value=\"\" /></td>"
    + "<td width=\"248px\" align=\"right\"><label for=\"txtEmailTutorEd" + numNewRow + "\">" + $('#hdEmailContact').val() + " </label>"
    + "<input type=\"text\" disabled=\"disabled\" name=\"txtEmailTutorEd" + numNewRow + "\" id=\"txtEmailTutorEd" + numNewRow + "\" size=\"25\" maxlength=\"70\""
    + "onchange=\"isDatesTutorValid('" + menSave + "', '" + menWait + "'," + numNewRow + ", " + idContact + ");\" onKeyUp=\"isDatesTutorValid('" + menSave + "', '" + menWait + "'," + numNewRow + ", " + idContact + ");\" onblur=\"isDatesTutorValid('" + menSave + "', '" + menWait + "'," + numNewRow + ", " + idContact + ");\" value=\"\" /></td>"
    + "<td width=\"130px\">"
    + "<img title=\"" + $('#hdEditarContacto').val() + "\" id=\"editar" + numNewRow + "\" onclick=\"habilitarDatosContacto(" + numNewRow + ", " + idContact + ")\" src=\"../SNSAngelGuardFB/resources/editContact.gif\" class=\"cabOtrosContactos\" />"
    + "<img title=\"\" id=\"cancelar" + numNewRow + "\" onclick=\"\" src=\"../SNSAngelGuardFB/resources/desCancelarContact.gif\" class=\"cabOtrosContactos\" />"
    + "<img title=\"\" id=\"eliminar" + numNewRow + "\"  onclick=\"\" src=\"../SNSAngelGuardFB/resources/desDeleteContact.gif\" class=\"cabOtrosContactos\" />"
    + "<img title=\"\" id=\"guardar" + numNewRow + "\" onclick=\"\" src=\"../SNSAngelGuardFB/resources/desSaveContact.gif\" class=\"cabOtrosContactos\" /></td>";

    tabla.insertRow(siguiente);
    tabla.rows[siguiente].className = "imgContent";
    tabla.rows[siguiente].innerHTML =  "<td width=\"32px\" align=\"center\">"
    + "</td><td width=\"247px\" align=\"right\"></td><td width=\"248px\" align=\"right\">"
    + "</td><td width=\"130px\" align=\"right\" >"
    + "<img title=\"" + $('#hdAnadirContacto').val() + "\" class=\"newAngel\" src=\"../SNSAngelGuardFB/resources/anadir.gif\""
    + "onclick=\"pintarNuevoContacto('" + menSave + "', '" + menWait + "','" + siguiente + "');\"></img>"
    + "</td>";

    despInfScroll('friendsImportContainer');
}

function habilitarDatosContacto(numRow, idContact){
    $(function(){

        // Habilitamos los cuadros de texto del contacto
        $('#txtNameTutorEd' + numRow).attr("disabled", "");
        $('#txtEmailTutorEd' + numRow).attr("disabled", "");
        
        
        if(!idContact){
            $('#typeOperationOtherContacts').val("0");
        }else{
            $('#typeOperationOtherContacts').val("1");
        }

        // Deshabilitamos el bot?n de editar
        $('#editar' + numRow).attr("src", "../SNSAngelGuardFB/resources/desEditContact.gif");
        $('#editar' + numRow).attr("title", "")
        $('#editar' + numRow).attr("class","cabOtrosContactosDisabled");
        if($('editar' + numRow).attr('onclick')){
            $('#editar' + numRow).removeAttr('onclick').click(function(){});
        }else{
            $('#editar' + numRow).unbind('click');
            $('#editar' + numRow).click(function(){});
        }
        // Habilitamos el bot?n de cancelar
        $('#cancelar' + numRow).attr("src","../SNSAngelGuardFB/resources/cancelarContact.gif");
        $('#cancelar' + numRow).attr("title", $('#hdCancelarContacto').val())
        $('#cancelar' + numRow).attr("class","cabOtrosContactos");
        if($('#cancelar' + numRow).attr('onclick')){
            $('#cancelar' + numRow).removeAttr('onclick').click(function(){
                desHabilitarDatosContacto(numRow, idContact);
            });
        }else{
            $('#cancelar' + numRow).unbind('click');
            $('#cancelar' + numRow).click(function(){
                desHabilitarDatosContacto(numRow, idContact);
            });
        }
    });
}

function desHabilitarDatosContacto(numRow, idContact){
    $(function(){
        var angelsEd = $('#hdAngelsEd').val();
        
        if (angelsEd != '') {
            var jsonAngelEd = getJsonObject(idContact);
            var oldNameAngel = getJsonIdValue(jsonAngelEd, "name");
            var oldEmailAngel = getJsonIdValue(jsonAngelEd, "email");

            var borrar = existAngelEd(oldNameAngel, oldEmailAngel);
            if (!borrar) {
                // Limpiamos los cuadros de texto
                $('#txtNameTutorEd' + numRow).attr("value", "");
                $('#txtEmailTutorEd' + numRow).attr("value", "");
            } else {
                // Reemplazamos los valores anteriores a la modificacion
                $('#txtNameTutorEd' + numRow).attr("value", oldNameAngel);
                $('#txtEmailTutorEd' + numRow).attr("value", oldEmailAngel);
            }
        } else {
            // Limpiamos los cuadros de texto
            $('#txtNameTutorEd' + numRow).attr("value", "");
            $('#txtEmailTutorEd' + numRow).attr("value", "");
        }

        // Deshabilitamos los cuadros de texto
        $('#txtNameTutorEd' + numRow).attr("disabled","disabled");
        $('#txtEmailTutorEd' + numRow).attr("disabled","disabled");

        // Habilitamos el bot?n de editar
        $('#editar' + numRow).attr("src","../SNSAngelGuardFB/resources/editContact.gif");
        $('#editar' + numRow).attr("title", $('#hdEditarContacto').val())
        $('#editar' + numRow).attr("class","cabOtrosContactos");
        if($('editar' + numRow).attr('onclick')){
            $('#editar' + numRow).removeAttr('onclick').click(function(){
                habilitarDatosContacto(numRow, idContact);
            });
        }else{
            $('#editar' + numRow).unbind('click');
            $('#editar' + numRow).click(function(){
                habilitarDatosContacto(numRow, idContact);
            });
        }

        // Deshabilitamos el bot?n de cancelar
        $('#cancelar' + numRow).attr("src","../SNSAngelGuardFB/resources/desCancelarContact.gif");
        $('#cancelar' + numRow).attr("title", "")
        $('#cancelar' + numRow).attr("class","cabOtrosContactosDisabled");
        if($('#cancelar' + numRow).attr('onclick')){
            $('#cancelar' + numRow).removeAttr('onclick').click(function(){});
        }else{
            $('#cancelar' + numRow).unbind('click');
            $('#cancelar' + numRow).click(function(){});
        }
    });
}

function habilitarBotonBorrarContacto(menSave, menWait, numRow, idContact){
    $(function(){
        
        // Deshabilitamos los cuadros de texto
        $('#txtNameTutorEd' + numRow).attr("disabled","disabled");
        $('#txtEmailTutorEd' + numRow).attr("disabled","disabled");

        // Habilitamos el bot?n de editar
        $('#editar' + numRow).attr("src","../SNSAngelGuardFB/resources/editContact.gif");
        $('#editar' + numRow).attr("title", $('#hdEditarContacto').val())
        $('#editar' + numRow).attr("class","cabOtrosContactos");
        if($('editar' + numRow).attr('onclick')){
            $('#editar' + numRow).removeAttr('onclick').click(function(){
                habilitarDatosContacto(numRow, idContact);
            });
        }else{
            $('#editar' + numRow).unbind('click');
            $('#editar' + numRow).click(function(){
                habilitarDatosContacto(numRow, idContact);
            });
        }

        // Habilitamos el bot?n de borrar
        $('#eliminar' + numRow).attr("src","../SNSAngelGuardFB/resources/deleteContact.gif");       
        $('#eliminar' + numRow).attr("title", $('#hdEliminarContacto').val());
        $('#eliminar' + numRow).attr("class","cabOtrosContactos");
        
        if($('eliminar' + numRow).attr('onclick')){
            $('#eliminar' + numRow).removeAttr('onclick').click(function(){
                borrarContacto(menSave, menWait, numRow, idContact);
            });
        }else{
            $('#eliminar' + numRow).unbind('click');
            $('#eliminar' + numRow).click(function(){
                borrarContacto(menSave, menWait, numRow, idContact);
            });
        }

        // Deshabilitamos el bot?n de cancelar
        $('#cancelar' + numRow).attr("src","../SNSAngelGuardFB/resources/desCancelarContact.gif");
        $('#cancelar' + numRow).attr("title", "")
        $('#cancelar' + numRow).attr("class","cabOtrosContactosDisabled");
        if($('#cancelar' + numRow).attr('onclick')){
            $('#cancelar' + numRow).removeAttr('onclick').click(function(){});
        }else{
            $('#cancelar' + numRow).unbind('click');
            $('#cancelar' + numRow).click(function(){});
        }

        // Deshabilitamos el bot?n de guardar
        $('#guardar' + numRow).attr("src","../SNSAngelGuardFB/resources/desSaveContact.gif");
        $('#guardar' + numRow).attr("title", "");
        $('#guardar' + numRow).attr("class","cabOtrosContactosDisabled");
        if($('#guardar' + numRow).attr('onclick')){
            $('#guardar' + numRow).removeAttr('onclick').click(function(){});
        }else{
            $('#guardar' + numRow).unbind('click');
            $('#guardar' + numRow).click(function(){});
        }
    });
}

function borrarContacto(menSave, menWait, numRow, idContact){
    $(function(){
        
        // Preparamos la operacion de borrado 
        $('#typeOperationOtherContacts').val("2");
        
        var typeOperation = $('#typeOperationOtherContacts').val();
        
        // Guardamos el email del usuario borrado
        var emailBorrado = escape($('#txtEmailTutorEd' + numRow).attr("value"));
        var nameBorrado = escape($('#txtNameTutorEd' + numRow).attr("value"));
        
        // Borramos el angel de todos los filtros de la aplicacion
        deleteAngelOfAllFilters(emailBorrado);
        
        // Lanzamos la operacion de borrado en servidor
        launchDoOperationWithOtherContact(menSave, menWait, typeOperation,nameBorrado, emailBorrado, idContact);
    });
}

function despInfScroll(idObjeto){
    var scc = document.getElementById(idObjeto);
    scc.scrollTop = scc.scrollHeight + scc.offsetHeight;

}

function getDesFiltroActivo(){
    var des = '';  
    
    if(document.getElementById('vigilant0').className == 'pijama2'){
        des = 'FltWall';
    }else if(document.getElementById('vigilant1').className == 'pijama2'){
        des = 'FltFriends';
    }else if(document.getElementById('vigilant2').className == 'pijama2'){
        des = 'FltPriv';
    }else if(document.getElementById('vigilant3').className == 'pijama2'){
        des = 'FltVist';
    }
    
    return des;
}

function saveSelectionAngels(){
    var actualFilter = document.getElementById('hdFiltroActual').value;
    if(isAnyFiltroActivo()){
        if(actualFilter == '0'){
            if(isActiveFilter('hdActiveFltWall')){
                document.getElementById('hdFrecFltWall').value = document.getElementById('slcFrecuency').value;
            }
        }
        else if(actualFilter == '1'){
            if(isActiveFilter('hdActiveFltFriends')){
                document.getElementById('hdFrecFltFriends').value = document.getElementById('slcFrecuency').value;
            }
        }
        else if(actualFilter == '2'){
            if(isActiveFilter('hdActiveFltPriv')){
                document.getElementById('hdFrecFltPriv').value = document.getElementById('slcFrecuency').value;
            }
        }
        else if(actualFilter == '3'){
            if(isActiveFilter('hdActiveFltVist')){
                document.getElementById('hdFrecFltVist').value = document.getElementById('slcFrecuency').value;
            }
        }
    }

    document.getElementById('hdAngelsAux').value = '';
}

function borrarItems(desFiltro){
    var itemEdBorrado = (document.getElementById("hdAngels" + desFiltro +"Aux").value).split(";");
    for(var i=0;i<itemEdBorrado.length - 1; i++){
        borrarItemVigilants(itemEdBorrado[i]);
    }
}

function borrarItemSelected(idItem, idLista){
    var lstAux = '';
    var arrayLstAngels = '';
    
    if($(idLista).val() != ''){
        arrayLstAngels = ($(idLista).val()).split(";");
        
        for(var i=0;i<arrayLstAngels.length;i++){
            if(arrayLstAngels[i] != ''){
                if(arrayLstAngels[i] != idItem){
                    if(lstAux == ''){
                        lstAux = arrayLstAngels[i] + ";";
                    }else{
                        lstAux = lstAux + arrayLstAngels[i] + ";";
                    }
                }
            }
        }
    }else{
        lstAux = '';
    }
    
    $(idLista).attr("value",lstAux);

}

function deleteAngelOfAllFilters(idItem){
    borrarItemSelected(idItem, "#hdLstAngelsFltWall");
    borrarItemSelected(idItem, "#hdLstAngelsFltFriends");
    borrarItemSelected(idItem, "#hdLstAngelsFltPriv");
    borrarItemSelected(idItem, "#hdLstAngelsFltVist");
}


function borrarItemVigilants(idItem){
    
    if($('#hdFiltroActual').val() === '0'){
        borrarItemSelected(idItem, "#hdLstAngelsFltWall");
    }else if($('#hdFiltroActual').val() === '1'){
        borrarItemSelected(idItem, "#hdLstAngelsFltFriends");
    }else if($('#hdFiltroActual').val() === '2'){
        borrarItemSelected(idItem, "#hdLstAngelsFltPriv");
    }else if($('#hdFiltroActual').val() === '3'){
        borrarItemSelected(idItem, "#hdLstAngelsFltVist");
    }

    habilitarGuardar();
}

var changeForm = false;
var sameForm = false;

function setChangeForm(valor){
    changeForm = valor;
}

function setWinOpen(){
    changeForm = true;
    muestraLoaderSin();
}


function clearWinOpen(){
    $(function(){
        if($('jquery-loader')){
            $("#jquery-loader").remove();
            $("#jquery-loader-background").remove();
        
        }
    });
}

function checkChangeForm(){
    
    if((changeForm == false && sameForm == true) || 
        (changeForm == false && sameForm == false)||
        (changeForm == true && sameForm == true)){
        
        clearWinOpen();
    }else if(changeForm == true){
        sameForm = false;
    }

}


function getURLvar(var_name){
  var re = new RegExp(var_name + "(?:=([^&]*))?", "i");
  var pm = re.exec(decodeURIComponent(location.search));
  if(pm === null) return undefined;
  return pm[1] || "";
}

function iniciarModal(){
    $(function(){
        var decodeURLAngels = getURLvar('hdAngelsGoogleSelected');
        var decodeAngels = decodeURIComponent(decodeURLAngels);
        
        $("#hdAngelsGoogleSelectedModal").val(decodeAngels); 
    });
}

function salirModalSinDatos(){
    $(function(){
        var dialogDiv = $("#modalDialog");
        
        dialogDiv.dialog("close");
        dialogDiv = $('<div id=\"modalDialog\"></div>').appendTo('#modalContainer');
        $("#modalDialog").dialog("destroy").remove();
   }); 
}

function salirModalCancelar(){
    window.parent.salirModalSinDatos();
}

function salirModalDesdePadre(menSave, menWait){
    $(function(){
        var dialogDiv = $("#modalDialog");
        
        dialogDiv.dialog("close");
        dialogDiv = $('<div id=\"modalDialog\"></div>').appendTo('#modalContainer');
        $("#modalDialog").dialog("destroy").remove();
        
        muestraLoader(menSave, menWait);
   }); 
}

function salirModal(menSave, menWait){
   window.parent.salirModalDesdePadre(menSave, menWait);
   
}

function initRender(){
    changeForm = true;
    sameForm = true;
}

function muestraDetalles(){
    $(document).ready(function() {
        if ($('#idBtnMostrar').attr("src") == "../SNSAngelGuardFB/resources/desplegar.gif") {
            $("#idDivDetails").show("slow");
            $('#idBtnMostrar').attr("src", "../SNSAngelGuardFB/resources/contraer.gif")
        } else if ($('#idBtnMostrar').attr("src") == "../SNSAngelGuardFB/resources/contraer.gif") {
            $("#idDivDetails").hide("slow");
            $('#idBtnMostrar').attr("src", "../SNSAngelGuardFB/resources/desplegar.gif")
        }
    });
}

function loadForm(url, menTitle, menWait){
    $(document).ready(function() {
        window.location = url;
        $('#frIndex').submit(function() {
            event.preventDefault();
            
            var objeto;
            objeto = $.ajax({
                type: 'POST',
                url: url,
                
                data: '',
                beforeSend: muestraLoader(menTitle, menWait),
                success: cerrarLoader()
            });
        });
    });
}

function loadFeedDialog(context, idFacebookAngel, uidAngel, uidPublic, title, subtitle, bodyPost){
        FB.ui(
                {
                    to: idFacebookAngel,
                    method: 'feed',
                    name: title,
                    link: context + 'SNSAngelGuardFB/saveEmailFacebookContact.jsp?uidAngel=' + uidAngel + '&uidPublicUser=' + uidPublic,
                    picture: 'https://snsangelguard.com/SNSAngelGuardFB/resources/logo200.png',
                    caption: subtitle,
                    description: bodyPost
                },
        function(response) {
            if (response && response.post_id) {
                lanzarModal('../SNSAngelGuardFB/infoMessage.jsp?typeInfo=0&infoMessage=' + menSaveOk,750,350);
            } else {
                deleteAngelAjaxByErrorPostingWall(idFacebookAngel);
            }
        }
        );
}

function validarEmailTest(valor)
{
    // creamos nuestra regla con expresiones regulares.
    var filter = /[\w-\.]{3,}@([\w-]{2,}\.)*([\w-]{2,}\.)[\w-]{2,4}/;
    // utilizamos test para comprobar si el parametro valor cumple la regla
    if (filter.test(valor))
        return true;
    else
        return false;
}

function validarEmail(msgEmptyEmail, msgNotValidEmail, menCarga, menWait) {
    $(document).ready(function() {
        $(function() {
            if ($("#txtEmailContact").val() == '')
            {
                lanzarModal('../SNSAngelGuardFB/infoMessage.jsp?typeInfo=1&infoMessage=' + msgEmptyEmail, 750, 350);
            } else if (validarEmailTest($("#txtEmailContact").val()))
            {
                // Cargamos los parametros para ser enviados en el formulario
                $('#par3').val('1');
                $('#par4').val('F');
                $('#par5').val($("#txtEmailContact").val());
                
                // Llamamos a la pagina de confirmacion del angel
                enviarFormularioAngelConfirmation(menCarga, menWait);
            } else
            {
                lanzarModal('../SNSAngelGuardFB/infoMessage.jsp?typeInfo=1&infoMessage=' + msgNotValidEmail, 750, 350);
            }
        });
    });
}

function deleteContactInEmailPage(menSave, menWait){
    $(document).ready(function() {
        $(function() {
            // Cargamos los parametros para ser enviados en el formulario
            $('#par3').val('0');

            // Llamamos a la pagina de confirmacion del angel
            enviarFormularioAngelConfirmation(menSave, menWait);
        });
    }); 
}

function enviarFormularioAngelConfirmation(menCarga, menWait){
    
        // Mostramos el loader mientras se carga la pagina
        muestraLoader(menCarga, menWait);
        
        // Cargamos la pagina de confirmacion del angel
        document.getElementById('frSaveEmailFacebookContact').setAttribute('method','post');
        document.getElementById('frSaveEmailFacebookContact').submit();
}

function deleteNewContact(uidPublic, idAngel){
    muestraLoader(menSave,menWait);
    var data = '..SNSAngelGuardFB/angelConfirmation.jsp' + '?' + "par1=" + uidPublic + "&par2=" + idAngel + "&par3=0";
    
    document.getElementById('frSNSAngels').setAttribute('action', data);
    document.getElementById('frSNSAngels').setAttribute('method','post');
    document.getElementById('frSNSAngels').submit();
}

function redirectToScheduler(uid, accessToken){
    $(function() {
        $('#uid').val(uid);
        $('#accessToken').val(accessToken);

        $('#frIndex').attr('action', 'schedulerUserLoggedFacebook.jsp');
        $('#frIndex').attr('method', 'post');
        $('#frIndex').submit();

    });
}
