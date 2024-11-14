package userInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class RecipeEditor{
	public String fed;
	public Vector<String> names, amounts, measures;
	public String[] ingrNames, ingrMeasures;
	public Double[] ingrAmounts;
	
	public RecipeEditor(String name) {
		//finding the info
		File file = new File("...\\..\\Recipes\\"+name+".txt");
		String buffer, tempWord;
		names = new Vector();
		amounts = new Vector();
		measures = new Vector();
		
		try {
			Scanner textFile = new Scanner(file);
			textFile.nextLine();
			textFile.nextLine();
			while(textFile.hasNextLine()) {//loops through all ingredients
				tempWord = "";
				buffer = textFile.nextLine();
				if(buffer.equals("!"))
					break;
				for(int i = 0; i < buffer.length(); i++) {
					if (buffer.charAt(i) == '|') {
						names.add(tempWord);
						tempWord = "";
					
					}else if(buffer.charAt(i) == '$') {
						amounts.add(tempWord);
						tempWord = "";
					}else {
						tempWord = tempWord + buffer.charAt(i);
					}
				}
				measures.add(tempWord);
			}
			
			ingrNames = new String[measures.size()];
			ingrAmounts = new Double[measures.size()];
			ingrMeasures = new String[measures.size()];
			for(int i = 0; i < measures.size(); i++) {
				ingrNames[i] = names.get(i);
				ingrAmounts[i] = MeasurementTranslator.translateMeasurement(false, Double.parseDouble(amounts.get(i)), measures.get(i), names.get(i));
				ingrMeasures[i] = measures.get(i);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//filling the info
		RecipeUI empty = new RecipeUI(name);
		empty.setAmountFed("5");
		empty.setMealTypes(RecipeListManager.findActiveLists(name));
		empty.setIngredients(ingrNames, ingrAmounts, ingrMeasures);
	}
	
	
	
}
