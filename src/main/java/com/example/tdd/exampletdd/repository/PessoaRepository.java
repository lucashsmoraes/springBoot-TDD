package com.example.tdd.exampletdd.repository;

import com.example.tdd.exampletdd.domain.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Optional findByCpf(String cpf);

    Optional findByTelefoneDddAndTelefoneNumero(String ddd, String numero);
}
