package classmanage.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


import classmanage.db.DBConnection;
import classmanage.db.DBConnection.DBClose;
import classmanage.dto.MemberDto;


public class MemberDao {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Scanner sc = new Scanner(System.in);

	// 회원가입시 입력받은 정보를 데이터 베이스로 저장
	public void saveToDatabase(MemberDto mDto) {
		String sql = "INSERT INTO member (USER_ID, NAME, JOB) VALUES (?, ?, ?)";
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mDto.getUserId());
			pstmt.setString(2, mDto.getName());
			pstmt.setString(3, mDto.getSelect());

			pstmt.executeLargeUpdate();

			System.out.println("회원 정보가 데이터베이스에 저장되었습니다.");

		} catch (SQLException e) {
			System.out.println("Warning : 회원 정보 저장 오류");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, null);
		}
	}

	// 사용자에게 입력받은 회원정보가 데이터 베이스에 이미 존재하는지 여부 확인
	public boolean isMemberExists(String id) {
		String sql = "SELECT COUNT(*) FROM member WHERE USER_ID = ?";
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0; // 결과가 1 이상이면 이미 회원이 존재함
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}

		return false; // 회원이 존재하지 않음
	}

	// 사용자에게 입력받은 ID를 통해 학생인지 교수인지 조회
	public String getRoleById(String id) {
		String sql = "SELECT JOB FROM member WHERE USER_ID = ?";
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getString("JOB");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}

		return null; // id에 해당하는 역할이 없는 경우
	}

	// 사용자에게 입력받은 ID를 통해 해당 회원의 이름 조회
	public String getNamebyId(String id) {
		String sql = "SELECT NAME FROM member WHERE USER_ID = ?";
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getString("NAME");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}

		return null;
	}
	// 데이터베이스에 저장된 자신의 회원정보를 수정
	public boolean updateModify(String userId, String name, String select) {
		String sql = "UPDATE member SET NAME = ?, JOB = ? WHERE USER_ID = ?";

		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, name);
				pstmt.setString(2, select);
				pstmt.setString(3, userId);

				int rows = pstmt.executeUpdate();
				if (rows > 0) {
					System.out.println("회원 정보가 데이터베이스에 수정되었습니다.");
					return true;
				}
			
		} catch (SQLException e) {
			System.out.println("Warning : 회원 정보 저장 오류");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, null);
		}
		return false;

	}
// 데이터 베이스에 저장된 자신의 정보 삭제
	public boolean deleteMember(String userId) {
		String sql = "delete from member where USER_ID = ?";
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, userId);

				int rows = pstmt.executeUpdate();
				if (rows > 0) {
					System.out.println("회원 정보가 데이터베이스에서 삭제되었습니다.");
					return true;
				}
		} catch (SQLException e) {
			System.out.println("Warning : 회원 정보 저장 오류");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, null);
		}
		return false;

	}
	
}