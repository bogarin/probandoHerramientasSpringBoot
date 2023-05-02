package com.ejemplos.bogarin.dto;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.ejemplos.bogarin.dto.SaludoDto.SaludoDtoBuilder;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
class SaludoDtoTest {
    @Test
    void probado1() {
        String mensaje = "hola mundo";
        SaludoDto sdto = new SaludoDto();
        sdto.setSaludo(mensaje);
        assertEquals(sdto.getSaludo(), mensaje);
    }

    @Test
    void probado2()  {
        String mensaje = "hola mundo";
        SaludoDto sdto = SaludoDto.builder().saludo(mensaje).build();
        assertEquals(sdto.getSaludo(), mensaje);
    }
    @Test
    void probado3()  {
        String mensaje = "hola mundo";
        SaludoDtoBuilder builder = SaludoDto.builder().saludo(mensaje);
        String result = builder.toString();
        log.info(result);
        assertEquals("SaludoDto.SaludoDtoBuilder(saludo=hola mundo)", result);
    }
}
