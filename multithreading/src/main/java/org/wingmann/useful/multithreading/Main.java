package org.wingmann.useful.multithreading;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        var queue = new BlockingQueue();

        var worker = new Thread(() -> {
            while (true) {
                var task = queue.get();
                task.run();
            }
        });

        worker.start();

        for (var i = 0; i < 5; ++i) {
            queue.put(getTask());
        }
    }

    public static Runnable getTask() {
        return new Runnable() {
            @Override
            public void run() {
                System.out.printf("Task started (%s)%n", this);

                try {
                    for (var i = 1; i <= 5; ++i) {
                        System.out.printf("%d ", i);
                        Thread.sleep(500);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("%n    - task finished (%s)%n%n", this);
            }
        };
    }

    public static class BlockingQueue {
        ArrayList<Runnable> tasks = new ArrayList<>();

        public synchronized Runnable get() {
            while (tasks.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            var task = tasks.get(0);
            tasks.remove(task);

            return task;
        }

        public synchronized void put(Runnable task) {
            tasks.add(task);
            notify();
        }
    }
}
