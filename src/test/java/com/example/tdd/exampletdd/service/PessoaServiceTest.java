package com.example.tdd.exampletdd.service;

import com.example.tdd.exampletdd.domain.Pessoa;
import com.example.tdd.exampletdd.repository.PessoaRepository;
import com.example.tdd.exampletdd.service.exception.CpfException;
import com.example.tdd.exampletdd.service.impl.PessoaServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class PessoaServiceTest {

    private static final String NOME = "Lucas";
    private static final String CPF = "06101169996";


    private PessoaService pessoaService;
    @MockBean
    private PessoaRepository pessoaRepository;
    private Pessoa pessoa;

    @Before
    public void setUp() throws Exception {
        pessoaService = new PessoaServiceImpl(pessoaRepository);

        pessoa = new Pessoa();
        pessoa.setNome(NOME);
        pessoa.setCpf(CPF);

        when(pessoaRepository.findByCpf(CPF)).thenReturn(Optional.empty());
    }

    @Test
    public void salvarPessoaRepositorio() throws Exception {
        pessoaService.salvar(pessoa);
        verify(pessoaRepository).save(pessoa);
    }

    @Test(expected = CpfException.class)
    public void naoSalvarDuasPessoasComMesmoCpf() throws Exception {
        when(pessoaRepository.findByCpf(CPF)).thenReturn(Optional.of(pessoa));

        pessoaService.salvar(pessoa);
    }
}
