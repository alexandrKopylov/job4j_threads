package ru.job4j.concurrent.wait;

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

        }
    }


    public void await() {
        count();
        synchronized (monitor) {
            while (count <= total) {
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
                    cb.await();
                    System.out.println(Thread.currentThread().getName() + "  print 11 ");
                }, "first");
        Thread second = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    cb.await();
                    System.out.println(Thread.currentThread().getName() + "  print 22 ");
                }, "second");

        Thread third = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    cb.await();
                    System.out.println(Thread.currentThread().getName() + "  print 33 ");
                }, "third");

        Thread fourth = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    cb.await();
                    System.out.println(Thread.currentThread().getName() + "  print 44 ");
                }, "fourth");

        first.start();
        Thread.sleep(2000);

        second.start();
        Thread.sleep(2000);

        third.start();
        Thread.sleep(2000);

        fourth.start();

        System.out.println(cb.count);
        System.out.println(first.getState());
        System.out.println(second.getState());
        System.out.println(third.getState());
        System.out.println(fourth.getState());
    }
}
