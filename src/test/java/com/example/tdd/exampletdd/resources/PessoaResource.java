package com.example.tdd.exampletdd.resources;

import com.example.tdd.exampletdd.ExampletddApplicationTests;
import org.junit.Test;
import org.springframework.http.HttpStatus;

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
}
