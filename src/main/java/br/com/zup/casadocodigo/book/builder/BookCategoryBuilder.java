package br.com.zup.casadocodigo.book.builder;

import br.com.zup.casadocodigo.category.Category;

/**
 * Criação do "Twisted Builder" a partir do artigo: https://aidium.se/2015/01/09/builder-pattern-for-mandatory-values/
 *
 * - Pontos fortes:
 * 1. Mantém o estado válido do objeto
 * 2. Interface fluente
 * 3. Melhora a leitura e facilita a criação do objeto em detrimento do construtor
 *
 * - Pontos fracos:
 * 1. Alta carga intrínseca
 * 2. Verbosidade
 * 3. Lentidão na implementação
 */
public interface BookCategoryBuilder {
    BookAuthorBuilder withCategory(Category category);
}
