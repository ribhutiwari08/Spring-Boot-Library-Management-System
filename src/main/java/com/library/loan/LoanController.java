package com.library.loan;

import com.library.book.Book;
import com.library.book.BookRepository;
import com.library.member.Member;
import com.library.member.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
@CrossOrigin
public class LoanController {
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public LoanController(LoanRepository loanRepository, BookRepository bookRepository, MemberRepository memberRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    @GetMapping
    public List<Loan> getAll() {
        return loanRepository.findAll();
    }

    @GetMapping("/active")
    public List<Loan> getActive() {
        return loanRepository.findByStatus(LoanStatus.ISSUED);
    }

    @PostMapping("/issue")
    @ResponseStatus(HttpStatus.CREATED)
    public Loan issue(@RequestParam Long bookId, @RequestParam Long memberId) {
        Book book = bookRepository.findById(bookId).orElseThrow();
        Member member = memberRepository.findById(memberId).orElseThrow();
        if (book.getAvailableCopies() <= 0) {
            throw new IllegalStateException("No available copies for this book");
        }
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setMember(member);
        loan.setIssueDate(LocalDate.now());
        loan.setStatus(LoanStatus.ISSUED);
        return loanRepository.save(loan);
    }

    @PostMapping("/{id}/return")
    public Loan returnBook(@PathVariable Long id) {
        Loan loan = loanRepository.findById(id).orElseThrow();
        if (loan.getStatus() == LoanStatus.RETURNED) {
            throw new IllegalStateException("Loan is already returned");
        }
        Book book = loan.getBook();
        book.setAvailableCopies(Math.min(book.getTotalCopies(), book.getAvailableCopies() + 1));
        bookRepository.save(book);
        loan.setReturnDate(LocalDate.now());
        loan.setStatus(LoanStatus.RETURNED);
        return loanRepository.save(loan);
    }
}
