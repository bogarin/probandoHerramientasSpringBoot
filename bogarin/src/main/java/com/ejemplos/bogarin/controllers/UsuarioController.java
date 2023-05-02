package com.ejemplos.bogarin.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplos.bogarin.dto.SaludoDto;
import com.ejemplos.bogarin.dto.Usuario2Dto;
import com.ejemplos.bogarin.response.ErrorResponse;
import com.ejemplos.bogarin.services.UsuarioServices;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    UsuarioServices usuarioServices;

    @GetMapping("/saludar")
    public ResponseEntity<SaludoDto> hello() {
        SaludoDto saludo = SaludoDto.builder().saludo("Hello, World!").build();
        return ResponseEntity.ok(saludo);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Object> busquedaNombre(@PathVariable String nombre) {
        try {
            List<Usuario2Dto> usuarios = usuarioServices.findByFirstName(nombre);
            return ResponseEntity.ok(usuarios.get(0));
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        String errorMessage = "El usuario con el nombre " + nombre + " no se encontró.";
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, errorMessage);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
