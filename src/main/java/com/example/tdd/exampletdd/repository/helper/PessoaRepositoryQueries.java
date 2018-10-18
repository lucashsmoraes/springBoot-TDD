package com.example.tdd.exampletdd.repository.helper;

import com.example.tdd.exampletdd.domain.Pessoa;
import com.example.tdd.exampletdd.repository.filtro.PessoaFiltro;

import java.util.List;

public interface PessoaRepositoryQueries {

    List<Pessoa> filtrar(PessoaFiltro filtro);
}
