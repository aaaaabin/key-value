package HashInstruction;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Set;

/*
哈希类型，一个 key 对应多个 key-value 型数据
hset[key][field][value]存储key对应的键值对数据
hget[key][field]获取key中field字段的value值
hdel [key][fie1d]删除key中field字段及其value值
hde1[key]删除key中所有数据
* */
public class HashInstruction {
//    hhm
    public static HashMap<String, HashMap<String, String>> hhm = new HashMap<>();
    //声明静态HashMap使得全局共享

    //声明hset
    public static void hset(String key,String field,String value){
        //使用putifAbsent方法判断当前的键有没有对应的HashMap,没有就new一个新的
        HashMap<String,String> currentKeyHashMap = hhm.putIfAbsent(key,new HashMap<>());
        hhm.get(key).put(field,value);
    }
    //声明hget
    public static String hget(String key,String field){
        HashMap<String,String> currentKeyHashMap =hhm.get(key);
        if(currentKeyHashMap==null|| currentKeyHashMap.isEmpty()){
            return null;
        }else{
            return currentKeyHashMap.get(field);
        }
    }
    //声明hdel
    public static String hdel(String key){
        HashMap<String,String> currentKeyHashMap =hhm.get(key);
        if(currentKeyHashMap==null){
            return "输入的键："+key+"不存在对应哈希表";
        }else{
            if(currentKeyHashMap.isEmpty()){
                return "当前键："+key+"为空";
            }
            hhm.remove(key);
            return  "清空成功";
        }
    }
    public static String hdel(String key,String field){
        HashMap<String,String> currentKeyHashMap =hhm.get(key);
        if(currentKeyHashMap==null){
            return "输入的键："+key+"不存在对应哈希表";
        }else if(currentKeyHashMap.isEmpty()){
            return "当前键："+key+"为空";
        }else {
            String re = currentKeyHashMap.get(field);
            if (re == null){
                return key+"["+field+"]"+"没有对应的value值！";
            }else {
                currentKeyHashMap.remove(field);
                return "删除成功";
            }
        }
    }


}
