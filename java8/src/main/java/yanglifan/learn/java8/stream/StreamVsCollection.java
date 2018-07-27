package yanglifan.learn.java8.stream;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StreamVsCollection {
    private List<Transaction> transactions;

    @Before
    public void setUp() throws Exception {
        transactions = Arrays.asList(
                new Transaction(1, Transaction.TransactionType.GROCERY, new BigDecimal("10")),
                new Transaction(2, Transaction.TransactionType.GROCERY, new BigDecimal("20")),
                new Transaction(3, Transaction.TransactionType.SUPERMARKET, new BigDecimal("100"))
        );
    }

    /**
     * select * from transactions where type = 'GROCERY'
     */
    @Test
    public void test_collection_way() {
        System.out.println("========== Collection Way ==========");
        List<Transaction> groceryTransactions = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getType() == Transaction.TransactionType.GROCERY) {
                groceryTransactions.add(t);
            }
        }
        Collections.sort(groceryTransactions, new Comparator<Transaction>() {
            public int compare(Transaction t1, Transaction t2) {
                return t2.getValue().compareTo(t1.getValue());
            }
        });
        List<Integer> transactionIds = new ArrayList<>();
        for (Transaction t : groceryTransactions) {
            transactionIds.add(t.getId());
        }

        for (int id : transactionIds) {
            System.out.println(id);
        }
    }

    @Test
    public void stream_way() {
        System.out.println("========== Stream Way ==========");
        transactions.stream()
                .filter(t -> t.getType() == Transaction.TransactionType.GROCERY)
                .sorted(Comparator.comparing(Transaction::getValue).reversed())
                .map(Transaction::getId)
                .forEach(System.out::println);
    }

    /**
     * Implement the 2nd exercise in http://docs.oracle.com/javase/tutorial/collections/streams/QandE/questions.html
     */
    @Test
    public void jdk_tutorial_exercise() {
        Collection<Album> source = albums();

        /**
         * Old way process.
         */
        List<Album> oldAlbums = new ArrayList<>();
        for (Album a : source) {
            boolean hasFavorite = false;
            for (Track t : a.tracks) {
                if (t.rating >= 4) {
                    hasFavorite = true;
                    break;
                }
            }
            if (hasFavorite)
                oldAlbums.add(a);
        }
        Collections.sort(oldAlbums, new Comparator<Album>() {
            public int compare(Album a1, Album a2) {
                return a1.name.compareTo(a2.name);
            }});

        /**
         * Stream way process.
         */
        List<Album> streamAlbums = source.stream()
                .filter(a -> a.tracks.stream().anyMatch(t -> t.rating >= 4))
                .sorted((a1, a2) -> a1.name.compareTo(a2.name))
                .collect(toList());

        assertThat(streamAlbums, is(oldAlbums));
    }

    Collection<Album> albums() {
        Collection<Album> albums = new ArrayList<>();
        Album album = new Album("Coast To Coast");
        album.tracks.add(new Track(5));
        album.tracks.add(new Track(4));
        album.tracks.add(new Track(3));
        albums.add(album);

        album = new Album("Black & Blue");
        album.tracks.add(new Track(5));
        album.tracks.add(new Track(4));
        album.tracks.add(new Track(3));
        albums.add(album);

        album = new Album("Borrowed Heaven");
        album.tracks.add(new Track(3));
        album.tracks.add(new Track(3));
        albums.add(album);

        return albums;
    }

    class Album {
        String name;

        Collection<Track> tracks = new ArrayList<>();

        public Album(String name) {
            this.name = name;
        }
    }

    class Track {
        int rating;

        public Track(int rating) {
            this.rating = rating;
        }
    }
}

