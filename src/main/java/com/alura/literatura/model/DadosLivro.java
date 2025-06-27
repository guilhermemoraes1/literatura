package com.alura.literatura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivro(
        String title,
        List<AutorJson> authors,
        List<String> languages,
        int download_count
) {
    @Override
    public String toString() {
        return "\n----- LIVRO -----"
                + "\n  Titulo: " + title
                + "\n  Autor: " + (authors != null && !authors.isEmpty() ? authors.get(0).name() : "Desconhecido")
                + "\n  Idioma: " + (languages != null && !languages.isEmpty() ? languages.get(0) : "Desconhecido")
                + "\n  Número de downloads: " + download_count
                + "\n-----------------\n";
    }

}
