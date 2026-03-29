package enrollment;

import java.util.ArrayList;
import java.util.LinkedList;

// University of Nueva Caceres (UNC)
// Enrollment System - Hash Table keyed by Course Code
//
// Same idea as the Student Hash Table, but flipped:
//   - Key   = course code
//   - Value = list of student IDs enrolled in that course
//
// Each bucket holds a linked list of CourseRecord objects.
// To find a course's bucket: courseCode % TABLE_SIZE
// Two course codes in the same bucket just chain together (separate chaining).
//
// Best operation: looking up who's in a specific course (O(1) avg)
// Slower operation: finding all courses for a student (O(n) - must check all courses)

public class EnrollmentSystemCourseHashTable {

    static final int TABLE_SIZE = 10007; // prime number helps spread entries evenly

    // holds one course's data in the chain
    static class CourseRecord {
        int courseCode;
        ArrayList<Long> students;

        CourseRecord(int courseCode) {
            this.courseCode = courseCode;
            this.students = new ArrayList<>();
        }
    }

    // the hash table: each slot holds a linked list of CourseRecords
    LinkedList[] table;

    public EnrollmentSystemCourseHashTable() {
        table = new LinkedList[TABLE_SIZE];
        for (int i = 0; i < TABLE_SIZE; i++) {
            table[i] = new LinkedList<>();
        }
    }

    // figure out which bucket a course code belongs to
    int getBucket(int courseCode) {
        return courseCode % TABLE_SIZE;
    }

    // find the CourseRecord for a course, or return null if not found
    CourseRecord findRecord(int courseCode) {
        int bucket = getBucket(courseCode);
        for (Object obj : table[bucket]) {
            CourseRecord record = (CourseRecord) obj;
            if (record.courseCode == courseCode) {
                return record;
            }
        }
        return null;
    }

    // find the CourseRecord, or create a new one if the course isn't in the table yet
    CourseRecord findOrCreate(int courseCode) {
        CourseRecord record = findRecord(courseCode);
        if (record == null) {
            record = new CourseRecord(courseCode);
            table[getBucket(courseCode)].add(record);
        }
        return record;
    }

    // Enroll a student in a course
    // Time: O(1) average
    public void addEnrollment(long studentID, int courseCode) {
        CourseRecord record = findOrCreate(courseCode);
        if (!record.students.contains(studentID)) {
            record.students.add(studentID);
        }
    }

    // Remove a student from a course
    // Time: O(1) average
    public void removeEnrollment(long studentID, int courseCode) {
        CourseRecord record = findRecord(courseCode);
        if (record != null) {
            record.students.remove(Long.valueOf(studentID));
        }
    }

    // Check if a student is enrolled in a course
    // Time: O(1) average
    public boolean isEnrolled(long studentID, int courseCode) {
        CourseRecord record = findRecord(courseCode);
        if (record == null) {
            return false;
        }
        return record.students.contains(studentID);
    }

    // Get all students enrolled in a course - fast, direct lookup
    // Time: O(1) average
    public ArrayList<Long> getStudentsInCourse(int courseCode) {
        CourseRecord record = findRecord(courseCode);
        if (record == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(record.students);
    }

    // Get all courses a student is enrolled in - slow, must check every course
    // Time: O(n) - scans every bucket and every record
    public ArrayList<Integer> getCoursesForStudent(long studentID) {
        ArrayList<Integer> courses = new ArrayList<>();
        for (int i = 0; i < TABLE_SIZE; i++) {
            for (Object obj : table[i]) {
                CourseRecord record = (CourseRecord) obj;
                if (record.students.contains(studentID)) {
                    courses.add(record.courseCode);
                }
            }
        }
        return courses;
    }
}
