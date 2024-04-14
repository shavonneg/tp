package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.BookKeeper;
import seedu.address.testutil.TypicalClients;

public class JsonSerializableBookKeeperTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableBookKeeperTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsBookKeeper.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonBookKeeper.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonBookKeeper.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableBookKeeper dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableBookKeeper.class).get();
        BookKeeper bookKeeperFromFile = dataFromFile.toModelType();
        BookKeeper typicalPersonsBookKeeper = TypicalClients.getTypicalAddressBook();
        assertEquals(bookKeeperFromFile, typicalPersonsBookKeeper);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableBookKeeper dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableBookKeeper.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableBookKeeper dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableBookKeeper.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableBookKeeper.MESSAGE_DUPLICATE_CLIENT,
                dataFromFile::toModelType);
    }

}
