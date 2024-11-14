package userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Entry extends JPanel {
	private JComboBox ingredientBox, measurementBox;
	private JTextField ingredientAmount;
	private JButton plus, minus;
	private EntryList parent;
	
	public Entry(JComboBox ingredient, JComboBox measurement, EntryList list) {
		this.ingredientBox = ingredient;
		this.measurementBox = measurement;
		this.parent = list;
		this.plus = new JButton(new AddEntryAction());
		this.minus = new JButton(new RemoveEntryAction());
		this.ingredientAmount = new JTextField(10);
		this.ingredientAmount.setText("0");
		add(this.plus);
		add(this.ingredientBox);
		add(this.ingredientAmount);
		add(this.measurementBox);
		add(this.minus);		
	}

	public JComboBox getIngredientBox() {
		return ingredientBox;
	}

	public JComboBox getMeasurementBox() {
		return measurementBox;
	}

	public JTextField getIngredientAmount() {
		return ingredientAmount;
	}
	
	public class AddEntryAction extends AbstractAction{

		public AddEntryAction() {
			super("+");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			parent.cloneEntry(Entry.this);
			
		}
		
	}
	public class RemoveEntryAction extends AbstractAction{

		public RemoveEntryAction() {
			super("-");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			parent.removeItem(Entry.this);
			
		}	
	}

	
	public void enableAdd(boolean enabled) {
		this.plus.setEnabled(enabled);
	}
	
	public void enableMinus(boolean enabled) {
		this.minus.setEnabled(enabled);
	}
	public void changeArray(String[] array) {
		this.measurementBox.removeAllItems();
		
		for(int i = 0; i < array.length; i++) {
			this.measurementBox.addItem(array[i]);
		}
	}
	
	public String[] findUnitType() {
		String[] tempReturn = {"Units"};
		String tempIngr = (String) this.ingredientBox.getSelectedItem();
		String tempMeasure = "";
		String tempLine = MeasurementTranslator.getIngredientLine(tempIngr);
		tempMeasure = MeasurementTranslator.getIngredientMeasure(tempLine);
		
		if (MeasurementTranslator.contains(tempMeasure,MeasurementTranslator.weightMeasurementUnits)) { 
			return MeasurementTranslator.weightMeasurementUnits;
		}else if(MeasurementTranslator.contains(tempMeasure,MeasurementTranslator.volumeMeasurementUnits)) {
			return MeasurementTranslator.volumeMeasurementUnits;
		}
		
		return tempReturn;
	}
}

