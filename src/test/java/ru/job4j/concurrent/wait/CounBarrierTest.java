package ru.job4j.concurrent.wait;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CounBarrierTest {
    @Test
    public void whenCount() {
        CounBarrier cb = new CounBarrier(3);
        Thread t1 = new Thread(() -> {
            cb.count();
        });
        Thread t2 = new Thread(() -> {
            cb.count();
        });
        Thread t3 = new Thread(() -> {
            cb.count();
        });
        t1.start();
        t2.start();
        t3.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(cb.getCount(), is(3));
    }

}