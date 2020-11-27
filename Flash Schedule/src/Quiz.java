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
	 * 
	 * @param args args[0] is "Q"
	 * @return a new Quiz given all the string in the splitted String[] in args
	 */
	public static Quiz fromCSV(String... args) {
		return new Quiz(args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8], args[9], args[10],
				args[11]);
	}

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
	public Quiz(String enrlRestr, String numSLN, String sectionID, String days, String times, String status,
			String enrlNum, String maxCapacity, String grading, String otherCode, String otherInfo) {
		super(enrlRestr, numSLN, sectionID, days, times, status, enrlNum, maxCapacity, grading, otherCode, otherInfo);
	}

}
