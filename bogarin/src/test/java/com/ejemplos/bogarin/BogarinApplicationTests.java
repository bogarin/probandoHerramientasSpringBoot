package com.ejemplos.bogarin;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.NoSuchElementException;

import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.MediaType;

import com.ejemplos.bogarin.dto.UsuarioDto;
import com.ejemplos.bogarin.entities.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BogarinApplicationTests extends BaseIntegrationTest {

        @Test
        @Order(1)
        void then_use_endpoint_findByAll2() throws Exception {
                mockMvc.perform(get("/api/usuario"))
                                .andExpect(status().isNotFound())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.status", is("NOT_FOUND")))
                                .andExpect(jsonPath("$.message", is("No se encontró ningún Usuario ")));

        }

        @Test
        @Order(2)
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
        @Order(3)
        void then_use_endpoint_saludo() throws Exception {
                mockMvc.perform(get("/api/usuario/saludar"))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.saludo", is("Hello, World!")));
        }

        @Test
        @Order(4)
        void then_use_endpoint_nombre() throws Exception {
                Usuario user = usuarioMapper.usuarioDtoToUsuario(UsuarioDto.builder().firstName("jose")
                                .lastName("bogarin")
                                .email("exmaple2@example.com")
                                .password("123").build());
                userRepository.save(user);

                mockMvc.perform(get("/api/usuario/nombre/jose"))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.firstName", is("jose")));
        }

        @Test
        @Order(5)
        void then_use_endpoint() throws Exception {
                mockMvc.perform(get("/api/usuario/nombre/luna"))
                                .andExpect(status().isNotFound())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.status", is("NOT_FOUND")))
                                .andExpect(jsonPath("$.message", is("No se encontró el usuario con el nombre luna")));
        }

        @Test
        @Order(6)
        void then_use_endpoint_findById() throws Exception {
                Usuario user = usuarioMapper.usuarioDtoToUsuario(UsuarioDto.builder().firstName("jose")
                                .lastName("bogarin")
                                .email("exmaple4@example.com")
                                .password("123").build());
                userRepository.save(user);

                mockMvc.perform(get("/api/usuario/" + user.getId()))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
                                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
                                .andExpect(jsonPath("$.email", is(user.getEmail())));
        }

        @Test
        @Order(7)
        void then_use_endpoint_findById2() throws Exception {
                mockMvc.perform(get("/api/usuario/" + 100))
                                .andExpect(status().isNotFound())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.status", is("NOT_FOUND")))
                                .andExpect(jsonPath("$.message", is("No se encontró el usuario con el id 100")));
        }

        @Test
        @Order(8)
        void then_use_endpoint_findByAll() throws Exception {
                Usuario user = usuarioMapper.usuarioDtoToUsuario(UsuarioDto.builder().firstName("jose")
                                .lastName("bogarin")
                                .email("exmaple19@example.com")
                                .password("123").build());
                userRepository.save(user);

                List<Usuario> usuario = userRepository.findAll();
                mockMvc.perform(get("/api/usuario"))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.[*]", hasSize(usuario.size())))
                                .andExpect(jsonPath("$.[0].lastName", is(usuario.get(0).getLastName())))
                                .andExpect(jsonPath("$.[0].email", is(usuario.get(0).getEmail())));
        }

        @Test
        @Order(9)
        void then_use_endpoint_createUser() throws Exception {
                JSONObject requestBody = new JSONObject();
                requestBody.put("firstName", "Juan");
                requestBody.put("lastName", "Pérez");
                requestBody.put("email", "juan.perez@example.com");
                requestBody.put("password", "mypassword");
                mockMvc.perform(post("/api/usuario")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody.toString()))
                                .andExpect(status().isOk());
        }

        @Test
        @Order(10)
        void testUpDateUser() throws Exception {
                Usuario user = usuarioMapper.usuarioDtoToUsuario(UsuarioDto.builder().firstName("jose")
                                .lastName("bogarin")
                                .email("exmaple20@example.com")
                                .password("123").build());
                userRepository.save(user);
                // 1. Crear objeto UsuarioDto y convertirlo a JSON
                UsuarioDto usuario = usuarioMapper.usuarioToUsuarioDto(user);

                String usuarioJson = new ObjectMapper().writeValueAsString(usuario);

                // 2. Crear solicitud PUT simulada
                mockMvc.perform(put("/api/usuario/{id}", usuario.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(usuarioJson))
                                // 3. Verificar respuesta exitosa y datos del usuario actualizado
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.id", is(usuario.getId().intValue())))
                                .andExpect(jsonPath("$.firstName", is(usuario.getFirstName())))
                                .andExpect(jsonPath("$.lastName", is(usuario.getLastName())))
                                .andExpect(jsonPath("$.email", is(usuario.getEmail())));

                // 4. Verificar respuesta con error si se intenta actualizar un usuario que no
                // existe
                UsuarioDto usuarioNoExistente = UsuarioDto.builder()
                                .id(999L)
                                .firstName("luna")
                                .lastName("Apellido inválido")
                                .email("invalido@example.com")
                                .password("123")
                                .build();
                String usuarioNoExistenteJson = new ObjectMapper().writeValueAsString(usuarioNoExistente);

                mockMvc.perform(put("/api/usuario/{id}", usuarioNoExistente.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(usuarioNoExistenteJson))
                                .andExpect(status().isNotFound())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.status", is("NOT_FOUND")))
                                .andExpect(jsonPath("$.message",
                                                containsString("No se encontró el usuario con el nombre luna")));
        }

        

   

}
