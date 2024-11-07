package com.challengejava.litealura;

import com.challengejava.litealura.model.Autor;
import com.challengejava.litealura.model.Libro;
import com.challengejava.litealura.principal.Principal;


import com.challengejava.litealura.repository.AutorRepository;
import com.challengejava.litealura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication

public class LitealuraApplication implements CommandLineRunner {
	@Autowired
	private LibroRepository libroRepository;

	@Autowired
	private AutorRepository autorRepository;

	public static void main(String[] args) {

		SpringApplication.run(LitealuraApplication.class, args);
	}

	@Override

	public void run(String... args) throws Exception {
		Principal principal = new Principal(libroRepository, autorRepository);
		principal.mostrarMenu();

	}
}