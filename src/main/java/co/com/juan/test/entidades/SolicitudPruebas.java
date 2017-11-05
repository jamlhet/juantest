package co.com.juan.test.entidades;

import co.com.juan.test.Soporte.Constantes;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

public class SolicitudPruebas {

    @Min(value = Constantes.UNO, message = Constantes.MAX_MSJ_PRUEBAS)
    @Max(value = Constantes.MAX_PRUEBAS, message = Constantes.MAX_MSJ_PRUEBAS)
    private int pruebas;

    @NotEmpty
    @Size(max=Constantes.MAX_PRUEBAS)
    private List<CasoDePrueba> listaDeCasosDePrueba;

    public SolicitudPruebas() {
    }

    public SolicitudPruebas(int pruebas, List<CasoDePrueba> listaSolicitudCubo) {
        this.pruebas = pruebas;
        this.listaDeCasosDePrueba = listaSolicitudCubo;
    }

    public int getPruebas() {
        return pruebas;
    }

    public void setPruebas(int pruebas) {
        this.pruebas = pruebas;
    }

    public List<CasoDePrueba> getListaDeCasosDePrueba() {
        return listaDeCasosDePrueba;
    }

    public void setListaDeCasosDePrueba(List<CasoDePrueba> listaDeCasosDePrueba) {
        this.listaDeCasosDePrueba = listaDeCasosDePrueba;
    }
}
