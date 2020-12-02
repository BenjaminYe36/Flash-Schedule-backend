import java.util.*;

/**
 * In addition to Section, Lecture typically include more information such as
 * credits, course fee and quizs, etc.
 * 
 * @author Guangyin Ye, Naw Naw
 *
 */
public class Lecture extends Section {
	/**
	 * The minimum credit this Lecture provides
	 */
	private int minCredit;
	/**
	 * The maximum credit this Lecture provides
	 */
	private int maxCredit;
	/**
	 * The additional cost for taking this Lecture
	 * <p>
	 * None is 0
	 */
	private int courseFee;
	/**
	 * The List of Quiz(s) (if any) under this Lecture section
	 */
	private List<Quiz> quizs;

	/**
	 * @param args args[0] is "L"
	 * @return a new Lecture given all the string in the splitted String[] in args
	 */
	public static Lecture fromCSV(String... args) {
		return new Lecture(args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8], args[9], args[10],
				args[11], args[12], args[13]);
	}

	/**
	 * Constructs a Lecture section with all the given strings
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
	 * @param credit
	 * @param courseFee
	 */
	public Lecture(String enrlRestr, String numSLN, String sectionID, String days, String times, String status,
			String enrlNum, String maxCapacity, String grading, String otherCode, String otherInfo, String credit,
			String courseFee) {
		super(enrlRestr, numSLN, sectionID, days, times, status, enrlNum, maxCapacity, grading, otherCode, otherInfo);
		if (credit.indexOf('-') != -1) {
			this.minCredit = Integer.parseInt(credit.split("-")[0]);
			this.maxCredit = Integer.parseInt(credit.split("-")[1]);
		} else {
			this.minCredit = Integer.parseInt(credit);
			this.maxCredit = minCredit;
		}
		if (courseFee.strip().equals("")) {
			this.courseFee = 0;
		} else {
			this.courseFee = Integer.parseInt(courseFee.strip());
		}
		this.quizs = new ArrayList<>();
	}

	/**
	 * @return a string showing all the detailed info in this Lecture
	 */
	public String detailedToString() {
		return super.detailedToString() + String.format(", Credit: %d-%d, courseFee: %d, number of Quizs: %d", minCredit,
				maxCredit, courseFee, quizs.size());
	}
	
	/**
	 * @return a string showing basic info in this Lecture
	 */
	public String toString() {
		return super.toString() + String.format(", Credit: %d-%d", minCredit, maxCredit);
	}

	/**
	 * @return true if this Lecture has additional cost requirement, false if not
	 */
	public boolean hasAdditionalCost() {
		return courseFee != 0;
	}

	/**
	 * @return true if this Lecture has Quiz sections that come with it, false if
	 *         not
	 */
	public boolean hasQuiz() {
		return quizs.size() > 0;
	}

	/**
	 * @return true if this Lecture doesn't have a range of credit values, false if
	 *         not
	 */
	public boolean isCreditConsistent() {
		return minCredit == maxCredit;
	}
	
	/**
	 * Adds the given quiz to this Lecture
	 * @param quiz
	 */
	public void addQuiz(Quiz quiz) {
		quizs.add(quiz);
	}
	
	/**
	 * @return the List that contains all the Quiz under this Lecture
	 */
	public List<Quiz> getQuizs() {
		return quizs;
	}
	
	/**
	 * @param constraints the object that stores the filters
	 * @return a Set of Quizs that satisfy the given constraints
	 */
	public Set<Quiz> getQuizs(Constraints constraints) {
		return constraints.filterQuizs(quizs);
	}
}
