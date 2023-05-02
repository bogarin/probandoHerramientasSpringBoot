package com.ejemplos.bogarin.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.ejemplos.bogarin.dto.Usuario2Dto;
import com.ejemplos.bogarin.entities.Usuario;
import com.ejemplos.bogarin.mapper.UsuarioMapper;
import com.ejemplos.bogarin.repositories.UsuarioRepository;
import com.ejemplos.bogarin.services.UsuarioServices;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServicesImpl implements UsuarioServices {

    private final UsuarioRepository repository;

    private final UsuarioMapper usuarioMapper;

    public List<Usuario2Dto> findByFirstName(String firstName) {
        List<Usuario> usuarios = repository.findByFirstName(firstName).orElse(Collections.emptyList());
        if (usuarios.isEmpty()) {
            throw new NoSuchElementException("No se encontró el usuario");
        }
        return usuarios.stream()
                .map(usuarioMapper::usuarioToUsuario2Dto).toList();
    }

}