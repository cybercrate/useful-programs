package org.wingmann.useful.multithreading;

public class Synchronized {
    private static int counter;

    public static void first() {
        var runnable = new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    counter = 0;

                    for (var i = 0; i < 5; ++i) {
                        ++counter;
                        System.out.printf("%s: %d%n", Thread.currentThread().getName(), counter);
                    }
                }
            }
        };

        var thread1 = new Thread(runnable);
        var thread2 = new Thread(runnable);

        thread1.start();
        thread2.start();
    }

    public static void second() {
        var stringBuffer = new StringBuffer();

        var runnable = new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    stringBuffer
                            .append("Lorem ipsum dolor sit amet ")
                            .append(Thread.currentThread().getName())
                            .append('\n');

                    System.out.println(stringBuffer);
                }
            }
        };

        var thread1 = new Thread(runnable);
        var thread2 = new Thread(runnable);
        var thread3 = new Thread(runnable);
        var thread4 = new Thread(runnable);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }
}
