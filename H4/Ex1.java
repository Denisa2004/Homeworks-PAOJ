import java.util.concurrent.ConcurrentHashMap;

public class Ex1 {

    enum Status {
        RUNNING,
        COMPLETED,
        TIMED_OUT
    }

    private static ConcurrentHashMap<Integer, Status> statuses = new ConcurrentHashMap<>();
    private static final Object monitor = new Object();

    static class Task implements Runnable {
        private int id;
        private long duration;
        private volatile boolean isRunning = true;

        public Task(int id, long duration) {
            this.id = id;
            this.duration = duration;
        }

        @Override
        public void run() {
            statuses.put(id, Status.RUNNING);
            System.out.println("Task " + id + " started.");

            synchronized (monitor) {
                try {
                    long start = System.currentTimeMillis();

                    while (System.currentTimeMillis() - start < duration) {
                        if (Thread.currentThread().isInterrupted()) {
                            System.out.println("Task " + id + " was interrupted.");
                            statuses.put(id, Status.TIMED_OUT);
                            return;
                        }
                    }

                    synchronized (monitor) {
                        statuses.put(id, Status.COMPLETED);
                    }

                    System.out.println("Task " + id + " completed.");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Watchdog extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(500);
                    System.out.println("----- Watchdog Status -----");
                    for (Integer id : statuses.keySet()) {
                        System.out.println("Task " + id + " : " + statuses.get(id));
                    }
                    System.out.println("---------------------------");

                    if (statuses.values().stream().allMatch(
                            status -> status == Status.COMPLETED || status == Status.TIMED_OUT)) {
                        break;
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Main
    public static void main(String[] args) {
        int n = 5;
        long Tmax = 2000;

        Thread[] threads = new Thread[n];
        long[] durations = {1000, 1800, 2100, 3000, 1500};

        for (int i = 0; i < n; i++) {
            Task task = new Task(i + 1, durations[i]);
            threads[i] = new Thread(task);
            threads[i].start();

            long start = System.currentTimeMillis();
            while (threads[i].isAlive() && System.currentTimeMillis() - start < Tmax) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (threads[i].isAlive()) {
                threads[i].interrupt();
            }
        }

        Watchdog watchdog = new Watchdog();
        watchdog.start();
    }
}
