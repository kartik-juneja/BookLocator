package com.bharat.booklocator;

public class BookRequestData {
     private  String Branch ,  Semester, BookTitle, AuthorName,Publisher;

    public BookRequestData() {

    }

    public BookRequestData(String branch, String semester, String bookTitle, String authorName, String publisher) {
        Branch = branch;
        Semester = semester;
        BookTitle = bookTitle;
        AuthorName = authorName;
        Publisher = publisher;

    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }

    public String getSemester() {
        return Semester;
    }

    public void setSemester(String semester) {
        Semester = semester;
    }

    public String getBookTitle() {
        return BookTitle;
    }

    public void setBookTitle(String bookTitle) {
        BookTitle = bookTitle;
    }

    public String getAuthorName() {
        return AuthorName;
    }

    public void setAuthorName(String authorName) {
        AuthorName = authorName;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }
}
