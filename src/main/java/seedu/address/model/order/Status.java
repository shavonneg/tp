package seedu.address.model.order;


import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;

/**
 * Represents an Order's status in the order book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 */
public class Status {

    public static final String MESSAGE_CONSTRAINTS =
            "Status can only be either 'pending', 'completed' or 'canceled'";

    private final StatusEnum statusEnum;

    /**
     * Constructs a {@code Status}.
     *
     * @param status A valid status.
     */
    public Status(String status) {
        requireNonNull(status);
        checkArgument(isValidStatus(status), MESSAGE_CONSTRAINTS);
        this.statusEnum = StatusEnum.valueOf(status.toUpperCase());
    }

    /**
     * Returns true if a given string is a valid status.
     */
    public static boolean isValidStatus(String test) {
        return !test.isBlank()
                && !test.isEmpty()
                && Arrays.stream(StatusEnum.values())
                .anyMatch(status -> status.name().equalsIgnoreCase(test));
    }

    /**
     * Returns the status enum value.
     */
    public StatusEnum getStatusEnum() {
        return statusEnum;
    }

    @Override
    public String toString() {
        return statusEnum.name();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Status)) {
            return false;
        }

        Status otherStatus = (Status) other;
        return otherStatus.statusEnum.equals(this.statusEnum);
    }

    @Override
    public int hashCode() {
        return statusEnum.hashCode();
    }

    /**
     * Represents the statuses an order can take in the order book.
     */
    public enum StatusEnum {
        PENDING, COMPLETED, CANCELED
    }


}
