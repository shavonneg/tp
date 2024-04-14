package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.BookKeeper;
import seedu.address.model.ReadOnlyBookKeeper;

/**
 * Represents a storage for {@link BookKeeper}.
 */
public interface BookKeeperStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getBookKeeperFilePath();

    /**
     * Returns BookKeeper(client) data as a {@link ReadOnlyBookKeeper}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyBookKeeper> readAddressBook() throws DataLoadingException;

    /**
     * @see #getBookKeeperFilePath()
     */
    Optional<ReadOnlyBookKeeper> readAddressBook(Path filePath) throws DataLoadingException;


    /**
     * Saves the given {@link ReadOnlyBookKeeper} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyBookKeeper addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyBookKeeper)
     */
    void saveAddressBook(ReadOnlyBookKeeper addressBook, Path filePath) throws IOException;


}
