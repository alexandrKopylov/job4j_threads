package ru.job4j.concurrent.wait;

import java.util.LinkedList;
import java.util.List;

public class CounBarrier {
    private final Object monitor = this;
    private final int total;
    private int count = 0;

    public CounBarrier(final int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            monitor.notifyAll();
            await();
        }
    }

    public void wakeUp() {
        synchronized (monitor) {
            monitor.notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            while (count >= total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CounBarrier cb = new CounBarrier(3);
        Thread first = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    cb.count();
                    System.out.println(Thread.currentThread().getName() + " want to print 11 ");
                }, "first");
        Thread second = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    cb.count();
                    System.out.println(Thread.currentThread().getName() + " want to print 22 ");
                }, "second");

        Thread third = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    cb.count();
                    System.out.println(Thread.currentThread().getName() + " want to print 33 ");
                }, "third");

        Thread fourth = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    cb.count();
                    System.out.println(Thread.currentThread().getName() + " want to print 44 ");
                }, "fourth");

        List<Thread> threads = new LinkedList<>();
        threads.add(first);
        threads.add(second);
        threads.add(third);
        threads.add(fourth);
        threads.forEach(Thread::start);
        Thread.sleep(3000);
        System.out.println(cb.count);
        System.out.println(first.getState());
    }
}
