package com.basic.dev.repository;

import com.basic.dev.entity.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @Order(1)
    @DisplayName("도서 등록 테스트")
    void testCreateBook() {
        Book book1 = Book.builder()
                .title("스프링 부트 입문")
                .author("홍길동")
                .isbn("9788956746425")
                .price(30000)
                .publishDate(LocalDate.of(2025, 5, 7))
                .build();

        Book book2 = Book.builder()
                .title("JPA 프로그래밍")
                .author("박둘리")
                .isbn("9788956746432")
                .price(35000)
                .publishDate(LocalDate.of(2025, 4, 30))
                .build();

        Book savedBook1 = bookRepository.save(book1);
        Book savedBook2 = bookRepository.save(book2);

        assertNotNull(savedBook1.getId());
        assertNotNull(savedBook2.getId());
    }

    @Test
    @Order(2)
    @DisplayName("ISBN으로 도서 조회 테스트")
    void testFindByIsbn() {
        Optional<Book> optionalBook = bookRepository.findByIsbn("9788956746425");

        assertTrue(optionalBook.isPresent());
        assertEquals("스프링 부트 입문", optionalBook.get().getTitle());
        assertEquals("홍길동", optionalBook.get().getAuthor());
    }

    @Test
    @Order(3)
    @DisplayName("저자명으로 도서 목록 조회 테스트")
    void testFindByAuthor() {
        List<Book> books = bookRepository.findByAuthor("박둘리");

        assertFalse(books.isEmpty());
        assertEquals(1, books.size());
        assertEquals("JPA 프로그래밍", books.get(0).getTitle());
    }

    @Test
    @Order(4)
    @DisplayName("도서 정보 수정 테스트")
    void testUpdateBook() {
        Optional<Book> optionalBook = bookRepository.findByIsbn("9788956746425");
        assertTrue(optionalBook.isPresent());

        Book book = optionalBook.get();
        book.setPrice(32000);
        book.setTitle("스프링 부트 입문 개정판");

        Book updatedBook = bookRepository.save(book);

        assertEquals(32000, updatedBook.getPrice());
        assertEquals("스프링 부트 입문 개정판", updatedBook.getTitle());
    }

    @Test
    @Order(5)
    @DisplayName("도서 삭제 테스트")
    void testDeleteBook() {
        Optional<Book> optionalBook = bookRepository.findByIsbn("9788956746432");
        assertTrue(optionalBook.isPresent());

        Book book = optionalBook.get();
        Long id = book.getId();

        bookRepository.delete(book);

        Optional<Book> deletedBook = bookRepository.findById(id);
        assertFalse(deletedBook.isPresent());
    }
}