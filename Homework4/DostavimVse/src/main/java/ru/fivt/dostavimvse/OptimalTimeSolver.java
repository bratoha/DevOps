package ru.fivt.dostavimvse;

import org.springframework.data.domain.Sort;
import ru.fivt.dostavimvse.models.Leg;
import ru.fivt.dostavimvse.models.Order;
import ru.fivt.dostavimvse.models.Route;
import ru.fivt.dostavimvse.models.RouteLeg;

import java.util.*;

/**
 * Created by akhtyamovpavel on 30.11.16.
 */

class SortTimeVertex implements Comparable {
    private int fromVertex;
    private double cost;

    public static final Double EPS = 1e-7;

    public int getFromVertex() {
        return fromVertex;
    }

    public void setFromVertex(int fromVertex) {
        this.fromVertex = fromVertex;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    SortTimeVertex(int fromVertex, double cost) {
        this.fromVertex = fromVertex;
        this.cost = cost;
    }

    @Override
    public int compareTo(Object o) {
        SortTimeVertex other = (SortTimeVertex)o;
        if (cost + EPS < other.cost) {
            return -1;
        } else if (cost > other.cost + EPS) {
            return 1;
        }
        if (fromVertex < other.fromVertex) {
            return -1;
        } else if (fromVertex > other.fromVertex) {
            return 1;
        } else {
            return 0;
        }
    }
}

public class OptimalTimeSolver implements OptimalSolver {
    @Override
    public Route buildOptimalRoute(Order order) {
        int startVertex = order.getStartVertex();
        int endVertex = order.getEndVertex();


        List<Leg> legs = Graph.getInstance().getLegs();

        ArrayList<LinkedList<Leg>> legsMatrix = new ArrayList<>();
        int maxVertex = 0;
        for (Leg leg: legs) {
            int legStartVertex = leg.getStartVertex();
            int legEndVertex = leg.getEndVertex();
            if (legStartVertex > maxVertex) {
                maxVertex = legStartVertex;
            }
            if (legEndVertex > maxVertex) {
                maxVertex = legEndVertex;
            }
        }

        for (int index = 0; index <= maxVertex; ++index) {
            legsMatrix.add(new LinkedList<>());
        }

        for (Leg leg: legs) {
            int legStartVertex = leg.getStartVertex();
            legsMatrix.get(legStartVertex).add(leg);
        }

        Double[] distances = new Double[maxVertex + 1];
        Boolean[] usedVertices = new Boolean[maxVertex + 1];
//        ArrayList<Boolean> usedVertices = new ArrayList<>(maxVertex + 1);
        for (int index = 0; index <= maxVertex; ++index) {
            if (index != startVertex) {
                distances[index] = Double.MAX_VALUE;
            } else {
                distances[index] = 0.0;
            }
            usedVertices[index] = false;
        }

        TreeSet<SortTimeVertex> sortVertices = new TreeSet<>();
        sortVertices.add(new SortTimeVertex(startVertex, 0.0));
        usedVertices[startVertex] = true;

        ArrayList<Leg> answerLegs = new ArrayList<>();
        for (int index = 0; index <= maxVertex; ++index) {
            answerLegs.add(null);
        }

        for (int index = 0; index <= maxVertex; ++index) {
            if (sortVertices.isEmpty()) {
                continue;
            }
            SortTimeVertex topVertex = sortVertices.first();
            int currentVertex = topVertex.getFromVertex();
            sortVertices.remove(topVertex);
            usedVertices[currentVertex] = true;

            for (Leg outputLeg: legsMatrix.get(currentVertex)) {
                double cost = getLegCost(order, outputLeg);
                double updatedCost = distances[outputLeg.getStartVertex()] + cost;
                double outputCost = distances[outputLeg.getEndVertex()];
                int toVertex = outputLeg.getEndVertex();
                if (cost == Double.MAX_VALUE) {
                    continue;
                }
                if (!usedVertices[toVertex] && updatedCost + cost + SortTimeVertex.EPS < outputCost) {

                    sortVertices.remove(new SortTimeVertex(outputLeg.getEndVertex(), outputCost));
                    distances[toVertex] = updatedCost;
                    sortVertices.add(new SortTimeVertex(outputLeg.getEndVertex(), updatedCost));
                    answerLegs.set(toVertex, outputLeg);
                }
            }
        }



        Route route = new Route();

        if (distances[endVertex] == Double.MAX_VALUE) {
            return null;
        }
        ArrayList<RouteLeg> routeWay = new ArrayList<>();

        int currentVertex = endVertex;
        while (currentVertex != startVertex) {
            RouteLeg leg = new RouteLeg();
            Leg answerLeg = answerLegs.get(currentVertex);
            leg.setLeg(answerLeg);
            routeWay.add(leg);
            leg.setRoute(route);
            currentVertex = answerLeg.getStartVertex();
        }


        // TODO: write this code in normal way
        route.setRouteLegs(new HashSet<>(routeWay));
        return route;
    }

    @Override
    public double getLegCost(Order order, Leg leg) {
        double weight = order.getOverallWeight();
        if (weight > leg.getMaxWeight()) {
            return Double.MAX_VALUE;
        }
        return leg.getSendTime(); // stub
    }
}
