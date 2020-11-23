package br.com.zup.casadocodigo.book.builder;

import br.com.zup.casadocodigo.category.Category;

public interface BookCategoryBuilder {
    BookAuthorBuilder withCategory(Category category);
}
