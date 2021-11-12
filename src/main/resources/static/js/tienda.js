/**
 * 
 */
 $(".btn-ver-categoria").click(function () {
            jQuery.ajax({
                url: '/api/v1/listarCategoria',
                type: "GET",
                data: null,
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    $(".row-categoria").html("");
                    $(".modal-body").LoadingOverlay("hide");
                     /*for (a in data) {
       					 console.log(data[a].idCategoria);
       					  $("<div>").addClass("col-4").append(
                                $("<button>").addClass("btn btn-outline-primary btn-categoria m-1 w-100").text(data[a].descripcion).attr({ "onclick": "listarProductos(" + data[a].idCategoria + ")" })
                            ).appendTo(".row-categoria");
   					 }*/
                    if (data != null) {
                        $("<li>").addClass("dropdown-item").append(
                            $("<button>").addClass("btn btn-outline-dark btn-categoria m-1 w-100").text("Mostrar Todo").attr({ "onclick": "listarProductos(0)" })
                        ).appendTo(".row-categoria");

                        $.each(data, function (i, item) {
                            $("<li>").addClass("dropdown-item").append(
                                $("<button>").addClass("btn btn-outline-dark btn-categoria m-1 w-100").text(item.descripcion).attr({ "onclick": "listarProductos(" + item.idCategoria + ")" })
                            ).appendTo(".row-categoria");
                        });
                    }
                },
                error: function (error) {
                    console.log(error)
                },
                beforeSend: function () {
                    $(".modal-body").LoadingOverlay("show");
                },
            });
            $('#exampleModal').modal('show');
        });

        $(document).ready(function () {
            listarProductos(0);
        })

        $(document).on('click', '.btn-detalle', function (event) {
            var json = $(this).data("elemento")
            window.location.href = "/vivian/producto/"+ json.idProducto;
        });

        $(document).on('click', '.btn-categoria', function (event) {
            $('#exampleModal').modal('hide');
        });


        function listarProductos(_idcategoria) {
            jQuery.ajax({
                url: '/api/v1/vivian/listarProducto/'+_idcategoria,
                type: "GET",
                //data: JSON.stringify({ idcategoria: _idcategoria}),
                //dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    $("#catalogo-productos").html("");

                    $("#catalogo-productos").LoadingOverlay("hide");
                    if (data != null) {

                        $.each(data, function (i, item) {
                            //catalogo-productos
                            $("<div>").addClass("col mb-5").append(
                                $("<div>").addClass("card h-100").append(
                                    $("<img>").addClass("card-img-top").attr({ "src": "data:image/" + item.extension + ";base64," + item.base64 }),
                                    //Product details
                                    $("<div>").addClass("card-body p-4").append(
                                        $("<div>").addClass("text-center").append(
                                            $("<h5>").addClass("fw-bolder").text(item.nombre),
                                            "S/. " + item.precio
                                        )
                                    ),
                                    //Product actions
                                    $("<div>").addClass("card-footer p-4 pt-0 border-top-0 bg-transparent").append(
                                        $("<div>").addClass("d-grid d-md-grid gap-2 d-md-block align-items-center text-center").append(
                                            $("<button>").addClass("btn btn-outline-dark mt-auto btn-detalle").text("Ver Detalle").attr({ "data-elemento": JSON.stringify(item) }),
                                         			//deberia ser solo si el usuario es cliente
                                                    $("<button>").addClass("btn btn-outline-dark mt-auto btn-agregar-carrito").data("idproducto", item.idProducto).text("Agregar al carrito")
   
                                        )
                                    )
                                )

                            ).appendTo("#catalogo-productos");
                        });
                    }
                },
                error: function (error) {
                    console.log(error)
                },
                beforeSend: function () {
                    $("#catalogo-productos").LoadingOverlay("show");
                },
            });
        }