/**
 * A data type class that stores section meeting times (contains days & times)
 * 
 * @author Guangyin Ye, Naw Naw
 *
 */
public class MeetingTimes {

	/**
	 * An array that maps integer index to String day names
	 */
	private static final String[] DAY_NAME = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
	private static final String[] DAY_NAME_SHORT = { "M", "T", "W", "Th", "F", "S" };
	/**
	 * An array that maps integer index to regex strings from Monday through
	 * Saturday
	 */
	private static final String[] DAY_REGEX = { ".*M.*", ".*T(?!h).*", ".*W.*", ".*Th.*", ".*F.*", ".*S.*" };
	/**
	 * Index 0 1 ... 5 corresponds to Monday Tuesday ... Saturday true at one index
	 * means has class on that day
	 */
	private final boolean[] hasClass = new boolean[6];
	/**
	 * In the format of minutes starting from 0:00 am
	 */
	private int startTime;
	/**
	 * In the format of minutes starting from 0:00 am
	 */
	private int endTime;

	/**
	 * Constructs a MeetingTimes instance storing the time info in the given String
	 * 
	 * @param times in the format of [start time]-[end time], time has no ":" in
	 *              between
	 *              <p>
	 *              Ex. 6:30am => 630, 1:30pm => 130
	 *              <p>
	 *              If the start time is later than 4:30pm there should be a "P" at
	 *              the end of the string
	 *              <p>
	 *              for more format info check out
	 *              https://www.washington.edu/students/reg/tshelp/meetings.html
	 */
	public MeetingTimes(String days, String times) {
		boolean startPM, endPM;
		int startHour, startMin, endHour, endMin;
		String startTime = times.split("-")[0];
		String endTime = times.split("-")[1];
		startHour = Integer.parseInt(startTime.substring(0, startTime.length() - 2));
		startMin = Integer.parseInt(startTime.substring(startTime.length() - 2, startTime.length()));
		if (times.indexOf("P") == -1) {
			endHour = Integer.parseInt(endTime.substring(0, endTime.length() - 2));
			endMin = Integer.parseInt(endTime.substring(endTime.length() - 2, endTime.length()));
			if (startHour == 12 || (startHour >= 1 && startHour <= 4)) {
				startPM = true;
			} else {
				startPM = false;
			}
			if (startPM || (endHour >= 1 && endHour <= 5)) {
				endPM = true;
			} else {
				endPM = false;
			}
		} else {
			startPM = true;
			endPM = true;
			endTime = endTime.substring(0, endTime.length() - 1); // get rid of "P" at the end
			endHour = Integer.parseInt(endTime.substring(0, endTime.length() - 2));
			endMin = Integer.parseInt(endTime.substring(endTime.length() - 2, endTime.length()));
		}
		if (startPM && startHour < 12) {
			startHour += 12;
		}
		if (endPM && endHour < 12) {
			endHour += 12;
		}
		this.startTime = startHour * 60 + startMin;
		this.endTime = endHour * 60 + endMin;
		for (int i = 0; i < hasClass.length; i++) {
			hasClass[i] = days.matches(DAY_REGEX[i]);
		}
	}

	/**
	 * @return a String the format of "[start time] to [end time]"
	 *         <p>
	 *         both times are in 24 hours format (%d:%d)
	 */
	public String toString() {
		String res = "Day&Time: ";
		for (int i = 0; i < hasClass.length; i++) {
			if (hasClass[i]) {
				res += DAY_NAME_SHORT[i];
			}
		}
		res += String.format(", %d:%d  to  %d:%d", startTime / 60, startTime % 60, endTime / 60, endTime % 60);
		return res;
	}

	/**
	 * @param other the other MeeetingTimes instance
	 * @return true if this MeetingTimes and other overlaps
	 */
	public boolean conflict(MeetingTimes other) {
		if (other != null) {
			for (int i = 0; i < this.hasClass.length; ++i) {
				if (this.hasClass[i] && other.hasClass[i]
						&& !(this.startTime > other.endTime || this.endTime < other.startTime)) {
					return true;
				}
			}
		}
		return false;
	}

}
