import java.util.*;

/**
 * The Constraints class is used for filtering classes by time and other filters
 * 
 * @author Guangyin Ye, Naw Naw
 *
 */
public class Constraints {
	/**
	 * A Set of MeetingTimes that represents the excluding time interval
	 * <p>
	 * AKA the excluding time interval filter
	 */
	private Set<MeetingTimes> mtSet;

	/**
	 * Constructs an empty Constraints instance
	 */
	public Constraints() {
		mtSet = new HashSet<>();
	}

	/**
	 * Adds the given mt to the excluding time interval set
	 * 
	 * @param mt See MeetingTimes
	 */
	public void addMeetingTimes(MeetingTimes mt) {
		mtSet.add(mt);
	}

	/**
	 * Removes the given mt from the excluding time interval set
	 * 
	 * @param mt See MeetingTimes
	 */
	public void removeMeetingTimes(MeetingTimes mt) {
		mtSet.remove(mt);
	}

	/**
	 * @param lectures The List of Lecture input to filter from
	 * @return A Set of Lecture that satisfy all the filters in this Constraints
	 */
	public Set<Lecture> filterLectures(List<Lecture> lectures) {
		Set<Lecture> res = new HashSet<>();
		for (Lecture lecture : lectures) {
			boolean isConflict = false;
			for (MeetingTimes mt : mtSet) {
				if (lecture.conflict(mt)) {
					isConflict = true;
					break;
				}
			}
			if (!isConflict) {
				res.add(lecture);
			}
		}
		return res;
	}

	/**
	 * @param quizs The List of Quiz input to filter from
	 * @return A Set of Quiz that satisfy all the filters in this Constraints
	 */
	public Set<Quiz> filterQuizs(List<Quiz> quizs) {
		Set<Quiz> res = new HashSet<>();
		for (Quiz quiz : quizs) {
			boolean isConflict = false;
			for (MeetingTimes mt : mtSet) {
				if (quiz.conflict(mt)) {
					isConflict = true;
					break;
				}
			}
			if (!isConflict) {
				res.add(quiz);
			}
		}
		return res;
	}

	/**
	 * @param combo the Combo to check for constraints
	 * @return true if the combo satisfy all the constraints, false otherwise
	 */
	public boolean satisfyConstraints(Combo combo) {
		for (MeetingTimes mt : mtSet) {
			if (combo.conflict(mt)) {
				return false;
			}
		}
		return true;
	}
}
