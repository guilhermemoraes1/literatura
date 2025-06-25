package com.alura.literatura.repository;

import com.alura.literatura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    Optional<Livro> findByTitle(String title);
    List<Livro> findByLanguage(String idioma);
}
