package br.com.alura.spring.data.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;

@Repository
public interface FuncionarioRepository extends PagingAndSortingRepository<Funcionario, Integer>,  JpaSpecificationExecutor<Funcionario> {
	
	//JPQL tem prioridade em relação a Derived Queryes
	
	Page<Funcionario> findByNome(String nome, Pageable pageable); //Derived Queryes

	@Query("SELECT f from Funcionario f where f.nome = :nome "
			+ "AND f.salario >= :salario AND f.dataContratacao = :data")
	List<Funcionario> findNomeSalarioMaiorDataContratacao(String nome, Double salario, LocalDate data);
	
	//@Query("SELECT f from Funcionario f where f.cargo.descricao = :descricao") -- Posso utilizar dessa maneira ou da maneira abaixo.
	@Query("SELECT f FROM Funcionario f JOIN f.cargo c WHERE c.descricao = :descricao") // JPQL - Utiliza os nomes das entidades Java
	List<Funcionario> findByCargoPelaDescricao(String descricao);
	
	@Query("SELECT f FROM Funcionario f JOIN f.unidadeTrabalhos u WHERE u.descricao = :descricao ")
	List<Funcionario> findByDescricaoUnidadeTrabalho(String descricao);
	
	@Query(value = "SELECT * FROM funcionarios f WHERE f.data_contratacao >= :data", nativeQuery = true) //Native Query - Utiliza os nomes das tabelas que estão no banco de dados
	List<Funcionario> findDataContratacao(LocalDate data);
	
	@Query(value = "SELECT f.id, f.nome, f.salario FROM funcionarios f WHERE f.salario >= :salario ORDER BY f.nome ASC", nativeQuery = true)
	List<FuncionarioProjecao> funcionarioSalario(Double salario);
}
