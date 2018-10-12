package com.example.tdd.exampletdd.repository;

import com.example.tdd.exampletdd.domain.Pessoa;

import java.util.Optional;

public interface PessoaRepository {

    Pessoa save(Pessoa pessoa);

    Optional findByCpf(String cpf);

    Optional findByTelefoneDddAndTelefoneNumero(String ddd, String numero);
}
