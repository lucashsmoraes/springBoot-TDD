package com.example.tdd.exampletdd.service.impl;

import com.example.tdd.exampletdd.domain.Pessoa;
import com.example.tdd.exampletdd.repository.PessoaRepository;
import com.example.tdd.exampletdd.service.PessoaService;
import com.example.tdd.exampletdd.service.exception.CpfException;

import java.util.Optional;

public class PessoaServiceImpl implements PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaServiceImpl(PessoaRepository pessoaRepository){
        this.pessoaRepository = pessoaRepository;
    }

    public Pessoa salvar(Pessoa pessoa) throws CpfException {
        Optional<Pessoa> optionalPessoa = pessoaRepository.findByCpf(pessoa.getCpf());

        if (optionalPessoa.isPresent()){
            throw new CpfException();
        }
        return pessoaRepository.save(pessoa);
    }
}
