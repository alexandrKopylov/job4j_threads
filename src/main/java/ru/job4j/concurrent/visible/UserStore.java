package ru.job4j.concurrent.visible;

import net.jcip.annotations.ThreadSafe;

import java.util.*;

@ThreadSafe
public class UserStore {

    private final Map<Integer, User> map;

    public UserStore() {
        this.map = new HashMap<>();
    }

    public boolean add(User user) {
        boolean flag = false;
        User tmp = map.putIfAbsent(user.getId(), user);
        if (tmp == null) {
            flag = true;
        }
        return flag;
    }

    public synchronized boolean update(User user) {
        boolean flag = map.containsKey(user.getId());
        if (flag) {
            map.replace(user.getId(), user);
        }
        return flag;
    }

    public int getUserAmount(int id) {
        return map.get(id).getAmount();
    }

    public boolean delete(User user) {
        return map.remove(user.getId(), user);
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
        User fromUser = map.get(fromId);
        User toUser = map.get(toId);
        if (fromUser == null || toUser == null) {
            throw new IllegalStateException("no user with id =" + fromId + "or id =" + toId);
        }
        if (fromUser.getAmount() < amount) {
            throw new IllegalArgumentException("the sender doesn't have enough money");
        }
        update(new User(fromId, fromUser.getAmount() - amount));
        update(new User(toId, toUser.getAmount() + amount));
    }
}
