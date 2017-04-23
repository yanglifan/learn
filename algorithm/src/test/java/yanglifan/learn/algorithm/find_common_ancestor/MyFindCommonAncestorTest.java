package yanglifan.learn.algorithm.find_common_ancestor;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MyFindCommonAncestorTest {
    private FindCommonAncestor findCommonAncestor = new MyFindCommonAncestor();

    @Test
    public void a_b() {
        String[] commits = {"B", "A"};
        String[][] parents = {{"A"}, null};
        String commit1 = "A";
        String commit2 = "B";
        String commonAncestor = findCommonAncestor.findCommonAncestor(commits, parents, commit1, commit2);
        assertThat(commonAncestor, is("A"));
    }

    @Test
    public void simple_1() {
        String[] commits = {"G", "F", "E", "D", "C", "B", "A"};
        String[][] parents = {{"F", "D"}, {"E"}, {"B"}, {"C"}, {"B"}, {"A"}, null};
        String commit1 = "D";
        String commit2 = "F";
        String commonAncestor = findCommonAncestor.findCommonAncestor(commits, parents, commit1, commit2);
        assertThat(commonAncestor, is("B"));
    }

    @Test
    public void simple_2() {
        String[] commits = {"F", "E", "D", "C", "B", "A"};
        String[][] parents = {{"C", "E"}, {"B"}, {"B"}, {"B"}, {"A"}, null};
        String commit1 = "D";
        String commit2 = "F";
        String commonAncestor = findCommonAncestor.findCommonAncestor(commits, parents, commit1, commit2);
        assertThat(commonAncestor, is("B"));
    }
}