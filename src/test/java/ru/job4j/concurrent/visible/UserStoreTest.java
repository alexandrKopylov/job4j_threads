package ru.job4j.concurrent.visible;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class UserStoreTest {
    @Test
    public void whenTransferMoney() {
        UserStore store = new UserStore();
        User borya = new User(1, 100);
        store.add(borya);
        User sasa = new User(2, 200);
        store.add(sasa);
        store.transfer(1, 2, 50);
        assertThat(borya.getAmount(), is(50));
        assertThat(sasa.getAmount(), is(250));
    }
}