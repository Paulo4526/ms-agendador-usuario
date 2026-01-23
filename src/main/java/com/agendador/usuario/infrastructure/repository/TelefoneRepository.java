package com.agendador.usuario.infrastructure.repository;


import com.agendador.usuario.infrastructure.entity.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, UUID> {
}
