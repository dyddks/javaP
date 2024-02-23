package classmanage.role.professor;

import java.util.List;
import java.util.Scanner;

import classmanage.db.dao.OpenClassDao;
import classmanage.db.dao.RegistClassDao;
import classmanage.dto.JoinDto;
import classmanage.role.professor.interFace.ProfessorSel3;
import classmanage.role.student.RegistClass;

public class ViewClassStudent {
	public void viewRegistClassStudent(String id, String subject) {
		Scanner sc = new Scanner(System.in);

		System.out.println("==" + subject + "과목 수강 학생==");
		RegistClassDao dao = new RegistClassDao();
		List<JoinDto> dto = dao.selectClassStudent(subject);

		for (JoinDto joinDto : dto) {
			System.out.println(joinDto.getSubject() + " : " + joinDto.getName() + "/" + joinDto.getUserId());
		}

		System.out.println("===============");
		System.out.println("1.학점 입력");
		System.out.println("2.학생 학점 보기");
		System.out.println("3.뒤로가기");
		System.out.println("3.종료");
		System.out.println("번호 입력 >> ");

		String sel = sc.next();
		switch (sel) {

		case ProfessorSel3.INPUT_SCORE: {
			InputScore is = new InputScore();
			is.inputScore(id, subject);
			break;
		}
		case ProfessorSel3.VIEW_SCORE: {
			ViewStudentScore vs = new ViewStudentScore();
			vs.viewStudentScore(id, subject);
			break;
		}
		case ProfessorSel3.BACK: {
			ProfessorManagement pm = new ProfessorManagement();
			pm.run(id);
			break;
		}case ProfessorSel3.EXIT: {
			System.exit(0);
			System.out.println("프로그램 종료.");
			break;
		}
		default: {
			System.out.println("잘못된 입력입니다.");
		}
		}

	}

}
