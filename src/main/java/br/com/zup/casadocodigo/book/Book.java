package br.com.zup.casadocodigo.book;

import br.com.zup.casadocodigo.author.Author;
import br.com.zup.casadocodigo.category.Category;
import br.com.zup.casadocodigo.validation.Unique;

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
    }

    public static class BookBuilder {

        private @NotBlank @Unique(clazz = Book.class) String title;
        private @NotBlank @Size(max = 500) String summary;
        private String index;
        private @NotNull @Min(value = 20L) BigDecimal price;
        private @NotNull @Min(value = 100L) Integer totalPages;
        private @NotBlank @Unique(clazz = Book.class) String isbn;
        private @Future LocalDate publishDate;
        private @NotNull Category category;
        private @NotNull Author author;

        /**
         * @see #aBook()
         */
        private BookBuilder() {
        }

        public static BookBuilder aBook() {
            return new BookBuilder();
        }

        public BookBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public BookBuilder withSummary(String summary) {
            this.summary = summary;
            return this;
        }

        public BookBuilder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public BookBuilder withTotalPages(Integer totalPages) {
            this.totalPages = totalPages;
            return this;
        }

        public BookBuilder withIsbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public BookBuilder withCategory(Category category) {
            this.category = category;
            return this;
        }

        public BookBuilder withAuthor(Author author) {
            this.author = author;
            return this;
        }

        public BookBuilder withIndex(String index) {
            this.index = index;
            return this;
        }

        public BookBuilder withPublishDate(LocalDate publishDate) {
            this.publishDate = publishDate;
            return this;
        }

        public Book build() {
            Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
            Set<ConstraintViolation<BookBuilder>> violationSet = validator.validate(this);
            if (!violationSet.isEmpty()) throw new ConstraintViolationException(violationSet);

            return new Book(this);
        }
    }

}
