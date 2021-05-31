package br.com.alura.spring.data.service;

import java.util.Optional;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.repository.CargoRepository;

@Service
public class CrudCargoService {

	private Boolean system = true;
	private final CargoRepository cargoRepository;

	public CrudCargoService(CargoRepository cargoRepository) {
		this.cargoRepository = cargoRepository;
	}

	public void inicial(Scanner scanner) {
		system = true;
		while (system) {
			System.out.println("Escolha a opção:");
			System.out.println("0 - Sair");
			System.out.println("1 - Novo Cargo");
			System.out.println("2 - Atualizar Cargo");
			System.out.println("3 - Listar todos os cargos");
			System.out.println("4 - Listar apenas 1 cargo");
			System.out.println("5 - Remover um cargo");
			int action = scanner.nextInt();

			switch (action) {
			case 1:
				salvar(scanner);
				break;
			case 2:
				atualizar(scanner);
				break;
			case 3:
				listaCargos();
				break;
			case 4:
				listaCargo(scanner);
				break;
			case 5:
				removeCargo(scanner);
				break;
			default:
				system = false;
				break;
			}
		}
	}
	public void salvar(Scanner scanner) {
		System.out.println("Descricao do cargo");
		String descricao = scanner.next();

		Cargo cargo = new Cargo();
		cargo.setDescricao(descricao);

		cargoRepository.save(cargo);

		System.out.println("Salvo");
	}

	public void atualizar(Scanner scanner) {
		System.out.println("Qual Id deseja atualizar: ");
		int id = scanner.nextInt();
		System.out.println("Atualize a descrição do cargo:");
		String descricao = scanner.next();

		Cargo cargo = new Cargo();
		cargo.setId(id);
		cargo.setDescricao(descricao);

		cargoRepository.save(cargo);
		System.out.println("Salvo");
	}
	
	public void listaCargos() {
		Iterable<Cargo> cargos = cargoRepository.findAll();
		//cargos.forEach(cargo -> System.out.println("Id: " + cargo.getId() + " ----- Descrição: " + cargo.getDescricao()));
		cargos.forEach(cargo -> System.out.println(cargo));
	}
	
	public void listaCargo(Scanner scanner) {
		System.out.println("Digite o Id do cargo que deseja listar: ");
		int id = scanner.nextInt();
		
		Optional<Cargo> cargo = cargoRepository.findById(id);
		//cargo.stream().forEach(x -> System.out.println(x.getDescricao()));
		
		cargo.ifPresent(x -> System.out.println(x.getDescricao()));
	}
	
	public void removeCargo(Scanner scanner) {
		System.out.println("Digite o Id que deseja remover: ");
		int id = scanner.nextInt();
		
		cargoRepository.deleteById(id);
		System.out.println("Removido");
	}
}
