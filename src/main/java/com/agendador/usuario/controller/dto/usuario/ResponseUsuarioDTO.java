package com.agendador.usuario.controller.dto.usuario;

import com.agendador.usuario.controller.dto.endereco.ResponseEnderecoDTO;
import com.agendador.usuario.controller.dto.telefone.ResponseTelefoneDTO;
import com.agendador.usuario.infrastructure.entity.Usuario;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseUsuarioDTO {

    private UUID id;
    private String nome;
    private String email;
    private String senha;
    private Integer idade;
    private List<ResponseEnderecoDTO> enderecos;
    private List<ResponseTelefoneDTO> telefones;

    public ResponseUsuarioDTO(Usuario usuario){
        this(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.getIdade(),
                usuario.getEnderecos() == null
                        ? List.of()
                        : usuario.getEnderecos()
                        .stream()
                        .map(ResponseEnderecoDTO::new)
                        .toList(),
                usuario.getTelefones() == null
                        ? List.of()
                        : usuario.getTelefones()
                        .stream()
                        .map(ResponseTelefoneDTO::new)
                        .toList()
        );
    }
}