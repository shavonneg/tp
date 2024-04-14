package seedu.address.testutil;

import seedu.address.model.BookKeeper;
import seedu.address.model.client.Client;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code BookKeeper ab = new BookKeeperBuilder().withPerson("John", "Doe").build();}
 */
public class BookKeeperBuilder {

    private BookKeeper bookKeeper;

    public BookKeeperBuilder() {
        bookKeeper = new BookKeeper();
    }

    public BookKeeperBuilder(BookKeeper bookKeeper) {
        this.bookKeeper = bookKeeper;
    }

    /**
     * Adds a new {@code Client} to the {@code BookKeeper} that we are building.
     */
    public BookKeeperBuilder withPerson(Client client) {
        bookKeeper.addClient(client);
        return this;
    }

    public BookKeeper build() {
        return bookKeeper;
    }
}
