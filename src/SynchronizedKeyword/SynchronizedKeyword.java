package SynchronizedKeyword;

public class SynchronizedKeyword {
    private int count = 0;

    //see expalantion below
    public synchronized void incrementCount() {
        count++;
    }

    public static void main(String[] args){

        SynchronizedKeyword app = new SynchronizedKeyword();
        app.doWork();
    }

    public void doWork() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000 ; i++) {
                    incrementCount();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000 ; i++) {
                    incrementCount();
                }
            }
        });

        t1.start();
        t2.start();

        /*
         By adding join() here, We wait at this point for our 2 threads
         to finish. and then execute the rest of the program after this.
         So the expected output is count == 20000

         Without this join() methods, the main thread will simply execute and
         our output for this count will be 0; Since the threads havent finished
         running their loops.

         Here the output could vary like 18000, 11500, etc... seems random.
         This is because count = count + 1 is a 3 step process. read, add, update.
         The 2 threads can read and update different values of the count var,
         that is why the random nature!

         Thus we use the "synchronized" keyword. It creates something like a lock
         or critical section where at a time on one thread has the control of
         the method.
        */

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }


        System.out.print("Count is: " +count);
    }
}
