package yanglifan.learn.core.dsl;

public class GraphDslSample {
    public static void main(String[] args) {
        GraphBuilder.graph()
                .edge()
                .from("A")
                .to("B")
                .weight(8d)
                .edge()
                .from("B")
                .to("C")
                .weight(4d)
                .edge()
                .from("A")
                .to("C")
                .weight(5d)
                .printGraph();
    }
}
