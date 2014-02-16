package spacecow.gui;

public class MenuObject {

	private String menuText, menuInput;
	private boolean secret, haveInputField;
	
	public MenuObject(String menuText, boolean haveInputField, boolean secret) {
		this.menuText = menuText;
		this.secret = secret;
		this.menuInput = "";
		this.haveInputField = haveInputField;
	}
	
	public String getMenuText() {
		return menuText;
	}
	
	public String getMenuInput() {
		return menuInput;
	}
	
	public boolean isSecret() {
		return secret;
	}
	
	public void setMenuText(String menuText) {
		this.menuText = menuText;
	}
	
	public void setMenuInput(String menuInput) {
		this.menuInput = menuInput;
	}
	
	public void setSecret(boolean secret) {
		this.secret = secret;
	}

	public boolean isHaveInputField() {
		return haveInputField;
	}

	public void setHaveInputField(boolean haveInputField) {
		this.haveInputField = haveInputField;
	}
}
