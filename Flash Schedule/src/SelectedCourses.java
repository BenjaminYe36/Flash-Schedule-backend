import java.util.*;

/**
 * A data structure class that stores the Selected Courses for the client.
 * <p>
 * Clients can add and remove courses from the SelctedCourses class.
 * 
 * @author Guangyin Ye, Naw Naw
 *
 */
public class SelectedCourses {
	/**
	 * A Set that stores the course type of selected courses
	 * <p>
	 * Ex. CSE 143 with no specified lecture/quiz sections
	 */
	private Set<Course> courses;
	/**
	 * A Map that stores the mapping from a Lecture to course Prefix + course Code +
	 * course title string
	 * <p>
	 * Its keySet is the set of selected lectures.
	 * <p>
	 * Ex. {Lecture : "CSE 143: Intro to Programming", ...}
	 */
	private Map<Lecture, String> lectures;
	/**
	 * A Set that stores the specific Lecture + Quiz section Combos
	 * <p>
	 * Ex. CSE 143 A + AA
	 */
	private Set<Combo> combos;

	/**
	 * Constructs a SelectedCourses instance with initialized collections
	 */
	public SelectedCourses() {
		this.courses = new HashSet<>();
		this.lectures = new HashMap<>();
		this.combos = new HashSet<>();
	}

	/**
	 * adds the given course into this SlectedCourses
	 * 
	 * @param course
	 */
	public void addCourse(Course course) {
		courses.add(course);
	}

	/**
	 * adds the given lecture & courseInfo into this SlectedCourses
	 * 
	 * @param lecture
	 * @param courseInfo
	 */
	public void addLecture(Lecture lecture, String courseInfo) {
		lectures.put(lecture, courseInfo);
	}

	/**
	 * adds the given combo into this SlectedCourses
	 * 
	 * @param combo
	 */
	public void addCombo(Combo combo) {
		combos.add(combo);
	}

	/**
	 * removes the given course from this SlectedCourses
	 * 
	 * @param course
	 */
	public void removeCourse(Course course) {
		courses.remove(course);
	}

	/**
	 * removes the given lecture from this SlectedCourses
	 * 
	 * @param lecture
	 */
	public void removeLecture(Lecture lecture) {
		lectures.remove(lecture);
	}

	/**
	 * removes the given combo from this SlectedCourses
	 * 
	 * @param combo
	 */
	public void removeCombo(Combo combo) {
		combos.remove(combo);
	}
}
