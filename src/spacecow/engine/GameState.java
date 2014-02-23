package spacecow.engine;

public class GameState {

	public enum Status {STARTGAME, OPTIONS, HIGHSCORE, MENU, EXIT, LOGON, CREATENEW, LOSTPASSWORD, HOWTOPLAY, CHANGEPASS, STARTSCREEN};
	
	private Status status;

	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
}
