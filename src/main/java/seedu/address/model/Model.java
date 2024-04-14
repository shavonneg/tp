package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.client.Client;
import seedu.address.model.order.Order;


/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true.
     */
    Predicate<Client> PREDICATE_SHOW_ALL_CLIENTS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true.
     */
    Predicate<Order> PREDICATE_SHOW_ALL_ORDERS = unused -> true;

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' bookkeeper file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' bookkeeper file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Returns BookKeeper.
     */
    ReadOnlyBookKeeper getAddressBook();

    /**
     * Replaces bookkeeper data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyBookKeeper addressBook);

    /**
     * Returns true if a client with the same identity as {@code client} exists in bookkeeper.
     */
    boolean hasClient(Client client);

    /**
     * Deletes the given client.
     * The client must exist in bookkeeper.
     */
    void deleteClient(Client target);

    /**
     * Adds the given client.
     * {@code client} must not already exist in bookkeeper.
     */
    void addClient(Client client);

    /**
     * Replaces the given client {@code target} with {@code editedClient}.
     * {@code target} must exist in bookkeeper.
     * The client identity of {@code editedClient} must not be the same as another existing client in bookkeeper.
     */
    void setClient(Client target, Client editedClient);

    void setClientAndAddOrder(Client target, Client editedClient, Order order);

    void setClientAndDeleteOrder(Client target, Client editedClient, Order order);

    void setClientAndEditOrder(Client target, Client editedClient, Order order, Order editedOrder);

    /**
     * Returns an unmodifiable view of the filtered client list.
     */
    ObservableList<Client> getFilteredClientList();

    /**
     * Returns an unmodifiable view of the filtered order list.
     */
    ObservableList<Order> getFilteredOrderList();

    /**
     * Updates the filter of the filtered client list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredClientList(Predicate<Client> predicate);

    /**
     * Updates the filter of the filtered order list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredOrderList(Predicate<Order> predicate);
}
