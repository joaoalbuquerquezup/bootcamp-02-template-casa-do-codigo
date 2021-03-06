package br.com.zup.casadocodigo.book;

import br.com.zup.casadocodigo.author.Author;
import br.com.zup.casadocodigo.book.builder.BookAuthorBuilder;
import br.com.zup.casadocodigo.book.builder.BookCategoryBuilder;
import br.com.zup.casadocodigo.book.builder.BookIsbnBuilder;
import br.com.zup.casadocodigo.book.builder.BookPriceBuilder;
import br.com.zup.casadocodigo.book.builder.BookSummaryBuilder;
import br.com.zup.casadocodigo.book.builder.BookTitleBuilder;
import br.com.zup.casadocodigo.book.builder.BookTotalPagesBuilder;
import br.com.zup.casadocodigo.book.builder.FinalBookBuilder;
import br.com.zup.casadocodigo.category.Category;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String title;

    @NotBlank @Size(max = 500)
    @Column(length = 500, nullable = false)
    private String summary;

    private String index;

    @NotNull
    @Min(value = 20L)
    @Column(nullable = false)
    private BigDecimal price;

    @NotNull @Min(value = 100L)
    @Column(nullable = false)
    private Integer totalPages;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String isbn;

    @Future
    private LocalDate publishDate;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Category category;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Author author;

    /**
     * Hibernate usage only
     * @see BookBuilder#aBook()
     */
    @Deprecated
    protected Book() { }

    private Book(BookBuilder bookBuilder) {
        this.title = bookBuilder.title;
        this.summary = bookBuilder.summary;
        this.index = bookBuilder.index;
        this.price = bookBuilder.price;
        this.totalPages = bookBuilder.totalPages;
        this.isbn = bookBuilder.isbn;
        this.publishDate = bookBuilder.publishDate;
        this.category = bookBuilder.category;
        this.author = bookBuilder.author;

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Book>> violationSet = validator.validate(this);
        if (!violationSet.isEmpty()) throw new ConstraintViolationException(violationSet);
    }

    public String getTitle() {
        return this.title;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

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
    public static class BookBuilder implements BookTitleBuilder, BookSummaryBuilder, BookPriceBuilder,
            BookTotalPagesBuilder, BookIsbnBuilder, BookCategoryBuilder,
            BookAuthorBuilder, FinalBookBuilder {

        private @NotBlank String title;
        private @NotBlank @Size(max = 500) String summary;
        private String index;
        private @NotNull @Min(value = 20L) BigDecimal price;
        private @NotNull @Min(value = 100L) Integer totalPages;
        private @NotBlank String isbn;
        private @Future LocalDate publishDate;
        private @NotNull Category category;
        private @NotNull Author author;

        /**
         * @see #aBook()
         */
        private BookBuilder() { }

        public static BookTitleBuilder aBook() {
            return new BookBuilder();
        }

        @Override
        public BookSummaryBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        @Override
        public BookPriceBuilder withSummary(String summary) {
            this.summary = summary;
            return this;
        }

        @Override
        public BookTotalPagesBuilder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        @Override
        public BookIsbnBuilder withTotalPages(Integer totalPages) {
            this.totalPages = totalPages;
            return this;
        }

        @Override
        public BookCategoryBuilder withIsbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        @Override
        public BookAuthorBuilder withCategory(Category category) {
            this.category = category;
            return this;
        }

        @Override
        public FinalBookBuilder withAuthor(Author author) {
            this.author = author;
            return this;
        }

        @Override
        public FinalBookBuilder withIndex(String index) {
            this.index = index;
            return this;
        }

        @Override
        public FinalBookBuilder withPublishDate(LocalDate publishDate) {
            this.publishDate = publishDate;
            return this;
        }

        @Override
        public Book build() {
            return new Book(this);
        }
    }

}
