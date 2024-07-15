package com.desafio.literalura2.repository;

import com.desafio.literalura2.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface AutorRepository extends JpaRepository<Autor, Long> {
    boolean existsByNome(String nome);

    Autor findByNome(String nome);

    @Query("SELECT a FROM Autor a WHERE a.dataDeFalecimento >= :anoSelecionado AND :anoSelecionado >= a.dataDeNascimento")
    List<Autor> BuscarPorAnoDeFalecimento(int anoSelecionado);
}
