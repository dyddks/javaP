package classmanage.role.login;

import java.util.Scanner;

import classmanage.db.dao.MemberDao;
import classmanage.interFace.UpdateSel;

public class Update {
	public void updateMember(String id) {
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.println("======수정 페이지======");
			System.out.println("1. 회원 정보 수정");
			System.out.println("2. 회원 정보 삭제");
			System.out.println("3. 뒤로가기");
			System.out.println("번호 입력  >> ");
			String sel = sc.next();

			// 입력값이 1에서 3 사이의 숫자가 아니거나, 다른 자료형을 입력했을 때
			try {
				switch (sel) {
				case UpdateSel.MODIFY: {
					MemberDao mDao = new MemberDao();
					String userId = id;
					String name = UpdateInputName(sc);
					String select = UpdateInputSelect(sc);
					mDao.updateModify(userId, name, select);
					break;
				}
				case UpdateSel.DELETE: {
					MemberDao mDao = new MemberDao();
					String userId = id;
					mDao.deleteMember(userId);
					break;
				}
				case UpdateSel.BACK: {
					return;
				}
				default:
					System.out.println("잘못된 입력입니다. 1~3 사이 숫자로만 입력해 주세요.");
					continue;
				}
			} catch (Exception e) {
			}
		}
	}
	
	private static String UpdateInputName(Scanner sc) {
		String name;
		while(true) {
			System.out.print("수정할 이름 입력: ");
			name = sc.next();
			if (!name.matches(".*\\d.*") && name.matches("[a-zA-Z가-힣]+")) {
				break;
			}else {
				System.out.println("잘못된 입력입니다. 이름은 숫자나 특수기호를 포함하지 않는 문자로 입력해주세요.");
			}
		}
		return name;
	}
	
	private static String UpdateInputSelect(Scanner sc) {
		String select;
		while(true) {
			System.out.print("수정할 (학생/교수) 입력: ");
			select = sc.next();
			if (select.equals("학생") || select.equals("교수")) {
				break;
			}else {
				System.out.println("잘못된 입력입니다. 학생/교수 중에 선택하여 입력해주세요.");
			}
		}
		return select;
	}
}
