package com.ejemplos.bogarin.utils;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Message {

        public static final UnaryOperator<String> msgConsulUserError = context -> "No se encontró el usuario con el "
                        + context;

        public static final Supplier<String> msgConsulUserAllError = () -> "No se encontró ningún Usuario ";

        public static final UnaryOperator<String> msgInsertUserError = context -> "No se pudo agregar el usuario con el "
                        + context;

        public static final Function<String, JsonNode> msgSuccessSaveUser = name -> {
                var factory = JsonNodeFactory.instance;
                return factory.objectNode()
                                .put("status", HttpStatus.OK.value())
                                .put("message", "ingreso con éxito el usuario: " + name);
        };
}
