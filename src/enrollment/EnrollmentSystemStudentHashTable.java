package enrollment;

import java.util.ArrayList;
import java.util.LinkedList;

// University of Nueva Caceres (UNC)
// Enrollment System - Hash Table keyed by Student ID
//
// How it works:
//   - We have an array of "buckets" (TABLE_SIZE buckets total).
//   - Each bucket is a LinkedList of StudentRecord objects.
//   - To find which bucket a student belongs to: studentID % TABLE_SIZE
//   - If two students land in the same bucket, they just both sit in
//     that bucket's linked list. This is called separate chaining.
//
// Each StudentRecord holds:
//   - one student ID
//   - that student's list of enrolled course codes
//
// Best operations: looking up/modifying a specific student's courses (O(1) avg)
// Slower operation: finding all students in a course (O(n) - must check everyone)

public class EnrollmentSystemStudentHashTable {

    static final int TABLE_SIZE = 10007; // prime number helps spread entries evenly

    // holds one student's data in the chain
    static class StudentRecord {
        long studentID;
        ArrayList<Integer> courses;

        StudentRecord(long studentID) {
            this.studentID = studentID;
            this.courses = new ArrayList<>();
        }
    }

    // the hash table: each slot holds a linked list of StudentRecords
    LinkedList[] table;

    public EnrollmentSystemStudentHashTable() {
        table = new LinkedList[TABLE_SIZE];
        for (int i = 0; i < TABLE_SIZE; i++) {
            table[i] = new LinkedList<>();
        }
    }

    // figure out which bucket a student ID belongs to
    int getBucket(long studentID) {
        return (int)(studentID % TABLE_SIZE);
    }

    // find the StudentRecord for a student, or return null if not found
    StudentRecord findRecord(long studentID) {
        int bucket = getBucket(studentID);
        for (Object obj : table[bucket]) {
            StudentRecord record = (StudentRecord) obj;
            if (record.studentID == studentID) {
                return record;
            }
        }
        return null;
    }

    // find the StudentRecord, or create a new one if the student isn't in the table yet
    StudentRecord findOrCreate(long studentID) {
        StudentRecord record = findRecord(studentID);
        if (record == null) {
            record = new StudentRecord(studentID);
            table[getBucket(studentID)].add(record);
        }
        return record;
    }

    // Enroll a student in a course
    // Time: O(1) average
    public void addEnrollment(long studentID, int courseCode) {
        StudentRecord record = findOrCreate(studentID);
        if (!record.courses.contains(courseCode)) {
            record.courses.add(courseCode);
        }
    }

    // Remove a student from a course
    // Time: O(1) average
    public void removeEnrollment(long studentID, int courseCode) {
        StudentRecord record = findRecord(studentID);
        if (record != null) {
            record.courses.remove(Integer.valueOf(courseCode));
        }
    }

    // Check if a student is enrolled in a course
    // Time: O(1) average
    public boolean isEnrolled(long studentID, int courseCode) {
        StudentRecord record = findRecord(studentID);
        if (record == null) {
            return false;
        }
        return record.courses.contains(courseCode);
    }

    // Get all courses a student is enrolled in - fast, direct lookup
    // Time: O(1) average
    public ArrayList<Integer> getCoursesForStudent(long studentID) {
        StudentRecord record = findRecord(studentID);
        if (record == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(record.courses);
    }

    // Get all students enrolled in a course - slow, must check every student
    // Time: O(n) - scans every bucket and every record
    public ArrayList<Long> getStudentsInCourse(int courseCode) {
        ArrayList<Long> students = new ArrayList<>();
        for (int i = 0; i < TABLE_SIZE; i++) {
            for (Object obj : table[i]) {
                StudentRecord record = (StudentRecord) obj;
                if (record.courses.contains(courseCode)) {
                    students.add(record.studentID);
                }
            }
        }
        return students;
    }
}
