package yanglifan.learn.algorithm.distributed.consistency_hash;

import java.util.NavigableSet;
import java.util.Random;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConsistencyHash {
    private int hashRingSize = 1024;
    private ConcurrentNavigableMap<Integer, Node<Server>> nodes = new ConcurrentSkipListMap<>();

    public Node<Server> addServer(Server server) {
        int index = (new Random()).nextInt(hashRingSize);
        Node<Server> serverNode = new Node<>(index, server);
        nodes.put(serverNode.getIndex(), serverNode);
        return serverNode;
    }

    public Node<Server> getServerNode(Object obj) {
        int hashCode = obj.hashCode();
        int objIndex = hashCode % hashRingSize;
        return findNode(objIndex);
    }

    private Node<Server> findNode(int index) {
        NavigableSet<Integer> keySet = nodes.keySet();
        if (keySet.isEmpty()) {
            return null;
        }

        Integer nodeIndex = keySet.higher(index);
        if (nodeIndex == null) {
            Integer lastIndex = keySet.first();
            return nodes.get(lastIndex);
        }
        return nodes.get(nodeIndex);
    }
}
