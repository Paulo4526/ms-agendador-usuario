package com.agendador.usuario.controller.converter;

import com.agendador.usuario.controller.dto.endereco.RequestEnderecoDTO;
import com.agendador.usuario.controller.dto.telefone.RequestTelefoneDTO;
import com.agendador.usuario.controller.dto.usuario.RequestUsuarioDTO;
import com.agendador.usuario.infrastructure.entity.Endereco;
import com.agendador.usuario.infrastructure.entity.Telefone;
import com.agendador.usuario.infrastructure.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//OBS: Esta maneira é feita devido não haver as funcionalidades da classe record no java 11, então neste microserviço será feito com metodos converter para salvar os dados nas entidades de Usuario e ShowUsuarioDTO
@Component
@RequiredArgsConstructor
public class UsuarioConverter {

    private final PasswordEncoder passwordEncoder;

    public Usuario paraUsuario(RequestUsuarioDTO requestUsuarioDTO){
        return Usuario.builder()
                .nome(requestUsuarioDTO.getNome())
                .email(requestUsuarioDTO.getEmail())
                .senha(encodePassword(requestUsuarioDTO.getSenha()))
                .idade(requestUsuarioDTO.getIdade())
                .enderecos(requestUsuarioDTO.getEnderecos() != null ? paraListaEndereco(requestUsuarioDTO.getEnderecos()) : null)
                .telefones(requestUsuarioDTO.getTelefones() != null ? paraListaTelefone(requestUsuarioDTO.getTelefones()) : null)
                .build();
    }

    public Usuario updateUsuario(RequestUsuarioDTO requestUsuarioDTO, Usuario entity){
        return Usuario.builder()
                .id(entity.getId())
                .nome(requestUsuarioDTO.getNome() != null ? requestUsuarioDTO.getNome() : entity.getNome())
                .senha(requestUsuarioDTO.getSenha() != null ? encodePassword(requestUsuarioDTO.getSenha()) : entity.getSenha())
                .email(requestUsuarioDTO.getEmail() != null ? requestUsuarioDTO.getEmail() : entity.getEmail())
                .idade(requestUsuarioDTO.getIdade() != null ? requestUsuarioDTO.getIdade() : entity.getIdade())
                .enderecos(entity.getEnderecos())
                .telefones(entity.getTelefones())
                .build();

    }

    //Cria endereco quando o usuario for criado
    public Endereco paraEndereco(RequestEnderecoDTO requestEnderecoDTO){
        return Endereco.builder()
                .rua(requestEnderecoDTO.getRua())
                .bairro(requestEnderecoDTO.getBairro())
                .cidade(requestEnderecoDTO.getCidade())
                .estado(requestEnderecoDTO.getEstado())
                .cep(requestEnderecoDTO.getCep())
                .numero(requestEnderecoDTO.getNumero())
                .complemento(requestEnderecoDTO.getComplemento())
                .build();
    }

    //Adiciona novos endereços após a criação do usuario
    public Endereco paraEnderecoEntity(UUID id, RequestEnderecoDTO requestEnderecoDTO){
        return Endereco.builder()
                .rua(requestEnderecoDTO.getRua())
                .bairro(requestEnderecoDTO.getBairro())
                .cidade(requestEnderecoDTO.getCidade())
                .estado(requestEnderecoDTO.getEstado())
                .cep(requestEnderecoDTO.getCep())
                .numero(requestEnderecoDTO.getNumero())
                .complemento(requestEnderecoDTO.getComplemento())
                .user_id(id)
                .build();
    }

    //Iteração com stream().map()
    public List<Endereco> paraListaEndereco(List<RequestEnderecoDTO> requestEnderecoDTO){
        return requestEnderecoDTO.stream().map(this::paraEndereco).toList();
    }

    public Endereco updateEndereco(RequestEnderecoDTO requestEnderecoDTO, Endereco endereco){
        return Endereco.builder()
                .id(endereco.getId())
                .rua(requestEnderecoDTO.getRua() != null ? requestEnderecoDTO.getRua() : endereco.getRua())
                .bairro(requestEnderecoDTO.getBairro() != null ? requestEnderecoDTO.getBairro() : endereco.getBairro())
                .cidade(requestEnderecoDTO.getCidade() != null ? requestEnderecoDTO.getCidade() : endereco.getCidade())
                .estado(requestEnderecoDTO.getEstado() != null ? requestEnderecoDTO.getEstado() : endereco.getEstado())
                .cep(requestEnderecoDTO.getCep() != null ? requestEnderecoDTO.getCep() : endereco.getCep())
                .numero(requestEnderecoDTO.getNumero() != null ? requestEnderecoDTO.getNumero() : endereco.getNumero())
                .complemento(requestEnderecoDTO.getComplemento() != null ? requestEnderecoDTO.getComplemento() : endereco.getComplemento())
                .user_id(endereco.getUser_id())
                .build();
    }

    public Telefone updateTelefone(RequestTelefoneDTO requestTelefoneDTO, Telefone telefone){
        return Telefone.builder()
                .id(telefone.getId())
                .ddd(requestTelefoneDTO.getDdd() != null ? requestTelefoneDTO.getDdd() : telefone.getDdd())
                .numero(requestTelefoneDTO.getNumero() != null ? requestTelefoneDTO.getNumero() : telefone.getNumero())
                .user_id(telefone.getUser_id())
                .build();
    }

    public Telefone paraTelefone(RequestTelefoneDTO requestTelefoneDTO){
        return Telefone.builder()
                .ddd(requestTelefoneDTO.getDdd())
                .numero(requestTelefoneDTO.getNumero())
                .build();
    }

    public Telefone paraTelefoneEntity(UUID id, RequestTelefoneDTO requestTelefoneDTO){
        return Telefone.builder()
                .ddd(requestTelefoneDTO.getDdd())
                .numero(requestTelefoneDTO.getNumero())
                .user_id(id)
                .build();
    }

    //Iteração em metodo for
    public List<Telefone> paraListaTelefone(List<RequestTelefoneDTO> requestTelefoneDTOS){
        List<Telefone> telefones = new ArrayList<>();
        for(RequestTelefoneDTO requestTelefoneDTO : requestTelefoneDTOS){
            telefones.add(paraTelefone(requestTelefoneDTO));
        }

        return telefones;
    }



    //Metodo que escriptara o password
    public String encodePassword(String password){
        return passwordEncoder.encode(password);
    }





}
