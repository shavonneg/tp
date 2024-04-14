package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyBookKeeper;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of BookKeeper data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private final BookKeeperStorage bookKeeperStorage;
    private final UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code BookKeeperStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(BookKeeperStorage bookKeeperStorage, UserPrefsStorage userPrefsStorage) {
        this.bookKeeperStorage = bookKeeperStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ BookKeeper methods ==============================

    @Override
    public Path getBookKeeperFilePath() {
        return bookKeeperStorage.getBookKeeperFilePath();
    }

    @Override
    public Optional<ReadOnlyBookKeeper> readAddressBook() throws DataLoadingException {
        return readAddressBook(bookKeeperStorage.getBookKeeperFilePath());
    }

    @Override
    public Optional<ReadOnlyBookKeeper> readAddressBook(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return bookKeeperStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyBookKeeper addressBook) throws IOException {
        saveAddressBook(addressBook, bookKeeperStorage.getBookKeeperFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyBookKeeper addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        bookKeeperStorage.saveAddressBook(addressBook, filePath);
    }
}
