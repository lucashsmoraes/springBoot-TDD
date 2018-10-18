package com.example.tdd.exampletdd.repository.helper;

import com.example.tdd.exampletdd.domain.Pessoa;
import com.example.tdd.exampletdd.repository.filtro.PessoaFiltro;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PessoaRepositoryImpl implements PessoaRepositoryQueries{

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Pessoa> filtrar(PessoaFiltro filtro) {
        final StringBuilder sb = new StringBuilder();
        final Map<String, Object> params = new HashMap<>();

        sb.append("SELECT p FROM Pessoa p where 1=1");

        preencherNomeSeNecessario(filtro, sb, params);

        preencherCpfSeNecessario(filtro, sb, params);

        Query query = manager.createQuery(sb.toString(), Pessoa.class);
        preencherParametrosDaQuery(params, query);
        return query.getResultList();
    }

    private void preencherCpfSeNecessario(PessoaFiltro filtro, StringBuilder sb, Map<String, Object> params) {
        if(StringUtils.hasText(filtro.getCpf())){
            sb.append("AND p.cpf LIKE :cpf ");
            params.put("cpf", "%" + filtro.getCpf() + "%");
        }
    }

    private void preencherNomeSeNecessario(PessoaFiltro filtro, StringBuilder sb, Map<String, Object> params) {
        if(StringUtils.hasText(filtro.getNome())){
            sb.append("AND p.nome LIKE :nome");
            params.put("nome", "%" + filtro.getNome() + "%");
        }
    }

    private void preencherParametrosDaQuery(Map<String, Object> params, Query query) {
        for(Map.Entry<String, Object> param : params.entrySet()){
            query.setParameter(param.getKey(), param.getValue());
        }
    }
}
