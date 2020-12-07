import java.util.*;
import java.io.*;
import java.net.*;

import com.sun.net.httpserver.*;

/**
 * The Class that runs the server of the back end to deal with api requests
 * 
 * Citation: this class has much of its code coming from template Server.java in Ed Lessons
 * https://us.edstem.org/courses/631/lessons/1646/slides/32348
 * @author Guangyin Ye, Naw Naw
 *
 */
public class Server {

    public static void main(String[] args) throws FileNotFoundException, IOException {
    	System.out.println("Running server");
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
		Set<Set<Combo>> possibleSchedules = ScheduleBuilder.generateSchedules(sc, new Constraints());
		
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 100);
        server.createContext("/schedules", (HttpExchange t) -> {
            send(t, "application/json", ScheduleBuilder.toJSONString(possibleSchedules));
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