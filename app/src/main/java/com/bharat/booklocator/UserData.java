package com.bharat.booklocator;

public class UserData {
    private String name,Email,password;

    public UserData() {
    }



    public UserData(String name, String email, String password) {
        this.name = name;
        Email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
