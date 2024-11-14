package main;

import java.util.ArrayList;
import java.util.Scanner;

//Takes in unpacked recipes, takes in number of campers, outputs 2d array of ingredients and quantiteis
public class TobisBrain {
	ArrayList<String> ingredientNameList;
	ArrayList<Float> ingredientAmountList;
	
	/**
	 * Loops through meal array from meal planner and makes calculations
	 * @param meals the full meal schedule
	 */
	public TobisBrain(mealPlanner meals, String weekType, int fullWeekCampers, int halfWeekCampers) {


		//String [][][] ingredientList = new String [mealPlanner.dayOfTheWeek.length][7][2];
		this.ingredientNameList  = new ArrayList<String>();
		this.ingredientAmountList = new ArrayList<Float>();
		mealPlanner Meal = meals;

		//Loops through each day of the week
		for(int i = 0; i < 7; i++) {
			//loops through each mealtype of that day
			for (int x = 0; x < 7; x++) {
				//sets recipe object to a recipe of the meal at day i, meal x
				Recipe recipe = new Recipe(Meal.getMeal(i, x));

				for (int y = 0; y < recipe.getOrderData().length; y++) { //loops through all ingredients of the list to create a master list
					int tempIndex = 0;//defaulting the variable for holding duplicate ingredient indexes
					
					try{	
						if(ingredientNameList.contains( recipe.getOrderData()[y][0])) {//if the ingredient is already in the list
							String tempRecipeName = recipe.getOrderData()[y][0];//the name of the recipe
							tempIndex = getIndex(ingredientNameList, tempRecipeName);
							float currentAmount = ingredientAmountList.get(tempIndex); //gets the current value of the ingredient
							// gets the new 
							float addedAmount = IngredientMath(Meal.getMeal(i, x) , tempIndex, i, weekType, fullWeekCampers, halfWeekCampers);
							float tempAmountFinal = currentAmount  + addedAmount;
							ingredientAmountList.set(tempIndex, tempAmountFinal);//adds the amount to the already needed number
							
						}
						else {
							ingredientNameList.add(recipe.getOrderData()[y][0]);
							float tempIngrAmountAdd = IngredientMath(Meal.getMeal(i, x), y, i, weekType, fullWeekCampers, halfWeekCampers);
							ingredientAmountList.add(tempIngrAmountAdd);
							
						}
					}catch(Exception ex) {
						System.out.println(Driver.getErrorMessage() + "ERROR 4 "+ ex.getLocalizedMessage());
					}
				}
			}

		}
	}
	/** this function takes in the recipe and location of desired ingredient in said recipe and then uses the 
	 * 	 configurations of the week to run the calculations needed to determine the neccecary amount of desired ingredient for one
	 *  instance of the ingredient and current recipe
	 *  
	 * @param recipeName inputed recipe name of ingredient for the recipe function to search for a file and open
	 * @param ingredientIndex the index of the ingredient in the current recipe being searched
	 * @param dayNumber the current day of the week to determine the weektype calculation
	 * @param weekType the week type for camper calculations
	 * @param fullWeek the first box input of campers
	 * @param halfWeek the second box input of campers
	 * @return the amount of this ingredient needed as a float for this specific instance of the recipe
	 */
	public float IngredientMath(String recipeName, int ingredientIndex, int dayNumber, String weekType, int fullWeek, int halfWeek) {
		//calls recipe class, looks through recipe for specific ingredient amounts and feeds, divides amount by feed
		Recipe recipe = new Recipe(recipeName);// creates an instance of the desired recipe
		float tempInt = 0; //
		float tempAmount;
		float tempFeeds;
	
				tempAmount = Float.parseFloat(recipe.getOrderData()[ingredientIndex][1]);
				tempFeeds = (float)recipe.getFeeds(); 
				tempInt = (tempAmount/tempFeeds);
				if(weekType.equals("fullWeek")) {
					tempInt = tempInt * fullWeek;
				}else if(weekType.equals("halfWeek")) {
					if(dayNumber < 4) {
						tempInt = tempInt * (fullWeek+halfWeek);
					}
					else {
						tempInt = tempInt * fullWeek;
					}
				}else if(weekType.equals("fatherSon")) {
					if(dayNumber < 4) {
						tempInt = tempInt * halfWeek;
					}else {
						tempInt = tempInt * fullWeek;
					}
				}
		
				
		return tempInt;
	}
	
	/** This takes in the current list of ingredient and loops through until it finds where the desired ingredient
	 * is located in said list
	 * 
	 * @param orderList the list of names to match up
	 * @param ingredientName the ingredient name to match too
	 * @return the index of that ingredient
	 */
	public int getIndex(ArrayList<String> orderList, String ingredientName) {
		int tempInt = 0;
		for (int i = 0; i < orderList.size(); i++) {
			if (orderList.get(i).equals(ingredientName)) {
				tempInt = i;
			}
		}
		return tempInt;
	}
	
}

