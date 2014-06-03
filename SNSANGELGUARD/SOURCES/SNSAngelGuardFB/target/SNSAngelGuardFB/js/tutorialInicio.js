/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

// Path a las imagenes del tutorial
var PATH_BASE_IMG = "../SNSAngelGuardFB/resources/imgTutorial/";

// Valor minimo para la paginacion
var MIN_PAG = parseInt(0);

// Valor maximo para la paginacion
var MAX_PAG = parseInt(16);

// Valor inicial para la paginacion
var currentValue = parseInt(0);

/**
 * Carga la descripci?n de la imagen actual.
 */
function loadDescription(){
    var arrayDes = $("#hdDesTutInitHelp").split(";");
    
    $("#idTdDescription").attr("innerHTML",'<h1 class="letraNormTut">' + arrayDes[parseInt(currentValue)] + '</h1>');
}


/**
 * Obtiene la siguiente imagen a mostrar y actualiza la paginacion.
 */
function getNext(){
    currentValue = parseInt(currentValue) + parseInt(1);
    
    loadDescription();
    $("#currentImgTut").attr("src", PATH_BASE_IMG + $("#hdLocaleValue").val() + "/" + currentValue + ".png");
    
    updatePagination();
}

/**
 * Muestra la imagen anterior a la actual y actualiza la paginacion.
 */
function getPrevious(){
    currentValue = parseInt(currentValue) - parseInt(1);
    
    loadDescription();
    $("#currentImgTut").attr("src", PATH_BASE_IMG + $("#hdLocaleValue").val() + "/" + currentValue + ".png");
    
    updatePagination();
}

/**
 * Muestra la primera pagina y actualiza la paginacion.
 */
function getFirst(){
    currentValue = MIN_PAG;
    
    loadDescription();
    $("#currentImgTut").attr("src", PATH_BASE_IMG + $("#hdLocaleValue").val() + "/" + currentValue + ".png");
    
    updatePagination();
}

/**
 * Muestra la ultima pagina y actualiza la paginacion.
 */
function getLast(){
    currentValue = MAX_PAG;
    
    loadDescription();
    $("#currentImgTut").attr("src", PATH_BASE_IMG + $("#hdLocaleValue").val() + "/" + currentValue + ".png");
    
    updatePagination();
}

/**
 * Actualiza los botones de la paginacion segun el valor actual de currentValue.
 */
function updatePagination(){
    if(parseInt(currentValue) === MIN_PAG){
        deshabilitarBotonQuery("#imgFirstPag", "../SNSAngelGuardFB/resources/pagination/first_des.png");
        deshabilitarBotonQuery("#imgPreviousPag", "../SNSAngelGuardFB/resources/pagination/previous_des.png");
        
        habilitarBotonNext("../SNSAngelGuardFB/resources/pagination/next.png");
        habilitarBotonLast("../SNSAngelGuardFB/resources/pagination/last.png");
    } else if(parseInt(currentValue) === MAX_PAG){
        habilitarBotonFirst("../SNSAngelGuardFB/resources/pagination/first.png");
        habilitarBotonPrevious("../SNSAngelGuardFB/resources/pagination/previous.png");
        
        deshabilitarBotonQuery("#imgNextPag", "../SNSAngelGuardFB/resources/pagination/next_des.png");
        deshabilitarBotonQuery("#imgLastPag", "../SNSAngelGuardFB/resources/pagination/last_des.png");
    } else {
        habilitarBotonFirst("../SNSAngelGuardFB/resources/pagination/first.png");
        habilitarBotonPrevious("../SNSAngelGuardFB/resources/pagination/previous.png");
        
        habilitarBotonNext("../SNSAngelGuardFB/resources/pagination/next.png");
        habilitarBotonLast("../SNSAngelGuardFB/resources/pagination/last.png");
    }
}

function deshabilitarBotonQuery(idBoton, src) {

    $(idBoton).attr("src", src);
    $(idBoton).attr("class", "disabledPag");
    $(idBoton).attr("title", "");
    
    if ($(idBoton).attr('onclick')) {
        $(idBoton).removeAttr('onclick').click(function() {
        });
    } else {
        $(idBoton).unbind('click');
        $(idBoton).click(function() {
        });
    }
}

function habilitarBotonNext(src){
    $("#imgNextPag").attr("src", src);
    $("#imgNextPag").attr("class", "enabledPag");
    $("#imgNextPag").attr("title", $("#hdTitleNext").val());
    
    if ($("#imgNextPag").attr('onclick')) {
        $("#imgNextPag").removeAttr('onclick').click(function() {
            getNext();
        });
    } else {
        $("#imgNextPag").unbind('click');
        $("#imgNextPag").click(function() {
            getNext();
        });
    }
}

function habilitarBotonPrevious(src){
    $("#imgPreviousPag").attr("src", src);
    $("#imgPreviousPag").attr("class", "enabledPag");
    $("#imgPreviousPag").attr("title", $("#hdTitlePrevious").val());
    
    if ($("#imgPreviousPag").attr('onclick')) {
        $("#imgPreviousPag").removeAttr('onclick').click(function() {
            getPrevious();
        });
    } else {
        $("#imgPreviousPag").unbind('click');
        $("#imgPreviousPag").click(function() {
            getPrevious();
        });
    }
}

function habilitarBotonFirst(src){
    $("#imgFirstPag").attr("src", src);
    $("#imgFirstPag").attr("class", "enabledPag");
    $("#imgFirstPag").attr("title", $("#hdTitleFirst").val());
    
    if ($("#imgFirstPag").attr('onclick')) {
        $("#imgFirstPag").removeAttr('onclick').click(function() {
            getFirst();
        });
    } else {
        $("#imgFirstPag").unbind('click');
        $("#imgFirstPag").click(function() {
            getFirst();
        });
    }
}

function habilitarBotonLast(src){
    $("#imgLastPag").attr("src", src);
    $("#imgLastPag").attr("class", "enabledPag");
    $("#imgLastPag").attr("title", $("#hdTitleLast").val());
    
    if ($("#imgLastPag").attr('onclick')) {
        $("#imgLastPag").removeAttr('onclick').click(function() {
            getLast();
        });
    } else {
        $("#imgLastPag").unbind('click');
        $("#imgLastPag").click(function() {
            getLast();
        });
    }
}