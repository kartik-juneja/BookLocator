package com.bharat.booklocator;

public class searchList {

    public searchList() {

    }
    private  String  BookName,authorName,publisher,BookCode;

    public searchList(String bookName, String authorName, String publisher, String bookCode) {
        BookName = bookName;
        this.authorName = authorName;
        this.publisher = publisher;
        BookCode = bookCode;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getBookCode() {
        return BookCode;
    }

    public void setBookCode(String bookCode) {
        BookCode = bookCode;
    }


}
