$(document).ready(function () {
            jQuery.ajax({
                url: '/api/v1/vivian/obtenerCarrito',
                type: "GET",
                data: null,
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    $.LoadingOverlay("hide");
                    if (data != null) {
                        $.each(data, function (i, item) {
                            $("<div>").addClass("card mb-2 card-producto").append(
                                $("<div>").addClass("card-body").append(
                                    $("<div>").addClass("row").append(
                                        $("<div>").addClass("col-1").append(
                                            $("<img>").addClass("rounded").attr({ "src": "data:image/" + item.producto.extension + ";base64," + item.producto.base64, "width": "50" })
                                        ),
                                        $("<div>").addClass("col-7").append(
                                            $("<div>").addClass("ml-2").append(
                                                $("<span>").addClass("font-weight-bold d-block").text(item.producto.nombre),
                                                $("<span>").addClass("spec").text(item.producto.descripcion),
                                                $("<span>").addClass("float-end").text("Precio Unitario : S./" + item.producto.precio )
                                            )
                                        ),
                                        $("<div>").addClass("col-3").append(
                                            $("<div>").addClass("d-flex justify-content-end controles").append(
                                                $("<button>").addClass("btn btn-outline-secondary btn-restar rounded-0").append($("<i>").addClass("fas fa-minus")).attr({ "type": "button" }),
                                                $("<input>").addClass("form-control input-cantidad p-1 text-center rounded-0").css({ "width": "40px" }).attr({ "disabled": "disabled" }).val("1").data("precio", item.producto.precio).data("idproducto", item.producto.idProducto),
                                                $("<button>").addClass("btn btn-outline-secondary btn-sumar rounded-0").append($("<i>").addClass("fas fa-plus")).attr({ "type": "button" })
                                            )
                                        ),
                                        $("<div>").addClass("col-1").append(
                                            $("<button>").addClass("btn btn-outline-danger btn-eliminar").append($("<i>").addClass("far fa-trash-alt")).data("informacion", { _idCarrito: item.idCarrito, _idProducto: item.producto.idProducto}),
                                        )
                                    )
                                )
                            ).appendTo("#productos-seleccionados");


                        })

                        obtenerPreciosPago();
                        obtenerCantidadProductos();
                    }
                },
                error: function (error) {
                    console.log(error)
                },
                beforeSend: function () {
                    $.LoadingOverlay("show");
                },
            });
            ListarDepartamento();
        })


        $(document).on('click', '.btn-sumar', function (event) {
            var div = $(this).parent("div.controles");
            var input = $(div).find("input.input-cantidad");
            var cantidad = parseInt($(input).val()) + 1;
            $(input).val(cantidad);
            obtenerPreciosPago()
        });

        $(document).on('click', '.btn-restar', function (event) {
            var div = $(this).parent("div.controles");
            var input = $(div).find("input.input-cantidad");
            var cantidad = parseInt($(input).val()) -1;
            if (cantidad >= 1) {
                $(input).val(cantidad);
            }
            obtenerPreciosPago()
        });


        $(document).on('click', '.btn-eliminar', function (event) {
            var json = $(this).data("informacion");
            var card_producto = $(this).parents("div.card-producto");
			
            if (confirm('¿Esta seguro de eliminar?')) {
                 jQuery.ajax({
                        url: '/api/v1/vivian/eliminarCarrito/'+json._idCarrito,
                        type: "DELETE",
                        //data: JSON.stringify({ id: json.idProducto}),
                        //dataType: "json",
                        contentType: "application/json; charset=utf-8",
                        success: function (data) {
								console.log(data.producto.nombre);
		                       if (data!=null) {
		                        card_producto.remove();
		                        obtenerPreciosPago();
		                        obtenerCantidadProductos();
		                        obtenerCantidad();
		                        swal("Exito", "Se elimino el producto: "+data.producto.nombre, "success")
		                       } else {
		                        alert("No se pudo eliminar")
		                        swal("Error", "No se elimino el item del carrito: "+data.producto.nombre, "error")
		                      }
                        },
                        error: function (error) {
                            console.log(error)
                			swal("Error", "No se elimino el producto", "error")
                        },
                        beforeSend: function () {

                        },
                    });
            }

        })


        function obtenerPreciosPago() {

            var total = 0;
            $("input.input-cantidad").each(function (index) {
                var precio = parseFloat($(this).val()) * parseFloat($(this).data("precio"));
                total = total + precio;
            });
            $("#totalPagar").text("S/. " + total.toFixed(2));
        }
        function obtenerCantidadProductos() {
            $("#cantidad-articulos").text(" " + $("#productos-seleccionados > div.card").length.toString() + " ");

            if ($("#productos-seleccionados > div.card").length == 0) {
               $("#btnProcesarPago").prop("disabled", true);
            }
        }

        $("#btnProcesarPago").on("click", function (e) {
            $("#cboDepartamento").val($("#cboDepartamento option:first").val());
            $("#cboProvincia").val($("#cboProvincia option:first").val());
            $("#cboDistrito").val($("#cboDistrito option:first").val());
            $("#txtContacto").val("");
            $("#txtTelefono").val("");
            $("#txtDireccion").val("");
            $(".control-validar").removeClass("border border-danger");


            if ($("#trj_nombre").val().trim() == "") {
                $("#mensaje-error").text("Debe ingresar nombre del titular");
                $('#toast-alerta').toast('show');
                return;
            } else if ($("#trj_numero").val().trim() == "") {
                $("#mensaje-error").text("Debe ingresar número de la tarjeta");
                $('#toast-alerta').toast('show');
                return;
            } else if ($("#trj_vigencia").val().trim() == "") {
                $("#mensaje-error").text("Debe ingresar vigencia de la tarjeta");
                $('#toast-alerta').toast('show');
                return;
            } else if ($("#trj_cvv").val().trim() == "") {
                $("#mensaje-error").text("Debe ingresar CVV de la tarjeta");
                $('#toast-alerta').toast('show');
                return;
            }

            $("#mdprocesopago").modal("show");

        })

        function ListarDepartamento() {
            jQuery.ajax({
                url: '/api/v1/vivian/obtenerDepartamento',
                type: "POST",
                data: null,
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    $("<option>").attr({ "value": "00","disabled":"disabled","selected":"true" }).text("Seleccionar").appendTo("#cboDepartamento");
                    if (data.lista != null) {
                        $.each(data.lista, function (i, v) {
                            $("<option>").attr({ "value": v.IdDepartamento }).text(v.Descripcion).appendTo("#cboDepartamento");
                        });
                    }
                    ListarProvincia();
                },
                error: function (error) {
                    console.log(error)
                },
                beforeSend: function () {
                },
            });
        }

        $("#cboDepartamento").on("change", function () {
            ListarProvincia();
        });

        function ListarProvincia() {
            jQuery.ajax({
                url: '/api/v1/vivian/obtenerProvincia',
                type: "POST",
                data: JSON.stringify({ _IdDepartamento: $("#cboDepartamento option:selected").val() }),
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    $("#cboProvincia").html("");
                    $("<option>").attr({ "value": "00","disabled":"disabled","selected":"true" }).text("Seleccionar").appendTo("#cboProvincia");
                    if (data.lista != null) {
                        $.each(data.lista, function (i, v) {
                            $("<option>").attr({ "value": v.IdProvincia}).text(v.Descripcion).appendTo("#cboProvincia");
                        });
                    }
                    ListarDistrito();
                },
                error: function (error) {
                    console.log(error)
                },
                beforeSend: function () {
                },
            });
        }

        $("#cboProvincia").on("change", function () {
            ListarDistrito();
        });

        function ListarDistrito() {
            jQuery.ajax({
                url: '/api/v1/vivian/obtenerDistrito',
                type: "POST",
                data: JSON.stringify({ _IdProvincia: $("#cboProvincia option:selected").val(),_IdDepartamento: $("#cboDepartamento option:selected").val() }),
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    $("#cboDistrito").html("");
                    $("<option>").attr({ "value": "00", "disabled": "disabled", "selected": "true" }).text("Seleccionar").appendTo("#cboDistrito");
                    if (data.lista != null) {
                        $.each(data.lista, function (i, v) {
                            $("<option>").attr({ "value": v.IdProvincia }).text(v.Descripcion).appendTo("#cboDistrito");
                        });
                    }
                },
                error: function (error) {
                    console.log(error)
                },
                beforeSend: function () {
                },
            });
        }

        $("#btnConfirmarCompra").on("click", function (e) {
  
            var falta_ingresar_datos = false;

            $(".control-validar").removeClass("border border-danger");

            $(".control-validar").each(function (i) {
                if ($(this).is('input')) {
                    if ($(this).val() == "") {
                        $(this).addClass("border border-danger")
                        falta_ingresar_datos = true;
                    }
                } else if ($(this).is('select')) {
                    if ($(this).children("option:selected").val() == "00") {
                        $(this).addClass("border border-danger")
                        falta_ingresar_datos = true;
                    }
                }
            });

            if (!falta_ingresar_datos) {

                var detalle = [];
                var total = 0;
                $("input.input-cantidad").each(function (index) {
                    var precio = parseFloat($(this).val()) * parseFloat($(this).data("precio"));
                    detalle.push({
                        IdProducto: parseInt($(this).data("idproducto")),
                        Cantidad: parseInt($(this).val()),
                        Total: precio
                    });

                    total = total + precio;
                });

                var request = {
                    oCompra: {
                        TotalProducto: $("#productos-seleccionados > div.card").length,
                        Total: total,
                        Contacto: $("#txtContacto").val(),
                        Telefono: $("#txtTelefono").val(),
                        Direccion: $("#txtDireccion").val(),
                        IdDistrito: $("#cboDistrito").val(),
                        oDetalleCompra: detalle
                    }

                }
           	                                              
                jQuery.ajax({
                    url: '/api/v1/vivian/registrarCompra',
                    type: "POST",
                    data: JSON.stringify(request),
                    //dataType: "json",
                    //contentType: "application/json; charset=utf-8",
                    success: function (data) {
                        //if (data.resultado) {
                            swal("Compra Realizada", "Pronto te informaremos la entrega de tu pedido", "success").then((value) => {
                                 window.location.href = "/vivian/";
                            });
                        //} else {
                          //  swal("Lo sentimos", "No se  pudo completar la compra", "warning");
                        //}
                    },
                    error: function (error) {
                        console.log(error)
                    },
                    beforeSend: function () {
                    },
                });
            }
        })

 