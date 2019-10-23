var requesturl='/webapi/';
var vidurl='http://49.4.69.36:8076/';

// ajax 处理
vehlic = {
    ajax : function( params ){
        $.ajax({
            type: params.type,
            url: params.url,
            data: params.data,
            dataType: params.dataType,
            error: function ( XMLHttpRequest, textStatus, errorThrown ) {
                if( textStatus == '500'){
                    window.location.href = "permissionDenied.html";
                }else{
                    params.error( XMLHttpRequest, textStatus, errorThrown );
                }
            },
            success: function (res) {
                if( res.code ==  999 ){
                    window.parent.location.href = "login.html";
                }else{
                    params.success(res);
                }
            }
        })
    }
}

// 防止重复提交
var isAjaxNow = false;
function cantAjaxAgain (){
    if ( isAjaxNow ) return true;
    isAjaxNow = true;
    setTimeout(function () {
        isAjaxNow = false;
    }, 600);
}
