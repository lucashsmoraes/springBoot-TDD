package com.example.tdd.exampletdd.Resource;

import com.example.tdd.exampletdd.domain.Pessoa;
import com.example.tdd.exampletdd.domain.Telefone;
import com.example.tdd.exampletdd.service.PessoaService;
import com.example.tdd.exampletdd.service.exception.TelefoneNaoEcontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/pessoas")
public class PessoaResource {

    @Autowired
    private PessoaService pessoaService;

    @RequestMapping(value = "/{ddd}/{numero}", method = RequestMethod.GET)
    public ResponseEntity<Pessoa> buscarPorDddEnumTelefone(
            @PathVariable("ddd") String ddd, @PathVariable("numero") String numero) throws TelefoneNaoEcontradoException {

        final Telefone telefone = new Telefone();
        telefone.setDdd(ddd);
        telefone.setNumero(numero);
        final Pessoa pessoa = pessoaService.buscarPorTelefone(telefone);

        return ResponseEntity.ok().body(pessoa);
    }

}
