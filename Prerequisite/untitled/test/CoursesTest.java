import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.*;

class CoursesTest {

    @Test
    void testEngineeringGauntletSchedule() {
        // Arrange - New format: Map<String, List<List<String>>>
        Map<String, List<List<String>>> preReqs = new HashMap<>();

        preReqs.put("PHYSICS 1", List.of());
        // PHYSICS 2 requires (PHYSICS 1)
        preReqs.put("PHYSICS 2", List.of(List.of("PHYSICS 1")));
        preReqs.put("THERMODYNAMICS", List.of(List.of("PHYSICS 2")));
        preReqs.put("HEAT TRANSFER", List.of(List.of("THERMODYNAMICS")));

        preReqs.put("CALC 1", List.of());
        preReqs.put("CALC 2", List.of(List.of("CALC 1")));
        preReqs.put("MAJOR DESIGN 1", List.of(List.of("CALC 2")));
        preReqs.put("MAJOR DESIGN 2", List.of(List.of("CALC 2")));

        // SENIOR PROJECT requires (MAJOR DESIGN 1 OR MAJOR DESIGN 2 OR HEAT TRANSFER)
        // Adjust the inner/outer lists if you meant AND
        preReqs.put("SENIOR PROJECT", List.of(List.of("MAJOR DESIGN 1", "MAJOR DESIGN 2", "HEAT TRANSFER")));

        Courses system = new Courses(preReqs, new HashMap<>());

        // Act
        String output = system.toString();
        String[] actualLines = output.split("\\R");

        // Assert
        //assertTrue(actualLines.length >= 5, "Schedule too short!");
        // Pass actualLines (the array), not actualLines[0] (the string)
        assertSemesterContains(actualLines, 1, "PHYSICS 1", "CALC 1");
        assertSemesterContains(actualLines, 2, "PHYSICS 2", "CALC 2");

    }

    @Test
    void testUltimateEngineeringGauntlet() {
        // Arrange
        Map<String, List<List<String>>> preReqs = new HashMap<>();

        // Track 1: Chain
        preReqs.put("PHYSICS 1", List.of());
        preReqs.put("PHYSICS 2", List.of(List.of("PHYSICS 1")));
        preReqs.put("THERMODYNAMICS", List.of(List.of("PHYSICS 2")));
        preReqs.put("HEAT TRANSFER", List.of(List.of("THERMODYNAMICS")));

        // Track 2: Math
        preReqs.put("CALC 1", List.of());
        preReqs.put("CALC 2", List.of(List.of("CALC 1")));
        preReqs.put("LINEAR ALGEBRA", List.of(List.of("CALC 2")));
        preReqs.put("MAJOR DESIGN 1", List.of(List.of("LINEAR ALGEBRA")));
        preReqs.put("MAJOR DESIGN 2", List.of(List.of("LINEAR ALGEBRA")));

        // Track 3: Chem
        preReqs.put("CHEM 1", List.of());
        preReqs.put("CHEM 2", List.of(List.of("CHEM 1")));

        // Final Boss: Needs ALL of these fulfilled (AND logic)
        // Each requirement gets its own sub-list
        preReqs.put("SENIOR PROJECT", List.of(
                List.of("MAJOR DESIGN 1"),
                List.of("MAJOR DESIGN 2"),
                List.of("HEAT TRANSFER"),
                List.of("CHEM 2")
        ));

        // Act
        Courses system = new Courses(preReqs, new HashMap<>());
        String output = system.toString();
        String[] actualLines = output.split("\\R");

        // Assert
        assertSemesterContains(actualLines, 1, "PHYSICS 1", "CALC 1", "CHEM 1");
        assertSemesterContains(actualLines, 2, "PHYSICS 2", "CALC 2", "CHEM 2");
        assertSemesterContains(actualLines, 3, "LINEAR ALGEBRA", "THERMODYNAMICS");
        assertSemesterContains(actualLines, 5, "SENIOR PROJECT");
    }
    @Test
    void testCombinedAndOrWithCorequisites() {
        // 1. Arrange Prerequisites (AND/OR Logic)
        Map<String, List<List<String>>> preReqs = new HashMap<>();
        preReqs.put("BIO 101", List.of());
        preReqs.put("CHEM 101", List.of());

        // SCIENCE LAB requires (BIO 101 OR CHEM 101)
        preReqs.put("SCIENCE LAB", List.of(
                List.of("BIO 101", "CHEM 101")
        ));

        // ADVANCED RESEARCH requires (SCIENCE LAB AND BIO 101)
        // Note: Even if you took CHEM 101 for the lab, you STILL need BIO 101 for this.
        preReqs.put("ADVANCED RESEARCH", List.of(
                List.of("SCIENCE LAB"),
                List.of("BIO 101")
        ));

        // 2. Arrange Corequisites
        Map<String, List<List<String>>> coReqs = new HashMap<>();
        coReqs.put("LAB SAFETY", List.of());
        // SCIENCE LAB requires LAB SAFETY as a corequisite
        coReqs.put("SCIENCE LAB", List.of(
                List.of("LAB SAFETY")
        ));

        // 3. Act
        Courses system = new Courses(preReqs, coReqs);
        String output = system.toString();
        String[] actualLines = output.split("\\R");

        // 4. Assert
        // Semester 1 should have the starters and the independent coreq
        assertSemesterContains(actualLines, 1, "BIO 101", "CHEM 101", "LAB SAFETY");

        // Semester 2 should have SCIENCE LAB (since BIO or CHEM is done, and SAFETY is done)
        assertSemesterContains(actualLines, 2, "SCIENCE LAB");

        // Semester 3 should have ADVANCED RESEARCH
        assertSemesterContains(actualLines, 3, "ADVANCED RESEARCH");
    }

    // Update this method in your test class
    private void assertSemesterContains(String[] lines, int semesterNum, String... courses) {
        // semesterNum - 1 because arrays are 0-indexed (Semester 1 is index 0)
        String line = lines[semesterNum - 1];
        assertTrue(line.contains("Semester " + semesterNum), "Line missing Semester " + semesterNum);
        for (String course : courses) {
            assertTrue(line.contains(course), "Semester " + semesterNum + " missing " + course);
        }
    }

    private void assertSemesterContains(String line, int semesterNum, String... courses) {
        assertTrue(line.contains("Semester " + semesterNum), "Line missing Semester " + semesterNum);
        for (String course : courses) {
            assertTrue(line.contains(course), "Semester " + semesterNum + " missing " + course);
        }
    }
}

