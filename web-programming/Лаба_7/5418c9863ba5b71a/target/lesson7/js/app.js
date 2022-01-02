window.notify = function (message) {
    $.notify(message, {
        position: "right bottom",
        className: "success"
    });
}

window.ajax = function (data, success) {
    $.ajax({
        type: "POST",
        url: "",
        dataType: "json",
        data: data,
        success: function(response) {
            if (response["redirect"]) {
                location.href = response["redirect"];
            }
            success(response);
        }
    });
}