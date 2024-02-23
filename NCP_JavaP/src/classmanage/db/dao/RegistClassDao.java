package classmanage.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import classmanage.db.DBConnection;
import classmanage.db.DBConnection.DBClose;
import classmanage.dto.JoinDto;
import classmanage.dto.RegistClassDto;
import classmanage.dto.ScoreClassDto;

public class RegistClassDao {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	List<RegistClassDto> dtoList = new ArrayList<>();

	public void registClass(String userId, String subject) throws SQLException {
		int result = 0;
		String sql = "INSERT INTO REGISTCLASS VALUES (?, ?)";
		conn = DBConnection.getConnection();
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, userId);
		pstmt.setString(2, subject);
		result = pstmt.executeUpdate();

		if (result == 1) {
			System.out.println("수강신청이 완료되었습니다.");
		}
	}

	public void deleteRegistClass(String userId, String subject) throws SQLException {
		int result = 0;
		String sql = "DELETE FROM REGISTCLASS WHERE USERID=? AND SUBJECT=?";
		conn = DBConnection.getConnection();
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, userId);
		pstmt.setString(2, subject);
		result = pstmt.executeUpdate();

		if (result == 1) {
			System.out.println("수강취소가 완료되었습니다.");
		}

	}

	public List<RegistClassDto> viewRegistClass(String userId) {
		String selectSql = "SELECT * FROM REGISTCLASS WHERE USERID = ?"; // select = 조회하는거 where 조건에 맞는거 가져오는거

		conn = DBConnection.getConnection();
		try {
			pstmt = conn.prepareStatement(selectSql);
			pstmt.setString(1, userId);
			// pstmt.executeQuery();입력한 sql 실행시켜주는 메소
			rs = pstmt.executeQuery();

			while (rs.next()) {

				RegistClassDto obj = new RegistClassDto(rs.getString(1), rs.getString(2));

				dtoList.add(obj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dtoList;
	}

	// 교수가 개설한 강좌 삭제
	public void deleteSubject(String className) {
		String sql = "DELETE FROM REGISTCLASS WHERE SUBJECT = ?";
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, className);
			pstmt.executeUpdate();
			int rows = pstmt.executeUpdate();
			System.out.println("수강신청과목 삭제된 행 수: " + rows);

			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, null);
		}
	}
	
	public List<JoinDto> selectClassStudent(String subjectP) {
		String sql = "select a.USER_ID, a.NAME, b.subject from member a, registclass b where a.USER_ID = b.userID and b.subject = ?";
		List<JoinDto> list = new ArrayList<JoinDto>();

		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, subjectP);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String userId = rs.getString(1);
				String name = rs.getString(2);
				String subject = rs.getString(3);
				JoinDto obj = new JoinDto(userId, name, subject);
				list.add(obj);

			}

		} catch (SQLException e) {
			System.out.println("데이터 검색 실패");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		return list;
	}
}
