package classmanage.role.professor;

import java.sql.SQLException;
import java.util.Scanner;

import classmanage.db.dao.OpenClassDao;
import classmanage.db.dao.RegistClassDao;
import classmanage.db.dao.ScoreClassDao;

public class DeleteClass {
	public void deleteClass(String id) {
		Scanner sc = new Scanner(System.in);

		System.out.print("삭제 할 과목 >>");
		String className = sc.next();

		OpenClassDao openClassDao = new OpenClassDao();
		 
		try {
			int result = openClassDao.checkClassExist(id, className);
			if (result == 0) {
				System.out.println("강좌가 없습니다.");
			} else {				
				RegistClassDao registClassDao = new RegistClassDao();
				ScoreClassDao scoreClassDao = new ScoreClassDao();
				
				openClassDao.deleteClass(className);
				registClassDao.deleteSubject(className);
				scoreClassDao.deleteSubject(className);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
