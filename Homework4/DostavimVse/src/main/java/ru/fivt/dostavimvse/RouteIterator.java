package ru.fivt.dostavimvse;

import ru.fivt.dostavimvse.models.Order;
import ru.fivt.dostavimvse.models.Route;
import ru.fivt.dostavimvse.models.RouteLeg;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Created by akhtyamovpavel on 02.12.16.
 */

public class RouteIterator implements Iterator<RouteLeg>{
    private Set<RouteLeg> routeLegs;
    private int currentVertex;
    private int endVertex;

    public RouteIterator(Order order, boolean fromStart) {
        Route route = order.getRoute();
        this.routeLegs = route.getRouteLegs();
        if (fromStart) {
            this.currentVertex = order.getStartVertex();
        } else {
            if (route.getCurrentLeg() == null) {
                this.currentVertex = order.getStartVertex();
            } else {
                this.currentVertex = route.getCurrentLeg().getLeg().getEndVertex();
            }
        }
        this.endVertex = order.getEndVertex();
    }

    @Override
    public boolean hasNext() {
        return currentVertex != endVertex;
    }

    @Override
    public RouteLeg next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Route iterator finished the end");
        }
        for (RouteLeg routeLeg: routeLegs) {
            if (routeLeg.getLeg().getStartVertex() == currentVertex) {
                currentVertex = routeLeg.getLeg().getEndVertex();
                return routeLeg;
            }
        }
        throw new NoSuchElementException("Failed to found next vertex");
    }
}
