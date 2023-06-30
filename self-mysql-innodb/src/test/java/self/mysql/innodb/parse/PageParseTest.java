//package self.mysql.innodb.parse;
//
//import ch.ethz.ssh2.Connection;
//import ch.ethz.ssh2.SCPClient;
//import ch.ethz.ssh2.SCPInputStream;
//import org.apache.sshd.client.ClientFactoryManager;
//import org.apache.sshd.client.SshClient;
//import org.apache.sshd.client.session.ClientSession;
//import org.apache.sshd.client.session.ClientSessionImpl;
//import org.apache.sshd.common.io.IoSession;
//import org.apache.sshd.scp.client.DefaultScpClient;
//import org.apache.sshd.scp.client.ScpClient;
//import org.junit.Test;
//import self.mysql.innodb.parse.entity.Page;
//
//import java.io.*;
//
//public class PageParseTest {
//
//    @Test
//    public void testScpIbd() throws IOException {
//        Connection conn = new Connection("10.1.62.12");
//        conn.connect();
//        boolean isAuthenticated = conn.authenticateWithPassword("root", "broada123");
//        if (!isAuthenticated) {
//            throw new IOException("Authentication failed.");
//        }
//        SCPClient client = new SCPClient(conn);
////        client.put("/opt/dbdata/mysql/uyun_udap/r_etl_source.ibd", "F:\\work\\code\\self-mysql\\self-mysql-innodb\\src\\test\\resources\\r_etl_source.ibd");
//        SCPInputStream scpInputStream = client.get("/opt/dbdata/mysql/uyun_udap/mytest.ibd");
//        byte[] buffer = new byte[16384];
//        while (scpInputStream.read(buffer) > 0) {
//            Page page = PageParse.parse(buffer);
//            System.out.println(page);
//        }
//        conn.close();
//    }
//
//    @Test
//    public void testParseScpIbd() throws Exception {
//        ClientFactoryManager sshClient = SshClient.setUpDefaultClient();
//        IoSession nio2Session = null;
//        ClientSession clientSession = new ClientSessionImpl(sshClient, nio2Session);
//        ScpClient scp = new DefaultScpClient(clientSession);
//        String remote = null;
//        byte[] buffer = scp.downloadBytes(remote);
//        Page page = PageParse.parse(buffer);
//        System.out.println(page);
//    }
//
//    @Test
//    public void testScp() throws IOException, InterruptedException {
//        ProcessBuilder processBuilder = new ProcessBuilder();
//        processBuilder.command("scp root@10.1.62.12:/opt/dbdata/mysql/uyun_udap/r_etl_source.ibd F:\\work\\code\\self-mysql\\self-mysql-innodb\\src\\test\\resources\\r_etl_source.ibd");
//        Process process = processBuilder.start();
//        try(BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getInputStream(), "utf-8"))) {
//            String line;
//            while ((line = stdError.readLine()) != null) {
//                System.out.println(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        process.waitFor();
//        Thread.sleep(1000 * 5);
//    }
//
//}
