package com.example.tdd.exampletdd.service;

import com.example.tdd.exampletdd.domain.Pessoa;
import com.example.tdd.exampletdd.service.exception.CpfException;
import com.example.tdd.exampletdd.service.exception.NumCelularException;

public interface PessoaService {

    Pessoa salvar(Pessoa pessoa) throws CpfException, NumCelularException;
}
