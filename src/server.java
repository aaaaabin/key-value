import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.net.Socket;

public class server {
    public static final Logger LOGGER =LoggerFactory.getLogger("key-value database");
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(10086);
            System.out.println("-----------------------");
            LOGGER.info("正在等待客户端连接(端口:10086)");
            while (true) {
                Socket socket = serverSocket.accept();

                new Thread(new serverThread(socket)).start();
            }
        } catch (Exception e) {
            LOGGER.error("服务端异常!");
        }finally {
            LOGGER.info("正在重启服务端...");
            System.out.println("-----------------------");
        }
    }
}
