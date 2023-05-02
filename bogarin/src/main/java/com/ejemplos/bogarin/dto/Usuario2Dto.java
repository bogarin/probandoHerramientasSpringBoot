package com.ejemplos.bogarin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario2Dto {

    private Long id;
    
    private String firstName;

    private String lastName;

    private String email;
}
