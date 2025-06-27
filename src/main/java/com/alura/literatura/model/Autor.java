package com.alura.literatura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique=true)
    private String name;
    private int birth_year;
    private int death_year;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor() {}

    public Autor(DadosLivro dadosLivro) {
        this.name = dadosLivro.authors().getFirst().name();
        this.birth_year = dadosLivro.authors().getFirst().birth_year();
        this.death_year = dadosLivro.authors().getFirst().death_year();
    }



    @Override
    public String toString() {
        StringBuilder livrosStr = new StringBuilder();
        for (Livro livro : livros) {
            if (livros.size() > 1 && livro != livros.getLast()) {
                livrosStr.append(livro.getTitle()).append(", ");
            } else {
                livrosStr.append(livro.getTitle());
            }

        }

        return "\n  Autor: " + this.name
                + "\n  Ano de Nascimento: " + this.birth_year
                + "\n  Ano de falecimento: " + this.death_year
                + "\n  Livros: " + livrosStr + "\n";
    }

    public String getName() {
        return name;
    }

    public void setLivros(List<Livro> livros) {
        livros.forEach(l -> l.setAutor(this));
        this.livros = livros;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public int getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(int birth_year) {
        this.birth_year = birth_year;
    }

    public int getDeath_year() {
        return death_year;
    }

    public void setDeath_year(int death_year) {
        this.death_year = death_year;
    }
}
