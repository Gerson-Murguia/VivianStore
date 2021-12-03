/**
 * 
 */
  $(document).ready(function () {
                obtenerCantidad();
            })
            function obtenerCantidad() {
                jQuery.ajax({
                    url: '/api/v1/vivian/carritoCantidad',
                    type: "GET",
                    data: null,
                    dataType: "json",
                    contentType: "application/json; charset=utf-8",
                    success: function (data) {
                        $(".contador-carrito").text(data);
                    },
                    error: function (error) {
                        console.log(error)
                    },
                    beforeSend: function () {

                    },
                });

            }
            $(document).on('click', '.btn-agregar-carrito', function (event) {

                var carrito = {
                        producto: { idProducto: $(this).data("idproducto")  }                   	
                }
                var btn=$(this);
				console.log($(this))
                jQuery.ajax({
                    url: '/api/v1/vivian/insertarCarrito',
                    type: "POST",
                    data: JSON.stringify(carrito),
                    dataType: "json",
                    contentType: "application/json; charset=utf-8",
                    success: function (data) {
                        var actual = parseInt($(".contador-carrito").text());
                       	console.log(actual);
                        if (data!= 0) {
                            actual = actual + 1;
                            $(".contador-carrito").text(actual);
                            $('#toast-carrito').toast('show');
                            btn.prop('disabled',true);
                        }
                    },
                    error: function (error) {
                        console.log(error)
                    },
                    beforeSend: function () {

                    }
                });

            });