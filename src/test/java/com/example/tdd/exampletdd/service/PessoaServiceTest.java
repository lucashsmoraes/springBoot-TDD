package com.example.tdd.exampletdd.service;

import com.example.tdd.exampletdd.domain.Pessoa;
import com.example.tdd.exampletdd.domain.Telefone;
import com.example.tdd.exampletdd.repository.PessoaRepository;
import com.example.tdd.exampletdd.service.exception.CpfException;
import com.example.tdd.exampletdd.service.exception.NumTelefoneException;
import com.example.tdd.exampletdd.service.exception.TelefoneNaoEcontradoException;
import com.example.tdd.exampletdd.service.impl.PessoaServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class PessoaServiceTest {

    private static final String NOME = "Lucas";
    private static final String CPF = "06101169996";
    private static final String DDD = "48";
    private static final String NUMERO = "985956966";


    private PessoaService pessoaService;
    @MockBean
    private PessoaRepository pessoaRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Pessoa pessoa;
    private Telefone telefone;

    @Before
    public void setUp() throws Exception {
        pessoaService = new PessoaServiceImpl(pessoaRepository);

        pessoa = new Pessoa();
        pessoa.setNome(NOME);
        pessoa.setCpf(CPF);

        telefone = new Telefone();
        telefone.setDdd(DDD);
        telefone.setNumero(NUMERO);

        pessoa.setTelefones(Arrays.asList(telefone));

        when(pessoaRepository.findByCpf(CPF)).thenReturn(Optional.empty());
        when(pessoaRepository.findByTelefoneDddAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.empty());
    }

    @Test
    public void salvarPessoaRepositorio() throws Exception {
        pessoaService.salvar(pessoa);
        verify(pessoaRepository).save(pessoa);
    }

    @Test
    public void naoSalvarDuasPessoasComMesmoCpf() throws Exception {
        when(pessoaRepository.findByCpf(CPF)).thenReturn(Optional.of(pessoa));

        expectedException.expect(CpfException.class);
        expectedException.expectMessage("Já existe pessoa cadastrada com esse CPF");

        pessoaService.salvar(pessoa);
    }

    @Test(expected = NumTelefoneException.class)
    public void naoSalvarDuasPessoasComMesmoNumTelefone() throws Exception {
        when(pessoaRepository.findByTelefoneDddAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.of(pessoa));

        pessoaService.salvar(pessoa);
    }

    @Test(expected = TelefoneNaoEcontradoException.class)
    public void retornaExceptionSePessoaNaoTemNumeroTelefone() throws TelefoneNaoEcontradoException {
        pessoaService.buscarPorTelefone(telefone);
    }

    @Test
    public void deve_retornar_dados_na_excecao_de_telefone_nao_encontrado_exception() throws Exception {
        expectedException.expect(TelefoneNaoEcontradoException.class);
        expectedException.expectMessage("Não existe pessoa com o telefone (" + DDD + ")" + NUMERO);
        pessoaService.buscarPorTelefone(telefone);
    }

    @Test
    public void buscarPessoaPeloDddENumTelefone() throws Exception {
        when(pessoaRepository.findByTelefoneDddAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.of(pessoa));
        Pessoa pessoaTeste = pessoaService.buscarPorTelefone(telefone);

        verify(pessoaRepository).findByTelefoneDddAndTelefoneNumero(DDD, NUMERO);

        assertThat(pessoaTeste).isNotNull();
        assertThat(pessoaTeste.getNome()).isEqualTo(NOME);
        assertThat(pessoaTeste.getCpf()).isEqualTo(CPF);

    }
}
