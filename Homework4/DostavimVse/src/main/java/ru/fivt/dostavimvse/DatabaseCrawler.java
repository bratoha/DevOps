package ru.fivt.dostavimvse;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import ru.fivt.dostavimvse.models.Order;
import ru.fivt.dostavimvse.models.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by akhtyamovpavel on 01.12.16.
 */
public class DatabaseCrawler implements Runnable {


    @Override
    public void run() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();

        Operator operator = Operator.getInstance();

        Query query = session.createQuery("SELECT o FROM Order o INNER JOIN o.route AS r LEFT JOIN r.currentLeg AS  rl WHERE (o.orderStatus = :order_status " +
                "AND (r.currentLeg is NULL OR rl.endTime < :end_time)) OR (o.orderStatus = :order_status_waiting " +
                "AND r.currentLeg is NULL)");
        query.setParameter("order_status", OrderStatus.MOVING);
        query.setParameter("end_time", LocalDateTime.now());
        query.setParameter("order_status_waiting", OrderStatus.WAIT_CHANGE);


        List<Order> orders = (List<Order>)query.list();

        orders.forEach(operator::changeRouteLeg);

        System.out.println("Session closed");
        session.close();
    }
}
