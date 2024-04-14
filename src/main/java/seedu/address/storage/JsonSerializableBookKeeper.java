package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.BookKeeper;
import seedu.address.model.ReadOnlyBookKeeper;
import seedu.address.model.client.Client;

/**
 * An Immutable BookKeeper that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableBookKeeper {

    public static final String MESSAGE_DUPLICATE_CLIENT = "Client list contains duplicate client(s).";

    private final List<JsonAdaptedClient> clients = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableBookKeeper} with the given persons.
     */
    @JsonCreator
    public JsonSerializableBookKeeper(@JsonProperty("clients") List<JsonAdaptedClient> clients) {
        this.clients.addAll(clients);
    }

    /**
     * Converts a given {@code ReadOnlyBookKeeper} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableBookKeeper}.
     */
    public JsonSerializableBookKeeper(ReadOnlyBookKeeper source) {
        clients.addAll(source.getClientList().stream().map(JsonAdaptedClient::new).collect(Collectors.toList()));
    }

    /**
     * Converts this bookkeeper into the model's {@code BookKeeper} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public BookKeeper toModelType() throws IllegalValueException {
        BookKeeper bookKeeper = new BookKeeper();
        for (JsonAdaptedClient jsonAdaptedClient : clients) {
            Client client = jsonAdaptedClient.toModelType();
            if (bookKeeper.hasClient(client)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CLIENT);
            }
            bookKeeper.addClient(client);
        }
        return bookKeeper;
    }

}
