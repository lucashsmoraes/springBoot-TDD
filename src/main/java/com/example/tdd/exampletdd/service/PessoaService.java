package com.example.tdd.exampletdd.service;

import com.example.tdd.exampletdd.domain.Pessoa;
import com.example.tdd.exampletdd.service.exception.CpfException;

public interface PessoaService {

    Pessoa salvar(Pessoa pessoa) throws CpfException;
}
