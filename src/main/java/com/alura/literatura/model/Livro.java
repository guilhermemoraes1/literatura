package com.alura.literatura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique=true)
    private String title;
    private String language;
    private long download_count;

    @ManyToOne
    private Autor autor;

    public Livro() {}

    public Livro (DadosLivro dadosLivro) {
        this.title = dadosLivro.title();
        this.language = dadosLivro.languages().getFirst();
        this.download_count = dadosLivro.download_count();
    }

    @Override
    public String toString() {
        return "\n----- LIVRO -----"
                + "\n  Titulo: " + this.title
                + "\n  Autor: " + (autor != null ? autor.getName() : "Autor desconhecido")
                + "\n  Idioma: " + this.language
                + "\n  Número de downloads: " + this.download_count
                + "\n-----------------\n";
    }


    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getDownload_count() {
        return download_count;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Autor getAutor() {
        return autor;
    }

    public String getLanguage() { return language; }
}
