package userInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import main.Driver;

/**
 * This class is responsible for choosing which action is done in the recipe window
 * whether it be to write new or edit existing as well as containing the logic needed
 * to open, and read old files into data for the recipe writer
 */
public class RecipeListManager {
	public static String[] prefixi = {"Master", "Breakfast", "Lunch", "Dinner", "Sides", "Snacks"};
	
	public static void addRecipe(String recipeName,Boolean[] listSettings) {
		//if trees, to add and remove it from the correct list, using "is on list" function
		String[] recipeLists = createLinks();
		for(int i = 0; i< recipeLists.length; i++) {
			if(listSettings[i] && !(isOnList(recipeName, recipeLists[i]))) {
				addToList(recipeName, recipeLists[i]);
			}
			if(!(listSettings[i]) &&isOnList(recipeName, recipeLists[i])){
				removeFromList(recipeName, recipeLists[i]);
			}
		}
	}
	
	private static boolean isOnList(String recipeName, String fileName) {
		return MeasurementTranslator.contains(recipeName,readList(fileName));
		
	}
	
	private static void addToList(String recipeName, String fileName) {
	//fix
		String[] tempList = readList(fileName);
		String[] finalList = new String[(tempList.length+1)];
		for(int i = 0; i < tempList.length; i++)
			finalList[i] = tempList[i];
		finalList[tempList.length] = recipeName;
		Arrays.sort(finalList);
		try {
			PrintWriter file = new PrintWriter(new File(fileName));
			file.println();
			for(int i = 0; i< finalList.length; i++) {
				file.println(finalList[i]);
			
			}
			file.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void removeFromList(String recipeName,String fileName) {
		String[] tempList = readList(fileName);
		int index = MeasurementTranslator.findIndex(recipeName, tempList);
		try {
			PrintWriter file = new PrintWriter(new File(fileName));
			file.println(); 
			for(int i = 0; i< tempList.length; i++) {
				if(i != index) {
					file.println(tempList[i]);
				}
			}
			file.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static String[] readList(String fileName) {	
		String buffer = ""; // creates a blank buffer to add letters to
		ArrayList<String> recipes = new ArrayList();  // creates an array list to add all the recipes through
		File tempFile = new File(fileName);
		Scanner recipeList;
		try {
			recipeList = new Scanner(tempFile);
			while(recipeList.hasNextLine()) { //Goes through the recipe text file until it reaches the end of it
				buffer = recipeList.nextLine(); // reads the current line
				recipes.add(buffer); // adds the line to the list
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // opens the file
		
		String[] recipesArray = new String[recipes.size()]; // creates the final array with the size of the array list
		for(int i = 0; i < recipes.size(); i++) { // adds all the elements from the list to the final array
			recipesArray[i] = recipes.get(i);
		}
		return recipesArray; //returns the final array of recipe names
	}
	
	private static String[] createLinks() {
		String[] fileLinks = new String[6];
		for(int i = 0; i < fileLinks.length; i++) {
			fileLinks[i] = "...\\..\\"+ prefixi[i] +  "Recipes.txt";
		}
		return fileLinks;
	}
	
	public static boolean[] findActiveLists(String name) {
		boolean[] returnedList = {true,false,false,false,false};
		String[] recipeLists = createLinks();
		
		for(int i = 0;i < returnedList.length; i++) {
			returnedList[i] = isOnList(name, recipeLists[i]);
		}
		
		return returnedList;
	}
}
