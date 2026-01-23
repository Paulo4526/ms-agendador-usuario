package com.agendador.usuario.controller;

import com.agendador.usuario.business.UsuarioService;
import com.agendador.usuario.controller.dto.endereco.RequestEnderecoDTO;
import com.agendador.usuario.controller.dto.endereco.ResponseEnderecoDTO;
import com.agendador.usuario.controller.dto.login.RequestLoginDTO;
import com.agendador.usuario.controller.dto.login.ResponseTokenDTO;
import com.agendador.usuario.controller.dto.telefone.RequestTelefoneDTO;
import com.agendador.usuario.controller.dto.telefone.ResponseTelefoneDTO;
import com.agendador.usuario.controller.dto.usuario.RequestUsuarioDTO;
import com.agendador.usuario.controller.dto.usuario.ResponseUsuarioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;


    @PostMapping
    public ResponseEntity<ResponseUsuarioDTO> salvaUsuario(@RequestBody RequestUsuarioDTO requestUsuarioDTO){
        ResponseUsuarioDTO salvo = usuarioService.salvaUsuario(requestUsuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseTokenDTO> login(@RequestBody RequestLoginDTO requestLoginDTO){
        return ResponseEntity.ok(usuarioService.autenticaUsuario(requestLoginDTO));
    }

    @GetMapping()
    public ResponseEntity<ResponseUsuarioDTO> findUserByEmail(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.findUserByEmail(token));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUserByEmail(@PathVariable String email){
        usuarioService.deleteUserByEmail(email);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<ResponseUsuarioDTO> atualizaUsuariov(@RequestHeader("Authorization") String token, @RequestBody RequestUsuarioDTO requestUsuarioDTO){
        return ResponseEntity.ok(usuarioService.atualizaDadosUsuario(token, requestUsuarioDTO));
    }

    @PutMapping("/endereco")
    public ResponseEntity<ResponseEnderecoDTO> atualizaEndereco(@RequestBody RequestEnderecoDTO requestEnderecoDTO, @RequestParam("enderecoId") UUID id){
        return ResponseEntity.ok(usuarioService.atualizandoEnderedo(id, requestEnderecoDTO));
    }

    @GetMapping("/endereco")
    public ResponseEntity<ResponseEnderecoDTO> buscaEnderecoById(@RequestParam("enderecoId") UUID id){
        return ResponseEntity.ok(usuarioService.buscaEnderecoById(id));
    }

    @DeleteMapping("/endereco")
    public ResponseEntity<Void> deleteEnderecoById(@RequestParam("enderecoId") UUID id){
        usuarioService.deleteEnderecoById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/endereco")
    public ResponseEntity<ResponseEnderecoDTO> cadastraNovoEndereco(@RequestHeader("Authorization") String token, @RequestBody RequestEnderecoDTO requestEnderecoDTO){
        ResponseEnderecoDTO salvo = usuarioService.cadastraNovoEndereco(token, requestEnderecoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/telefone")
    public ResponseEntity<ResponseTelefoneDTO> atualizaTelefone(@RequestBody RequestTelefoneDTO requestTelefoneDTO, @RequestParam("telefoneId") UUID id){
        return ResponseEntity.ok(usuarioService.atualizandoTelefone(id, requestTelefoneDTO));
    }

    @GetMapping("/telefone")
    public ResponseEntity<ResponseTelefoneDTO> buscaTelefoneById(@RequestParam("telefoneId") UUID id){
        return ResponseEntity.ok(usuarioService.buscaTelefoneById(id));
    }

    @DeleteMapping("/telefone")
    public ResponseEntity<Void> deleteTelefoneById(@RequestParam("telefoneId") UUID id){
        usuarioService.deleteTelefoneBydId(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/telefone")
    public ResponseEntity<ResponseTelefoneDTO> cadastraNovoTelefone(@RequestHeader("Authorization") String token, @RequestBody RequestTelefoneDTO requestTelefoneDTO){
        ResponseTelefoneDTO salvo = usuarioService.cadastraNovoTelefone(token, requestTelefoneDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

}
