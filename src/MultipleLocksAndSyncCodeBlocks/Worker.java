package MultipleLocksAndSyncCodeBlocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Worker {

    private Random random = new Random();

    // Its a better practice to declare locks separately and not on lists
    // Tis is because on Java it could happen that if you are incrementing
    // a number the object can point to same number for optimization,
    // this could complicate things.

    private Object lock1 = new Object();
    private Object lock2 = new Object();

    private List<Integer> list1 = new ArrayList<Integer>();
    private List<Integer> list2 = new ArrayList<Integer>();

    //public synchronized void stageOne() throws InterruptedException {
    public void stageOne() throws InterruptedException {

        synchronized (lock1) {
            Thread.sleep(1);
            list1.add(random.nextInt(100));
        }
    }

    //public synchronized void stageTwo() throws InterruptedException {
    public void stageTwo() throws InterruptedException {
        synchronized (lock2) {
            Thread.sleep(1);
            list2.add(random.nextInt(100));
        }
    }

    public void process() throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            stageOne();
            stageTwo();
        }
    }

    public void main() throws InterruptedException {
        System.out.println("Starting...");

        long start = System.currentTimeMillis();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    process();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    process();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        long end = System.currentTimeMillis();

        System.out.println("Time taken: " + (end - start));
        System.out.println("List1: " +list1.size() +" List2: " +list2.size());

        /*
        With the basic program without synchronized keywords to method stageOne()
        and stageTwo(), we will get errors and wrong outputs since the Threads
        are interleaving and doing crazy things.

        And when we use synchronize keywords, everythng runs correctly,
        except the execution takes nearly "double" the time.

        This is inefficient because the intrinsic lock on the Worker object will
        be acquired by a thread. Thus it will have lock on all synchronised method
        of that object, even though the methods are working on two different lists in
        this case.

        To solve this problem we create multiple locks.
         */
    }
}
