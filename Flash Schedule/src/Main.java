import java.io.*;
import java.util.*;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		// Testing for MeetingTimes class
//		System.out.println(new MeetingTimes("TTh", "630-720"));
//		System.out.println(new MeetingTimes("MWF", "730-820"));
//		System.out.println(new MeetingTimes("MWF", "830-920"));
//		System.out.println(new MeetingTimes("MWF", "930-1020"));
//		System.out.println(new MeetingTimes("MWF", "1030-1120"));
//		System.out.println(new MeetingTimes("MWF", "1130-1220"));
//		System.out.println(new MeetingTimes("MWF", "1230-120"));
//		System.out.println(new MeetingTimes("MWF", "130-220"));
//		System.out.println(new MeetingTimes("MWF", "230-320"));
//		System.out.println(new MeetingTimes("MWF", "330-420"));
//		System.out.println(new MeetingTimes("MWF", "430-520P"));
//		System.out.println(new MeetingTimes("MWF", "530-620P"));
//		System.out.println(new MeetingTimes("MWF", "630-720P"));
//		System.out.println(new MeetingTimes("MWF", "730-820P"));
//		System.out.println(new MeetingTimes("MWF", "830-920P"));
//		System.out.println(new MeetingTimes("MWF", "930-1020P"));
//		
//		System.out.println(new MeetingTimes("MWF", "1130-120"));
//		System.out.println(new MeetingTimes("MWF", "1130-220"));
//		System.out.println(new MeetingTimes("MWF", "1130-320"));
//		System.out.println(new MeetingTimes("MWF", "1130-420"));

		// Testing for reading from CSV
		List<Course> courseList = new ArrayList<>();
		Course currCourse = null;
		Lecture currLecture = null;
		try (Scanner input = new Scanner(new File("test.csv"))) {
			while (input.hasNextLine()) {
				String[] res = input.nextLine().split(",");
				switch (res[0]) {
				case "C":
					currCourse = Course.fromCSV(res);
					courseList.add(currCourse);
					break;
				case "L":
					currLecture = Lecture.fromCSV(res);
					currCourse.addLecture(currLecture);
					break;
				case "Q":
					Quiz tempQuiz = Quiz.fromCSV(res);
					currLecture.addQuiz(tempQuiz);
					break;
				default:
					System.out.println("No match and is an ERROR");
				}
			}
		}
		for (Course c : courseList) {
			System.out.println(c);
			for (Lecture l : c.getLectures()) {
				System.out.println(l);
				for (Quiz q : l.getQuizs()) {
					System.out.println(q);
				}
			}
		}
	}
}
