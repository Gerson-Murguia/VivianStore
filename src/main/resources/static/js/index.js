$(document).ready(function() {
    jQuery.ajax({
        url: 'api/v1/listarProductos',
        type: "GET",
        data: null,
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function(data) {
            if (data!=null) {
                $("#total-productos").text(data.length);
            }
        },
        error: function(error) {
            console.log(error)
        },
        beforeSend: function() {

        }
    });

    jQuery.ajax({
        url: 'api/v1/listarMarca',
        type: "GET",
        data: null,
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function(data) {
            if (data!=null) {
                $("#total-marcas").text(data.length);
            }
        },
        error: function(error) {
            console.log(error)
        },
        beforeSend: function() {

        }
    });

    jQuery.ajax({
        url: 'api/v1/listarCategoria',
        type: "GET",
        data: null,
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function(data) {
			console.log(data.length);
            if (data!=null) {
                $("#total-categorias").text(data.length);
            }
        },
        error: function(error) {
            console.log(error)
        },
        beforeSend: function() {

        }
    });
})