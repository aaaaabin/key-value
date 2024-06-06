import HashInstruction.HashInstruction;
import HelpInstruction.HelpInstruction;
import ListInstruction.ListInstruction;
import StringInstruction.StringInstruction;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class serverThread implements Runnable {
    private final Socket socket;
    private String userip;
    private String[] orders;
    //正则表达式判断是否为整数
    private static final Pattern NUMBER_PATTERN = Pattern.compile("-?\\d+?");
    public static boolean isNumeric2(String str) {
        return str != null && NUMBER_PATTERN.matcher(str).matches();
    }


    public serverThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                //开启输入输出流
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        ){
            String order;
            //获取用户IP
            userip=in.readLine();
            server.LOGGER.info(userip + "已连接");
            // 获取当前线程并设置新的名字
            Thread.currentThread().setName(userip);
            while ((order=in.readLine())!=null) {
                server.LOGGER.debug(userip+":"+order);
                //把指令分割
                orders = order.split(" ");

                switch (orders[0]) {
                    //字符串
                    case "set":
                        out.write(set());
                        close(out);
                        break;
                    case "get":
                        out.write(get());
                        close(out);
                        break;
                    case "del":
                        out.write(del());
                        close(out);
                        break;
                    //双向链表
                    case "lpush":
                        out.write(lpush());
                        close(out);
                        break;
                    case "rpush":
                        out.write(rpush());
                        close(out);
                        break;
                    case "range":
                        out.write(range());
                        close(out);
                        break;
                    case "len":
                        out.write(len());
                        close(out);
                        break;
                    case "lpop":
                        out.write(lpop());
                        close(out);
                        break;
                    case "rpop":
                        out.write(rpop());
                        close(out);
                        break;
                    case "ldel":
                        out.write(ldel());
                        close(out);
                        break;
                    //ping
                    case "ping":
                        out.write("pong");
                        server.LOGGER.info("pong");
                        close(out);
                        break;
                    //help
                    case "help":
                        out.write(help());
                        close(out);
                        break;
                    //哈希类型
                    case "hset":
                        out.write(hset());
                        close(out);
                        break;
                    case "hget":
                        out.write(hget());
                        close(out);
                        break;
                    case "hdel":
                        out.write(hdel());
                        close(out);
                        break;

                    case "exit":
                        server.LOGGER.info(userip+"已断开连接");
                        return;
                    default:
                        server.LOGGER.error("无效指令");
                        out.write("无效指令");
                        close(out);
                        break;
                }
            }
        } catch (Exception e) {
            server.LOGGER.error(userip+">异常退出");
        }
    }

    //写入完成的标志
    public void close(BufferedWriter out){
        try {
            //因为这里加了空格，所以write中的末尾不能有"\n"!!!
            out.newLine();
            out.write("null");
            out.newLine();
            out.flush();
        } catch (IOException e) {
            server.LOGGER.error("返回错误数据！");
        }
    }
    //Hash指令区
    public String hset(){
        String re=null;
        if(orders.length==4) {
            HashInstruction.hset(orders[1], orders[2], orders[3]);
            re =  "Success！";
        }else{
            re =  "指令有误！";
        }
        server.LOGGER.info("服务端->"+userip +":"+ re);
        return re;
    }
    public String hget(){
        String re=null;
        if(orders.length==3){
            String value=HashInstruction.hget(orders[1], orders[2]);
            if(value!=null){
                re =  orders[1]+"["+orders[2]+"] = "+value;
            }else {
                re =  "键："+orders[1]+"不存在HashMap,或"+orders[1]+"["+orders[2]+"]不存在!";
            }
        }else{
            re =  "指令有误！";
        }
        server.LOGGER.info("服务端->"+userip +":"+ re);
        return re;
    }
    public String hdel(){
        String re=null;
        if(orders.length>3||orders.length<2){
            re =  "指令有误！";
        }else{
            if(orders.length==2){
                re =  HashInstruction.hdel(orders[1]);
            }else{
                re =  HashInstruction.hdel(orders[1],orders[2]);
            }
        }
        server.LOGGER.info("服务端->"+userip +":"+ re);
        return re;
    }

    //help指令
    public String help(){
        String re=null;
        if(orders.length>2){
            re =  "指令有误！";
        }else {
            if(orders.length==1) {
                re =  HelpInstruction.help();
            }else{
            re =  HelpInstruction.help(orders[1]);
            }
        }
        server.LOGGER.info("服务端->"+userip +":"+ re);
        return re;
    }
//字符串指令区
    //set指令
    public String set(){
        String re=null;
        if(orders.length==3) {
            StringInstruction.set(orders[1], orders[2]);
            re =  "Success！";
        }else {
            re =  "指令有误！";
        }
        server.LOGGER.info("服务端->"+userip +":"+ re);
        return re;
    }
    //get指令
    public String get(){
        String re=null;
        if(orders.length==2){
            String value=StringInstruction.get(orders[1]);
            if(value!=null){
                re =  orders[1]+" = "+value;
            }else {
                re =  "键："+orders[1]+"没有对应的value值！";
            }
        }else{
            re =  "指令有误！";
        }
        server.LOGGER.info("服务端->"+userip +":"+ re);
        return re;
    }
    //del指令
    public String del(){
        String re=null;
        if(orders.length==2){
            String value=StringInstruction.del(orders[1]);
            if(value!=null){
                re =  "成功删除："+orders[1]+" = "+value;
            }else {
                re =  "键："+orders[1]+"没有对应的value值！";
            }
        }else{
            re =  "指令有误！";
        }
        server.LOGGER.info("服务端->"+userip +":"+ re);
        return re;
    }
    //双向链表指令区
    public String lpush(){
        String re=null;
        if(orders.length==3) {
            ListInstruction.lpush(orders[1], orders[2]);
            re =  "Success！";
        }else {
            re =  "指令有误！";
        }
        server.LOGGER.info("服务端->"+userip +":"+ re);
        return re;
    }
    public String rpush(){
        String re=null;
        if(orders.length==3) {
            ListInstruction.rpush(orders[1], orders[2]);
            re =  "Success！";
        }else {
            re =  "指令有误！";
        }
        server.LOGGER.info("服务端->"+userip +":"+ re);
        return re;
    }
    public String range(){
        String re=null;
        if(orders.length==4&&isNumeric2(orders[2])&&isNumeric2(orders[3])){
            //使用正则表达式判断输入的是否为整数
            //输的是下标
            ArrayList<String> tmp = ListInstruction.range(orders[1], Integer.parseInt(orders[2]), Integer.parseInt(orders[3]));
            if (tmp == null) {
                re =  "当前键："+orders[1]+"没有对应的链表！";
            } else {
                re =  String.valueOf(tmp);
            }
        }else {
            re =  "指令有误！";
        }
        server.LOGGER.info("服务端->"+userip +":"+ re);
        return re;
    }
    public String len(){
        String re=null;
        if(orders.length==2) {
            String count = ListInstruction.len(orders[1]);
            if (count == null) {
                re =  "当前键："+orders[1]+"没有对应的链表！";
            }else{
                re =  orders[1] + "的链表长度为：" + count;
            }
        }else{
            re =  "指令有误！";
        }
        server.LOGGER.info("服务端->"+userip +":"+ re);
        return re;
    }
    public String lpop(){
        String re=null;
        if(orders.length==2) {
            String value = ListInstruction.lpop(orders[1]);
            re =  value;
        }else{
            re =  "指令有误！";
        }
        server.LOGGER.info("服务端->"+userip +":"+ re);
        return re;
    }
    public String rpop(){
        String re=null;
        if(orders.length==2) {
            String value = ListInstruction.rpop(orders[1]);
            re =  value;
        }else{
            re =  "指令有误！";
        }
        server.LOGGER.info("服务端->"+userip +":"+ re);
        return re;
    }
    public String ldel(){
        String re=null;
        if(orders.length==2) {
            if (ListInstruction.ldel(orders[1])) {
                re =  "Success！";
            } else {
                re =  "输入的键："+orders[1]+"不存在对应链表";
            }
        }else{
            re =  "指令有误！";
        }
        server.LOGGER.info("服务端->"+userip +":"+ re);
        return re;
    }





/*//判断是否执行成功
    public String check(boolean flag){
        if(flag){
            re =  "Success！";
        }else{
            re =  "指令有误！";
        }
    }*/

}
