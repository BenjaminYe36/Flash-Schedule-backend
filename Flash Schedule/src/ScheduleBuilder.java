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
	public static Set<Set<Combo>> generateSelected(SelectedCourses sc, Constraints constraints) {
		// TODO: finish implementation
		// This can be done with loops or recursion.
		// With recursion, may be some set places needs to be changed to list
		// ! ! ! Remember to convert everything into Combo for convenience ! ! !
		// Watch out for reference semantics, and make copies when necessary

		// This method can be divided into three parts
		// 1. generate for Courses Set in sc (use validCombinations(constraints) to get
		//   Combos, then check for conflicts, compatible Combos will be stored in a set)
		// 2. for each set of Combos generated from step 1, check if Combos constructed
		//   by (lectures + getQuizs(constraints) + String value from map splitted)
		//   conflicts, compatible Combos will be added to the old Set
		// 3. for each set of Combos generated from step 2, check if Combos from the
		//   Combo Set in sc conflicts,
		//   compatible Combos will be added to the old Set
		// Finally, return the Set of Set of Combos from step 3
		// (PS. I'll give an example: if the Set of Combos generated after step 1 is
		//   Set1 Set2 and Set3, and Set1 of Combos conflicts with every Combo in step 2,
		//   Set1 will not be added to the result in step 2, because when we are
		//   generating for SlectedCourses we want every course in the Course Set, the
		//   Lecture Map and the Combo Set to be present in the final schedule, one of
		//   them missing means this Set of Combo can not be a possible schedule)
		return null; // only for place holder (delete this comment after implementation)
	}
}
