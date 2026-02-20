# RIP Protocol Manager

A Java Swing application for managing and simulating the Routing Information Protocol (RIP).

## 📋 Overview

RIP Manager is a desktop application that provides a graphical interface for managing routers, viewing routing tables, and simulating RIP protocol updates. It's designed for educational purposes to help understand how the RIP routing protocol works in a network environment.

## ✨ Features

### Router Management
- **Add/Remove Routers**: Create and delete virtual routers
- **Connect Routers**: Establish connections between routers to form a network topology
- **View Router Status**: Monitor active routers and their neighbor connections
- **IP Assignment**: Automatic IP address allocation for each router

### Routing Tables
- **Real-time Viewing**: Display routing tables for individual routers
- **Route Information**: View destination networks, next hops, metrics, and interfaces
- **Route Timers**: Monitor age of routing entries
- **Dynamic Updates**: Routing tables update as the simulation runs

### RIP Simulation
- **Configurable Update Intervals**: Set how often RIP updates are broadcast (30 seconds default)
- **Manual Updates**: Trigger RIP updates on-demand
- **Update Logging**: View a history of all RIP updates with timestamps
- **Metric Calculation**: Automatic hop count increments (max 15 hops)
- **Route Propagation**: Watch routes propagate through the network

### User Interface
- **Tabbed Interface**: Organized into Routers, Routing Tables, and Simulation tabs
- **Menu Bar**: File and Help menus with About dialog
- **Toolbars**: Quick access to common operations
- **Tables**: Clean tabular display of router information and routing data
- **Responsive Design**: Real-time updates and logging

## 🚀 Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Eclipse IDE (or any Java IDE)

### Installation

1. **Clone or download the project**
2. **Import into Eclipse:**
   - File → Import → Existing Projects into Workspace
   - Select the project directory
   - Click Finish

3. **Run the application:**
   - Navigate to `RIPManagerGUI.java`
   - Right-click → Run As → Java Application

## 💻 Usage Guide

### Adding Routers
1. Go to the **Routers** tab
2. Click **Add Router** button
3. Enter a name for the router
4. The router will appear in the table with an auto-assigned IP

### Connecting Routers
1. Ensure you have at least two routers added
2. Click **Connect Routers** button
3. Enter router IDs in format: `1-2` (connects Router 1 to Router 2)
4. The neighbor count will update in the table

### Viewing Routing Tables
1. Go to the **Routing Tables** tab
2. Select a router from the dropdown
3. Click **Refresh** to view its current routing table

### Running Simulations
1. Go to the **Simulation** tab
2. Set the update interval (in seconds)
3. Click **Start Simulation** to begin automatic RIP updates
4. Click **Stop Simulation** to pause
5. Use **Send RIP Update** for manual updates
6. View all updates in the log table

## 🏗️ Architecture

### Classes

| Class | Description |
|-------|-------------|
| `RIPManagerGUI` | Main application window and UI components |
| `Router` | Represents a network router with routing capabilities |
| `RoutingTable` | Manages routing entries and operations |
| `RIPPacket` | Encapsulates RIP update messages |

### Key Concepts

- **RIP Protocol**: Distance-vector routing protocol using hop count as metric
- **Route Metrics**: Maximum of 15 hops (16 = unreachable)
- **Update Intervals**: Periodic updates every 30 seconds by default
- **Route Propagation**: Routes are advertised with incremented metrics

## 🎯 Learning Outcomes

This project demonstrates:
- GUI development with Java Swing
- Network protocol simulation
- Object-oriented design patterns
- Event-driven programming
- Real-time data visualization
- Routing algorithm concepts

## 🔧 Customization

You can extend the application by:
- Adding support for RIPv1/RIPv2
- Implementing route poisoning
- Adding triggered updates
- Creating network topology visualization
- Saving/loading configurations
- Exporting routing tables
- Adding authentication

## 📝 Notes

- This is a simulation tool, not a real network router
- RIP updates are simulated within the application
- Maximum metric is 15 (RIP protocol standard)
- Routes with metric 16 are considered unreachable

## 🐛 Troubleshooting

**Issue**: Buttons not responding
- Ensure you've added routers before trying to connect them
- Check that router IDs exist when connecting

**Issue**: Routing table not updating
- Verify simulation is started
- Check if routers are properly connected

**Issue**: Application won't start
- Verify Java version (8+)
- Check for compilation errors in Eclipse

## 📄 License

This project is for educational purposes. Feel free to modify and distribute for learning.

## 👥 Contributing

Feel free to fork and enhance the project with:
- Better visualization
- Additional routing protocols
- Network topology graphs
- Performance improvements
- Documentation updates

## 📞 Support

For issues or questions:
- Check the troubleshooting section
- Review the code comments
- Consult Java Swing and networking documentation

---

**Happy Routing!** 🚀
