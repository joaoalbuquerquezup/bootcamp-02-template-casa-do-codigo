package br.com.zup.casadocodigo.book.builder;

import br.com.zup.casadocodigo.book.Book;

import java.time.LocalDate;

public interface FinalBookBuilder {
    FinalBookBuilder withIndex(String index);
    FinalBookBuilder withPublishDate(LocalDate publishDate);
    Book build();
}
