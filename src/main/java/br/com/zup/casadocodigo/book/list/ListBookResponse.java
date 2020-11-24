package br.com.zup.casadocodigo.book.list;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ListBookResponse {

    private Long id;
    private String title;

    public ListBookResponse(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
