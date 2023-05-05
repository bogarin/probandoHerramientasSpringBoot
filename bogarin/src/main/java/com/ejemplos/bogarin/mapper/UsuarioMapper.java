package com.ejemplos.bogarin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ejemplos.bogarin.dto.Usuario2Dto;
import com.ejemplos.bogarin.dto.UsuarioDto;
import com.ejemplos.bogarin.entities.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    
    public UsuarioDto usuarioToUsuarioDto(Usuario usuario);
    
  
    public Usuario2Dto usuarioToUsuario2Dto(Usuario usuario);

    @Mapping(target = "id", ignore = true)
    public Usuario usuarioDtoToUsuario(UsuarioDto usuarioDto);

    @Mapping(target = "password", ignore = true)
    public Usuario usuarioToUsuario2Dto(Usuario2Dto usuario);
}
