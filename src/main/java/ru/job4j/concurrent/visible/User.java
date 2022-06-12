package ru.job4j.concurrent.visible;

public class User {
    private final int id;
    private final int amount;

    public User(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public synchronized int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", amount=" + amount + '}';
    }
}
