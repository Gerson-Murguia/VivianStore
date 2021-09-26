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
                    "ajax": {
                        "url": '@Url.Action("ListarProducto", "Home")',
                        "type": "GET",
                        "datatype": "json"
                    },
                    "columns": [
                        { "data": "Nombre" },
                        { "data": "Descripcion" },
                        {
                            "data": "oMarca", render: function (data) {

                                return data.Descripcion
                            }
                        },
                        {
                            "data": "oCategoria", render: function (data) {
                                return data.Descripcion
                            }
                        },
                        { "data": "Precio" },
                        { "data": "Stock" },
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
                            "data": "IdProducto", "render": function (data, type, row, meta) {

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
                url: '@Url.Action("ListarCategoria", "Home")',
                type: "GET",
                data: null,
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    $.each(data.data, function (index, value) {
                        $("<option>").attr({ "value": value.IdCategoria }).text(value.Descripcion).appendTo("#cbocategoria");
                    });
                },
                error: function (error) {
                    console.log(error)
                },
                beforeSend: function () {

                },
            });

            jQuery.ajax({
                url: '@Url.Action("ListarMarca", "Home")',
                type: "GET",
                data: null,
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    $.each(data.data, function (index, value) {
                        $("<option>").attr({ "value": value.IdMarca }).text(value.Descripcion).appendTo("#cbomarca");
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

            abrirModal(json)
        });

        function abrirModal(json) {
            $("#txtid").val(0);
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
                $("#txtid").val(json.IdProducto);


                $("#img_producto").attr({ "src": "data:image/" + json.extension + ";base64," + json.base64});
                $("#txtnombre").val(json.Nombre);
                $("#txtdescripcion").val(json.Descripcion);
                $("#cbomarca").val(json.oMarca.IdMarca);
                $("#cbocategoria").val(json.oCategoria.IdCategoria);
                $("#txtprecio").val(json.Precio);
                $("#txtstock").val(json.Stock);
                $("#cboEstado").val(json.Activo == true ? 1 : 0);
            }

            $('#FormModal').modal('show');
        }

        $(document).on('click', '.btn-eliminar', function (event) {
            var json = $(this).data("informacion")

            if (confirm('¿Esta seguro de eliminar?')) {
                 jQuery.ajax({
                        url: '@Url.Action("EliminarProducto", "Home")',
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

            var ImagenSeleccionada = ($("#imgproducto"))[0].files[0];

            var objeto = {
                IdProducto: $("#txtid").val(),
                Nombre: $("#txtnombre").val(),
                Descripcion: $("#txtdescripcion").val(),
                oMarca: {IdMarca : $("#cbomarca option:selected").val()},
                oCategoria: { IdCategoria : $("#cbocategoria option:selected").val() },
                Precio: $("#txtprecio").val(),
                Stock: $("#txtstock").val(),
                RutaImagen : "",
                Activo: parseInt($("#cboEstado").val()) == 1 ? true : false
            }

            var request = new FormData();
            request.append("imagenArchivo", ImagenSeleccionada);
            request.append("objeto", JSON.stringify(objeto));

            jQuery.ajax({
                url: '@Url.Action("GuardarProducto", "Home")',
                type: "POST",
                data: request,
                processData: false,
                contentType : false,
                success: function (data) {

                    if (data.resultado) {
                        tabladata.ajax.reload();
                        $('#FormModal').modal('hide');
                    } else {
                        alert("No se pudo guardar los cambios")
                    }
                },
                error: function (error) {
                    console.log(error)
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