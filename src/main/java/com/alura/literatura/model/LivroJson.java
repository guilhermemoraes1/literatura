package com.alura.literatura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LivroJson(

        String title,
        List<AutorJson> authors,
        List<String> languages,
        String download_count
) {}
