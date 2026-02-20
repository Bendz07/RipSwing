package Com;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class RIPManagerGUI extends JFrame {
    private JTabbedPane tabbedPane;
    private JPanel routerPanel;
    private JPanel routingTablePanel;
    private JPanel simulationPanel;
    
    private DefaultTableModel routerTableModel;
    private DefaultTableModel routingTableModel;
    private DefaultTableModel ripUpdatesModel;
    
    private List<Router> routers;
    private Timer simulationTimer;
    
    public RIPManagerGUI() {
        routers = new ArrayList<>();
        initializeUI();
        setupSimulation();
    }
    
    private void initializeUI() {
        setTitle("RIP Protocol Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAboutDialog());
        helpMenu.add(aboutItem);
        
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
        
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        
        // Router Management Panel
        routerPanel = createRouterPanel();
        tabbedPane.addTab("Routers", routerPanel);
        
        // Routing Table Panel
        routingTablePanel = createRoutingTablePanel();
        tabbedPane.addTab("Routing Tables", routingTablePanel);
        
        // Simulation Panel
        simulationPanel = createSimulationPanel();
        tabbedPane.addTab("Simulation", simulationPanel);
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Set frame size and position
        setSize(800, 600);
        setLocationRelativeTo(null);
    }
    
    private JPanel createRouterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Create toolbar
        JToolBar toolBar = new JToolBar();
        JButton addRouterBtn = new JButton("Add Router");
        JButton removeRouterBtn = new JButton("Remove Router");
        JButton connectRoutersBtn = new JButton("Connect Routers");
        
        addRouterBtn.addActionListener(e -> addRouter());
        removeRouterBtn.addActionListener(e -> removeRouter());
        connectRoutersBtn.addActionListener(e -> connectRouters());
        
        toolBar.add(addRouterBtn);
        toolBar.add(removeRouterBtn);
        toolBar.add(connectRoutersBtn);
        
        panel.add(toolBar, BorderLayout.NORTH);
        
        // Router table
        String[] columns = {"Router ID", "Name", "IP Address", "Status", "Neighbors"};
        routerTableModel = new DefaultTableModel(columns, 0);
        JTable routerTable = new JTable(routerTableModel);
        JScrollPane scrollPane = new JScrollPane(routerTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createRoutingTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Router selection combo box
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Select Router:"));
        JComboBox<String> routerCombo = new JComboBox<>();
        routerCombo.addItem("Router 1");
        routerCombo.addItem("Router 2");
        routerCombo.addItem("Router 3");
        topPanel.add(routerCombo);
        
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> refreshRoutingTable(routerCombo.getSelectedIndex()));
        topPanel.add(refreshBtn);
        
        panel.add(topPanel, BorderLayout.NORTH);
        
        // Routing table
        String[] columns = {"Destination", "Next Hop", "Metric", "Interface", "Timer"};
        routingTableModel = new DefaultTableModel(columns, 0);
        JTable routingTable = new JTable(routingTableModel);
        JScrollPane scrollPane = new JScrollPane(routingTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Add sample data
        addSampleRoutingData();
        
        return panel;
    }
    
    private JPanel createSimulationPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Control panel
        JPanel controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        controlPanel.add(new JLabel("Update Interval (seconds):"), gbc);
        
        gbc.gridx = 1;
        JTextField intervalField = new JTextField("30", 5);
        controlPanel.add(intervalField, gbc);
        
        gbc.gridx = 2;
        JButton startBtn = new JButton("Start Simulation");
        startBtn.addActionListener(e -> startSimulation(intervalField.getText()));
        controlPanel.add(startBtn, gbc);
        
        gbc.gridx = 3;
        JButton stopBtn = new JButton("Stop Simulation");
        stopBtn.addActionListener(e -> stopSimulation());
        controlPanel.add(stopBtn, gbc);
        
        gbc.gridx = 4;
        JButton sendUpdateBtn = new JButton("Send RIP Update");
        sendUpdateBtn.addActionListener(e -> sendRIPUpdate());
        controlPanel.add(sendUpdateBtn, gbc);
        
        panel.add(controlPanel, BorderLayout.NORTH);
        
        // RIP updates log
        String[] columns = {"Time", "Source Router", "Destination", "Update Type", "Routes"};
        ripUpdatesModel = new DefaultTableModel(columns, 0);
        JTable updatesTable = new JTable(ripUpdatesModel);
        JScrollPane scrollPane = new JScrollPane(updatesTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Clear log button
        JButton clearLogBtn = new JButton("Clear Log");
        clearLogBtn.addActionListener(e -> ripUpdatesModel.setRowCount(0));
        panel.add(clearLogBtn, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void addRouter() {
        String name = JOptionPane.showInputDialog(this, "Enter Router Name:", "Add Router", JOptionPane.QUESTION_MESSAGE);
        if (name != null && !name.trim().isEmpty()) {
            String ip = "192.168.1." + (routers.size() + 1);
            routerTableModel.addRow(new Object[]{
                routers.size() + 1,
                name,
                ip,
                "Active",
                "0"
            });
            
            Router router = new Router(routers.size() + 1, name, ip);
            routers.add(router);
        }
    }
    
    private void removeRouter() {
        int selectedRow = routerTableModel.getRowCount() - 1;
        if (selectedRow >= 0) {
            routerTableModel.removeRow(selectedRow);
            if (!routers.isEmpty()) {
                routers.remove(routers.size() - 1);
            }
        }
    }
    
    private void connectRouters() {
        String input = JOptionPane.showInputDialog(this, 
            "Enter router IDs to connect (e.g., 1-2):", 
            "Connect Routers", 
            JOptionPane.QUESTION_MESSAGE);
        
        if (input != null && input.matches("\\d+-\\d+")) {
            String[] ids = input.split("-");
            int router1 = Integer.parseInt(ids[0]) - 1;
            int router2 = Integer.parseInt(ids[1]) - 1;
            
            if (router1 >= 0 && router1 < routers.size() && 
                router2 >= 0 && router2 < routers.size()) {
                
                routers.get(router1).addNeighbor(routers.get(router2));
                routers.get(router2).addNeighbor(routers.get(router1));
                
                // Update neighbor count in table
                routerTableModel.setValueAt(routers.get(router1).getNeighborCount(), router1, 4);
                routerTableModel.setValueAt(routers.get(router2).getNeighborCount(), router2, 4);
                
                JOptionPane.showMessageDialog(this, 
                    "Routers connected successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    private void addSampleRoutingData() {
        routingTableModel.addRow(new Object[]{"192.168.1.0/24", "Direct", "1", "eth0", "30"});
        routingTableModel.addRow(new Object[]{"10.0.0.0/8", "192.168.1.2", "2", "eth1", "25"});
        routingTableModel.addRow(new Object[]{"172.16.0.0/16", "192.168.1.3", "3", "eth1", "20"});
    }
    
    private void refreshRoutingTable(int routerIndex) {
        routingTableModel.setRowCount(0);
        if (routerIndex >= 0 && routerIndex < routers.size()) {
            // Add actual routing table entries from the selected router
            Router router = routers.get(routerIndex);
            // This would normally fetch from the router's routing table
            addSampleRoutingData();
        }
    }
    
    private void setupSimulation() {
        simulationTimer = new Timer(30000, e -> {
            // Simulate RIP update every 30 seconds
            if (!routers.isEmpty()) {
                for (Router router : routers) {
                    router.sendRIPUpdate();
                    logRIPUpdate(router);
                }
            }
        });
    }
    
    private void startSimulation(String intervalStr) {
        try {
            int interval = Integer.parseInt(intervalStr) * 1000;
            simulationTimer.setDelay(interval);
            simulationTimer.start();
            JOptionPane.showMessageDialog(this, 
                "RIP Simulation Started", 
                "Simulation", 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "Invalid interval value", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void stopSimulation() {
        simulationTimer.stop();
        JOptionPane.showMessageDialog(this, 
            "RIP Simulation Stopped", 
            "Simulation", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void sendRIPUpdate() {
        if (!routers.isEmpty()) {
            for (Router router : routers) {
                router.sendRIPUpdate();
                logRIPUpdate(router);
            }
        }
    }
    
    private void logRIPUpdate(Router router) {
        java.util.Date date = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(date);
        
        ripUpdatesModel.addRow(new Object[]{
            time,
            router.getName(),
            "All neighbors",
            "RIP Update",
            router.getRoutingTable().getRouteCount() + " routes"
        });
        
        // Keep only last 100 entries
        if (ripUpdatesModel.getRowCount() > 100) {
            ripUpdatesModel.removeRow(0);
        }
    }
    
    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this,
            "RIP Protocol Manager v1.0\n\n" +
            "A tool for managing and simulating RIP routing protocol\n" +
            "Created with Java Swing\n\n" +
            "Features:\n" +
            "- Router management\n" +
            "- Routing table visualization\n" +
            "- RIP update simulation\n" +
            "- Network topology management",
            "About RIP Manager",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new RIPManagerGUI().setVisible(true);
        });
    }
}