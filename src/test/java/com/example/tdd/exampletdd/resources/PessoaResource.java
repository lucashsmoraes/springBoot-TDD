package com.example.tdd.exampletdd.resources;

import com.example.tdd.exampletdd.ExampletddApplicationTests;
import com.example.tdd.exampletdd.domain.Pessoa;
import com.example.tdd.exampletdd.domain.Telefone;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;


public class PessoaResource extends ExampletddApplicationTests {

    @Test
    public void deve_procurar_pessoa_pelo_ddd_e_numero_telefone() throws Exception {
        given()
                .pathParam("ddd", "86")
                .pathParam("numero", "35006330")
        .get("/pessoas/{ddd}/{numero}")
        .then()
                .log().body().and()
                .statusCode(HttpStatus.OK.value())
                .body("codigo", equalTo(3),
                        "nome", equalTo("Cauê"),
                        "cpf", equalTo("38767897100"));
    }

    @Test
    public void deve_retornar_erro_se_pessoa_nao_existir() throws Exception {

        given()
                .pathParam("ddd", "99")
                .pathParam("numero", "33456699")
        .get("/pessoas/{ddd}/{numero}")
        .then()
                .log().body().and()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("erro", equalTo("Não existe pessoa com o telefone (99)33456699"));

    }

    @Test
    public void salvar_nova_pessoa() throws Exception {
        final Pessoa pessoa = new Pessoa();
        pessoa.setNome("João");
        pessoa.setCpf("62461410720");

        final Telefone tel = new Telefone();
        tel.setDdd("79");
        tel.setNumero("36977168");

        pessoa.setTelefones(Arrays.asList(tel));

        given()
                .request()
                .header("Accept", ContentType.ANY)
                .header("Content-Type", ContentType.JSON)
                .body(pessoa)
        .when()
        .post("/pessoas")
        .then()
                .log().headers()
            .and()
                .log().body()
            .and()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", equalTo("http://localhost:"+porta+"/pessoas/79/36977168"))
                .body("codigo", equalTo(6),
                        "nome", equalTo("João"),
                        "cpf", equalTo("62461410720"));
    }

    @Test
    public void nao_salvar_pessoa_com_o_mesmo_cpf() throws Exception {
        final Pessoa pessoa = new Pessoa();
        pessoa.setNome("João");
        pessoa.setCpf("72788740417");

        final Telefone tel = new Telefone();
        tel.setDdd("79");
        tel.setNumero("36977168");

        pessoa.setTelefones(Arrays.asList(tel));

        given()
                .request()
                .header("Accept", ContentType.ANY)
                .header("Content-Type", ContentType.JSON)
                .body(pessoa)
            .when()
            .post("/pessoas")
            .then()
                    .log().body()
                .and()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("erro", equalTo("Já existe pessoa cadastrada com esse CPF"));
    }

}
