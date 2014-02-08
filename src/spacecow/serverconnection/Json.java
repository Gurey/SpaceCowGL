package spacecow.serverconnection;

import java.sql.Date;

public class Json {

	private String type, name, password, eMail;
	private int accID, time, astoids, multi, cookies, stars, magnets;
	private long score, avgScore;
	private Json[] jsonArray;
	private Date date;
	
	
	public Json() {
		this.type= "";
		this.name = "";
		this.password="";
		this.eMail ="";
	}
	public long getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(long avgScore) {
		this.avgScore = avgScore;
	}

	public int getTime() {
		return time;
	}

	public int getAstoids() {
		return astoids;
	}

	public int getMulti() {
		return multi;
	}

	public int getCookies() {
		return cookies;
	}

	public int getStars() {
		return stars;
	}

	public int getMagnets() {
		return magnets;
	}

	public long getScore() {
		return score;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public void setAstoids(int astoids) {
		this.astoids = astoids;
	}

	public void setMulti(int multi) {
		this.multi = multi;
	}

	public void setCookies(int cookies) {
		this.cookies = cookies;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public void setMagnets(int magnets) {
		this.magnets = magnets;
	}

	public void setScore(long score) {
		this.score = score;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAccID() {
		return accID;
	}

	public void setAccID(int accID) {
		this.accID = accID;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public Json[] getJsonArray() {
		return jsonArray;
	}

	public void setJsonArray(Json[] jsonArray) {
		this.jsonArray = jsonArray;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
