package user;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;


public class ServerLauncher extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServerSocket serverSocket;
	private Socket socket;
	public AcceptThread accpetThread;
	
    public ServerLauncher() {
    	
	}
	
    public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServerSetting();
	}
    
    public void closeAll() {
    	try {
			serverSocket.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void ServerSetting() {
	    try {
	    	System.out.println("Ŭ���̾�Ʈ�� ������ ��ٸ�����");
	    	serverSocket = new ServerSocket(7777);
	    	accpetThread = new AcceptThread(serverSocket);
	    	new Thread(accpetThread).start();
	    }
	    catch(Exception e){
	    	e.printStackTrace();
	    }
    }
}
//try { 
//// ������ �⵿�Ǹ� ���� ������ �⵿�Ѵ�.
//SocketServer server = SocketServer.getInstance();
//System.out.println("�������� ����"); 
//// �����ʸ� ����Ѵ�. 
//server.addListener((client, msg) -> { 
//System.out.println("�Դ�"); 
//// �޽����� ������ echo�� ������ �������Ѵ�. 
//System.out.println(msg); 
//String sendmsg = "echo : " + msg;
//server.send(client, sendmsg); 
//}); 
//} catch (IOException e) {
//throw new ServletException(e);	
//}
