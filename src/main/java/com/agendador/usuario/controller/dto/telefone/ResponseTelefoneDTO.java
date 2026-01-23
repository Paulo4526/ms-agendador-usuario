package com.agendador.usuario.controller.dto.telefone;

import com.agendador.usuario.infrastructure.entity.Telefone;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseTelefoneDTO {
    private UUID id;
    private Integer numero;
    private Integer ddd;

    public ResponseTelefoneDTO(Telefone telefone){
        this(telefone.getId(), telefone.getNumero(), telefone.getDdd());
    }
}
