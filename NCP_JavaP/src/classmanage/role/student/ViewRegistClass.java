package classmanage.role.student;

import java.util.List;
import java.util.Scanner;

import classmanage.db.dao.RegistClassDao;
import classmanage.dto.RegistClassDto;
import classmanage.role.student.interFace.ViewRegistClassSel;

public class ViewRegistClass {
	public void viewRegistClass(String userId) {
		Scanner sc = new Scanner(System.in);
		UnregistClass uc = new UnregistClass();
		ViewGrade vg = new ViewGrade();
		while(true) {
			System.out.println();
			System.out.println("====신청한 과목====");
			RegistClassDao dao = new RegistClassDao();
			List<RegistClassDto> dtoList = dao.viewRegistClass(userId);
			
			for(RegistClassDto dto : dtoList) {
	        	System.out.print(dto.getUserId() + " ");
	        	System.out.println(dto.getSubject());
			}
			
			System.out.println("===============");
			System.out.println();
			
			System.out.println("1.성적확인  2.수강취소  3.뒤로가기");
			System.out.print("번호 입력 >> ");
			String num = sc.nextLine();
			switch(num) {
			case ViewRegistClassSel.VIEW_GRADE: {
				vg.viewGrade(userId);
				break;
			}
			case ViewRegistClassSel.UNREGIST_CLASS: {
				uc.unRegistClass(userId, dtoList);
				break;
			}
			case ViewRegistClassSel.BACK: {
				return;
			}
			default: {
				System.out.println("잘못된 입력입니다.");
			}
			}
		}
	}
}
