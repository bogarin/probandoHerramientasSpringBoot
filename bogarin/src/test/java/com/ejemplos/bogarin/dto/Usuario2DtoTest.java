package com.ejemplos.bogarin.dto;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ejemplos.bogarin.dto.Usuario2Dto.Usuario2DtoBuilder;
import com.ejemplos.bogarin.entities.Usuario;
import com.ejemplos.bogarin.mapper.UsuarioMapper;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
 class Usuario2DtoTest {

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Test
    void probado1() {
        Long valor = 1L;
        Usuario2Dto userDto = new Usuario2Dto();
        userDto.setId(valor);
        userDto.setFirstName("jose");
        userDto.setLastName("bogarin");
        userDto.setEmail("exmaple3@example.com");

        Usuario user = usuarioMapper.usuarioToUsuario2Dto(userDto);
        assertEquals(valor, userDto.getId());
        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getFirstName(), userDto.getFirstName());
        assertEquals(user.getLastName(), userDto.getLastName());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(null, user.getPassword());

    }

    @Test
    void probado2()  {
        Long valor = 1L;
        Usuario2DtoBuilder builder = Usuario2Dto.builder()
        .id(valor)
        .firstName("jose")
        .lastName("bogarin")
        .email("exmaple3@example.com");
        String result = builder.toString();
        log.info(result);
        assertEquals("Usuario2Dto.Usuario2DtoBuilder(id=1, firstName=jose, lastName=bogarin, email=exmaple3@example.com)", result);


    }
}
