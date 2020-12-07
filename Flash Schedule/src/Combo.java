/**
 * The Combo class mainly used for the schedule builder part, so that every
 * valid selection(a Lecture + Quiz pair)/(A Lecture + null if it has no Quiz)
 * of courses is the same type
 * 
 * @author Guangyin Ye, Naw Naw
 *
 */
public class Combo {
	/**
	 * (Ex. "CSE" in "CSE 143")
	 */
	private String coursePrefix;
	/**
	 * (Ex. 143 in "CSE 143")
	 */
	private int courseCode;
	/**
	 * The title of the course that contains this Lecture + Quiz Combo
	 * <p>
	 * (Ex. "Computer Programming II")
	 */
	private String title;
	/**
	 * The Lecture section stored in this Combo
	 */
	private Lecture lecture;
	/**
	 * The Quiz section stored in this Combo.
	 * <p>
	 * If this Combo doesn't has a Quiz, it will be null
	 */
	private Quiz quiz;

	/**
	 * Constructs a Combo that stores the given parameters
	 * 
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
	 * @param other The other Combo instance
	 * @return true if this MeetingTimes of this Combo and other overlaps
	 */
	public boolean conflict(Combo other) {
		if (this.quiz != null) {
			return this.lecture.conflict(other.lecture) || this.lecture.conflict(other.quiz)
					|| this.quiz.conflict(other.lecture) || this.quiz.conflict(other.quiz);
		}
		return this.lecture.conflict(other.lecture) || this.lecture.conflict(other.quiz);
	}

	/**
	 * @param mt see MeetingTimes
	 * @return true if this Combo overlaps with the given mt, false otherwise
	 */
	public boolean conflict(MeetingTimes mt) {
		if (this.quiz != null) {
			return this.lecture.conflict(mt) || this.quiz.conflict(mt);
		}
		return this.lecture.conflict(mt);
	}

	/**
	 * @return combo ID
	 */
	public String getComboID() {
		return coursePrefix + courseCode;
	}

	/**
	 * @return a String representation of lecture in this combo in a front end
	 *         required format
	 */
	public String getLectureString() {
		return String.format("%s %s %s", coursePrefix, courseCode, lecture.getSectionID());
	}

	/**
	 * @return a String representation of quiz in this combo in a front end required
	 *         format, null if quiz is null
	 */
	public String getQuizString() {
		if (quiz == null) {
			return null;
		} else {
			return String.format("%s %s %s", coursePrefix, courseCode, quiz.getSectionID());
		}
	}
	
	/**
	 * @return the lecture stored in this Combo
	 */
	public Lecture getLecture() {
		return lecture;
	}
	
	/**
	 * @return the quiz stored in this Combo, the quiz could be null
	 */
	public Quiz getQuiz() {
		return quiz;
	}

	/**
	 * @return a string showing the basic info about this Combo
	 *         <p>
	 *         Lecture + Quiz(if not null)
	 */
	public String toString() {
		return String.format("%s %s, Lecture: %s, Quiz: %s", coursePrefix, courseCode, lecture, quiz);
	}
}
