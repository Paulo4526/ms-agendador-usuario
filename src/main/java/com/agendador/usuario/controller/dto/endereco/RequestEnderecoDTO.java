package com.agendador.usuario.controller.dto.endereco;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestEnderecoDTO {

    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private Integer numero;
    private String complemento;
}
