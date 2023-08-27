package ds.util;

public abstract class PriorityElement {
    protected int priority;

    public void setPriority(int p) {
        this.priority = p;
    }

    public int getPriority() {
        return this.priority;
    }
}
