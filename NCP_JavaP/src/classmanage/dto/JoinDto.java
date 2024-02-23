package classmanage.dto;

public class JoinDto {
	private String userId;
	private String name;
	private String subject;
	private int score;
	
	public JoinDto(String userId, String name, String subject, int score) {
		this.userId = userId;
		this.name = name;
		this.subject = subject;
		this.score = score;
	}
	
	public JoinDto(String userId, String name, String subject) {
		this.userId = userId;
		this.name = name;
		this.subject = subject;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	

	
}
