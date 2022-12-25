package toDoApp;

public class AddNewTask implements Pages {

	private final String m_content;
	
	public AddNewTask() {
		this.m_content = "<h2>Add new task</h2>"
				+ "If you entered a valid task name - that is - <b>not just an empty space</b>, followed by"
				+ " the date and time then the task you typed will be added to the tasks list."
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
