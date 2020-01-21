package ru.otus.l019.api.model;

public class User {

    @Id
    private long id;

    private String name;

    private long age;

    public User(long id, String name, long age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public User(String name, long age) {
        this.name = name;
        this.age = age;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
