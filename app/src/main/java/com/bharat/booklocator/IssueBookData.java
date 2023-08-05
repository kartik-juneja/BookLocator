package com.bharat.booklocator;

public class IssueBookData {

    private String StudentName,EmailAddress,BookName,AuthorName,IssueDate,ReturnDate;

    public IssueBookData() {

    }

    public IssueBookData(String studentName, String emailAddress, String bookName, String authorName, String issueDate, String returnDate) {
        StudentName = studentName;
        EmailAddress = emailAddress;
        BookName = bookName;
        AuthorName = authorName;
        IssueDate = issueDate;
        ReturnDate = returnDate;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public String getAuthorName() {
        return AuthorName;
    }

    public void setAuthorName(String authorName) {
        AuthorName = authorName;
    }

    public String getIssueDate() {
        return IssueDate;
    }

    public void setIssueDate(String issueDate) {
        IssueDate = issueDate;
    }

    public String getReturnDate() {
        return ReturnDate;
    }

    public void setReturnDate(String returnDate) {
        ReturnDate = returnDate;
    }
}
