import java.util.*;
import java.util.stream.Stream;
import java.util.stream.Collectors;

/**
 * Course class which encapsulate all the possible fields that can be useful for
 * registration. (Course has a list of Lectures and each Lecture has a list of
 * Quizs)
 * 
 * @author Guangyin Ye, Naw Naw
 *
 */
public class Course {
	private static final Map<String, Integer> CREDIT_TYPE_TABLE = Stream.of(new String[][] { { "C", "0" },
			{ "DIV", "1" }, { "I&S", "2" }, { "NW", "3" }, { "QSR", "4" }, { "VLPA", "5" }, { "W", "6" } })
			.collect(Collectors.toMap(p -> p[0], p -> Integer.parseInt(p[1])));
	private String coursePrefix;
	private int courseCode;
	private String title;
	private String creditTypeString;
	private Set<Set<Integer>> creditType;
	private boolean dependency;
	List<Lecture> lectures;

	/**
	 * 
	 * @param args args[0] is "C"
	 * @return a new Course given all the string in the splitted String[] in args
	 */
	public static Course fromCSV(String... args) {
		return new Course(args[1], args[2], args[3], args[4], args[5]);
	}

	public Course(String coursePrefix, String courseCode, String title, String creditType, String dependency) {
		this.coursePrefix = coursePrefix;
		this.courseCode = Integer.parseInt(courseCode);
		this.title = title;
		this.creditTypeString = creditType;
		this.creditType = creditHelper(creditType);
		this.dependency = dependency.matches(".*Prerequisites.*");
		this.lectures = new ArrayList<>();
	}

	private Set<Set<Integer>> creditHelper(String creditType) {
		// TODO: finish implementation
		return null; // only for place holder
	}
	
	/**
	 * @return a string showing all the basic info in this course
	 */
	public String toString() {
		return String.format("Course: %s %s: %s, Credit Type: %s, Has PreReq: %b, Lecture numbers: %d", coursePrefix,
				courseCode, title, creditTypeString, dependency, lectures.size());
	}

	public void addLecture(Lecture lecture) {
		lectures.add(lecture);
	}

	public Set<Combo> validCombintations(Constraints constraints) {
		// TODO: finish implementation
		return null; // only for place holder
	}

	public Set<Lecture> getLectures(Constraints constraints) {
		// TODO: finish implementation
		return null; // only for place holder
	}

	public List<Lecture> getLectures() {
		return lectures;
	}

	public boolean hasPreRequisite() {
		return dependency;
	}
}
