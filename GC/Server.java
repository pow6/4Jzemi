import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket();
            server.bind(new InetSocketAddress(5000)); // ポート4000で待ち受ける
            // 無限ループで待ち受けを続ける
            while (true) {
                Socket socket = server.accept();  // 接続を受け付け
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                String line;
                // 受信した本文を標準出力に表示する
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}