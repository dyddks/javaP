package classmanage.role.student;

import java.util.List;
import java.util.Scanner;

import classmanage.db.dao.ScoreClassDao;
import classmanage.dto.JoinDto;

public class ViewGrade {
	public void viewGrade(String userId) {
		Scanner sc = new Scanner(System.in);
		ScoreClassDao dao = new ScoreClassDao();
		List<JoinDto> dtoList = dao.viewScore(userId);
		System.out.println();
		System.out.println("====성적열람====");
		if(dtoList.isEmpty()) {
			System.out.println("성적 조회 기간이 아닙니다.");
		}
		for(JoinDto dto : dtoList) {
			System.out.print(dto.getUserId() + " ");
			System.out.print(dto.getName() + " ");
			System.out.print(dto.getSubject() + " ");
			System.out.print(dto.getScore() + " ");
			if (dto.getScore() >= 95) {
			    System.out.println("A+");
			} else if (dto.getScore() >= 90) {
			    System.out.println("A0");
			} else if (dto.getScore() >= 85) {
			    System.out.println("B+");
			} else if (dto.getScore() >= 80) {
			    System.out.println("B0");
			} else if (dto.getScore() >= 75) {
			    System.out.println("C+");
			} else if (dto.getScore() >= 70) {
			    System.out.println("C0");
			} else if (dto.getScore() >= 65) {
			    System.out.println("D+");
			} else if (dto.getScore() >= 60) {
			    System.out.println("D0");
			} else {
				System.out.println("F");
			}
		}
		System.out.println("===============");
		System.out.println();
		System.out.println("뒤로가려면 Enter를 눌러주세요.");
		sc.nextLine();
	}
}
