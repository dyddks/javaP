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
import classmanage.dto.ScoreClassDto;

public class ScoreClassDao {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	public void insertScore(ScoreClassDto dto) {
		String sql = "INSERT INTO SCORECLASS (USER_ID, SUBJECT, SCORE)" + "VALUES (?, ?, ?)";

		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getSubject());
			pstmt.setInt(3, dto.getScore());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
	}

	public List<JoinDto> viewScore(String userId) {
		String sql = "SELECT B.USER_ID,A.SUBJECT, B.NAME, A.SCORE FROM SCORECLASS A, MEMBER B WHERE A.USER_ID = B.USER_ID AND A.USER_ID = ?";
		ResultSet rs = null;
		conn = DBConnection.getConnection();
		List<JoinDto> dtoList = new ArrayList<>();
		JoinDto dto;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				dto = new JoinDto(rs.getString(1), rs.getString(3), rs.getString(2), rs.getInt(4));
				dtoList.add(dto);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dtoList;
	}

	// 강좌 삭제
	public void deleteSubject(String className) {
		String sql = "DELETE FROM SCORECLASS WHERE SUBJECT = ?";

		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, className);

			int rows = pstmt.executeUpdate();
			System.out.println("등록된 학점과목 삭제된 행 수: " + rows);

			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
	}

	public List<JoinDto> viewStudentScore(String subject) {
	    String sql = "SELECT B.USER_ID,A.SUBJECT, B.NAME, A.SCORE FROM SCORECLASS A, MEMBER B WHERE A.USER_ID = B.USER_ID AND A.SUBJECT = ?";
	    List<JoinDto> list = new ArrayList<JoinDto>();

	    try {
	        conn = DBConnection.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, subject);
	        rs = pstmt.executeQuery();

	        while(rs.next()) {
	        	String userId = rs.getString(1);
	            String sub = rs.getString(2);
	            String name = rs.getString(3);
	            int score = rs.getInt(4);

	            JoinDto dto = new JoinDto(userId, name, sub, score);
	            list.add(dto);
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