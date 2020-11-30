import java.util.*;
import java.util.regex.*;

/**
 * An Utility class that provides methods with algorithms to generate possible
 * schedules
 * <p>
 * It does not store any fields in it.
 * 
 * @author Guangyin Ye, Naw Naw
 *
 */
public class ScheduleBuilder {
	/**
	 * The Regex pattern for matching Course Prefix + Course Code + Course title for convenience
	 * <p>
	 * Course Prefix is group 1, Course Code is Group 2, Course title is Group 3
	 */
	public static final Pattern COURSE_REGEX = Pattern.compile("^([A-Z&]+ *[A-Z&]+) *([0-9]+) *: *(.*)");
	
	/**
	 * @param sc          See SelectedCourses class
	 * @param constraints the object that stores all the filters
	 * @return a Set of possible schedules(Set of Combos) given the SelectedCourses
	 *         and satisfies the constraints
	 *         <p>
	 *         When there is no possible schedules, it will return an empty Set
	 */
	public static Set<Set<Combo>> generateSchedules(SelectedCourses sc, Constraints constraints) {
		Set<Set<Combo>> possibleSchedule = new HashSet<>();
		// use map to separate 
		Map<String, Set<Combo>> courses = new HashMap<>(); // courses that must be in schedule
		
		if(sc.getCourses() != null) { // #1 From our implementation of sc, if sc isn't null the inner collection could never be null (see the constructor)
			for(Course course : sc.getCourses()) {
				// #2 if there is no possible quiz sections with the given constraints, there is no possible schedule
				Set<Combo> validCombos = course.validCombinations(constraints);
				if (validCombos.isEmpty()) {
					return possibleSchedule;
				}
				courses.put(course.getCourseID(), validCombos);
			}
		}
		
		if(sc.getLectures() != null) { // #1 same reason as line 34
			for(Map.Entry<Lecture, String> entry : sc.getLectures().entrySet()) {
				// extracts course code, prefix and title
				
				// Old code
//				String coursePrefix="", courseCode="", value = entry.getValue();
//				String title = value.substring(value.indexOf(':') + 1);
//				Lecture lecture = entry.getKey();
//				for(int i=0; i<value.length(); ++i) {
//					char ch = value.charAt(i);
//					if(ch == ':')  break;
//					if(ch == ' ') continue;
//					if(ch >= '0' && ch <= '9') {
//						courseCode += ch;
//					}else {
//						coursePrefix += ch;
//					}
//				}
				
				// #3 use regex to simplify the extraction
				// New code
				Matcher matcher = COURSE_REGEX.matcher(entry.getValue());
				String coursePrefix, courseCode, title;
				if (matcher.find()) {
					coursePrefix = matcher.group(1);
					courseCode = matcher.group(2);
					title = matcher.group(3);
				} else {
					throw new IllegalArgumentException("The course info String in Lecture map is not valid!");
				}
				
				Lecture lecture = entry.getKey();
				Set<Combo> combos = new HashSet<>();
				if(lecture.hasQuiz()) {
					// #2 if there is no possible quiz sections with the given constraints, there is no possible schedule
					Set<Quiz> filteredQuizs = lecture.getQuizs(constraints);
					if (filteredQuizs.isEmpty()) {
						return possibleSchedule;
					}
					for(Quiz quiz : filteredQuizs) {
						combos.add(new Combo(coursePrefix, Integer.valueOf(courseCode), title, lecture, quiz));
					}
				}else {
					combos.add(new Combo(coursePrefix, Integer.valueOf(courseCode), title, lecture, null));
				}
				// #4 forgets to add the Combo Set constructed from each lecture to the courses map?
				// Added New code
				courses.put(coursePrefix + courseCode, combos);
			}
			
		}
		
		if(sc.getCombos() != null) { // #1 same reason as line 34
			for(Combo combo : sc.getCombos()) {
				// #2 if any combo doesn't satisfy the constraints, there is no possible schedule
				if(!constraints.satisfyConstraints(combo)) {
					return possibleSchedule;
				}
				courses.put(combo.getComboID(), new HashSet<>());
				courses.get(combo.getComboID()).add(combo);
			}
		}
		
		// Old code
//		ArrayList<Set<Combo>> arrli=new ArrayList<>();
//		for(Map.Entry<String, Set<Combo>> entry : courses.entrySet()) {
//			arrli.add(entry.getValue());
//		}
		
		// #5 simplify arrayList copy by passing in the values collection (should be the same outcome?)
		// New code
		ArrayList<Set<Combo>> arrli = new ArrayList<>(courses.values());
		
		generateSchedules(arrli, 0, new HashSet<>(), possibleSchedule);
		return possibleSchedule; // only for place holder (delete this comment after implementation)
	}
	
	
	private static void generateSchedules(ArrayList<Set<Combo>> courses, int courseIndex, Set<Combo> sofar, Set<Set<Combo>> possibleSchedule) {
		if(courseIndex == courses.size()) {
			possibleSchedule.add(new HashSet<>(sofar));
		} else {
			// System.out.println(courses.get(courseIndex).size());

			for(Combo combo : courses.get(courseIndex)) {
				if(canAdd(sofar, combo)) {
					sofar.add(combo);
					generateSchedules(courses, courseIndex + 1, sofar, possibleSchedule);
					sofar.remove(combo);
				}

			}
		}
	}
	
	/**
	 * @return true if combo have concurrent meeting times with any combo in set combos
	 */
	private static boolean canAdd(Set<Combo> combos, Combo combo) {
		for(Combo e : combos) {
			if(e.conflict(combo)) return false;
		}
		
		return true;
	}
}
