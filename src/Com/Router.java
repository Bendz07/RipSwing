package Com;

import java.util.ArrayList;
import java.util.List;

public class Router {
    private int id;
    private String name;
    private String ipAddress;
    private List<Router> neighbors;
    private RoutingTable routingTable;
    private boolean isActive;
    
    public Router(int id, String name, String ipAddress) {
        this.id = id;
        this.name = name;
        this.ipAddress = ipAddress;
        this.neighbors = new ArrayList<>();
        this.routingTable = new RoutingTable();
        this.isActive = true;
        initializeRoutingTable();
    }
    
    private void initializeRoutingTable() {
        // Add directly connected networks
        routingTable.addRoute(ipAddress + "/24", "Direct", 1, "eth0");
    }
    
    public void addNeighbor(Router neighbor) {
        if (!neighbors.contains(neighbor)) {
            neighbors.add(neighbor);
        }
    }
    
    public void removeNeighbor(Router neighbor) {
        neighbors.remove(neighbor);
    }
    
    public void sendRIPUpdate() {
        if (!isActive) return;
        
        RIPPacket packet = new RIPPacket(this, routingTable.getAllRoutes());
        
        for (Router neighbor : neighbors) {
            receiveRIPUpdate(neighbor, packet);
        }
    }
    
    public void receiveRIPUpdate(Router sourceRouter, RIPPacket packet) {
        if (!isActive) return;
        
        System.out.println("Router " + name + " received RIP update from " + sourceRouter.getName());
        
        // Process RIP update (simplified)
        for (RoutingTable.Route route : packet.getRoutes()) {
            int newMetric = route.getMetric() + 1; // Add one hop
            
            if (newMetric <= 15) { // RIP max metric is 15
                routingTable.addOrUpdateRoute(
                    route.getDestination(),
                    sourceRouter.getIpAddress(),
                    newMetric,
                    "eth1"
                );
            }
        }
    }
    
    // Getters and setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getIpAddress() { return ipAddress; }
    public int getNeighborCount() { return neighbors.size(); }
    public RoutingTable getRoutingTable() { return routingTable; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}