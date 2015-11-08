package yanglifan.learn.algorithm.distributed.consistency_hash;

import org.junit.Test;

import java.util.concurrent.ConcurrentMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ConsistencyHashTest {
    private ConsistencyHash consistencyHash = new ConsistencyHash();

    @Test
    public void add_a_server_then_find() {
        Server server = new Server("server_one");
        consistencyHash.addServer(server);

        Node<Server> targetNode = consistencyHash.getServerNode(new Object());

        assertThat(targetNode.getValue().getName(), is(server.getName()));
    }

    @Test
    public void add_two_servers_then_find() throws Exception {
        Server server1 = new Server("server_1");
        Node<Server> server1Node = consistencyHash.addServer(server1);

        Server server2 = new Server("server_2");
        Node<Server> server2Node = consistencyHash.addServer(server2);

        Node<Server> targetNode = consistencyHash.getServerNode(new Object() {
            @Override
            public int hashCode() {
                return server1Node.getIndex() - 1;
            }
        });

        assertThat(targetNode.getValue().getName(), is(server1.getName()));

        targetNode = consistencyHash.getServerNode(new Object() {
            @Override
            public int hashCode() {
                return server2Node.getIndex() + 1;
            }
        });

        assertThat(targetNode.getValue().getName(), is(server1.getName()));
    }
}