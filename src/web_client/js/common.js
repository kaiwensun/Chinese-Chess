function postToApi(api, data, callback) {
    var baseApi = 'http://skwapp.org:8080/chessgame/api/v1/';
    $.ajax( {    
        url: baseApi + api,
        type:'post',
        data: JSON.stringify(data),
        contentType: "application/json",
        dataType: 'json',
        success: callback,
        error : function() {
              alert("异常！");
        }
    });
}
