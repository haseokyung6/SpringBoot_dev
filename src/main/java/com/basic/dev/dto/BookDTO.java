package com.basic.dev.dto;

import com.basic.dev.entity.Book;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

public class BookDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BookCreateRequest {

        @NotBlank(message = "제목은 필수입니다.")
        private String title;

        @NotBlank(message = "저자는 필수입니다.")
        private String author;

        @NotBlank(message = "ISBN은 필수입니다.")
        private String isbn;

        @NotNull(message = "가격은 필수입니다.")
        @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
        private Integer price;

        @NotNull(message = "출판일은 필수입니다.")
        private LocalDate publishDate;

        public Book toEntity() {
            return Book.builder()
                    .title(title)
                    .author(author)
                    .isbn(isbn)
                    .price(price)
                    .publishDate(publishDate)
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BookUpdateRequest {

        private String title;

        private String author;

        @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
        private Integer price;

        private LocalDate publishDate;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BookResponse {

        private Long id;
        private String title;
        private String author;
        private String isbn;
        private Integer price;
        private LocalDate publishDate;

        public static BookResponse from(Book book) {
            return BookResponse.builder()
                    .id(book.getId())
                    .title(book.getTitle())
                    .author(book.getAuthor())
                    .isbn(book.getIsbn())
                    .price(book.getPrice())
                    .publishDate(book.getPublishDate())
                    .build();
        }
    }
}