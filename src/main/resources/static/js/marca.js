/**
 * 
 */
 var tabladata;

        $(document).ready(function () {

                //convertir tabla #tabla en Datatable
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
						},
					],	
                    "ajax": {
                        "url": '/api/v1/listarMarca',
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
                            "data": "IdMarca", "render": function (data, type, row, meta) {

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
/*$(document).ready(function() {    
    $('#example').DataTable({        
        language: {
                "lengthMenu": "Mostrar _MENU_ registros",
                "zeroRecords": "No se encontraron resultados",
                "info": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
                "infoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
                "infoFiltered": "(filtrado de un total de _MAX_ registros)",
                "sSearch": "Buscar:",
                "oPaginate": {
                    "sFirst": "Primero",
                    "sLast":"??ltimo",
                    "sNext":"Siguiente",
                    "sPrevious": "Anterior"
			     },
			     "sProcessing":"Procesando...",
            },
        //para usar los botones   
        responsive: "true",
        dom: 'Bfrtilp',       
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
			},
		]	        
    });     
}); */


        $(document).on('click', '.btn-editar', function (event) {
            //guarda los valores del data-informacion(todos los valores de la columna) del boton editar
            //obtiene el data-informacion(no se pone data-,solo informacion)
            var json = $(this).data("informacion");

            abrirModal(json);
        });

        function abrirModal(json) {
            //SE LE ASIGNA 0 AL ID y el estado en true
            //son los input del formulario editar
            $("#txtid").val(0);
            $("#txtdescripcion").val("");
            $("#cboEstado").val(1);
            //si el data-informacion tiene valores(se va a editar)
            if (json != null) {

                $("#txtid").val(json.IdMarca);
                $("#txtdescripcion").val(json.Descripcion);
                //$("#cboEstado").val(json.Activo == true ? 1 : 0);
                $("#cboEstado").val((json.Activo) ? 1 : 0);
            }
            //si se va a solo  crear la marca
            $('#FormModal').modal('show');
        }

        $(document).on('click', '.btn-eliminar', function (event) {
            //guarda los valores del data-informacion del boton eliminar
            var json = $(this).data("informacion");

            jQuery.ajax({
                url: '@Url.Action("EliminarMarca", "Home")',
                type: "POST",
                //se envia en json el valor de id marca
                data: JSON.stringify({ id: json.IdMarca}),
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function (data) {

                    if (data.resultado) {
                        tabladata.ajax.reload();
                    } else {
                        alert("No se pudo eliminar");
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
                //objeto >>>> debe llevar el mismo nombre que el PARAMETRO de guardarMarca
                oMarca: {
                    IdMarca: $("#txtid").val(),
                    Descripcion: $("#txtdescripcion").val(),
                    Activo: parseInt($("#cboEstado").val()) == 1 ? true : false
                }
            }

            jQuery.ajax({
                url: '@Url.Action("GuardarMarca", "Home")',
                type: "POST",
                data: JSON.stringify(request),
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function (data) {

                    if (data.resultado) {
                        tabladata.ajax.reload();
                        $('#FormModal').modal('hide');
                    } else {
                        alert("No se pudo guardar los cambios");
                    }
                },
                error: function (error) {
                    console.log(error);
                },
                beforeSend: function () {

                }
            });

        }

 