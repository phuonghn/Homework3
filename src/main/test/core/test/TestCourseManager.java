package core.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalMatchers;
import org.mockito.Mockito;
import org.mockito.Spy;

import core.api.ICourseManager;
import core.api.impl.Admin;
import core.api.impl.CourseManager;

/**
 * Tests course manager. Since the Admin implementation is known to be bugging. 
 * 
 * @author Vincent
 *
 */
public class TestCourseManager {

	@Spy
	private Admin admin;
	private ICourseManager courseManager;
	
	@Before
	public void setup() {
		this.admin = Mockito.spy(new Admin());
		this.courseManager = new CourseManager(this.admin);
		setupMocking();
	}

	/*
	 * Shows some initial set-up for the mocking of Admin.
	 * This includes fixing a known bug (year in past is not correctly checked) in the Admin class by Mocking its behavior.
	 * Not all fixes to Admin can be made from here, so for the more complex constraints you can simply Mock the
	 * specific calls to Admin's createClass to yield the correct behavior in the unit test itself.
	 */
	public void setupMocking() {
		Mockito.doNothing().when(this.admin).createClass(Mockito.anyString(), AdditionalMatchers.lt(2017), Mockito.anyString(), Mockito.anyInt());
		Mockito.doNothing().when(this.admin).createClass(Mockito.anyString(), AdditionalMatchers.gt(2017), Mockito.anyString(), Mockito.anyInt());
		Mockito.doNothing().when(this.admin).createClass(Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(), AdditionalMatchers.gt(999));
		Mockito.doNothing().when(this.admin).createClass(Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(), AdditionalMatchers.lt(1));
		
	}

	@Test
	public void testCreateClassCorrect() {
		this.courseManager.createClass("ECS161", 2017, "Instructor", 1);
		assertTrue(this.courseManager.classExists("ECS161", 2017));
	}
	
	@Test
	public void testCreateClassInPast() {
		this.courseManager.createClass("ECS161", 2016, "Instructor", 1);
		assertFalse(this.courseManager.classExists("ECS161", 2016));
	}

	@Test
	public void testCreateClassInFuture() {
		this.courseManager.createClass("ECS161", 2018, "Instructor", 1);
		Mockito.verify(this.admin, Mockito.never()).createClass(Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyInt());
	}
	//---------Test capacity-------
	@Test
	public void testCapacityLT0() {
		this.courseManager.createClass("ECS161", 2017, "Instructor", -1);
		assertFalse(this.courseManager.classExists("ECS161", 2017));
	}
	@Test
	public void TestCapacityMT999() {
		this.courseManager.createClass("ECS161", 2017, "Instructor", 1000);
		Mockito.verify(this.admin, Mockito.never()).createClass(Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyInt());
	}
	
	@Test
	public void testCapacity0() {
		this.courseManager.createClass("ECS161", 2017, "Instructor", 0);
		Mockito.verify(this.admin, Mockito.never()).createClass(Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyInt());
	}
	
	@Test
	public void testCapacityCorrect() {
		this.courseManager.createClass("ECS161", 2017, "Instructor", 1);
		assertTrue(this.courseManager.classExists("ECS161", 2017));
	}
	
	
	//--------no   more   than   two   classes   per   instructor   per   year---------
	@Test//Bug
	public void instructorFor3Classes() {
		this.courseManager.createClass("ECS161", 2017, "Instructor", 10);
		this.courseManager.createClass("ECS161A", 2017, "Instructor", 10);
		this.courseManager.createClass("ECS161B", 2017, "Instructor", 10);
		assertFalse(this.courseManager.classExists("ECS161B", 2017));
	}
	
	@Test
	public void instructorFor2Classes() {
		this.courseManager.createClass("ECS161", 2017, "Instructor", 10);
		this.courseManager.createClass("ECS161A", 2017, "Instructor", 10);
		assertTrue(this.courseManager.classExists("ECS161A", 2017));
	}
	
	//---------Empty class & instructor------------
	@Test
	public void EmptyClass() {
		this.courseManager.createClass("", 2017, "Instructor", 10);
		Mockito.verify(this.admin, Mockito.never()).createClass(Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyInt());
	}
	
	@Test
	public void EmptyInstructor() {
		this.courseManager.createClass("Class", 2017, "", 10);
		Mockito.verify(this.admin, Mockito.never()).createClass(Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyInt());
	}
	
	
	
	
	//---  no   two   classes   with same   name/year).   --------------------------
	@Test//bug
	public void createClassSameNameYear() {
		this.courseManager.createClass("Class", 2017, "Instructor", 10);
		this.courseManager.createClass("Class", 2017, "B", 10);
		assertFalse(this.courseManager.getClassInstructor("Class", 2017) == "B");
	}
}
