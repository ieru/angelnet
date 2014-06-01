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
    $("#imgCurrentVig").attr("title", $("#hdNameVig" + getIdFiltro(desFiltro)).val());
    
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
    loadVigilantsState(desFiltro,getAngelsListByFiltro(desFiltro), getIdFiltro(desFiltro));
}

function habilitarEdicionVig(desFiltro){
    initActiveValoresFiltro(desFiltro);
    habilitarDivSelectAngels();
    
    habilitarImgAlertAndFunction(desFiltro, getAngelsListByFiltro(desFiltro), getIdFiltro(desFiltro));
    
    habilitarGuardar();
}

function deshabilitarEdicionVig(desFiltro){
   initNoActiveValoresFiltro(desFiltro);
   deshabilitarDivSelectAngels(); 
   
   deshabilitarImgAlertAndFunction(desFiltro, getIdFiltro(desFiltro));
   
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

function seleccionVig(idVigilante){
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
    setVigActual(idVigilante);
}

function saveSelectionAngels(){
    var actualFilter = $('#hdFiltroActual').val();
    
    if(isAnyAngelForFilter()){
        switch(actualFilter){
            case '0':
            if($('#hdActiveFltWall').val() === '1'){
                $('#hdFrecFltWall').attr("value", $('#slcFrecuency').val());
            }
            break;
            case '1':
            if($('#hdActiveFltFriends').val() === '1'){
                $('#hdFrecFltFriends').attr("value", $('#slcFrecuency').val());
            }
            break;
            case '2':
            if($('#hdActiveFltPriv').val() === '1'){
                $('#hdFrecFltPriv').attr("value", $('#slcFrecuency').val());
            }
            break;
            case '3':
                if($('#hdActiveFltVist').val() === '1'){
                $('#hdFrecFltVist').attr("value", $('#slcFrecuency').val());
            }
            break;
        }
    }

    $('#hdAngelsAux').attr("value", "");
}

function setVigActual(idContainerVig){
    switch (idContainerVig) {
        case 'vigilantContainer1':
            $('#hdFiltroActual').attr("value", '0');
            break;
        case 'vigilantContainer2':
            $('#hdFiltroActual').attr("value", '1');
            break;
        case 'vigilantContainer3':
            $('#hdFiltroActual').attr("value", '2');
            break;
        case 'vigilantContainer4':
            $('#hdFiltroActual').attr("value", '3');
            break;
    }
}

function getDesFiltroActual(){
    switch($("#hdFiltroActual").val()){
        case '0':
            return 'FltWall';
        case '1':
            return 'FltFriends';
        case '2':
            return 'FltPriv';
        case '3':
            return 'FltVist';
    }
}

function loadVigResources(nameVig, sentenceAlarm){
    var arrayNameVig = nameVig.split(';');
    
    for(var i = 0; i < arrayNameVig.length; i++){
        
        var index = parseInt(i) + parseInt(1);
        
        $("#hdNameVig" + index).attr("value", arrayNameVig[i]);
        $("#imgContRobot" + index).attr("title", arrayNameVig[i]);
        $("#hdAlarmNotVig" + index).attr("value", sentenceAlarm + arrayNameVig[i]);
        $("#imgAlertNotAngels" + index).attr("title", $("#hdAlarmNotVig" + index).val());
    }
}

function loadTurnOnOffBottons(nameTurnOn, nameTurnOff){
    $("#hdTitleAltVigOn").attr("value", nameTurnOn);
    $("#hdTitleAltVigOff").attr("value", nameTurnOff);
}