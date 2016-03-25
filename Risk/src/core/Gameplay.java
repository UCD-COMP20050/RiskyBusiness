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
				ErrorHandling.placeReinforcementsErrorChecksP1(country);
			}
			else{
				CommandInput.appendStringTo(currentPlayer + " has no reinforcements left, skipping turn.\n", Color.RED);
				CommandInput.currentPlayer = CommandInput.player2;
				placeReinforcements(CommandInput.currentPlayer);
			}
		}


		if(currentPlayer.compareTo(CommandInput.player2)==0){
			if(Gameplay.reinforcementsLeft(CommandInput.player2)==true){
				CommandInput.appendStringTo(currentPlayer + ", please type the country name to place Reinforcements in: \n", Color.BLACK);
				String country = CommandInput.getCommand();
				ErrorHandling.placeReinforcementsErrorChecksP2(country);
			}
			else{
				CommandInput.appendStringTo(currentPlayer + " has no reinforcements left, skipping turn.\n", Color.RED);
				CommandInput.currentPlayer = CommandInput.player1;
				placeReinforcements(CommandInput.currentPlayer);
			}
		}	
	}

	static void calculateReinforcements() {
		for(int i=0;i<42;i++){
			if(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName.compareToIgnoreCase(CommandInput.player1)==0){
				numP1Territories++;
				Deck.countriesAfterShuffle[i].getOccupyingPlayer().numTerritories = numP1Territories;
			}
			if(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName.compareToIgnoreCase(CommandInput.player2)==0){
				numP2Territories++;
				Deck.countriesAfterShuffle[i].getOccupyingPlayer().numTerritories = numP2Territories;
			}
			if(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName.compareToIgnoreCase("Neutral 1")==0){
				numN1Territories++;
				Deck.countriesAfterShuffle[i].getOccupyingPlayer().numTerritories = numN1Territories;
			}
			if(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName.compareToIgnoreCase("Neutral 2")==0){
				numN2Territories++;
				Deck.countriesAfterShuffle[i].getOccupyingPlayer().numTerritories = numN2Territories;

			}
			if(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName.compareToIgnoreCase("Neutral 3")==0){
				numN3Territories++;
				Deck.countriesAfterShuffle[i].getOccupyingPlayer().numTerritories = numN3Territories;

			}
			if(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName.compareToIgnoreCase("Neutral 4")==0){
				numN4Territories++;
				Deck.countriesAfterShuffle[i].getOccupyingPlayer().numTerritories = numN4Territories;

			}


		}
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



		if(takeArmies.getPlayerArmies()<=1){
			System.out.println("You do not have enough armies to do this foritfy");
			Fortify(takeArmies,  putArmies, amountMoved);
		}
		else{
			System.out.println("1");
			for(int k=0; k<= Data.COUNTRY_NAMES.length-1;k++){
				if(takeArmies.getName().compareToIgnoreCase(Data.COUNTRY_NAMES[k])  == 0 || putArmies.getName().compareToIgnoreCase(Data.COUNTRY_NAMES[k]) == 0 ){
					System.out.println("2");



					for(int j =0; j<takeArmies.getAdjacent().length-1; j++){
						System.out.println("3");

						if(takeArmies.getAdjacent()[j]== k && takeArmies.getOccupyingPlayer().playerName.compareTo(putArmies.getOccupyingPlayer().playerName)==0){
							System.out.println("4");

							takeArmies.setPlayerArmies(takeArmies.getPlayerArmies()-amountMoved);
							putArmies.setPlayerArmies(putArmies.getPlayerArmies()+amountMoved);
							CommandInput.appendStringTo(takeArmies.getName() + " now has " + takeArmies.getPlayerArmies() + " units.", Color.RED);
							CommandInput.appendStringTo(putArmies.getName() + " now has " + putArmies.getPlayerArmies() + " units.", Color.RED);
						}


					}
				}
				else{
					System.out.println("this is not a counrty");

				}
			}

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
			if(country.compareTo(Deck.countriesAfterShuffle[i].getAbbreviation())==0){
				return Deck.countriesAfterShuffle[i].getName();
			}
			if(country.compareTo(Deck.countriesAfterShuffle[i].getName())==0){
				return Deck.countriesAfterShuffle[i].getName();
			}

		}
		return null;
	}

	public static Country setCountry(String country) {
		for(int i =0;i<42;i++){
			if (country.compareTo(Deck.countriesAfterShuffle[i].getName())==0){
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
	}


	public static void SortingPlayersForCombat(String currentPlayer){
	
		if(currentPlayer == CommandInput.player1){
			attackingPlayer = CommandInput.player1;
			attackingPlayerColour = CommandInput.player1Colour;
		
			defendingPlayer = CommandInput.player2;
			defendingPlayerColour = CommandInput.player2Colour;
		}
		else{
			attackingPlayer = CommandInput.player2;
			attackingPlayerColour = CommandInput.player2Colour;

			defendingPlayer = CommandInput.player1;
			defendingPlayerColour = CommandInput.player1Colour;
		}
	}
	
	static void PickAttackingCountry(String currentPlayer, String attackingPlayer, String defendingPlayer){

		CommandInput.appendStringTo(attackingPlayer + ", please enter which of your countries you wish to attack with: \n", Color.BLACK);
		countryToAttackWith = CommandInput.getCommand();
		
		int NumArmies = 0;
		NumArmies = Deck.countriesAfterShuffle[getIndex(countryToAttackWith)].getPlayerArmies();

		String didPickOccur = "NO";

		for(int i=0;i<42;i++){
			if (countryToAttackWith.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getName())==0 || countryToAttackWith.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getAbbreviation()) == 0){
				
				if(attackingPlayer.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName)==0){
					/*if(NumArmies <= 1){
						CommandInput.appendStringTo("You cannot attack using a country that contains less than 2 armies, please select another country to attack with: ", Color.RED);
						combat(currentPlayer);
					}
					else{*/
						CommandInput.appendStringTo("You have chose " + countryToAttackWith + " to attack with. \n", attackingPlayerColour);
						didPickOccur = "YES";
					//}
				}

				else{
					CommandInput.appendStringTo("You do not own this country\n", Color.RED);
				}
			}
		}
		if(didPickOccur == "NO"){
			PickAttackingCountry(currentPlayer, attackingPlayer, defendingPlayer);
		}
	}

	static void PickCountryToAttack(String currentPlayer, String attackingPlayer, String defendingPlayer){
		CommandInput.appendStringTo(attackingPlayer + ", please enter which country you wish to attack.\n", Color.BLACK);
		countryToAttack = CommandInput.getCommand();

		String didPickOccur = "NO";
	
		for(int i=0;i<42;i++){
			if (countryToAttack.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getName()) == 0 || countryToAttack.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getAbbreviation()) == 0){
				int attackingCountryIndex = getIndex(countryToAttackWith);
				if(attackingPlayer.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName)!=0){
					if(Map.arrayContains(Deck.countriesAfterShuffle[i].getAdjacent(), attackingCountryIndex)){
						CommandInput.appendStringTo("You have chosen to attack " + countryToAttack + "\n", attackingPlayerColour);
						didPickOccur = "YES";
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

	private static int getIndex(String countryToAttackWith2) {
		int index = -1;
		for(int i=0;i<42;i++){
			if (countryToAttackWith2.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getName())==0 || countryToAttackWith2.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getAbbreviation())==0){
				index = Deck.countriesAfterShuffle[i].getIndex();
			}
		}
		return index;
	}

	static void AttackingPlayerBattleDecisions(String currentPlayer, String attackingPlayer, String defendingPlayer){

		CommandInput.appendStringTo(attackingPlayer + ", please enter how many units you wish to attack with.\n", Color.BLACK);
		CheckAttackerIntegerErrorInput(attackingPlayer, defendingPlayer);
		int NumArmies = 0;
		
		NumArmies = Deck.countriesAfterShuffle[getIndex(countryToAttackWith)].getPlayerArmies();
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
			if(count > 0){
				AttackingPlayerBattleDecisions(currentPlayer, attackingPlayer, defendingPlayer);
			}
		
			if(count == 0){				
				CommandInput.appendStringTo(attackingPlayer + " uses " + countryToAttackWith + " to attack " + countryToAttack + " and uses " + numberOfUnitsToAttackWith + " armies to battle. \n", attackingPlayerColour);
			}
		}	
	}

	private static void DefendingPlayerBattleDecisions(String currentPlayer, String attackingPlayer, String defendingPlayer) {
				
		int NumArmies = 0;
		int count = 0;
		NumArmies = Deck.countriesAfterShuffle[getIndex(countryToAttack)].getPlayerArmies();
		System.out.println("" + NumArmies);
		
			if(NumArmies == 1){
				CommandInput.appendStringTo(defendingPlayer + ", only have one army in " + countryToAttack + ", so you do not get to choose how many armies to defend with.", defendingPlayerColour);
				numberOfUnitsToDefendWith = 1;
			}
			else{
				CommandInput.appendStringTo(defendingPlayer + ", " + attackingPlayer + " has attacked your country, " + countryToAttack + ", using " + numberOfUnitsToAttackWith + " units. Please specify how many units you wish to defend with: ", defendingPlayerColour);
		
				CheckDefenderIntegerErrorInput(attackingPlayer, defendingPlayer);
				System.out.println(" " + numberOfUnitsToDefendWith);

				if(count == 0){
					if(numberOfUnitsToDefendWith < 1){
						CommandInput.appendStringTo("You must defend with at least one army. Please pick how many armies to defend with again.", Color.RED);
						count++;
					}
					else if(numberOfUnitsToDefendWith > 2){
						CommandInput.appendStringTo("You must defend with less than three armies. Please pick how many armies to defend with again.", Color.RED);
						count++;
					}
					else if(numberOfUnitsToDefendWith > NumArmies){
						CommandInput.appendStringTo("You do not have that number of armies. Please pick how many armies to defend with again.", Color.RED);
					}
				}
				if(count>0){
					DefendingPlayerBattleDecisions(currentPlayer, attackingPlayer, defendingPlayer);
				}
				if(count==0){
					CommandInput.appendStringTo("You have chosen to defend with " + numberOfUnitsToDefendWith + " units. ", defendingPlayerColour);
				}
			}
		
	}

	private static void InternalCombatLogic(String currentPlayer, String attackingPlayer, String defendingPlayer, int numberOfUnitsToAttackWith, int numberOfUnitsToDefendWith) {

		DiceRoll.combatDiceRoll(currentPlayer, attackingPlayer, defendingPlayer, numberOfUnitsToAttackWith, numberOfUnitsToDefendWith);
		
		if(DiceRoll.combatDiceRollWinner().equals(0)){
			
		}
		
	}

	static void CheckAttackerIntegerErrorInput(String attackingPlayer, String defendingPlayer){

		numberOfUnitsToAttackWith = -1234;
		
		while(numberOfUnitsToAttackWith == -1234 ){
			try{
				numberOfUnitsToAttackWith = Integer.parseInt(CommandInput.getCommand());	
			}
			catch(NumberFormatException exception){
				CommandInput.appendStringTo("You entered the number incorrectly. Please enter it in Integer form (e.g 7, 3, 4) \n Please enter how many units you wish to attack with.\n", Color.BLACK);
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


}

