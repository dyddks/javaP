package classmanage.dto;

public class OpenClassDto {	
	private int openSubjectIdx;
	private String userId;
	private String subject;
	
	public OpenClassDto(String userId2, String subject2) {
		this.userId = userId2;
		this.subject = subject2;
	}
	public OpenClassDto() {

	}
	
	public int getOpenSubjectIdx() {
		return openSubjectIdx;
	}
	public void setOpenSubjectIdx(int openSubjectIdx) {
		this.openSubjectIdx = openSubjectIdx;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	@Override
	public String toString() {
		return "OpenSubjectDto [openSubjectIdx=" + openSubjectIdx + ", userId=" + userId + ", subject=" + subject + "]";
	}
	
}
