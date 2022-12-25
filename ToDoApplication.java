package toDoApp;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;
import java.io.IOException;
import super_simple_web_server.SuperSimpleWebServer;

public class ToDoApplication {
	final static String MARK_COMPLETED = "markcompleted";
	final static String TASK_DELITION = "taskdelition";
	final static String ADD_NEW_TASK = "addnewtask";
	final static String MARK_NOT_COMPLETED = "marknotcompleted";
	final static String TASK_DISPLAY = "taskdisplay";

	public static void main(String[] args) {
		final HashMap<String, Pages> pages = new HashMap<String, Pages>();
		pages.put(TASK_DISPLAY, new TaskDisplay());
		pages.put(MARK_COMPLETED, new MarkCompleted());
		pages.put(TASK_DELITION, new TaskDelition());
		pages.put(ADD_NEW_TASK, new AddNewTask());
		pages.put(MARK_NOT_COMPLETED, new MarkNotCompleted());
		
		final Logger logger = Logger.getLogger(ToDoApplication.class.getCanonicalName());
		
		try {
			SuperSimpleWebServer server = new SuperSimpleWebServer(1080, logger);
			while(true) {
				try (SuperSimpleWebServer.Request request = server.waitForRequest()) {
					final String untrust_uri = request.getUri();	
					final String[] untrust_nameParts = untrust_uri.split("/");
					if (untrust_nameParts.length > 0) {
						for (int i=0; i<untrust_nameParts.length; ++i) {
							logger.log(Level.INFO, "I=" + String.valueOf(i) + " VALUE: " + untrust_nameParts[i]);
						}
						try {
							Pages page = pages.get(untrust_nameParts[1].toLowerCase());
							request.getWriter().write(page.getContent());
						}
						catch (NullPointerException npe) {
							logger.log(Level.INFO, "NO VALUE ADDED SO FAR");
						}
						if (untrust_nameParts.length>3) {
							Pages addTask = pages.get(TASK_DISPLAY);
							addTask.setContent(untrust_nameParts);
						}
					}
					else {
						request.getWriter().write(pages.get("").getContent());
					}
				}
			}
		}
		catch (IOException ex) {
			logger.log(Level.SEVERE, "IOException " + ex.getMessage());
			return;
		}

	}

}
