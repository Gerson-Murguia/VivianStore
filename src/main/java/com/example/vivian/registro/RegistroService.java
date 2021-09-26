package com.example.vivian.registro;


import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.vivian.email.EmailSender;
import com.example.vivian.email.EmailValidator;
import com.example.vivian.models.AppUsuario;
import com.example.vivian.registro.token.ConfirmacionToken;
import com.example.vivian.registro.token.ConfirmacionTokenService;
import com.example.vivian.security.AppUsuarioRol;
import com.example.vivian.service.AppUsuarioService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegistroService {
	
	private final AppUsuarioService appUsuarioService;
	private final EmailValidator emailValidator;
	private final ConfirmacionTokenService confirmacionTokenService;
	private final EmailSender emailSender;
	

	public String registrar(RegistroRequest request) {
		
		boolean isValidEmail= emailValidator.test(request.getEmail());
		
		if (!isValidEmail) {
			throw new IllegalStateException("email not valid");
		}
		
		
		String token= appUsuarioService.registroUsuario(
								new AppUsuario(
										request.getNombre(),
										request.getApellido(),
										request.getEmail(),
										request.getPassword(),
										AppUsuarioRol.USUARIO)); 
		//TODO: cambiar rol al momento de registrar
		/*{
			"nombre": "Gerson",
			"apellido": "Murguia",
			"email": "aldmurguia1@gmail.com",
			"password":"password",
			"rol":[{"ADMIN":"ADMIN"}]
}*/
		String link="http://localhost:8080/api/v1/registro/confirmar?token="+token;
		
		emailSender.send(request.getEmail(), construirEmail(request.getNombre(), link));
		//maildev localhost:1080
		
		return token;
	}
	
	@Transactional
	public String confirmToken(String token) {
		
		//obtener el token
		ConfirmacionToken confirmacionToken= confirmacionTokenService.getToken(token)
				.orElseThrow(()-> new IllegalStateException(String.format("token %s no encontrado", token)));
		
		//si ya se agrego la fecha de confirmacion
		if (confirmacionToken.getFechaConfirmado()!=null) {
			throw new IllegalStateException("email ya registrado");
		}
		//obtener fecha expiracion
		LocalDateTime fechaExpiracion=confirmacionToken.getFechaExpiracion();
		
		//si ya expiro
		if (fechaExpiracion.isBefore(LocalDateTime.now())) {
			throw new IllegalStateException("token expirado");
		}
		//poner la fecha confirmacion y activar usuario
		//dos update, asi que transactional
		confirmacionTokenService.setFechaConfirmacion(token);
		appUsuarioService.enableAppUsuario(confirmacionToken.getAppUsuario().getEmail());
		
		
		return "Confirmado";
	}
	
	private String construirEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hola " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Gracias por registrarte. Por favor dale click al link de abajo para activatar tu cuenta: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activar Cuenta</a> </p></blockquote>\n El enlace expirara en 30 minutos. <p>Gerson Murguia, Developer</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
	
}
