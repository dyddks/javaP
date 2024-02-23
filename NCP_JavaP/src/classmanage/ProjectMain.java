package classmanage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import classmanage.main.ClassMangement;

public class ProjectMain {

	public static void main(String[] args) {
		//final String IP = "192.168.0.50";
		final String IP = "127.0.0.1";
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ClassMangement cm = new ClassMangement(pw, br);
		cm.run();
	}

}