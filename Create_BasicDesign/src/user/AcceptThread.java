package user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.google.gson.Gson;

public class AcceptThread implements Runnable{
   ServerSocket serverSocket;
    Socket client;
    public ArrayList<UserThread> clientList = new ArrayList<UserThread>();//클라이언트를 관리하는 리스트
    public ArrayList<UserThread> WrongHashList = new ArrayList<UserThread>();//틀린 해시를 보낸 클라이언트를 담는 리스트
    public HashMap<Socket,String> redeemList = new HashMap<Socket,String>();//클라이언트와 유저아이디를 담는 리스트
    public HashMap<Socket,String> hashList = new HashMap<Socket,String>();//클라이언트와 각각이 보낸 해쉬를 담는 리스트
    public HashMap<String,Integer> confirmHash = new HashMap<String,Integer>();//클라이언트의 해쉬를 비교할때 쓰는 리스트
    public Transaction transaction;
    public String accurateHash ="";
    public EventModule module;
    public Boolean m_status = true;
    public User user;
    public String[] fund_transaction = new String[2];
    public String[] charge_transaction = new String[2];
    public AcceptThread(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        transaction = new UserDAO().make_currentTransaction();
    }
 
    @Override
    public void run() {
       SendSystemin systemin = new SendSystemin();
       systemin.start();
        while (m_status) {
            try {
                client = serverSocket.accept();
                UserThread user = new UserThread(client);
                clientList.add(user);
                user.start();
                System.out.println("connected client"+ client);
            }
            catch (SocketException e) {
               e.printStackTrace();
            }
            catch (Exception e) {
                // TODO: handle exception
            }
            if(systemin.getState() == Thread.State.TERMINATED) {
            m_status = false;
            }
            
        }
        try {
         client.close();
         serverSocket.close();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
    }
    
    public void msgSendAll(String msg) {    // 연결된 모든 클라이언트에 메시지 중계
      for(UserThread ut : clientList) {
         ut.outMsg.println(msg);
      }
   }
    
    public class SendSystemin extends Thread{
       String msg = "";
       Boolean status = true;
       
       public SendSystemin() {
          
       }
       
       public SendSystemin(String msg) {//test안해봄
          this.msg = msg;
          msgSendAll(msg);
       }
       
       
       public void run() {
          Scanner scan = new Scanner(System.in);
          
          while(status) {
             msg = scan.nextLine();
             if("/quit".equals(msg)) {
                for(int i =0;i<clientList.size();i++) {
                    if(clientList.get(i) != null) {
                       clientList.get(i).stop();
                    }
                }
                status = false;
             }
             else if(msg.contains("timeout")) {
                msgSendAll("timeout");
                CalcHashThread calcthread = new CalcHashThread();
               calcthread.start();
             }
             else {
                msg = null;
             }
          }
       }
    }
    
    public class UserThread extends Thread {
       
       public Socket socket;
       private PrintWriter outMsg = null;
       private BufferedReader inMsg = null;
       String msg = "";
       boolean status;
       public UserThread(Socket socket) {
          this.socket = socket;
       }
       
       public void run() {
          status = true;
          
          try {
             System.out.println("사용자 접속");
             inMsg = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             outMsg = new PrintWriter(socket.getOutputStream(), true);
             
             Gson gson = new Gson();
             String sel = gson.toJson(transaction.getTransaction());
             outMsg.println(sel);
             
             while(status) {
                msg = inMsg.readLine();//msg가 hash이면 계산후 통보
                System.out.println("유저 : " + msg);
                
                if(msg == null || msg.contains(FSOCKET.QUIT_CODE)) {
                   clientList.remove(this);
                   status = false;
                   outMsg.println("quit_ok");
                   System.out.println("유저  나감");
                }
                else if(msg.contains(FSOCKET.NEWUSER_CODE)) {
                   //새로운유저의 userID를 받아와서 EventModule안에 Money=0을 같이 저장후 송신
                   module = new EventModule();
                   gson = new Gson();
                   module.setMoney("0");
                   module.setUserID(msg.substring(7));
                   transaction.getTransaction().add(module);
                   String msg = gson.toJson(module);
                   msgSendAll(msg);
                }
                else if(msg.contains(FSOCKET.HASH_CODE)) {
                   String hashmessage = msg.substring(4);
                   hashList.put(socket, hashmessage);
                }
                else if(msg.contains(FSOCKET.LOGIN_CODE)) {
                   String Login_userID = msg.substring(6);
                   redeemList.put(socket, Login_userID);
                   System.out.println(redeemList.get(socket));
                }
                else if(msg.contains(FSOCKET.FUNDING_CODE)) {
                   EventModule funding_module = new EventModule();
                   String Stringmodule = msg.substring(7);
                      fund_transaction = Stringmodule.split("&-");
                      for(int i =0;i<transaction.getTransaction().size();i++) {
                         if(transaction.getTransaction().get(i).getUserID().equals(fund_transaction[0])) {
                            transaction.getTransaction().get(i).setMoney(String.valueOf(Integer.parseInt(transaction.getTransaction().get(i).getMoney()) - Integer.parseInt(fund_transaction[1])));
                             System.out.println(transaction.getTransaction().get(i).getMoney());
                            break;
                         }
                      }
                      funding_module.setUserID(fund_transaction[0]);
                      funding_module.setMoney("-" + fund_transaction[1]);
                      
                      gson = new Gson();
                      String msg = gson.toJson(funding_module);
                      msgSendAll(msg);
                      
                      for(int i=0;i<fund_transaction.length;i++) {
                         fund_transaction[i] = null;
                      }
                }
                else if(msg.contains(FSOCKET.CHARGE_CODE)) {
                   EventModule Charge_module = new EventModule();
                   String Stringmodule = msg.substring(6);
                   charge_transaction = Stringmodule.split("&");
                      
                      for(int i =0;i<transaction.getTransaction().size();i++) {
                         if(transaction.getTransaction().get(i).getUserID().equals(charge_transaction[0])) {
                            transaction.getTransaction().get(i).setMoney(String.valueOf(Integer.parseInt(transaction.getTransaction().get(i).getMoney()) + Integer.parseInt(charge_transaction[1])));
                         }
                      }
                      
                      Charge_module.setUserID(charge_transaction[0]);
                      Charge_module.setMoney(charge_transaction[1]);
                      
                      gson = new Gson();
                      String msg = gson.toJson(Charge_module);
                      msgSendAll(msg);
                      
                      for(int i=0;i<charge_transaction.length;i++) {
                         charge_transaction[i] = null;
                      }
                }
                else if(msg.contains(FSOCKET.COMPENSATE_CODE)) {
                   int change_Money = 0;
                   user = new User();
                   String compensate_ary[] = new String[2];
                   EventModule compensate_module = new EventModule();
                   String compensate_Info = msg.substring(10);
                   compensate_ary = compensate_Info.split("&");
                   
                   compensate_module.setUserID(compensate_ary[0]);
                   compensate_module.setMoney(compensate_ary[1]);
                   user = new UserDAO().searchUser(compensate_module.getUserID());
                   change_Money = user.getUserMoney() + Integer.parseInt(compensate_module.getMoney());
                   new UserDAO().modify_Money(compensate_module.getUserID(),change_Money);
                   
                   for(int i =0;i<transaction.getTransaction().size();i++) {
                         if(transaction.getTransaction().get(i).getUserID().equals(compensate_module.getUserID())) {
                            transaction.getTransaction().get(i).setMoney(String.valueOf(change_Money));
                         }
                      }
                   
                   gson = new Gson();
                      String msg = gson.toJson(compensate_module);
                      msgSendAll(msg);
                      compensate_module.clear();
                      for(int i=0;i<compensate_ary.length;i++) {
                         compensate_ary[i] = null;
                      }
                }
             }
             this.interrupt();
          } catch (IOException e) {
             clientList.remove(this);
             e.printStackTrace();
          }  // 입출력 스트림 생성
          outMsg.println("quit_ok");
       }
    }
    
    public class CalcHashThread extends Thread{
       public Boolean hashmapStatus = true;
       public Boolean status = true;
        public String StringOfTransaction;
        public String HashOfTransaction;
        public int max=0;
        public ArrayList<Integer> findmaxArray = new ArrayList<Integer>();
       
       public void run() {
          while(status) {
             System.out.println(clientList.size() +"/"+ hashList.size());
             if(clientList.size() == hashList.size()) {
                //서버의 트랜젝션을 해시로만드는과정
                for (EventModule component : transaction.getTransaction()){
                       StringOfTransaction += (component.getUserID() + component.getMoney());
                   }
                   HashOfTransaction = SignatureUtil.applySHA256(StringOfTransaction);
                                      
                   //confirmHash에 각 해시마다 몇개씩인지 세어서 넣는 과정
                   confirmHash.put(HashOfTransaction, 0);
                   
                   for(int i =0;i<clientList.size();i++) {
                      String previousHash = hashList.get(clientList.get(i).socket);
                      for(String key : confirmHash.keySet()){
                            if(key.equals(previousHash)) {
                               hashmapStatus = false;
                            }
                       }
                      if(hashmapStatus) {
                          confirmHash.put(previousHash, 0);
                       }
                       else {
                          hashmapStatus = true;
                       }
                      confirmHash.put(previousHash, confirmHash.get(previousHash) + 1);
                   }
                   
                   //confirmHash의 어느 해시값이 제일 많은지 알아내는 과정
                   for(String key : confirmHash.keySet()){
                      findmaxArray.add(confirmHash.get(key));
                   }
                   for(int index : findmaxArray) {
                      if(max < index) {
                         max = index;
                      }
                   }
                   for(String key : confirmHash.keySet()){
                      if(max == confirmHash.get(key)) {
                         accurateHash = key;
                      }
                   }
                   
                   System.out.println("정확한 해쉬는 " + accurateHash + "입니다.");
                   
                   //틀린 해시를 보낸 클라이언트를 담는 과정
                   for(int i =0;i<hashList.size();i++) {
                      if(!accurateHash.equals(hashList.get(clientList.get(i).socket))) {
                         WrongHashList.add(clientList.get(i));
                      }
                   }
                   System.out.println("해쉬가 틀린 사용자의 수 : " + WrongHashList.size());
                   
                   //나온결과를 처리하는 과정
                   //1.맞는 해시를 전달한 클라이언트에게는 아무 메시지를 전달하지 않는다.
                   for(UserThread ut : WrongHashList) {
                     Gson gson = new Gson();
                     String msg = "accurateTransaction" + gson.toJson(transaction.getTransaction());
                     ut.outMsg.println(msg);
                   }
                   
                   //DB 최신화 과정
                   Transaction currentTransaction = new Transaction();
                   currentTransaction = new UserDAO().make_currentTransaction();
                   
                   for(int i =0;i<transaction.getTransaction().size();i++) {
                      if(!currentTransaction.getTransaction().get(i).getMoney().equals(transaction.getTransaction().get(i).getMoney())) {
                         new UserDAO().modify_Transaction(transaction.getTransaction().get(i));
                         System.out.println(transaction.getTransaction().get(i).getUserID());
                      }
                   }
                   
                   
                   //초기화 과정
                   max = 0;
                   confirmHash.clear();
                   hashList.clear();
                   findmaxArray.clear();
                   WrongHashList.clear();
                   findmaxArray.clear();
                   currentTransaction = null;
                   status = false;
             }
          }
       }
    }
}