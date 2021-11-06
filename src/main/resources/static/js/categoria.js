/**
 * 
 */
 var tabladata;

$(document).ready(function () {

        tabladata = $('#tabla').DataTable({
            responsive:true,
            dom: 'frtilpB',       
	        buttons:[ 
				{
					extend:    'excelHtml5',
					text:      '<i class="fas fa-file-excel"></i> ',
					titleAttr: 'Exportar a Excel',
					className: 'btn btn-success'
				},
				{
					extend:    'pdfHtml5',
					text:      '<i class="fas fa-file-pdf"></i> ',
					titleAttr: 'Exportar a PDF',
					className: 'btn btn-danger'
				},
				{
					extend:    'print',
					text:      '<i class="fa fa-print"></i> ',
					titleAttr: 'Imprimir',
					className: 'btn btn-info'
				}
			],	
            "ajax": {
                "url": '/api/v1/listarCategoria',
                "type": "GET",
                "datatype": "json",
                //TODO: Averiguar para que se necesita el dataSrc "" si requiere un array de objetos
                dataSrc:""
            },
            "columns": [
                { "data": "descripcion" },
                {
                    "data": "esActivo", "render": function (data) {
                        if (data) {
                            return '<span class="badge bg-success">Activo</span>'
                        } else {
                            return '<span class="badge bg-danger">No Activo</span>'
                        }
                    }
                },
                {
                    "data": "idCategoria", "render": function (data, type, row, meta) {

                        return $("<button>").addClass("btn btn-primary btn-editar btn-sm").append(
                            $("<i>").addClass("fas fa-pen")
                        ).attr({ "data-informacion": JSON.stringify(row) })[0].outerHTML
                        +
                        $("<button>").addClass("btn btn-danger btn-eliminar btn-sm ms-2").append(
                            $("<i>").addClass("fas fa-trash")
                        ).attr({ "data-informacion": JSON.stringify(row) })[0].outerHTML;

                    },
                    "orderable": false,
                    "searchable": false
                }

            ],
            "language": {
                "url": "//cdn.datatables.net/plug-ins/1.10.25/i18n/Spanish.json"
            }
        });
});



$(document).on('click', '.btn-editar', function (event) {
    var json = $(this).data("informacion")
	console.log(json)
    abrirModal(json)
});

function abrirModal(json) {
    $("#txtid").val();
    $("#txtdescripcion").val("");
    $("#cboEstado").val(1);
	
	//al editar
	//asegurarse que los nombres sean igual al data-informacion
    if (json != null) {
        $("#txtid").val(json.idCategoria);
        $("#txtdescripcion").val(json.descripcion);
        $("#cboEstado").val(json.esActivo == true ? 1 : 0);
    }

    $('#FormModal').modal('show');
}

$(document).on('click', '.btn-eliminar', function (event) {
    var json = $(this).data("informacion")
	
    jQuery.ajax({
        url: '/api/v1/eliminarCategoria/'+json.idCategoria,
        type: "DELETE",
        //data: JSON.stringify({ id: json.idCategoria}),
        //dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (data) {
	
                tabladata.ajax.reload();
                swal("Exito", "Se elimino la categoria", "success")
        },
        error: function (error) {
            console.log(error)
			swal("Error", "No se elimino la categoria", "error")

        },
        beforeSend: function () {
			
        },
    });
});

function Guardar() {
    var request = {
            idCategoria: $("#txtid").val(),
            descripcion: $("#txtdescripcion").val(),
            esActivo: parseInt($("#cboEstado").val()) == 1 ? true : false 
    }
    jQuery.ajax({
        url: '/api/v1/guardarCategoria',
        type: "POST",
        data: JSON.stringify(request),
        contentType: "application/json; charset=utf-8",
        success: function (data) {

            if (data) {
                tabladata.ajax.reload();
                $('#FormModal').modal('hide');
                swal("Exito", "Se guardo la categoria", "success")

            } else {
                swal("Error", "No se pudo guardar los cambios", "warning")
            }
        },
        error: function (error) {
            console.log(error)
        },
        beforeSend: function () {
			console.log(request)
        },
    });

}