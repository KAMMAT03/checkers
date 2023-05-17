import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Tree implements Serializable {

    private Field data = null;

    private List<Tree> children = new ArrayList<>();
    private List<Integer> childrenData = new ArrayList<>();

    private Tree parent = null;

    public Tree(Field data) {
        this.data = data;
    }

    public Tree addChild(Tree child) {
        child.setParent(this);
        this.children.add(child);
        this.childrenData.add(child.getData().getId());
        return child;
    }

    public void addChildren(List<Tree> children) {
        children.forEach(each -> each.setParent(this));
        this.children.addAll(children);
    }

    public List<Tree> getChildren() {
        return children;
    }
    public void clearStriked(Tree node) {
        if (node != null) {
            node.getData().setStriked(null);

            for (Tree child : node.getChildren()) {
                clearStriked(child);
            }
        }
    }
    public void reset() {
        this.children.clear();
        this.childrenData.clear();
    }

    public List<Integer> getChildrenData() {
        return childrenData;
    }

    public Field getData() {
        return data;
    }

    public void setData(Field data) {
        this.data = data;
    }

    private void setParent(Tree parent) {
        this.parent = parent;
    }

    public Tree getParent() {
        return parent;
    }

}