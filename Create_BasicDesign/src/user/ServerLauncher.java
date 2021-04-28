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
	    	System.out.println("클라이언트의 접속을 기다리는중");
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
//// 서버가 기동되면 소켓 서버를 기동한다.
//SocketServer server = SocketServer.getInstance();
//System.out.println("서버동작 시작"); 
//// 리스너를 등록한다. 
//server.addListener((client, msg) -> { 
//System.out.println("왔다"); 
//// 메시지를 받으면 echo를 붙혀서 재전송한다. 
//System.out.println(msg); 
//String sendmsg = "echo : " + msg;
//server.send(client, sendmsg); 
//}); 
//} catch (IOException e) {
//throw new ServletException(e);	
//}
