import java.util.*;

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
	 * @param sc          See SelectedCourses class
	 * @param constraints the object that stores all the filters
	 * @return a Set of possible schedules(Set of Combos) given the SelectedCourses
	 *         and satisfies the constraints
	 *         <p>
	 *         When there is no possible schedules, it will return an empty Set
	 */
	public static Set<Set<Combo>> generateSchedules(SelectedCourses sc, Constraints constraints) {
		
		// use map to separate 
		Map<String, Set<Combo>> courses = new HashMap<>(); // courses that must be in schedule
		
		if(sc.getCourses() != null) {
			for(Course course : sc.getCourses()) {
				courses.put(course.getCourseID(), course.validCombinations(constraints));
			}
		}
		
		if(sc.getLectures() != null) {
			for(Map.Entry<Lecture, String> entry : sc.getLectures().entrySet()) {
				// extracts course code, prefix and title
				String coursePrefix="", courseCode="", value = entry.getValue();
				String title = value.substring(value.indexOf(':') + 1);
				Lecture lecture = entry.getKey();
				for(int i=0; i<value.length(); ++i) {
					char ch = value.charAt(i);
					if(ch == ':')  break;
					if(ch == ' ') continue;
					if(ch >= '0' && ch <= '9') {
						courseCode += ch;
					}else {
						coursePrefix += ch;
					}
				}
				
				Set<Combo> combos = new HashSet<>();
				if(lecture.hasQuiz()) {
					for(Quiz quiz : lecture.getQuizs(constraints)) {
						combos.add(new Combo(coursePrefix, Integer.valueOf(courseCode), title, lecture, quiz));
					}
				}else {
					combos.add(new Combo(coursePrefix, Integer.valueOf(courseCode), title, lecture, null));
				}
			}
			
		}
		
		if(sc.getCombos() != null) {
			for(Combo combo : sc.getCombos()) {
				courses.put(combo.getComboID(), new HashSet<>());
				courses.get(combo.getComboID()).add(combo);
			}
		}
		
		ArrayList<Set<Combo>> arrli=new ArrayList<>();
		for(Map.Entry<String, Set<Combo>> entry : courses.entrySet()) {
			arrli.add(entry.getValue());
		}
		
		Set<Set<Combo>> possibleSchedule = new HashSet<>();
		
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
