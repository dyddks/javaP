package classmanage.role.professor;

import java.util.List;

import classmanage.db.dao.ScoreClassDao;
import classmanage.dto.JoinDto;

public class ViewStudentScore {
	public void viewStudentScore(String id, String subject) {
		System.out.println("========" + subject + "학점 보기 =========");
		ScoreClassDao dao = new ScoreClassDao();
		List<JoinDto> list = dao.viewStudentScore(subject);
		System.out.println(subject + " 과목 학생 학점===========");

		String[] scoreCh = new String[list.size()];

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getScore() >= 95) {
				scoreCh[i] = "A+";
			} else if (list.get(i).getScore() >= 90) {
				scoreCh[i] = "A0";

			} else if (list.get(i).getScore() >= 85) {
				scoreCh[i] = "B+";

			} else if (list.get(i).getScore() >= 80) {
				scoreCh[i] = "B0";

			} else if (list.get(i).getScore() >= 75) {
				scoreCh[i] = "C+";

			} else if (list.get(i).getScore() >= 70) {
				scoreCh[i] = "C0";

			} else {
				scoreCh[i] = "F";

			}

			System.out.println(list.get(i).getUserId() + "/" + list.get(i).getName() + " : " + list.get(i).getScore()
					+ " -> " + scoreCh[i]);
		}

		ProfessorManagement pm = new ProfessorManagement();
		pm.run(id);
	}
}
