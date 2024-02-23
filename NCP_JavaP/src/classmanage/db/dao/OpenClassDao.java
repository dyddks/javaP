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
import classmanage.dto.OpenClassDto;

public class OpenClassDao {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	// 강좌 개설
	public void openClass(OpenClassDto dto) {
		String sql = "INSERT INTO OPENCLASS (USER_ID, SUBJECT)" + "VALUES (?, ?)";

		try {
			conn = DBConnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getSubject());

			int rows = pstmt.executeUpdate();
			System.out.println("저장된 행 수: " + rows);

			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
	}

	// 선택된 강좌
	public int checkClassExist(String id, String className) throws SQLException {
		String sql = "SELECT COUNT(*) FROM OPENCLASS WHERE USER_ID = ? AND SUBJECT = ?";
		
		int cnt = 0;
		conn = DBConnection.getConnection();
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.setString(2, className);
		rs = pstmt.executeQuery();
		while(rs.next()) {
			cnt = rs.getInt(1);
		}
		
		return cnt;
	}
	// 강좌 삭제
	public void deleteClass(String className) {
		String sql = "DELETE FROM OPENCLASS WHERE SUBJECT = ?";

		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, className);

			int rows = pstmt.executeUpdate();
			System.out.println("삭제된 행 수: " + rows);

			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
	}

	public List<OpenClassDto> viewMyClass(String id) {
		String sql = "SELECT * FROM OPENCLASS WHERE USER_ID = ?";
		List<OpenClassDto> list = new ArrayList<OpenClassDto>();

		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String userId = rs.getString(2);
				String subject = rs.getString(3);

				OpenClassDto obj = new OpenClassDto(userId, subject);
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



	public List<OpenClassDto> allSubject() {
	    String sql = "SELECT * FROM OPENCLASS";
	    Connection conn = null;
		PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    List<OpenClassDto> list = new ArrayList<OpenClassDto>();

	    try {
	        conn = DBConnection.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        rs = pstmt.executeQuery();

	        while(rs.next()) {
	        	OpenClassDto obj = new OpenClassDto();
	            obj.setOpenSubjectIdx(rs.getInt(1));
	            obj.setUserId(rs.getString(2));
	            obj.setSubject(rs.getString(3));
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
