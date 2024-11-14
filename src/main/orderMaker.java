package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.*;

//takes in recipes for the week, calls recipe programs to break it down and then add it to an 2D Array of ingredient and quantity
public class orderMaker {
	private final int ingredientVectorIncriment = 5;
	private Vector ingredeintinfo = new Vector(0, ingredientVectorIncriment);
	private TobisBrain ingrList;

	public orderMaker(TobisBrain meals) {
		Vector tempIngredient;//vector to hold all the information on the current ingredient
		this.ingrList = meals;//creates a variable to hold all the 

		for (int i = 0; i < ingrList.ingredientNameList.size(); i++) {
			tempIngredient = formatIngredient(ingrList.ingredientNameList.get(i), ingrList.ingredientAmountList.get(i));
			for (int j = 0; j < tempIngredient.size(); j++) {
				
				ingredeintinfo.add(tempIngredient.get(j));
			}
		}
	}

	private Vector formatIngredient(String ingrName, Float ingrAmount) {
		Vector ingredient = new Vector(ingredientVectorIncriment, ingredientVectorIncriment);
		int fileIndex =  getIngredientLine(ingrName);
		String tempLine = "";
		String tempWord = "";
		int vectorIndex = 0;
		double tempCase = 0.0;
		boolean secondRoundLoop = false;
		if(fileIndex == -1) {
			System.out.println("Ingredient Does not exist on file");
			ingredient = null;
			return ingredient;
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
				
				//loops through the whole line
				tempLine = file.nextLine();
				//System.out.println(tempLine);
						
				for (int i = 0; i < tempLine.length(); i ++) {
					if (tempLine.charAt(i) == '$') {//Stops the loop at the end of the line
						break;
					}else if(tempLine.charAt(i) == '|'){//logic used once the end of a section is reached
						if(vectorIndex == 1) {//loop that adds the int to the vector with the proper calculation

							tempCase = Math.ceil(  ingrAmount / Double.parseDouble(tempWord));
							//System.out.println(tempCase);
							
							//adds the number to the vector
							ingredient.add(vectorIndex, tempCase);
						} else if(vectorIndex == 3 && secondRoundLoop == false) {
							//empty to skip the unit of measurement in the ingredient and fixes the vector index trip
							vectorIndex -= 1;
							secondRoundLoop = true;
						}else {//adds the word as a string to the vector	
							//System.out.println(tempWord);
							ingredient.add(vectorIndex, tempWord);
						}
						
						//increases the vector count and resets the word
						vectorIndex ++;
						tempWord = "";
						
					}else {//adds a letter to the word
						tempWord += tempLine.charAt(i);
					}
				
				}
				
				file.close();
				
		}	catch (FileNotFoundException e) {
			System.out.println(Driver.getErrorMessage() + "Error Code 3 ");
		}
		
		return ingredient;
		}
		}

	public static int getIngredientLine(String name) {
		int ingrLoc = 0;// initializes the location
		File location = new File("Ingredients.txt");
		Scanner file;
		String tempLine = "";
		try {
			file = new Scanner(location);
			file.nextLine();// skips the format line
			while (file.hasNextLine()) {
				ingrLoc++;
				tempLine = file.nextLine();// sets the line wanted for scanning
				String tempName = "";// resets the name of the object
				for (int i = 0; i < tempLine.length(); i++) {
					if (tempLine.charAt(i) == '|') {
						break;
					} else {
						tempName += tempLine.charAt(i);
					}
				}
				if (tempName.equalsIgnoreCase(name)) {
					return ingrLoc;
				}

			}
			file.close();
		} catch (FileNotFoundException e) {
			System.out.println(Driver.getErrorMessage() + "Error Code 3 " );
		}
		// checks all the line of the ingredient file, looking for a name match

		ingrLoc = -1;// initializes the location as a "No Result" Return
		return ingrLoc;
	}

	public Vector useInventory(Vector unMod) {// UNFINISHED takes in csv and the orderlist(or a Tobis Brain object),
												// subtracts current inventory
		Vector Mod = new Vector(0, ingredientVectorIncriment);
		return Mod;
	}

	/**
	 * THis function runs through all ingredients on the formatted vector of the current ordermaker object
	 * and prints them out to the console
	 */
	public void printOrder() {

		for (int i = 0; i < this.ingredeintinfo.size(); i += ingredientVectorIncriment) {
			System.out.print("You Need " + this.ingredeintinfo.get(i+1));
			System.out.print(" " + this.ingredeintinfo.get(i+2) + " of ");
			System.out.print(this.ingredeintinfo.get(i) + " the ingredient's System name is ");
			System.out.print(this.ingredeintinfo.get(i+3) + " and its id # is ");
			System.out.println(this.ingredeintinfo.get(i+4));
		}

	}
	
	/**
	 * This function writes a CSV file, where each row is one ingredient and the title of the output is the current date
	 * @throws FileNotFoundException 
	 */
	public void writeOrder() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");//Creates a date format
		LocalDate now = LocalDate.now();//gets the raw date data
		String fileName = dtf.format(now) + ".csv";//Creates a string with the value of the current data + the filetype
		File csvOutputFile = new File(fileName); //Creates a file with the value of the current date as the filename
		
		try {
			csvOutputFile.createNewFile();
		} catch (IOException e) {
			System.out.println(Driver.getErrorMessage() + "Error Code 2.1: " + "fileName");
		}
		
		try(PrintWriter pw = new PrintWriter(csvOutputFile)){
			pw.println("Ingr Name,Ingr Amount,Ingr Case, Sys Name, Ingr Num");
			for (int i = 0; i < this.ingredeintinfo.size(); i+= ingredientVectorIncriment ) {
				String tempRow = this.ingredeintinfo.get(i) + "," + this.ingredeintinfo.get(i+1) + "," + this.ingredeintinfo.get(i+2) 
						+ "," + this.ingredeintinfo.get(i+3) + "," + this.ingredeintinfo.get(i+4);
				pw.println(tempRow);
			}
		} catch (IOException e) {
			 System.out.println(Driver.getErrorMessage() + "Error Code 2.2: " + "fileName");
		}
	}

}
