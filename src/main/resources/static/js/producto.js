/**
 * 
 */
  var tabladata;

        function readURL(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();

                reader.onload = function (e) {
                    $('#img_producto')
                        .attr('src', e.target.result)
                        .width(200)
                        .height(198);
                };

                reader.readAsDataURL(input.files[0]);
            }
        }

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
						//por default es 0 o sin valor y asignar en controller
                        "url": '/api/v1/listarProducto/0',
                        "type": "GET",
                        "datatype": "json",
                         dataSrc:""

                    },
                    "columns": [
                        { "data": "nombre" },
                        { "data": "descripcion" },
                        /*{
                            "data": "oMarca", render: function (data) {

                                return data.Descripcion
                            }
                        },*/
                        {
                            "data": "idCategoria", render: function (data) {
                                return data//.descripcion
                            }
                        },
                        { "data": "precio" },
                        { "data": "stock" },
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
                            "data": "idProducto", "render": function (data, type, row, meta) {

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

            jQuery.ajax({
                url: '/api/v1/listarCategoria',
                type: "GET",
                data: null,
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    $.each(data, function (index, value) {
                        $("<option>").attr({ "value": value.idCategoria }).attr({"data-informacion":JSON.stringify(data)}).text(value.descripcion).appendTo("#cbocategoria");
                    });
                },
                error: function (error) {
                    console.log(error);
                },
                beforeSend: function () {

                }
            });

            jQuery.ajax({
                url: '/api/v1/listarMarca',
                type: "GET",
                data: null,
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    $.each(data, function (index, value) {
                        $("<option>").attr({ "value": value.idMarca }).attr('data-info', data).text(value.descripcion).appendTo("#cbomarca");
                    });
                },
                error: function (error) {
                    console.log(error)
                },
                beforeSend: function () {

                },
            });

        });




        $(document).on('click', '.btn-editar', function (event) {
            var json = $(this).data("informacion")
			console.log(json)
            abrirModal(json)
        });

        function abrirModal(json) {
            $("#txtid").val();
            $("#img_producto").attr({ "src": "" });
            $("#txtnombre").val("");
            $("#txtdescripcion").val("");
            $("#cbomarca").val($("#cbomarca option:first").val());
            $("#cbocategoria").val($("#cbocategoria option:first").val());
            $("#txtprecio").val("");
            $("#txtstock").val("");
            $("#cboEstado").val(1);



            if (json != null) {
                console.log(json)
                $("#txtid").val(json.idProducto);
                //TODO: el json.extension no se esta pasando
                $("#img_producto").attr({ "src": "data:image/" + json.extension + ";base64," + json.base64});
                $("#txtnombre").val(json.nombre);
                $("#txtdescripcion").val(json.descripcion);
                //$("#cbomarca").val(json.oMarca.idMarca);
                $("#cbocategoria").val(json.idCategoria);
                $("#txtprecio").val(json.precio);
                $("#txtstock").val(json.stock);
                $("#cboEstado").val(json.esActivo == true ? 1 : 0);
            }

            $('#FormModal').modal('show');
        }

        $(document).on('click', '.btn-eliminar', function (event) {
            var json = $(this).data("informacion")

            if (confirm('¿Esta seguro de eliminar?')) {
                 jQuery.ajax({
                        url: 'api/v1/eliminarProducto',
                        type: "POST",
                        data: JSON.stringify({ id: json.IdProducto}),
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
            } else {

            }

        });

        function Guardar() {

            var imagenSeleccionada = ($("#imgproducto"))[0].files[0];

            var objeto = {
                idProducto: $("#txtid").val(),
                nombre: $("#txtnombre").val(),
                descripcion: $("#txtdescripcion").val(),
                //oMarca: {idMarca : $("#cbomarca option:selected").val()},
               	idCategoria : $("#cbocategoria option:selected").val(),
                precio: $("#txtprecio").val(),
                stock: $("#txtstock").val(),
                rutaImagen : "",
                esActivo: parseInt($("#cboEstado").val()) == 1 ? true : false
            }

            var request = new FormData();
            request.append("imagenArchivo", imagenSeleccionada);
            request.append("appProducto", JSON.stringify(objeto));

            jQuery.ajax({
                url: '/api/v1/guardarProducto',
                type: "POST",
                data: request,
                processData: false,
                contentType : false,
                success: function (data) {

                    if (data) {
                        tabladata.ajax.reload();
                        $('#FormModal').modal('hide');
                    } else {
                        alert("No se pudo guardar los cambios")
                    }
                },
                error: function (error) {
                    console.log("Error: "+error)
                    alert("No se pudo guardar los cambios")
                },
                beforeSend: function () {

                },
            });

    }

    $.fn.inputFilter = function (inputFilter) {
        return this.on("input keydown keyup mousedown mouseup select contextmenu drop", function () {
            if (inputFilter(this.value)) {
                this.oldValue = this.value;
                this.oldSelectionStart = this.selectionStart;
                this.oldSelectionEnd = this.selectionEnd;
            } else if (this.hasOwnProperty("oldValue")) {
                this.value = this.oldValue;
                this.setSelectionRange(this.oldSelectionStart, this.oldSelectionEnd);
            } else {
                this.value = "";
            }
        });
    };

    $("#txtstock").inputFilter(function (value) {
        return /^-?\d*$/.test(value);
    });

    $("#txtprecio").inputFilter(function (value) {
        return /^-?\d*[.]?\d{0,2}$/.test(value);
    });