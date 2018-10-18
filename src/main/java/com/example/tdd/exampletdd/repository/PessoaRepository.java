package com.example.tdd.exampletdd.repository;

import com.example.tdd.exampletdd.domain.Pessoa;
import com.example.tdd.exampletdd.repository.helper.PessoaRepositoryQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>, PessoaRepositoryQueries {

    Optional<Pessoa> findByCpf(String cpf);

    @Query("SELECT obj FROM Pessoa obj JOIN obj.telefones tele WHERE tele.ddd = :ddd AND tele.numero = :numero")
    Optional<Pessoa> findByTelefoneDddAndTelefoneNumero(@Param("ddd") String ddd, @Param("numero") String numero);
}
