package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.client.Client;
import seedu.address.model.client.UniqueClientList;
import seedu.address.model.order.Order;

/**
 * Wraps all data at the application level.
 * Duplicates are not allowed (by .isSameClient comparison)
 */
public class BookKeeper implements ReadOnlyBookKeeper {
    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    private final UniqueClientList clients;

    {
        clients = new UniqueClientList();
    }

    public BookKeeper() {
    }

    /**
     * Creates BookKeeper using the Client in the {@code toBeCopied}.
     */
    public BookKeeper(ReadOnlyBookKeeper toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the client list with {@code clients}.
     * {@code clients} must not contain duplicate clients.
     */
    public void setClients(List<Client> clients) {
        this.clients.setClients(clients);
    }

    /**
     * Resets the existing data of this {@code BookKeeper} with {@code newData}.
     */
    public void resetData(ReadOnlyBookKeeper newData) {
        requireNonNull(newData);

        setClients(newData.getClientList());
    }

    //// client-level operations

    /**
     * Returns true if a client with the same identity as {@code client} exists in Bookkeeper's client list.
     */
    public boolean hasClient(Client client) {
        requireNonNull(client);
        return clients.contains(client);
    }

    /**
     * Adds a client to bookkeeper.
     * The client must not already exist in Bookkeeper's client list.
     */
    public void addClient(Client p) {
        clients.add(p);
    }


    /**
     * Replaces the given client {@code target} in the list with {@code editedClient}.
     * {@code target} must exist in Bookkeeper's client list.
     * The client identity of {@code editedClient} must not be the same as another existing client in bookkeeper.
     */
    public void setClient(Client target, Client editedClient) {
        requireNonNull(editedClient);
        clients.setClient(target, editedClient);
    }

    /**
     * Replaces the given client {@code target} in the list with {@code editedClient} and adds {@code order}
     * to the OrderList.
     * This method allows updating of the OrderList with the newly added Order.
     * {@code target} must exist in Bookkeeper's client list.
     * The client identity of {@code editedOrder} must not be the same as another existing client in the address book.
     */
    public void setClientAndAddOrder(Client target, Client editedClient, Order order) {
        requireNonNull(editedClient);
        clients.setClientAndAddOrder(target, editedClient, order);
    }


    /**
     * Replaces the given client {@code target} in the list with {@code editedClient} and deletes {@code order}
     * from the OrderList.
     * This method allows updating of the OrderList with the newly added Order.
     * {@code target} must exist in the Bookkeeper's client list.
     * The client identity of {@code editedOrder} must not be the same as another existing client in the address book.
     */
    public void setClientAndDeleteOrder(Client target, Client editedClient, Order order) {
        requireNonNull(editedClient);
        clients.setClientAndDeleteOrder(target, editedClient, order);
    }

    /**
     * Replaces the given client {@code target} in the list with {@code editedClient} and replaces {@code order}
     * with {@code editedOrder} in the OrderList.
     * This method allows updating of the OrderList with the newly added Order.
     * {@code target} must exist in Bookkeeper's client list.
     * The client identity of {@code editedOrder} must not be the same as another existing client in the address book.
     */
    public void setClientAndEditOrder(Client target, Client editedClient, Order order, Order editedOrder) {
        requireNonNull(editedOrder);
        clients.setClientAndEditOrder(target, editedClient, order, editedOrder);
    }

    /**
     * Removes {@code key} from this {@code BookKeeper}.
     * {@code key} must exist in Bookkeeper's client list.
     */
    public void removeClient(Client key) {
        clients.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("clients", clients)
                .toString();
    }

    @Override
    public ObservableList<Client> getClientList() {
        return clients.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Order> getOrderList() {
        return clients.asUnmodifiableObservableListOrders();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BookKeeper)) {
            return false;
        }

        BookKeeper otherBookKeeper = (BookKeeper) other;
        return clients.equals(otherBookKeeper.clients);
    }

    @Override
    public int hashCode() {
        return clients.hashCode();
    }

}
