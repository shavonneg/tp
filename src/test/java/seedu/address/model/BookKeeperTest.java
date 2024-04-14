package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClients.ALICE;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.client.Client;
import seedu.address.model.client.exceptions.DuplicateClientException;
import seedu.address.model.order.Order;
import seedu.address.testutil.ClientBuilder;

public class BookKeeperTest {

    private final BookKeeper bookKeeper = new BookKeeper();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), bookKeeper.getClientList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> bookKeeper.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        BookKeeper newData = getTypicalAddressBook();
        bookKeeper.resetData(newData);
        assertEquals(newData, bookKeeper);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two clients with the same identity fields
        Client editedAlice = new ClientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Client> newClients = Arrays.asList(ALICE, editedAlice);
        BookKeeperStub newData = new BookKeeperStub(newClients);

        assertThrows(DuplicateClientException.class, () -> bookKeeper.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> bookKeeper.hasClient(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(bookKeeper.hasClient(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        bookKeeper.addClient(ALICE);
        assertTrue(bookKeeper.hasClient(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        bookKeeper.addClient(ALICE);
        Client editedAlice = new ClientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(bookKeeper.hasClient(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> bookKeeper.getClientList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = BookKeeper.class.getCanonicalName() + "{clients=" + bookKeeper.getClientList() + "}";
        assertEquals(expected, bookKeeper.toString());
    }

    /**
     * A stub ReadOnlyBookKeeper whose clients list can violate interface constraints.
     */
    private static class BookKeeperStub implements ReadOnlyBookKeeper {
        private final ObservableList<Client> clients = FXCollections.observableArrayList();
        private final ObservableList<Order> orders = FXCollections.observableArrayList();

        BookKeeperStub(Collection<Client> clients) {
            this.clients.setAll(clients);
        }

        @Override
        public ObservableList<Client> getClientList() {
            return clients;
        }

        @Override
        public ObservableList<Order> getOrderList() {
            return orders;
        }

    }

}
