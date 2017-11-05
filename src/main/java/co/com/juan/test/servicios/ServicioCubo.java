package co.com.juan.test.servicios;

import co.com.juan.test.Soporte.Constantes;
import co.com.juan.test.entidades.CasoDePrueba;
import co.com.juan.test.entidades.Cubo;
import co.com.juan.test.entidades.RespuestaCubo;
import co.com.juan.test.entidades.SolicitudPruebas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServicioCubo implements IServicioCubo {

    private Cubo cubo;

    private SolicitudPruebas solicitudPruebas;

    private List<CasoDePrueba> listaDeCasoDePrueba;

    private Map<String, List<String>> mapaDeRespuestas = new HashMap<>();

    @Autowired
    private Environment env;

    public ServicioCubo(SolicitudPruebas solicitudPruebas) {
        this.listaDeCasoDePrueba = solicitudPruebas.getListaDeCasosDePrueba();
        this.solicitudPruebas = solicitudPruebas;
    }

    public String UPDATE(String opUPDATE) {
        String[] valorParaHacerUPDATE = opUPDATE.split(" ");
        int x = Integer.parseInt(valorParaHacerUPDATE[1]);
        int y = Integer.parseInt(valorParaHacerUPDATE[2]);
        int z = Integer.parseInt(valorParaHacerUPDATE[3]);
        int valor = Integer.parseInt(valorParaHacerUPDATE[4]);
        System.out.println("Ejecutando " + valorParaHacerUPDATE[0] + "UPDATE (" + x + "," + y + "," + z + ") = " + valor);
        cubo.setValorEnCubo(x - 1, y - 1, z - 1, valor);
        return "UPDATE (" + x + "," + y + "," + z + ") = " + cubo.getValorEnCubo(x - 1, y - 1, z - 1);
    }

    public String QUERY(String opQUERY) {
        String[] valorParaHacerQUERY = opQUERY.split(" ");
        int x0 = Integer.parseInt(valorParaHacerQUERY[1]);
        int y0 = Integer.parseInt(valorParaHacerQUERY[2]);
        int z0 = Integer.parseInt(valorParaHacerQUERY[3]);
        int x1 = Integer.parseInt(valorParaHacerQUERY[4]);
        int y1 = Integer.parseInt(valorParaHacerQUERY[5]);
        int z1 = Integer.parseInt(valorParaHacerQUERY[6]);
        long resultadoSuma = 0;
        for (int i = x0 - 1; i < x1; i++) {
            for (int j = y0 - 1; j < y1; j++) {
                for (int k = z0 - 1; k < z1; k++) {
                    resultadoSuma += this.cubo.getValorEnCubo(i, j, k);
                }
            }
        }
        return "QUERY=" + resultadoSuma;
    }

    public Boolean validarDimensiones(CasoDePrueba caso) {
        if (caso.getDimensiones().matches("\\d+\\s\\d+")) {
            String[] dimensiones = caso.getDimensiones().split(" ");
            if (dimensiones.length > 2 || dimensiones.length < 2) {
                return false;
            } else {
                this.cubo = new Cubo(Integer.parseInt(dimensiones[0]));
                this.cubo.setOperacionesCubo(Integer.parseInt(dimensiones[1]));
                if (this.cubo.getOperacionesCubo() > caso.getOperaciones().size() || this.cubo.getOperacionesCubo() < caso.getOperaciones().size()) {
                    return false;
                } else {
                    return true;
                }
            }
        } else {
            return false;
        }
    }

    private String ejecutarOperacion(String operacion) {
        if (operacion.matches("(?)^UPDATE\\s\\d+\\s\\d+\\s\\d+\\s-?\\d+")) {
            return UPDATE(operacion);
        } else if (operacion.matches("(?)^QUERY\\s\\d+\\s\\d+\\s\\d+\\s\\d+\\s\\d+\\s\\d+")) {
            return QUERY(operacion);
        } else {
            return "Operación no permitida";
        }
    }

    public RespuestaCubo execute() {
        if (this.solicitudPruebas.getPruebas() > Constantes.MAX_PRUEBAS || this.solicitudPruebas.getPruebas() < 1) {
            return new RespuestaCubo("La cantidad de pruebas no es valida", mapaDeRespuestas);
        } else {
            if (this.solicitudPruebas.getPruebas() != this.solicitudPruebas.getListaDeCasosDePrueba().size()) {
                return new RespuestaCubo("La cantidad de pruebas no concuerda con la cantidad de casos de prueba", mapaDeRespuestas);
            } else {
                int numeroDeCaso = 1;
                for (CasoDePrueba caso : this.listaDeCasoDePrueba) {
                    String llaveCaso = "Caso-" + numeroDeCaso;
                    List<String> respuesta = new ArrayList<>();
                    if (validarDimensiones(caso)) {
                        caso.getOperaciones().forEach(
                                operacion -> respuesta.add(ejecutarOperacion(operacion))
                        );
                        mapaDeRespuestas.put(llaveCaso, respuesta);
                    } else {
                        respuesta.add("Las dimensiones no cumplen con los requisitos minimos");
                        System.out.println("Caso ok" + llaveCaso);
                        mapaDeRespuestas.put(llaveCaso, respuesta);
                    }
                    numeroDeCaso++;
                }
                return new RespuestaCubo("Solicitudes procesadas", mapaDeRespuestas);
            }
        }
    }


}