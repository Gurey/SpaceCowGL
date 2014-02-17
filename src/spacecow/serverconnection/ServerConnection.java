package spacecow.serverconnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.gson.Gson;

import spacecow.engine.Game;
import spacecow.engine.GameState.Status;

public class ServerConnection implements Runnable {

	private Socket serverCon;
	private PrintWriter out;
	private BufferedReader in;
	private boolean connected;
	private String conMsg;
	private Game game;
	private Gson gson;
	private boolean closeThread = false;

	public ServerConnection(Game game) {
		gson = new Gson();
		this.game = game;
		try {
			serverCon = new Socket("localhost", 10000);
			out = new PrintWriter(serverCon.getOutputStream());
			in = new BufferedReader(new InputStreamReader(serverCon.getInputStream()));
			connected = true;
		} catch (IOException e) {
			System.err.println("Could not create connection: "+e.getMessage());
			try {
				serverCon.close();
				out.close();
				in.close();
			} catch (IOException e1) {
				System.err.println("Could not close connection "+e1.getMessage());
			}
		}
		if (!out.checkError()) {
			conMsg = "Connected to server!";
		}
		else {
			conMsg = "Could not connect to server! :(";
		}
		System.out.println(conMsg);
	}

	private void recive(){
		String msgIn = null;
		try {
			msgIn = in.readLine();
			if (msgIn != null) {
				Json json = gson.fromJson(msgIn, Json.class);
				System.out.println("Incomming: "+msgIn);
				handleQuery(json);
			}
		} catch (IOException e) {
			System.err.println("ERROR reciving message: "+e.getMessage());
		}
	}
	public void send(String msg){
		System.out.println("Outgoing: "+msg);
		out.println(msg);
		out.flush();
	}

	@Override
	public void run() {
		while (!serverCon.isClosed()) {
			recive();
		}
		try {
			in.close();
		} catch (IOException e) {
			System.err.println("Error closing instream"+e.getMessage());
		}
		System.out.println("Thread Closed");
	}

	private void handleQuery(Json json){
		Json jsonToSend = new Json();
		switch (json.getType()) {
		case "LOGIN RETURN":
			if (json.getAccID()>0) {
				game.getGameState().setStatus(Status.MENU);
				game.getOptions().setSkin(json.getSkinID());
			}
			else game.getLogonMenu().setMessage("Wrong username or password :(");
			break;
		case "STATS":
			game.getStartMenu().setPlayerStats(json);
			System.out.println("Stats loaded from server!");
			break;
		case "TOPTEN":
			game.getScoreMenu().setHighscore(json.getJsonArray());
			break;
		case "PERSONALTOPTEN":
			game.getScoreMenu().setPersonalTop10(json.getJsonArray());
			break;
		case "BESTAVG":
			game.getScoreMenu().setBestAvg(json.getJsonArray());
			break;
		case "CHANGEPASS":
			game.getChangePassword().setMessage(json.getName());
			break;
		case "LOSTPASS":
			game.getLostPassword().setMessage(json.getName());
			break;
		default:
			break;
		}
	}

	public void closeAllConnections(){
		closeThread = true;
		try {
			serverCon.close();
			out.close();
			in.close();
			System.out.println("Connections closed!");
		} 
		catch (Exception e) {
			System.err.println("Could not close connection: "+e.getMessage());		
			}
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}
}