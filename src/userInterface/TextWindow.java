package userInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import userInterface.IngredientCreatorUI.IngredientCreationListener;

public class TextWindow {
	public static boolean ingrRecipeState;
	public static boolean editNewState;
	public static JTextField entryField;
	public static JComboBox entryCombo;
	public static JButton go;
	public static JFrame textWindow;
	public String returnString;
	
	public TextWindow(boolean ingrRecipe, boolean editNew) {
		ingrRecipeState = ingrRecipe;
		editNewState = editNew;
		String tempWord = "";
		String outputLabel= "";
		String actionTypeLabel= "";
		String[] tempSelections= {};
		
		if(editNew) {
			actionTypeLabel = "Edit";
			if(ingrRecipe) {
				outputLabel = "Ingredient";
				tempSelections = getIngrNames();
			}else {
				outputLabel = "Recipe";
				tempSelections = RecipeListManager.readList("...\\..\\MasterRecipes.txt");
			}
		}else {
			actionTypeLabel = "Create";
			if(ingrRecipe) {
				outputLabel = "Ingredient";
				tempSelections = getIngrNames();
			}else {
				outputLabel = "Recipe";
				tempSelections = RecipeListManager.readList("...\\..\\MasterRecipes.txt");
			}
		}
		
		
		
		textWindow = new JFrame("Name Collection");
		JPanel dataEntry = new JPanel(new GridBagLayout());
		dataEntry.setBackground(Color.white);
		GridBagConstraints config = new GridBagConstraints();
		
		JLabel actionLabel = new JLabel("Enter your " + outputLabel + " to " + actionTypeLabel + " ");
		dataEntry.add(actionLabel,config);
		
		config.gridy = 1;
		if(editNew) {
			entryCombo = new JComboBox(tempSelections);
			dataEntry.add(entryCombo, config);
			AutoCompletion.enable(entryCombo);
		}else {
			entryField = new JTextField("", 10);
			dataEntry.add(entryField, config);
		}
		
		go = new JButton(this.new GoButtonListener());
		config.gridy = 2;
		dataEntry.add(go,config);

		textWindow.add(dataEntry);
		textWindow.setSize(GUI.getWindowWidth(), GUI.getWindowHeight());// sets the size of the page
		textWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);// sets the default exit button function
		textWindow.setVisible(true);
		
		this.returnString = tempWord;
	}
	
	public static String[] getIngrNames() {
		String[] tempStr = null;
		String fileName = "...\\..\\Ingredients.txt";
		String buffer = ""; // creates a blank buffer to add letters to
		String tempWord;
		ArrayList<String> ingr = new ArrayList();  // creates an array list to add all the recipes through
		File tempFile = new File(fileName);
		Scanner ingrFile;
		try {
			ingrFile = new Scanner(tempFile);
			while(ingrFile.hasNextLine()) { //Goes through the recipe text file until it reaches the end of it
				buffer = ingrFile.nextLine(); // reads the current line
				tempWord = "";
				for(int i = 0; i < buffer.length(); i++) {
					if (buffer.charAt(i) == '|') {
						break;
					}else {
						tempWord += buffer.charAt(i);
					}
					
				}
				ingr.add(tempWord); // adds the line to the list
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // opens the file
		
		tempStr = new String[ingr.size()]; // creates the final array with the size of the array list
		for(int i = 0; i < ingr.size(); i++) { // adds all the elements from the list to the final array
			tempStr[i] = ingr.get(i);
		}
		
		return tempStr;
	}
	public class GoButtonListener extends AbstractAction{
		GoButtonListener(){
			super("Go");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			String tempText;
			if(ingrRecipeState) {
				if(editNewState) {
					tempText = (String) entryCombo.getSelectedItem();
					IngredientEditorUI test = new IngredientEditorUI(tempText);
				}else {	
					tempText = entryField.getText();
					IngredientCreatorUI test = new IngredientCreatorUI(tempText);
				}
			}else {
				if(editNewState) {
					tempText = (String) entryCombo.getSelectedItem();
					RecipeEditor newRec = new RecipeEditor(tempText);
				}else {
					tempText = entryField.getText();
					RecipeUI newRec = new RecipeUI(tempText);
				}
			}
			
			textWindow.setVisible(false);
		}

	}
}
