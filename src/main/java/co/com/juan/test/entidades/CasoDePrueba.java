package co.com.juan.test.entidades;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

public class CasoDePrueba {

    @NotEmpty
    private String dimensiones;
    private List<String> operaciones;

    public CasoDePrueba() {
    }

    public CasoDePrueba(String dimensiones, List<String> operaciones) {
        this.dimensiones = dimensiones;
        this.operaciones = operaciones;
    }

    public String getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(String dimensiones) {
        this.dimensiones = dimensiones;
    }

    public List<String> getOperaciones() {
        return operaciones;
    }

    public void setOperaciones(List<String> operaciones) {
        this.operaciones = operaciones;
    }
}
