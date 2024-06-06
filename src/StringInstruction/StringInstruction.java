package StringInstruction;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/*
字符串类型(一个key对应一个字符串value)
set [key][value]存储 key-value 类型数据
get [key] 获取 key 对应的 value
del [key] 删除 key 对应的 value
* */
public class StringInstruction {
    public static HashMap<String,String> shm = new HashMap<>();
    //String.properties
    public static void getFile(){
        Properties properties=new Properties();
        try {
            properties.load(new FileReader("src\\StringInstruction\\StringInstruction.properties"));
            Set<String> keys=properties.stringPropertyNames();
            for (String key : keys) {
                String value =properties.getProperty(key);
                shm.put(key,value);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void updateFile(){
        Properties properties=new Properties();
        for (Map.Entry<String, String> StringEntry : shm.entrySet()) {
            properties.setProperty(StringEntry.getKey(),StringEntry.getValue());
        }
        try {
            properties.store(new FileWriter("src\\StringInstruction\\StringInstruction.properties"),"updateFile/Abin");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //声明静态HashMap使得全局共享
    public static int set(String key,String value){
        //声明静态set方法指令
        getFile();
        shm.put(key,value);
        updateFile();
        return 1;
    }
    public static String get(String key){
        getFile();
        //声明静态get方法指令
        String s = shm.get(key);
        updateFile();
        return s;
    }
    public static String del(String key){
        getFile();
        //声明静态del方法指令
        if(shm.get(key)==null){
            return null;
        }else {
            String re=shm.remove(key);
            updateFile();
        return re;
        }
    }
}
