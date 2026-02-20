package Com;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoutingTable {
    
    public static class Route {
        private String destination;
        private String nextHop;
        private int metric;
        private String interface_;
        private long lastUpdated;
        
        public Route(String destination, String nextHop, int metric, String interface_) {
            this.destination = destination;
            this.nextHop = nextHop;
            this.metric = metric;
            this.interface_ = interface_;
            this.lastUpdated = System.currentTimeMillis();
        }
        
        public String getDestination() { return destination; }
        public String getNextHop() { return nextHop; }
        public int getMetric() { return metric; }
        public String getInterface() { return interface_; }
        public long getLastUpdated() { return lastUpdated; }
        
        public void updateMetric(int newMetric) {
            this.metric = newMetric;
            this.lastUpdated = System.currentTimeMillis();
        }
        
        @Override
        public String toString() {
            return destination + " via " + nextHop + " metric " + metric;
        }
    }
    
    private Map<String, Route> routes;
    
    public RoutingTable() {
        routes = new HashMap<>();
    }
    
    public void addRoute(String destination, String nextHop, int metric, String interface_) {
        routes.put(destination, new Route(destination, nextHop, metric, interface_));
    }
    
    public void addOrUpdateRoute(String destination, String nextHop, int metric, String interface_) {
        Route existingRoute = routes.get(destination);
        
        if (existingRoute == null) {
            addRoute(destination, nextHop, metric, interface_);
        } else if (metric < existingRoute.getMetric()) {
            existingRoute.updateMetric(metric);
        }
    }
    
    public void removeRoute(String destination) {
        routes.remove(destination);
    }
    
    public Route getRoute(String destination) {
        return routes.get(destination);
    }
    
    public List<Route> getAllRoutes() {
        return new ArrayList<>(routes.values());
    }
    
    public int getRouteCount() {
        return routes.size();
    }
    
    public void clear() {
        routes.clear();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Routing Table:\n");
        sb.append(String.format("%-20s %-15s %-10s %-10s\n", 
            "Destination", "Next Hop", "Metric", "Interface"));
        sb.append("-".repeat(60)).append("\n");
        
        for (Route route : routes.values()) {
            sb.append(String.format("%-20s %-15s %-10d %-10s\n",
                route.getDestination(),
                route.getNextHop(),
                route.getMetric(),
                route.getInterface()));
        }
        
        return sb.toString();
    }
}