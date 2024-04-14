package seedu.address.logic.commands.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ORDERS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.Pair;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.order.Order;

/**
 * Removes an existing order in Bookkeeper.
 */
public class DeleteOrderCommand extends Command {
    public static final String MESSAGE_SUCCESS = "Deleted Order: %1$s";

    public static final String COMMAND_WORD = "deleteOrder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the order identified by the index number used in the displayed order list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " index";

    public static final String MESSAGE_DELETE_ORDER_FAILURE = "Failed to delete Order!";

    private final Index targetIndex;


    /**
     * Creates an DeleteOrderCommand to delete the specified {@code Order}.
     */
    public DeleteOrderCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    private Pair<Client, Client> getEditedClient(List<Client> clientList, Order orderToDelete) throws CommandException {

        for (Client client : clientList) {
            if (client.getOrders().contains(orderToDelete)) {
                Client editedClient = client.removeOrder(orderToDelete);
                return new Pair<>(client, editedClient);
            }
        }
        throw new CommandException(MESSAGE_DELETE_ORDER_FAILURE);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Order> lastShownOrderList = model.getFilteredOrderList();

        if (targetIndex.getZeroBased() >= lastShownOrderList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Order orderToDelete = lastShownOrderList.get(targetIndex.getZeroBased());

        List<Client> clientList = model.getFilteredClientList();
        Pair<Client, Client> pair = getEditedClient(clientList, orderToDelete);
        Client client = pair.getFirst();
        Client editedClient = pair.getSecond();

        model.setClientAndDeleteOrder(client, editedClient, orderToDelete);
        model.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(orderToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteOrderCommand)) {
            return false;
        }

        DeleteOrderCommand otherAddCommand = (DeleteOrderCommand) other;
        return targetIndex.equals(otherAddCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", targetIndex)
                .toString();
    }
}
