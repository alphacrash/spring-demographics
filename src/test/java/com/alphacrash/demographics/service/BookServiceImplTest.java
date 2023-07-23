package com.alphacrash.demographics.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;


class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testUpdateBook() {
        // Arrange
        long bookId = 1L;
        BookPayloadDTO bookPayloadDTO = new BookPayloadDTO();
        Book book = new Book();
        book.setBookId(bookId);
        Book updatedBook = new Book();
        updatedBook.setBookId(bookId);
        BookResponseDTO expectedResponse = new BookResponseDTO();
        expectedResponse.setBookId(String.valueOf(bookId));

        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        Mockito.when(mapper.map(bookPayloadDTO, Book.class)).thenReturn(book);
        Mockito.when(bookRepository.save(book)).thenReturn(updatedBook);
        Mockito.when(mapper.map(updatedBook, BookResponseDTO.class)).thenReturn(expectedResponse);

        // Act
        BookResponseDTO actualResponse = bookService.updateBook(bookPayloadDTO, bookId);

        // Assert
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testUpdateBookNotFound() {
        // Arrange
        long bookId = 1L;
        BookPayloadDTO bookPayloadDTO = new BookPayloadDTO();

        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(RuntimeException.class, () -> bookService.updateBook(bookPayloadDTO, bookId));
    }
}