package classmanage.role.student;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import classmanage.db.dao.RegistClassDao;
import classmanage.dto.RegistClassDto;

public class UnregistClass {
	public void unRegistClass(String userId, List<RegistClassDto> dtoList) {
		Scanner sc = new Scanner(System.in);
		System.out.print("수강 취소할 과목명을 입력해주세요 >> ");
		String subject = sc.nextLine();
		
		for(RegistClassDto dto : dtoList) {
			if(dto.getSubject().equals(subject)) {
				RegistClassDao dao = new RegistClassDao();
				try {
					dao.deleteRegistClass(userId, subject);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}
		}
		System.out.println("존재하지 않는 과목입니다.");
	}
}
