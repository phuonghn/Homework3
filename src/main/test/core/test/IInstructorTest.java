package core.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import core.api.IAdmin;
import core.api.IInstructor;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Instructor;
import core.api.impl.Student;
public class IInstructorTest{
	   private IAdmin admin;
	   private IStudent student;
	   private IInstructor instructor;
	    
	   @Before
	    public void setup() {
	        this.admin = new Admin();
	        this.instructor = new Instructor();
	        this.student = new Student();
	    }
	   //-------------------------Only assigned instructor should be able to assign the homework-----
	   @Test//Bug when fake instructor adds hw
	   public void addHWTest() {
		   this.admin.createClass("class", 2017, "Instructor", 15);
		   this.instructor.addHomework("fake", "class" , 2017, "hw");
		   assertFalse(this.instructor.homeworkExists("class", 2017, "hw"));
		   
	   }
	   
	   @Test
	   public void addHWTestTrue() {
		   this.admin.createClass("class", 2017, "Instructor", 15);
		   this.instructor.addHomework("Instructor", "class" , 2017, "hw");
		   assertTrue(this.instructor.homeworkExists("class", 2017, "hw"));
		   
	   }//real instructor adds hw
	   
	   //-------------------------Only assigned instructor should be able to assign grade -----
	   
	   @Test
	   public void assignGradeTest() {
		   this.admin.createClass("class", 2017, "Instructor", 15);
		   this.student.registerForClass("student", "class", 2017);
		   this.instructor.addHomework("Instructor", "class" , 2017, "hw");
		   this.student.submitHomework("student", "hw", "answer", "class", 2017);
		   this.instructor.assignGrade("Instructor", "class", 2017, "hw", "student", 100);
		   assertTrue(this.instructor.getGrade("class", 2017, "hw", "student") == 100);
	   }
	    
	   @Test //Bugg fake instructor assign grade 
	   public void assignGradeTestFakeInstuctor() {
		   this.admin.createClass("class", 2017, "Instructor", 15);
		   this.student.registerForClass("student", "class", 2017);
		   this.instructor.addHomework("Instructor", "class" , 2017, "hw");
		   this.student.submitHomework("student", "hw", "answer", "class", 2017);
		   this.instructor.assignGrade("Fake", "class", 2017, "hw", "student", 100);
		   assertFalse(this.instructor.getGrade("class", 2017, "hw", "student") == 100);
	   }
	   
	 //-------------------------Check if hw can be assigned to fake class -----
	   @Test 
	   public void addHWtoFakeClass() {
		   this.admin.createClass("class", 2017, "Instructor", 15);
		   this.instructor.addHomework("Instructor", "class2" , 2017, "hw");
		   assertFalse(this.instructor.homeworkExists("class", 2017, "hw"));
	   }//can't assign hw to fake class
	   
	   //----------------add hw with empty hw name---------------
	   
	   @Test //BUG
	   public void addHWemptyHwName() {
		   this.admin.createClass("class", 2017, "Instructor", 15);
		   this.instructor.addHomework("Instructor", "class" , 2017, "");
		   assertFalse(this.instructor.homeworkExists("class", 2017, ""));
	   }
	   //----------------add hw with empty instructor name---------------
	   @Test //BUG
	   public void addHWemptyInstructorName() {
		   this.admin.createClass("class", 2017, "A", 15);
		   this.instructor.addHomework("", "class" , 2017, "HW");
		   assertFalse(this.instructor.homeworkExists("class", 2017, "HW"));
	   }
	    
	   //------------grade can be negative------------
	    
	   @Test //Bug
	   public void assignGradeNegative() {
		   this.admin.createClass("class", 2017, "Instructor", 15);
		   this.student.registerForClass("student", "class", 2017);
		   this.instructor.addHomework("Instructor", "class" , 2017, "hw");
		   this.student.submitHomework("student", "hw", "answer", "class", 2017);
		   this.instructor.assignGrade("Instructor", "class", 2017, "hw", "student", -1);
		   assertFalse(this.instructor.getGrade("class", 2017, "hw", "student") == -1);
	   }
	   
	   //-----An instructor can assign a grade even though the student hasn't submitted anything-----
	   @Test //Bug
	   public void assignGradeforHWnotSubmitted() {
		   this.admin.createClass("class", 2017, "Instructor", 15);
		   this.student.registerForClass("student", "class", 2017);
		   this.instructor.addHomework("Instructor", "class" , 2017, "hw");
		   this.instructor.assignGrade("Instructor", "class", 2017, "hw", "student", 10);
		   assertFalse(this.instructor.getGrade("class", 2017, "hw", "student") == 10);
	   }
	   
	   @Test 
	   public void assignGradeCorrect() {
		   this.admin.createClass("class", 2017, "Instructor", 15);
		   this.student.registerForClass("student", "class", 2017);
		   this.instructor.addHomework("Instructor", "class" , 2017, "hw");
		   this.student.submitHomework("student", "hw", "answer", "class", 2017);
		   this.instructor.assignGrade("Instructor", "class", 2017, "hw", "student", 10);
		   assertTrue(this.instructor.getGrade("class", 2017, "hw", "student") == 10);
	   }
	   
	   
}
