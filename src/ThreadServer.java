import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class ThreadServer implements Runnable {

    @Override
    public void run() {
        try (ServerSocketChannel serverChannel = ServerSocketChannel.open()) {
            serverChannel.bind(new InetSocketAddress("localhost", 22222));
            while (true) {
                try (SocketChannel socketChannel = serverChannel.accept()) {
                    final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 13);
                    while (socketChannel.isConnected()) {
                        int bytesCount = socketChannel.read(inputBuffer);
                        if (bytesCount == -1) break;
                        final String msg = new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8);
                        inputBuffer.clear();
                        socketChannel.write(ByteBuffer.wrap(("Эхо: " +
                                msg.replaceAll("\\s", "")).getBytes(StandardCharsets.UTF_8)));
                    }
                    break;
                } catch (IOException err) {
                    System.out.println(err.getMessage());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
