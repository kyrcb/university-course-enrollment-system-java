package enrollment;

import java.util.ArrayList;

/**
 * University of Nueva Caceres (UNC)
 * Course Enrollment System — Demo / Driver
 *
 * Exercises all five operations on each of the three implementations:
 *   1. EnrollmentSystemDAT              (Direct Address Table)
 *   2. EnrollmentSystemStudentHashTable (Hash Table keyed by student)
 *   3. EnrollmentSystemCourseHashTable  (Hash Table keyed by course)
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("==============================================");
        System.out.println("  University of Nueva Caceres (UNC)");
        System.out.println("  Course Enrollment System — Demo");
        System.out.println("==============================================\n");

        // --- shared test data ---
        long   s1 = 1000001L, s2 = 1000002L, s3 = 1000003L;
        int    c1 = 1001,     c2 = 1002,     c3 = 1003;

        // ============================================================
        // 1. Direct Address Table
        // ============================================================
        System.out.println("--- 1. Direct Address Table (DAT) ---\n");

        EnrollmentSystemDAT dat = new EnrollmentSystemDAT();

        // addEnrollment
        dat.addEnrollment(s1, c1);
        dat.addEnrollment(s1, c2);
        dat.addEnrollment(s2, c1);
        dat.addEnrollment(s3, c3);
        System.out.println("Enrolled s1->c1, s1->c2, s2->c1, s3->c3");

        // isEnrolled
        System.out.println("isEnrolled(s1, c1) = " + dat.isEnrolled(s1, c1));   // true
        System.out.println("isEnrolled(s1, c3) = " + dat.isEnrolled(s1, c3));   // false
        System.out.println("isEnrolled(s2, c2) = " + dat.isEnrolled(s2, c2));   // false

        // getCoursesForStudent
        ArrayList<Integer> datCourses = dat.getCoursesForStudent(s1);
        System.out.println("Courses for s1: " + datCourses);                    // [1001, 1002]

        // getStudentsInCourse
        ArrayList<Long> datStudents = dat.getStudentsInCourse(c1);
        System.out.println("Students in c1: " + datStudents);                   // [s1, s2]

        // removeEnrollment
        dat.removeEnrollment(s1, c1);
        System.out.println("After removing s1 from c1:");
        System.out.println("  isEnrolled(s1, c1) = " + dat.isEnrolled(s1, c1)); // false
        System.out.println("  Courses for s1: " + dat.getCoursesForStudent(s1)); // [1002]
        System.out.println("  Students in c1: " + dat.getStudentsInCourse(c1));  // [s2]

        System.out.println();

        // ============================================================
        // 2. Hash Table keyed by Student
        // ============================================================
        System.out.println("--- 2. Student Hash Table (separate chaining) ---\n");

        EnrollmentSystemStudentHashTable sht = new EnrollmentSystemStudentHashTable();

        sht.addEnrollment(s1, c1);
        sht.addEnrollment(s1, c2);
        sht.addEnrollment(s2, c1);
        sht.addEnrollment(s3, c3);
        System.out.println("Enrolled s1->c1, s1->c2, s2->c1, s3->c3");

        System.out.println("isEnrolled(s1, c1) = " + sht.isEnrolled(s1, c1));   // true
        System.out.println("isEnrolled(s1, c3) = " + sht.isEnrolled(s1, c3));   // false
        System.out.println("isEnrolled(s2, c2) = " + sht.isEnrolled(s2, c2));   // false

        ArrayList<Integer> shtCourses = sht.getCoursesForStudent(s1);
        System.out.println("Courses for s1: " + shtCourses);                    // [1001, 1002]

        ArrayList<Long> shtStudents = sht.getStudentsInCourse(c1);
        System.out.println("Students in c1: " + shtStudents);                   // [s1, s2]

        sht.removeEnrollment(s1, c1);
        System.out.println("After removing s1 from c1:");
        System.out.println("  isEnrolled(s1, c1) = " + sht.isEnrolled(s1, c1)); // false
        System.out.println("  Courses for s1: " + sht.getCoursesForStudent(s1)); // [1002]
        System.out.println("  Students in c1: " + sht.getStudentsInCourse(c1));  // [s2]

        System.out.println();

        // ============================================================
        // 3. Hash Table keyed by Course
        // ============================================================
        System.out.println("--- 3. Course Hash Table (separate chaining) ---\n");

        EnrollmentSystemCourseHashTable cht = new EnrollmentSystemCourseHashTable();

        cht.addEnrollment(s1, c1);
        cht.addEnrollment(s1, c2);
        cht.addEnrollment(s2, c1);
        cht.addEnrollment(s3, c3);
        System.out.println("Enrolled s1->c1, s1->c2, s2->c1, s3->c3");

        System.out.println("isEnrolled(s1, c1) = " + cht.isEnrolled(s1, c1));   // true
        System.out.println("isEnrolled(s1, c3) = " + cht.isEnrolled(s1, c3));   // false
        System.out.println("isEnrolled(s2, c2) = " + cht.isEnrolled(s2, c2));   // false

        ArrayList<Long> chtStudents = cht.getStudentsInCourse(c1);
        System.out.println("Students in c1: " + chtStudents);                   // [s1, s2]

        ArrayList<Integer> chtCourses = cht.getCoursesForStudent(s1);
        System.out.println("Courses for s1: " + chtCourses);                    // [1001, 1002]

        cht.removeEnrollment(s1, c1);
        System.out.println("After removing s1 from c1:");
        System.out.println("  isEnrolled(s1, c1) = " + cht.isEnrolled(s1, c1)); // false
        System.out.println("  Students in c1: " + cht.getStudentsInCourse(c1));  // [s2]
        System.out.println("  Courses for s1: " + cht.getCoursesForStudent(s1)); // [1002]

        System.out.println();
        System.out.println("==============================================");
        System.out.println("  Demo complete.");
        System.out.println("==============================================");
    }
}
