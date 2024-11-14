package userInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class RecipeUI {
	private JFrame ui;
	private JPanel recipe;
	private JTextField  amountFed;
	private JLabel recipeName, nameField, feedsField;
	private JButton createRecipe;
	private JCheckBox[] mealTypes = new JCheckBox[5];
	private EntryList panel;
	
	public RecipeUI(String name) {
		this.ui = new JFrame("Recipe Creator"); //the main window for the creator menu		
		
		this.panel = new EntryList();
		
		this.recipe = new JPanel(new GridBagLayout());//creates the recipe panel
		recipe.setBackground(Color.white); //Configures the background color
		
		GridBagConstraints config = new GridBagConstraints(); //Config variable for the grid size

		this.nameField = new JLabel("Name of Recipe:"); //defining and assigning
		config.gridx = 0;//setting configs
		config.gridy = 0;
		recipe.add(nameField, config); //adding to the pane
		
		this.recipeName = new JLabel(name);// defines the textfield for the name
		config.gridx = 1;
		recipe.add(recipeName, config); //adding to the pane
		
		this.feedsField = new JLabel("Campers Fed:"); //defining and assigning
		config.gridx = 0;//setting configs
		config.gridy = 1;
		recipe.add(feedsField, config); //adding to the pane
		
		this.amountFed = new JTextField();// defines the textfield for the name
		amountFed.setText("0"); //setting the default value
		config.gridx = 1;
		recipe.add(amountFed, config); //adding to the pane
		
		this.createRecipe = new JButton(new RecipeCreationListener());
		config.gridwidth = 2;
		config.gridx = 0;
		config.gridy = 2;
		recipe.add(createRecipe, config);
		
		int tempCounter = 3;
		config.gridwidth = 1;
		config.fill = GridBagConstraints.BOTH;
		for(int i = 0; i < mealTypes.length; i++) {
			if(i % 2 == 0) {
				config.gridx = 0;
				config.gridy = tempCounter;
			}else {
				config.gridx = 1;
				tempCounter++;
			}
			this.mealTypes[i] = new JCheckBox(RecipeListManager.prefixi[(i+1)]);
			recipe.add(mealTypes[i],config);
		}

		
		ui.add(panel);
		ui.add(recipe, BorderLayout.NORTH); //adds the menu window to the active page
		
		
		// Configuring the window
		ui.setSize(GUI.getWindowWidth(), GUI.getWindowHeight());// sets the size of the page
		ui.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);// sets the default exit button function
		
		ui.setVisible(true);
	}
	
	public void setRecipeName(String recipeName) {
		this.recipeName.setText(recipeName);
	}

	public void setAmountFed(String amountFed) {
		this.amountFed.setText(amountFed);
	}

	public void setMealTypes(boolean[] mealTypes) {
		for(int i = 0; i <mealTypes.length; i++) {
			this.mealTypes[i].setSelected(mealTypes[i]);
		}

	}

	public void setIngredients(String[] names, Double[] amounts, String[] measures) {
		Entry tempIngredient = this.panel.getEntries().get(0);
		tempIngredient.getIngredientBox().setSelectedItem(names[0]);
		tempIngredient.getIngredientAmount().setText(amounts[0].toString());
		tempIngredient.getMeasurementBox().setSelectedItem(measures[0]);
		for(int i = 1; i < names.length; i++) {
			this.panel.cloneEntry(this.panel.getEntries().get((i-1)));
			tempIngredient = this.panel.getEntries().get(i);
			tempIngredient.getIngredientBox().setSelectedItem(names[i]);
			tempIngredient.getIngredientAmount().setText(amounts[i].toString());
			tempIngredient.getMeasurementBox().setSelectedItem(measures[i]);
		}
		
	}

	public class RecipeCreationListener extends AbstractAction{
			RecipeCreationListener(){
				super("Create Recipe");
			}
		@Override
		public void actionPerformed(ActionEvent e) {
			String name = recipeName.getText();
			int feeds = Integer.parseInt(amountFed.getText());
			JFrame tempFrame = (JFrame) SwingUtilities.getRoot((Component) e.getSource());
			JRootPane tempPane = (JRootPane) tempFrame.getComponents()[0];
			JLayeredPane tempLPane = (JLayeredPane) tempPane.getComponent(1);
			JPanel tempPanel = (JPanel) tempLPane.getComponents()[0];
			EntryList tempEntryList = (EntryList) tempPanel.getComponents()[0];
			Entry tempEntry;
			Boolean[] mealConfig = {true, false, false, false, false, false,};
			
					
			int numIngr = tempEntryList.getEntries().size();
			String[] ingredientList = new String[numIngr];
			double [] ingredientAmounts = new double[numIngr];
			String[] ingredientMeasures = new String[numIngr];
			for(int i = 0; i < numIngr; i++) {
				tempEntry = tempEntryList.getEntries().get(i);
				ingredientList[i] = (String) tempEntry.getIngredientBox().getSelectedItem();
				ingredientAmounts[i] = Double.parseDouble( tempEntry.getIngredientAmount().getText());
				ingredientMeasures[i] = (String) tempEntry.getMeasurementBox().getSelectedItem();		
			}
			for(int i = 0; i < mealTypes.length; i++) {
				mealConfig[(i+1)] = mealTypes[i].isSelected();
			}
			
			
			RecipeWriter newRecipe = new RecipeWriter(name, ingredientList, ingredientAmounts, ingredientMeasures, feeds, true);
			RecipeListManager.addRecipe(name, mealConfig);
			
			tempFrame.hide();
		}
		
	}
	
	
}
