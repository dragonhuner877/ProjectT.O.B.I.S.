package userInterface;
//outdated class?
public class InterfaceData {
	int campersOne, campersTwo;
	String weekType;

	public InterfaceData () {
		campersOne = 0;
		campersTwo = 0;
		weekType = "";
	}

	/**
	 * @return the campersOne
	 */
	public int getCampersOne() {
		return campersOne;
	}

	/**
	 * @param campersOne the campersOne to set
	 */
	public void setCampersOne(int campersOne) {
		this.campersOne = campersOne;
	}

	/**
	 * @return the campersTwo
	 */
	public int getCampersTwo() {
		return campersTwo;
	}

	/**
	 * @param campersTwo the campersTwo to set
	 */
	public void setCampersTwo(int campersTwo) {
		this.campersTwo = campersTwo;
	}

	/**
	 * @return the weekType
	 */
	public String getWeekType() {
		return weekType;
	}

	/**
	 * @param weekType the weekType to set
	 */
	public void setWeekType(String weekType) {
		this.weekType = weekType;
	}
}
