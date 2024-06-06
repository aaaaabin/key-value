package ListInstruction;

import java.util.*;

/*
双向链表类型(一个key对应一个双向链表，即可左右遍历，可以当栈，也可以当队列使用的数据结构)
lpush [keyl] [value] 可直接放一个数据在左端
rpush [keyl] [va1ue] 可直接放一个数据在右端
range [key] [start] [end]将key 对应 start 到 end 位置的数据全部返回
len [key] 获取 key 存储数据的个数
lpop [key] 获取key最左端的数据并删除
rpop [key] 获取key最右端的数据并删除
ldel [key] 删除key 所有的数据
* */
public class ListInstruction {
    public static HashMap<String, LinkedList<String> > lhm = new HashMap<>();
    //声明静态HashMap使得全局共享

    //声明静态lpush方法指令
    public static void lpush(String key,String value){
        //使用putifAbsent方法判断当前的键有没有对应的LinkedList,没有就new一个新的
        lhm.putIfAbsent(key,new LinkedList<>());
        LinkedList<String> currentKeyList=lhm.get(key);
        currentKeyList.addFirst(value);
    }
    //声明静态rpush方法指令
    public static void rpush(String key,String value){
        //使用putifAbsent方法判断当前的键有没有对应的LinkedList,没有就new一个新的
        lhm.putIfAbsent(key,new LinkedList<>());
        LinkedList<String> currentKeyList=lhm.get(key);
        currentKeyList.addLast(value);
    }
    //声明静态range方法指令
    public static ArrayList range(String key, int start, int end) {
            LinkedList<String> currentKeyList = lhm.get(key);
            if (currentKeyList == null) {
                return null;
            }
            ArrayList<String> tmp = new ArrayList<>();
            int size = currentKeyList.size();
            if (!(start > end || start < 0 || end >= size)) {
                for (int i = start; i <= end; i++) {
                    tmp.add(currentKeyList.get(i));
                }
                return tmp;
            } else {
                tmp.add("输入有误！当前链表的长度为" + size);
                return tmp;
            }
    }
    //声明静态len方法指令
    public static String len(String key){
        LinkedList<String> currentKeyList=lhm.get(key);
        if(currentKeyList==null){
            return null;
        }
        return String.valueOf(currentKeyList.size());
    }
    //声明静态lpop方法指令
    public static String lpop(String key){
        LinkedList<String> currentKeyList=lhm.get(key);
        if(currentKeyList==null){
            return "输入的键："+key+"不存在对应链表";
        }else{
            if(currentKeyList.isEmpty()){
                return "当前链表为空";
            }
            String deletedValue=currentKeyList.removeFirst();
            return  "删除成功---被删除的值为"+deletedValue;
        }
    }
    //声明静态rpop方法指令
    public static String rpop(String key){
        LinkedList<String> currentKeyList=lhm.get(key);
        if(currentKeyList==null){
            return "输入的键："+key+"不存在对应链表";
        }else{
            if(currentKeyList.isEmpty()){
                return "当前链表为空";
            }
            String deletedValue=currentKeyList.removeLast();
            return  "删除成功---被删除的值为"+deletedValue;
        }
    }
    //声明静态ldel方法指令
    public static boolean ldel(String key){
        LinkedList<String> currentKeyList=lhm.get(key);
        if(currentKeyList==null){
            return false;
        }else{
            currentKeyList.clear();
            return true;
        }
    }

}
