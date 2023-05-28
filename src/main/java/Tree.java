import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Tree implements Serializable {

    private Field data = null;

    private List<Tree> children;
    private List<Integer> childrenData;
    private boolean afterStrike;
    private Tree parent = null;

    public Tree(Field data) {
        this.data = data;
        afterStrike = false;
        childrenData = new ArrayList<>();
        children = new ArrayList<>();
    }

    public void addChild(Tree child) {
        child.setParent(this);
        this.children.add(child);
        this.childrenData.add(child.getData().getId());
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
        if (this.childrenData != null) this.childrenData.clear();
    }

    public boolean isAfterStrike() {
        return afterStrike;
    }

    public void setAfterStrike(boolean afterStrike) {
        this.afterStrike = afterStrike;
    }
    public List<Integer> getChildrenData() {
        return childrenData;
    }

    public Field getData() {
        return data;
    }

    private void setParent(Tree parent) {
        this.parent = parent;
    }

    public int getMaxDepth() {
        return getMaxDepth(this);
    }

    private int getMaxDepth(Tree node) {
        if (node == null) {
            return 0;
        }

        int maxChildDepth = 0;
        for (Tree child : node.getChildren()) {
            int childDepth = getMaxDepth(child);
            maxChildDepth = Math.max(maxChildDepth, childDepth);
        }

        return maxChildDepth + 1;
    }


}