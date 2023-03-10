package com.driver;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("books")
public class BookController {

    private List<Book> bookList;
    private int id;

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookController(){
        this.bookList = new ArrayList<Book>();
        this.id = 0;
    }

    // post request /create-book
    // pass book as request body
    @PostMapping("/create-book")
    public ResponseEntity<Book> createBook(@RequestBody Book book){
        // Your code goes here.
        book.setId(id);
        getBookList().add(id,book);
        id++;

        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    // get request /get-book-by-id/{id}
    // pass id as path variable
    // getBookById()
    @GetMapping("/get-book-by-id/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") String id){
        int n = Integer.parseInt(id);
        for(Book book: getBookList()){
            if(book.getId()==n){
                return new ResponseEntity<>(book,HttpStatus.FOUND);
            }
        }

        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }

    // delete request /delete-book-by-id/{id}
    // pass id as path variable
    // deleteBookById()
    @DeleteMapping("/delete-book-by-id/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable("id") String id){
        int n = Integer.parseInt(id);
        for(Book book: getBookList()){
            if(book.getId()==n){
                getBookList().remove(book);
                return new ResponseEntity<>("Deleted",HttpStatus.ACCEPTED);
            }
        }

        return new ResponseEntity<>("No book with id found",HttpStatus.NOT_FOUND);
    }

    // get request /get-all-books
    // getAllBooks()
    @GetMapping("/get-all-books")
    public ResponseEntity<List<Book>> getAllBooks(){
        return new ResponseEntity<>(getBookList(),HttpStatus.OK);
    }

    // delete request /delete-all-books
    // deleteAllBooks()
    @DeleteMapping("/delete-all-books")
    public ResponseEntity<String> deleteAllBooks(){
        getBookList().clear();
        return new ResponseEntity<>("All books are deleted", HttpStatus.OK);
    }

    // get request /get-books-by-author
    // pass author name as request param
    // getBooksByAuthor()
    @GetMapping("/get-books-by-author")
    public ResponseEntity<List<Book>> getBooksByAuthor(@RequestParam String author){
        List<Book> list = new ArrayList<>();
        for(Book book: getBookList()){
            if(book.getAuthor().equals(author)){
                list.add(book);
            }
        }
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    // get request /get-books-by-genre
    // pass genre name as request param
    // getBooksByGenre()

    @GetMapping("/get-books-by-genre")
    public ResponseEntity<List<Book>> getBooksByGenre(@RequestParam String genre){
        List<Book> list = new ArrayList<>();
        for(Book book: getBookList()){
            if(book.getGenre().equals(genre)){
                list.add(book);
            }
        }
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
}
