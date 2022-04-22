package ru.job4j.concurrent;

public final class DCLSingleton {

    private static volatile DCLSingleton inst;

    public static DCLSingleton instOf() {
        if (inst == null) {
            synchronized (DCLSingleton.class) {
                if (inst == null) {
                    inst = new DCLSingleton();
                }
            }
        }
        return inst;
    }

    private DCLSingleton() {
    }

    public static void main(String[] args) {
        DCLSingleton dcl = DCLSingleton.instOf();
        DCLSingleton dcl2 = DCLSingleton.instOf();
        System.out.println(dcl == dcl2);
    }
}