package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyBookKeeper;

/**
 * A class to access BookKeeper data stored as a json file on the hard disk.
 */
public class JsonBookKeeperStorage implements BookKeeperStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonBookKeeperStorage.class);
    private final Path bookKeeperFilePath;

    /**
     * Constructs a {@code JsonBookKeeperStorage} with the specified file paths.
     *
     * @param bookKeeperFilePath The file path for bookkeeper data.
     */
    public JsonBookKeeperStorage(Path bookKeeperFilePath) {
        this.bookKeeperFilePath = bookKeeperFilePath;
    }


    public Path getBookKeeperFilePath() {
        return bookKeeperFilePath;
    }


    @Override
    public Optional<ReadOnlyBookKeeper> readAddressBook() throws DataLoadingException {
        return readAddressBook(bookKeeperFilePath);
    }

    /**
     * Similar to {@link #readAddressBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyBookKeeper> readAddressBook(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableBookKeeper> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableBookKeeper.class);
        if (jsonAddressBook.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAddressBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveAddressBook(ReadOnlyBookKeeper addressBook) throws IOException {
        saveAddressBook(addressBook, bookKeeperFilePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyBookKeeper)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveAddressBook(ReadOnlyBookKeeper addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableBookKeeper(addressBook), filePath);
    }

}
