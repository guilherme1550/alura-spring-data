package br.com.alura.spring.data.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.UnidadeTrabalho;
import br.com.alura.spring.data.repository.CargoRepository;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import br.com.alura.spring.data.repository.UnidadeTrabalhoRepository;

@Service
public class CrudFuncionarioService {
	private Boolean system = true;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private final FuncionarioRepository funcionarioRepository;
	private final CargoRepository cargoRepository;
	private final UnidadeTrabalhoRepository unidadeTrabalhoRepository;

	public CrudFuncionarioService(FuncionarioRepository funcionarioRepository, CargoRepository cargoRepository, UnidadeTrabalhoRepository unidadeTrabalhoRepository) {
		this.funcionarioRepository = funcionarioRepository;
		this.cargoRepository = cargoRepository;
		this.unidadeTrabalhoRepository = unidadeTrabalhoRepository;
	}

	public void inicial(Scanner scanner) {
		system = true;
		while (system) {
			System.out.println("Escolha a opção:");
			System.out.println("0 - Sair");
			System.out.println("1 - Novo funcionário");
			System.out.println("2 - Atualizar funcionário");
			System.out.println("3 - Listar todos os funcionários");
			System.out.println("4 - Listar apenas 1 funcionário");
			System.out.println("5 - Remover um funcionário");
			int action = scanner.nextInt();

			switch (action) {
			case 1:
				salvar(scanner);
				break;
			case 2:
				atualizar(scanner);
				break;
			case 3:
				listaFuncionarios(scanner);
				break;
			case 4:
				listaFuncionario(scanner);
				break;
			case 5:
				removeFuncionario(scanner);
				break;
			default:
				system = false;
				break;
			}
		}
	}

	public void salvar(Scanner scanner) {
		System.out.println("Nome do Funcionário");
		String nome = scanner.next();
		
		System.out.println("CPF do funcionário");
		String cpf = scanner.next();
		
		System.out.println("Salário do funcionário");
		Double salario = scanner.nextDouble();
		
		System.out.println("Data da Contratação do funcionário");
		String dataContratacao = scanner.next();
		
		System.out.println("Digite o ID do Cargo");
		Integer cargoId = scanner.nextInt();
		Optional<Cargo> cargo = cargoRepository.findById(cargoId);
		
		List<UnidadeTrabalho> unidadesTrabalho = unidades(scanner);
		
		Funcionario funcionario = new Funcionario();
		funcionario.setNome(nome);
		funcionario.setCpf(cpf);
		funcionario.setSalario(salario);
		funcionario.setDataContratacao(LocalDate.parse(dataContratacao, formatter));
		funcionario.setCargo(cargo.get());
		funcionario.setUnidadeTrabalhos(unidadesTrabalho);

		funcionarioRepository.save(funcionario);

		System.out.println("Salvo");
	}
	
	public List<UnidadeTrabalho> unidades(Scanner scanner) {
		List<UnidadeTrabalho> unidadesTrabalho = new ArrayList<UnidadeTrabalho>();
		Boolean system = true;
		
		while(system) {
			System.out.println("Digite o ID da unidade de trabalho ---- 0 - Para sair");
			int id = scanner.nextInt();
			if(id == 0)
				system = false;
			Optional<UnidadeTrabalho> unidadeTrabalho = unidadeTrabalhoRepository.findById(id);
			unidadeTrabalho.ifPresent(unidade -> unidadesTrabalho.add(unidade));
		}
		
		return unidadesTrabalho;
	}

	public void atualizar(Scanner scanner) {
		System.out.println("Id do funcionário");
		Integer id = scanner.nextInt();
		
		System.out.println("Nome do Funcionário");
		String nome = scanner.next();
		
		System.out.println("CPF do funcionário");
		String cpf = scanner.next();
		
		System.out.println("Salário do funcionário");
		Double salario = scanner.nextDouble();
		
		System.out.println("Data da Contratação do funcionário");
		String dataContratacao = scanner.next();
		
		System.out.println("Digite o ID do Cargo");
		Integer cargoId = scanner.nextInt();
		Optional<Cargo> cargo = cargoRepository.findById(cargoId);
		
		List<UnidadeTrabalho> unidadesTrabalho = unidades(scanner);
		
		Funcionario funcionario = new Funcionario();
		funcionario.setId(id);
		funcionario.setNome(nome);
		funcionario.setCpf(cpf);
		funcionario.setSalario(salario);
		funcionario.setDataContratacao(LocalDate.parse(dataContratacao, formatter));
		funcionario.setCargo(cargo.get());
		funcionario.setUnidadeTrabalhos(unidadesTrabalho);

		funcionarioRepository.save(funcionario);
		System.out.println("Atualizado");
	}

	public void listaFuncionarios(Scanner scanner) {
		System.out.println("Digite a página que deseja visualizar");
		int page = scanner.nextInt();
		
		Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.ASC, "nome"));
		
		Page<Funcionario> funcionarios = funcionarioRepository.findAll(pageable);
		
		System.out.println(funcionarios); //Page 1 of 1 - Página atual começando do 1
		System.out.println("Pagina atual " + funcionarios.getNumber()); //Exibe a página atual começando do 0
		System.out.println("Total de elementos " + funcionarios.getTotalElements()); //Exibe total de elementos
		funcionarios.forEach(funcionario -> System.out.println(funcionario));
	}

	public void listaFuncionario(Scanner scanner) {
		System.out.println("Digite o Id do funcionario que deseja listar: ");
		int id = scanner.nextInt();

		Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
		// cargo.stream().forEach(x -> System.out.println(x.getDescricao()));

		funcionario.ifPresent(x -> System.out.println(x));
	}

	public void removeFuncionario(Scanner scanner) {
		System.out.println("Digite o Id que deseja remover: ");
		int id = scanner.nextInt();

		funcionarioRepository.deleteById(id);
		System.out.println("Removido");
	}
}
