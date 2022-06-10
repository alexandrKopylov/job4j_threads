package ru.job4j.concurrent.visible;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;

@ThreadSafe
public class UserStore {

    private List<User> list;

    public UserStore() {
        this.list = new ArrayList<>();
    }

    public boolean add(User user) {
        return list.add(user);
    }

    public boolean update(User user) {
        return false;
    }

    public boolean delete(User user) {
        return list.remove(user);
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
        User[] users = find2User(fromId, toId);
        users[0].setAmount(users[0].getAmount() - amount);
        users[1].setAmount(users[1].getAmount() + amount);
    }

    private synchronized User[] find2User(int fromId, int toId) {

        User[] users = new User[2];
        boolean boolFrom = true;
        boolean boolTo = true;

        for (User user : list) {
            if (boolFrom) {
                if (user.getId() == fromId) {
                   users[0] = user;
                    boolFrom = false;
                    continue;
                }
            }

            if (boolTo) {
                if (user.getId() == toId) {
                    users[1] = user;
                    boolTo = false;
                }
            }
            if (!boolFrom && !boolTo) {
                break;
            }
        }
        return users;
    }


    public void size() {
        System.out.println(list.size());
    }

    public void print() {
        for (User user : list) {
            System.out.println(user);
        }
    }

    public static void main(String[] args) {
        UserStore store = new UserStore();

        store.add(new User(1, 100));
        store.add(new User(2, 200));
        store.add(new User(3, 200));
        store.add(new User(4, 200));
        store.add(new User(5, 200));

        store.print();
        store.transfer(1, 2, 50);

        System.out.println("======");
        store.print();
    }
}
