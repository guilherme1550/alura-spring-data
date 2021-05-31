package br.com.alura.spring.data.service;

import java.util.Optional;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.UnidadeTrabalho;

import br.com.alura.spring.data.repository.UnidadeTrabalhoRepository;

@Service
public class CrudUnidadeTrabalhoService {
	private Boolean system = true;
	private final UnidadeTrabalhoRepository unidadeTrabalhoRepository;

	public CrudUnidadeTrabalhoService(UnidadeTrabalhoRepository unidadeTrabalhoRepository) {
		this.unidadeTrabalhoRepository = unidadeTrabalhoRepository;
	}

	public void inicial(Scanner scanner) {
		system = true;
		while (system) {
			System.out.println("Escolha a opção:");
			System.out.println("0 - Sair");
			System.out.println("1 - Nova Unidade de Trabalho");
			System.out.println("2 - Atualizar Unidade de Trabalho");
			System.out.println("3 - Listar todas as unidades de trabalho");
			System.out.println("4 - Listar apenas 1 unidade de trabalho");
			System.out.println("5 - Remover uma unidade de trabalho");
			int action = scanner.nextInt();

			switch (action) {
			case 1:
				salvar(scanner);
				break;
			case 2:
				atualizar(scanner);
				break;
			case 3:
				listaUnidadesTrabalho();
				break;
			case 4:
				listaUnidadeTrabalho(scanner);
				break;
			case 5:
				removeUnidadeTrabalho(scanner);
				break;
			default:
				system = false;
				break;
			}
		}
	}

	public void salvar(Scanner scanner) {
		System.out.println("Descricao da Unidade de Trabalho");
		String descricao = scanner.next();
		System.out.println("Endereco da Unidade de Trabalho");
		String endereco = scanner.next();

		UnidadeTrabalho unidadeTrabalho = new UnidadeTrabalho();
		unidadeTrabalho.setDescricao(descricao);
		unidadeTrabalho.setEndereco(endereco);

		unidadeTrabalhoRepository.save(unidadeTrabalho);

		System.out.println("Salvo");
	}

	public void atualizar(Scanner scanner) {
		System.out.println("Qual Id deseja atualizar: ");
		int id = scanner.nextInt();
		System.out.println("Atualize a descrição da unidade de trabalho:");
		String descricao = scanner.next();
		System.out.println("Atualize o endereco da unidade de trabalho:");
		String endereco = scanner.next();

		UnidadeTrabalho unidadeTrabalho = new UnidadeTrabalho();
		unidadeTrabalho.setId(id);
		unidadeTrabalho.setDescricao(descricao);
		unidadeTrabalho.setEndereco(endereco);

		unidadeTrabalhoRepository.save(unidadeTrabalho);
		System.out.println("Salvo");
	}

	public void listaUnidadesTrabalho() {
		Iterable<UnidadeTrabalho> unidades = unidadeTrabalhoRepository.findAll();
		// cargos.forEach(cargo -> System.out.println("Id: " + cargo.getId() + " -----
		// Descrição: " + cargo.getDescricao()));
		unidades.forEach(unidade -> System.out.println(unidade));
	}

	public void listaUnidadeTrabalho(Scanner scanner) {
		System.out.println("Digite o Id da unidade de trabalho que deseja listar: ");
		int id = scanner.nextInt();

		Optional<UnidadeTrabalho> unidadeTrabalho = unidadeTrabalhoRepository.findById(id);
		// cargo.stream().forEach(x -> System.out.println(x.getDescricao()));

		unidadeTrabalho.ifPresent(x -> System.out.println(x.getDescricao()));
	}

	public void removeUnidadeTrabalho(Scanner scanner) {
		System.out.println("Digite o Id que deseja remover: ");
		int id = scanner.nextInt();

		unidadeTrabalhoRepository.deleteById(id);
		System.out.println("Removido");
	}
}
