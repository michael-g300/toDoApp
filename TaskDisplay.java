package toDoApp;

import java.util.*;

public class TaskDisplay implements Pages {

	private String m_content;
	private final List<String[]> m_tasks = new ArrayList<String[]>();
	
	public TaskDisplay() {
		this.m_content = "<h2>Tasks List: </h2>"		
				+ "<br><Br>";
	}
	
	@Override
	public String getContent() {
		return this.m_content;
	}

	@Override
	public void setContent(String[] nameParts) {
		if (this.m_tasks.size() == 0) {
			this.m_tasks.add(new String[] {nameParts[2], nameParts[3], nameParts[4]});
			return;
		}
		for (int i = 0 ; i < this.m_tasks.size(); ++i) {
			if (Integer.valueOf(nameParts[3]) < Integer.valueOf(this.m_tasks.get(i)[1])) {
				this.m_tasks.add(i, new String[] {nameParts[2], nameParts[3], nameParts[4]});
			}
			else if ((Integer.valueOf(nameParts[3]) == Integer.valueOf(this.m_tasks.get(i)[1])) && (Integer.valueOf(nameParts[4]) <= Integer.valueOf(this.m_tasks.get(i)[2]))) {
				this.m_tasks.add(i, new String[] {nameParts[2], nameParts[3], nameParts[4]});
			}
			else if (Integer.valueOf(nameParts[3]) > Integer.valueOf(this.m_tasks.get(i)[1])) {
				this.m_tasks.add(new String[] {nameParts[2], nameParts[3], nameParts[4]});
			}
		}
		String display = new String();
		if (this.m_tasks.size() == 0) {
			for (String[] task : this.m_tasks) {
				display += "<input type='checkbox' id='accept'>" + task[0] + "     " + 
				task[2].charAt(0) + task[2].charAt(1) + ":" + task[2].charAt(3) + task[2].charAt(4) + "<br>";
			}
		}
		this.m_content = "<h2>Tasks List: </h2>"		
				+ "<br><Br>" + display;

	}

}
