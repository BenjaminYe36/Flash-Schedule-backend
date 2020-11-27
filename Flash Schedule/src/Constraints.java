import java.util.*;

/**
 * The Constraints class is used for filtering classes by time and other filters
 * 
 * @author Guangyin Ye, Naw Naw
 *
 */
public class Constraints {
	private Set<MeetingTimes> mtSet;

	public Constraints() {
		mtSet = new HashSet<>();
	}

	public void addMeetingTimes(MeetingTimes mt) {
		mtSet.add(mt);
	}
	
	public void removeMeetingTimes(MeetingTimes mt) {
		mtSet.remove(mt);
	}

	public Set<Lecture> filterLectures(List<Lecture> lectures) {
		// TODO: finish implementation
		return null; // only for place holder
	}

	public Set<Quiz> filterQuizs(List<Quiz> quizs) {
		// TODO: finish implementation
		return null; // only for place holder
	}
}
