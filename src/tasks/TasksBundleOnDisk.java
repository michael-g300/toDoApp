package tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import uriparse.TaskParser;

public class TasksBundleOnDisk implements TasksBundle {
	public static final String TASKS_DIRECTORY ="C:\\Users\\Michael\\eclipse-workspace\\ToDo\\tasks";
	
	@Override
	public final void add(final Task task) {
		Path filePath = Paths.get(TASKS_DIRECTORY + "\\" + task.getName());
		if (Files.exists(filePath)) {
			throw new TaskAlreadyExistsException(task);
		}
		try {
			Files.createFile(filePath);
			Files.writeString(filePath, task.getDueTime() + "\nfalse");
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public final Iterator<Entry<Task, MutableState>> iterator() {
		try {
			DirectoryStream<Path> ds = Files.newDirectoryStream(Paths.get(TASKS_DIRECTORY));
			Iterator<Path> pathIterator = ds.iterator();
			Map<Task, MutableState> tasks = new HashMap<>();
			while (pathIterator.hasNext()) {
				Path currPath = pathIterator.next();
				InputStream fileContent = Files.newInputStream(currPath);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fileContent));
				String[] lines = new String[2];
				int i = 0;
				while(reader.ready()) {
				     lines[i] = reader.readLine();
				     ++i;
				}
				Task task = new Task(currPath.getFileName().toString(), LocalDateTime.parse(lines[0]));
				MutableState state = new MutableState();
				state.setCompleted(task, lines[1].equals("true"));
				tasks.put(task, state);
			}
			return tasks.entrySet().iterator();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		throw new NullPointerException();
	}

	@Override
	public final boolean isEmpty() {
		if (Files.isDirectory(Paths.get(TASKS_DIRECTORY))) {
			try (DirectoryStream<Path> dir = Files.newDirectoryStream(Paths.get(TASKS_DIRECTORY))) {
	            return !dir.iterator().hasNext();
	        } 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public final int size() {
		DirectoryStream<Path> ds;
		int filesCount = 0;
		try {
			ds = Files.newDirectoryStream(Paths.get(TASKS_DIRECTORY));
			Iterator<Path> iterator = ds.iterator();
			while (iterator.hasNext()) {
				iterator.next();
				++filesCount;
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return filesCount;
	}

	@Override
	public final MutableState getState(Task task) {
		Path filePath = Paths.get(TASKS_DIRECTORY + "\\" + task.getName());
		MutableState state = new MutableState();
		boolean isCompleted = false;
		BufferedReader reader;
		try {
			reader = Files.newBufferedReader(filePath);
	        while (reader.ready()) {
	            if (reader.readLine().equals("true")) {
	            	isCompleted = true;
	            }
	        }
		} 
	    catch (IOException e) {
			e.printStackTrace();
		}
		state.setCompleted(task, isCompleted);
		return state;
	}
	
	public static void taskDelete(Task task) throws IOException {
		Path filePath = Paths.get(TASKS_DIRECTORY + "\\" + task.getName());
		Files.delete(filePath);
	}
	
	static void setState(Task task, boolean isCompleted) {
		Path filePath = Paths.get(TASKS_DIRECTORY + "\\" + task.getName());
		if (!Files.exists(filePath)) {
			return;
		}
		InputStream fileContent;
		try {
			fileContent = Files.newInputStream(filePath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(fileContent));
			String[] lines = new String[2];
			int i = 0;
			while(reader.ready()) {
			     lines[i] = reader.readLine();
			     ++i;
			}
			Files.delete(filePath);
			Files.createFile(filePath);
			Files.writeString(filePath, task.getDueTime() + "\n" + isCompleted);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
