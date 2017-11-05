package co.com.juan.test;

import co.com.juan.test.entidades.CasoDePrueba;
import co.com.juan.test.entidades.SolicitudPruebas;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class CuboTests {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void caminoFeliz() throws Exception {
        List<CasoDePrueba> listaDeCasosDePrueba = new ArrayList<>();
        List<String> operaciones = new ArrayList<>();
        operaciones.add("UPDATE 2 2 2 4");
        operaciones.add("QUERY 1 1 1 3 3 3");
        operaciones.add("UPDATE 1 1 1 23");
        operaciones.add("QUERY 2 2 2 4 4 4");
        operaciones.add("QUERY 1 1 1 3 3 3");
        CasoDePrueba casoDePrueba = new CasoDePrueba("4 5",operaciones);
        listaDeCasosDePrueba.add(casoDePrueba);
        mockMvc.perform(post("/opcubo")
                .content(this.json(new SolicitudPruebas(1,listaDeCasosDePrueba)))
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mesaje", Matchers.is("Solicitudes procesadas")))
        ;
    }

    @Test
    public void errorCantidadPruebas() throws Exception {
        List<CasoDePrueba> listaDeCasosDePrueba = new ArrayList<>();
        List<String> operaciones = new ArrayList<>();
        operaciones.add("UPDATE 2 2 2 4");
        operaciones.add("QUERY 1 1 1 3 3 3");
        operaciones.add("UPDATE 1 1 1 23");
        operaciones.add("QUERY 2 2 2 4 4 4");
        operaciones.add("QUERY 1 1 1 3 3 3");
        CasoDePrueba casoDePrueba = new CasoDePrueba("4 5",operaciones);
        listaDeCasosDePrueba.add(casoDePrueba);
        mockMvc.perform(post("/opcubo")
                .content(this.json(new SolicitudPruebas(100,listaDeCasosDePrueba)))
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mesaje", Matchers.is("La cantidad de pruebas no es valida")))
        ;
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        System.out.println("JSON para prueba: " + mockHttpOutputMessage.getBodyAsString());
        return mockHttpOutputMessage.getBodyAsString();
    }
}