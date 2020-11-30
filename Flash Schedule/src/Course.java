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
	/**
	 * The Map that maps credit type strings to booleans
	 * <p>
	 * True means has that credit, false means doesn't have that credit
	 */
	private Map<String, Boolean> creditTable;
	/**
	 * (Ex. "CSE" in "CSE 143")
	 */
	private String coursePrefix;
	/**
	 * (Ex. 143 in "CSE 143")
	 */
	private int courseCode;
	/**
	 * The title of this Course
	 * <p>
	 * (Ex. "Computer Programming II")
	 */
	private String title;
	/**
	 * String that stores credit type for convenience of printing
	 */
	private String creditTypeString;
	/**
	 * The boolean value that represents PreRequisites
	 * <p>
	 * true = has , false = doesn't have
	 */
	private boolean dependency;
	/**
	 * The List of Lecture(s) under this Course
	 */
	List<Lecture> lectures;

	/**
	 * @param args args[0] is "C"
	 * @return a new Course given all the string in the splitted String[] in args
	 */
	public static Course fromCSV(String... args) {
		return new Course(args[1], args[2], args[3], args[4], args[5]);
	}

	/**
	 * Constructs a Course instance with the given Strings
	 * 
	 * @param coursePrefix
	 * @param courseCode
	 * @param title
	 * @param creditType
	 * @param dependency
	 */
	public Course(String coursePrefix, String courseCode, String title, String creditType, String dependency) {
		this.creditTable = Stream
				.of(new String[][] { { "C", "0" }, { "DIV", "0" }, { "I&S", "0" }, { "NW", "0" }, { "QSR", "0" },
						{ "VLPA", "0" }, { "W", "0" } })
				.collect(Collectors.toMap(p -> p[0], p -> Boolean.parseBoolean(p[1])));
		this.coursePrefix = coursePrefix;
		this.courseCode = Integer.parseInt(courseCode);
		this.title = title;
		this.creditTypeString = creditType;
		for (String s : creditType.split("[/,]")) {
			creditTable.put(s, true);
		}
		this.dependency = dependency.matches(".*Prerequisites.*");
		this.lectures = new ArrayList<>();
	}

	/**
	 * @param creditType The String of credit type that wants to be checked in this
	 *                   course
	 * @return true if this Course has the given creditType
	 */
	public boolean hasCredit(String creditType) {
		return hasCredit(creditType, false, false);
	}

	/**
	 * @param creditType The String of credit type that wants to be checked in this
	 *                   course
	 * @param needDIV    true if wants to filter for this course has DIV credit type
	 * @param needQSR    true if wants to filter for this course has QSR credit type
	 * @return true if this Course has the given creditType & (DIV & QSR if
	 *         specified in needDIV & needQSR), false otherwise
	 */
	public boolean hasCredit(String creditType, boolean needDIV, boolean needQSR) {
		if (creditTable.containsKey(creditType)) {
			return creditTable.get(creditType) && (needDIV ? creditTable.get("DIV") : true)
					&& (needQSR ? creditTable.get("QSR") : true);
		}
		return false;
	}

	/**
	 * @return a string showing all the basic info in this course
	 */
	public String toString() {
		return String.format("Course: %s %s: %s, Credit Type: %s, Has PreReq: %b, Lecture numbers: %d", coursePrefix,
				courseCode, title, creditTypeString, dependency, lectures.size());
	}

	/**
	 * Adds the given lecture to this Course
	 * 
	 * @param lecture
	 */
	public void addLecture(Lecture lecture) {
		lectures.add(lecture);
	}

	/**
	 * @param constraints the object that stores the filters
	 * @return a Set of Combo (valid Lecture + Quiz section pair) that satisfy the
	 *         given constraints
	 */
	public Set<Combo> validCombinations(Constraints constraints) {
		Set<Combo> combinations = new HashSet<>();
		for (Lecture lecture : getLectures(constraints)) {
			if (!lecture.hasQuiz()) {
				combinations.add(new Combo(coursePrefix, courseCode, title, lecture, null));
			} else {
				for (Quiz quiz : lecture.getQuizs(constraints)) {
					combinations.add(new Combo(coursePrefix, courseCode, title, lecture, quiz));
				}
			}
		}
		return combinations;
	}

	/**
	 * @param constraints the object that stores the filters
	 * @return a Set of Lectures that satisfy the given constraints
	 */
	public Set<Lecture> getLectures(Constraints constraints) {
		return constraints.filterLectures(lectures);
	}

	/**
	 * @return a List that contains all the Lecture under this Course
	 */
	public List<Lecture> getLectures() {
		return lectures;
	}
	
	/**
	 * @return course ID
	 */
	public String getCourseID() {
		return coursePrefix + courseCode;
	}
	
	/**
	 * @return true if this Course has PreRequisite Courses, false otherwise
	 */
	public boolean hasPreRequisite() {
		return dependency;
	}
	
}
