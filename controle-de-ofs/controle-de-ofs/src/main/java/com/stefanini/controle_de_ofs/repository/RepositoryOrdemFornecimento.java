package com.stefanini.controle_de_ofs.repository;

import com.stefanini.controle_de_ofs.models.Employee;
import com.stefanini.controle_de_ofs.models.OrdemFornecimento;
import com.stefanini.controle_de_ofs.projection.OrdemFornecimentoMes;
import com.stefanini.controle_de_ofs.projection.OrdemFornecimentoProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RepositoryOrdemFornecimento extends CrudRepository<OrdemFornecimento, Integer>{
    @Query(value = "select codigo, description, ordem_fornecimento.status, created_at, updated_at, name as collaborator from ordem_fornecimento join employee on employee.id = employee join user on user.id = employee.user", nativeQuery = true)
    List<OrdemFornecimentoProjection> acharTudoSemMandarObjetoChaveEstrangeira();
    OrdemFornecimento findByCodigo(Integer codigo);
    OrdemFornecimento findByEmployee(Employee obj);
    Integer countByCodigo(Integer codigo);

    List<OrdemFornecimento> findByStatus(String status);

    @Query(value = "select month(created_at) as mes, count(month(created_at)) as quantidade from ordem_fornecimento group by mes limit 5", nativeQuery = true)
    List<OrdemFornecimentoMes> listarPorMes();
}
