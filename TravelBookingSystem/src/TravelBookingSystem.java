import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

// Interface for Bookable items
interface Bookable {
    void book();
    void cancel();
    double calculatePrice();
    boolean isAvailable();
    String getType();
}

// Interface for Displayable items
interface Displayable {
    String[] getDisplayData();
}

// Abstract base class for all travel entities
abstract class TravelEntity implements Displayable {
    protected String id;
    protected String name;
    protected double basePrice;

    public TravelEntity(String id, String name, double basePrice) {
        this.id = id;
        this.name = name;
        this.basePrice = basePrice;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getBasePrice() { return basePrice; }
}

// Flight Class
class Flight extends TravelEntity implements Bookable {
    private String airline;
    private String source;
    private String destination;
    private String departureTime;
    private int availableSeats;
    private boolean isBooked;

    public Flight(String id, String name, double basePrice, String airline,
                  String source, String destination, String departureTime, int availableSeats) {
        super(id, name, basePrice);
        this.airline = airline;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.availableSeats = availableSeats;
        this.isBooked = false;
    }

    @Override
    public void book() {
        if (isAvailable()) {
            isBooked = true;
            availableSeats--;
        }
    }

    @Override
    public void cancel() {
        if (isBooked) {
            isBooked = false;
            availableSeats++;
        }
    }

    @Override
    public double calculatePrice() {
        return basePrice + (basePrice * 0.18); // 18% tax
    }

    @Override
    public boolean isAvailable() {
        return availableSeats > 0;
    }

    @Override
    public String getType() {
        return "Flight";
    }

    @Override
    public String[] getDisplayData() {
        return new String[]{
                id, name, "Flight", airline,
                source + " to " + destination,
                departureTime,
                String.valueOf(availableSeats),
                String.format("$%.2f", basePrice),
                String.format("$%.2f", calculatePrice()),
                isAvailable() ? "Yes" : "No"
        };
    }
}

// Hotel Class
class Hotel extends TravelEntity implements Bookable {
    private String location;
    private int availableRooms;
    private int rating;
    private boolean isBooked;

    public Hotel(String id, String name, double basePrice, String location,
                 int availableRooms, int rating) {
        super(id, name, basePrice);
        this.location = location;
        this.availableRooms = availableRooms;
        this.rating = rating;
        this.isBooked = false;
    }

    @Override
    public void book() {
        if (isAvailable()) {
            isBooked = true;
            availableRooms--;
        }
    }

    @Override
    public void cancel() {
        if (isBooked) {
            isBooked = false;
            availableRooms++;
        }
    }

    @Override
    public double calculatePrice() {
        double ratingMultiplier = 1 + (rating * 0.1);
        return basePrice * ratingMultiplier;
    }

    @Override
    public boolean isAvailable() {
        return availableRooms > 0;
    }

    @Override
    public String getType() {
        return "Hotel";
    }

    @Override
    public String[] getDisplayData() {
        return new String[]{
                id, name, "Hotel", location,
                rating + " Stars", "-",
                String.valueOf(availableRooms),
                String.format("$%.2f", basePrice),
                String.format("$%.2f", calculatePrice()),
                isAvailable() ? "Yes" : "No"
        };
    }
}

// Car Rental Class
class CarRental extends TravelEntity implements Bookable {
    private String carType;
    private int availableCars;
    private boolean isBooked;

    public CarRental(String id, String name, double basePrice, String carType, int availableCars) {
        super(id, name, basePrice);
        this.carType = carType;
        this.availableCars = availableCars;
        this.isBooked = false;
    }

    @Override
    public void book() {
        if (isAvailable()) {
            isBooked = true;
            availableCars--;
        }
    }

    @Override
    public void cancel() {
        if (isBooked) {
            isBooked = false;
            availableCars++;
        }
    }

    @Override
    public double calculatePrice() {
        if (carType.equalsIgnoreCase("luxury")) {
            return basePrice * 1.5;
        }
        return basePrice;
    }

    @Override
    public boolean isAvailable() {
        return availableCars > 0;
    }

    @Override
    public String getType() {
        return "Car Rental";
    }

    @Override
    public String[] getDisplayData() {
        return new String[]{
                id, name, "Car Rental", carType,
                "-", "-",
                String.valueOf(availableCars),
                String.format("$%.2f", basePrice),
                String.format("$%.2f", calculatePrice()),
                isAvailable() ? "Yes" : "No"
        };
    }
}

// Booking Manager Class
class BookingManager {
    private List<Bookable> bookings;
    private List<Displayable> travelEntities;
    private Map<String, Bookable> bookableMap;

    public BookingManager() {
        bookings = new ArrayList<>();
        travelEntities = new ArrayList<>();
        bookableMap = new HashMap<>();
    }

    public void addTravelEntity(Displayable entity) {
        travelEntities.add(entity);
        if (entity instanceof Bookable) {
            Bookable bookable = (Bookable) entity;
            bookableMap.put(((TravelEntity) entity).getId(), bookable);
        }
    }

    public boolean bookItem(String id) {
        Bookable item = bookableMap.get(id);
        if (item != null && item.isAvailable()) {
            item.book();
            bookings.add(item);
            return true;
        }
        return false;
    }

    public boolean cancelBooking(String id) {
        Bookable item = bookableMap.get(id);
        if (item != null && bookings.contains(item)) {
            item.cancel();
            bookings.remove(item);
            return true;
        }
        return false;
    }

    public List<Displayable> getAllEntities() {
        return new ArrayList<>(travelEntities);
    }

    public List<Displayable> getAvailableEntities() {
        List<Displayable> available = new ArrayList<>();
        for (Displayable entity : travelEntities) {
            if (entity instanceof Bookable) {
                Bookable bookable = (Bookable) entity;
                if (bookable.isAvailable()) {
                    available.add(entity);
                }
            }
        }
        return available;
    }

    public List<Bookable> getBookings() {
        return new ArrayList<>(bookings);
    }

    public Bookable findBookableById(String id) {
        return bookableMap.get(id);
    }
}

// Main GUI Class
public class TravelBookingSystem {
    private BookingManager manager;
    private JTable dataTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> filterComboBox;
    private JFrame frame;

    // Column names for the table
    private final String[] COLUMN_NAMES = {
            "ID", "Name", "Type", "Details", "Route/Rating", "Time",
            "Available", "Base Price", "Final Price", "Status"
    };

    public TravelBookingSystem() {
        manager = new BookingManager();
        initializeSampleData();
        initializeGUI();
    }

    private void initializeSampleData() {
        // Create sample flights
        Flight flight1 = new Flight("F001", "AI-101", 200.0, "Air India",
                "Delhi", "Mumbai", "10:00 AM", 5);
        Flight flight2 = new Flight("F002", "SG-202", 150.0, "SpiceJet",
                "Mumbai", "Bangalore", "02:30 PM", 0);

        // Create sample hotels
        Hotel hotel1 = new Hotel("H001", "Taj Hotel", 100.0, "Mumbai", 3, 5);
        Hotel hotel2 = new Hotel("H002", "Ibis Hotel", 50.0, "Delhi", 2, 3);
        Hotel hotel3 = new Hotel("H003", "Grand Plaza", 80.0, "Bangalore", 4, 4);

        // Create sample car rentals
        CarRental car1 = new CarRental("C001", "Toyota Innova", 40.0, "SUV", 2);
        CarRental car2 = new CarRental("C002", "Mercedes E-Class", 80.0, "Luxury", 1);
        CarRental car3 = new CarRental("C003", "Honda City", 30.0, "Sedan", 3);

        // Add all to manager
        manager.addTravelEntity(flight1);
        manager.addTravelEntity(flight2);
        manager.addTravelEntity(hotel1);
        manager.addTravelEntity(hotel2);
        manager.addTravelEntity(hotel3);
        manager.addTravelEntity(car1);
        manager.addTravelEntity(car2);
        manager.addTravelEntity(car3);
    }

    private void initializeGUI() {
        frame = new JFrame("Travel Booking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);

        // Create main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create header
        JLabel headerLabel = new JLabel("Travel Booking System", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(0, 100, 200));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Create control panel
        JPanel controlPanel = createControlPanel();
        mainPanel.add(controlPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // Create search and filter panel
        JPanel topPanel = createTopPanel();
        panel.add(topPanel, BorderLayout.NORTH);

        // Create table
        createTable();
        JScrollPane scrollPane = new JScrollPane(dataTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Create button panel
        JPanel buttonPanel = createButtonPanel();
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Search field
        panel.add(new JLabel("Search:"));
        searchField = new JTextField(15);
        searchField.addActionListener(e -> filterTable());
        panel.add(searchField);

        // Filter combo box
        panel.add(new JLabel("Filter:"));
        filterComboBox = new JComboBox<>(new String[]{"All", "Available", "Flights", "Hotels", "Car Rentals"});
        filterComboBox.addActionListener(e -> filterTable());
        panel.add(filterComboBox);

        // Refresh button
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> refreshTable());
        panel.add(refreshBtn);

        return panel;
    }

    private void createTable() {
        tableModel = new DefaultTableModel(COLUMN_NAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };

        dataTable = new JTable(tableModel);
        dataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dataTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        dataTable.setRowHeight(25);
        dataTable.setFont(new Font("Arial", Font.PLAIN, 12));

        // Set column widths
        dataTable.getColumnModel().getColumn(0).setPreferredWidth(60); // ID
        dataTable.getColumnModel().getColumn(1).setPreferredWidth(120); // Name
        dataTable.getColumnModel().getColumn(2).setPreferredWidth(80); // Type
        dataTable.getColumnModel().getColumn(3).setPreferredWidth(120); // Details
        dataTable.getColumnModel().getColumn(4).setPreferredWidth(100); // Route/Rating
        dataTable.getColumnModel().getColumn(5).setPreferredWidth(80); // Time
        dataTable.getColumnModel().getColumn(6).setPreferredWidth(70); // Available
        dataTable.getColumnModel().getColumn(7).setPreferredWidth(80); // Base Price
        dataTable.getColumnModel().getColumn(8).setPreferredWidth(80); // Final Price
        dataTable.getColumnModel().getColumn(9).setPreferredWidth(60); // Status

        refreshTable();
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        // Book button
        JButton bookBtn = new JButton("Book Selected");
        bookBtn.setBackground(new Color(50, 150, 50));
        bookBtn.setForeground(Color.WHITE);
        bookBtn.addActionListener(e -> bookSelectedItem());
        panel.add(bookBtn);

        // Cancel button
        JButton cancelBtn = new JButton("Cancel Booking");
        cancelBtn.setBackground(new Color(200, 50, 50));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.addActionListener(e -> cancelSelectedBooking());
        panel.add(cancelBtn);

        // View All button
        JButton viewAllBtn = new JButton("View All");
        viewAllBtn.addActionListener(e -> showAllItems());
        panel.add(viewAllBtn);

        // View Available button
        JButton viewAvailableBtn = new JButton("View Available");
        viewAvailableBtn.addActionListener(e -> showAvailableItems());
        panel.add(viewAvailableBtn);

        // View Bookings button
        JButton viewBookingsBtn = new JButton("View Bookings");
        viewBookingsBtn.addActionListener(e -> showBookings());
        panel.add(viewBookingsBtn);

        // Exit button
        JButton exitBtn = new JButton("Exit");
        exitBtn.addActionListener(e -> System.exit(0));
        panel.add(exitBtn);

        return panel;
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Displayable> entities = manager.getAllEntities();

        for (Displayable entity : entities) {
            tableModel.addRow(entity.getDisplayData());
        }
    }

    private void filterTable() {
        String searchText = searchField.getText().toLowerCase();
        String filterType = (String) filterComboBox.getSelectedItem();

        tableModel.setRowCount(0);
        List<Displayable> entities = manager.getAllEntities();

        for (Displayable entity : entities) {
            String[] rowData = entity.getDisplayData();
            boolean matchesSearch = true;
            boolean matchesFilter = true;

            // Apply search filter
            if (!searchText.isEmpty()) {
                matchesSearch = false;
                for (String data : rowData) {
                    if (data.toLowerCase().contains(searchText)) {
                        matchesSearch = true;
                        break;
                    }
                }
            }

            // Apply type filter
            if (filterType != null && !filterType.equals("All")) {
                if (filterType.equals("Available")) {
                    matchesFilter = rowData[9].equals("Yes");
                } else if (filterType.equals("Flights")) {
                    matchesFilter = rowData[2].equals("Flight");
                } else if (filterType.equals("Hotels")) {
                    matchesFilter = rowData[2].equals("Hotel");
                } else if (filterType.equals("Car Rentals")) {
                    matchesFilter = rowData[2].equals("Car Rental");
                }
            }

            if (matchesSearch && matchesFilter) {
                tableModel.addRow(rowData);
            }
        }
    }

    private void bookSelectedItem() {
        int selectedRow = dataTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an item to book.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id = (String) tableModel.getValueAt(selectedRow, 0);
        String name = (String) tableModel.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(frame,
                "Are you sure you want to book: " + name + "?",
                "Confirm Booking", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (manager.bookItem(id)) {
                JOptionPane.showMessageDialog(frame, "Booking successful for: " + name, "Success", JOptionPane.INFORMATION_MESSAGE);
                filterTable();
            } else {
                JOptionPane.showMessageDialog(frame, "Booking failed! Item may not be available.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cancelSelectedBooking() {
        int selectedRow = dataTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a booking to cancel.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id = (String) tableModel.getValueAt(selectedRow, 0);
        String name = (String) tableModel.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(frame,
                "Are you sure you want to cancel booking for: " + name + "?",
                "Confirm Cancellation", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (manager.cancelBooking(id)) {
                JOptionPane.showMessageDialog(frame, "Booking cancelled for: " + name, "Success", JOptionPane.INFORMATION_MESSAGE);
                filterTable();
            } else {
                JOptionPane.showMessageDialog(frame, "Cancellation failed! Booking not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showAllItems() {
        filterComboBox.setSelectedItem("All");
        searchField.setText("");
        refreshTable();
    }

    private void showAvailableItems() {
        filterComboBox.setSelectedItem("Available");
    }

    private void showBookings() {
        tableModel.setRowCount(0);
        List<Bookable> bookings = manager.getBookings();

        if (bookings.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No active bookings found.", "Bookings", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Bookable booking : bookings) {
                if (booking instanceof Displayable) {
                    Displayable displayable = (Displayable) booking;
                    tableModel.addRow(displayable.getDisplayData());
                }
            }
        }
    }

    public void show() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Set system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create and show GUI
        SwingUtilities.invokeLater(() -> {
            TravelBookingSystem gui = new TravelBookingSystem();
            gui.show();
        });
    }
}