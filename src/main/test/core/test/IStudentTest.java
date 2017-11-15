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
public class IStudentTest{
	   private IAdmin admin;
	   private IStudent student;
	   private IInstructor instructor;
	    
	   @Before
	    public void setup() {
	        this.admin = new Admin();
	        this.instructor = new Instructor();
	        this.student = new Student();
	    }
	   
	   @Test
	   public void registerCorrect() {
		   this.admin.createClass("class", 2017, "Instructor", 10);
		   this.student.registerForClass("student", "class", 2017);
		   assertTrue(this.student.isRegisteredFor("student", "class", 2017));
		  
	   }
	   
	   
	   //------student registered even though className/year pair is full--------
	   @Test//Bug
	   public void registerFullClass() {
		   this.admin.createClass("class", 2017, "Instructor", 1);
		   this.student.registerForClass("student", "class", 2017);
		   this.student.registerForClass("Student2", "class", 2017);
		   assertFalse(this.student.isRegisteredFor("Student2", "class", 2017));
		  
	   }
	   //-----------student register for class that doesn't exist-------
	   @Test
	   public void registerNonexistentClass() {
		   
		   this.student.registerForClass("student", "DNE", 2017);
		   assertFalse(this.student.isRegisteredFor("student", "DNE", 2017));
		  
	   }
	   
	   //--------student register for class with empty string for student name------
	   
	   @Test//Bug
	   public void registerEmptyStudent() {
		   this.admin.createClass("class", 2017, "Instructor", 1);
		   this.student.registerForClass("", "class", 2017);
		   assertFalse(this.student.isRegisteredFor("", "class", 2017));
		  
	   }
	   
	   //---------register empty class name-------------------
	   @Test//Bug
	   public void registerEmptyClassName() {
		   this.admin.createClass("class", 2017, "Instructor", 1);
		   this.student.registerForClass("Student", "", 2017);
		   assertFalse(this.student.isRegisteredFor("Student", "", 2017));
		  
	   }
	 //-----------------register past year class------------------
	   @Test//Bug
	   public void registerPastYear() {
		   this.admin.createClass("class", 2017, "Instructor", 1);
		   this.student.registerForClass("Student", "class", 2016);
		   assertFalse(this.student.isRegisteredFor("Student", "class", 2016));
		  
	   }
	 //-----------------register future year class------------------
	   @Test//Bug
	   public void registerFutureYear() {
		   this.admin.createClass("class", 2017, "Instructor", 1);
		   this.student.registerForClass("Student", "class", 2018);
		   assertFalse(this.student.isRegisteredFor("Student", "class", 2018));
		  
	   }
	   //-------student can submit homework even though they are not registered for the class------
	   @Test//bug
	   public void submitHWnotReg() {
		   this.admin.createClass("class", 2017, "Instructor", 1);
		   this.instructor.addHomework("Instructor", "class" , 2017, "hw");
		   this.student.submitHomework("student", "hw", "ans", "class", 2017);
		   assertFalse(this.student.hasSubmitted("student", "hw", "class", 2017));
		   
	   }
	   
	   //---------submit true----------------------------------------------------
	   @Test
	   public void submitHWReg() {
		   this.admin.createClass("class", 2017, "Instructor", 1);
		   this.instructor.addHomework("Instructor", "class" , 2017, "hw");
		   this.student.registerForClass("student", "class", 2017);
		   this.student.submitHomework("student", "hw", "ans", "class", 2017);
		   assertTrue(this.student.hasSubmitted("student", "hw", "class", 2017));
		   
	   }
	   
	   //-------student can submit homework for a class that isn't for this year----
	   @Test
	   public void submitHWwrongYear() {
		   this.admin.createClass("class", 2017, "Instructor", 1);
		   this.instructor.addHomework("Instructor", "class" , 2017, "hw");
		   this.student.registerForClass("student", "class", 2017);
		   this.student.submitHomework("student", "hw", "ans", "class", 2016);
		   assertFalse(this.student.hasSubmitted("student", "hw", "class", 2017));
		   
	   }
	   
	   //--------Drop class future year--------
	   
	   @Test
	   public void dropClassFutureYear() {
		   this.admin.createClass("class", 2017, "Instructor", 1);
		   this.student.registerForClass("student", "class", 2017);
		   this.student.dropClass("student","class",  2018);
		   assertTrue(this.student.isRegisteredFor("student", "class", 2017));
		   	
		   
	   }
	   
	   //--------Drop class past year--------
	   @Test
	   public void dropClassPastYear() {
		   this.admin.createClass("class", 2017, "Instructor", 1);
		   this.student.registerForClass("student", "class", 2017);
		   this.student.dropClass("student","class",  2016);
		   assertTrue(this.student.isRegisteredFor("student", "class", 2017));
		   
	   }
	 //--------Drop class current year (correct--------
	   @Test
	   public void dropClassCurrentYear() {
		   this.admin.createClass("class", 2017, "Instructor", 1);
		   this.student.registerForClass("student", "class", 2017);
		   this.student.dropClass("student","class",  2017);
		   assertFalse(this.student.isRegisteredFor("student", "class", 2017));
		   
	   }
	   
	   //------------drop class empty classname-------------
	   
	   @Test
	   public void dropClassEmptyclassName() {
		   this.admin.createClass("class", 2017, "Instructor", 1);
		   this.student.registerForClass("student", "class", 2017);
		   this.student.dropClass("student","",  2017);
		   assertTrue(this.student.isRegisteredFor("student", "class", 2017));
		   
	   }
	   //----------drop class empty student's name-----------------
	   @Test
	   public void dropClassEmptyStudentName() {
		   this.admin.createClass("class", 2017, "Instructor", 1);
		   this.student.registerForClass("student", "class", 2017);
		   this.student.dropClass("","class",  2017);
		   assertTrue(this.student.isRegisteredFor("student", "class", 2017));
		   
	   }
	   
}