package com.ejemplos.bogarin.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ejemplos.bogarin.dto.Usuario2Dto;

@Service
public interface UsuarioServices {
    public List<Usuario2Dto> findByFirstName(String firstName);
}
