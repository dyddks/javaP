package classmanage.role.login;

import java.util.Scanner;

import classmanage.db.dao.MemberDao;
import classmanage.dto.MemberDto;

public class SignUp {

	public void member() {
		Scanner sc = new Scanner(System.in);
		boolean isSave = true;
		String id = null;
		String name = null;
		String select = null;

		while (isSave) {

			MemberDao mDao = new MemberDao();

			if (id == null) {
				System.out.print("ID(학번/교수번호)(0 입력시 뒤로가기) 입력 >>  ");
				id = sc.next();
				if("0".equals(id)) {
					id = null;
					break;
				}
				// ID 입력시 10자를 초과하거나, 숫자가 아닌 경우
				if (id.length() > 10 || !id.matches("\\d+")) {
					System.out.println("잘못된 입력입니다. ID는 10자 이하 숫자로만 입력해주세요.");
					id = null;
					continue;
				}
				// ID가 이미 존재하는 경우
				if (mDao.isMemberExists(id)) {
					System.out.println("이미 존재하는 회원입니다.");
					id = null;
					continue;
				}

			}

			if (name == null) {
				System.out.print("이름 입력(메인으로가려면 뒤로가기 입력) >> ");
				name = sc.next();
				if("뒤로가기".equals(name)) {
					id = null;
		            name = null;
		            break; 
				}
				// 이름 입력시 숫자나 특수기호를 입력하는 경우
				if (name.matches(".*\\d.*") || !name.matches("[a-zA-Z가-힣]+")) {
					System.out.println("잘못된 입력입니다. 이름은 숫자나 특수기호를 포함하지 않는 문자로 입력해주세요.");
					name = null;
					continue;
				}
			}

			if (select == null) {
				System.out.print("학생/교수 선택 (메인으로가려면 0 입력) >> ");
				select = sc.next();
				 if ("0".equals(select)) {
					 	id = null;
			            name = null;
			            select = null;
			            break;
			        }
				
				if (!select.equals("학생") && !select.equals("교수") || select.length() > 2) {
					System.out.println("잘못된 입력입니다. 학생/교수 중에 선택하여 입력해주세요.");
					select = null;
					continue;
				}
			}

			MemberDto mDto = new MemberDto(id, name, select);

			mDao.saveToDatabase(mDto);

			isSave = false;
		}

	}
}