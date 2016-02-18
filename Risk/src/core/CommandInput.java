package core;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class CommandInput extends JPanel implements ActionListener {



	protected static JTextField commandInputWindow;
	protected JScrollPane scrollPane;
	public static boolean inputUpdated = false;
	static JTextPane outputWindow = new JTextPane();


	public static String player1 = "";
	public static String player2 = "";
	static StyledDocument doc = outputWindow.getStyledDocument();
	static Style style = outputWindow.addStyle("Style", null);
	static int i=0; //iterator for going thru the two players

	public CommandInput() {


		commandInputWindow = new JTextField(15);
		outputWindow.setEditable(false);
		scrollPane=new JScrollPane(outputWindow);
		
		
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(250, 240));
		scrollPane.setMinimumSize(new Dimension(10, 10));
		
		/*
		 * The above is setting up the scroll bar and adding the output window to it
		 */
		



		JButton enterButton = new JButton("ENTER");
		enterButton.addActionListener(this);
		commandInputWindow.addActionListener(this);  //Allows enter press on keyboard
		DefaultCaret caret = (DefaultCaret)outputWindow.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		/*
		 * Adding the button and the action listener to the enter key and
		 * th update caret follows the scroll barto the bottom of the screen
		 */
		
		
		
		
		
		JPanel finishedPanel = new JPanel();




		finishedPanel.setLayout(new BorderLayout());
		finishedPanel.add(scrollPane, BorderLayout.SOUTH);
		finishedPanel.add(enterButton, BorderLayout.EAST);
		finishedPanel.add(commandInputWindow);

		/*
		 * adds all elements to the final panel and gets it ready to be aded to the frame in main.
		 */

		add(finishedPanel);
	}

	public void actionPerformed(ActionEvent e) {
		/*
		 * The actionlistener adds the player one and
		 * player two names to the respective string variables, 
		 * and gives a dice role to see who goes first. 
		 */

		if(i==0){
			Player1UsernameChecks();
		}
			
		if(i==1){
			Player2UsernameChecks();
		}
		
		i++;
	}

public static void run(){
	
	appendStringTo("Enter Username for Player 1: \n", Color.RED);

}

	public static void Player1UsernameChecks(){ //performs error checks on user input
	
		player1 = commandInputWindow.getText();
		appendStringTo(commandInputWindow.getText() + "\n", Color.BLACK);
		
		if (player1.length() < 3){ //Why does this if statement not work more than once?
			i=-1;
			appendStringTo("Your username is too short, please enter a new name: \n", Color.GREEN);
		}
		
		if (player1.length() > 10){
			i=-1;
			appendStringTo("You username is too long, please enter a new name: \n", Color.GREEN);
		}

		
		commandInputWindow.setText(" ");
		
		System.out.println("Player 1 = " + player1);

		
		if(i==0){
		appendStringTo("Enter Username for Player 2: \n", Color.BLUE);	
		}

	}

	public static void Player2UsernameChecks() {
			
		player2 = commandInputWindow.getText();

		appendStringTo(commandInputWindow.getText() + "\n", Color.BLACK);
		commandInputWindow.setText(" ");
	
		System.out.println("Player 2 = " + player2);
	
		if(player2.length() < 3){
			appendStringTo("Your username is too short, please enter a new name: \n", Color.GREEN);
			i=0;			
		}
		if (player2.length() > 10){
			i=0;
			appendStringTo("You username is too long, please enter a new name: \n", Color.GREEN);			
		}
		if(i==1){
			randomPlayerGenerator(player1, player2);
		}
	
		StyleConstants.setForeground(style, Color.blue);
		
}
	
	public static String getPlayer1() {
		return player1;
	}


	public static String getPlayer2(){
		return player2;
	}

	public static void randomPlayerGenerator(String player1, String player2){
		/*
		 * random generator for who plays first.
		 */
		DiceRoll playsFirst = new DiceRoll();

		if(playsFirst.getRoll() == "1" ){
			appendStringTo((player1) + " to go first.\n", Color.RED);

		}
		else{
			appendStringTo((player2) + " to go first.\n", Color.BLUE);
		}
	}
	
	
	
	public static void appendStringTo(String s, Color c){
		//Add the ">" too each line in Dark Gray.
		StyleConstants.setForeground(style, Color.DARK_GRAY);
		try {
			doc.insertString(doc.getLength(), "> ", style);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//Add the actual text the user inputs with the Colour you want
		StyleConstants.setForeground(style, c);
		try {
			doc.insertString(doc.getLength(), s,style);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

