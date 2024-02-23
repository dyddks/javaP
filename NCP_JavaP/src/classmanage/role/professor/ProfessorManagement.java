package classmanage.role.professor;

import java.util.Scanner;
import classmanage.role.professor.interFace.ProfessorSel1;

public class ProfessorManagement {

	public void run(String id) {
		Scanner sc = new Scanner(System.in);
		boolean isRun = true;

		while (isRun) {
			System.out.println("======교수 페이지======");
			System.out.println("1. 과목 개설");
			System.out.println("2. 개설 강좌 조회");
			System.out.println("3. 과목 삭제");
			System.out.println("4. 종료");
			System.out.println("번호 입력 >> ");

			String sel = sc.next();
			switch (sel) {

			case ProfessorSel1.OPEN_CLASS: {
				CreateClass cc = new CreateClass();
				cc.createClass(id);
				break;
			}
			case ProfessorSel1.MY_CLASS: {
				ViewMyClass vmc = new ViewMyClass();
				vmc.myClass(id);
				break;
			}
			case ProfessorSel1.DELETE_CLASS: {
				DeleteClass dc = new DeleteClass();
				dc.deleteClass(id);
				break;
			}
			case ProfessorSel1.EXIT: {
				System.out.println("프로그램 종료.");
				isRun = false;
				System.exit(0);
				break;
			}
			default: {
				System.out.println("잘못된 입력입니다.");
			}
			}

		}
	}

}
