import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client implements Runnable {
    @Override
    public void run() {
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 22222);
        try (SocketChannel socketChannel = SocketChannel.open()) {
            socketChannel.connect(socketAddress);
            try (Scanner scanner = new Scanner(System.in)) {
                final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);
                String msg;
                while (true) {
                    System.out.println("Введите строку с пробелами");
                    msg = scanner.nextLine();
                    if ("end".equals(msg)) break;
                    socketChannel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
                    Thread.sleep(1000);
                    int bytesCount = socketChannel.read(inputBuffer);
                    System.out.println(new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8).trim());
                    inputBuffer.clear();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
