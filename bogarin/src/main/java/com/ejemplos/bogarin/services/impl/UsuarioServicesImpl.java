package com.ejemplos.bogarin.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.ejemplos.bogarin.dto.Usuario2Dto;
import com.ejemplos.bogarin.dto.UsuarioDto;
import com.ejemplos.bogarin.entities.Usuario;
import com.ejemplos.bogarin.mapper.UsuarioMapper;
import com.ejemplos.bogarin.repositories.UsuarioRepository;
import com.ejemplos.bogarin.services.UsuarioServices;

import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServicesImpl implements UsuarioServices {

    private final UsuarioRepository repository;

    private final UsuarioMapper usuarioMapper;
    private static final String MESSAGE_FOR_NAME="No se encontró el usuario";

    public List<Usuario2Dto> findByFirstName(String firstName) {
        List<Usuario> usuarios = repository.findByFirstName(firstName).orElse(Collections.emptyList());
        if (usuarios.isEmpty()) {
            throw new NoSuchElementException(MESSAGE_FOR_NAME);
        }
        return usuarios.stream()
                .map(usuarioMapper::usuarioToUsuario2Dto).toList();
    }

    @Override
    public Usuario2Dto findById(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(MESSAGE_FOR_NAME));
        return usuarioMapper.usuarioToUsuario2Dto(usuario);
    }

    @Override
    public void addUser(UsuarioDto usuarioRequest) throws PersistenceException {
        repository.save(usuarioMapper.usuarioDtoToUsuario(usuarioRequest));

    }

    @Override
    public List<Usuario2Dto> findByAll() {
        List<Usuario> usuarios = repository.findAll();
        if (usuarios.isEmpty()) {
            throw new NoSuchElementException("No se encontró ningún usuario");
        }
        return usuarios.stream()
                .map(usuarioMapper::usuarioToUsuario2Dto).toList();
    }

    @Override
    public Usuario2Dto upDateUser(Usuario2Dto usuarioRequest) {
        Usuario usuario = repository.findById(usuarioRequest.getId())
                .orElseThrow(() -> new NoSuchElementException(MESSAGE_FOR_NAME));
        usuario.setFirstName(usuarioRequest.getFirstName());
        usuario.setLastName(usuarioRequest.getLastName());
        usuario.setEmail(usuarioRequest.getEmail());
        repository.save(usuario);
        return usuarioMapper.usuarioToUsuario2Dto(usuario);
    }

}