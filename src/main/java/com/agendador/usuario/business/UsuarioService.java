package com.agendador.usuario.business;

import com.agendador.usuario.controller.dto.endereco.RequestEnderecoDTO;
import com.agendador.usuario.controller.dto.endereco.ResponseEnderecoDTO;
import com.agendador.usuario.controller.dto.login.RequestLoginDTO;
import com.agendador.usuario.controller.dto.login.ResponseTokenDTO;
import com.agendador.usuario.controller.dto.telefone.ResponseTelefoneDTO;
import com.agendador.usuario.controller.dto.telefone.RequestTelefoneDTO;
import com.agendador.usuario.controller.dto.usuario.ResponseUsuarioDTO;
import com.agendador.usuario.controller.dto.usuario.RequestUsuarioDTO;
import com.agendador.usuario.controller.converter.UsuarioConverter;
import com.agendador.usuario.infrastructure.entity.Endereco;
import com.agendador.usuario.infrastructure.entity.Telefone;
import com.agendador.usuario.infrastructure.entity.Usuario;
import com.agendador.usuario.infrastructure.exceptions.ConflictException;
import com.agendador.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.agendador.usuario.infrastructure.exceptions.UnauthorizedException;
import com.agendador.usuario.infrastructure.repository.EnderecoRepository;
import com.agendador.usuario.infrastructure.repository.TelefoneRepository;
import com.agendador.usuario.infrastructure.repository.UsuarioRepository;
import com.agendador.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public ResponseUsuarioDTO salvaUsuario(RequestUsuarioDTO requestUsuarioDTO){
        try{
            //Verifica se o e-mail existe
            emailExiste(requestUsuarioDTO.getEmail());
            //Recebe os valores do usuário DTO e copia para a entidade Usuário
            Usuario usuario = usuarioConverter.paraUsuario(requestUsuarioDTO);
            //Salva os dados recebidos pela entidade DTO no banco de dados
            usuario = usuarioRepository.save(usuario);
            //Retorna o ShowUsuarioDTO onde não mostrará a senha do usuário
            return new ResponseUsuarioDTO(usuario);

        }catch(ConflictException e){
            throw new ConflictException("Email já existente: " + requestUsuarioDTO.getEmail());
        }
    }

    //Funcionalidade que verifica dentro do banco de dados se o email é existente
    public boolean verificaEmailExiste(String email){
        return usuarioRepository.existsByEmail(email);
    }

    //Funcionalidade que chama o metodo verificaEmail e faz os tratamentos necessários
    public void emailExiste(String email){
        try {
            //Verifica se o e-mail já existe
            boolean existe = verificaEmailExiste(email);
            //Condicional que verifica, caso e-mail exista gerará uma Exception de conflito personalizada. Caso de erro entratrá no catch se não seguira normalmente.
            if (existe){
                throw new ConflictException("Email já cadastrado!");
            }
        }catch (ConflictException e){
            throw new ConflictException("Email já cadastrado: " + email);
        }
    }

    //Metodo de autenticação de usuário
    public ResponseTokenDTO autenticaUsuario(RequestLoginDTO requestLoginDTO){
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestLoginDTO.getEmail(), requestLoginDTO.getSenha())
            );
            return new ResponseTokenDTO(jwtUtil.generateToken(authentication.getName()));
        } catch (BadCredentialsException | UnauthorizedException | AuthorizationDeniedException e) {
            throw new UnauthorizedException("Usuário não autorizado, por favor preencha os dados validos para autenticação! ", e.getCause());
        }

    }

    //Metodo que deleta o usuário pelo e-mail também podemos utilizar pelo id.
    public void deleteUserByEmail(String email){
        try{
            if (!verificaEmailExiste(email)){
                throw new ResourceNotFoundException("Email não encontrado");
            }
            usuarioRepository.existsByEmail(email);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Usuário não encontrado: " + email);
        }
    }

    //Metodo que procura os dados do usuário pelo e-mail, também é possivel procurar pelo id.
    //OBS: As opções de busca são personalizadas de acordo com a necessidade e regra de negócio, podendo ser buscado por identificadores unicos como e-mail, cpf, rg, id e etc.
    public ResponseUsuarioDTO findUserByEmail(String token){
        String email = jwtUtil.extractUsername(token.substring(7));
        return new ResponseUsuarioDTO(usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email de usuário não encontrado!")));
    }

    //Metodo que atualiza os dados do usuário.
    public ResponseUsuarioDTO atualizaDadosUsuario(String token, RequestUsuarioDTO requestUsuarioDTO){
        //Variavel que receberá o e-mail do metodo extractUserName, onde será passado o token e será extraido o email.
        String email = jwtUtil.extractUsername(token.substring(7));
        //Construtor que recebe os dados do usuário após a busca por e-mail no banco de dados pelo metodo findByEmail no repository
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não localizado!"));
        //Passando os valores recebidos pelo usuarioDTO na requisição e os valores recebidos da entity, onde será feito a comparação dentro do metodo updateUsuario
        //Caso o usuario altere qualquer valor sendo senha, email, idade, ou nome será passado um novo valor para entidade antes de ser salvo no bancod e dados
        Usuario usuario = usuarioConverter.updateUsuario(requestUsuarioDTO, usuarioEntity);
        return new ResponseUsuarioDTO(usuarioRepository.save(usuario));
    }

    public ResponseEnderecoDTO atualizandoEnderedo(UUID id, RequestEnderecoDTO requestEnderecoDTO){
        Endereco enderecoEntity = enderecoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID do endereço não encontrado! " + id));
        Endereco endereco = usuarioConverter.updateEndereco(requestEnderecoDTO, enderecoEntity);
        return new ResponseEnderecoDTO(enderecoRepository.save(endereco));
    }

    public ResponseEnderecoDTO buscaEnderecoById(UUID id){
        Endereco enderecoEntity = enderecoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID do endereço não encontrado " + id));
        return new ResponseEnderecoDTO(enderecoEntity);
    }

    public void deleteEnderecoById(UUID id){
        enderecoRepository.deleteById(id);
    }

    public ResponseEnderecoDTO cadastraNovoEndereco(String token, RequestEnderecoDTO requestEnderecoDTO){

        String email = jwtUtil.extractUsername(token.substring(7));

        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email de usuario nao encontrado " + email));

        Endereco endereco = usuarioConverter.paraEnderecoEntity(usuario.getId(), requestEnderecoDTO);

        return new ResponseEnderecoDTO(enderecoRepository.save(endereco));

    }

    public ResponseTelefoneDTO atualizandoTelefone(UUID id, RequestTelefoneDTO requestTelefoneDTO){
        Telefone telefoneEntity = telefoneRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID do telefone não encontrado " + id));
        Telefone telefone = usuarioConverter.updateTelefone(requestTelefoneDTO, telefoneEntity);
        return new ResponseTelefoneDTO(telefoneRepository.save(telefone));
    }

    public ResponseTelefoneDTO buscaTelefoneById(UUID id){
        Telefone telefoneEntity = telefoneRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID do telefone não encontrado " + id));
        return new ResponseTelefoneDTO(telefoneEntity);
    }

    public void deleteTelefoneBydId(UUID id){
        telefoneRepository.deleteById(id);
    }

    public ResponseTelefoneDTO cadastraNovoTelefone(String token, RequestTelefoneDTO requestTelefoneDTO){
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email de usuário não encontrado " + email));
        Telefone telefone = usuarioConverter.paraTelefoneEntity(usuario.getId(), requestTelefoneDTO);
        return new ResponseTelefoneDTO(telefoneRepository.save(telefone));
    }

}
