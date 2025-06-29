package com.alura.literatura.principal;

import com.alura.literatura.model.Autor;
import com.alura.literatura.model.DadosLivro;
import com.alura.literatura.model.Livro;
import com.alura.literatura.repository.AutorRepository;
import com.alura.literatura.repository.LivroRepository;
import com.alura.literatura.service.ConsumoApi;
import com.alura.literatura.service.ConverteDados;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    ConverteDados conversor = new ConverteDados();
    private LivroRepository repositorioLivro;
    private AutorRepository repositorioAutor;
    List<Livro> livros;

    public Principal(LivroRepository repositorioLivro, AutorRepository autorRepository) {
        this.repositorioLivro = repositorioLivro;
        this.repositorioAutor = autorRepository;
    }

    public void exibeMenu(){
        var opcao = -1;
        while (opcao != 0) {

                var menu = """
                        ---------------------------
                        1 - Buscar livro pelo título
                        2 - listar livros registrados
                        3 - listar autores registrados
                        4 - listar autores vivos em um determinado ano
                        5 - listar livros em um determinado idioma
                        0 - sair""";

                System.out.println(menu);
                opcao = leitura.nextInt();
                leitura.nextLine();

                switch (opcao){
                    case 1:
                        buscarLivroWeb();
                        break;
                    case 2:
                        listarLivrosBuscados();
                        break;
                    case 3:
                        listarAutoresBuscados();
                        break;
                    case 4:
                        listarAutorPorAno();
                        break;
                    case 5:
                        listarIdiomas();
                        break;
                    case 0:
                        System.out.println("Programa finalizado.");
                        break;
                    default:
                        System.out.println("Opção inválida");
                        break;
                }
        }
    }

    private void buscarLivroWeb() {
        DadosLivro dados = getDadosLivro();
        if (dados == null) {
            System.out.println("Nenhum dado do livro retornado.");
            return;
        }

        String nomeAutor = (dados.authors() != null && !dados.authors().isEmpty())
                ? dados.authors().getFirst().name()
                : "Autor Desconhecido";
        String tituloLivro = dados.title();

        // Verificar se o autor já existe
        Autor autor = repositorioAutor.findByName(nomeAutor)
                .orElseGet(() -> {
                    Autor novoAutor = new Autor(dados);
                    return repositorioAutor.save(novoAutor);
                });

        // Verificar se o livro já existe
        Optional<Livro> livroExistente = repositorioLivro.findByTitle(tituloLivro);
        if (livroExistente.isPresent()) {
            System.out.println("Livro já registrado no banco de dados.");
            return;
        }

        // Criar novo livro e associar ao autor
        Livro novoLivro = new Livro(dados);
        novoLivro.setAutor(autor);

        repositorioLivro.save(novoLivro);
        System.out.println("Livro salvo com sucesso!");
    }


    private DadosLivro getDadosLivro() {
        System.out.println("Digite o nome do livro que você deseja procurar");
        var nomeLivro = leitura.nextLine();
        String ENDERECO = "https://gutendex.com/books/?search=";
        var json = consumo.obterDados(ENDERECO + nomeLivro.replace(" ", "+"));

        try {
            // Cria o ObjectMapper
            ObjectMapper mapper = new ObjectMapper();

            // Lê a resposta como árvore JSON
            JsonNode raiz = mapper.readTree(json);

            // Acessa o array "results"
            JsonNode results = raiz.get("results");

            // Verifica se tem pelo menos um item
            if (results != null && results.isArray() && !results.isEmpty()) {
                JsonNode primeiroLivro = results.get(0);

                // Converte o primeiro resultado para DadosLivro
                return mapper.treeToValue(primeiroLivro, DadosLivro.class);
            } else {
                System.out.println("Nenhum livro encontrado.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Erro ao processar os dados do livro: " + e.getMessage());
            return null;
        }
    }


    private void listarLivrosBuscados() {
        livros = repositorioLivro.findAll();
        livros.forEach(System.out::println);
    }

    private void listarAutoresBuscados() {
        List<Autor> autores = repositorioAutor.findAll();
        autores.forEach(System.out::println);
    }


    private void listarAutorPorAno() {
        System.out.println("Digite o ano que deseja pesquisar: ");
        var opcao = leitura.nextInt();
        leitura.nextLine();
        List<Autor> autores = repositorioAutor.findAll();

        List<Autor> autoresVivosNoAno = autores.stream()
                .filter(a -> a.getBirth_year() <= opcao &&
                        a.getDeath_year() >= opcao)
                .toList();

        if (autoresVivosNoAno.isEmpty()) {
            System.out.println("Nenhum autor encontrado para o ano " + opcao);
        } else {
            autoresVivosNoAno.forEach(System.out::println);
        }

    }

    private void listarIdiomas() {
        System.out.println("""
        Insira o idioma para realizar a busca:
        es- espanhol
        en- inglês
        fr- francês
        pt- português
        """);

        var opcao = leitura.next();
        leitura.nextLine();

        if (opcao.equals("es") || opcao.equals("en") || opcao.equals("fr") || opcao.equals("pt")) {
            List<Livro> livros = repositorioLivro.findByLanguage(opcao);
            if (!livros.isEmpty()) {
                livros.forEach(System.out::println);

            } else {
                System.out.println("Não tem livro pra esse idioma " + opcao);
            }
        } else {
            System.out.println("Resposta inválida " + opcao);
        }
    }


}
