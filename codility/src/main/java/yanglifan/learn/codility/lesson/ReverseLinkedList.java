package yanglifan.learn.codility.lesson;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ReverseLinkedList {
    class Node {
        String value;
        Node next;

        public Node(String value) {
            this.value = value;
        }
    }

    public static Node solution(Node head) {
        Node current = head;
        Node next = head.next;
        while (next != null) {
            Node tmpNext = next.next;
            next.next = current;
            current = next;
            next = tmpNext;
        }
        return current;
    }

    @Test
    public void test() {
        Node head = new Node("A");
        head.next = new Node("B");
        head.next.next = new Node("C");
        head.next.next.next = new Node("D");

        Node tail =  solution(head);
        assertThat(tail.value, is("D"));
        assertThat(tail.next.value, is("C"));
        assertThat(tail.next.next.value, is("B"));
        assertThat(tail.next.next.next.value, is("A"));
    }
}
