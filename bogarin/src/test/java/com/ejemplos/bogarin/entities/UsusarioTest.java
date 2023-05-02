package com.ejemplos.bogarin.entities;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ejemplos.bogarin.dto.UsuarioDto;
import com.ejemplos.bogarin.entities.Usuario.UsuarioBuilder;
import com.ejemplos.bogarin.mapper.UsuarioMapper;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
class UsusarioTest {

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Test
    void probando1() {
        Usuario user = new Usuario();
        user.setId(1L);
        user.setFirstName("jose");
        user.setLastName("bogarin");
        user.setEmail("exa@exa.com");
        user.setPassword("123");
        UsuarioDto userDto = usuarioMapper.usuarioToUsuarioDto(user);
        assertEquals(user.getFirstName(), userDto.getFirstName());
        assertEquals(user.getLastName(), userDto.getLastName());
    }

    @Test
    void probando2() {
        UsuarioBuilder builder = Usuario.builder()
                .id(1L)
                .firstName("jose")
                .lastName("bogarin")
                .email("exa@exa.com")
                .password("123");
        String result = builder.toString();
        log.info(result);
        assertEquals("Usuario.UsuarioBuilder(id=1, firstName=jose, lastName=bogarin, email=exa@exa.com, password=123)",
                result);

    }
}
