package br.com.zup.casadocodigo.book.view;

import br.com.zup.casadocodigo.book.Book;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.math.BigDecimal;
import java.time.LocalDate;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ViewBookResponse {

    private String title;
    private String summary;
    private String index;
    private BigDecimal price;
    private Integer totalPages;
    private String isbn;
    private LocalDate publishDate;

    private String authorName;
    private String authorDescription;

    private String categoryName;

    public ViewBookResponse(Book book, String categoryName, String authorName, String authorDescription) {
        this.title = book.getTitle();
        this.summary = book.getSummary();
        this.index = book.getIndex();
        this.price = book.getPrice();
        this.totalPages = book.getTotalPages();
        this.isbn = book.getIsbn();
        this.publishDate = book.getPublishDate();
        this.authorName = authorName;
        this.authorDescription = authorDescription;
        this.categoryName = categoryName;
    }
}
