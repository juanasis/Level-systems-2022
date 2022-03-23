package ar.edu.undec.level.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/*
*
* Esta clase va a comprobar si hay un token valido, sino va a devolver una respuesta 401 no autorizado
*
 */
@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {
    //esto es para ver cual es el metodo que nos esta dando error en caso de que falle la aplicacion
    private final static Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException e) throws IOException, ServletException {
       //indicamos cual es el metodo que da el error
        //se utiliza solo en desarrollo en produccion no interesa Logger
        logger.error("fail en el método commence");
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "no autorizado");
    }
}
