package br.com.zup.casadocodigo.book.builder;

public interface BookSummaryBuilder {
    BookPriceBuilder withSummary(String summary);
}