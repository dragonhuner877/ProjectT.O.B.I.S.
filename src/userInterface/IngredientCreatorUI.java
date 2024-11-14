package userInterface;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import userInterface.RecipeUI.RecipeCreationListener;

public class IngredientCreatorUI {
	String[] measUnits = {"units", "mltr", "tsp", "tbsp", "oz(volume)", "cup", "pint", "quart", "litre", "Gal", "grams", "oz(weight)", "lbs"};
	JFrame IngredientWindow;
	JPanel ingredient;
	JLabel ingrName, spaceC, caseText, caseLabelText, caseUnitText, orderNameText, ingredientNumberText;
	JTextField caseField, caseLabelField, orderNameField, ingredientNumberField;
	JComboBox caseUnitField;
	JButton createIngr;
	public IngredientCreatorUI(String name) {
		this.IngredientWindow = new JFrame("Ingredient Creator");
		this.ingredient = new JPanel(new GridBagLayout());
		ingredient.setBackground(Color.white);
		GridBagConstraints config = new GridBagConstraints();
		
		//Static name text
		ingrName = new JLabel("Name of Ingredient ");
		config.gridx  = 0;
		config.gridy = 0;
		ingredient.add(ingrName, config);
		
		spaceC = new JLabel(name);
		config.gridx  = 1;
		ingredient.add(spaceC, config);
		
		//Amount minimum per order, field
		caseText = new JLabel("Amount in a case ");
		config.gridx  = 0;
		config.gridy = 1;
		ingredient.add(caseText, config);
		
		caseField = new JTextField("", 10);
		config.gridx = 1;
		ingredient.add(caseField,config);
		
		//label the cases are ordered in field
		caseLabelText = new JLabel("Name of case ");
		config.gridx = 0;
		config.gridy = 2;
		ingredient.add(caseLabelText,config);
		
		caseLabelField = new JTextField("", 10);
		config.gridx = 1;
		ingredient.add(caseLabelField, config);
		
		//unit of measurement of the cases dropdown
		caseUnitText = new JLabel("Type of measurement ");
		config.gridx = 0;
		config.gridy = 3;
		ingredient.add(caseUnitText,config);
		
		caseUnitField = new JComboBox(measUnits);
		config.gridx = 1;
		ingredient.add(caseUnitField, config);
		
		//SystemName field
		orderNameText = new JLabel("Catalogue Name ");
		config.gridx = 0;
		config.gridy = 4;
		ingredient.add(orderNameText,config);
		
		orderNameField = new JTextField("n/a", 10);
		config.gridx = 1;
		ingredient.add(orderNameField, config);
		
		//serialNumberField
		ingredientNumberText = new JLabel("Serial number ");
		config.gridx = 0;
		config.gridy = 5;
		ingredient.add(ingredientNumberText,config);
		
		ingredientNumberField = new JTextField("n/a", 10);
		config.gridx = 1;
		ingredient.add(ingredientNumberField, config);
		
		//finish button
		createIngr = new JButton(new IngredientCreationListener());
		config.gridwidth = 2;
		config.gridx = 0;
		config.gridy = 6;
		ingredient.add(createIngr, config);
				
		IngredientWindow.add(ingredient);
		IngredientWindow.setSize(GUI.getWindowWidth(), GUI.getWindowHeight());// sets the size of the page
		IngredientWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// sets the default exit button function
		IngredientWindow.setVisible(true);
		
	}
	
	//Action listener
	public class IngredientCreationListener extends AbstractAction{
		IngredientCreationListener(){
			super("Create Ingredient");
		}
	@Override
	public void actionPerformed(ActionEvent e) {
		String name = spaceC.getText();
		String newIngr = "";
		String caseResult = caseField.getText();
		String caseLabelResult = caseLabelField.getText();
		String caseUnitResult = (String) caseUnitField.getSelectedItem();
		String orderNameResult = orderNameField.getText();
		String ingredientNumberResult = ingredientNumberField.getText();
		newIngr = name + "|" + caseResult + "|" + caseLabelResult + "|" + caseUnitResult + "|"
				+ orderNameResult + "|" + ingredientNumberResult + "$";
				
		writeFile(newIngr);
		IngredientWindow.hide();
	}
	
}
	//read ingrList
	public static String[] readIngredients() {	
		String buffer = ""; // creates a blank buffer to add letters to
		ArrayList<String> ingredients = new ArrayList();  // creates an array list to add all the ingredient lines to
		File tempFile = new File("...\\..\\Ingredients.txt");
		Scanner ingrFile;
		try {
			ingrFile = new Scanner(tempFile);
			ingrFile.nextLine();
			while(ingrFile.hasNextLine()) { //Goes through the ingredient text file until it reaches the end of it
				buffer = ingrFile.nextLine(); // reads the current line
				ingredients.add(buffer); // adds the line to the list
			}
			ingrFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // opens the file
		
		String[] ingredientArray = new String[ingredients.size()]; // creates the final array with the size of the array list
		for(int i = 0; i < ingredients.size(); i++) { // adds all the elements from the list to the final array
			ingredientArray[i] = ingredients.get(i);
		}
		return ingredientArray; //returns the final array of ingredient Lines
	}
	
	
	
	
	//Add to file
	public static void writeFile(String newLine) {
		String[] oldFile = readIngredients();
		oldFile = sortAddString(oldFile,newLine);
		try {
			PrintWriter file = new PrintWriter(new File("...\\..\\Ingredients.txt"));
			file.println();
			for(int i = 0; i< oldFile.length; i++) {
				file.println(oldFile[i]);	
			}
			file.close();
		} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
			}
		}
	
		
	

	//sortFunction
	public static String[] sortAddString(String[] oldArray, String newEntry) {
		int arrayLen = oldArray.length + 1;
		String name = "";
		String[] newArray;
		for(int i = 0; i < newEntry.length(); i ++) {
			if(newEntry.charAt(i) == '|') {
				break;
			}
			name += newEntry.charAt(i);
		}
		
		
		int index = isOnList(oldArray, name);
		if (index == -1) {
			newArray = new String[arrayLen];
			for (int i = 0; i< oldArray.length; i++) {
				newArray[i] = oldArray[i];
			}
			newArray[oldArray.length] = newEntry;
		}else {
			newArray = new String[oldArray.length];
			for (int i = 0; i< oldArray.length; i++) {
				if (i == index) {
					newArray[i] = newEntry;
				}else {
					newArray[i] = oldArray[i];
				}
			}
		}
			
		Arrays.sort(newArray);
		
		return newArray;
	}
	
	//StringCompare
	public static boolean ingrGreaterThan(String a, String b) {
		boolean result = true;
		for(int i = 0; i < a.length(); i++) {
			if(a.charAt(i) == '|' || b.charAt(i) == '|'  ) {
				break;
			}
			if(!(a.charAt(i) > b.charAt(i))) {
				return false;
			}
		}
		
		
		return result;
	}
	
	public static int isOnList(String[] searchedArray, String name) {
		
		for(int i = 0; i < searchedArray.length; i++) {
			String tempWord = "";
			for(int j = 0; j < searchedArray[i].length(); j++) {
				if (searchedArray[i].charAt(j) == '|') {
					break;
				}else {
					tempWord += searchedArray[i].charAt(j);
				}
			}
			if(tempWord.equals(name)) {
				return i;
			}
		}
		
		return -1;
	}


	public void setSpaceC(String spaceC) {
		this.spaceC.setText(spaceC);
	}


	public void setCaseField(String caseField) {
		this.caseField.setText(caseField); 
	}


	public void setCaseLabelField(String caseLabelField) {
		this.caseLabelField.setText(caseLabelField);
	}


	public void setOrderNameField(String orderNameField) {
		this.orderNameField.setText(orderNameField);
	}


	public void setIngredientNumberField(String ingredientNumberField) {
		this.ingredientNumberField.setText(ingredientNumberField);
	}


	public void setCaseUnitField(String caseUnitField) {
		this.caseUnitField.setSelectedItem(caseUnitField);
	}
}
