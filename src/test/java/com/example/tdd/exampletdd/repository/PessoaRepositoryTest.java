package com.example.tdd.exampletdd.repository;

import com.example.tdd.exampletdd.domain.Pessoa;
import com.example.tdd.exampletdd.repository.filtro.PessoaFiltro;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Sql(value = "/load-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
public class PessoaRepositoryTest {

    @Autowired
    private PessoaRepository sut;

    @Test
    public void deve_procurar_pessoa_pelo_cpf() throws Exception {
        Optional<Pessoa> optional = sut.findByCpf("38767897100");

        assertThat(optional.isPresent()).isTrue();

        Pessoa pessoa = optional.get();
        assertThat(pessoa.getCodigo()).isEqualTo(3L);
        assertThat(pessoa.getNome()).isEqualTo("Cauê");
        assertThat(pessoa.getCpf()).isEqualTo("38767897100");
    }

    @Test
    public void deve_encontrar_pessoa_pelo_ddd_e_numero_de_telefone() throws Exception {
        Optional<Pessoa> optional = sut.findByTelefoneDddAndTelefoneNumero("86", "35006330");

        assertThat(optional.isPresent()).isTrue();

        Pessoa pessoa = optional.get();
        assertThat(pessoa.getCodigo()).isEqualTo(3L);
        assertThat(pessoa.getNome()).isEqualTo("Cauê");
        assertThat(pessoa.getCpf()).isEqualTo("38767897100");
    }

    @Test
    public void nao_deve_encontrar_pessoa_se_ddd_e_telefone_nao_estajam_cadastrados() throws Exception {
        Optional<Pessoa> optionalPessoa = sut.findByTelefoneDddAndTelefoneNumero("11", "324516731");

        assertThat(optionalPessoa.isPresent()).isFalse();
    }

    @Test
    public void deve_filtrar_por_nome() throws Exception {
        PessoaFiltro filtro = new PessoaFiltro();
        filtro.setNome("a");

        List<Pessoa> pessoaList = sut.filtrar(filtro);

        assertThat(pessoaList.size()).isEqualTo(3);
    }

    @Test
    public void deve_filtrar_por_cpf() throws Exception {
        PessoaFiltro pessoaFiltro = new PessoaFiltro();
        pessoaFiltro.setCpf("78");

        List<Pessoa> pessoaList = sut.filtrar(pessoaFiltro);
        assertThat(pessoaList.size()).isEqualTo(3);
    }

    @Test
    public void deve_filtrar_por_nome_e_cpf() throws Exception {
        PessoaFiltro pessoaFiltro = new PessoaFiltro();
        pessoaFiltro.setNome("Iago");
        pessoaFiltro.setCpf("86730543540");

        List<Pessoa> pessoas = sut.filtrar(pessoaFiltro);
        assertThat(pessoas.size()).isEqualTo(1);
    }

    @Test
    public void deve_filtrar_pessoa_por_ddd() throws Exception {
        PessoaFiltro pessoaFiltro = new PessoaFiltro();
        pessoaFiltro.setDdd("21");

        List<Pessoa> pessoas = sut.filtrar(pessoaFiltro);
        assertThat(pessoas.size()).isEqualTo(1);
    }

    @Test
    public void deve_filtrar_pessoa_por_telefone() throws Exception {
        PessoaFiltro pessoaFiltro = new PessoaFiltro();
        pessoaFiltro.setTelefone("997538804");

        List<Pessoa> pessoas = sut.filtrar(pessoaFiltro);
        assertThat(pessoas.size()).isEqualTo(1);
    }

    @Test
    public void deve_filtrar_pessoa_por_ddd_e_telefone() throws Exception {
        PessoaFiltro pessoaFiltro = new PessoaFiltro();
        pessoaFiltro.setDdd("21");
        pessoaFiltro.setTelefone("997538804");

        List<Pessoa> pessoas = sut.filtrar(pessoaFiltro);
        assertThat(pessoas.size()).isEqualTo(1);
    }
}
