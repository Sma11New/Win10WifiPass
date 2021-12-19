import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author Sma11New
 * @Github https://github.com/Sma11New
 **/
public class WifiPassTool {
    public static void main(String[] args) throws IOException, InterruptedException {
        List wifiName = getWifiName();

//        test();

    }

    // 获取wifi名（return 列表）
    public static List getWifiName() throws IOException, InterruptedException {
        List wifiNameList = new ArrayList();
        String cmd = "netsh wlan show profiles";
        // 创建命令进程
        Process p = Runtime.getRuntime().exec(cmd);
        // 获取输入流
        InputStream inputStream = p.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("gbk")));
        p.waitFor();

        if (p.exitValue() != 0) {
            System.out.println("WIFI名查询失败");
            System.out.println("11111111111111");
        }

        String s = null;
        while ((s = reader.readLine()) != null) {
            // 创建 Pattern 对象
            Pattern pattern = Pattern.compile("(配置文件 : )(.*?)(\r)");
            // 现在创建 matcher 对象
            Matcher matcher = pattern.matcher(s);
            if (matcher.find()){
                System.out.println(matcher.group(2));
            } else {
                System.out.println("NO MATCH");
            }
//            System.out.println(s);

        }
        return wifiNameList;
    }

    // 获取wifi密码
    public static void getWifiPass(String wifiName) {
//        pass;
    }



}
