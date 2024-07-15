package com.desafio.literalura2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;

    @Column(nullable = true)
    private Integer dataDeNascimento;

    @Column(nullable = true)
    private Integer dataDeFalecimento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor(String nome, Integer dataDeNascimento, Integer dataDeFalecimento) {
        this.nome = nome;
        this.dataDeFalecimento = dataDeFalecimento;
        this.dataDeNascimento = dataDeNascimento;
    }

    public Autor(DadosAutor dadosAutor) {
        this.nome = dadosAutor.nomeDoAutor();
        this.dataDeNascimento = dadosAutor.anoDeNascimento() != null ? dadosAutor.anoDeNascimento() : 0;
        this.dataDeFalecimento = dadosAutor.anoDeFalecimento() != null ? dadosAutor.anoDeFalecimento() : 0;
    }

    @Override
    public String toString() {
        return "---------------------------------------"+
                "\nNome: " + nome +
                "\nData De Nascimento: " + dataDeNascimento +
                "\nData De Falecimento: " + dataDeFalecimento;
    }
}