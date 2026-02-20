package Com;


import java.util.ArrayList;
import java.util.List;

public class RIPPacket {
    private Router sourceRouter;
    private List<RoutingTable.Route> routes;
    private int command; // 1 = request, 2 = response
    private int version; // RIPv1 or RIPv2
    private long timestamp;
    
    public RIPPacket(Router sourceRouter, List<RoutingTable.Route> routes) {
        this.sourceRouter = sourceRouter;
        this.routes = new ArrayList<>(routes);
        this.command = 2; // Response
        this.version = 2; // RIPv2
        this.timestamp = System.currentTimeMillis();
    }
    
    public RIPPacket(Router sourceRouter, int command) {
        this.sourceRouter = sourceRouter;
        this.routes = new ArrayList<>();
        this.command = command;
        this.version = 2;
        this.timestamp = System.currentTimeMillis();
    }
    
    // Getters
    public Router getSourceRouter() { return sourceRouter; }
    public List<RoutingTable.Route> getRoutes() { return routes; }
    public int getCommand() { return command; }
    public int getVersion() { return version; }
    public long getTimestamp() { return timestamp; }
    
    // Add route to packet
    public void addRoute(RoutingTable.Route route) {
        routes.add(route);
    }
    
    // Check if packet is expired (30 seconds timeout)
    public boolean isExpired() {
        return (System.currentTimeMillis() - timestamp) > 30000;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("RIP Packet [command=").append(command == 1 ? "REQUEST" : "RESPONSE");
        sb.append(", version=").append(version);
        sb.append(", routes=").append(routes.size());
        sb.append("]");
        return sb.toString();
    }
}