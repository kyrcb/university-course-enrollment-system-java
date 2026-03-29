package enrollment;

import java.util.ArrayList;

// University of Nueva Caceres (UNC)
// Enrollment System using a Direct Address Table (DAT)
//
// We use a 2D boolean array called "table".
//   - Rows   = student IDs  (1,000,000 to 9,999,999)
//   - Columns = course codes (1,000 to 9,999)
//
// To find a cell, we subtract the minimum value so the index starts at 0.
//   Example: student 1000005 -> row index 5
//            course   1003   -> column index 3
//
// All lookups are instant (O(1)), but the array is enormous:
// 9,000,000 rows x 9,000 columns = ~81 billion booleans.
// That's the big downside of direct addressing for large ID ranges.

public class EnrollmentSystemDAT {

    static final int STUDENT_MIN   = 1000000;
    static final int STUDENT_MAX   = 9999999;
    static final int COURSE_MIN    = 1000;
    static final int COURSE_MAX    = 9999;

    // how many rows and columns the array needs
    static final int STUDENT_RANGE = STUDENT_MAX - STUDENT_MIN + 1; // 9,000,000
    static final int COURSE_RANGE  = COURSE_MAX  - COURSE_MIN  + 1; // 9,000

    boolean[][] table;

    public EnrollmentSystemDAT() {
        // This line creates the full direct-address table.
        // In a real run this would require ~81 GB of memory, which shows
        // exactly why DATs are impractical for large, spread-out ID ranges.
        table = new boolean[STUDENT_RANGE][COURSE_RANGE];
    }

    // Mark a student as enrolled in a course
    // Time: O(1) - just one array write
    public void addEnrollment(long studentID, int courseCode) {
        table[(int)(studentID - STUDENT_MIN)][courseCode - COURSE_MIN] = true;
    }

    // Mark a student as no longer enrolled in a course
    // Time: O(1)
    public void removeEnrollment(long studentID, int courseCode) {
        table[(int)(studentID - STUDENT_MIN)][courseCode - COURSE_MIN] = false;
    }

    // Check if a student is enrolled in a course
    // Time: O(1)
    public boolean isEnrolled(long studentID, int courseCode) {
        return table[(int)(studentID - STUDENT_MIN)][courseCode - COURSE_MIN];
    }

    // Get every student enrolled in a given course
    // Has to scan the entire column (all 9 million rows) - slow! O(STUDENT_RANGE)
    public ArrayList<Long> getStudentsInCourse(int courseCode) {
        ArrayList<Long> students = new ArrayList<>();
        int col = courseCode - COURSE_MIN;
        for (int row = 0; row < STUDENT_RANGE; row++) {
            if (table[row][col]) {
                students.add((long)(row + STUDENT_MIN));
            }
        }
        return students;
    }

    // Get every course a given student is enrolled in
    // Has to scan the entire row (all 9,000 columns) - O(COURSE_RANGE)
    public ArrayList<Integer> getCoursesForStudent(long studentID) {
        ArrayList<Integer> courses = new ArrayList<>();
        int row = (int)(studentID - STUDENT_MIN);
        for (int col = 0; col < COURSE_RANGE; col++) {
            if (table[row][col]) {
                courses.add(col + COURSE_MIN);
            }
        }
        return courses;
    }
}
