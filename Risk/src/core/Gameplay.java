/*
 * Alan Halvey - 14465722
 * Alan Holmes - 14719591
 * Greg Sloggett - 14522247
 * 
 */
package core;

import java.awt.Color;

public class Gameplay {
	static int numP1Territories;
	static int numP2Territories;
	static int numN1Territories;
	static int numN2Territories;
	static int numN3Territories;
	static int numN4Territories;

	static String countryToAttackWith = "";
	static String countryToAttack = "";
	static int numberOfUnitsToAttackWith = 0;
	static int numberOfUnitsToDefendWith = 0;

	static String attackingPlayer = "";
	static String defendingPlayer = "";
	static String defendingPlayerString = "";

	static Color attackingPlayerColour = Color.ORANGE;
	static Color defendingPlayerColour = Color.ORANGE;


	public static boolean reinforcementsLeft(String player){
		boolean result = true;
		for(int k = 0;k<42;k++){
			if(player.compareToIgnoreCase(Deck.countriesAfterShuffle[k].getOccupyingPlayer().playerName)==0){
				if(Deck.countriesAfterShuffle[k].getOccupyingPlayer().numReinforcements == 0){
					result = false;
					
				}
			}

		}
		
		return result;
		
	}

	static void placeReinforcements(String currentPlayer){
		if(currentPlayer.compareTo(CommandInput.player1)==0){
			if(Gameplay.reinforcementsLeft(CommandInput.player1)==true){
				CommandInput.appendStringTo(currentPlayer + ", please type the country name to place Reinforcements in: \n", Color.BLACK);
				String country = CommandInput.getCommand();
				CommandInput.appendStringTo(country+"\n.", Color.BLACK);
				boolean countryCheck = CommandInput.countryCheck(country);
				if(countryCheck){
					ErrorHandling.placeReinforcementsErrorChecksP1(country);
				}
				else{
					CommandInput.appendStringTo("Invalid input. Try again.\n", Color.RED);
					placeReinforcements(currentPlayer);
				}
			}
			else{
				CommandInput.appendStringTo(currentPlayer + " has no reinforcements left, placing reinforcements.\n", Color.RED);

			}
		}


		if(currentPlayer.compareTo(CommandInput.player2)==0){
			if(Gameplay.reinforcementsLeft(CommandInput.player2)==true){
				CommandInput.appendStringTo(currentPlayer + ", please type the country name to place Reinforcements in: \n", Color.BLACK);
				String country = CommandInput.getCommand();
				CommandInput.appendStringTo(country+"\n.", Color.BLACK);
				boolean countryCheck = CommandInput.countryCheck(country);
				if(countryCheck){
					ErrorHandling.placeReinforcementsErrorChecksP2(country);
				}
				else{
					CommandInput.appendStringTo("Invalid input. Try again.\n", Color.RED);
					placeReinforcements(currentPlayer);
				}
			}
			else{
				CommandInput.appendStringTo(currentPlayer + " has no reinforcements left, skipping placing reinforcements.\n", Color.RED);
			}
		}	
	}

	static void calculateReinforcements() {

		for(int i = 0;i<42;i++){
			if(Deck.countriesAfterShuffle[i].getOccupyingPlayer().numTerritories<=9){
				Deck.countriesAfterShuffle[i].getOccupyingPlayer().numReinforcements = 3;
			}
			else{
				Deck.countriesAfterShuffle[i].getOccupyingPlayer().numReinforcements = Deck.countriesAfterShuffle[i].getOccupyingPlayer().numTerritories / 3;
			}
		}

	}

	public static void Fortify(Country takeArmies, Country putArmies, int amountMoved){

		if(takeArmies.getOccupyingPlayer().fortified==true && putArmies.getOccupyingPlayer().fortified == true){
			if(takeArmies.getPlayerArmies()<=1){
				CommandInput.appendStringTo("You do not have enough armies to do this foritfy\n", Color.RED);
			}
			else{
				System.out.println("1");
				for(int k=0; k<= Data.COUNTRY_NAMES.length-1;k++){
					if(takeArmies.getName().compareToIgnoreCase(Data.COUNTRY_NAMES[k])  == 0 || putArmies.getName().compareToIgnoreCase(Data.COUNTRY_NAMES[k]) == 0 ){
						System.out.println("2");

						for(int j =0; j<takeArmies.getAdjacent().length-1; j++){
							System.out.println("3");
							if(Map.arrayContains(takeArmies.getAdjacent(), k) && takeArmies.getOccupyingPlayer().playerName.compareTo(putArmies.getOccupyingPlayer().playerName)==0 && takeArmies.getOccupyingPlayer().fortified==true){
								System.out.println("4");

								takeArmies.setPlayerArmies(takeArmies.getPlayerArmies()-amountMoved);
								putArmies.setPlayerArmies(putArmies.getPlayerArmies()+amountMoved);
								CommandInput.appendStringTo(takeArmies.getName() + " now has " + takeArmies.getPlayerArmies() + " units.\n", Color.RED);
								CommandInput.appendStringTo(putArmies.getName() + " now has " + putArmies.getPlayerArmies() + " units.\n", Color.RED);
								takeArmies.getOccupyingPlayer().fortified=false;
							}
						}
					}
					else{
						System.out.println("this is not a country");
					}
				}
			}
		}
		else{
			CommandInput.appendStringTo("you have already used your fortify.\n", Color.RED);
			Main.Fortify();
		}
	}

	public static void skip(){

		if (CommandInput.currentPlayer.compareToIgnoreCase(CommandInput.player1) == 0){
			CommandInput.currentPlayer= CommandInput.player2;
		}
		else {
			CommandInput.currentPlayer = CommandInput.player1;
		}

	}


	public static String setFromAbbreviation(String country) {
		for(int i = 0;i<42;i++){
			if(country.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getAbbreviation())==0){
				return Deck.countriesAfterShuffle[i].getName();
			}
			if(country.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getName())==0){
				return Deck.countriesAfterShuffle[i].getName();
			}

		}
		return null;
	}

	public static Country setCountry(String country) {
		for(int i =0;i<42;i++){
			if (country.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getName())==0){
				return Deck.countriesAfterShuffle[i];
			}
		}
		return null;
	}

	public static void combat(String currentPlayer){
		SortingPlayersForCombat(currentPlayer);

		PickAttackingCountry(currentPlayer, attackingPlayer, defendingPlayer);

		PickCountryToAttack(currentPlayer, attackingPlayer, defendingPlayer);

		AttackingPlayerBattleDecisions(currentPlayer, attackingPlayer, defendingPlayer);

		DefendingPlayerBattleDecisions(currentPlayer, attackingPlayer, defendingPlayer);

		InternalCombatLogic(currentPlayer, attackingPlayer, defendingPlayer, numberOfUnitsToAttackWith, numberOfUnitsToDefendWith);

		CheckPlayerEliminated();
		
		NoArmiesLeft();
	}


	public static void SortingPlayersForCombat(String currentPlayer){

		if(currentPlayer == CommandInput.player1){
			attackingPlayer = CommandInput.player1;
			attackingPlayerColour = CommandInput.player1Colour;
		}
		if(currentPlayer == CommandInput.player2){
			attackingPlayer = CommandInput.player2;
			attackingPlayerColour = CommandInput.player2Colour;
		}
		if(currentPlayer == "Neutral 1"){
			attackingPlayer = "Neutral 1";
			attackingPlayerColour = Color.BLACK;
		}
		if(currentPlayer == "Neutral 2"){
			attackingPlayer = "Neutral 2";
			attackingPlayerColour = Color.GREEN;
		}
		if(currentPlayer == "Neutral 3"){
			attackingPlayer = "Neutral 3";
			attackingPlayerColour = Color.RED;
		}
		if(currentPlayer == "Neutral 4"){
			attackingPlayer = "Neutral 4";
			attackingPlayerColour = Color.YELLOW;
		}
	}

	static void PickAttackingCountry(String currentPlayer, String attackingPlayer, String defendingPlayer){

		for(int i=0;i<42;i++){
			ReassignCountriesArmies(Data.COUNTRY_NAMES[i]);
		}
		int count = 0;
		CommandInput.appendStringTo(attackingPlayer + ", please enter which of your countries you wish to attack with: \n", Color.BLACK);
		countryToAttackWith = CommandInput.getCommand();
		boolean validCountryCheck = CommandInput.countryCheck(countryToAttackWith);
		if(!validCountryCheck){
			CommandInput.appendStringTo("That's not a country. Try again.\n", Color.RED);
			PickAttackingCountry(currentPlayer, attackingPlayer, defendingPlayer);
		}
		else{
			int NumArmies = 0;
			NumArmies = Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getPlayerArmies();

			String didPickOccur = "NO";

			for(int i=0;i<42;i++){
				if (countryToAttackWith.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getName())==0 || countryToAttackWith.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getAbbreviation()) == 0){

					if(attackingPlayer.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName)==0){
						if(NumArmies <= 1){
							CommandInput.appendStringTo("You cannot attack using a country that contains less than 2 armies, please select another country to attack with: ", Color.RED);
							combat(currentPlayer);
						}
						else{
							CommandInput.appendStringTo("You have chose " + countryToAttackWith + " to attack with. \n", attackingPlayerColour);
							didPickOccur = "YES";
						}
					}

					else{
						CommandInput.appendStringTo("You do not own this country\n", Color.RED);
					}
				}
			}
			if(count>0){
				PickAttackingCountry(currentPlayer, attackingPlayer, defendingPlayer);
			}
			if(didPickOccur == "NO"){
				PickAttackingCountry(currentPlayer, attackingPlayer, defendingPlayer);
			}
		}
	}

	static void PickCountryToAttack(String currentPlayer, String attackingPlayer, String defendingPlayer){
		CommandInput.appendStringTo(attackingPlayer + ", please enter which country you wish to attack.\n", Color.BLACK);
		countryToAttack = CommandInput.getCommand();
		boolean validCountryCheck = CommandInput.countryCheck(countryToAttack);
		if(!validCountryCheck){
			CommandInput.appendStringTo("That's not a country. Try again.\n", Color.RED);
			PickCountryToAttack(currentPlayer, attackingPlayer, defendingPlayer);
		}
		else{
			String didPickOccur = "NO";

			for(int i=0;i<42;i++){
				if (countryToAttack.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getName()) == 0 || countryToAttack.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getAbbreviation()) == 0){
					int attackingCountryIndex = getIndex(countryToAttackWith);
					if(attackingPlayer.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName)!=0){
						if(Map.arrayContains(Deck.countriesAfterShuffle[i].getAdjacent(), attackingCountryIndex)){
							CommandInput.appendStringTo("You have chosen to attack " + countryToAttack + "\n", attackingPlayerColour);
							didPickOccur = "YES";
							defendingPlayer = Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName;
							defendingPlayerString = defendingPlayer;

							if(defendingPlayer == CommandInput.player1){
								defendingPlayerColour = CommandInput.player1Colour;
							}
							if(defendingPlayer == CommandInput.player2){
								defendingPlayerColour = CommandInput.player2Colour;							
							}
							if(defendingPlayer == "Neutral 1"){
								defendingPlayerColour = Color.BLACK;
							}
							if(defendingPlayer == "Neutral 2"){
								defendingPlayerColour = Color.GREEN;							
							}
							if(defendingPlayer == "Neutral 3"){
								defendingPlayerColour = Color.RED;
							}
							if(defendingPlayer == "Neutral 4"){
								defendingPlayerColour = Color.YELLOW;
							}


						}
						else{
							CommandInput.appendStringTo("You cannot attack this country, please select an adjacent country that you do not own. \n", Color.RED);
						}	
					}
					else{
						CommandInput.appendStringTo("You cannot attack this country, please select an adjacent country that you do not own. \n", Color.RED);
					}
				}
			}

			if(didPickOccur == "NO"){
				PickCountryToAttack(currentPlayer, attackingPlayer, defendingPlayer);
			}
		}
	}

	static int getIndex(String countryToAttackWith2) {
		int index = -1;

		for(int i=0;i<42;i++){
			if (countryToAttackWith2.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getName())==0 || countryToAttackWith2.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getAbbreviation())==0){
				index = Deck.countriesAfterShuffle[i].getIndex();
				System.out.println(Deck.countriesAfterShuffle[i].getName());
				System.out.println(index);
				System.out.println("\n\n");
			}
		}
		return index;
	}

	static void AttackingPlayerBattleDecisions(String currentPlayer, String attackingPlayer, String defendingPlayer){

		for(int i=0;i<42;i++){
			ReassignCountriesArmies(Data.COUNTRY_NAMES[i]);
		}
		
		CommandInput.appendStringTo(attackingPlayer + ", please enter how many units you wish to attack with.\n", Color.BLACK);
		CheckAttackerIntegerErrorInput(attackingPlayer, defendingPlayer);
		int NumArmies = 0;
		
		NumArmies = Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getPlayerArmies();
		System.out.println("" + NumArmies);
		int count = 0;
		if(count == 0){

			if(numberOfUnitsToAttackWith == 0){
				CommandInput.appendStringTo("You must attack with at least one army", Color.RED);
				count++;
			}
			else if(numberOfUnitsToAttackWith >= NumArmies){
				CommandInput.appendStringTo("You do not have a sufficient number of armies for that attack, " + countryToAttackWith + " has " + NumArmies + ". Please enter a value between 1-" + NumArmies, Color.RED);
				count++;
			}
			else if(numberOfUnitsToAttackWith > 3){
				CommandInput.appendStringTo("You can only attack with a maximum of 3 units", Color.RED);
				count++;
			}
		}
			if(count > 0){
				AttackingPlayerBattleDecisions(currentPlayer, attackingPlayer, defendingPlayer);
			}

			if(count == 0){				
				CommandInput.appendStringTo(attackingPlayer + " uses " + countryToAttackWith + " to attack " + countryToAttack + " and uses " + numberOfUnitsToAttackWith + " armies to battle. \n", attackingPlayerColour);
			}	
	}

	private static void DefendingPlayerBattleDecisions(String currentPlayer, String attackingPlayer, String defendingPlayer) {

		for(int i=0;i<42;i++){
			ReassignCountriesArmies(Data.COUNTRY_NAMES[i]);
		}
		
		int NumArmies = 0;
		int count = 0;
		NumArmies = Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getPlayerArmies();
		System.out.println("" + NumArmies);

		if(NumArmies == 1){
			CommandInput.appendStringTo(defendingPlayer + ", only have one army in " + countryToAttack + ", so you do not get to choose how many armies to defend with:\n", defendingPlayerColour);
			numberOfUnitsToDefendWith = 1;
		}
		else{
			CommandInput.appendStringTo(defendingPlayerString + ", " + attackingPlayer + " has attacked your country, " + countryToAttack + ", using " + numberOfUnitsToAttackWith + " units. Please specify how many units you wish to defend with: \n", defendingPlayerColour);

			CheckDefenderIntegerErrorInput(attackingPlayer, defendingPlayer);
			System.out.println(" " + numberOfUnitsToDefendWith);

			if(count == 0){
				if(numberOfUnitsToDefendWith < 1){
					CommandInput.appendStringTo("You must defend with at least one army. Please pick how many armies to defend with again: \n", Color.RED);
					count++;
				}
				else if(numberOfUnitsToDefendWith > 2){
					CommandInput.appendStringTo("You must defend with less than three armies. Please pick how many armies to defend with again: \n", Color.RED);
					count++;
				}
				else if(numberOfUnitsToDefendWith > NumArmies){
					CommandInput.appendStringTo("You do not have that number of armies. Please pick how many armies to defend with again: \n", Color.RED);
				}
			}
		}
		if(count>0){
			DefendingPlayerBattleDecisions(currentPlayer, attackingPlayer, defendingPlayer);
		}
		if(count==0){
			CommandInput.appendStringTo("You have chosen to defend with " + numberOfUnitsToDefendWith + " units. \n", defendingPlayerColour);
		}

	}

	private static void InternalCombatLogic(String currentPlayer, String attackingPlayer, String defendingPlayer, int numberOfUnitsToAttackWith, int numberOfUnitsToDefendWith) {
		for(int i=0;i<42;i++){
			ReassignCountriesArmies(Data.COUNTRY_NAMES[i]);
		}
		DiceRoll.combatDiceRoll(currentPlayer, attackingPlayer, defendingPlayer, numberOfUnitsToAttackWith, numberOfUnitsToDefendWith);
		System.out.println("Attacking"+Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getName());
		System.out.println("Attacking with"+Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getName()+ " " + Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getIndex());
		// indexToAttack = 
		if((DiceRoll.string1 == "0") && DiceRoll.string2 == "-1"){

			CommandInput.appendStringTo(attackingPlayer + " wins one combat. " + attackingPlayer + " does not lose an army. " + defendingPlayerString + " loses one army.\n", Color.BLACK);
			Deck.countriesBeforeShuffle[getIndex(countryToAttack)].setPlayerArmies((Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getPlayerArmies())-1);
			System.out.println(Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getName() + " now has this many armies: " + Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getPlayerArmies()) ;
			Screen.mainFrame.repaint();
			ReassignArmies(Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getName());
		}
		else if((DiceRoll.string1 == "1") && DiceRoll.string2 == "-1"){

			CommandInput.appendStringTo(defendingPlayerString + " wins one combat. " + defendingPlayerString + " does not lose an army. " + attackingPlayer + " loses one army.\n", Color.BLACK);
			Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].setPlayerArmies((Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getPlayerArmies())-1);
			System.out.println(Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getName() + " now has this many armies: " + Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getPlayerArmies()) ;
			ReassignArmies(Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getName());
			Screen.mainFrame.repaint();
		}
		else if((DiceRoll.string1 == "0") && DiceRoll.string2 == "0"){

			CommandInput.appendStringTo( attackingPlayer + " wins both combats. " + attackingPlayer + " does not lose an army. " + defendingPlayerString + " loses two armies.\n", Color.BLACK);
			Deck.countriesBeforeShuffle[getIndex(countryToAttack)].setPlayerArmies((Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getPlayerArmies())-2);
			System.out.println(Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getName() + " now has this many armies: " + Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getPlayerArmies()) ;
			ReassignArmies(Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getName());
			Screen.mainFrame.repaint();
		}
		else if((DiceRoll.string1 == "1") && DiceRoll.string2 == "1"){

			CommandInput.appendStringTo(defendingPlayerString + " wins both combats. " + defendingPlayerString + " does not lose an army. " + attackingPlayer + " loses two armies.\n", Color.BLACK);
			Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].setPlayerArmies((Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getPlayerArmies())-2);
			System.out.println(Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getName() + " now has this many armies: " + Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getPlayerArmies()) ;
			ReassignArmies(Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getName());
			Screen.mainFrame.repaint();
		}
		else if((DiceRoll.string1 == "0") && DiceRoll.string2 == "1"){

			CommandInput.appendStringTo(attackingPlayer + " and " + defendingPlayerString + " each win and lose a combat and both lose 1 army.\n", Color.BLACK);
			Deck.countriesBeforeShuffle[getIndex(countryToAttack)].setPlayerArmies((Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getPlayerArmies())-1);
			Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].setPlayerArmies((Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getPlayerArmies())-1);
			System.out.println(Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getName() + " now has this many armies: " + Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getPlayerArmies()) ;
			System.out.println(Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getName() + " now has this many armies: " + Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getPlayerArmies()) ;
			ReassignArmies(Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getName());
			ReassignArmies(Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getName());
			Screen.mainFrame.repaint();
		}
		else if((DiceRoll.string1 == "1") && DiceRoll.string2 == "0"){

			CommandInput.appendStringTo(attackingPlayer + " and " + defendingPlayerString + " each win and lose a combat and both lose 1 army.\n", Color.BLACK);
			Deck.countriesBeforeShuffle[getIndex(countryToAttack)].setPlayerArmies((Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getPlayerArmies())-1);
			Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].setPlayerArmies((Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getPlayerArmies())-1);
			System.out.println(Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getName() + " now has this many armies: " + Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getPlayerArmies()) ;
			System.out.println(Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getName() + " now has this many armies: " + Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getPlayerArmies()) ;
			ReassignArmies(Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getName());
			ReassignArmies(Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getName());
			Screen.mainFrame.repaint();
		}
		Screen.mainFrame.repaint();
	}

	private static void ReassignCountriesArmies(String string) {
		int armiesToPass = 0;
		for(int i=0;i<42;i++){
			if(string.compareTo(Deck.countriesAfterShuffle[i].getName())==0){
				armiesToPass = Deck.countriesAfterShuffle[i].getPlayerArmies();
			}
		}
		for(int i=0;i<42;i++){
			if(string.compareTo(Deck.countriesBeforeShuffle[i].getName())==0){
				Deck.countriesBeforeShuffle[i].setPlayerArmies(armiesToPass);
			}
		}

	}

	private static void ReassignArmies(String name) {
		int indexForReassignment = 0;
		for(int i=0;i<42;i++){
			if(name.compareTo(Deck.countriesBeforeShuffle[i].getName())==0){
				indexForReassignment=i;
			}
		}

		for(int i=0;i<42;i++){
			if(name.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getName())==0){
				System.out.println("got here");
				Deck.countriesAfterShuffle[i].setPlayerArmies(Deck.countriesBeforeShuffle[indexForReassignment].getPlayerArmies());
				Screen.mainFrame.repaint();
				System.out.println(Deck.countriesAfterShuffle[i].getName() + " now has changed units.");
			}
		}

	}



	static void CheckAttackerIntegerErrorInput(String attackingPlayer, String defendingPlayer){

		numberOfUnitsToAttackWith = -1234;

		while(numberOfUnitsToAttackWith == -1234 ){
			try{
				numberOfUnitsToAttackWith = Integer.parseInt(CommandInput.getCommand());	
			}
			catch(NumberFormatException exception){
				CommandInput.appendStringTo("You entered the number incorrectly. Please enter it in Integer form (e.g 7, 3, 4) \n ",Color.BLACK);
				CommandInput.appendStringTo("Please enter how many units you wish to attack with.\n", Color.BLACK);
			}
		}

	}

	static void CheckDefenderIntegerErrorInput(String attackingPlayer, String defendingPlayer){

		numberOfUnitsToDefendWith = -1234;

		while(numberOfUnitsToDefendWith == -1234 ){
			try{
				numberOfUnitsToDefendWith = Integer.parseInt(CommandInput.getCommand());	
			}
			catch(NumberFormatException exception){
				CommandInput.appendStringTo("You entered the number incorrectly. Please enter it in Integer form (e.g 7, 3, 4) \n Please enter how many units you wish to attack with.\n", Color.BLACK);
			}
		}

	}

	static void CheckPlayerEliminated(){

		for(int i=0;i<42;i++){
			if(Deck.countriesAfterShuffle[i].getOccupyingPlayer().numTerritories == 0){
				CommandInput.appendStringTo(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName + "has been eliminated from the game.\n", Color.BLACK);
				break;
			}
		}
		}	


		
	static void NoArmiesLeft(){
			
			for(int i =0; i<42;i++){
				
					if(Deck.countriesAfterShuffle[i].getPlayerArmies()==0){
						Deck.countriesAfterShuffle[i].setOccupyingPlayer(null);
						CommandInput.appendStringTo(Deck.countriesAfterShuffle[i].getName() + "has zero armies in place\n", Color.BLACK);
					}
				}
				
			}
}


