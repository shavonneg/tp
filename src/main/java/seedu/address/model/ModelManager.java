package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.client.Client;
import seedu.address.model.order.Order;

/**
 * Represents the in-memory model of bookkeeper data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final BookKeeper bookKeeper;
    private final UserPrefs userPrefs;
    private final FilteredList<Client> filteredClients;
    private final FilteredList<Order> filteredOrders;


    /**
     * Initializes a ModelManager with the given bookKeeper and userPrefs.
     */
    public ModelManager(ReadOnlyBookKeeper addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with save file: " + addressBook + " and user prefs " + userPrefs);

        this.bookKeeper = new BookKeeper(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredClients = new FilteredList<>(this.bookKeeper.getClientList());
        filteredOrders = new FilteredList<>(this.bookKeeper.getOrderList());
    }

    public ModelManager() {
        this(new BookKeeper(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== BookKeeper ================================================================================

    @Override
    public ReadOnlyBookKeeper getAddressBook() {
        return bookKeeper;
    }

    @Override
    public void setAddressBook(ReadOnlyBookKeeper addressBook) {
        this.bookKeeper.resetData(addressBook);
    }

    @Override
    public boolean hasClient(Client client) {
        requireNonNull(client);
        return bookKeeper.hasClient(client);
    }

    @Override
    public void deleteClient(Client target) {
        bookKeeper.removeClient(target);
    }

    @Override
    public void addClient(Client client) {
        bookKeeper.addClient(client);
        updateFilteredClientList(PREDICATE_SHOW_ALL_CLIENTS);
    }

    @Override
    public void setClient(Client target, Client editedClient) {
        requireAllNonNull(target, editedClient);

        bookKeeper.setClient(target, editedClient);

    }

    @Override
    public void setClientAndAddOrder(Client target, Client editedClient, Order order) {
        requireAllNonNull(target, editedClient);

        bookKeeper.setClientAndAddOrder(target, editedClient, order);
    }

    @Override
    public void setClientAndDeleteOrder(Client target, Client editedClient, Order order) {
        requireAllNonNull(target, editedClient);

        bookKeeper.setClientAndDeleteOrder(target, editedClient, order);
    }

    @Override
    public void setClientAndEditOrder(Client target, Client editedClient, Order order, Order editedOrder) {
        requireAllNonNull(target, editedClient, order, editedOrder);

        bookKeeper.setClientAndEditOrder(target, editedClient, order, editedOrder);
    }


    //=========== Order ================================================================================

    /**
     * Returns an unmodifiable view of the list of {@code Order} backed by the internal list of.
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Order> getFilteredOrderList() {
        return filteredOrders;
    }

    @Override
    public void updateFilteredOrderList(Predicate<Order> predicate) {
        requireNonNull(predicate);
        filteredOrders.setPredicate(predicate);
    }

    //=========== Filtered Client List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Client} backed by the internal list of
     * {@code versionedAddressBook}.
     */
    @Override
    public ObservableList<Client> getFilteredClientList() {
        return filteredClients;
    }

    @Override
    public void updateFilteredClientList(Predicate<Client> predicate) {
        requireNonNull(predicate);
        filteredClients.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return bookKeeper.equals(otherModelManager.bookKeeper)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredClients.equals(otherModelManager.filteredClients);
    }
}
