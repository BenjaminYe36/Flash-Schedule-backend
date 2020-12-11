import java.util.*;
import java.util.regex.*;
import org.json.simple.*;

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
	 * The Regex pattern for matching Course Prefix + Course Code + Course title for
	 * convenience
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
		Set<Set<Combo>> possibleSchedules = new HashSet<>();
		// use map to separate different courses
		// each course is mapped to a set all valid combinations of lecture + quiz(if it
		// has quiz)
		Map<String, Set<Combo>> courses = new HashMap<>(); // courses that must be in schedule

		for (Course course : sc.getCourses()) {
			Set<Combo> validCombos = course.validCombinations(constraints);
			if (validCombos.isEmpty()) {
				return possibleSchedules;
			}
			courses.put(course.getCourseID(), validCombos);
		}

		for (Map.Entry<Lecture, String> entry : sc.getLectures().entrySet()) {
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
			if (lecture.hasQuiz()) {
				Set<Quiz> filteredQuizs = lecture.getQuizs(constraints);
				if (filteredQuizs.isEmpty()) {
					return possibleSchedules;
				}
				for (Quiz quiz : filteredQuizs) {
					combos.add(new Combo(coursePrefix, Integer.valueOf(courseCode), title, lecture, quiz));
				}
			} else {
				combos.add(new Combo(coursePrefix, Integer.valueOf(courseCode), title, lecture, null));
			}
			courses.put(coursePrefix + courseCode, combos);
		}

		for (Combo combo : sc.getCombos()) {
			if (!constraints.satisfyConstraints(combo)) {
				return possibleSchedules;
			}
			courses.put(combo.getComboID(), new HashSet<>());
			courses.get(combo.getComboID()).add(combo);
		}

		ArrayList<Set<Combo>> arrli = new ArrayList<>(courses.values());

		generateSchedules(arrli, 0, new HashSet<>(), possibleSchedules);
		return possibleSchedules;
	}

	private static void generateSchedules(ArrayList<Set<Combo>> courses, int courseIndex, Set<Combo> sofar,
			Set<Set<Combo>> possibleSchedules) {
		if (courseIndex == courses.size()) {
			possibleSchedules.add(new HashSet<>(sofar));
		} else {
			// System.out.println(courses.get(courseIndex).size());

			for (Combo combo : courses.get(courseIndex)) {
				if (canAdd(sofar, combo)) {
					sofar.add(combo);
					generateSchedules(courses, courseIndex + 1, sofar, possibleSchedules);
					sofar.remove(combo);
				}

			}
		}
	}

	/**
	 * @param possibleSchedules see generateSchedules
	 * @return a json string containing all the possible format which is convenient
	 *         for the front end to process
	 */
	@SuppressWarnings("unchecked")
	public static String toJSONString(Set<Set<Combo>> possibleSchedules) {
		JSONObject outterObj = new JSONObject();
		JSONArray schedules = new JSONArray();
		int i = 1;
		for (Set<Combo> oneSchedule : possibleSchedules) {
			JSONObject innerObj = new JSONObject();
			JSONArray classes = new JSONArray();
			JSONArray time = new JSONArray();
			innerObj.put("id", i);
			for (Combo combo : oneSchedule) {
				JSONObject timeObjLecture = new JSONObject();
				if (combo.getLecture().getMeetingTimes() != null) {
					classes.add(combo.getLectureString());
					MeetingTimes mtLecture = combo.getLecture().getMeetingTimes();
					timeObjLecture.put("days", mtLecture.getDays());
					timeObjLecture.put("lectureFrom", mtLecture.getStartTime() + ":00");
					timeObjLecture.put("lectureTo", mtLecture.getEndTime() + ":00");
					timeObjLecture.put("together", String.format("%s %s-%s", mtLecture.getDays(),
							mtLecture.getStartTime(), mtLecture.getEndTime()));
					time.add(timeObjLecture);
				}
				if (combo.getQuiz() != null) {
					JSONObject timeObjQuiz = new JSONObject();
					if (combo.getQuiz().getMeetingTimes() != null) {
						classes.add(combo.getQuizString());
						MeetingTimes mtQuiz = combo.getQuiz().getMeetingTimes();
						timeObjQuiz.put("days", mtQuiz.getDays());
						timeObjQuiz.put("lectureFrom", mtQuiz.getStartTime() + ":00");
						timeObjQuiz.put("lectureTo", mtQuiz.getEndTime() + ":00");
						timeObjQuiz.put("together", String.format("%s %s-%s", mtQuiz.getDays(), mtQuiz.getStartTime(),
								mtQuiz.getEndTime()));
						time.add(timeObjQuiz);
					}

				}
			}
			innerObj.put("classes", classes);
			innerObj.put("time", time);
			innerObj.put("description", "Schedule " + i);
			innerObj.put("show", false);
			innerObj.put("pinned", false);
			schedules.add(innerObj);
			// limit output json to 6 schedules
			if (i == 6) {
				break;
			}
			i++;
		}
		outterObj.put("schedules", schedules);
		return outterObj.toJSONString();
	}

	/**
	 * @return true if combo have concurrent meeting times with any combo in set
	 *         combos
	 */
	private static boolean canAdd(Set<Combo> combos, Combo combo) {
		for (Combo e : combos) {
			if (e.conflict(combo))
				return false;
		}
		return true;
	}
}
