package seedu.address.model.client;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClients.ALICE;
import static seedu.address.testutil.TypicalClients.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.client.exceptions.ClientNotFoundException;
import seedu.address.model.client.exceptions.DuplicateClientException;
import seedu.address.model.order.Order;
import seedu.address.testutil.ClientBuilder;
import seedu.address.testutil.OrderBuilder;

public class UniqueClientListTest {

    private final UniqueClientList uniqueClientList = new UniqueClientList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClientList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueClientList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueClientList.add(ALICE);
        assertTrue(uniqueClientList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueClientList.add(ALICE);
        Client editedAlice = new ClientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueClientList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClientList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueClientList.add(ALICE);
        assertThrows(DuplicateClientException.class, () -> uniqueClientList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClientList.setClient(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClientList.setClient(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(ClientNotFoundException.class, () -> uniqueClientList.setClient(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueClientList.add(ALICE);
        uniqueClientList.setClient(ALICE, ALICE);
        UniqueClientList expectedUniqueClientList = new UniqueClientList();
        expectedUniqueClientList.add(ALICE);
        assertEquals(expectedUniqueClientList, uniqueClientList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueClientList.add(ALICE);
        Client editedAlice = new ClientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueClientList.setClient(ALICE, editedAlice);
        UniqueClientList expectedUniqueClientList = new UniqueClientList();
        expectedUniqueClientList.add(editedAlice);
        assertEquals(expectedUniqueClientList, uniqueClientList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueClientList.add(ALICE);
        uniqueClientList.setClient(ALICE, BOB);
        UniqueClientList expectedUniqueClientList = new UniqueClientList();
        expectedUniqueClientList.add(BOB);
        assertEquals(expectedUniqueClientList, uniqueClientList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueClientList.add(ALICE);
        uniqueClientList.add(BOB);
        assertThrows(DuplicateClientException.class, () -> uniqueClientList.setClient(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClientList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(ClientNotFoundException.class, () -> uniqueClientList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueClientList.add(ALICE);
        uniqueClientList.remove(ALICE);
        UniqueClientList expectedUniqueClientList = new UniqueClientList();
        assertEquals(expectedUniqueClientList, uniqueClientList);
    }

    @Test
    public void addOrder_existingPerson_addOrder() {
        Order order = new OrderBuilder().build();
        Client client = new ClientBuilder().build();
        uniqueClientList.add(client);
        Client editedClient = client.addOrder(order);
        uniqueClientList.setClientAndAddOrder(client, editedClient, order);
        assertEquals(1, uniqueClientList.asUnmodifiableObservableListOrders().size());
    }

    @Test
    public void removeOrder_existingPersonAndOrder_removeOrder() {
        Order order = new OrderBuilder().build();
        Client client = new ClientBuilder().build();
        uniqueClientList.add(client);
        Client editedClient = client.addOrder(order);
        uniqueClientList.setClientAndAddOrder(client, editedClient, order);
        uniqueClientList.setClientAndDeleteOrder(editedClient, client, order);
        assertEquals(0, uniqueClientList.asUnmodifiableObservableListOrders().size());
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClientList.setClients((UniqueClientList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueClientList.add(ALICE);
        UniqueClientList expectedUniqueClientList = new UniqueClientList();
        expectedUniqueClientList.add(BOB);
        uniqueClientList.setClients(expectedUniqueClientList);
        assertEquals(expectedUniqueClientList, uniqueClientList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClientList.setClients((List<Client>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueClientList.add(ALICE);
        List<Client> clientList = Collections.singletonList(BOB);
        uniqueClientList.setClients(clientList);
        UniqueClientList expectedUniqueClientList = new UniqueClientList();
        expectedUniqueClientList.add(BOB);
        assertEquals(expectedUniqueClientList, uniqueClientList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Client> listWithDuplicateClients = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateClientException.class, () -> uniqueClientList.setClients(listWithDuplicateClients));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueClientList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueClientList.asUnmodifiableObservableList().toString(), uniqueClientList.toString());
    }

    @Test
    public void setPersonsAndAddOrder_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClientList.setClientAndAddOrder(ALICE, null,
                null));
    }

    @Test
    public void setPersonsAndAddOrder_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClientList.setClientAndAddOrder(null,
                null, null));
    }

    @Test
    public void setPersonAndAddOrder_validPersonAndOrder_success() {
        UniqueClientList uniqueClientList = new UniqueClientList();
        Client originalClient = new ClientBuilder(ALICE).build();
        Client editedClient = new ClientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        OrderBuilder orderBuilder = new OrderBuilder();
        Order newOrder = orderBuilder.build();

        uniqueClientList.add(originalClient);
        assertDoesNotThrow(() -> uniqueClientList.setClientAndAddOrder(originalClient, editedClient, newOrder));

        assertTrue(uniqueClientList.contains(editedClient));
    }

    @Test
    public void setPersonsAndDeleteOrder_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClientList.setClientAndDeleteOrder(BOB, null,
                null));
    }

    @Test
    public void setPersonsAndDeleteOrder_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClientList.setClientAndDeleteOrder(null,
                null, null));
    }

    @Test
    public void setPersonAndDeleteOrder_orderDoesNotExist_personUnchanged() {
        UniqueClientList uniqueClientList = new UniqueClientList();
        Client originalClient = new ClientBuilder(ALICE).build();
        Client editedClient = new ClientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        OrderBuilder orderBuilder = new OrderBuilder();
        Order newOrder = orderBuilder.build();

        uniqueClientList.add(originalClient);

        assertDoesNotThrow(() -> uniqueClientList.setClientAndDeleteOrder(originalClient, editedClient, newOrder));
    }

    @Test
    public void testEquals_sameInput() {
        UniqueClientList uniqueClientList = new UniqueClientList();
        assertEquals(uniqueClientList, uniqueClientList);
    }

    @Test
    public void testEquals_sameOutput() {
        UniqueClientList list1 = new UniqueClientList();
        UniqueClientList list2 = new UniqueClientList();
        list1.add(ALICE);
        list2.add(ALICE);
        assertEquals(list1.equals(list2), list2.equals(list1));
    }

    @Test
    public void testHashCode() {
        UniqueClientList uniqueClientList = new UniqueClientList();
        int initialHashCode = uniqueClientList.hashCode();
        for (int i = 0; i < 10; i++) {
            assertEquals(initialHashCode, uniqueClientList.hashCode());
        }
    }

    public void equals() {
        assertTrue(uniqueClientList.equals(uniqueClientList));
        UniqueClientList uniqueClientList2 = new UniqueClientList();
        uniqueClientList2.add(new ClientBuilder().build());
        assertFalse(uniqueClientList.equals(uniqueClientList2));
    }
}
