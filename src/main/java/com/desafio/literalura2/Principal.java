package com.desafio.literalura2;

import com.desafio.literalura2.model.Autor;
import com.desafio.literalura2.model.DadosAutor;
import com.desafio.literalura2.model.DadosLivro;
import com.desafio.literalura2.model.Livro;
import com.desafio.literalura2.repository.AutorRepository;
import com.desafio.literalura2.repository.LivroRepository;
import com.desafio.literalura2.service.ConsumoApi;
import com.desafio.literalura2.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumoAPI = new ConsumoApi();
    private AutorRepository autorRepositorio;
    private LivroRepository livroRepositorio;
    private ConverteDados conversor = new ConverteDados();
    private List<Livro> livro = new ArrayList<>();
    private final String ENDERECO = "http://gutendex.com/books/?search=";

    public Principal(LivroRepository livroRepositorio, AutorRepository autorRepositorio) {
        this.autorRepositorio = autorRepositorio;
        this.livroRepositorio = livroRepositorio;
    }


    public void exibirMenu(){
        var opcao = -1;

        while (opcao != 0){
            var menu = """
                    \n\n\n*** Bem Vindo ao LiterAlura ***
                    
                    Digite a opção desejada:
                    1 - Pesquisar livro pelo nome
                    2 - Listar livros salvos
                    3 - Listar autores salvos
                    4 - Listar autores vivos por ano
                    5 - Listar livros por idioma
                    0 - Sair
                    ========================================""";

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao){
                case 1:
                    pesquisarArtistaPorNome();
                    break;
                case 2:
                    listarLivros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLivrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Digite uma opção válida");
            }
        }
    }

    private void pesquisarArtistaPorNome() {
        System.out.println("\nQual livro deseja buscar?");
        var buscaDoUsuario = leitura.nextLine();
        var dados = consumoAPI.consumo(ENDERECO + buscaDoUsuario.replace(" ","%20"));
        salvar(dados);
    }

    private void listarLivros() {
        var buscaLivros = livroRepositorio.findAll();
        if (!buscaLivros.isEmpty()){
            System.out.println("""
                    ============================
                    Livros Cadastrados no Banco
                    ============================""");
            buscaLivros.forEach(System.out::println);
        } else {
            System.out.println("Nenhuma Livro encontrado no Banco de Dados");
        }
    }

    private void listarAutores() {
        var buscarAutores = autorRepositorio.findAll();
        if (!buscarAutores.isEmpty()){
            System.out.println("""
                    =============================
                    Autores Cadastrados no Banco
                    =============================""");
            buscarAutores.forEach(System.out::println);
        } else {
            System.out.println("Nenhum Autor Cadastrado!");
        }
    }

    private void listarAutoresVivos() {
        System.out.println("Digite o ano que gostaria de pesquisar!");
        var anoSelecionado = leitura.nextInt();
        leitura.nextLine();
        var buscarAutoresNoBanco = autorRepositorio.BuscarPorAnoDeFalecimento(anoSelecionado);
        if (!buscarAutoresNoBanco.isEmpty()){
            System.out.println(String.format("""
                    =============================
                    Autores Vivos no Ano de: %d
                    =============================""", anoSelecionado));
            buscarAutoresNoBanco.forEach(System.out::println);
        } else {
            System.out.println("""
                    =====================================================
                    Nenhuma autor encontrado para a data selecionada!
                    =====================================================""");
        }


    }

    private void listarLivrosPorIdioma() {
        var idiomasDisponiveis = livroRepositorio.bucasIdiomas();
        System.out.println(String.format("""
                =====================
                Idiomar Disponíveis:
                %s
                =====================""", idiomasDisponiveis));
        idiomasDisponiveis.forEach(System.out::println);
        System.out.println("Selecione o Idioma Desejado!");
        var idiomaDesejado = leitura.nextLine();
        livroRepositorio.buscarPorIdioma(idiomaDesejado).forEach(System.out::println);


    }

    private void salvar(String dados) {
        try {
            DadosLivro dadosLivro = conversor.obterDados(dados, DadosLivro.class);
            DadosAutor dadosAutor = conversor.obterDados(dados, DadosAutor.class);

            Autor autor = new Autor(dadosAutor);
            if (!autorRepositorio.existsByNome(dadosAutor.nomeDoAutor())) {
                autorRepositorio.save(autor);
            } else {
                autor = autorRepositorio.findByNome(autor.getNome());
            }

            Livro livro = new Livro(dadosLivro);
            livro.setAutor(autor);
            if (!livroRepositorio.existsByNome(livro.getNome())){
                livroRepositorio.save(livro);
            }

            System.out.println("Livro Adicionado com Sucesso!!! " + livro);
        } catch (Exception e) {
            System.out.println("\n\n*** Ops! Erro ao salvar o livro ***\n\n");
            e.printStackTrace();
        }
    }
}
