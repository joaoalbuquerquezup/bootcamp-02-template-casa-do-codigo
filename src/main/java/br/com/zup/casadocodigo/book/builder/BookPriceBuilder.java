package br.com.zup.casadocodigo.book.builder;

import java.math.BigDecimal;

public interface BookPriceBuilder {
    BookTotalPagesBuilder withPrice(BigDecimal price);
}
