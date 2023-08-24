/**
 * CS2103T iP Week 2
 * AY23/24 Semester 1
 * A product named Eva (Originally Duke), a Personal Assistant Chatbot that helps a person
 * to keep track of various things.
 *
 * @author bhnuka, Bhanuka Bandara Ekanayake (AXXX7875J), G01
 * @version v1.0 CS2103T AY 23/24 Sem 1
 */

import java.util.ArrayList;
import java.util.Scanner;

/**
 * All the sourcecode behind the chatbot, Eva
 */
public class Eva {

    /**
     * The initialisation of the chatbot, Eva
     */
    public static void main(String[] args) {
        String logo = "  ______          \n"
                + " |  ____|         \n"
                + " | |____   ____ _ \n"
                + " |  __\\ \\ / / _` |\n"
                + " | |___\\ V / (_| |\n"
                + " |______\\_/ \\__,_|\n";
        System.out.println("Hello from\n" + logo);
        Scanner scanner = new Scanner(System.in);
        System.out.println("\t____________________________________________________________");
        System.out.println("\t Hello! I'm Eva.");
        System.out.println("\t What can I do for you?");
        System.out.println("\t____________________________________________________________");

        ArrayList<Task> tasks = new ArrayList<>(); // Use ArrayList to store tasks
        int taskCount = 0; // Counter for tasks

        // Set of cases that the chatbot considers, namely: bye, list, mark, unmark,
        // todo, deadline, event & delete
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) { // Exits when the user types the command bye
                System.out.println("\t____________________________________________________________");
                System.out.println("\t Bye. Hope to see you again soon!");
                System.out.println("\t____________________________________________________________");
                break;
            } else if (input.equals("list")) { // Displays items in the list when requested
                System.out.println("\t____________________________________________________________");
                if (taskCount == 0) {
                    System.out.println("\t No tasks added yet.");
                } else {
                    System.out.println("\t Here are the tasks in your list:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println("\t " + (i + 1) + "." + tasks.get(i));
                    }
                }
                System.out.println("\t____________________________________________________________");
            } else if (input.startsWith("mark")) { // The ability to mark tasks as done
                int taskIndex = Integer.parseInt(input.substring(5)) - 1;
                if (taskIndex >= 0 && taskIndex < taskCount) {
                    tasks.get(taskIndex).markDone();
                    System.out.println("\t____________________________________________________________");
                    System.out.println("\t Nice! I've marked this task as done:");
                    System.out.println("\t   " + tasks.get(taskIndex));
                    System.out.println("\t____________________________________________________________");
                } else {
                    System.out.println("\t Task not found.");
                }
            } else if (input.startsWith("unmark")) { // The ability to change the status back to not done
                int taskIndex = Integer.parseInt(input.substring(7)) - 1;
                if (taskIndex >= 0 && taskIndex < taskCount) {
                    tasks.get(taskIndex).markUndone();
                    System.out.println("\t____________________________________________________________");
                    System.out.println("\t OK, I've marked this task as not done yet:");
                    System.out.println("\t   " + tasks.get(taskIndex));
                    System.out.println("\t____________________________________________________________");
                } else {
                    System.out.println("\t Task not found.");
                }
            } else if (input.startsWith("todo")) {
                // Tasks without any date/time attached to it e.g., visit new theme park
                try {
                    if (input.length() <= 5) {
                        throw new DukeException("\t ☹ OOPS!!! The description of a todo cannot be empty.");
                    }
                    tasks.add(new Todo(input.substring(5)));
                    taskCount++;
                    System.out.println("\t____________________________________________________________");
                    System.out.println("\t Got it. I've added this task: ");
                    System.out.println("\t\t" + tasks.get(taskCount - 1));
                    System.out.println("\t Now you have " + taskCount + " task(s) in the list.");
                    System.out.println("\t____________________________________________________________");
                } catch (DukeException e) {
                    System.out.println("\t____________________________________________________________");
                    System.out.println("\t" + e.getMessage());
                    System.out.println("\t____________________________________________________________");
                }
            } else if (input.startsWith("deadline")) {
                // Tasks that need to be done before a specific date/time e.g., submit report by 11/10/2019 5pm
                try{
                    int byIndex = input.indexOf("/by");
                    if (byIndex == -1) {
                        throw new DukeException("\t ☹ OOPS!!! The deadline description must include a /by date.");
                    }
                    String description = input.substring(9, byIndex).trim();
                    if (description.isEmpty()) {
                        throw new DukeException("☹ OOPS!!! The deadline description cannot be empty.");
                    }
                    String by = input.substring(byIndex + 3).trim();
                    tasks.add(new Deadline(description, by));
                    taskCount++;
                    System.out.println("\t____________________________________________________________");
                    System.out.println("\t Got it. I've added this task: ");
                    System.out.println("\t\t" + tasks.get(taskCount-1));
                    System.out.println("\t Now you have " + taskCount + " task(s) in the list.");
                    System.out.println("\t____________________________________________________________");
                } catch (DukeException e) {
                    System.out.println("\t____________________________________________________________");
                    System.out.println("\t" + e.getMessage());
                    System.out.println("\t____________________________________________________________");
                }
            } else if (input.startsWith("event")) {
                // Tasks that start at a specific date/time and ends at a specific date/time
                // e.g., (a) team project meeting 2/10/2019 2-4pm (b) orientation week 4/10/2019 to 11/10/2019
                try {
                    int fromIndex = input.indexOf("/from");
                    int toIndex = input.indexOf("/to");
                    if (fromIndex == -1 && toIndex == -1) {
                        throw new DukeException("\t ☹ OOPS!!! The event description must include both /from and /to dates.");
                    }
                    String description = input.substring(6, fromIndex).trim();
                    if (description.isEmpty()) {
                        throw new DukeException("☹ OOPS!!! The event description cannot be empty.");
                    }
                    String from = input.substring(fromIndex + 5, toIndex).trim();
                    String to = input.substring(toIndex + 3).trim();
                    tasks.add(new Event(description, from, to));
                    taskCount++;
                    System.out.println("\t____________________________________________________________");
                    System.out.println("\t Got it. I've added this task: ");
                    System.out.println("\t\t" + tasks.get(taskCount-1));
                    System.out.println("\t Now you have " + taskCount + " task(s) in the list.");
                    System.out.println("\t____________________________________________________________");
                } catch (DukeException e) {
                    System.out.println("\t____________________________________________________________");
                    System.out.println("\t" + e.getMessage());
                    System.out.println("\t____________________________________________________________");
                }
            } else if (input.startsWith("delete")) {
                // Delete tasks from the list
                try {
                    int taskIndex = Integer.parseInt(input.substring(7)) - 1;
                    if (taskIndex >= 0 && taskIndex < tasks.size()) {
                        Task removedTask = tasks.remove(taskIndex);
                        System.out.println("\t____________________________________________________________");
                        System.out.println("\t Noted. I've removed this task:");
                        System.out.println("\t   " + removedTask);
                        System.out.println("\t Now you have " + tasks.size() + " tasks in the list.");
                        System.out.println("\t____________________________________________________________");
                        taskCount--;
                    } else {
                        throw new DukeException("☹ OOPS!!! Task not found.");
                    }
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    System.out.println("\t____________________________________________________________");
                    System.out.println("\t ☹ OOPS!!! Please enter a valid task index to delete.");
                    System.out.println("\t____________________________________________________________");
                } catch (DukeException e) {
                    System.out.println("\t____________________________________________________________");
                    System.out.println("\t" + e.getMessage());
                    System.out.println("\t____________________________________________________________");
                }
            } else {
                System.out.println("\t____________________________________________________________");
                System.out.println("\t ☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
                System.out.println("\t____________________________________________________________");
            }
        }
        scanner.close();
    }

    /**
     * Class DukeException to represent exceptions specific to Duke.
     */
    static class DukeException extends Exception {
        public DukeException(String message) {
            super(message);
        }
    }


    /**
     * Enums to represent the different types of tasks
     */
    public enum TaskType {
        TODO,
        DEADLINE,
        EVENT
    }

    /**
     * A Task class to represent tasks
     */
    static class Task {
        private final String description;
        private boolean isDone;
        private final TaskType type;

        /**
         * Constructs task with the given description and type
         *
         * @param description the given description of the task
         * @param type the given type of the task
         */
        public Task(String description, TaskType type) {
            this.description = description;
            this.isDone = false;
            this.type = type;
        }

        /**
         * Method that marks tasks as done
         */
        public void markDone() {
            isDone = true;
        }

        /**
         * Method that changes back the task status to not done
         */
        public void markUndone() {
            isDone = false;
        }

        /**
         * Returns String representing the task
         *
         * @return String representing task
         */
        @Override
        public String toString() {
            return "[" + (isDone ? "X" : " ") + "] " + type + ": " + description;
        }
    }

    /**
     * A Todo class to represent todo tasks
     */
    static class Todo extends Task {

        /**
         * Constructs todo with the given description
         *
         * @param description the given description of the todo
         */
        public Todo(String description) {
            super(description, TaskType.TODO);
        }

        /**
         * Returns String representing the todo
         *
         * @return String representing todo
         */
        @Override
        public String toString() {
            return "[T]" + super.toString();
        }
    }

    /**
     * A Deadline class to represent deadline tasks
     */
    static class Deadline extends Task {
        protected String by;

        /**
         * Constructs deadline with the given description
         *
         * @param description the given description of the deadline
         * @param by the date by which the deadline is
         */
        public Deadline(String description, String by) {
            super(description, TaskType.DEADLINE);
            this.by = by;
        }

        /**
         * Returns String representing the deadline
         *
         * @return String representing deadline
         */
        @Override
        public String toString() {
            return "[D]" + super.toString() + " (by: " + by + ")";
        }
    }

    /**
     * An Event class to represent event tasks
     */
    static class Event extends Task {
        protected String from;
        protected String to;

        /**
         * Constructs deadline with the given description
         *
         * @param description the given description of the deadline
         * @param from the time at which the event starts
         * @param to the time at which the event ends
         */
        public Event(String description, String from, String to) {
            super(description, TaskType.EVENT);
            this.from = from;
            this.to = to;
        }

        /**
         * Returns String representing the event
         *
         * @return String representing event
         */
        @Override
        public String toString() {
            return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
        }
    }
}