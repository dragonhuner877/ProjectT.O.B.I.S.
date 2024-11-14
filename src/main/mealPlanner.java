package main;

public class mealPlanner {
	//3D array the meal planner object will modify
	private String[][][] Schedule = {};
	/**
	 * Creates the mealPlanner Object, which is used to store the meals in their respective days and meal slots
	 */
	public mealPlanner() {
		this.Schedule = plannerConstructor();
	}

	//Setting up arrays to loop through for individual meal selection, as well as date variable, 
	static String[] dayOfTheWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
	static String[] mealTypes = {"Breakfast", "Side", "Lunch", "Side", "Dinner", "Side",  "Snack"};

	/**
	 * @return an empty 3D string which is used to hold the meals
	 */
	public static String[][][] plannerConstructor() {
		//3d array with first dimension being the days of week, and 2nd being the meals in that day, and the 3rd being the actual recipe name
		String[][][] mealSchedule = new String[dayOfTheWeek.length][7][2];
		//Create a 3 dimensional array with each day and the meal they have on it and an open space for the recipe in each meal, by looping through the list of days, and looping through types of meal
		for (int i= 0; i < dayOfTheWeek.length; i ++) { //looping through the days
			for(int x = 0; x < mealTypes.length; x += 1) { // looping through each type of meal
					mealSchedule[i][x][0] = mealTypes[x]; //sets the internal 3d array of the planner to hold the meals and days
				}
			}
		//Returns empty meal schedule
		return mealSchedule;
	}
	
	/**
	 * sets a meal at a position in the day and meal index to inputed recipe
	 * @param day The Day of the week
	 * @param meal The Meal set as
	 * @param recipe The recipe stored in that slot
	 */
	public void setMeal (int day, int meal, String recipe) {
		Schedule[day][meal][1] = recipe;
	}

	/**
	 * gets the string name of the recipe at points day and meal in the 3d arrays index
	 * @param day the Desired day index
	 * @param meal the desired meal index
	 * @return the meal stored there
	 */
	public String getMeal(int day, int meal) {
		String temp = Schedule[day][meal][1];
		return temp;
	}

}
