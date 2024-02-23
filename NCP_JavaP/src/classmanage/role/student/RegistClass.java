package classmanage.role.student;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import classmanage.db.dao.RegistClassDao;
import classmanage.dto.OpenClassDto;

public class RegistClass {
	public void registClass(List<OpenClassDto> dtoList, String userId) {
		Scanner sc = new Scanner(System.in);
		System.out.print("수강할 과목명 입력 >> ");
		String subjectName = sc.nextLine();

		for(OpenClassDto dto : dtoList) {
			if(dto.getSubject().equals(subjectName)) {
				RegistClassDao dao = new RegistClassDao();
				try {
					dao.registClass(userId, subjectName);
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
