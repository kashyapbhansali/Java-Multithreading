package ReentrantLocks;

//re-entrant is alternative to synchronized keywords

import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Runner {

    private int count = 0;
    //Reentrant means once a thread acquire this lock, it can lock it again
    // if it wants to and it keeps a count and then we have
    // to unlock it that many number  of times.
    private Lock lock = new ReentrantLock();
    // We use Condition class to make use of await and signal methods
    //which is similar to wait and notify for Object class.
    private Condition cond = lock.newCondition();

    private void increment() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }
    
    public void firstThread() throws InterruptedException {
        lock.lock();
        System.out.println("Waiting....");
        cond.await();

        System.out.println("Woken up!");
        /*
        It is important to use the try block and then unlock inside the finally
        block because if increment throws an exception it will be a problem
        as it will never reach the unlocking call
         */
        try {
            increment();
        } finally {
            lock.unlock();
        }


    }

    public void secondThread() throws InterruptedException {

        Thread.sleep(1000);
        lock.lock();

        System.out.println("Press retun key");
        new Scanner(System.in).nextLine();
        System.out.println("retun key pressed");

        cond.signal();

        try {
            increment();
        } finally {
            lock.unlock();
        }
    }

    public void finished() {
        System.out.println("Count is: " +count);

    }
}
