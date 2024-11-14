package userInterface;

public class IngredientEditorUI {
	public IngredientEditorUI(String name) {
		String tempName, tempCase, tempLabel, tempUnit, tempCataName, tempSerial;
		IngredientCreatorUI edited = new IngredientCreatorUI(name);
		int index = IngredientCreatorUI.isOnList(IngredientCreatorUI.readIngredients(), name);
		String[] tempList = edited.readIngredients();
		String ingrLine = tempList[index];
		int counter = 0;
		tempName = "";
		tempCase = ""; 
		tempLabel = ""; 
		tempUnit = "";
		tempCataName = "";
		tempSerial = "";
		for(int i = 0; i < ingrLine.length(); i++) {
			if (ingrLine.charAt(i) == '$') {
				break;
			}else if (ingrLine.charAt(i) == '|') {
				counter++;
			}else {
				switch(counter) {
				case 0:
					tempName += ingrLine.charAt(i);
					break;
				case 1:
					tempCase += ingrLine.charAt(i);
					break;
				case 2:
					tempLabel += ingrLine.charAt(i);
					break;
				case 3:
					tempUnit += ingrLine.charAt(i);
					break;
				case 4:
					tempCataName += ingrLine.charAt(i);
					break;
				case 5:
					tempSerial += ingrLine.charAt(i);
					break;
				}
			}
		}
		
		
		edited.setCaseField(tempCase);
		edited.setCaseLabelField(tempLabel);
		edited.setCaseUnitField(tempUnit);
		edited.setOrderNameField(tempCataName);
		edited.setIngredientNumberField(tempSerial);
		
		
	}
}
