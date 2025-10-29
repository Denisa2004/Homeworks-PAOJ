import java.util.*;
import java.util.concurrent.TimeUnit;

public class Ex2 {

    private static final List<Integer> bufferA = Collections.synchronizedList(new ArrayList<>());
    private static final List<Integer> bufferB = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {
        simulareCuDeadlock();
        //simulareFaraDeadlock();
    }

    private static void simulareCuDeadlock() {
        System.out.println("----Simulare cu deadlock----");

        Thread producer1 = new Thread(() -> {
            synchronized (bufferA) {
                System.out.println("P1 locked BufferA");
                sleep(100);
                synchronized (bufferB) {
                    System.out.println("P1 locked BufferB");
                    bufferA.add(1);
                    bufferB.add(2);
                    System.out.println("P1 a produs.");
                }
            }
        });

        Thread producer2 = new Thread(() -> {
            synchronized (bufferB) {
                System.out.println("P2 locked BufferB");
                sleep(100);
                synchronized (bufferA) {
                    System.out.println("P2 locked BufferA");
                    bufferA.add(3);
                    bufferB.add(4);
                    System.out.println("P2 a produs.");
                }
            }
        });

        Thread consumer1 = new Thread(() -> {
            synchronized (bufferA) {
                System.out.println("C1 locked BufferA");
                sleep(100);
                synchronized (bufferB) {
                    System.out.println("C1 locked BufferB");
                    System.out.println("C1 a consumat: " + bufferA + " și " + bufferB);
                }
            }
        });

        Thread consumer2 = new Thread(() -> {
            synchronized (bufferB) {
                System.out.println("C2 locked BufferB");
                sleep(100);
                synchronized (bufferA) {
                    System.out.println("C2 locked BufferA");
                    System.out.println("C2 a consumat: " + bufferA + " și " + bufferB);
                }
            }
        });

        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();

        try {
            producer1.join(3000);
            producer2.join(3000);
            consumer1.join(3000);
            consumer2.join(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (producer1.isAlive() || producer2.isAlive() || consumer1.isAlive() || consumer2.isAlive()) {
            System.out.println("Deadlock detectat intre thread-uri");
        } else {
            System.out.println("Program terminat fara deadlock.");
        }

        bufferA.clear();
        bufferB.clear();
    }

    private static void simulareFaraDeadlock() {
        System.out.println("\n----Simulare fara deadlock (lock ordering)-----");

        Thread producer3 = new Thread(() -> {
            synchronized (bufferA) {
                System.out.println("P3 locked BufferA");
                sleep(100);
                synchronized (bufferB) {
                    System.out.println("P3 locked BufferB");
                    bufferA.add(5);
                    bufferB.add(6);
                    System.out.println("P3 a produs.");
                }
            }
        });

        Thread producer4 = new Thread(() -> {
            synchronized (bufferA) {
                System.out.println("P4 locked BufferA");
                sleep(100);
                synchronized (bufferB) {
                    System.out.println("P4 locked BufferB");
                    bufferA.add(7);
                    bufferB.add(8);
                    System.out.println("P4 a produs.");
                }
            }
        });

        Thread consumer3 = new Thread(() -> {
            synchronized (bufferA) {
                System.out.println("C3 locked BufferA");
                sleep(100);
                synchronized (bufferB) {
                    System.out.println("C3 locked BufferB");
                    System.out.println("C3 a consumat: " + bufferA + " și " + bufferB);
                }
            }
        });

        Thread consumer4 = new Thread(() -> {
            synchronized (bufferA) {
                System.out.println("C4 locked BufferA");
                sleep(100);
                synchronized (bufferB) {
                    System.out.println("C4 locked BufferB");
                    System.out.println("C4 a consumat: " + bufferA + " și " + bufferB);
                }
            }
        });

        producer3.start();
        producer4.start();
        consumer3.start();
        consumer4.start();

        try {
            producer3.join();
            producer4.join();
            consumer3.join();
            consumer4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Program terminat fara deadlock!!");
    }

    private static void sleep(long ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
