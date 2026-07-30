/*import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
void main(String[] args) {
    String query="SELECT * from courses where course_id=?;";
    // Call the Connector utility directly using the class name
    try (Connection conn = Connector.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        // Set query parameters safely (prevents SQL Injection)
        stmt.setString(1, "ECSE 343");

        // Execute query and process results
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String id = rs.getString("course_id");
                String title = rs.getString("title");
                System.out.println("Course ID: " + id + ", Title: " + title);
            }
        }

    } catch (SQLException e) {
        System.err.println("Database operation failed!");
        e.printStackTrace();
    }
}*/
void main(String[] args) {
    String courseName= "ENGL 204";
    Collector test1= new Collector(courseName);
    Set<String> allCourses= test1.getAllCourses();
    //System.out.println(allCourses);

    String[] avoided={"MATH 323","MATH 247","ECSE 331","ECSE 210","GEPR 221","COMP 250","ECSE 251","COMP 204","ECSE 543"
            ,"COMP 208","COMP 551","COMP 230"};
    String[] completed={"MATH 140", "MATH 141", "MATH 139","MATH 133", "MATH 150","PHYS 142"};
    //avoided= new String[]{};
    //completed= new String[]{};
    test1.setAvoided(avoided);
    test1.setCompleted(completed);
    /*System.out.println("=====================Printing pre================");
    printPreMapCompact(test1.getPrerequisiteData());
    System.out.println("=====================Printing co================");
    printPreMapCompact(test1.getCorequisiteData());*/
    boolean modified=true;
    Map<String, List<List<String>>> preMap=test1.getPrerequisiteData(modified);
    Map<String, List<List<String>>> coMap=test1.getCorequisiteData(modified);
    String warnings=test1.getWarning();
    System.out.println("=====================Warnings================");
    System.out.println(warnings);
    System.out.println("=====================Printing pre================");
    printPreMapCompact(preMap);
    System.out.println("=====================Printing co================");
    printPreMapCompact(coMap);
    Courses course= new Courses(preMap,coMap);
    System.out.println("=====================Printing order================");
    String expected= """
Completed classes [MATH 139, MATH 141, PHYS 142, MATH 133, MATH 140, MATH 150]
Semester 1[MATH 263, COMP 202, ECSE 200, MATH 262]
Semester 2[COMP 206, ECSE 205, ECSE 222, ECSE 250]
Semester 3[ECSE 551, ECSE 343]
            """;
    System.out.println(course);
    System.out.println(course.toJson());
    System.out.println(expected.equals(course.toString()));
    String[] finalCourses={"MATH 240"};
    Finals finals= new Finals(finalCourses,"W26");
    System.out.println(finals);
}
public void printPreMapCompact(Map<String, List<List<String>>> preMap) {
    if (preMap == null || preMap.isEmpty()) {
        System.out.println("The map is empty.");
        return;
    }

    // Iterate through the map entries and print the key and 2D list together
    for (Map.Entry<String, List<List<String>>> entry : preMap.entrySet()) {
        System.out.println(entry.getKey() + ": " + entry.getValue());
    }
}



