package spacecow.gui;

public class MenuObject {

	private String menuText, menuInput;
	private boolean secret, haveInputField, editable;
	
	public MenuObject(String menuText, boolean haveInputField, boolean secret, boolean editable) {
		this.menuText = menuText;
		this.secret = secret;
		this.menuInput = "";
		this.haveInputField = haveInputField;
		this.editable = editable;
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

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
}
