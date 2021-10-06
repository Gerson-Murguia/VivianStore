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
               //el tipo de dato que devolvera el servidor dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function (data) {

                    if (data) {
						//termina pantalla de carga
                        $.LoadingOverlay("hide");
                        //location.href = "/login";
                        //TODO: hacer un reload o limpiar los inputs
                       $('#mensaje').addClass('alert alert-dark').html('📧Revisa tu correo para confirmar tu cuenta...')
									
                    } else {
                        console.log("No se pudo guardar los cambios");
                    }
                },
                error: function (error) {
                    console.log("mando error"+error);
                    $.LoadingOverlay("hide");
                },
                beforeSend: function () {
					//muestra pantalla de carga, el proceso de envio de correo
					//demora 5 seg, tal vez mandar el email aparte sea mejor?
					$.LoadingOverlay("show",{
						image:"",
						text:"Registrando...",
						fontawesome: "fas fa-circle-notch fa-spin"
					});
                }
            });

        }

$('#txtpassword, #txtconfpassword').on('keyup', function () {
  if ($('#txtpassword').val() == $('#txtconfpassword').val()) {
    $('#mensaje').html('');
  } else 
    $('#mensaje').html('Las contraseñas deben coincidir');
});

