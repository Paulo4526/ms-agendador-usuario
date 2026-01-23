package com.agendador.usuario.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

//Utilizando o Lombok
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//Utilizando o JPA(Java Persistence API) para persistencia de dados no banco de dados
@Entity
@Table(name = "endereco")
@Builder
public class Endereco {

    //Criando o atributo e suas especificidades que serão criadas na tabela do nosso banco de dados
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "rua", nullable = false, length = 100)
    private String rua;
    @Column(name = "bairro", nullable = false, length = 100)
    private String bairro;
    @Column(name = "cidade", nullable = false, length = 100)
    private String cidade;
    @Column(name = "estado", nullable = false, length = 100)
    private String estado;
    @Column(name = "cep", nullable = false, length = 20)
    private String cep;
    @Column(name = "numero", nullable = false)
    private Integer numero;
    @Column(name = "complemento", nullable = true, length = 100)
    private String complemento;
    @Column(name = "user_id")
    private UUID user_id;
}
