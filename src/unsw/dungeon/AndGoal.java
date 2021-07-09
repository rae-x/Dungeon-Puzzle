package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Leaf Component for Composite Pattern, used to store And Goals
 */
public class AndGoal implements Task {
    
    private String taskCondition;
    private BooleanProperty completed;
    private Task left;
    private Task right;

    public AndGoal() {
        this.taskCondition = "AND";
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
        if (this.left.isCompleted() && this.right.isCompleted()) {
            this.completed.set(true);
        }
        return this.completed.get();
    }

    @Override
    public void clearTask(String taskCondition) {
        if (taskCondition.equals("exit")) {
            if (this.left.getTaskCondition().equals("exit")) {
                if (this.right.isCompleted()){
                    this.left.clearTask(taskCondition);
                }
            } else if (this.right.getTaskCondition().equals("exit")) {
                if (this.left.isCompleted()) {
                    this.right.clearTask(taskCondition);
                }
            } else {
                this.left.clearTask(taskCondition);
                this.right.clearTask(taskCondition);
            }
        } else {
            this.left.clearTask(taskCondition);
            this.right.clearTask(taskCondition);
        }
    }
}