package com.ejemplos.bogarin.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplos.bogarin.dto.SaludoDto;
import com.ejemplos.bogarin.dto.Usuario2Dto;
import com.ejemplos.bogarin.dto.UsuarioDto;
import com.ejemplos.bogarin.response.ErrorResponse;
import com.ejemplos.bogarin.services.UsuarioServices;
import com.ejemplos.bogarin.utils.Message;

import jakarta.persistence.PersistenceException;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    UsuarioServices usuarioServices;
    private static final String MESSAGE_USER_TYPE = "nombre ";

    @GetMapping("/saludar")
    public ResponseEntity<SaludoDto> hello() {
        SaludoDto saludo = SaludoDto.builder().saludo("Hello, World!").build();
        return ResponseEntity.ok(saludo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        try {
            Usuario2Dto usuario = usuarioServices.findById(id);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND,
                Message.msgConsulUserError.apply("id " + id));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Object> findByName(@PathVariable String nombre) {
        try {
            List<Usuario2Dto> usuarios = usuarioServices.findByFirstName(nombre);
            return ResponseEntity.ok(usuarios.get(0));
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND,
                Message.msgConsulUserError.apply(MESSAGE_USER_TYPE + nombre));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @GetMapping
    public ResponseEntity<Object> findByAll() {
        try {
            List<Usuario2Dto> usuarios = usuarioServices.findByAll();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND,
                Message.msgConsulUserAllError.get());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody UsuarioDto usuarioRequest) {
        try {
            usuarioServices.addUser(usuarioRequest);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Message.msgSuccessSaveUser.apply(usuarioRequest.getFirstName()));
        } catch (PersistenceException e) {
            log.info(e.getMessage());
        }
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND,
                Message.msgInsertUserError.apply(MESSAGE_USER_TYPE + usuarioRequest.getFirstName()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> upDateUser(@RequestBody Usuario2Dto usuarioRequest) {
        try {
            return ResponseEntity.ok(usuarioServices.upDateUser(usuarioRequest));
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND,
                Message.msgConsulUserError.apply(MESSAGE_USER_TYPE + usuarioRequest.getFirstName()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
