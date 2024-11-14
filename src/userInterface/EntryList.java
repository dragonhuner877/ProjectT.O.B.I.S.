package userInterface;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import main.Driver;
import main.orderMaker;

public class EntryList extends JPanel{
	private List<Entry> entries;
	private static final String [] ingrMasterList = getIngrList();
	public String[] temp = {};
	private GridBagConstraints configOrder;
	
	public EntryList() {
		super(new GridBagLayout());
		this.entries = new ArrayList<Entry>();
		this.configOrder = new GridBagConstraints(); //Config variable for the grid size
		configOrder.gridx = 0;
		configOrder.gridy = 0;
		
		JComboBox initialBox = new JComboBox(ingrMasterList);
		initialBox.addItemListener(new ItemChangeListener());
		AutoCompletion.enable(initialBox);
		
		JComboBox initialMeasurement = new JComboBox(temp);
		AutoCompletion.enable(initialMeasurement);
		
		Entry initial = new Entry(initialBox, initialMeasurement, this );
		addItem(initial,configOrder);
		
	}

	public void cloneEntry(Entry entry) {
		JComboBox copyA = new JComboBox(ingrMasterList);
		copyA.addItemListener(new ItemChangeListener());
		AutoCompletion.enable(copyA);
		
		JComboBox copyB = new JComboBox(temp);
		AutoCompletion.enable(copyB);
		
		Entry theClone = new Entry(copyA, copyB, this);
		
		addItem(theClone, this.configOrder);
	}
	
	
	private void addItem(Entry entry,GridBagConstraints config) {
		entries.add(entry);
		
		config.gridy = entries.size();
		add(entry, config);
		refresh();
	}
	
	public void removeItem(Entry entry) {
		entries.remove(entry);
		
		remove(entry);
		refresh();
		repaint();
	}

	private void refresh() {
		revalidate();
		
		if(entries.size() ==1) {
			entries.get(0).enableMinus(false);
		}else {
			for(Entry e : entries) {
				e.enableMinus(true);
			}
		}
		
	}
	
	
	public List<Entry> getEntries() {
		return entries;
	}
	
	
	private static String[] getIngrList() {
		Vector<String> tempList = new Vector<String>();
		String tempLine,tempWord;
		
		
		//creates the file location
		File location = new File("Ingredients.txt");
			
		//tries the loop for errors
		try {		
			//creates the text scanner
			Scanner file = new Scanner(location);
			//Loops through all ingredients
			while(file.hasNextLine()) {
				tempLine = file.nextLine();
				tempWord = "";
				for(int i = 0; i < tempLine.length(); i++) {
					if(tempLine.charAt(i) == '|') {
						tempList.add(tempWord);
						break;
					}else {
						tempWord = tempWord + tempLine.charAt(i); 
					}
				}
			}
			
			file.close();	
		}catch (FileNotFoundException e) {
			System.out.println(Driver.getErrorMessage() + "Error Code 3 ");
		}
		String[] finalArray = new String[tempList.size()];
		for(int i = 0; i < tempList.size(); i++) {
			finalArray[i] = tempList.get(i);
		}
	
		return finalArray;
	}


	public class ItemChangeListener implements ItemListener{

		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				JComboBox temp = (JComboBox) e.getSource();
				Entry tempo = (Entry) temp.getParent();
				String[] tempArray = tempo.findUnitType();
				tempo.changeArray(tempArray);
				
			}
		}
		
	}
}
