package ru.fivt.dostavimvse;

import org.hibernate.Query;
import org.hibernate.Session;
import ru.fivt.dostavimvse.models.Leg;

import java.util.List;

/**
 * Created by akhtyamovpavel on 30.11.16.
 */
public class Graph {
    private static Graph ourInstance = new Graph();

    public static Graph getInstance() {
        return ourInstance;
    }

    public List<Leg> legs = null;

    private Graph() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Query query = session.createQuery("FROM Leg");
        legs = (List<Leg>)query.list();
    }

    public List<Leg> getLegs() {
        return legs;
    }
}
