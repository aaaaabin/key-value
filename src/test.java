import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class test {
    //记录用户IP
    public static String userip;
    static String order;
    public static int count=0;//记录重连次数

    public static void main(String[] args){
        while (true) {
            try {
                Scanner sc = new Scanner(System.in);
                userip = InetAddress.getLocalHost().getHostAddress();
                System.out.println("本机IP地址是:"+userip);
                Socket socket=new Socket(userip,10086);
                System.out.println("已连接上服务端开启交互");
                System.out.println("---------------");
                count=0;//重连成功后重置重连次数

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                out.write(userip);
                out.newLine();
                out.flush();
                System.out.println("请输入要发送的指令:");
                while (true) {
                    System.out.print(userip+">");
                    order=sc.nextLine();
                    if("exit".equals(order)) {
                        out.write(order);
                        out.newLine();
                        out.flush();
                        System.out.println("客户端已断开连接");
                        socket.close();
                        return;
                    }else{
                        out.write(order);
                        out.newLine();
                        out.flush();
                        String re=null;
                        StringBuilder sb = new StringBuilder();
                        //读取服务端返回信息
                        while (!(re = in.readLine()).equals("null") &&!re.isEmpty()) { // 如果 line 为空说明读完了
                            sb.append(re).append("\n");
                        }
                        System.out.print("服务端-> ");
                        System.out.print(sb.toString());
                    }
                }
            } catch (Exception e) {
                //即使出现异常也要重新连接！
                if(count<20){
                System.out.println("正在重新连接服务端...");
                System.out.println("---------------");
                count++;
                main(args);
                return;
                }
                else{
                    System.out.println("重连次数过多，程序退出！");
                    return;
                }
//                throw new RuntimeException(e);
            }
        }
    }

}
