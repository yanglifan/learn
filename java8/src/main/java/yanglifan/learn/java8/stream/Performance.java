package yanglifan.learn.java8.stream;

import java.util.stream.Stream;

import static java.util.stream.Stream.concat;

/**
 * @author Yang Lifan
 */
public interface Performance {

    public String getName();

    public Stream<Artist> getMusicians();

    // TODO: test
    public default Stream<Artist> getAllMusicians() {
        return getMusicians().flatMap(artist -> {
            return concat(Stream.of(artist), artist.getMembers());
        });
    }

}
