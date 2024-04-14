package seedu.address.logic.commands.order;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.order.Deadline;
import seedu.address.model.order.OrderDate;
import seedu.address.model.order.Price;
import seedu.address.model.order.Remark;
import seedu.address.model.order.Status;

/**
 * Stores the details to edit the order with. Each non-empty field value will replace the
 * corresponding field value of the order.
 */
public class EditOrderDescriptor {
    private OrderDate orderDate;
    private Deadline deadline;
    private Price price;
    private Remark remark;
    private Status status;

    public EditOrderDescriptor() {
    }

    /**
     * Copy constructor.
     * A defensive copy of {@code tags} is used internally.
     */
    public EditOrderDescriptor(EditOrderDescriptor toCopy) {
        setOrderDate(toCopy.orderDate);
        setDeadline(toCopy.deadline);
        setPrice(toCopy.price);
        setRemark(toCopy.remark);
        setStatus(toCopy.status);
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(orderDate, deadline, price, remark, status);
    }

    public Optional<OrderDate> getOrderDate() {
        return Optional.ofNullable(orderDate);
    }

    public void setOrderDate(OrderDate orderDate) {
        this.orderDate = orderDate;
    }

    public Optional<Deadline> getDeadline() {
        return Optional.ofNullable(deadline);
    }

    public void setDeadline(Deadline deadline) {
        this.deadline = deadline;
    }

    public Optional<Price> getPrice() {
        return Optional.ofNullable(price);
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Optional<Remark> getRemark() {
        return Optional.ofNullable(remark);
    }

    public void setRemark(Remark remark) {
        this.remark = remark;
    }

    public Optional<Status> getStatus() {
        return Optional.ofNullable(status);
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditOrderDescriptor)) {
            return false;
        }

        EditOrderDescriptor otherEditOrderDescriptor = (EditOrderDescriptor) other;
        return Objects.equals(orderDate, otherEditOrderDescriptor.orderDate)
                && Objects.equals(deadline, otherEditOrderDescriptor.deadline)
                && Objects.equals(price, otherEditOrderDescriptor.price)
                && Objects.equals(remark, otherEditOrderDescriptor.remark)
                && Objects.equals(status, otherEditOrderDescriptor.status);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("orderDate", orderDate)
                .add("deadline", deadline)
                .add("price", price)
                .add("remark", remark)
                .add("status", status)
                .toString();
    }
}
