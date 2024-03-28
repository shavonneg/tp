package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.DecimalFormat;

/**
 * Represents the amount of items for the order in the order book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAmount(String)}
 */
public class Amount {

    public static final String MESSAGE_CONSTRAINTS =
            "Amount must be a positive number";
    public final double value;

    /**
     * Constructs a {@code Amount}.
     *
     * @param value A valid amount.
     */
    public Amount(String value) {
        requireNonNull(value);
        checkArgument(isValidAmount(value), MESSAGE_CONSTRAINTS);
        this.value = truncateToTwoDecimalPlaces(Double.parseDouble(value));
    }

    /**
     * Returns true if a given double is a valid amount.
     *
     * @param test the amount to be tested
     * @return true if the amount is valid
     */
    public static boolean isValidAmount(String test) {
        return Double.parseDouble(test) > 0;
    }

    /**
     * Truncates a double value to two decimal places.
     *
     * @param value the double value to be truncated
     * @return the truncated value
     */
    private double truncateToTwoDecimalPlaces(double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(value));
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Amount)) {
            return false;
        }

        Amount otherAmount = (Amount) other;
        return value == otherAmount.value;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }
}
