package com.desafio.literalura2.repository;

import com.desafio.literalura2.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    boolean existsByNome(String nome);

    @Query("SELECT DISTINCT l.idioma FROM Livro l ORDER BY l.idioma")
    List<String> bucasIdiomas();

    @Query("SELECT l FROM Livro l WHERE l.idioma = :idiomaSelecionado")
    List<Livro> buscarPorIdioma(String idiomaSelecionado);
}
