package classmanage.role.student;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import org.json.JSONObject;

import classmanage.db.dao.OpenClassDao;
import classmanage.dto.OpenClassDto;
import classmanage.role.student.interFace.ViewAllClassSel;

public class ViewAllClass {
	public void viewAllClass(String userId, PrintWriter pw, BufferedReader br) {
		RegistClass rc = new RegistClass();
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.println();
			System.out.println("====개설한 과목====");
			//OpenClassDao dao = new OpenClassDao();
			//List<OpenClassDto> dtoList = dao.allSubject();
			
			JSONObject packetObj = new JSONObject();
			packetObj.put("cmd", "ALLCLASS");
			
			String packet = packetObj.toString();
			pw.println(packet);
			pw.flush();
			List<OpenClassDto> dtoList = null;
			try {
				String ackPacket = br.readLine();
				
				JSONObject ackObj = new JSONObject(ackPacket);
				
				for(OpenClassDto dto : dtoList) {
					System.out.print(dto.getOpenSubjectIdx() + " ");
		        	System.out.print(dto.getUserId() + " ");
		        	System.out.println(dto.getSubject());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			System.out.println("===============");
			System.out.println();
			System.out.println("1.수강신청  2.뒤로가기");
			System.out.print("번호 입력 >> ");
			String num = sc.nextLine();
			switch(num) {
			case ViewAllClassSel.REGIST_CALSS: {
				rc.registClass(dtoList, userId);
				break;
			}
			case ViewAllClassSel.BACK: {
				return;
			}
			default: {
				System.out.println("잘못된 입력입니다.");
			}
			}
		}
	}
}
