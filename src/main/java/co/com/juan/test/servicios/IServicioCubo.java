package co.com.juan.test.servicios;

import co.com.juan.test.entidades.RespuestaCubo;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

public interface IServicioCubo {

    public RespuestaCubo execute();

}
