package co.com.juan.test;

import co.com.juan.test.entidades.RespuestaCubo;
import co.com.juan.test.entidades.SolicitudPruebas;
import co.com.juan.test.servicios.IServicioCubo;
import co.com.juan.test.servicios.ServicioCubo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControladorCubo {

    @RequestMapping(path = "/opcubo", method = RequestMethod.POST, produces = "application/json")
    public RespuestaCubo opcubo(@RequestBody SolicitudPruebas solicitudPruebas) {
        IServicioCubo servicioCubo = new ServicioCubo(solicitudPruebas);
        try {
            return servicioCubo.execute();
        } catch (Exception e) {
            e.printStackTrace();
            return new RespuestaCubo("Ocurrio un error inesperado procesando la petición", null);
        }
    }
}