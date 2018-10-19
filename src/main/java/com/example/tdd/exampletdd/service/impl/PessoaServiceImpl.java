package com.example.tdd.exampletdd.service.impl;

import com.example.tdd.exampletdd.domain.Pessoa;
import com.example.tdd.exampletdd.domain.Telefone;
import com.example.tdd.exampletdd.repository.PessoaRepository;
import com.example.tdd.exampletdd.service.PessoaService;
import com.example.tdd.exampletdd.service.exception.CpfException;
import com.example.tdd.exampletdd.service.exception.NumTelefoneException;
import com.example.tdd.exampletdd.service.exception.TelefoneNaoEcontradoException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PessoaServiceImpl implements PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaServiceImpl(PessoaRepository pessoaRepository){
        this.pessoaRepository = pessoaRepository;
    }

    public Pessoa salvar(Pessoa pessoa) throws CpfException, NumTelefoneException {
        Optional<Pessoa> optionalPessoa = pessoaRepository.findByCpf(pessoa.getCpf());

        if (optionalPessoa.isPresent()){
            throw new CpfException();
        }

        final String ddd = pessoa.getTelefones().get(0).getDdd();
        final String numero = pessoa.getTelefones().get(0).getNumero();
        optionalPessoa = pessoaRepository.findByTelefoneDddAndTelefoneNumero(ddd, numero);

        if (optionalPessoa.isPresent()) {
            throw new NumTelefoneException();
        }

        return pessoaRepository.save(pessoa);
    }

    @Override
    public Pessoa buscarPorTelefone(Telefone telefone) throws TelefoneNaoEcontradoException {
        Optional<Pessoa> pessoaOptional= pessoaRepository.findByTelefoneDddAndTelefoneNumero(telefone.getDdd(), telefone.getNumero());
        return pessoaOptional.orElseThrow(() -> new TelefoneNaoEcontradoException());
    }
}
