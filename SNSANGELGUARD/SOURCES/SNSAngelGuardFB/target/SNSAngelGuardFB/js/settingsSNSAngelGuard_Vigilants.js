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

    if (isAnyAngelForFilter()) {
        if ($('#hdActive' + $("#hdArrayKeysFilter")[actualFilter]).val() === '1') {
            $('#hdFrec+ $("#hdArrayKeysFilter")[actualFilter]').attr("value", $('#slcFrecuency').val());
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
    
    return $("#hdArrayKeysFilter")[$("#hdFiltroActual").val()];
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

function loadHTMLFilters(){
    
    var htmlTableVigilant ='<table id="tableContentNewVigilants" width="695px">';
    
    for(var i = 1 ; i <= $("#hdArrayKeysFilter").length; i++){
        var htmlTRVigilant = '<tr>' +
                '<td> <!--imagen del usuario del usuario--> ' +
                '<div id="vigilantContainer' + i + 
                '" class="vigilantContainer" onmouseover="this.className = "vigilantContainerOver"" onmouseout="this.className = "vigilantContainer"' +
                'onclick="seleccionVig("vigilantContainer' + i +'");loadEstadoFiltro("' +$("#hdArrayKeysFilter")[i - 1] + '", "../SNSAngelGuardFB/resources/robots/robot' + i + '.png");">' +
                '<table width="698px">' +
                '<tr>' +
                '<td>' +
                '<figure class="user">' +
                '<img id="imgContRobot' + i + '" src="../SNSAngelGuardFB/resources/robots/robot' + i + '.png" WIDTH="50" HEIGHT="72" alt="" />' +
                '</figure>' + 
                '<blockquote class="description arrowLeft"> <!--informacion del usuario-->' +
                '<h1 class="vigilantDescription"><%= controler.getJspResources().getArrayDes()[' + i + ']%></h1>' +
                '</blockquote>' +
                '</td>' +
                '<td width="35px" class="botonBox">' +
                '<table>' +
                '<tr>' +
                '<td>' +
                '<img id="imgTurnOnOff' + i + '" src="../SNSAngelGuardFB/resources/turnOn.png" WIDTH="20" HEIGHT="20" alt="" />' +
                '</td>' +
                '</tr>' +
                '<tr>' +
                '<td>' +
                '<img id="imgAlertNotAngels' + i + '" src="../SNSAngelGuardFB/resources/alertNotAngels.png" WIDTH="20" HEIGHT="20" alt="" />' +
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