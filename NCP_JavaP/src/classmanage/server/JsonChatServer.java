package classmanage.server;

import java.net.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;

import classmanage.db.dao.MemberDao;
import classmanage.db.dao.OpenClassDao;
import classmanage.db.dao.RegistClassDao;
import classmanage.db.dao.ScoreClassDao;
import classmanage.dto.MemberDto;
import classmanage.dto.OpenClassDto;
import classmanage.dto.RegistClassDto;
import classmanage.role.student.RegistClass;

import java.io.*;

/* 1) EchoServer
 * 	- 서버소켓의 동작방식 (서버소켓(accpt), 통신소켓(send/recv))
 * 2) EchoMultiThreadServer
 * 	- 병렬 처리를 위해 Client 1개당 Thread 1개 배정
 * 3) JsonChatServer
 * 	- 다양한 업무 처리를 위해서 Json 포맷 사용 (패킷)
 * 	- 채팅은 1사람한테 수신된 데이터를 다른 사람한테 전달해야 한다.
 * 	  그러므로 사용자 등록/관리가 필요하다. (HashTable == HashMap+동기화)
 * */
public class JsonChatServer {
	public static void main(String[] args) {
		final int PORT = 9000;
		Hashtable clientHt = new Hashtable();	// 접속자를 관리하는 테이블
		
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
			String mainThreadName = Thread.currentThread().getName();
			
			/* main thread는 계속 accept()처리를 담당한다. */
			while(true) {
				System.out.printf("[서버-%s] Client접속을 기다립니다.\n", mainThreadName);
				Socket socket = serverSocket.accept();
				
				
				/* worker thread는 Client와의 통신을 담당한다. */
				WorkerThread wt = new WorkerThread(socket, clientHt);
				wt.start();
			}
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}

class WorkerThread extends Thread{
	private MemberDao mbd = new MemberDao();
	private OpenClassDao ocd = new OpenClassDao();
	private RegistClassDao rcd = new RegistClassDao();
	private ScoreClassDao scd = new ScoreClassDao();
	
	private Socket socket;
	private Hashtable ht;
	public WorkerThread(Socket socket, Hashtable ht) {
		this.socket = socket;
		this.ht = ht;
		
	}
	
	@Override
	public void run() {
		try {
			InetAddress inetAddr = socket.getInetAddress();
			System.out.printf("<서버-%s>%s로부터 접속했습니다.\n", getName(), inetAddr.getHostAddress());
			OutputStream out = socket.getOutputStream();
			InputStream in = socket.getInputStream();
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			while(true) {
				/*client가 json오브젝트를 string으로 변환해서 보낸 것을 수신*/
				String line = br.readLine();
				if(line == null) {
					break;
				}
				/* json패킷을 해석해서 알맞은 처리를 한다.
				 * 문자열 -> JSONObject 변환 -> cmd를 해석해서 어떤 명령인지?
				 * */
				JSONObject packetObj = new JSONObject(line);
				processPacket(packetObj);
			}
		}catch(Exception e) {
			System.out.printf("<서버-%s>%s\n", getName(), e.getMessage());
		}
		
	}
	
	private void processPacket(JSONObject packetObj) throws IOException {
		String cmd = packetObj.getString("cmd");
		JSONObject ackObj = new JSONObject();
		
		if(cmd.equals("IDCHECK")) {
			String id = packetObj.getString("id");
			boolean res = mbd.isMemberExists(id);
			// 응답
			ackObj.put("cmd", "IDCHECK");
			ackObj.put("ack", res);
			String ack = ackObj.toString();
			// 클라이언트한테 전송
			OutputStream out = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
			pw.println(ack);
			pw.flush();
		}else if(cmd.equals("SIGNUP")) {
			String id = packetObj.getString("id");
			String name = packetObj.getString("name");
			String job = packetObj.getString("job");
			MemberDto dto = new MemberDto(id, name, job);
			mbd.saveToDatabase(dto);
			ackObj.put("cmd", "SIGNUP");
			ackObj.put("ack", "ok");
			String ack = ackObj.toString();
			
			OutputStream out = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
			pw.println(ack);
			pw.flush();
		}else if(cmd.equals("LOGIN")) {
			String id = packetObj.getString("id");
			String role = mbd.getRoleById(id);
			ackObj.put("job", role);
			String ack = ackObj.toString();
			
			OutputStream out = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
			pw.println(ack);
			pw.flush();
		}else if(cmd.equals("AllCLASS")) {
			List<OpenClassDto> list = new ArrayList<>();
			list = ocd.allSubject();
			
			OutputStream out = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
			for(OpenClassDto dto: list) {
				ackObj.put("class", dto);
			}
			
			String ack = ackObj.toString();
			pw.println(ack);
			pw.flush();
		}else if(cmd.equals("MYCLASS")) {
			String id = packetObj.getString("id");
			List<RegistClassDto> list = new ArrayList<>();
			list = rcd.viewRegistClass(id);
			OutputStream out = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
			ackObj.put("cmd", "MYCLASS");
			ackObj.put("ack", list);
			String ack = ackObj.toString();
			pw.println(ack);
			pw.flush();
		}else if(cmd.equals("REGISTCLASS")) {
			
		}else if(cmd.equals("VIEWSCORE")) {
			
		}
		
	}
}
