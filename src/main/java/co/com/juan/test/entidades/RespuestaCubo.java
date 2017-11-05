package co.com.juan.test.entidades;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RespuestaCubo {

    private String mesaje;
    private Map<String, List<String>> resultados = new HashMap<>();

    public RespuestaCubo() {
    }

    public RespuestaCubo(String mesaje, Map<String, List<String>> map) {
        this.mesaje = mesaje;
        this.resultados = map;
    }

    public String getMesaje() {
        return mesaje;
    }

    public void setMesaje(String mesaje) {
        this.mesaje = mesaje;
    }

    public Map<String, List<String>> getResultados() {
        return resultados;
    }

    public void setResultados(Map<String, List<String>> resultados) {
        this.resultados = resultados;
    }
}
