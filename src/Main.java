public class Main {
    static final int THREAD_SLEEP = 500;
    public static void main(String[] args) {
        new Thread(new ThreadServer()).start();
        try {
            Thread.sleep(THREAD_SLEEP);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread((new Client())).start();
    }
}
