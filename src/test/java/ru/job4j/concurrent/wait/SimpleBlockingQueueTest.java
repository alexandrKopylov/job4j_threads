package ru.job4j.concurrent.wait;

import org.junit.Test;
import java.util.Set;
import java.util.TreeSet;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void when1consumer1produser() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        final int[] rsl = new int[1];
        Thread producer = new Thread(() -> {
            queue.offer(1);
        });
        Thread consumer = new Thread(() -> {
            rsl[0] = queue.poll();
        });
        producer.start();
        producer.join();
        consumer.start();
        consumer.join();

        assertThat(rsl[0], is(1));
    }

    @Test
    public void when1producer() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread producer = new Thread(() -> {
            queue.offer(8);
        });
        producer.start();
        producer.join();
        Set<Integer> rsl = new TreeSet<>();
        rsl.add(queue.poll());
        assertThat(rsl, is(Set.of(8)));
    }
}