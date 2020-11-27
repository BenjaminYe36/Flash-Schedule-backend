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
	public Lecture(String enrlRestr, String numSLN, String sectionID, String days, String times, String status, String enrlNum,
			String maxCapacity, String grading, String otherCode, String otherInfo, String credit, String courseFee) {
		super(enrlRestr, numSLN, sectionID, days, times, status, enrlNum, maxCapacity, grading, otherCode, otherInfo);
		if (credit.indexOf('-') != -1) {
			this.minCredit = Integer.parseInt(credit.split("-")[0]);
			this.maxCredit = Integer.parseInt(credit.split("-")[1]);
		} else {
			this.minCredit = Integer.parseInt(credit);
			this.maxCredit = minCredit;
		}
		if (courseFee.equals("")) {
			this.courseFee = 0;
		} else {
			this.courseFee = Integer.parseInt(courseFee);
		}
		this.quizs = new ArrayList<>();
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
		//TODO: finish implementation
		return null; // only for place holder
	}
}
