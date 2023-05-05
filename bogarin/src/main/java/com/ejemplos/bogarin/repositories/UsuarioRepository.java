package com.ejemplos.bogarin.repositories;

import java.util.List;
import java.util.Optional;



import org.springframework.data.jpa.repository.JpaRepository;

import com.ejemplos.bogarin.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario>findAll();
    Optional<Usuario> findById(Long id); 
    Optional<List<Usuario>> findByFirstName(String firstName);
}
