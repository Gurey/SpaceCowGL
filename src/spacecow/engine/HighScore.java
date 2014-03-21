package spacecow.engine;

import java.util.Date;

public class HighScore implements Comparable<HighScore> {

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

	@Override
	public int compareTo(HighScore o) {
	if (this.score==o.getScore())	
	return 0;
	else if (this.score<o.getScore())
	return +1;
	else return -1;
	}
}
