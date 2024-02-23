package classmanage.dto;

public class RegistClassDto {
	private String userId;
	private String subject;
	
	public RegistClassDto(String userId2, String subject2) {
		this.userId =userId2;
		this.subject =subject2;
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
		return "RegistClassDto [userId=" + userId + ", subject=" + subject + "]";
	}
	
}
