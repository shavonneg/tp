package seedu.address.logic.commands.client;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.BookKeeper;
import seedu.address.model.Model;

/**
 * Clears the bookkeeper.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "BookKeeper has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new BookKeeper());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
