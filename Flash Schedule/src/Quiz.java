/**
 * A data type class that has exactly the same fields and methods as Section
 * <p>
 * The Quiz class is just to separate Quiz sections from Lectures
 * 
 * @author Guangyin Ye, Naw Naw
 *
 */
public class Quiz extends Section {

	/**
	 * Constructs a Quiz section with all the given strings
	 * 
	 * @param enrlRestr
	 * @param numSLN
	 * @param sectionID
	 * @param days
	 * @param times
	 * @param enrlNum
	 * @param maxCapacity
	 * @param grading
	 * @param otherCode
	 * @param otherInfo
	 */
	public Quiz(String enrlRestr, String numSLN, String sectionID, String days, String times, String enrlNum,
			String maxCapacity, String grading, String otherCode, String otherInfo) {
		super(enrlRestr, numSLN, sectionID, days, times, enrlNum, maxCapacity, grading, otherCode, otherInfo);
	}

}
