package yanglifan.learn.java8.stream;

import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Yang Lifan
 */
public class CollectorsTest {
    @Test
    public void testNameOfAlbums() {

    }

    public Map<Artist, List<String>> nameOfAlbums(Stream<Album> albums) {
        return albums.collect(Collectors.groupingBy(Album::getMainMusician, Collectors.mapping(Album::getName, Collectors.toList())));
    }
}
