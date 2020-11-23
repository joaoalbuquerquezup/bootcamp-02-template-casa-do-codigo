package br.com.zup.casadocodigo.book.builder;

import br.com.zup.casadocodigo.author.Author;

public interface BookAuthorBuilder {
    FinalBookBuilder withAuthor(Author author);
}
