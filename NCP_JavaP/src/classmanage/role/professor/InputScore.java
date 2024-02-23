package classmanage.role.professor;

import java.util.List;
import java.util.Scanner;

import classmanage.db.dao.OpenClassDao;
import classmanage.db.dao.RegistClassDao;
import classmanage.db.dao.ScoreClassDao;
import classmanage.dto.JoinDto;
import classmanage.dto.ScoreClassDto;

public class InputScore {

	public void inputScore(String id, String subject) {
		Scanner sc = new Scanner(System.in);

		System.out.println("=====학점 입력=====");
		RegistClassDao osDao = new RegistClassDao();
		List<JoinDto> list = osDao.selectClassStudent(subject);
		int[] score = new int[list.size()];
		String[] subj = new String[list.size()];
		String[] userId = new String[list.size()];

		System.out.println("-------학점 입력용 리스트---------------");
		for (int i = 0; i < list.size(); i++) {
			System.out.println("학번: " + list.get(i).getUserId() + " 과목 : " + list.get(i).getSubject());
			userId[i] = list.get(i).getUserId();
			subj[i] = list.get(i).getSubject();
			System.out.println("점수 입력 >> ");
			score[i] = sc.nextInt();

			ScoreClassDto dto = new ScoreClassDto(userId[i], subj[i], score[i]);
			ScoreClassDao scDao = new ScoreClassDao();
			scDao.insertScore(dto);
		}

		ProfessorManagement pm = new ProfessorManagement();
		pm.run(id);

	}

}
