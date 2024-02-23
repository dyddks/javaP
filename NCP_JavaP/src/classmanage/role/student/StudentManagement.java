package classmanage.role.student;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Scanner;

import classmanage.role.student.interFace.StudentMainSel;


public class StudentManagement {
	public void run(String userId, PrintWriter pw, BufferedReader br) {
		Scanner sc = new Scanner(System.in);
		ViewAllClass vac = new ViewAllClass();
		ViewRegistClass vrc = new ViewRegistClass();
		while(true) {
			System.out.println();
			System.out.println("1. 과목 조회");
			System.out.println("2. 수강 과목 조회");
			System.out.println("3. 로그아웃");
			System.out.print("번호 입력 >> ");
			String sel = sc.nextLine();
			switch (sel) {
				case StudentMainSel.VIEW_ALLCLASS: {
					// 전체 과목 조회 -> 수강신청
					vac.viewAllClass(userId, pw, br);
					break;
				}
				case StudentMainSel.VIEW_REGISTCALSS: {
					// 현재 수강중인 과목 조회 -> 성적조회
					vrc.viewRegistClass(userId);
					break;
				}
				case StudentMainSel.LOGOUT: {
					// 로그아웃. 초기 화면으로 이동
					return;
				}
				default: {
					System.out.println("잘못된 입력입니다.");
				}
			}
		}
			
	}
}
