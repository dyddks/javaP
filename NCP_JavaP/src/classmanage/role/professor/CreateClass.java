package classmanage.role.professor;

import java.util.List;
import java.util.Scanner;

import classmanage.db.dao.OpenClassDao;
import classmanage.dto.OpenClassDto;

public class CreateClass {
	
	public void createClass(String id) {
		Scanner sc = new Scanner(System.in);

		System.out.print("개설 할 과목 >>");
		String subjectName = sc.next();
		OpenClassDto dto = new OpenClassDto(id, subjectName);
		OpenClassDao dao = new OpenClassDao();

		List<OpenClassDto> list = dao.viewMyClass(id);
		int count = 0;
		if (list.size() == 0) {
			dao.openClass(dto);
		} else if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (subjectName.equals(list.get(i).getSubject())) {
					System.out.println("이미 개설된 강좌입니다.");
					count++;
				}
				;
			}
			if (count == 0) {
				dao.openClass(dto);
			}
		}

	}
}
