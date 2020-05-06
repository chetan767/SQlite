package com.example.sqlite;

public class adapter_item {
    String user;
    String pass;
    String id;
    public adapter_item(String id,String user,String pass)
    {
        this.user = user;
        this.pass = pass;
        this.id = id;
    }

    public String getPass() {
        return pass;
    }

    public String getUser() {
        return user;
    }

    public String getId() {
        return id;
    }
}
