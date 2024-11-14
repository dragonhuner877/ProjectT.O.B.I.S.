package main;

import java.util.Arrays;

public class Ingredient {
	//name is name of ingredient, orderName is the name printed on the list, Case is the amount in one order
	private String Name;
	private int Case;
	private String CaseLabel;
	private String OrderName;
	private String IngredientNumber;

	/**
	 * The object used to hold the data from a ingredient text file
	 * @param name Holds the name of the ingredient
	 * @param orderName Holds what the website refers to the ingredient as(serial number, technical name, ect)
	 * @param numCase Holds the increment it can be ordered in
	 * @param caseLabel What unit of measurement the case is in
	 */
	public Ingredient (String name, String orderName, int numCase, String caseLabel) {
		this.Name = name;
		this.Case = numCase;
		this.CaseLabel = caseLabel;
		this.OrderName = orderName;

	}

	@Override
	public String toString() {
		return "Ingredient [Name=" + Name + ", Case=" + Case
				+ ", CaseLabel=" + CaseLabel + "]";

	}	
	public String getName() {
		return Name;
	}


	public void setName(String name) {
		Name = name;
	}

}
