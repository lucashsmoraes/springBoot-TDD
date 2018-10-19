package com.example.tdd.exampletdd.service;

import com.example.tdd.exampletdd.domain.Pessoa;
import com.example.tdd.exampletdd.domain.Telefone;
import com.example.tdd.exampletdd.service.exception.CpfException;
import com.example.tdd.exampletdd.service.exception.NumTelefoneException;
import com.example.tdd.exampletdd.service.exception.TelefoneNaoEcontradoException;
import org.springframework.stereotype.Service;


public interface PessoaService {

    Pessoa salvar(Pessoa pessoa) throws CpfException, NumTelefoneException;

    Pessoa buscarPorTelefone(Telefone telefone) throws TelefoneNaoEcontradoException;
}
