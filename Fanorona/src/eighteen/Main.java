package eighteen;

public class Main {
	/*
	 * MAIN IS HERE
	 */
    public static void main(String[] args) {
    	//the options GUI will create all instances of FanoronaGUI
    	OptionsGUI.OGUI = new OptionsGUI();
    	OptionsGUI.OGUI.setVisible(true);
    	//GUI =  new FanoronaGUI();
    	//start a thread waiting for the user to pick server or client, something like that
    }
}
