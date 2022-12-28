package tasks;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Optional;

public class TasksBundleCached implements TasksBundle {
	private final TasksBundleOnDisk m_onDisk = new TasksBundleOnDisk();
	private Optional<TasksBundleInMemory> m_inMemory = Optional.empty();
	
	private void initializeInMemory() {
		m_inMemory = Optional.of(new TasksBundleInMemory());
		for (Entry<Task, MutableState> item : m_onDisk) {
			m_inMemory.get().add(item.getKey());
			if (item.getValue().isCompleted()) {
				m_inMemory.get().getState(item.getKey()).setCompleted(item.getKey(), true);
			}
		}
	}

	@Override
	public void add(Task task) {
		initializeInMemory();
		m_onDisk.add(task);
		m_inMemory.get().add(task);
	}

	@Override
	public Iterator<Entry<Task, MutableState>> iterator() {
		initializeInMemory();
		return m_inMemory.get().iterator();
	}

	@Override
	public boolean isEmpty() {
		initializeInMemory();
		assert m_inMemory.get().isEmpty() == m_onDisk.isEmpty();
		return m_inMemory.get().isEmpty();
	}

	@Override
	public int size() {
		initializeInMemory();
		assert m_inMemory.get().size() == m_onDisk.size();
		return m_inMemory.get().size();
	}

	@Override
	public MutableState getState(Task task) {
		initializeInMemory();
		MutableState stateInMemory = m_inMemory.get().getState(task);
		MutableState stateOnDisk = m_onDisk.getState(task);
		assert stateInMemory.isCompleted() == stateOnDisk.isCompleted();
		return stateInMemory;
	}

	public void taskDelete(Task task) throws IOException {
		m_onDisk.taskDelete(task);
		while (m_inMemory.get().iterator().hasNext()) {
			if (m_inMemory.get().iterator().next().getKey().equals(task)) {
				m_inMemory.get().iterator().remove();
			}
		}
	}
}
