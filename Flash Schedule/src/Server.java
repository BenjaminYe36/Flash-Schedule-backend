import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;

import java.io.*;
import java.net.*;

import com.sun.net.httpserver.*;

/**
 * The Class that runs the server of the back end to deal with api requests
 * 
 * Citation: this class has much of its code coming from template Server.java in
 * Ed Lessons https://us.edstem.org/courses/631/lessons/1646/slides/32348
 * 
 * @author Guangyin Ye, Naw Naw
 *
 */
public class Server {

	public static final Pattern COURSEID_REGEX = Pattern.compile("^([A-Z&]+ *[A-Z&]+) *([0-9]+) *(.*)$");

	public static void main(String[] args) throws FileNotFoundException, IOException {
		System.out.println("Running server");
		JSONObject success = new JSONObject(), fail = new JSONObject();
		success.put("success", true);
		fail.put("success", false);
		Map<String, Course> courseMap = new HashMap<>();
		Course currCourse = null;
		Lecture currLecture = null;
		try (Scanner input = new Scanner(new File("test.csv"))) {
			while (input.hasNextLine()) {
				String[] res = input.nextLine().split(",");
				switch (res[0]) {
				case "C":
					currCourse = Course.fromCSV(res);
					courseMap.put(currCourse.getCourseID(), currCourse);
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
		// Testing for building schedule
		SelectedCourses sc = new SelectedCourses();
		// Selected courses part
//		sc.addCombo(courseMap.get("CSE351").getCombo("AA"));
		sc.addCourse(courseMap.get("CSE351"));
//		sc.addLecture(courseMap.get("CSE351").getLecture("A"),"CSE351:SW HW..");
//		sc.addCourse(courseMap.get("MATH308"));
		sc.addCombo(courseMap.get("CSE311").getCombo("AA"));

		// Generate schedules
//		Set<Set<Combo>> possibleSchedules = ScheduleBuilder.generateSchedules(sc, new Constraints());

		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 100);
		server.createContext("/schedules", (HttpExchange t) -> {
			send(t, "application/json",
					ScheduleBuilder.toJSONString(ScheduleBuilder.generateSchedules(sc, new Constraints())));
		});

		server.createContext("/addCourse", (HttpExchange t) -> {
			if (!t.getRequestURI().getPath().contains("=")) {
				send(t, "application/json", fail.toJSONString());
			} else {
				String courseString = t.getRequestURI().getPath().split("=", 2)[1];
				System.out.println(courseString);
				Matcher matcher = COURSEID_REGEX.matcher(courseString);
				String courseID = "", sectionID = "";
				if (matcher.find()) {
					courseID = matcher.group(1) + matcher.group(2);
					sectionID = matcher.group(3);
				} else {
					send(t, "application/json", fail.toJSONString());
				}
				if (sectionID.equals("")) {
					if (courseMap.containsKey(courseID)) {
						sc.addCourse(courseMap.get(courseID));
						send(t, "application/json", success.toJSONString());
					} else {
						send(t, "application/json", fail.toJSONString());
					}
				} else if (sectionID.length() > 2) {
					send(t, "application/json", fail.toJSONString());
				} else if (sectionID.length() == 2) {
					if (courseMap.containsKey(courseID)) {
						sc.addCombo(courseMap.get(courseID).getCombo(sectionID));
						send(t, "application/json", success.toJSONString());
					} else {
						send(t, "application/json", fail.toJSONString());
					}
				} else {
					if (courseMap.containsKey(courseID)) {
						sc.addLecture(courseMap.get(courseID).getLecture(sectionID),
								courseMap.get(courseID).getCourseInfo());
						send(t, "application/json", success.toJSONString());
					} else {
						send(t, "application/json", fail.toJSONString());
					}
				}
			}
		});

		server.createContext("/selectedCourses", (HttpExchange t) -> {
			send(t, "application/json", sc.toJSONString());
		});
		
		server.createContext("/removeCourse", (HttpExchange t) -> {
			if (!t.getRequestURI().getPath().contains("=")) {
				send(t, "application/json", fail.toJSONString());
			} else {
				String courseString = t.getRequestURI().getPath().split("=", 2)[1];
				System.out.println(courseString);
				Matcher matcher = COURSEID_REGEX.matcher(courseString);
				String courseID = "", sectionID = "";
				if (matcher.find()) {
					courseID = matcher.group(1) + matcher.group(2);
					sectionID = matcher.group(3);
				} else {
					send(t, "application/json", fail.toJSONString());
				}
				if (sectionID.equals("")) {
					sc.removeCourse(courseMap.get(courseID));
					send(t, "application/json", success.toJSONString());
				} else if (sectionID.length() > 2) {
					send(t, "application/json", fail.toJSONString());
				} else if (sectionID.length() == 2) {
					sc.removeCombo(courseMap.get(courseID).getCombo(sectionID));
					send(t, "application/json", success.toJSONString());
				} else {
					sc.removeLecture(courseMap.get(courseID).getLecture(sectionID));
					send(t, "application/json", success.toJSONString());
				}
			}
		});

		server.setExecutor(null);
		server.start();
	}

	private static void send(HttpExchange t, String contentType, String data)
			throws IOException, UnsupportedEncodingException {
		t.getResponseHeaders().set("Content-Type", contentType);
		byte[] response = data.getBytes("UTF-8");
		t.sendResponseHeaders(200, response.length);
		try (OutputStream os = t.getResponseBody()) {
			os.write(response);
		}
	}

}