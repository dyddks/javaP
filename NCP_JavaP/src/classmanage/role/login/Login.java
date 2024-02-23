package classmanage.role.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.json.JSONObject;

import classmanage.db.dao.MemberDao;
import classmanage.interFace.CheckJob;
import classmanage.role.professor.ProfessorManagement;
import classmanage.role.student.StudentManagement;

public class Login {
	Scanner sc = new Scanner(System.in);
	public void distinct(PrintWriter pw, BufferedReader br) {

		MemberDao mDao = new MemberDao();
		Scanner sc = new Scanner(System.in);
		boolean isRun = true;
		
		while (isRun) {

			System.out.print("ID 입력(0 입력시 뒤로가기) >> ");
			String id = sc.next();
			if("0".equals(id)) {
				return;
			}
			JSONObject packetObj = new JSONObject();
			packetObj.put("cmd", "LOGIN");
			packetObj.put("id", id);
			
			String packet = packetObj.toString();
			pw.println(packet);
			pw.flush();
			String role = null;
			try {
				String ackPacket = br.readLine();
				JSONObject ackObj = new JSONObject(ackPacket);
				role = ackObj.getString("job");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			if (role == null) {
				System.out.println("아이디가 없습니다.");
				return;
			}else {
				// 사용자에게 입력받은 id를 통해 학생인지 교수인지 조회
				switch (role) {
				case CheckJob.STUDENT:
					System.out.println(mDao.getNamebyId(id) + " " + role + "님 로그인 성공!");
					StudentManagement st = new StudentManagement();
					st.run(id, pw, br);
					return;
				case CheckJob.PROFESSOR:
					System.out.println(mDao.getNamebyId(id) + " " + role + "님 로그인 성공!");
					ProfessorManagement pm = new ProfessorManagement();
					pm.run(id);
					return;
				}
			}
		}
	}
}
