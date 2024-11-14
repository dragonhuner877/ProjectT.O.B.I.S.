package userInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

import main.Driver;
import main.orderMaker;

public class MeasurementTranslator {
	public static String[] volumeMeasurementUnits = {"mltr", "tsp", "tbsp", "oz(volume)", "cup", "pint", "quart", "litre", "Gal"};
	private static double[] volumeMeasurementIncriments = { 1.0, .203, .333, .5, .125, .5, .5, .946353, .264172}; 
	public static String[] weightMeasurementUnits = {"grams", "oz(weight)", "lbs"};
	private static double[] weightMeasurementIncriments = {1.0, .0353, .0625};
	
	/**
	 * This function is the starting point of converting units, it first determines 
	 * the default measurement of the ingredient and the corresponding "Units"
	 * that it will be converted along. this is used exclusively for the 
	 * recipe creation UI
	 * 
	 * @param readWrite this value indicates if it is being read from a text file
	 * or written to one where "false" is reading and "true" is writing
	 * @param initialValue the initial double value that is being converted
	 * @param firstMeasure the inputed value to be converted to or from
	 * @return the converted value as a double
	 */
	public static double translateMeasurement(boolean readWrite, double initialValue,String firstMeasure, String ingredientName) {
		//initializing necessary variables
		double finalMeasurement = 0;
		String tempLine;
		String[] tempString = {};
		double[] tempIncriments = {};
		String secondMeasure = "";
		int position = 0;
		
		tempLine = getIngredientLine(ingredientName);
				
		secondMeasure = getIngredientMeasure(tempLine);	

		
		//assigning the measurement array
		if (contains(secondMeasure,weightMeasurementUnits)) { 
			tempString = weightMeasurementUnits;
			tempIncriments = weightMeasurementIncriments;
		}else if(contains(secondMeasure,volumeMeasurementUnits)) {
			tempString = volumeMeasurementUnits;
			tempIncriments = volumeMeasurementIncriments;
		}else {
			finalMeasurement = initialValue;
			return finalMeasurement;
		}
		
		//determining in which direction the measurement is converted
		if (readWrite) {
			finalMeasurement = translationArithmatic(initialValue, firstMeasure, secondMeasure,tempString, tempIncriments);
		}else {
			finalMeasurement = translationArithmatic(initialValue, secondMeasure, firstMeasure, tempString, tempIncriments);
			
		}
			
		//output
		return finalMeasurement;
	}
	
	/**
	 * This function simply uses the provided arrays to multiply or divide 
	 * up or down the  of the provided arrays for the conversion
	 * 
	 * @param initialValue The initial value being converted
	 * @param startingUnit The unit that the measurement is starting out
	 * in
	 * @param endUnit The unit in which the measurement is being translated to
	 * @param measureUnits The array which contains the desired unit names
	 * @param measureIncriments The Array which contains the corresponding 
	 * conversion numbers
	 * @return the converted number in double format
	 */
	static double translationArithmatic (double initialValue, String startingUnit, String endUnit, String[] measureUnits, double[] measureIncriments) {
		double result= initialValue ;
		int startIndex, endIndex;
		
		//finding index of the two units on the unit arrays 
		startIndex = findIndex(startingUnit, measureUnits);
		endIndex = findIndex(endUnit, measureUnits);

		
		//determining whether to multiply up or divide down based 
		//on position of units
		if (endIndex > startIndex) {
			startIndex++;
			for (int i = startIndex; i <= endIndex; i++) {
				result = result * measureIncriments[i];
			}
		}else if ( endIndex < startIndex){
			for(int i = startIndex; i > endIndex; i--) {
				result = result / measureIncriments[i];
			}
		}
			
		
		return result;
	}
	
	/**
	 * This function loops through the provided String array to find the
	 * index of the entered string, if it is contained
	 * @param item String desired to be found
	 * @param array list of name to search for it in
	 * @return The index where the string was found or -1
	 * to indicate a missing item
	 */
	public static int findIndex(String item, String[] array) {
		for(int i = 0; i < array.length; i++) {
			if (array[i].equals(item)) {
				return i;
			}
		}
		return -1;
	}
	
	public static boolean contains(String item, String[] array) {
		for(int i = 0; i < array.length; i++) {
			if (array[i].equals(item)) {
				return true;
			}
		}
		return false;
	}
	public static String getIngredientLine(String ingrName) {
		//logic to retrieve the base unit of measurement
		String tempLine = "";
		int fileIndex = orderMaker.getIngredientLine(ingrName);//find where the ingredient is located
		if(fileIndex == -1) {
			System.out.println("Ingredient Does not exist on file");
		}else {
			//creates the file location
			File location = new File("Ingredients.txt");
				
			//tries the loop for errors
			try {		
				//creates the text scanner
				Scanner file = new Scanner(location);
				
				//gets to the correct index
				for(int i = 0; i < fileIndex; i++) {
					file.nextLine();
				}
				
				//Stores the line
				tempLine = file.nextLine();
				file.close();	
			}catch (FileNotFoundException e) {
				System.out.println(Driver.getErrorMessage() + "Error Code 3 ");
			}
		}
		
		return tempLine;
	}
	
	public static String getIngredientMeasure(String ingrLine) {
		String tempMeasure = "";
		int position = 0;
		
		for (int i = 0; i < ingrLine.length(); i ++) {
			if (ingrLine.charAt(i) == '$') {//Stops the loop at the end of the line, this should never be reached
					break;
			}
			if(position == 3 && !(ingrLine.charAt(i) == '|')) {//If in the right section, begins to create the word
				tempMeasure += ingrLine.charAt(i);	
			}
			if(ingrLine.charAt(i) == '|'){//finds the end of section
				position++;// skips to section 3
			}										
		}	
		
		return tempMeasure;
	}
}
