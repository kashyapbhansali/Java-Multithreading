package ThreadPools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Processor implements Runnable {

    private int id;

    public Processor(int id) {
        this.id = id;
    }
    public void run() {
        System.out.println("Starting: " +id);

        try {
            Thread.sleep(5000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Completed: " +id);
    }
}
public class App {
    public static void main(String[] args) {

        //Ex. We create 2 factory workers and executor runs its own managerial thread.
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Where i is the number of tasks
        // So we want 2 factory workers to work together on 5 tasks one after the other.
        for (int i = 0; i < 5; i++) {
            executor.submit(new Processor(i));
        }

        executor.shutdown();

        System.out.println("All tasks submitted");

        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All tasks submitted");
    }
}
