package ru.fivt.dostavimvse;

import org.hibernate.Session;
import ru.fivt.dostavimvse.models.Order;
import ru.fivt.dostavimvse.models.OrderStatus;
import ru.fivt.dostavimvse.models.OrderType;
import ru.fivt.dostavimvse.models.Route;

import java.util.concurrent.Callable;

/**
 * Created by akhtyamovpavel on 01.12.16.
 */
public class OrderCreateTask implements Callable<Order> {
    private Order order;

    public OrderCreateTask(Order order) {
        this.order = order;
    }

    @Override
    public Order call() {
        OptimalSolver solver = null;
        if (order.getOrderType() == OrderType.TIME) {
            solver = new OptimalTimeSolver();
        } else if (order.getOrderType() == OrderType.PRICE) {
            solver = new OptimalPriceSolver();
        } else {
            return null;
        }

        Route route = solver.buildOptimalRoute(order);
        order.setRoute(route);
        route.setOrder(order);
        order.setOrderStatus(OrderStatus.WAIT_CHANGE);

        return order;
    }

}
