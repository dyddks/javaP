package ch19.server.jsonchatclient04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import org.json.JSONObject;

public class JsonChatClient {
	static boolean isPending = false;
	
	public static void main(String[] args) {
		final String IP = "192.168.0.50";
		//final String IP = "127.0.0.1";
		final int PORT = 9000;
		Socket socket = null;
		PrintWriter pw = null;
		BufferedReader br = null;
		Scanner sc = null;
		String id = "";
		try {
			socket = new Socket(IP, PORT);
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			sc = new Scanner(System.in);
			
			// 별도의 worker thread를 생ㅇ성해서 서버로부터의 수신을 담당한다.
			ReceiveThread rt = new ReceiveThread(br);
			rt.start();
			
			// main thread는 서버에 전송을 담당한다.
			// 1) id를 서버에 등록한다.
			id = sendId(sc, pw);
			
			// 2) 메뉴를 선택해서 원하는 요청을 서버로 보낸다.
			boolean isRun = true;
			while(isRun) {
				if(JsonChatClient.isPending) {
					continue;
				}
				int sel = getSelectMenu(sc);
				switch(sel) {
				case ServiceMenu.ALL_CHAT:
					sendAllChat(sc, pw, id);
					break;
				case ServiceMenu.ONE_CHAT:
					sendOneChat(sc, pw, id);
					break;
				case ServiceMenu.CALC_ARITH:
					JsonChatClient.isPending = true;
					sendArith(sc, pw, id);
					break;
				case ServiceMenu.EXIT:
					isRun = false;
					break;
				}
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}finally {
			if(socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			sc.close();
		}
	}
	
	public static void sendOneChat(Scanner sc, PrintWriter pw, String id) {
		System.out.println("상대방 id입력 >> ");
		String youId = sc.nextLine();
		boolean isRun = true;
		
		while(isRun) {
			System.out.println("전송 메시지 (quit는 종료) >> ");
			String msg = sc.nextLine();
			if(msg.equals("quit")) {
				isRun = false;
				break;
			}
			JSONObject packetObj = new JSONObject();
			packetObj.put("cmd", "ONECHAT");
			packetObj.put("id", id);
			packetObj.put("youId", youId);
			packetObj.put("msg", msg);
			
			String packet = packetObj.toString();
			pw.println(packet);
			pw.flush();
		}
	}
	
	public static void sendAllChat(Scanner sc, PrintWriter pw, String id) {
		boolean isRun = true;
		while(isRun) {
			System.out.println("전송 메시지 (quit는 종료) >> ");
			String msg = sc.nextLine();
			if(msg.equals("quit")) {
				isRun = false;
				break;
			}
			JSONObject packetObj = new JSONObject();
			packetObj.put("cmd", "ALLCHAT");
			packetObj.put("id", id);
			packetObj.put("msg", msg);
			
			String packet = packetObj.toString();
			pw.println(packet);
			pw.flush();
		}
		
	}
	
	public static void sendArith(Scanner sc, PrintWriter pw, String id) {
		System.out.println("연산자 입력(+ - * /) >> ");
		String op = sc.nextLine();
		System.out.println("첫번째 숫자 입력 >>");
		String val1 = sc.nextLine();
		System.out.println("두번째 숫자 입력 >>");
		String val2 = sc.nextLine();
		
		JSONObject packetObj = new JSONObject();
		packetObj.put("cmd", "ARITH");
		packetObj.put("id", id);
		packetObj.put("op", op);
		packetObj.put("val1", val1);
		packetObj.put("val2", val2);
		
		String packet = packetObj.toString();
		pw.println(packet);
		pw.flush();
	}
	
	// id 생성
	public static String sendId(Scanner sc, PrintWriter pw) {
		System.out.println("당신의 id입력 >> ");
		String id = sc.nextLine();
		
		// json형식으로 패킷생성
		JSONObject idObj = new JSONObject();
		idObj.put("cmd", "ID");
		idObj.put("id", id);
		
		// 문자열로 변환해서 서버로 전송
		String packet = idObj.toString();
		pw.println(packet);
		pw.flush();
		return id;
	}
	
	public static int getSelectMenu(Scanner sc) {
		System.out.println("1. 전체채팅");
		System.out.println("2. 1:1채팅");
		System.out.println("3. 사칙연산");
		System.out.println("4. exit");
		int sel = Integer.parseInt(sc.nextLine());
		return sel;
	}
}

class ReceiveThread extends Thread{
	private BufferedReader br = null;
	
	public ReceiveThread(BufferedReader br) {
		this.br = br;
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				String packet = br.readLine();
				if(packet == null) {
					break;
				}
				JSONObject packetObj = new JSONObject(packet); 
				processPacket(packetObj);
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void processPacket(JSONObject packetObj) {
		String cmd = packetObj.getString("cmd");
		if(cmd.equals("ID")) {	// id등록시 서버응답
			String ack = packetObj.getString("ack");
			if(ack.equals("ok")) {
				System.out.println("[서버응답] id등록 성공");
			}else {
				System.out.println(ack);
			}
		}else if(cmd.equals("ARITH")) {	// 사칙연산에 대한 결과 서버응답
			String ack = packetObj.getString("ack");
			System.out.println("[서버응답] 연산 결과 = " + ack);
			JsonChatClient.isPending = false;
		}else if(cmd.equals("ALLCHAT")) {	// 전체채팅 전송에 대한 서버 응답
			String ack = packetObj.getString("ack");
			if(ack.equals("ok")) {
				System.out.println("[서버응답] 전송성공");
			}else {
				System.out.println(ack);
			}
		}else if(cmd.equals("ONECHAT")) {	// 1대1 채팅 전송에 대한 서버 응답
			String ack = packetObj.getString("ack");
			if(ack.equals("ok")) {
				System.out.println("[서버응답] 1대1전송성공");
			}else {
				System.out.println(ack);
			}
		}else if(cmd.equals("BROADCHAT")) {	// 전체 채팅 수신
			String id = packetObj.getString("id");
			String msg = packetObj.getString("msg");
			System.out.printf("[%s] %s\n", id, msg);
		}else if(cmd.equals("UNICHAT")) { // 1대1 채팅 수신
			String id = packetObj.getString("id");
			String msg = packetObj.getString("msg");
			System.err.printf("[%s] %s\n", id, msg);
		}
	}
}