package com.timeWork.core;

import java.util.Comparator;

public class TaskComparator implements Comparator<Task> {

	@Override
	public int compare(Task t1, Task t2) {
		return t1.getTitleProperty().getValue().compareTo(t2.getTitleProperty().getValue());
	}
}