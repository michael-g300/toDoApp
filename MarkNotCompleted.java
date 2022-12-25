package toDoApp;

public class MarkNotCompleted implements Pages {

private final String m_content;
	
	public MarkNotCompleted() {
		this.m_content = "<h2>Mark Task as Uncomplete</h2>"
				+ "If you entered a valid task name - that is - <b>the task appears in the task lists"
				+ " then the task you typed will be marked as uncomplete."
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
