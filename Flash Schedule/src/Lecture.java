import java.util.*;

/**
 * In addition to Section, Lecture typically include more information such as
 * credits, course fee and quizs, etc.
 * 
 * @author Guangyin Ye, Naw Naw
 *
 */
public class Lecture extends Section {
	private int minCredit;
	private int maxCredit;
	private int courseFee;
	private List<Quiz> quizs;

	/**
	 * 
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
			this.courseFee = Integer.parseInt(courseFee);
		}
		this.quizs = new ArrayList<>();
	}
	
	/**
	 * @return a string showing all the basic info in this Lecture
	 */
	public String toString() {
		return super.toString() + String.format(", Credit: %d-%d, courseFee: %d, number of Quizs: %d", minCredit,
				maxCredit, courseFee, quizs.size());
	}

	public boolean hasAdditionalCost() {
		return courseFee != 0;
	}

	public boolean hasQuiz() {
		return quizs.size() > 0;
	}

	public boolean isCreditConsistent() {
		return minCredit == maxCredit;
	}

	public void addQuiz(Quiz quiz) {
		quizs.add(quiz);
	}

	public List<Quiz> getQuizs() {
		return quizs;
	}

	public Set<Quiz> getQuizs(Constraints constraints) {
		// TODO: finish implementation
		return null; // only for place holder
	}
}
