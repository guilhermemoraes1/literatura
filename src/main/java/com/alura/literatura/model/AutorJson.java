package com.alura.literatura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AutorJson(String name, int birth_year, int death_year) {
    @Override
    public String name() {
        return name;
    }

    @Override
    public int birth_year() {
        return birth_year;
    }

    @Override
    public int death_year() {
        return death_year;
    }
}
