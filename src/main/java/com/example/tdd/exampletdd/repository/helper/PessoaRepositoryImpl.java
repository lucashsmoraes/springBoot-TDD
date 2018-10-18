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

        if(StringUtils.hasText(filtro.getNome())){
            sb.append("AND p.nome LIKE :nome");
            params.put("nome", "%" + filtro.getNome() + "%");
        }

        if(StringUtils.hasText(filtro.getCpf())){
            sb.append("AND p.cpf LIKE :cpf ");
            params.put("cpf", "%" + filtro.getCpf() + "%");
        }

        Query query = manager.createQuery(sb.toString(), Pessoa.class);
        for(Map.Entry<String, Object> param : params.entrySet()){
            query.setParameter(param.getKey(), param.getValue());
        }
        return query.getResultList();
    }
}
