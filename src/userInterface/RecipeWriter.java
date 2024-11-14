package userInterface;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * A class which is responsible for taking in the parameters of a recipe and 
 * writing them into the format for a text file in which the tobis brain
 * can then reread
 */
public class RecipeWriter {
	//
	public RecipeWriter(String name, String[] ingredientList, double [] ingredientAmounts, String[] ingredientMeasures, int feeds,boolean readWrite ) {
		String fileName = "../Project_TOBIS/Recipes/" + name + ".txt";
		double tempIngredientAmount = 0.0;
		File file = new File(fileName);
		String tempLine = "";
		try {
			PrintWriter writer = new PrintWriter(file);
			writer.println("//First line is the name of recipe, following lines are the ingredients and their quantities, ordered as name first, separated with the \"|\" character, followed by the ingredient \"universal\" amount, separated with a \"$\" character and followed with the unit of measurement to be converted to. The \"!\" character denotes the end of ingredients and the line after contains the number of people the recipe serves");
			writer.println(name);
			for (int i = 0; i < ingredientList.length; i++) {
				tempIngredientAmount = MeasurementTranslator.translateMeasurement(readWrite, ingredientAmounts[i], ingredientMeasures[i], ingredientList[i]);
				tempLine = ingredientList[i] + "|" + tempIngredientAmount + "$" + ingredientMeasures[i];
				writer.println(tempLine);
				
			}
			writer.println("!");
			writer.println(feeds);
			
			writer.close();
		}catch(IOException ex){
			ex.printStackTrace();
			
		}
	}
	
}
