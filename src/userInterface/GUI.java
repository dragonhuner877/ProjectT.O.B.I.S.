package userInterface;

import javax.swing.*;

import main.Driver;
import main.TobisBrain;
import main.mealPlanner;
import main.orderMaker;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GUI {

	final static int windowWidth = 1200;
	final static int windowHeight = 800;
	private JFrame gui;
	private JLabel weekType, camperInputHelpOne, camperInputHelpTwo, ingrLabel, recipeLabel;
	private JRadioButton normalWeek, halfWeek, fatherSon;
	private JButton submitSchedule, ingrCreate, ingrEdit, recipeCreate, recipeEdit;
	private ButtonGroup weekButtons;
	private JTextField halfOne, halfTwo;
	private JPanel weekSelect, mealLayout;
	private ButtonGroup weekSelection;
	public InterfaceData transfer;
	public static JComboBox[] mealInputs = new JComboBox[49];
	public JPanel[] mealFormats = new JPanel[49];
	public JLabel[] formatTitles = new JLabel[49];
	
	public GUI() {
		
		gui = new JFrame("Menu");// Main planner window

		weekType = new JLabel("What type of Week is it?");//sets text for the question field

		submitSchedule = new JButton("Finish"); //Creates the submission button
		submitSchedule.addActionListener(new FinalSweepListener()); //assigns a listener for the Submission button

		JPanel menu = new JPanel(new BorderLayout()); //Creates the Menu Window With he Border Layout

		menu.add(submitSchedule, BorderLayout.EAST); //Adds The Submission Button the the window

		menu.setBackground(Color.white); //Configures the background color

		gui.add(menu); //adds the menu window to the active page

		// Configuring the window
		gui.setSize(windowWidth, windowHeight);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		weekSelect = new JPanel(new GridBagLayout());// creating the panel for week Selection
		GridBagConstraints config = new GridBagConstraints(); //Config variable for the grid size
		
		
		
		weekSelection = new ButtonGroup(); //button group to organize and add single active button functionality

		
		config.fill = GridBagConstraints.HORIZONTAL; //setting the fill settings
		config.anchor = GridBagConstraints.CENTER; //Setting the orientation settings
		
		//Positioning for the first element
		config.gridx = 0;
		config.gridy = 1;
		
		normalWeek = new JRadioButton("Full Week");//creates the button
		normalWeek.addActionListener(new FullWeekListener()); //assigns the button functioality
		normalWeek.setSelected(true); //Sets the default selection
		
		weekSelect.add(normalWeek, config);//adds the button to the panel
		weekSelection.add(normalWeek);// adds the button to the group
		
		//repeats lines 68-73 for the half week and full week buttons
		halfWeek = new JRadioButton("Half Week");
		halfWeek.addActionListener(new HalfWeekListener());
		config.gridy =2; //sets position of the button
		weekSelect.add(halfWeek, config);
		weekSelection.add(halfWeek);
		fatherSon = new JRadioButton("Father Son");
		fatherSon.addActionListener(new FatherSonListener());
		config.gridy = 3; 
		weekSelect.add(fatherSon, config);
		weekSelection.add(fatherSon);
		
		
		halfOne = new JTextField(4); //making the first text field
		halfOne.setText("0"); //setting the default value
		config.gridy = 5; //positioning the field
		weekSelect.add(halfOne, config); //adding it to the pane
		
		//repeating lines 88-91 for the second field
		halfTwo = new JTextField(4);
		config.gridx = 1;
		halfTwo.setText("0");
		halfTwo.setVisible(false);//defaulting it to hidden
		weekSelect.add(halfTwo,config);
		
		
		camperInputHelpOne = new JLabel("Full week Campers:");//Declaring the label
		config.gridy = 4;// positioning the label
		config.gridx = 0;
		weekSelect.add(camperInputHelpOne, config); // adding it to the pane
		camperInputHelpTwo = new JLabel("--------------------------"); //creating the blank section
		config.gridx = 1;//positioning the label
		weekSelect.add(camperInputHelpTwo, config); //adding the second field to the pane

		//positioning and adding a the question label
		config.gridy = 0;
		config.gridx = 0;
		weekSelect.add(weekType,config);
		
		//adding the submit button to the pane
		config.gridy = 6;
		weekSelect.add(submitSchedule, config);
		
		//adding the ingredient functions
		config.gridy = 7;
		config.gridwidth = 2;
		ingrLabel = new JLabel("Ingredient");
		weekSelect.add(ingrLabel, config);
		
		ingrCreate = new JButton(new IngredientCreationListener());
		config.gridy = 8;
		config.gridwidth = 1;
		weekSelect.add(ingrCreate, config);
		
		ingrEdit = new JButton(new IngredientEditorListener());
		config.gridx = 1;
		weekSelect.add(ingrEdit, config);	

		//adding the Recipe functions
		config.gridy = 9;
		config.gridx = 0;
		config.gridwidth = 2;
		recipeLabel = new JLabel("Recipe");
		weekSelect.add(recipeLabel, config);
		
		recipeCreate = new JButton(new RecipeCreationListener());
		config.gridy = 10;
		config.gridwidth = 1;
		weekSelect.add(recipeCreate, config);
		
		recipeEdit = new JButton(new RecipeEditorListener());
		config.gridx = 1;
		weekSelect.add(recipeEdit, config);
		
		GridLayout mealGrid = new GridLayout(7,7);//creates the grid for the meal selection
		mealLayout = new JPanel(mealGrid); //creates a panel for the meal selectors to be stored
		
	
		// Gets the list of recipes
		String[] breakfast = getRecipes("Breakfast"); 
		String[] lunch = getRecipes("Lunch");
		String[] dinner = getRecipes("Dinner");
		String[] sides = getRecipes("Sides");
		String[] snack = getRecipes("Snacks");
		
		
		for (int i = 0; i < mealInputs.length; i++) { //loops through the days and adds the selectors
			int dayDetermine = (i/7); // determines what day of the week it is based on the modulo
			int tempDay = dayDetermine%7; // determines what day of the week it is based on the modulo
			

			
			mealFormats[i] = new JPanel(new BorderLayout()); // creates a panel for the selectors to be held in for the week 
			
			switch(tempDay) { // determines what meal list to use for the dropdown
			case 0: 
				formatTitles[i] = new JLabel("Breakfast"); //sets the title to the mealtype in the array
				mealInputs[i] = new JComboBox(breakfast); //creates the dropdown box with the mealtype list
				break;
			case 1:
				formatTitles[i] = new JLabel("Side");
				mealInputs[i] = new JComboBox(sides);
				break;
			case 2:
				formatTitles[i] = new JLabel("Lunch");
				mealInputs[i] = new JComboBox(lunch);
				break;
			case 3:
				formatTitles[i] = new JLabel("Side");
				mealInputs[i] = new JComboBox(sides);
				break;
			case 4:
				formatTitles[i] = new JLabel("Dinner");
				mealInputs[i] = new JComboBox(dinner);
				break;
			case 5:
				formatTitles[i] = new JLabel("Side");
				mealInputs[i] = new JComboBox(sides);
				break;
			case 6:
				formatTitles[i] = new JLabel("Snack");
				mealInputs[i] = new JComboBox(snack);
				break;
			default:
				formatTitles[i] = new JLabel("Error");// catches any items that dont match the criteria
				break;
			}
			mealFormats[i].add(formatTitles[i], BorderLayout.NORTH);// adds the new title to the section
			mealFormats[i].add(mealInputs[i], BorderLayout.SOUTH); // adds the new input box to the array
			mealLayout.add(mealFormats[i]); // adds it to the meal layout Jpane
			AutoCompletion.enable(mealInputs[i]); // enables the autocomplete modules code
		}

		gui.add(weekSelect, BorderLayout.WEST);// adds the week selection pane to the left side of the main pane
		gui.add(mealLayout); // adds the selector layout to the center of the screen
		gui.setVisible(true); // sets the entire menu GUI to visible
		

	}
	/**
	 * @return the windowWidth
	 */
	public static int getWindowWidth() {
		return windowWidth;
	}
	/**
	 * @return the windowHeight
	 */
	public static int getWindowHeight() {
		return windowHeight;
	}
	/**
	 * 
	 * @param index the JComboBox to Choose from
	 * @return the recipe name at the desired index
	 */
	public static String getRecipeName(int index) {
		String recipeName = "";// sets a default value of empty
		
		recipeName = (String) mealInputs[index].getSelectedItem(); // gets the current element in the desired combo box and formats it as a String
		
		return recipeName; //returns the recipe name
	}

	public class FinalSweepListener implements ActionListener {//the Listener for the submit button

		@Override
		public void actionPerformed(ActionEvent e) {
			String weekType = "fullWeek";// sets the default value of the string to the fullweek
			int partOne = Integer.parseInt(halfOne.getText()); // gets the value of the first text field and turns it into an integer
			int partTwo = Integer.parseInt(halfTwo.getText()); // gets the value of the second text field and turns it into an integer

			if (halfWeek.isSelected()) { // inputs which button is selected
				weekType = "halfWeek";
			} else if (normalWeek.isSelected()) {
				weekType = "fullWeek";
			} else if (fatherSon.isSelected()) {
				weekType = "fatherSon";
			}

			runBrain(weekType, partOne, partTwo );
			 // Inputs the data from the week selection fields
		}

	}
	public class FullWeekListener implements ActionListener{ // the button that switches the text fields to Full week Configuration

		@Override
		public void actionPerformed(ActionEvent e) { 
			camperInputHelpOne.setText("Full week Campers:"); //sets the JLabel
			camperInputHelpTwo.setText("--------------------------"); //Sets the Secondary text
			halfTwo.setText("0"); // Defaults the value
			halfTwo.setVisible(false); // Hides the second box
		}
		
	}

	public class HalfWeekListener implements ActionListener{ // Action for the Halfweek button

		@Override
		public void actionPerformed(ActionEvent e) {
			camperInputHelpOne.setText("Full week campers:");
			camperInputHelpTwo.setText("Half Week Campers:");
			halfTwo.setVisible(true);

		}
		
	}
	public class FatherSonListener implements ActionListener{ // Action for the Father Son Button

		@Override
		public void actionPerformed(ActionEvent e) {
			camperInputHelpOne.setText("First Half Campers:");      
			camperInputHelpTwo.setText("Second Half Campers:");
			halfTwo.setVisible(true);

		}
		
	}
	
	public class IngredientCreationListener extends AbstractAction{
		IngredientCreationListener(){
			super("Create ");
		}
	@Override
	public void actionPerformed(ActionEvent e) {
		TextWindow temp = new TextWindow(true, false);
	}
	
}
	
	public class IngredientEditorListener extends AbstractAction{
		IngredientEditorListener(){
			super("Edit");
		}
	@Override
	public void actionPerformed(ActionEvent e) {
		TextWindow temp = new TextWindow(true, true);
		
	}
	
}
	
	public class RecipeCreationListener extends AbstractAction{
		RecipeCreationListener(){
			super("Create ");
		}
	@Override
	public void actionPerformed(ActionEvent e) {
		TextWindow temp = new TextWindow(false, false);
		
	}
	
}
	
	public class RecipeEditorListener extends AbstractAction{
		RecipeEditorListener(){
			super("Edit ");
		}
	@Override
	public void actionPerformed(ActionEvent e) {
		TextWindow temp = new TextWindow(false, true);
		
	}
	
}
	/**
	 * 
	 * @param meal the meal type AKA "Breakfast", "Lunch", "Dinner", "Snack"
	 * @return returns an array of all the mealnames for that type
	 */
	public static  String[] getRecipes(String meal) {

		File recipeLocation = new File( meal + "Recipes.txt"); // creates a file for the general list of recipes
		String buffer = ""; // creates a blank buffer to add letters to
		ArrayList<String> recipes = new ArrayList();  // creates an array list to add all the recipes through
		try {
			Scanner recipeList = new Scanner(recipeLocation); // opens the file
			while(recipeList.hasNextLine()) { //Goes through the recipe text file until it reaches the end of it
				buffer = recipeList.nextLine(); // reads the current line
				recipes.add(buffer); // adds the line to the list
			}
		} catch (FileNotFoundException e) {//prints the error message if there is one
			System.out.println( Driver.getErrorMessage() + "Error Code 1: " + meal + "Recipes.txt");
		}
		String[] recipesArray = new String[recipes.size()]; // creates the final array with the size of the array list
		for(int i = 0; i < recipes.size(); i++) { // adds all the elements from the list to the final array
			recipesArray[i] = recipes.get(i);
		}
		return recipesArray; //returns the final array of recipe names
	}
	
	public static void runBrain(String WeekType, int FullWeekCampers, int HalfWeekCampers){
		mealPlanner Meal = new mealPlanner();
		
		for (int i = 0; i < 49; i++) {//sets all of the meals from the GUI into one list
			//two variables so that the order of the meals is collected one day at a time, in the correct order
				int recipeDay = i % 7; 
				int recipeMeal = ((i-(i%7))/7);

				Meal.setMeal(recipeDay, recipeMeal, getRecipeName(i)); //putting all the meals into the list
		}
		//Runs the Brain using the list of meals, creates list of ingredients	
		TobisBrain TOBIS = new TobisBrain(Meal, WeekType, FullWeekCampers, HalfWeekCampers );
		//Takes list of Ingredients into 
		orderMaker Order = new orderMaker(TOBIS);

			Order.writeOrder();
		

	}
}
