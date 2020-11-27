/**
 * The Combo class mainly used for the schedule builder part, so that every
 * valid selection(a Lecture + Quiz pair)/(A Lecture + null if it has no Quiz)
 * of courses is the same type
 * 
 * @author Guangyin Ye, Naw Naw
 *
 */
public class Combo {
	private String coursePrefix;
	private int courseCode;
	private String title;

	private Lecture lecture;
	private Quiz quiz;
	
	/**
	 * Constructs a Combo that stores the given parameters
	 * @param coursePrefix
	 * @param courseCode
	 * @param title
	 * @param lecture
	 * @param quiz
	 */
	public Combo(String coursePrefix, int courseCode, String title, Lecture lecture, Quiz quiz) {
		this.coursePrefix = coursePrefix;
		this.courseCode = courseCode;
		this.title = title;
		this.lecture = lecture;
		this.quiz = quiz;
	}

	/**
	 * 
	 * @param other The other Combo instance
	 * @return true if this MeetingTimes of this Combo and other overlaps
	 */
	public boolean conflict(Combo other) {
		// TODO: finish implementation
		return true; // only for place holder
	}
}
