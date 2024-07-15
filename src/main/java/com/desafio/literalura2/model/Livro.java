package com.desafio.literalura2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="livros")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;

    @Column(nullable = true)
    private String idioma;

    @Column(name = "quantidade_de_downloads", nullable = true)
    private Integer quantidadeDownloads;

    @ManyToOne
    @JoinColumn(name = "autor_id",nullable = false)
    private Autor autor;

    public Livro(DadosLivro dadosLivro) {
        this.nome = dadosLivro.nomeDoLivro();
        this.idioma = String.join(", ", dadosLivro.idiomas());  // Converte a lista em uma string
        this.quantidadeDownloads = dadosLivro.numeroDownload();
    }

    @Override
    public String toString() {
        return "\n---------------------------------------"+
                "\nNome: " + nome +
                "\nIdioma: " + idioma +
                "\nAutor: " + autor.getNome() +
                "\nQuantidade De Downloads: " + quantidadeDownloads;
    }
}
