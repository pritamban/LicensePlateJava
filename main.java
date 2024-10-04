package edu.brandeis.pa3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class Plates {

	public static void main(String[] args) {
		// String fname = "plates.txt";
		// String[] plates = readPlatesFromFile(fname);

		// System.out.println(createRandomPlate("123AB4", 0));

		String nextRandomPlate = nextPlate("123AB4");
		System.out.println(nextRandomPlate);

	}

//	Given a string representing a serial format and an integer corresponding to the month of vehicle
//	expiration, return a randomly generated plate adhering to the serial format.	
	public static String createRandomPlate(String serial, int month) {
		Random rand = new Random();
		String plate = "";

		// Generates a plate based off the 4 different type of serial formats
		if (serial == "123AB4"){

			// Generates the numbers 
			for (int i = 0; i < 3; i++){
				int randInt = rand.nextInt(0,9);
				if (i ==0 && randInt == 0){
					randInt = rand.nextInt(0,9);
				}
				plate += randInt;
			}
			// Generates the letters
			for (int i = 0; i < 2; i++){
				char randomLetter = (char) (rand.nextInt(65,90));
				plate += randomLetter;
			}
			// Adds on the month at the end
			plate += month;
	
			return plate;

		} else if (serial == "12AB34") {

			// Generates the two numbers
			for (int i = 0; i < 2; i++){
				int randInt = rand.nextInt(0,9);
				if (i ==0 && randInt == 0){
					randInt = rand.nextInt(0,9);
				}
				plate += randInt;
			}
			// Generates the two letters
			for (int i = 0; i < 2; i++){
				char randomLetter = (char) (rand.nextInt(65,90));
				plate += randomLetter;
			}
			// Generates the last number and adds on the month
			plate += rand.nextInt(0,9);
			plate += month;

			return plate;

		} else if (serial == "1AB234") {
			
			// Generates the first number and the first two letters
			plate += rand.nextInt(1,9);
			for (int i = 0; i < 2; i++){
				char randomLetter = (char) (rand.nextInt(65,90));
				plate += randomLetter;
			}
			// Generates the two numbers and adds on the plate at the end
			for (int i = 0; i < 2; i++){
				int randInt = rand.nextInt(0,9);
				plate += randInt;
			}
			plate += month;

			return plate;

		} else if (serial == "1ABC23") {

			// Generates the first number, the middle three letters, and the last number
			plate += rand.nextInt(1,9);
			for (int i = 0; i < 3; i++){
				char randomLetter = (char) (rand.nextInt(65,90));
				plate += randomLetter;
			}
			plate += rand.nextInt(0,9);
			//Adds on the month to the plate
			plate += month;

			return plate;
		} else {
			// Just in case the serial format provided is not valid in MA
			return "Provide valid serial format";
		}
	}

//	Given a string representing a license plate,
//	return a new string representing the incremented, next plate in the series.	
	public static String nextPlate(String plate) {
		char [] plateArray = new char[plate.length()];

		// Populates the plateArray list with the plate that has been called with the function
		for (int i = 0; i < plate.length(); i++){
			plateArray[i] = plate.charAt(i);
		}

		// Goes through the plate and increments the digit depending on the last character of the plate
		for (int i = plateArray.length-1; i >= 0; i--){
			if (Character.isDigit(plateArray[i])){
				if (plateArray[i] == '9'){
					plateArray[i] = '0';
				} else {
					plateArray[i]++;
					return String.valueOf(plateArray);
				}
				
			} else if (Character.isLetter(plateArray[i])){
				if (plateArray[i] == 'Z'){
					plateArray[i] = 'A'; 
				} else {
					plateArray[i]++;
					return String.valueOf(plateArray);
				}
			}
		}
		return "error";
	}
	
//	Given a plate string, return a string corresponding to the serial format of that plate
	public static String getSerial(String plate) {

		String serialNum = "";
		int letterCount = 0;
		int digitCount = 0;

		// Go through each character in the plate and see if it is a digit or letter
		// Then adds the corresponding digit or letter depending on the digit count
		for (int i = 0; i < plate.length(); i++) {
			char currentChar = plate.charAt(i);
			if (Character.isDigit(currentChar)) {
				digitCount++;
				if (digitCount == 1) {
					serialNum += 1;
				} else {
					serialNum += digitCount;
				}
			} else if (Character.isLetter(currentChar)) {
				letterCount++;
				serialNum += ((char) ('A' + letterCount - 1)); // add letters as A, B, C, etc.
			}
		}
	
		return serialNum; // Convert StringBuilder to String and return
	}
	
//	Given a plate string, return a boolean value denoting whether the plate is a legal vanity plate.
	public static boolean isLegalVanityPlate(String plate) {
		// Checked to see if plate is a legal length
		if (plate.length() < 2 || plate.length() > 6) {
			return false; 
		}
	
		boolean hasLetter = false; 
		boolean hasNumber = false;
	
		for (int i = 0; i < plate.length(); i++) {
			char currentChar = plate.charAt(i);
			
			// Check if the character is a letter or a digit
			if (!Character.isLetter(currentChar) && !Character.isDigit(currentChar)) {
				return false;
			}
			// Check if the first two characters are letters
			if (i < 2 && Character.isLetter(currentChar)) {
				hasLetter = true; // At least two letters at the start
			}
			// Check if the character is a digit
			if (Character.isDigit(currentChar)) {
				hasNumber = true; // At least one number is present
			}
			// Check for number placement: digits must come at the end
			if (hasNumber == true && Character.isLetter(currentChar)) {
				return false; // If a letter appears after a digit, it's illegal
			}
		}
	
		return hasLetter && plate.charAt(0) != '0'; 
	}
	
//	Given an array of plate strings, return an array of floats corresponding to the
//	frequency of each expiration month.
	public static float[] getMonthStats(String[] plate) {
		int[] monthCounts = new int[10];

		// For loop to access each plate, get the month and increment the corresponding month in the list by one
        for (int i = 0; i < plate.length; i++) {
			String plateCurrent = plate[i];
			char lastChar = plateCurrent.charAt(plateCurrent.length() - 1);
			int month = Character.getNumericValue(lastChar);
			monthCounts[month]++;
		}

        int totalPlates = plate.length;
        float[] monthStats = new float[10];

		// For loop to get the final stats each month in the given set of plates
        for (int i = 0; i < monthCounts.length; i++) {
            monthStats[i] = (float) monthCounts[i] / totalPlates;
        }

        return monthStats;
	 }
	
//	Given an array of plate strings and an array of serial format strings, return an array of floats 
//	corresponding to the frequency of each serial format.
	public static float[] getSerialStats(String[] plate, String[] serials) {
		int[] serialCounts = new int[serials.length];
    
		// Go through each plate and calls get serial to get the serial number of each plate
		for (int i = 0; i < plate.length; i++) {
			// Get the serial format of the current plate
			String plateSerial = getSerial(plate[i]);
			
			// Compares each plates serial formats and adds to the corresponding index of formats, similar to getMonthStats
			for (int j = 0; j < serials.length; j++) {
				if (plateSerial.equals(serials[j])) {
					serialCounts[j]++;
				}
			}
		}
		
		int totalPlates = plate.length;
		
		float[] serialStats = new float[serials.length];
		
		// Calculate the percentage for each serial format, same as getMonthStats
		for (int i = 0; i < serialCounts.length; i++) {
			serialStats[i] = (float) serialCounts[i] / totalPlates;
			System.out.println(serialStats[i]);
		}
		
		return serialStats;
	}

//	Given a partial string and an array of plate strings, return an array of strings corresponding
//	to the plates that match. 	
	public static String[] matchPlate(String partial, String[] plates) {
		// Create new list to store matched plates
		String[] matchedPlates = new String[plates.length];
		int matchedCount = 0;
		int dashIndex = partial.indexOf('-');	
		String startPart = "";
		String endPart = "";
		
		startPart = partial.substring(0, dashIndex);
		endPart = partial.substring(dashIndex + 1);

		// Go through the plates and add up the matches
		for (int i = 0; i < plates.length; i++) {
			String plate = plates[i];
			boolean matchesStart = plate.contains(startPart);
			boolean matchesEnd = plate.contains(endPart);
	
			if (matchesStart && matchesEnd) {
				matchedPlates[matchedCount] = plate;
				matchedCount++; 
			}
		}
	
		// Create the final array to return, with the exact size of matches found
		String[] result = new String[matchedCount];
		for (int i = 0; i < matchedCount; i++) {
			result[i] = matchedPlates[i];
		}
		
		return result;
	}

//	Utility method for reading from plates.txt
	public static String[] readPlatesFromFile(String fname) {
		List<String> plates = new ArrayList<>();
		try {
			FileReader reader = new FileReader(fname);
			BufferedReader br = new BufferedReader(reader);
			
			String line;
            while ((line = br.readLine()) != null) {
            	plates.add(line);
            }
            reader.close();	
		} catch (IOException e) {
			e.printStackTrace();
		}
		return plates.toArray(new String[0]);
	}

}
