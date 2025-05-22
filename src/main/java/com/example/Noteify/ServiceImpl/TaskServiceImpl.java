package com.example.Noteify.ServiceImpl;

import com.example.Noteify.Model.Task;
import com.example.Noteify.Model.User;
import com.example.Noteify.Repo.TaskRepo;
import com.example.Noteify.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepo taskRepo;

	@Override
	public Task saveTask(Task task) {
		return taskRepo.save(task);
	}

	@Override
	public List<Task> getAllTasks() {
		return taskRepo.findAll();
	}

	@Override
	public Task getTaskById(int id) {
		return taskRepo.findById(id).orElse(null);
	}

	@Override
	public Task updateTask(int id, Task updatedTask) {
		Task existingTask = taskRepo.findById(id).orElse(null);
		if (existingTask != null) {
			existingTask.setTitle(updatedTask.getTitle());
			existingTask.setDescription(updatedTask.getDescription());
			existingTask.setDuedate(updatedTask.getDuedate());
			existingTask.setPriority(updatedTask.getPriority());
			existingTask.setStatus(updatedTask.getStatus());
			existingTask.setIsImportant(updatedTask.getIsImportant());
			existingTask.setCategory(updatedTask.getCategory());
			existingTask.setReminderAt(updatedTask.getReminderAt());
			return taskRepo.save(existingTask);
		}
		return null;
	}

	@Override
	public String deleteTask(int id) {
		taskRepo.deleteById(id);
		return "Task with ID " + id + " deleted successfully.";
	}

	@Override
	public List<Task> findByDuedateBetween(Date start, Date end) {
		return taskRepo.findByDuedateBetween(start, end);
	}

	@Override
	public List<Task> getTasksByCategory(String category) {
		return taskRepo.findAll().stream()
				.filter(t -> t.getCategory() != null && t.getCategory().equalsIgnoreCase(category))
				.collect(Collectors.toList());
	}

	@Override
	public List<Task> getTasksForUser(User user) {
		return taskRepo.findByUser_Id(user.getId());
	}

	/*****************************************************************/

	@Override
	public List<Task> getTasksSortedByPriorityForUser(User user) {
		List<Task> allTasks = getTasksForUser(user);

		// Convert list to array
		Task[] taskArray = new Task[allTasks.size()];
		for (int i = 0; i < allTasks.size(); i++) {
			taskArray[i] = allTasks.get(i);
		}

		// Apply QuickSort
		quickSort(taskArray, 0, taskArray.length - 1);

		// Convert back to list
		List<Task> sortedTasks = new ArrayList<>();
		for (Task task : taskArray) {
			sortedTasks.add(task);
		}

		return sortedTasks;
	}

	/*****************************************************************/

	private void quickSort(Task[] arr, int low, int high) {
		if (low < high) {
			int pi = partition(arr, low, high);

			quickSort(arr, low, pi - 1);
			quickSort(arr, pi + 1, high);
		}
	}

	private int partition(Task[] arr, int low, int high) {
		Task pivot = arr[high];
		int pivotPriority = getPriorityLevel(pivot);
		int i = low - 1;

		for (int j = low; j < high; j++) {
			if (getPriorityLevel(arr[j]) <= pivotPriority) {
				i++;
				// swap arr[i] and arr[j]
				Task temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
			}
		}

		// swap arr[i+1] and arr[high] (pivot)
		Task temp = arr[i + 1];
		arr[i + 1] = arr[high];
		arr[high] = temp;

		return i + 1;
	}

	/*****************************************************************/

	/*
	 * @Override public List<Task> getTasksSortedByStatusForUser(User user) { return
	 * getTasksForUser(user).stream()
	 * .sorted(Comparator.comparingInt(this::getStatusLevel))
	 * .collect(Collectors.toList()); }
	 */
	/*****************************************************************/
	@Override
	public List<Task> getTasksSortedByStatusForUser(User user) {
		List<Task> tasks = getTasksForUser(user);
		if (tasks.isEmpty())
			return new ArrayList<>();

		Task[] heap = new Task[tasks.size()];
		for (int i = 0; i < tasks.size(); i++) {
			heap[i] = tasks.get(i);
		}

		buildMinHeap(heap, heap.length);

		List<Task> sortedList = new ArrayList<>();
		int size = heap.length;

		while (size > 0) {
			sortedList.add(heap[0]);
			heap[0] = heap[size - 1];
			size--;
			minHeapify(heap, size, 0);
		}

		return sortedList;
	}

	private void buildMinHeap(Task[] heap, int size) {
		for (int i = size / 2 - 1; i >= 0; i--) {
			minHeapify(heap, size, i);
		}
	}

	private void minHeapify(Task[] heap, int size, int i) {
		int smallest = i;
		int left = 2 * i + 1;
		int right = 2 * i + 2;

		if (left < size && getStatusLevel(heap[left]) < getStatusLevel(heap[smallest])) {
			smallest = left;
		}

		if (right < size && getStatusLevel(heap[right]) < getStatusLevel(heap[smallest])) {
			smallest = right;
		}

		if (smallest != i) {
			Task temp = heap[i];
			heap[i] = heap[smallest];
			heap[smallest] = temp;
			minHeapify(heap, size, smallest);
		}
	}
	/*
	 * private int getStatusLevel(Task task) { if (task.getStatus() == null) return
	 * Integer.MAX_VALUE; switch (task.getStatus().toUpperCase()) { case "PENDING":
	 * return 1; case "IN_PROGRESS": return 2; case "COMPLETED": return 3; default:
	 * return Integer.MAX_VALUE; } }
	 */

	/*****************************************************************/

	@Override
	public List<Task> getUpcomingTasksForUser(User user) {
		LocalDate today = LocalDate.now();
		LocalDate nextWeek = today.plusDays(7);
		return getTasksForUser(user).stream().filter(t -> {
			if (t.getDuedate() == null)
				return false;
			LocalDate due = new java.sql.Date(t.getDuedate().getTime()).toLocalDate();
			return !due.isBefore(today) && !due.isAfter(nextWeek);
		}).sorted(Comparator.comparing(Task::getDuedate)).collect(Collectors.toList());
	}

	private int getPriorityLevel(Task task) {
		return switch (Optional.ofNullable(task.getPriority()).orElse("").toLowerCase()) {
		case "high" -> 1;
		case "medium" -> 2;
		case "low" -> 3;
		default -> 4;
		};
	}

	private int getStatusLevel(Task task) {
		return switch (Optional.ofNullable(task.getStatus()).orElse("").toLowerCase()) {
		case "pending" -> 1;
		case "in-progress" -> 2;
		case "completed" -> 3;
		default -> 4;
		};
	}

	@Override
	public void deleteTaskByUser(int id, User user) {
		Task task = taskRepo.findById(id).orElse(null);
		if (task != null && task.getUser() != null && task.getUser().getId() == user.getId()) {
			taskRepo.delete(task);
		} else {
			throw new IllegalArgumentException("Task not found or unauthorized");
		}
	}

	@Override
	public List<Task> getTasksSortedByPriority() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Task> getTasksSortedByStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Task> getUpcomingTasksSortedByDueDate() {
		// TODO Auto-generated method stub
		return null;
	}
}
