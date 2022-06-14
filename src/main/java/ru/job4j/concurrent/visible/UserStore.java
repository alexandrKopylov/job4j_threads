package ru.job4j.concurrent.visible;

import net.jcip.annotations.ThreadSafe;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStore {

    private final Map<Integer, User> map;

    public UserStore() {
        this.map = new ConcurrentHashMap<>();
    }

    public synchronized boolean add(User user) {
        return map.putIfAbsent(user.getId(), user) == null;
    }

    public synchronized boolean update(User user) {
        return map.replace(user.getId(), map.get(user.getId()), user);
    }

    public synchronized int getUserAmount(int id) {
        return map.get(id).getAmount();
    }

    public synchronized boolean delete(User user) {
        return map.remove(user.getId(), user);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        User fromUser = map.get(fromId);
        User toUser = map.get(toId);
        boolean flag;
        if (fromUser == null || toUser == null || fromUser.getAmount() < amount) {
            System.out.println("bad parameters");
            flag = false;
        } else {
            fromUser.setAmount(fromUser.getAmount() - amount);
            toUser.setAmount(toUser.getAmount() + amount);
            flag = true;
        }
        return flag;
    }
}
