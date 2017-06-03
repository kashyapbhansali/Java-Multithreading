package basic_synchronization;

import java.util.Scanner;

class Processor extends Thread {
    /*
     Volatile keyword is used to prevent the caching of variable
     by threads when they are not changed from within that specific thread.
     So it is important to use volatile, when variable are changed by
     some other thread like the main thread in this case.

     Ex. Below thread p1 doesnt modify running, so it caches it.
     But the main thread changes running with shutdown().
     In some implementations of java this will not stop the
     execution of thread p1, since p1 is using cached version of
     (var running = true). Volatile helps to avoid this scenario.
     */
    private volatile boolean running = true; // see explanation above

    public void run() {

        while(running) {  // see explanation above
            System.out.println("Hello");

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutdown() {
        running = false;
    }
}


public class VolatileKeyword {
    public static void main(String[] args) {
        Processor p1 = new Processor();
        p1.start();

        System.out.println("Press ENTER to stop...");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();


        p1.shutdown();
    }
}