package com.ejemplos.bogarin;

import com.ejemplos.bogarin.dto.Usuario2Dto;
import com.ejemplos.bogarin.dto.UsuarioDto;
import com.ejemplos.bogarin.entities.Usuario;
import com.ejemplos.bogarin.mapper.UsuarioMapper;
import com.ejemplos.bogarin.repositories.UsuarioRepository;

import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BogarinApplicationTests {

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private MockMvc mockMvc;

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13.3")
            .withDatabaseName("bogarin")
            .withUsername("bogarin")
            .withPassword("springboot")
            .withEnv("hibernate.hbm2ddl.auto", "create-drop");

    @BeforeAll
    public static void setUp() {
        postgres.start();

    }

    @AfterAll
    public static void tearDown() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void probando() throws Exception {
        Usuario user = usuarioMapper.usuarioDtoToUsuario(UsuarioDto.builder().firstName("jose")
                .lastName("bogarin")
                .email("exmaple3@example.com")
                .password("123").build());

        userRepository.save(user);
        List<Usuario> resultUser = userRepository.findByFirstName("jose")
                .orElseThrow(() -> new NoSuchElementException("No se encontró el usuario"));

        UsuarioDto userDto = usuarioMapper.usuarioToUsuarioDto(resultUser.get(0));
        assertEquals(user.getFirstName(), userDto.getFirstName());
        assertEquals(user.getLastName(), userDto.getLastName());
    }

    @Test
    void then_use_endpoint_saludo() throws Exception {
        mockMvc.perform(get("/api/saludar"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.saludo", is("Hello, World!")));
    }

    @Test
    void then_use_endpoint_nombre() throws Exception {
        Usuario user = usuarioMapper.usuarioDtoToUsuario(UsuarioDto.builder().firstName("jose")
                .lastName("bogarin")
                .email("exmaple2@example.com")
                .password("123").build());
        userRepository.save(user);

        mockMvc.perform(get("/api/nombre/jose"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", is("jose")));
    }

    @Test
    void then_use_endpoint() throws Exception {
        mockMvc.perform(get("/api/nombre/luna"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is("NOT_FOUND")))
                .andExpect(jsonPath("$.message", is("El usuario con el nombre luna no se encontró.")));
    }

}
