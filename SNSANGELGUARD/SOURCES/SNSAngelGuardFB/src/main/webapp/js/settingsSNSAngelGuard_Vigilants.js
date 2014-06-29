/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var arrayAngelsVig = '';

function setArrayAngels(arrayAngels){
    arrayAngelsVig = arrayAngels;
}

function habilitarImgAlertAndFunction(idFiltro, angelsFiltro, numberFiltro){

    $("#imgTurnOnOff" + numberFiltro).attr("src", "../SNSAngelGuardFB/resources/turnOff.png");
    $("#imgTurnOnOff" + numberFiltro).attr("title", $("#hdTitleAltVigOff").val());

    $('#imgTurnOnOff' + numberFiltro).unbind('click');
    $('#imgTurnOnOff' + numberFiltro).click(function() {
        deshabilitarEdicionVig(idFiltro);
    });

    isAnyAngelForVig(angelsFiltro, numberFiltro);
}

function deshabilitarImgAlertAndFunction(idFiltro, numberFiltro){

    $("#imgTurnOnOff" + numberFiltro).attr("src", "../SNSAngelGuardFB/resources/turnOn.png");
    $("#imgTurnOnOff" + numberFiltro).attr("title", $("#hdTitleAltVigOn").val());

    $('#imgTurnOnOff' + numberFiltro).unbind('click');
    $('#imgTurnOnOff' + numberFiltro).click(function() {
        habilitarEdicionVig(idFiltro);
    });

    deshabilitarAlertNotAngels(numberFiltro);
}

function loadVigilantsState(idFiltro, angelsFiltro, numberFiltro) {

    if ($("#hdActive" + idFiltro).attr("value") === "1") {
        habilitarImgAlertAndFunction(idFiltro, angelsFiltro, numberFiltro);
    } else {
        deshabilitarImgAlertAndFunction(idFiltro, numberFiltro);
    }
}

function habilitarAlertNotAngels(numberFiltro){
    $("#imgAlertNotAngels" + numberFiltro).attr("style", "");
    $("#imgAlertNotAngels" + numberFiltro).attr("title", $("#hdAlarmNotVig" + numberFiltro).val());
}

function deshabilitarAlertNotAngels(numberFiltro){
    $("#imgAlertNotAngels" + numberFiltro).attr("style", "display:none");
    $("#imgAlertNotAngels" + numberFiltro).attr("title", "");
}

function isAnyAngelForVig(angelsFiltro, numberFiltro){
    if (angelsFiltro === '') {
        habilitarAlertNotAngels(numberFiltro);
    } else {
        deshabilitarAlertNotAngels(numberFiltro);
    }
    
}

function initNoActiveValoresFiltro(desFiltro){
    $("#hdActive" + desFiltro).attr("value","0");
    $("#hdFrec" + desFiltro).attr("value","3");
    $("#hdLstAngels" + desFiltro).attr("value","");
    $('#hdAngelsAux').attr("value", "");
}

function initActiveValoresFiltro(desFiltro){
    $("#hdActive" + desFiltro).attr("value","1");
    $("#hdFrec" + desFiltro).attr("value","3");
    $("#hdLstAngels" + desFiltro).attr("value","");
    $('#hdAngelsAux').attr("value", "");
}

function deshabilitarDivSelectAngels(){
    if($("#vigilantSettings").attr("class") === "vigilantSettings"){
        $("#vigilantSettings").attr("class","vigilantSettingsNone"); 
    }
}

function habilitarDivSelectAngels(){
    if($("#vigilantSettings").attr("class") === "vigilantSettingsNone"){
        $("#vigilantSettings").attr("class","vigilantSettings");
        habilitarAngelesFiltro();
    }
}

function loadEstadoFiltro(desFiltro, srcImg){
    $("#imgCurrentVig").attr("src", srcImg);
    $("#imgCurrentVig").attr("title", $("#hdNameVig" + desFiltro).val());

    if($("#hdActive" + desFiltro).val() === '1'){
        if($("#hdLstAngels" + desFiltro).val() !== "null"){
            $("#hdAngelsAux").attr("value",$("#hdLstAngels" + desFiltro).val());
        }
        $("#slcFrecuency").attr("value",$("#hdFrec" + desFiltro).val());
        habilitarAngelesFiltro(desFiltro);
        loadAngelSelects($("#hdAngelsAux").val());
    }
    else{
        initNoActiveValoresFiltro(desFiltro);
        deshabilitarDivSelectAngels();
    }
}
        
function loadStateFiltro(desFiltro){
    loadVigilantsState(desFiltro,getAngelsListByFiltro(desFiltro), desFiltro);
}

function habilitarEdicionVig(desFiltro){
    initActiveValoresFiltro(desFiltro);
    habilitarDivSelectAngels();
    
    habilitarImgAlertAndFunction(desFiltro, getAngelsListByFiltro(desFiltro), desFiltro);
    
    habilitarGuardar();
}

function deshabilitarEdicionVig(desFiltro){
   initNoActiveValoresFiltro(desFiltro);
   deshabilitarDivSelectAngels(); 
   
   deshabilitarImgAlertAndFunction(desFiltro, desFiltro);
   
   habilitarBtnGuardar();
}

function habilitarAngelesFiltro(){
    var strSubAng = $('#hdTitleFbList').val();
    var titleAngelSettAng = $('#hdTitleAngelSettAng').val();

    $('#slcFrecuency').removeAttr("disabled","disabled");
    $('#vigilantAngels').attr("innerHTML","<ul id=\"fcbklist\"></ul>");
    cargarListaAngels(arrayAngelsVig,titleAngelSettAng);
    cargarListaVig(strSubAng);
}

function deshabilitarAngelesFiltro(desFiltro){
    $('#hdActive' + desFiltro).val('0');
    $('#slcFrecuency').attr("disabled", "disabled");
    if(!$('#vigilantAngelsDisabled')){
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
    
    $('#hdLstAngels' + desFiltro).attr("value", "");
    $('#hdAngelsAux').attr("value", "");
    loadAngelSelects($('#hdAngelsAux').value);
}

function cargarListaVig(strFbList){
    var array = strFbList.split(";");

    $(document).ready(function() {
        //id(ul id),width,height(element height),row(elements in row)
        $.fcbkListSelection("#fcbklist","450","50","2",array[0],array[1],array[2]);
    });
}

function loadTitleFcbList(titleFbList,titleAngelSettAng){
    $('#hdTitleFbList').attr("value",titleFbList);
    $('#hdTitleAngelSettAng').attr("value",titleAngelSettAng);
}

function seleccionVig(idVigilante, posContainer){
    // Guardamos la selecci?n del angel
    saveSelectionAngels();

    // Inicializamos la clase de todos los vigilantes
    limpiarListaVigilantes();

    // Establecemos la clase del vigilante seleccionado
    $('#' + idVigilante).attr("class","vigilantContainerSelected");
 
    $('#' + idVigilante).mouseover(function(){
            $(this).removeClass().addClass("vigilantContainerSelected");
        }).mouseout(function(){
            $(this).removeClass().addClass("vigilantContainerSelected");
        });
    
    $('#vigilantSettings').attr("class", "vigilantSettings");
   
    // Establecemos internamente el vigilante seleccionado para guardar sus valores
    setVigActual(posContainer);

}

function saveSelectionAngels(){
    var actualFilter = $('#hdFiltroActual').val();
    var arrayFilters = $("#hdArrayKeysFilter").val().split(";");

    if (isAnyAngelForFilter()) {

        if ($('#hdActive' + arrayFilters[actualFilter]).val() === '1') {
            $('#hdFrec' + arrayFilters[actualFilter]).attr("value", $('#slcFrecuency').val());
        }

    }

    $('#hdAngelsAux').attr("value", "");
}

function setVigActual(posContainer){
    $('#hdFiltroActual').attr("value", getIdFiltro(posContainer));
}

function getDesFiltroActual(){
    var arrayFilters = $("#hdArrayKeysFilter").val().split(";");
    return arrayFilters[$("#hdFiltroActual").val()];
}

function loadVigResources(nameVig, sentenceAlarm){
    var jsonNameVig = $.parseJSON(nameVig);
    var arrayFilters = $("#hdArrayKeysFilter").val().split(";");
    
    for(var i = 0; i < arrayFilters.length; i++){
        var keyActive = arrayFilters[i];
        
        $("#hdNameVig" + keyActive).attr("value", jsonNameVig[keyActive]);
        $("#imgContRobot" + keyActive).attr("title", jsonNameVig[keyActive]);
        $("#hdAlarmNotVig" + keyActive).attr("value", sentenceAlarm + jsonNameVig[keyActive]);
        $("#imgAlertNotAngels" + keyActive).attr("title", $("#hdAlarmNotVig" + jsonNameVig[keyActive]).val());
    }
}

function loadTurnOnOffBottons(nameTurnOn, nameTurnOff){
    $("#hdTitleAltVigOn").attr("value", nameTurnOn);
    $("#hdTitleAltVigOff").attr("value", nameTurnOff);
}

function limpiarListaVigilantes(){
    var arrayFilters = $("#hdArrayKeysFilter").val().split(";");
    
    for(var i = 0;i < arrayFilters.length; i++){
        var idVig = '#vigilantContainer' + arrayFilters[i];
        
        $(idVig).attr("class","vigilantContainer");
        
        $(idVig).mouseover(function(){
            $(this).removeClass().addClass("vigilantContainerOver");
        }).mouseout(function(){
            $(this).removeClass().addClass("vigilantContainer");
        });
    }
}

function loadHTMLFilters(strDesFilter){
    var jsonDesFilter = $.parseJSON(strDesFilter);
    var htmlTableVigilant ='<table id="tableContentNewVigilants" width="600px">';
    
    var arrayFilters = $("#hdArrayKeysFilter").val().split(";");
    
    for(var i = 0 ; i < arrayFilters.length; i++){
        var keyActiveFilter = arrayFilters[i];
        
        var htmlTRVigilant = '<tr>' +
                '<td> <!--imagen del usuario del usuario--> ' +
                '<div id="vigilantContainer' + keyActiveFilter + '" class="vigilantContainer" onmouseover="this.className=\'vigilantContainerOver\'" onmouseout="this.className=\'vigilantContainer\'"' +
                'onclick="seleccionVig(\'vigilantContainer' + keyActiveFilter + '\', \'' + keyActiveFilter+ '\');loadEstadoFiltro(\'' + keyActiveFilter + '\', \'../SNSAngelGuardFB/resources/robots/robot' + keyActiveFilter + '.png\');">' +
                '<table width="680px">' +
                '<tr>' +
                '<td>' +
                '<figure class="user">' +
                '<img id="imgContRobot' + keyActiveFilter + '" src="..\/SNSAngelGuardFB\/resources\/robots\/robot' + keyActiveFilter + '.png" WIDTH="50" HEIGHT="72" alt="" />' +
                '</figure>' + 
                '<blockquote class="description arrowLeft"> <!--informacion del usuario-->' +
                '<h1 class="vigilantDescription">' + jsonDesFilter[keyActiveFilter] + '</h1>' +
                '</blockquote>' +
                '</td>' +
                '<td width="35px" class="botonBox">' +
                '<table>' +
                '<tr>' +
                '<td>' +
                '<img id="imgTurnOnOff' + keyActiveFilter + '" src="../SNSAngelGuardFB/resources/turnOn.png" WIDTH="20" HEIGHT="20" alt="" />' +
                '</td>' +
                '</tr>' +
                '<tr>' +
                '<td>' +
                '<img id="imgAlertNotAngels' + keyActiveFilter + '" src="../SNSAngelGuardFB/resources/alertNotAngels.png" WIDTH="20" HEIGHT="20" alt="" />' +
                '</td>' +
                '</tr>' +
                '</table>' +
                '</td>' +
                '</tr>' +
                '</table>' +
                '</div>' +
                '</td>' +
                '</tr>';
        htmlTableVigilant += htmlTRVigilant;
    }
     
    htmlTableVigilant += '</table>'; 
                                    
    $("#vigilantDefinition").html(htmlTableVigilant);      
}

function obtenerFrecuencia(idSelect) {
    var objectFrec = $("#" + idSelect);
    var arrayFilters = $("#hdArrayKeysFilter").val().split(";");
    var currentFilter = $("#hdFiltroActual").val();

    $('#hdFrec' + arrayFilters[currentFilter]).attr("value", objectFrec.val());
}