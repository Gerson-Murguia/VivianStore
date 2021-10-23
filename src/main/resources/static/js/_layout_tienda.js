/**
 * 
 */
  $(document).ready(function () {
                obtenerCantidad();
            })
            function obtenerCantidad() {
                jQuery.ajax({
                    url: '@Url.Action("CantidadCarrito", "Tienda")',
                    type: "GET",
                    data: null,
                    dataType: "json",
                    contentType: "application/json; charset=utf-8",
                    success: function (data) {
                        $(".contador-carrito").text(data.respuesta);
                    },
                    error: function (error) {
                        console.log(error)
                    },
                    beforeSend: function () {

                    },
                });

            }
            $(document).on('click', '.btn-agregar-carrito', function (event) {

                var request = {
                    oCarrito: {
                        oProducto: { IdProducto: $(this).data("idproducto")  }
                    }
                }

                jQuery.ajax({
                    url: '@Url.Action("InsertarCarrito", "Tienda")',
                    type: "POST",
                    data: JSON.stringify(request),
                    dataType: "json",
                    contentType: "application/json; charset=utf-8",
                    success: function (data) {
                        var actual = parseInt($(".contador-carrito").text());
                        if (data.respuesta != 0) {
                            actual = actual + 1;
                            $(".contador-carrito").text(actual);
                            $('#toast-carrito').toast('show');
                        }
                    },
                    error: function (error) {
                        console.log(error)
                    },
                    beforeSend: function () {

                    },
                });

            });