package br.com.zup.casadocodigo.book.view;

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

    public ViewBookResponse(String title,
                            String summary,
                            String index,
                            BigDecimal price,
                            Integer totalPages,
                            String isbn,
                            LocalDate publishDate,
                            String authorName,
                            String authorDescription,
                            String categoryName) {
        this.title = title;
        this.summary = summary;
        this.index = index;
        this.price = price;
        this.totalPages = totalPages;
        this.isbn = isbn;
        this.publishDate = publishDate;
        this.authorName = authorName;
        this.authorDescription = authorDescription;
        this.categoryName = categoryName;
    }
}
