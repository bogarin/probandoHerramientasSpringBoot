package com.ejemplos.bogarin.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ejemplos.bogarin.dto.Usuario2Dto;
import com.ejemplos.bogarin.dto.UsuarioDto;

import jakarta.persistence.PersistenceException;

@Service
public interface UsuarioServices {
    public List<Usuario2Dto> findByFirstName(String firstName);

    public Usuario2Dto findById(Long id);

    public void addUser(UsuarioDto usuarioRequest)throws PersistenceException;

    public List<Usuario2Dto> findByAll();

    public Usuario2Dto upDateUser(Usuario2Dto usuarioRequest);
}
