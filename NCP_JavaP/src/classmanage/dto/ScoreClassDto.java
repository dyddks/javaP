package classmanage.dto;

public class ScoreClassDto {
	private int scoreClassIdx;
	private String userId;
	private String subject;
	private int score;
	
	public ScoreClassDto(String userId2, String subj, int score) {
		this.userId = userId2;
		this.subject = subj;
		this.score = score;
	}

	public int getScoreClassIdx() {
		return scoreClassIdx;
	}

	public void setScoreClassIdx(int scoreClassIdx) {
		this.scoreClassIdx = scoreClassIdx;
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "ScoreClassDto [scoreClassIdx=" + scoreClassIdx + ", userId=" + userId + ", subject=" + subject
				+ ", score=" + score + "]";
	}
	
	
}
