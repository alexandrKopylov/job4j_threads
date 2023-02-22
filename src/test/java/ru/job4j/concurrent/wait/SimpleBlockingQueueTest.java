package ru.job4j.concurrent.wait;

import org.junit.Test;

import java.util.Set;
import java.util.TreeSet;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void when1consumer1produser() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        final int[] rsl = new int[1];
        Thread producer = new Thread(() -> {
            try {
                queue.offer(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread consumer = new Thread(() -> {
            try {
                rsl[0] = queue.poll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        producer.start();
        producer.join();
        consumer.start();
        consumer.join();

        assertThat(rsl[0], is(1));
    }

    @Test
    public void when1producer() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        Thread producer = new Thread(() -> {
            try {
                queue.offer(8);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        producer.start();
        producer.join();
        Set<Integer> rsl = new TreeSet<>();
        rsl.add(queue.poll());
        assertThat(rsl, is(Set.of(8)));
    }
}