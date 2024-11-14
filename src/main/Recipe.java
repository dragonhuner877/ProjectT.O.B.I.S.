package main;
import java.util.ArrayList;
import java.io.File;
import java.io.InputStream;
import java.util.Scanner;

public class Recipe {
	private String name;
	private int feeds;
	private int feedsAmount;
	private ArrayList<String> ingredientsName = new ArrayList<String>();
	private ArrayList<String> ingredientsAmount = new ArrayList<String>();
	private String[][] orderData = {};


	/**
	 * Constructor class used for reading and creating an object to return 2 arrays of ingredients and quantities for that recipe called "ingedrientsName" and "ingrediantsAmount"
	 * @param name the File name of the recipe
	 */
	public Recipe(String name) {
		this.name = name;
		this.orderData = readRecipe(name);
		this.feeds = feedsAmount;
	}
	/**
	 * @param orderData the orderData to set
	 */
	public void setOrderData(String[][] orderData) {
		this.orderData = orderData;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the feeds
	 */
	public int getFeeds() {
		return feeds;
	}
	/**
	 * @return the orderData
	 */
	public String[][] getOrderData() {
		return orderData;
	}
	
	/**
	 * takes the inputed file name, and makes an object with a name, amount it feeds and array of ingredients with their quantities
	 * @param fileName is used to find the correct file to read
	 * @summary takes the named recipe, which is a parameter of the function. reads the text file specified, via adding each line to a buffer. this buffer is then checked for certain delimiters "|", "$", and "!", in order to determine when the ingredient name is over, the recipe amount is over and the recipe is over respectively. based on these parameters, the line is added to either a name or amount array, and the next line replaces the value of buffer.
	 */
	private String[][] readRecipe(String fileName) {
		//converting the inputed recipe name into a text file path
		File filePath = new File("Recipes//" + fileName + ".txt");
		//opening and scanning said text files
		try(Scanner input = new Scanner(filePath)){
			input.nextLine();//skips the first line of the file for formatting information
			name = input.nextLine();
			//setting up variables to contain temporary text lines, and individual worlds/numbers
			String buffer ="";
			String tempWord = "";
			String tempInt = "";
			//adds the line to the variable "buffer" and checks to see if it needs to stop
			while(!buffer.contains("!")) { //stops if the buffer contains "!" which denotes the end of the ingredients list
				buffer = input.nextLine(); //adds the next line to the buffer variable
				if(buffer.contains("!")) { //rechecks if it contains the end of ingredient delimiter
					break;
				}
				tempWord = "";
				tempInt = "";

				for (int i = 0; i < buffer.length(); i++) { //goes through buffer character by character
					if (buffer.charAt(i) == '$') { //checks for the '$' delimiter which indicates the end of the line
						break;
					}

					if (buffer.charAt(i) != '|') { //checks for the '|' delimiter which denotes the ingredient name is over, if it does contain it, it adds it to the temporary ingredient name
						tempWord += buffer.charAt(i);

					}
					if (buffer.charAt(i) == '|') {// checks for the '|' delimiter, if it does, it increases the index, and adds the characters until they equal $ which denotes the end of the ingredient number
						i++;
						while (buffer.charAt(i) != '$') {
							tempInt += buffer.charAt(i);
							i++;
						}
	
					}
				}
				//adds the name of the ingredient and the amount to their respective lists
				ingredientsName.add(tempWord);
				ingredientsAmount.add(tempInt);
			}
			//sets up the correct amount of campers the recipe feeds
			buffer = input.nextLine();
			feedsAmount = Integer.parseInt(buffer);
			input.close();
		}
		catch(Exception ex) {
			System.out.println(Driver.getErrorMessage() + "Error Code 5: " + "Recipes//" + fileName + ".txt");
		}
		
		//puts the two list together with a 2d array with name and number of ingredients in that dimension with a for loop
		String[][] tempOrder = new String[ingredientsName.size()][2];
		for (int i = 0; i < ingredientsName.size(); i++) {
			tempOrder[i][1] = ingredientsAmount.get(i);
			tempOrder[i][0] = ingredientsName.get(i);
		}
		//returns the 2d array of ingredient names for the recipe and the amounts for each ingredient
		return tempOrder;
	}
}
