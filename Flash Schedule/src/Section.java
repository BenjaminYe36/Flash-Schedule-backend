/**
 * A data type class that encapsulates all the possible fields that can be
 * useful for registration
 * <p>
 * modeled after UW time schedule web site
 * https://www.washington.edu/students/timeschd/WIN2021/ais.html
 * 
 * @author Guangyin Ye, Naw Naw
 *
 */
public class Section {
	/**
	 * Represents Enrollment Restrictions (""/"restr"/"IS"/has">")
	 * <p>
	 * see https://www.washington.edu/students/reg/tshelp/restr.html
	 */
	protected String enrlRestr;
	/**
	 * SLN for this section
	 */
	protected int numSLN;
	/**
	 * (like "A"/"AA"/"B"/"BA")
	 */
	protected String sectionID;
	/**
	 * MeetingTimes that stores meeting days & times in it
	 * <p>
	 * is null when the meeting time is "to be arranged"
	 */
	protected MeetingTimes mt;
	/**
	 * Open or Closed status of this section
	 * <p>
	 * true = open, false = closed
	 */
	protected boolean status;
	/**
	 * The number of currently enrolled students in this section
	 */
	protected int enrlNum;
	/**
	 * Max capacity of number of students in this section
	 */
	protected int maxCapacity;
	/**
	 * true = normal, false = CR/NC
	 */
	protected boolean isNormalGrading;
	/**
	 * see https://www.washington.edu/students/reg/tshelp/other.html
	 * <p>
	 * now we only care about "J"(jointly offered)
	 */
	protected String otherCode;
	/**
	 * other information under the regular course info
	 */
	protected String otherInfo;

	/**
	 * Constructs a Section with all the given strings
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
	public Section(String enrlRestr, String numSLN, String sectionID, String days, String times, String status,
			String enrlNum, String maxCapacity, String grading, String otherCode, String otherInfo) {
		this.enrlRestr = enrlRestr;
		this.numSLN = Integer.parseInt(numSLN);
		this.sectionID = sectionID;
		if (!days.equals("to be arranged")) {
			this.mt = new MeetingTimes(days, times);
		} else {
			this.mt = null;
		}
		this.status = !status.equals("Closed");
		this.enrlNum = Integer.parseInt(enrlNum);
		this.maxCapacity = Integer.parseInt(maxCapacity);
		this.isNormalGrading = grading.equals("");
		this.otherCode = otherCode;
		this.otherInfo = otherInfo;
	}

	/**
	 * @return a string showing all the basic info in this section
	 */
	public String toString() {
		return String.format(
				"hasRestrictions: %b, SLN: %d, SectionID: %s, MeetingTimes: %s, status: %b, "
						+ "enrlNum: %d, maxCapacity: %d, isNormalGrading: %b, otherCode: %s, otherInfo: %s",
				isMajorOnly(), numSLN, sectionID, mt, status, enrlNum, maxCapacity, isNormalGrading, otherCode,
				otherInfo);
	}

	/**
	 * 
	 * @return the SLN number of this section
	 */
	public int getSLN() {
		return numSLN;
	}

	/**
	 * 
	 * @return the sectionID of this section
	 */
	public String getSectionID() {
		return sectionID;
	}

	/**
	 * 
	 * @return the MeetingTimes of this section
	 */
	public MeetingTimes getMeetingTimes() {
		return mt;
	}

	/**
	 * 
	 * @return true if this section is only open for a specific major, false
	 *         otherwise
	 */
	public boolean isMajorOnly() {
		return enrlRestr.matches(".*restr.*");
	}

	/**
	 * 
	 * @return true if the section is open, false if the section is closed
	 */
	public boolean isAvailable() {
		return status;
	}

	/**
	 * 
	 * @return true if this section is jointly offered with another course, false
	 *         otherwise
	 */
	public boolean isJoint() {
		return otherCode.matches(".*J.*");
	}

	/**
	 * 
	 * @return true if this section requires add code to join, false otherwise
	 */
	public boolean hasAddCode() {
		return enrlRestr.matches(".*>.*");
	}

	/**
	 * 
	 * @param other the other Section instance
	 * @return true if this MeetingTimes of this section and other overlaps
	 */
	public boolean conflict(Section other) {
		// TODO: finish implementation
		return true; // only for place holder
	}
}
