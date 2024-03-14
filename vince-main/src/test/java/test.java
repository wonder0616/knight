import java.net.InetAddress;
import java.net.UnknownHostException;

public class test {

    public static void main(String[] args) throws UnknownHostException {

        InetAddress localHost = InetAddress.getLocalHost();
        String ipAddress = localHost.getHostAddress();
        System.out.println("启动成功："+ipAddress+":8081");
    }
}
