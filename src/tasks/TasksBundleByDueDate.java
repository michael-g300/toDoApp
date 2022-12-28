package tasks;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class TasksBundleByDueDate implements TasksBundle {
	private final TasksBundle m_bundle;
	
	public TasksBundleByDueDate(TasksBundle bundle) {
		this.m_bundle = bundle;
	}

	@Override
	public void add(Task task) {
		this.m_bundle.add(task);
	}

	@Override
	public Iterator<Entry<Task, MutableState>> iterator() {
		Map<Task, MutableState> tasks = new HashMap<>();
		for (Entry<Task, MutableState> item : this.m_bundle) {
			tasks.put(item.getKey(), item.getValue());
		}
		Map<Task, MutableState> sortedTasks = tasks.entrySet().stream().sorted
				((i1,i2)->i1.getKey().getDueTime().compareTo(i2.getKey().getDueTime())).collect(Collectors.toMap(Map.Entry::getKey,
	                      Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		return sortedTasks.entrySet().iterator();
	}

	@Override
	public boolean isEmpty() {
		return this.m_bundle.isEmpty();
	}

	@Override
	public int size() {
		return this.m_bundle.size();
	}

	@Override
	public MutableState getState(Task task) {
		return this.m_bundle.getState(task);
	}

}
