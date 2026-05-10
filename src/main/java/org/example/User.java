package org.example;

public abstract class User {
    private final String name;
    private final int id;

    public User(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public abstract String getUserInfo();

    public int getId() {
        return id;
    }

    public String getName() {

        return name;
    }
}
