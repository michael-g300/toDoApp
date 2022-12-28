package uriparse;

import static org.junit.jupiter.api.Assertions.*;

import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.logging.Level;
import java.util.logging.Logger;

import tasks.Task;

class ParseAndEncodeTest {
	private static final Logger s_logger = Logger.getLogger(ParseAndEncodeTest.class.getCanonicalName());
	
	SecureRandom random = new SecureRandom();
	final static int DATE_LEFT_LIMIT = 20230101;
	final static int DATE_RIGHT_LIMIT = 20230131;
	final static int TIME_LEFT_LIMIT = 1000;
	final static int TIME_RIGHT_LIMIT = 2359;
	final static int NAME_SIZE = 10;
	final static int LEFT_LIMIT = 65; // letter 'A'
	final static int RIGHT_LIMIT = 90; // letter 'Z'

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		String dateString = stringGenerator(DATE_LEFT_LIMIT, DATE_RIGHT_LIMIT);
		s_logger.log(Level.INFO, "date String: " + dateString);
		
		String timeString = stringGenerator(TIME_LEFT_LIMIT, TIME_RIGHT_LIMIT);
		s_logger.log(Level.INFO, "time String: " + timeString);
		
		LocalDateTime generatedDueTime = LocalDateTime.of(convertToDate(dateString), convertToTime(timeString));
		
	    String generatedName = nameGenerator(NAME_SIZE, LEFT_LIMIT, RIGHT_LIMIT);
	    s_logger.log(Level.INFO, "generated name: " + generatedName);
	    
	    Task task1 = new Task(generatedName, generatedDueTime);
	    s_logger.log(Level.INFO, "task1 name: " + task1.getName());
	    s_logger.log(Level.INFO, "task1 date and time: " + task1.getDueTime());
	    
	    String encodedTask = TaskEncoder.encode(task1);
	    s_logger.log(Level.INFO, "encoded task string: " + encodedTask);
	    String nameEncoded = encodedTask.substring(0, 10);
	    s_logger.log(Level.INFO, "encoded task name: " + nameEncoded);
	    String dateEncoded = encodedTask.substring(11, 19);
	    s_logger.log(Level.INFO, "encoded task date: " + dateEncoded);
	    String timeEncoded = encodedTask.substring(20, 24);
	    s_logger.log(Level.INFO, "encoded task time: " + timeEncoded);
	    
	    Task task2 = TaskParser.parseTask(nameEncoded, dateEncoded, timeEncoded);
	    
	    assertTrue(task1.equals(task2));
	    
	}
	
	private static LocalTime convertToTime(final String strHhMm) {
		if (strHhMm.length() != 4) {
			throw new IllegalArgumentException("time parameter of wrong length. use hhmm.");
		}
	
		int hour = Integer.valueOf(strHhMm.substring(0, 2));
		int minute = Integer.valueOf(strHhMm.substring(2, 4));
		
		return LocalTime.of(hour,  minute);
	}
	
	private static LocalDate convertToDate(final String str) {
		if (str.length() != 8) {
			throw new IllegalArgumentException("date parameter of wrong length. use yyyymmdd.");
		}
	
		int year = Integer.valueOf(str.substring(0, 4));
		int month = Integer.valueOf(str.substring(4, 6));
		int day = Integer.valueOf(str.substring(6, 8));
		
		return LocalDate.of(year, month, day);
	}
	
	private static String stringGenerator(int leftLimit, int rightLimit) {
	    int randomLimitedInt = ThreadLocalRandom.current().nextInt(leftLimit, rightLimit + 1);
	    s_logger.log(Level.INFO, "current int: " + randomLimitedInt);
	    return String.valueOf(randomLimitedInt);
	}
	
	private static String nameGenerator(int size, int leftLimit, int rightLimit) {
		String name = new String();
		for (int i = 0 ; i < size ; ++i) {
			int randomLimitedInt = ThreadLocalRandom.current().nextInt(leftLimit, rightLimit + 1);
			name += (char)randomLimitedInt;
		}
	    s_logger.log(Level.INFO, "current name: " + name);
	    return name;
	}

}
