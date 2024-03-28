package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.Pair;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of orders.
 */
public class OrderListPanel extends UiPart<Region> {
    private static final String FXML = "OrderListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(OrderListPanel.class);

    @FXML
    private ListView<Pair<Person, Order>> personOrderListView;


    /**
     * Creates a {@code OrderListPanel} with the given {@code ObservableList}.
     */
    public OrderListPanel(ObservableList<Pair<Person, Order>> personOrderList) {
        super(FXML);
        personOrderListView.setItems(personOrderList);
        personOrderListView.setCellFactory(listView -> new OrderListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Order} using a {@code OrderCard}.
     */
    class OrderListViewCell extends ListCell<Pair<Person, Order>> {
        @Override
        protected void updateItem(Pair<Person, Order> order, boolean empty) {
            super.updateItem(order, empty);

            if (empty || order == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new OrderCard(order, getIndex() + 1).getRoot());
            }
        }
    }

}
