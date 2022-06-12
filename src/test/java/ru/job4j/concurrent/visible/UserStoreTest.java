package ru.job4j.concurrent.visible;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class UserStoreTest {
    @Test
    public void whenTransferMoney() {
        UserStore store = new UserStore();
        store.add(new User(1,100));
        store.add(new User(2,200));

        store.transfer(1, 2, 50);
        assertThat(store.getUserAmount(1), is(50));
        assertThat(store.getUserAmount(2), is(250));
    }
}