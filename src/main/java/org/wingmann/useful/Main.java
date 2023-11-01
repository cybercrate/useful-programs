package org.wingmann.useful;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        BlockingQueue queue = new BlockingQueue();

        Thread worker = new Thread(() -> {
            while (true) {
                Runnable task = queue.get();
                task.run();
            }
        });

        worker.start();

        for (int i = 0; i < 5; ++i) {
            queue.put(getTask());
        }
    }

    public static Runnable getTask() {
        return new Runnable() {
            @Override
            public void run() {
                System.out.printf("Task started (%s)%n", this);

                try {
                    for (int i = 1; i <= 5; ++i) {
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

            Runnable task = tasks.get(0);
            tasks.remove(task);

            return task;
        }

        public synchronized void put(Runnable task) {
            tasks.add(task);
            notify();
        }
    }
}
