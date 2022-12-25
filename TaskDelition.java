package toDoApp;

public class TaskDelition implements Pages {

private final String m_content;
	
	public TaskDelition() {
		this.m_content = "<h2>Delete task</h2>"
				+ "If you entered a valid task name - that is - <b>the task appears in the task lists"
				+ " then the task you typed will be deleted from the tasks list."
				+ "<br><Br> <A href='/taskdisplay'>Back to the tasks page</A>";
	}
	
	@Override
	public String getContent() {
		return this.m_content;
	}

	@Override
	public void setContent(String[] nameParts) {
		// TODO Auto-generated method stub

	}

}
