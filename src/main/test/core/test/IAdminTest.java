package core.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import core.api.IAdmin;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Student;
public class IAdminTest{
	   private IAdmin admin;
	   private IStudent student;
	  // private IInstructor instructor;
	    @Before
	    public void setup() {
	        this.admin = new Admin();
	        //this.instructor = new Instructor();
	        this.student = new Student();
	    }
	    //---------------Calendar yr cannot be in the past----------------
	    @Test
	    public void testMakeClass() {
	        this.admin.createClass("Test", 2017, "Instructor", 15);
	        assertTrue(this.admin.classExists("Test", 2017));
	    }//calendar year is in presence YES

	    @Test //BUG 
	    public void testMakeClass2() {
	        this.admin.createClass("Test", 2016, "Instructor", 15);
	        assertFalse(this.admin.classExists("Test", 2016));
	    }//calendar year is in past NO
	    
	    //-----------------------Capacity has to be  > 0----------------
	    
	    @Test 
	    public void CreateClassCapacityPositiveTest(){
	    	this.admin.createClass("Test", 2017, "Instructor", 1);//create a class with + capacity
	    	assertTrue(this.admin.classExists("Test", 2017));
	    }//check when capacity is positive
	    	
	    @Test //BUG
	    public void CreateClassCapacityZeroTest(){
	    	this.admin.createClass("Test", 2017, "Instructor", 0);//create a class with 0 capacity
	    		assertFalse(this.admin.classExists("Test", 2017));
	    	}//check when capacity is 0
	    	
	    @Test //BUG
	    public void CreateClassCapacityNegativeTest(){
	    	this.admin.createClass("Test", 2017, "Instructor", -1);//create a class with - capacity
	    		assertFalse(this.admin.classExists("Test", 2017));
	    	}//check when capacity is negative
	    
	    //--------------------- Instructor can't be assigned to 2 courses----------
	    @Test //BUG
	    public void CreateClassoneClassPerInstructor() {
	    		this.admin.createClass("class1", 2017, "A", 1);
	    		this.admin.createClass("class2", 2017, "A", 1);
	 		
	    		assertFalse(this.admin.getClassInstructor("class2", 2017) == "A");
	    	
	    }//if the instructor is the same for two class the second class shouldn't exists'
	    
	    @Test 
	    public void CreateClassoneClassOneInstructor() {
	    		this.admin.createClass("class1", 2017, "A", 1);		
	    		assertTrue(this.admin.getClassInstructor("class1", 2017) == "A");
	    	
	    }
	    
	    
	    //------------------------New capacity of this class, must be at least equal to the number of students enrolled---------
	    @Test  //BUG
	    public void changeCapacityTest() {
		    	this.admin.createClass("class1", 2017, "A", 2);
		    	this.student.registerForClass("student1" , "class1" , 2017);
		    	this.student.registerForClass("student2" , "class1" , 2017);
		    	this.admin.changeCapacity("class1", 2017, 1);
		    	assertFalse(this.admin.getClassCapacity("class1", 2017) == 1 );	
		    }
	    
	    @Test  
	    public void changeCapacityTestTrue() {
		    	this.admin.createClass("class1", 2017, "A", 2);
		    	this.student.registerForClass("student1" , "class1" , 2017);
		    	this.student.registerForClass("student2" , "class1" , 2017);
		    	this.admin.changeCapacity("class1", 2017, 3);
		    	assertTrue(this.admin.getClassCapacity("class1", 2017) == 3 );	
		    }
	    
	    
	    //-------------------Change capacity to negative---------
	    @Test  //BUG
	    public void changeCapacityNegative() {
	    	this.admin.createClass("class1", 2017, "A", 1);
	    	this.student.registerForClass("student1" , "class1" , 2017);
	    	this.admin.changeCapacity("class1", 2017, -1);
	    	assertFalse(this.admin.getClassCapacity("class1", 2017) == -1 );	
	    
	    }
	    //--------------The className/year pair must be unique--------------------
	    @Test 
	    public void createClassUniqueNameYearTestFalse() {
	    		this.admin.createClass("class1", 2017, "A", 2);
	    		this.admin.createClass("class1", 2017, "A", 15);
	    		assertFalse(this.admin.getClassCapacity("class1", 2017) == 15);
	    }// class with same name and year shouldn't exist
	    
	    @Test 
	    public void createClassUniqueNameYearTestTrue() {
	    		this.admin.createClass("class1", 2017, "A", 2);
	    		this.admin.createClass("class2", 2017, "B", 15);
	    		assertTrue(this.admin.classExists("class2", 2017));
	    }// class with same name and year shouldn't exist
	    
	    
	    //-----------------Make a class name with empty string class name ----------
	    
	    @Test //BUG
	    public void createClassEmptyNameClass() {
	    		this.admin.createClass("", 2017, "A", 2);
	    		assertFalse(this.admin.classExists("", 2017));
	    }
	  //-----------------Make a class name with empty string instructor----------
	    @Test //BUG
	    public void createClassEmptyNameInstuctor() {
	    		this.admin.createClass("class", 2017, "", 2);
	    		assertFalse(this.admin.classExists("class", 2017));
	    }
	   //--------create class with correct info.---------
	    @Test 
	    public void createClassCorrect() {
	    		this.admin.createClass("Class", 2017, "A", 2);
	    		assertTrue(this.admin.classExists("Class", 2017));
	    }
	    

}