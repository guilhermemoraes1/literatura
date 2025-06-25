package com.alura.literatura.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
