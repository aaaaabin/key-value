package HelpInstruction;

public class HelpInstruction {
    public static String help(){
        return "指令集合：\n" +
                "\tset [key] [value]存储 key-value 类型数据\n" +
                "\tget [key] 获取 key 对应的 value\n" +
                "\tdel [key] 删除 key 对应的 value\n" +
                "\tlpush [keyl] [value] 可直接放一个数据在左端\n" +
                "\trpush [keyl] [va1ue] 可直接放一个数据在右端\n" +
                "\trange [key] [start] [end]将key 对应 start 到 end 位置的数据全部返回\n" +
                "\tlen [key] 获取 key 存储数据的个数\n" +
                "\tlpop [key] 获取key最左端的数据并删除\n" +
                "\trpop [key] 获取key最右端的数据并删除\n" +
                "\tldel [key] 删除key 所有的数据\n"+
                "\thset [key] [field] [value]存储key对应的键值对数据\n" +
                "\thget [key] [field]获取key中field字段的value值\n" +
                "\thdel [key]删除key中所有数据\n" +
                "\thdel [key] [fie1d]删除key中field字段及其value值\n" +
                "\thelp 查看帮助\n"+
                "\thelp [command] 查看指定指令的帮助\n"+
                "\tping 测试连接\n"+
                "\texit 退出连接";
    }
    public static String help(String command){
        return switch (command) {
            case "set" -> "set [key] [value]存储 key-value 类型数据";
            case "get" -> "get [key] 获取 key 对应的 value";
            case "del" -> "del [key] 删除 key 对应的 value";
            case "lpush" -> "lpush [keyl] [value] 可直接放一个数据在左端";
            case "rpush" -> "rpush [keyl] [va1ue] 可直接放一个数据在右端";
            case "range" -> "range [key] [start] [end]将key 对应 start 到 end 位置的数据全部返回";
            case "len" -> "len [key] 获取 key 存储数据的个数";
            case "lpop" -> "1pop [key] 获取key最左端的数据并删除";
            case "rpop" -> "rpop [key] 获取key最右端的数据并删除";
            case "ldel" -> "1de1 [key] 删除key 所有的数据";
            case "hset" -> "hset [key] [field] [value]存储key对应的键值对数据";
            case "hget" -> "hget [key] [field]获取key中field字段的value值";
            case "hdel" -> "hdel [key]删除key中所有数据\n" +
                    "hdel [key] [fie1d]删除key中field字段及其value值";
            case "help" -> "help 查看帮助";
            case "ping" -> "ping 测试连接";
            case "exit" -> "exit 退出连接";
            default -> "指令有误！";
        };
    }
}
