package com.desafio.literalura2;

import com.desafio.literalura2.repository.AutorRepository;
import com.desafio.literalura2.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Literalura2Application implements CommandLineRunner {

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    LivroRepository livroRepository;

    public static void main(String[] args) {
        SpringApplication.run(Literalura2Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal(livroRepository, autorRepository);
        principal.exibirMenu();
    }
}
