package com.basic.dev.service;

import com.basic.dev.dto.BookDTO;
import com.basic.dev.entity.Book;
import com.basic.dev.exception.BusinessException;
import com.basic.dev.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<BookDTO.BookResponse> getAllBooks(){
        return bookRepository.findAll()
                .stream()
                .map(BookDTO.BookResponse::from)
                .toList();
    }
    public BookDTO.BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("해당 ID의 도서를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        return BookDTO.BookResponse.from(book);
    }

    public BookDTO.BookResponse getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BusinessException("해당 ISBN의 도서를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        return BookDTO.BookResponse.from(book);
    }

    public List<BookDTO.BookResponse> getBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author)
                .stream()
                .map(BookDTO.BookResponse::from)
                .toList();
    }
    @Transactional
    public BookDTO.BookResponse createBook(BookDTO.BookCreateRequest request) {
        if (bookRepository.findByIsbn(request.getIsbn()).isPresent()) {
            throw new BusinessException("이미 등록된 ISBN입니다.", HttpStatus.BAD_REQUEST);
        }

        Book savedBook = bookRepository.save(request.toEntity());
        return BookDTO.BookResponse.from(savedBook);
    }

    @Transactional
    public BookDTO.BookResponse updateBook(Long id, BookDTO.BookUpdateRequest request) {
        Book existBook = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("수정할 도서를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        // 변경이 필요한 필드만 업데이트
        if (request.getTitle() != null) {
            existBook.setTitle(request.getTitle());
        }

        if (request.getAuthor() != null) {
            existBook.setAuthor(request.getAuthor());
        }

        if (request.getPrice() != null) {
            existBook.setPrice(request.getPrice());
        }

        if (request.getPublishDate() != null) {
            existBook.setPublishDate(request.getPublishDate());
        }

        Book updatedBook = bookRepository.save(existBook);
        return BookDTO.BookResponse.from(updatedBook);
    }

    @Transactional
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("삭제할 도서를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        bookRepository.delete(book);
    }
}
