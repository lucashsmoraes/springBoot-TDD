package com.example.tdd.exampletdd.Resource;

import com.example.tdd.exampletdd.domain.Pessoa;
import com.example.tdd.exampletdd.domain.Telefone;
import com.example.tdd.exampletdd.repository.PessoaRepository;
import com.example.tdd.exampletdd.repository.filtro.PessoaFiltro;
import com.example.tdd.exampletdd.service.PessoaService;
import com.example.tdd.exampletdd.service.exception.CpfException;
import com.example.tdd.exampletdd.service.exception.NumTelefoneException;
import com.example.tdd.exampletdd.service.exception.TelefoneNaoEcontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/pessoas")
public class PessoaResource {

    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private PessoaRepository pessoaRepository;

    @RequestMapping(value = "/{ddd}/{numero}", method = RequestMethod.GET)
    public ResponseEntity<Pessoa> buscarPorDddEnumTelefone(
            @PathVariable("ddd") String ddd, @PathVariable("numero") String numero) throws TelefoneNaoEcontradoException {

        final Telefone telefone = new Telefone();
        telefone.setDdd(ddd);
        telefone.setNumero(numero);
        final Pessoa pessoa = pessoaService.buscarPorTelefone(telefone);

        return ResponseEntity.ok().body(pessoa);
    }

    @PostMapping
    public ResponseEntity<Pessoa> salvarNovaPessoa(@RequestBody Pessoa pessoa, HttpServletResponse response) throws CpfException, NumTelefoneException {
        final Pessoa pessoaSalva = pessoaService.salvar(pessoa);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{ddd}/{numero}")
                .buildAndExpand(pessoa.getTelefones().get(0).getDdd(), pessoa.getTelefones().get(0).getNumero()).toUri();

        response.setHeader("Location", uri.toASCIIString());

        return new ResponseEntity<>(pessoaSalva, HttpStatus.CREATED);
    }

    @PostMapping("/filtrar")
    public ResponseEntity<List<Pessoa>> filtrar(@RequestBody PessoaFiltro pessoaFiltro){
        List<Pessoa> pessoas = pessoaRepository.filtrar(pessoaFiltro);

        return new ResponseEntity<>(pessoas, HttpStatus.OK);

    }

    @ExceptionHandler({CpfException.class})
    public ResponseEntity<Erro> handlerCpfException(CpfException e) {
        return new ResponseEntity<>(new Erro(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({TelefoneNaoEcontradoException.class})
    public ResponseEntity<Erro> handlerTelefoneNaoEncontradoException(TelefoneNaoEcontradoException e) {
            return new ResponseEntity<Erro>(new Erro(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    class Erro {
        private final String erro;

        public Erro(String erro){
            this.erro = erro;
        }

        public String getErro() {
            return erro;
        }
    }

}
