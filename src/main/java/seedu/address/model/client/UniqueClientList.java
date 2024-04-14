package seedu.address.model.client;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.client.exceptions.ClientNotFoundException;
import seedu.address.model.client.exceptions.DuplicateClientException;
import seedu.address.model.order.Order;

/**
 * A list of clients that enforces uniqueness between its elements and does not allow nulls.
 * A client is considered unique by comparing using {@code Client#isSameClient(Client)}. As such, adding and updating of
 * clients uses Client#isSameClient(Client) for equality so as to ensure that the client being added or updated is
 * unique in terms of identity in the UniqueClientList. However, the removal of a client uses Client#equals(Object) so
 * as to ensure that the client with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Client#isSameClient(Client)
 */
public class UniqueClientList implements Iterable<Client> {

    private final ObservableList<Client> internalList = FXCollections.observableArrayList();
    private final ObservableList<Client> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);
    private final ObservableList<Order> internalOrderList = FXCollections.observableArrayList();
    private final ObservableList<Order> internalUnmodifiableListOrder =
            FXCollections.unmodifiableObservableList(internalOrderList);

    /**
     * Returns true if the list contains an equivalent client as the given argument.
     */
    public boolean contains(Client toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameClient);
    }

    /**
     * Adds a client to the list.
     * The client must not already exist in the list.
     */
    public void add(Client toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateClientException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the client {@code target} in the list with {@code editedClient}.
     * {@code target} must exist in the list.
     * The client identity of {@code editedClient} must not be the same as another existing client in the list.
     */
    public void setClient(Client target, Client editedClient) {
        requireAllNonNull(target, editedClient);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ClientNotFoundException();
        }

        if (!target.isSameClient(editedClient) && contains(editedClient)) {
            throw new DuplicateClientException();
        }

        internalList.set(index, editedClient);

        // Needed to refresh the OrderList with updated Client Names
        internalOrderList.setAll(new ArrayList<>(internalOrderList));
    }

    /**
     * Replaces the client {@code target} in the list with {@code editedClient}.
     * Removes the respective Order object from the ObservableList as well.
     *
     * @param target       client to be removed.
     * @param editedClient client to be added.
     * @param order        order to be added.
     */
    public void setClientAndDeleteOrder(Client target, Client editedClient, Order order) {
        setClient(target, editedClient);
        internalOrderList.remove(order);
        sortOrders();
    }

    /**
     * Replaces the client {@code target} in the list with {@code editedClient}.
     * Adds the Order object to the ObservableList as well.
     *
     * @param target       client to be removed.
     * @param editedClient client to be added.
     * @param order        order to be removed.
     */
    public void setClientAndAddOrder(Client target, Client editedClient, Order order) {
        setClient(target, editedClient);
        internalOrderList.add(order);
        sortOrders();
    }

    /**
     * Replaces the client {@code target} in the list with {@code editedClient}.
     *
     * @param client        client to be removed.
     * @param editedClient  client to be added.
     * @param orderToDelete order to be removed.
     * @param orderToAdd    order to be added.
     */

    public void setClientAndEditOrder(Client client, Client editedClient, Order orderToDelete, Order orderToAdd) {
        requireAllNonNull(client, orderToDelete, orderToAdd);
        setClient(client, editedClient);
        int index = internalOrderList.indexOf(orderToDelete);
        internalOrderList.set(index, orderToAdd);
        sortOrders();
    }

    /**
     * Removes the equivalent client from the list.
     * The client must exist in the list.
     */
    public void remove(Client toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ClientNotFoundException();
        }
        internalOrderList.removeAll(toRemove.getOrdersList());
    }

    public void setClients(UniqueClientList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code clients}.
     * {@code clients} must not contain duplicate clients.
     */
    public void setClients(List<Client> clients) {
        requireAllNonNull(clients);
        if (!clientsAreUnique(clients)) {
            throw new DuplicateClientException();
        }

        internalList.setAll(clients);
        setOrders();
    }


    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Client> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }


    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Order> asUnmodifiableObservableListOrders() {
        return internalUnmodifiableListOrder;
    }

    private void setOrders() {
        List<Order> creationOrderList = new ArrayList<>();
        for (Client client : internalList) {
            creationOrderList.addAll(client.getOrdersList());
        }
        internalOrderList.setAll(creationOrderList);
    }

    private void sortOrders() {
        FXCollections.sort(internalOrderList, (order1, order2) ->
                order1.getDeadline().compareTo(order2.getDeadline()));
    }

    @Override
    public Iterator<Client> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueClientList)) {
            return false;
        }

        UniqueClientList otherUniqueClientList = (UniqueClientList) other;
        return internalList.equals(otherUniqueClientList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code clients} contains only unique clients.
     */
    private boolean clientsAreUnique(List<Client> clients) {
        for (int i = 0; i < clients.size() - 1; i++) {
            for (int j = i + 1; j < clients.size(); j++) {
                if (clients.get(i).isSameClient(clients.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
