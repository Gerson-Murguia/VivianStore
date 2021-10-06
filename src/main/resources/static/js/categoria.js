/**
 * 
 */
 var tabladata;

$(document).ready(function () {

        tabladata = $('#tabla').DataTable({
            responsive:true,
            "ajax": {
                "url": '/api/v1/listarCategoria',
                "type": "GET",
                "datatype": "json"
            },
            "columns": [
                { "data": "Descripcion" },
                {
                    "data": "Activo", "render": function (data) {
                        if (data) {
                            return '<span class="badge bg-success">Activo</span>'
                        } else {
                            return '<span class="badge bg-danger">No Activo</span>'
                        }
                    }
                },
                {
                    "data": "IdCategoria", "render": function (data, type, row, meta) {

                        return $("<button>").addClass("btn btn-primary btn-editar btn-sm").append(
                            $("<i>").addClass("fas fa-pen")
                        ).attr({ "data-informacion": JSON.stringify(row) })[0].outerHTML
                        +
                        $("<button>").addClass("btn btn-danger btn-eliminar btn-sm ms-2").append(
                            $("<i>").addClass("fas fa-trash")
                        ).attr({ "data-informacion": JSON.stringify(row) })[0].outerHTML;

                    },
                    "orderable": false,
                    "searchable": false,
                    "width": "90px"
                }

            ],
            "language": {
                "url": "//cdn.datatables.net/plug-ins/1.10.25/i18n/Spanish.json"
            }
        });
});



$(document).on('click', '.btn-editar', function (event) {
    var json = $(this).data("informacion")

    abrirModal(json)
});

function abrirModal(json) {
    $("#txtid").val(0);
    $("#txtdescripcion").val("");
    $("#cboEstado").val(1);

    if (json != null) {

        $("#txtid").val(json.IdCategoria);
        $("#txtdescripcion").val(json.Descripcion);
        $("#cboEstado").val(json.Activo == true ? 1 : 0);
    }

    $('#FormModal').modal('show');
}

$(document).on('click', '.btn-eliminar', function (event) {
    var json = $(this).data("informacion")

    jQuery.ajax({
        url: '@Url.Action("EliminarCategoria", "Home")',
        type: "POST",
        data: JSON.stringify({ id: json.IdCategoria}),
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (data) {

            if (data.resultado) {
                tabladata.ajax.reload();
            } else {
                alert("No se pudo eliminar")
            }
        },
        error: function (error) {
            console.log(error)
        },
        beforeSend: function () {

        },
    });
});

function Guardar() {
    var request = {
        objeto: {
            IdCategoria: $("#txtid").val(),
            Descripcion: $("#txtdescripcion").val(),
            Activo: parseInt($("#cboEstado").val()) == 1 ? true : false
        }
    }

    jQuery.ajax({
        url: '@Url.Action("GuardarCategoria", "Home")',
        type: "POST",
        data: JSON.stringify(request),
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (data) {

            if (data.resultado) {
                tabladata.ajax.reload();
                $('#FormModal').modal('hide');
            } else {
                alert("No se pudo guardar los cambios")
                //swal("Mensaje", "No se pudo guardar los cambios", "warning")
            }
        },
        error: function (error) {
            console.log(error)
        },
        beforeSend: function () {

        },
    });

}