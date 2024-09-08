package app;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author vasil
 */
public class Tura {
    
    private static Set<List<Node>> result = new HashSet<>();
    
    public static void main(String[] args) {
        var board = getBoard();
        printBoard(board);
        System.out.println("");
        step(board, null, 0);
        
        System.out.println("result = " + result.size());
        
        var rr = result.stream()
                .map(t -> t.stream().sorted((o1, o2) -> o1.getI() - o2.getI()).map(t1 -> t1.getI() + ":" + t1.getJ() + "").distinct().collect(Collectors.joining(",")))
                .sorted()
                .distinct()
                .toList();
        
        System.out.println("size = " + rr.size());
        
//        rr.forEach(t -> {
//            System.out.println("t = " + t);
//            showResultBoard(t);
//        });
        
        result.forEach(t -> {
            showResultBoard(t);
            System.out.println("");
        });

    }
    
    private static void step(Set<Node> board, Node node, int stepNum) {
        stepNum++;
//        Optional.ofNullable(node).ifPresent(t -> System.out.println(t.getResult()));
        Optional.ofNullable(node).ifPresent((t) -> t.getResult().add(node));
        Set<Node> boardElemets = Collections.emptySet();
        
        if (node != null) {
            final int i = node.getI();
            final int j = node.getJ();
            boardElemets = board.stream()
                    .filter(t -> (t.getI() != i) && (t.getJ() != j))
                    .map(t -> {
                        var newnode = new Node(t.getI(), t.getJ());
                        t.getResult().stream()
                                .forEach(t1 -> newnode.getResult().add(t1));
                        return newnode;
                    })
                    .collect(Collectors.toSet());
        } else {
            boardElemets = getBoard();
        }

//        System.out.println("board_elements = " + boardElemets);
        if (boardElemets.size() > 1) {
            for (Node boardElemet : boardElemets) {
                // Проверяем что элемент отсутствует в пути
                boolean isActiveFlag = boardElemet.getResult().stream().filter(t -> t.getI() == boardElemet.getI() && t.getJ() == boardElemet.getJ()).findAny().isPresent();
                if (!isActiveFlag) {
                    Optional.ofNullable(node)
                            .ifPresent(t -> t.getResult().stream().sorted((o1, o2) -> o1.getI() - o2.getI()).distinct().forEach(t1 -> boardElemet.getResult().add(t1)));
                    step(boardElemets, boardElemet, stepNum);
                }
            }
            
        } else {
            System.out.println("result = " + result.size());
            System.out.println("------------------------------------");
            node.getResult().add(boardElemets.stream().findAny().get());
            result.add(node.getResult());
            
        }
    }
    
    private static Set<Node> getBoard() {
        final Set<Node> board = new HashSet<>();
        for (int i = 0; i < AppConst.BOARD_SIZE; i++) {
            for (int j = 0; j < AppConst.BOARD_SIZE; j++) {
                board.add(new Node(i, j));
            }
        }
        return board;
    }
    
    private static void printBoard(Set<? extends NodeBase> board) {
        board.stream()
                .collect(Collectors.groupingBy(NodeBase::getI))
                .entrySet()
                .stream()
                .forEach(t1 -> {
                    final var s = t1.getValue().stream().map(t2 -> " 0").collect(Collectors.joining(" "));
                    System.out.println(s);
                });
    }
    
    private static void showResultBoard(final List<? extends NodeBase> boardActiveElement) {
        for (int i = 0; i < AppConst.BOARD_SIZE; i++) {
            final var ii = i;
            var s = IntStream.range(0, AppConst.BOARD_SIZE)
                    .mapToObj(t -> {
                        var flag = boardActiveElement.stream()
                                .filter(t1 -> ii == t1.getI() && t1.getJ() == t)
                                .findAny()
                                .isPresent();
                        return flag ? " ■" : " x";
                    })
                    .collect(Collectors.joining());
            System.out.println(s);
        }
    }
}
