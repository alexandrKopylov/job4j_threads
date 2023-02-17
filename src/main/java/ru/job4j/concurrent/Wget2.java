package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

/**
 * Программа должна скачивать файл из сети с ограничением по скорости скачки.
 * <p>
 * 3. По определению скорости скачивания - учтите следующие рекомендации
 * - для начала определимся в каких единицах мы будем измерять скорость скачивания - пусть это будут байты в секунду.
 * Если качаем со скоростью 1 мб / с -> то это будет 1048576 байт
 * - до цикла добавляем переменную int downloadData и присваиваем значение 0
 * - в цикле наращиваем значение этой переменной за счет bytesRead
 * - как только downloadData достигла значения speed - проверяем за сколько времени это произошло
 * --- если время между текущим моментом и стартом меньше секунды - то ставим на паузу на величину 1с минус интервал между
 * текущим моментом и старт
 * --- после паузы обнуляем downloadData и заново отсекаем start
 */
public class Wget2 implements Runnable {
    private final static int MB = 1048576;
    private final String url;
    private final int speed;

    public Wget2(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        String filename = "";
        try {
            filename = Paths.get(new URI(url).getPath()).getFileName().toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(filename)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int downloadData = 0;
            long diff;
            long start = System.currentTimeMillis();

            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                downloadData += bytesRead;
                if (downloadData > (speed * MB)) {
                    diff = System.currentTimeMillis() - start;
                    if (diff < 1000) {
                        Thread.sleep(1000 - diff);
                    }
                    downloadData = 0;
                    start = System.currentTimeMillis();
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    /**
     * @param args 1) url                  example :  https: proof.ovh.net files 10Mb.dat
     *             2) Speed downloading    example : 1 = 1Mbs,  5 = 5 Mbs
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        if (args.length != 2) {
            throw new IllegalArgumentException("wrong count arguments (there should be 2 arguments:"
                    + "URL and Speed downloading)");
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget2(url, speed));
        wget.start();
        wget.join();
    }
}