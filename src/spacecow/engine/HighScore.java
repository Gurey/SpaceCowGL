package spacecow.engine;

import java.util.Date;

public class HighScore {

	private long score;
	private Date date;
	private String name;
	
	public HighScore(long score, Date date, String name){
		this.score = score;
		this.date = date;
		this.name = name.trim();
	}

	public long getScore() {
		return score;
	}

	public Date getDate() {
		return date;
	}

	public String getName() {
		return name;
	}

	public void setScore(long score) {
		this.score = score;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setName(String name) {
		this.name = name;
	}
}
