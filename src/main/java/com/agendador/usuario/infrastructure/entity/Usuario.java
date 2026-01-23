package com.agendador.usuario.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

//Utilizando o Lombok
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//Utilizando o JPA(Java Persistence API) para persistencia de dados no banco de dados
@Entity
@Table(name = "usuario")
//Após baixar a dependencia do Spring Security devemos implementar 3 metodos obrigatórios
@Builder
public class Usuario implements UserDetails {

    //Criando o atributo e suas especificidades que serão criadas na tabela do nosso banco de dados
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;
    @Column(name = "senha", nullable = false)
    private String senha;
    @Column(name = "idade", nullable = false)
    private Integer idade;
    //Tipo de Relacionamento um para muitos
    @OneToMany(cascade = CascadeType.ALL)
    //Criando o relacionamento entre as tabelas
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
    private transient List<Endereco> enderecos;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
    private transient List<Telefone> telefones;

    //Secutiry Metodo obrigatorio 1
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    //Secutiry Metodo obrigatorio 2
    @Override
    public String getPassword() {
        return senha; //Quando implementar o metodo informar qual o atributo será utilizado como senha de autenticação
    }

    //Secutiry Metodo obrigatorio 3
    @Override
    public String getUsername() {
        return email; //Quando implementar o metodo informar qual atributo será utilizado como usuario de autenticação
    }
}
