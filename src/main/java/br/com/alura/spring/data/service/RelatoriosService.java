package br.com.alura.spring.data.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;
import br.com.alura.spring.data.repository.FuncionarioRepository;

@Service
public class RelatoriosService {
	private Boolean system = true;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private final FuncionarioRepository funcionarioRepository;

	public RelatoriosService(FuncionarioRepository funcionarioRepository) {
		this.funcionarioRepository = funcionarioRepository;
	}

	public void inicial(Scanner scanner) {
		system = true;
		while (system) {
			System.out.println("Escolha a opção:");
			System.out.println("0 - Sair");
			System.out.println("1 - Buscar funcionario por nome");
			System.out.println("2 - Buscar funcionario por nome, salario maior e data");
			System.out.println("3 - Buscar funcionario cargo");
			System.out.println("4 - Buscar funcionario por descricao da unidade de trabalho");
			System.out.println("5 - Buscar funcionario por data de contratacao");
			System.out.println("6 - Buscar funcionario por salario");

			int action = scanner.nextInt();

			switch (action) {
			case 1:
				buscaFuncionarioNome(scanner);
				break;
			case 2:
				buscaFuncionarioNomeSalariorMaiorDataContratacao(scanner);
				break;
			case 3:
				buscaFuncionarioCargo(scanner);
				break;
			case 4:
				buscaFuncionarioDescricaoUnidadeTrabalho(scanner);
				break;
			case 5:
				buscaFuncionarioDataContratacao(scanner);
				break;
			case 6:
				buscaFuncionarioSalario(scanner);
				break;
			default:
				system = false;
				break;
			}
		}
	}

	public void buscaFuncionarioNome(Scanner scanner) {
		System.out.println("Digite o nome do funcionário");
		String nome = scanner.next();

		Pageable pageable = PageRequest.of(0, 1, Sort.unsorted());

		Page<Funcionario> funcionarios = funcionarioRepository.findByNome(nome, pageable);
		System.out.println(funcionarios.getTotalElements());
		funcionarios.forEach(System.out::println);
	}

	public void buscaFuncionarioNomeSalariorMaiorDataContratacao(Scanner scanner) {
		System.out.println("Digite o nome do funcionario");
		String nome = scanner.next();
		System.out.println("Digite o salario");
		Double salario = scanner.nextDouble();
		System.out.println("Digite a data de contratacao");
		String dataContratacao = scanner.next();

		LocalDate dataContratacaoFormatted = LocalDate.parse(dataContratacao, formatter);

		List<Funcionario> funcionarios = funcionarioRepository.findNomeSalarioMaiorDataContratacao(nome, salario,
				dataContratacaoFormatted);
		funcionarios.forEach(System.out::println);

	}

	public void buscaFuncionarioCargo(Scanner scanner) {
		System.out.println("Digite o cargo");
		String cargo = scanner.next();

		List<Funcionario> funcionarios = funcionarioRepository.findByCargoPelaDescricao(cargo);
		funcionarios.forEach(System.out::println);
	}

	public void buscaFuncionarioDescricaoUnidadeTrabalho(Scanner scanner) {
		System.out.println("Digite a descricao da unidade de trabalho");
		String descricao = scanner.next();

		List<Funcionario> funcionarios = funcionarioRepository.findByDescricaoUnidadeTrabalho(descricao);
		funcionarios.forEach(System.out::println);
	}

	public void buscaFuncionarioDataContratacao(Scanner scanner) {
		System.out.println("Digite a data de contratacao");
		String dataContratacao = scanner.next();

		LocalDate dataContratacaoFormatted = LocalDate.parse(dataContratacao, formatter);

		List<Funcionario> funcionarios = funcionarioRepository.findDataContratacao(dataContratacaoFormatted);
		funcionarios.forEach(System.out::println);
	}

	public void buscaFuncionarioSalario(Scanner scanner) {
		System.out.println("Digite o salario");
		Double salario = scanner.nextDouble();

		List<FuncionarioProjecao> funcionarios = funcionarioRepository.funcionarioSalario(salario);
		funcionarios.forEach(funcionario -> System.out.println("[id: " + funcionario.getId() + " nome: "
				+ funcionario.getNome() + " salario " + funcionario.getSalario() + "]"));
		

	}
}
