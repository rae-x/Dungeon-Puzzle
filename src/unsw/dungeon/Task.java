package unsw.dungeon;

import javafx.beans.property.BooleanProperty;

/**
 * Interface Component for Composite Pattern, used to store complex goals
 * Implemented using a Binary Search Tree, with the root element being
 * either a AndGoal, OrGoal of SubGoal object
 */
public interface Task {
    public boolean isCompleted();
    public void clearTask(String taskCondition);
    public Task getLeft();
    public Task getRight();
    public void setLeft(Task task);
    public void setRight(Task task);
    public String getTaskCondition();
    public BooleanProperty completed();
}