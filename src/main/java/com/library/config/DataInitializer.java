package com.library.config;

import com.library.book.Book;
import com.library.book.BookRepository;
import com.library.member.Member;
import com.library.member.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner seed(BookRepository books, MemberRepository members) {
        return args -> {
            if (books.count() == 0) {
                books.save(book("Clean Code", "Robert C. Martin", "9780132350884", 5));
                books.save(book("Effective Java", "Joshua Bloch", "9780134685991", 3));
                books.save(book("Head First Java", "Kathy Sierra", "9780596009205", 4));
            }
            if (members.count() == 0) {
                members.save(member("Ribhu Tiwari", "ribhu@example.com"));
                members.save(member("Aman Sharma", "aman@example.com"));
            }
        };
    }

    private Book book(String title, String author, String isbn, int copies) {
        Book b = new Book();
        b.setTitle(title); b.setAuthor(author); b.setIsbn(isbn); b.setTotalCopies(copies); b.setAvailableCopies(copies);
        return b;
    }

    private Member member(String name, String email) {
        Member m = new Member(); m.setName(name); m.setEmail(email); return m;
    }
}
