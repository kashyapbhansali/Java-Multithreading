package ThreadClass;

class Runner extends Thread {
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Hello" +i);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


public class Main {

    public static void main(String[] args) {
        Runner runner1 = new Runner();
        runner1.start();
        // creates a special thread that check for the run method
        // if run called directly uses the applications main thread
        Runner runner2 = new Runner();
        runner2.start();

    }
}
