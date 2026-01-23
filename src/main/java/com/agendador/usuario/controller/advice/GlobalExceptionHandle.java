package com.agendador.usuario.controller.advice;

import com.agendador.usuario.infrastructure.exceptions.ConflictException;
import com.agendador.usuario.infrastructure.exceptions.ErrorResponseDTO;
import com.agendador.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.agendador.usuario.infrastructure.exceptions.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneId;

//Anotação requerida para ativar o nosso Controller Adivice
@ControllerAdvice
public class GlobalExceptionHandle {

    private static final String REGIAO = "America/Sao_Paulo";

    //Anotação para informar que esse metodo é uma exception global
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(
            ResourceNotFoundException ex, HttpServletRequest request
    ){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponseDTO(
                        LocalDateTime.now(ZoneId.of(REGIAO)),
                        "Not Found",
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage(),
                        request.getRequestURI()
                        ));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDTO> handleConflictException(
            ConflictException ex,
            HttpServletRequest request
    ){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ErrorResponseDTO(
                        LocalDateTime.now(ZoneId.of(REGIAO)),
                        "Conflict",
                        HttpStatus.CONFLICT.value(),
                        ex.getMessage(),
                        request.getRequestURI()
                )
        ) ;
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnauthorizedException(
            UnauthorizedException ex,
            HttpServletRequest request
    ){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ErrorResponseDTO(
                        LocalDateTime.now(ZoneId.of(REGIAO)),
                        "Unauthorized",
                        HttpStatus.UNAUTHORIZED.value(),
                        ex.getMessage(),
                        request.getRequestURI()
                )
        );
    }
}
