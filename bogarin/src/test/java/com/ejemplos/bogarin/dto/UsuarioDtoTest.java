package com.ejemplos.bogarin.dto;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ejemplos.bogarin.dto.UsuarioDto.UsuarioDtoBuilder;
import com.ejemplos.bogarin.entities.Usuario;
import com.ejemplos.bogarin.mapper.UsuarioMapper;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
class UsuarioDtoTest {

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Test
    void probado1() {
        Long valor = 1L;
        UsuarioDto userDto = new UsuarioDto();
        userDto.setId(valor);
        userDto.setFirstName("jose");
        userDto.setLastName("bogarin");
        userDto.setEmail("exmaple3@example.com");
        userDto.setPassword("123");

        Usuario user = usuarioMapper.usuarioDtoToUsuario(userDto);
        assertEquals(valor, userDto.getId());
        assertEquals(null, user.getId());
        assertEquals(user.getFirstName(), userDto.getFirstName());
        assertEquals(user.getLastName(), userDto.getLastName());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getPassword(), userDto.getPassword());

    }

    @Test
    void probado2() {
        Long valor = 1L;
        UsuarioDtoBuilder builder = UsuarioDto.builder()
                .id(valor)
                .firstName("jose")
                .lastName("bogarin")
                .email("exmaple3@example.com")
                .password("123");
        String result = builder.toString();
        log.info(result);
        assertEquals("UsuarioDto.UsuarioDtoBuilder(id=1, firstName=jose, lastName=bogarin, email=exmaple3@example.com, password=123)", result);

    }

}
