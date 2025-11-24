# Travel-Booking-System
The Travel Booking System is a comprehensive Java-based desktop application with GUI interface that allows users to book flights, hotels, and car rentals. This project demonstrates object-oriented programming principles, interface implementation, Swing GUI development, and database integration concepts.


# 🏗️ Project Structure
...
TravelBookingSystem/
|
│
├── src/
│   └── TravelBookingSystem.java      # Main application file(source code)
│
├── docs/
│   └── README.md                     # Project documentation
│
├── lib/                              # External libraries (if any)
│
└── bin/                              # Compiled class files
...
# 🎯 Features
Core Functionality
Multi-service Booking: Book flights, hotels, and car rentals

Real-time Availability: Live availability checking

Search & Filter: Advanced search and filtering options

Booking Management: Book, view, and cancel reservations

Price Calculation: Automatic price calculation with taxes and multipliers

# User Interface
Modern GUI: Built with Java Swing

Responsive Design: User-friendly interface

Data Table: Organized display of travel options

Interactive Controls: Buttons, search, and filters

# 🔧 Technical Architecture
Interfaces
Bookable
java
public interface Bookable {
    void book();
    void cancel();
    double calculatePrice();
    boolean isAvailable();
    String getType();
}
Displayable
java
public interface Displayable {
    String[] getDisplayData();
}
Class Hierarchy
text
TravelEntity (Abstract Class)
│
├── Flight (Implements Bookable, Displayable)
├── Hotel (Implements Bookable, Displayable)
└── CarRental (Implements Bookable, Displayable)
Key Classes
TravelEntity - Abstract base class for all travel entities

Flight - Manages flight bookings with airline details

Hotel - Handles hotel reservations with rating system

CarRental - Manages car rentals with type-based pricing

BookingManager - Central controller for all booking operations

TravelBookingSystem - Main GUI application class

# 🚀 Installation & Setup
Prerequisites
Java Development Kit (JDK) 8 or higher

IntelliJ IDEA (recommended) or any Java IDE

Step-by-Step Setup
Clone or Download the Project

bash
# Create project directory
mkdir TravelBookingSystem
cd TravelBookingSystem
Create Source Structure

text
TravelBookingSystem/
└── src/
    └── TravelBookingSystem.java
Using IntelliJ IDEA:

Open IntelliJ IDEA

Select "Open or Import"

Navigate to your project folder

Select the project and click "OK"

IntelliJ will automatically detect the Java project structure

Manual Compilation (Command Line):

bash
# Compile the project
javac src/TravelBookingSystem.java -d bin/

# Run the application
java -cp bin TravelBookingSystem
💻 How to Use
Launching the Application
Run the TravelBookingSystem class

The main window will open with all available travel options

# Basic Operations
Viewing Options
View All: Shows all travel options

View Available: Filters to show only available items

Search: Type in the search box to filter by name, type, or details

Filter: Use the dropdown to filter by category (Flights, Hotels, Car Rentals)

# Making a Booking
Select an item from the table

Click "Book Selected" button

Confirm the booking in the dialog box

The system will update availability automatically

# Managing Bookings
View Bookings: Click "View Bookings" to see all current reservations

Cancel Booking: Select a booked item and click "Cancel Booking"

# 📊 Sample Data
The system comes pre-loaded with sample data:

Flights
AI-101: Delhi to Mumbai, Air India, $200

SG-202: Mumbai to Bangalore, SpiceJet, $150

Hotels
Taj Hotel: Mumbai, 5 stars, $100/night

Ibis Hotel: Delhi, 3 stars, $50/night

Grand Plaza: Bangalore, 4 stars, $80/night

Car Rentals
Toyota Innova: SUV, $40/day

Mercedes E-Class: Luxury, $80/day

Honda City: Sedan, $30/day

# 🔍 Code Explanation
Interface Implementation
The project demonstrates proper use of interfaces:

java
// Example of interface implementation
class Flight extends TravelEntity implements Bookable, Displayable {
    // Implements all abstract methods from both interfaces
    public void book() { /* implementation */ }
    public String[] getDisplayData() { /* implementation */ }
}
GUI Components
JTable: Displays travel options in tabular format

JTextField: Search functionality

JComboBox: Filter options

JButton: Action triggers

JOptionPane: Confirmation dialogs and messages

Design Patterns
Strategy Pattern: Through Bookable interface for different booking behaviors

Template Method: In TravelEntity abstract class

Observer Pattern: Table updates when data changes

# 🛠️ Development
Adding New Travel Types
To add a new travel type (e.g., Bus):

Create a new class extending TravelEntity

Implement both Bookable and Displayable interfaces

Add to BookingManager in initializeSampleData()

java
class Bus extends TravelEntity implements Bookable, Displayable {
    // Implement required methods
    // Add bus-specific properties
}
Modifying Pricing Logic
Override the calculatePrice() method in specific classes:

java
@Override
public double calculatePrice() {
    // Custom pricing logic
    return basePrice * seasonalMultiplier + fees;
}
# 🐛 Troubleshooting
Common Issues
"Class not found" error

Ensure the file is named TravelBookingSystem.java

Check that the class is in the correct package structure

GUI not displaying properly

Verify Java version compatibility

Check system look and feel support

Compilation errors

Ensure all imports are correct

Verify JDK installation

Debugging Tips
Use IntelliJ's built-in debugger

Check console for error messages

Verify all interface methods are implemented

# 📈 Future Enhancements
Potential improvements for the system:

Database integration for persistent storage

User authentication and profiles

Payment gateway integration

Email confirmation system

Advanced search with date ranges

Booking history and receipts

Multi-language support

Export booking data

# 📄 License
This project is created for educational purposes as part of a college project.


