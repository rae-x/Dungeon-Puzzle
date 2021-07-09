package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Leaf Component for Composite Pattern, used to store base goals
 */
public class SubGoal implements Task {
     
    private String taskCondition;
    private BooleanProperty completed;
    private Task left;
    private Task right;

    public SubGoal(String taskCondition) {
        this.taskCondition = taskCondition;
        this.completed = new SimpleBooleanProperty(false);
        this.left = null;
        this.right = null;
    }

    @Override
    public String getTaskCondition() {
        return this.taskCondition;
    }

    @Override
    public Task getLeft() {
        return this.left;
    }

    @Override
    public Task getRight() {
        return this.right;
    }

    @Override
    public void setLeft(Task task) {
        this.left = task;
    }

    @Override
    public void setRight(Task task) {
        this.right = task;
    }

    @Override
    public BooleanProperty completed() {
        return this.completed;
    }

    @Override
    public boolean isCompleted() {
        return this.completed.get();
    }

    @Override
    public void clearTask(String taskCondition) {
        if (this.taskCondition.equals(taskCondition)) {
            this.completed.set(true);
        }
    }   
}