package ru.job4j.concurrent.wget;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Программа должна скачивать файл из сети с ограничением по скорости скачки.
 * <p>
 * Чтобы ограничить скорость скачивания, нужно засечь время скачивания 1024 байт. Если время меньше указанного, то нужно выставить паузу за счет Thread.sleep.
 * <p>
 * Пауза должна вычисляться, а не быть константой.
 * <p>
 * <p>
 * Может кому поможет. Скорость задаем в Байт/с. Отсюда все расчеты.
 */

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(this.url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(
                     "src\\main\\java\\ru\\job4j\\concurrent\\dog.jpg")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {


                fileOutputStream.write(dataBuffer, 0, bytesRead);

                Thread.sleep(1000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);



        url =  "https://placepic.ru/wp-content/uploads/2018/10/zwalls.ru-20414.jpg";
        speed = 1;

        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}

