package ru.fivt.dostavimvse;

import org.hibernate.Session;
import ru.fivt.dostavimvse.models.Order;
import ru.fivt.dostavimvse.models.OrderStatus;
import ru.fivt.dostavimvse.models.Route;
import ru.fivt.dostavimvse.models.RouteLeg;

import java.time.LocalDateTime;

/**
 * Created by akhtyamovpavel on 01.12.16.
 */
public class OrderChangeLegTask implements Runnable {

    private Order order;

    public OrderChangeLegTask(Order order) {
        this.order = order;
    }

    @Override
    public void run() {
        Route route = order.getRoute();
        RouteIterator routeIterator = new RouteIterator(order, false);
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        // TODO: write current session in RouteLeg
        if (routeIterator.hasNext()) {
            RouteLeg newLeg = routeIterator.next();

            int sendTime = newLeg.getLeg().getSendTime();

            newLeg.setStartTime(LocalDateTime.now());
            newLeg.setEndTime(LocalDateTime.now().plusSeconds(sendTime));
            // Add some info for next leg
            order.setOrderStatus(OrderStatus.MOVING);

            route.setCurrentLeg(newLeg);

        } else {
            order.setOrderStatus(OrderStatus.READY);
            route.setCurrentLeg(null);
        }

//        session.saveOrUpdate(route);
        session.saveOrUpdate(order);

        session.getTransaction().commit();
        session.close();

    }
}
