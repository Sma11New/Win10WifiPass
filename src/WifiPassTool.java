import sun.awt.OSInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author Sma11New
 * @Github https://github.com/Sma11New
 **/

public class WifiPassTool {

    static {
        // 校验操作系统
        String systemType = System.getProperty("os.name");
        if (!systemType.equals("Windows 10")){
            System.out.println("仅支持Win10，当前系统为" + systemType);
            System.exit(0);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        WifiPassTool wifiPass = new WifiPassTool();
        // 获取wifi名
        String cmd = "netsh wlan show profiles";
        BufferedReader br = wifiPass.execCmd(cmd);
        List<String> wifiNameList = wifiPass.extractData(br, "所有用户配置文件");
        System.out.println("-----------Win10 WIFI 密码获取(共" + wifiNameList.size() + "个)-----------");
        // 获取wifi密码
        for (String wifiName:wifiNameList){
            cmd = "netsh wlan show profiles name=\"" + wifiName + "\" key=clear";
            br = wifiPass.execCmd(cmd);
            List<String> passList = wifiPass.extractData(br, "关键内容");
            System.out.println("【" + wifiName + "】 ---> " + passList.get(0));
        }
    }

    // 执行命令
    public BufferedReader execCmd(String cmd) throws IOException, InterruptedException {
        // 创建命令进程
        Process p = Runtime.getRuntime().exec(cmd);
        // 获取输入流
        InputStream inputStream = p.getInputStream();
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("gbk")));
        p.waitFor();

        return bufferReader;
    }

    // 提取数据
    public List<String> extractData(BufferedReader cmdResult, String key) throws IOException {
        List<String> dataResult = new ArrayList();

        if (cmdResult == null) {
            System.out.println("查询失败");
        }
        // 获取需要的数据
        String s = null;
        while ((s = cmdResult.readLine()) != null) {
            if (s.trim().startsWith(key)){
                List<String> ls = Arrays.asList(s.split(":"));
                dataResult.add(ls.get(1).trim());
            }
        }
        // 未提取到
        if (dataResult.size() == 0){
            dataResult.add("未读取到密码");
        }

        return dataResult;
    }
}
