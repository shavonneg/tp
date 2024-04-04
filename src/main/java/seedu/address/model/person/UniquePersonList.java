package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.order.Order;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Person#isSamePerson(Person)
 */
public class UniquePersonList implements Iterable<Person> {

    private final ObservableList<Person> internalList = FXCollections.observableArrayList();
    private final ObservableList<Person> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);
    private final ObservableList<Order> internalOrderList = FXCollections.observableArrayList();
    private final ObservableList<Order> internalUnmodifiableListOrder =
            FXCollections.unmodifiableObservableList(internalOrderList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Person toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Person toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSamePerson(editedPerson) && contains(editedPerson)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedPerson);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * Removes the respective Order object from the ObservableList as well.
     *
     * @param target       person to be removed.
     * @param editedPerson person to be added.
     * @param order        order to be added.
     */
    public void setPersonAndDeleteOrder(Person target, Person editedPerson, Order order) {
        setPerson(target, editedPerson);
        internalOrderList.remove(order);
        sortOrders();
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * Adds the Order object to the ObservableList as well.
     *
     * @param target       person to be removed.
     * @param editedPerson person to be added.
     * @param order        order to be removed.
     */
    public void setPersonAndAddOrder(Person target, Person editedPerson, Order order) {
        setPerson(target, editedPerson);
        internalOrderList.add(order);
        sortOrders();
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     *
     * @param person        person to be removed.
     * @param editedPerson  person to be added.
     * @param orderToDelete order to be removed.
     * @param orderToAdd    order to be added.
     */

    public void setPersonAndEditOrder(Person person, Person editedPerson, Order orderToDelete, Order orderToAdd) {
        requireAllNonNull(person, orderToDelete, orderToAdd);
        setPerson(person, editedPerson);
        int index = internalOrderList.indexOf(orderToDelete);
        internalOrderList.set(index, orderToAdd);
        sortOrders();
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Person toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
        internalOrderList.removeAll(toRemove.getOrdersList());
    }

    public void setPersons(UniquePersonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        requireAllNonNull(persons);
        if (!personsAreUnique(persons)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(persons);
        setOrders();
    }


    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Person> asUnmodifiableObservableList() {
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
        for (Person person : internalList) {
            creationOrderList.addAll(person.getOrdersList());
        }
        internalOrderList.setAll(creationOrderList);
    }

    private void sortOrders() {
        FXCollections.sort(internalOrderList, (order1, order2) ->
                order1.getDeadline().compareTo(order2.getDeadline()));
    }

    @Override
    public Iterator<Person> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniquePersonList)) {
            return false;
        }

        UniquePersonList otherUniquePersonList = (UniquePersonList) other;
        return internalList.equals(otherUniquePersonList.internalList);
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
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean personsAreUnique(List<Person> persons) {
        for (int i = 0; i < persons.size() - 1; i++) {
            for (int j = i + 1; j < persons.size(); j++) {
                if (persons.get(i).isSamePerson(persons.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
