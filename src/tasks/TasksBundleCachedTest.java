package tasks;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class TasksBundleCachedTest {
	
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
		final TasksBundle tasks = new TasksBundleCached();
		tasks.add(t);
		
		assertThrowsExactly(TaskAlreadyExistsException.class, new AddTaskExecutable(tasks, t));
		
			tasks.taskDelete(t);
	}
	
	@Test
	void testAddSeveralTasks() {
		final Task t1 = new Task("abc", LocalDateTime.now());
		final Task t2 = new Task("abc", LocalDateTime.now());
		final Task t3 = new Task("abc", LocalDateTime.now());
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
	void testIterator() {
		fail("Not yet implemented");
	}

	@Test
	void testIsEmpty() {
		fail("Not yet implemented");
	}

	@Test
	void testSize() {
		fail("Not yet implemented");
	}

	@Test
	void testGetState() {
		fail("Not yet implemented");
	}

}
