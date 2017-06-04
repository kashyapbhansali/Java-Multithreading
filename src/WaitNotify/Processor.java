package WaitNotify;

import java.util.Scanner;

public class Processor {

    public void producer() throws InterruptedException {
        synchronized (this) {
            System.out.println("Producer thread running ...");
            //it waits and doesnt consume lot of system resources
            //can only be called in sync blocks.
            wait();
            System.out.println("Resumed.");
        }
    }

    public void consumer() throws InterruptedException{
        Scanner sc = new Scanner(System.in);
        Thread.sleep(2000);

        synchronized (this) {
            System.out.println("Waiting for return key");
            sc.nextLine();
            System.out.println("Return key pressed");
            //notifies to relinquish control to other thread
            //that needs lock on the same object.
            notify();
        }
    }
}
