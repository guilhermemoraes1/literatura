package com.alura.literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivro(
        @JsonAlias("results") List<LivroJson> resultado
) {
    @Override
    public String toString() {
        return "\n----- LIVRO -----"
                + "\n  Titulo: " + resultado.getFirst().title()
                + "\n  Autor: " + resultado.getFirst().authors().getFirst().name()
                + "\n  Idioma: " + resultado.getFirst().languages().getFirst()
                + "\n  Número de downloads: " + resultado.getFirst().download_count()
                + "\n-----------------\n";
    }
}


