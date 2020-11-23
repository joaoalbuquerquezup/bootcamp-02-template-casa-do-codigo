package br.com.zup.casadocodigo.book;

import br.com.zup.casadocodigo.author.Author;
import br.com.zup.casadocodigo.category.Category;
import br.com.zup.casadocodigo.validation.Unique;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.LongFunction;

import static br.com.zup.casadocodigo.book.Book.BookBuilder.aBook;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CreateBookRequest {

    @NotBlank @Unique(clazz = Book.class)
    private String title;

    @NotBlank @Size(max = 500)
    private String summary;

    private String index;

    @NotNull @Min(value = 20L)
    private BigDecimal price;

    @NotNull @Min(value = 100L)
    private Integer totalPages;

    @NotBlank @Unique(clazz = Book.class)
    private String isbn;

    @Future
    private LocalDate publishDate;

    @NotNull
    private Long categoryId;

    @NotNull
    private Long authorId;

    public Book toModel(LongFunction<Category> categoryLoader,
                        LongFunction<Author> authorLoader) {
        return aBook()
            .withTitle(this.title)
            .withSummary(this.summary)
            .withPrice(this.price)
            .withTotalPages(this.totalPages)
            .withIsbn(this.isbn)
            .withCategory(categoryLoader.apply(this.categoryId))
            .withAuthor(authorLoader.apply(this.authorId))
            .withIndex(this.index)
            .withPublishDate(this.publishDate)
            .build();
    }
}
