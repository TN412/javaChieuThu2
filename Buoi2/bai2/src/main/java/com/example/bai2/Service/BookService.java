package com.example.bai2.Service;

import com.example.bai2.Model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private List<Book> books = new ArrayList<>();
    private int nextId = 1;

    public BookService() {
        // Thêm một số sách mẫu
        books.add(new Book(nextId++, "Spring Boot in Action", "Craig Walls"));
        books.add(new Book(nextId++, "Clean Code", "Robert C. Martin"));
        books.add(new Book(nextId++, "Effective Java", "Joshua Bloch"));
    }

    // Lấy danh sách tất cả sách
    public List<Book> getAllBooks() {
        return books;
    }

    // Lấy sách theo ID
    public Optional<Book> getBookById(int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst();
    }

    // Thêm sách mới
    public Book addBook(Book book) {
        book.setId(nextId++);
        books.add(book);
        return book;
    }

    // Cập nhật thông tin sách
    public Optional<Book> updateBook(int id, Book updatedBook) {
        Optional<Book> existingBook = getBookById(id);
        if (existingBook.isPresent()) {
            Book book = existingBook.get();
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            return Optional.of(book);
        }
        return Optional.empty();
    }

    // Xóa sách theo ID
    public boolean deleteBook(int id) {
        return books.removeIf(book -> book.getId() == id);
    }
}
