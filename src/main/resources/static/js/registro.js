/**
 * 
 */
 
 function Registrar() {
                
            var request = {
                //objeto >>>> debe llevar el mismo nombre que el PARAMETRO de registrar
					nombre:$("#txtnombre").val(),
					apellido:$("#txtapellido").val(),
					email:$("#txtemail").val(),
					password:$("#txtpassword").val()
                //falta implementar el confirmar contraseña
            }
            console.log(request)
            jQuery.ajax({
                url: '/api/v1/registro',
                type: "POST",
                data: JSON.stringify(request),
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function (data) {

                    if (data.resultado) {
                        alert("aqui deberia funcionar o redirigir?");
                    } else {
                        alert("No se pudo guardar los cambios");
                    }
                },
                error: function (error) {
                    console.log(error);
                },
                beforeSend: function () {
					//aqui poner el loading overlay
                }
            });

        }
        
$('#txtpassword, #txtconfpassword').on('keyup', function () {
  if ($('#txtpassword').val() == $('#txtconfpassword').val()) {
    $('#mensaje').html('');
  } else 
    $('#mensaje').html('Las contraseñas deben coincidir');
});

