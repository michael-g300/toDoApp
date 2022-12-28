package tasks;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.function.Executable;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

class TasksBundleOnDiskTest {
	private static final Logger s_logger = Logger.getLogger(TasksBundleOnDiskTest.class.getCanonicalName());
	
	protected TasksBundle createTasksBundle() {
		return null;
	}  //factory method
	
	private static class AddTaskExecutable implements Executable {
		private final TasksBundle tasks;
		private final Task t;
		
		public AddTaskExecutable(final TasksBundle tasks, final Task t) {
			this.tasks = tasks;
			this.t = t;
		}
		
		@Override
		public void execute() throws Throwable {
			tasks.add(t);
		}
		
	}
	@Test
	void testAddDuplicateTask() {
		final Task t = new Task("abc", LocalDateTime.now());
		final TasksBundle tasks = new TasksBundleOnDisk();
		tasks.add(t);
		
		assertThrowsExactly(TaskAlreadyExistsException.class, new AddTaskExecutable(tasks, t));
		
		try {
			TasksBundleOnDisk.taskDelete(t);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testAddNull() {
		final Task t = new Task(null, LocalDateTime.now());
		final TasksBundle tasks = new TasksBundleOnDisk();
		tasks.add(t);
		
		assertThrowsExactly(IllegalArgumentException.class, new AddTaskExecutable(tasks, t));
		
		try {
			TasksBundleOnDisk.taskDelete(t);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testIsEmptyOnEmpty() {
		TasksBundle tasks = new TasksBundleOnDisk();
		
		assertTrue(tasks.isEmpty());
		assertEquals(0, tasks.size());
		assertTrue(!tasks.iterator().hasNext());
	}
	
	@Test
	void testIsEmptyOnExisting() {
		TasksBundle tasks = new TasksBundleOnDisk();
		final Task t = new Task("abc", LocalDateTime.now());
		tasks.add(t);
		
		assertTrue(!tasks.isEmpty());
		assertEquals(1, tasks.size());
		
		try {
			TasksBundleOnDisk.taskDelete(t);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testIterator() {
		TasksBundle tasks = new TasksBundleOnDisk();
		final Task t1 = new Task("abc", LocalDateTime.now());
		final Task t2 = new Task("FFF", LocalDateTime.now());
		tasks.add(t1);
		tasks.add(t2);
		
		assertEquals(2, tasks.size());
		Iterator<Entry<Task, MutableState>> iter = tasks.iterator();
		assertTrue(tasks.iterator().hasNext());
		s_logger.log(Level.INFO, "new task name: " + iter.next().toString());
		assertTrue(tasks.iterator().hasNext());
		s_logger.log(Level.INFO, "new task name: " + iter.next().toString());
		
		try {
			TasksBundleOnDisk.taskDelete(t1);
			TasksBundleOnDisk.taskDelete(t2);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testGetStateFalse() {
		TasksBundle tasks = new TasksBundleOnDisk();
		final Task t = new Task("abc", LocalDateTime.now());
		tasks.add(t);
		assertTrue(!tasks.getState(t).isCompleted());
		
		try {
			TasksBundleOnDisk.taskDelete(t);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	@Test
	void testGetStateTrue() {
		TasksBundle tasks = new TasksBundleOnDisk();
		final Task t = new Task("abc", LocalDateTime.now());
		tasks.add(t);
		TasksBundleOnDisk.setState(t, true);
		assertTrue(tasks.getState(t).isCompleted());
		
		try {
			TasksBundleOnDisk.taskDelete(t);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
