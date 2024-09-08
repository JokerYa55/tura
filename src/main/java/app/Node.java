package app;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author vasil
 */
@Getter
@Setter
public class Node extends NodeBase {

    @ToString.Exclude
    private List<Node> result = new ArrayList<>();

    public Node(int i, int j) {
        super(i, j);
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
