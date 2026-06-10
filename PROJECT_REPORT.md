# Vehicle Rental Portal
## Complete Academic Project Report

**Subject:** Java Programming Lab  
**Project Title:** Vehicle Rental Portal  
**Technologies:** Core Java, Swing, JDBC, SQLite, File I/O  
**Author:** [Your Name]  
**Roll No:** [Your Roll Number]  
**Date:** May 2026  

---

## 1. Introduction

The **Vehicle Rental Portal** is a desktop application built using Java Swing that allows users to rent vehicles (Car, Bike, or Premium Car), calculate rental costs with optional discounts, save rental records to an SQLite database, view rental history, and generate text receipts.

This project demonstrates all major syllabus concepts from Labs 1 through 8 in a single integrated application suitable for university lab submission and viva examination.

---

## 2. Objectives

- Enter customer details and select vehicle type
- Calculate rental cost with optional discount
- Demonstrate OOP concepts: classes, inheritance, polymorphism, abstraction, interfaces
- Handle exceptions using custom and built-in exception types
- Display a live system clock using multithreading
- Provide a professional Swing GUI
- Persist rental data using JDBC with SQLite
- Generate receipts using File I/O

---

## 3. Syllabus Mapping

| Lab | Topic | Implementation File(s) |
|-----|-------|------------------------|
| Lab 1 | OOP Fundamentals | `VehicleRentalGUI.java` – method overloading (`calculateTotal`) |
| Lab 2 | Inheritance & Polymorphism | `Vehicle.java`, `Car.java`, `Bike.java`, `VehicleRentalGUI.java` |
| Lab 3 | Multiple Inheritance (Interfaces) | `Insured.java`, `GPS.java`, `PremiumVehicle.java` |
| Lab 4 | Multithreading | `SystemClockThread.java` |
| Lab 5 | Exception Handling | `InvalidDaysException.java`, `VehicleRentalGUI.java` |
| Lab 6 | Abstraction | `Vehicle.java` (abstract class) |
| Lab 7 | Swing GUI | `VehicleRentalGUI.java` |
| Lab 8 | JDBC & File I/O | `DBConnection.java`, `DatabaseManager.java`, `ReceiptGenerator.java` |

---

## 4. Project Structure

```
VehicleRentalPortal/
├── src/
│   ├── Main.java                 → Application entry point
│   ├── Vehicle.java              → Abstract parent class
│   ├── Car.java                  → Child class
│   ├── Bike.java                 → Child class
│   ├── PremiumVehicle.java       → Multiple interface implementation
│   ├── Insured.java              → Interface 1
│   ├── GPS.java                  → Interface 2
│   ├── InvalidDaysException.java → Custom exception
│   ├── SystemClockThread.java    → Background clock thread
│   ├── VehicleRentalGUI.java     → Swing GUI
│   ├── DBConnection.java         → JDBC connection
│   ├── DatabaseManager.java      → INSERT / SELECT operations
│   └── ReceiptGenerator.java     → File I/O receipt
├── lib/
│   ├── README.txt                → JDBC driver setup guide
│   └── sqlite-jdbc-x.x.x.jar     → (download separately)
├── run.bat                       → Windows compile & run script
├── vehicle_rental.db             → (auto-created on first run)
└── receipt.txt                   → (generated on Print Receipt)
```

---

## 5. File Descriptions

### 5.1 Main.java
**Concept:** Application launcher  
**Purpose:** Initializes the database and launches the Swing GUI on the Event Dispatch Thread.

### 5.2 Vehicle.java
**Concept:** Abstraction (Lab 6) + Inheritance base (Lab 2)  
**Purpose:** Abstract parent class with common fields and abstract methods `calculateBaseRent()` and `displayDetails()`. Cannot be instantiated directly.

### 5.3 Car.java
**Concept:** Inheritance  
**Purpose:** Extends `Vehicle`, uses `super()` in constructor, overrides abstract methods.

### 5.4 Bike.java
**Concept:** Inheritance & Polymorphism  
**Purpose:** Another child of `Vehicle` with its own rent calculation and display logic.

### 5.5 Insured.java & GPS.java
**Concept:** Interfaces (Lab 3)  
**Purpose:** Define contracts for insurance and GPS features.

### 5.6 PremiumVehicle.java
**Concept:** Multiple interface implementation  
**Purpose:** Extends `Car` and implements both `Insured` and `GPS`. Selected when user chooses "Premium Car".

### 5.7 InvalidDaysException.java
**Concept:** Custom exception (Lab 5)  
**Purpose:** Thrown when rental days ≤ 0.

### 5.8 SystemClockThread.java
**Concept:** Multithreading (Lab 4)  
**Purpose:** Implements `Runnable`, updates clock label every second in a background thread.

### 5.9 VehicleRentalGUI.java
**Concept:** Swing GUI + Method Overloading + Exception Handling + Polymorphism  
**Purpose:** Main user interface with all input fields, buttons, and business logic.

### 5.10 DBConnection.java & DatabaseManager.java
**Concept:** JDBC (Lab 8)  
**Purpose:** SQLite connection and CRUD operations using `PreparedStatement`.

### 5.11 ReceiptGenerator.java
**Concept:** File I/O (Lab 8)  
**Purpose:** Writes rental receipt to `receipt.txt` using `FileWriter` and `BufferedWriter`.

---

## 6. Database Design

**Database File:** `vehicle_rental.db` (SQLite, auto-created)

**Table:** `rental_history`

| Column | Type | Description |
|--------|------|-------------|
| id | INTEGER PRIMARY KEY AUTOINCREMENT | Unique record ID |
| customer_name | TEXT | Customer name |
| vehicle_type | TEXT | Car / Bike / Premium Car |
| days | INTEGER | Rental duration |
| total_cost | REAL | Final amount |
| rental_date | TEXT | Timestamp of rental |

---

## 7. Execution Steps

### Prerequisites
- JDK 8 or higher installed
- SQLite JDBC JAR downloaded (see `lib/README.txt`)

### Option A: Using run.bat (Windows)
1. Download `sqlite-jdbc-3.45.3.0.jar` into `lib/` folder
2. Double-click `run.bat` OR run from terminal:
   ```
   cd VehicleRentalPortal
   run.bat
   ```

### Option B: Using IntelliJ IDEA
1. Open `VehicleRentalPortal` folder
2. Add SQLite JAR to Project Dependencies
3. Mark `src` as Sources Root
4. Run `Main.java`

### Option C: Command Line
```bat
cd VehicleRentalPortal
javac -cp "lib\sqlite-jdbc-3.45.3.0.jar;src" -d out src\*.java
java -cp "out;lib\sqlite-jdbc-3.45.3.0.jar" Main
```

---

## 8. Application Workflow

1. **Launch** → Database table is created automatically; GUI opens with live clock
2. **Enter Details** → Customer name, vehicle type, days, optional discount %
3. **Calculate Rent** → Creates polymorphic `Vehicle` object, applies overloaded `calculateTotal()`, shows summary
4. **Save Rental** → Inserts record into SQLite via JDBC
5. **View History** → SELECT all records and display in result area
6. **Print Receipt** → Writes `receipt.txt` using File I/O
7. **Clear** → Resets all fields

---

## 9. Sample Test Case

| Field | Value |
|-------|-------|
| Customer Name | Rahul Sharma |
| Vehicle | Premium Car |
| Days | 5 |
| Discount | 10% |

**Expected:** Base rent calculated with premium multiplier, 10% discount applied, insurance/GPS info shown, save to DB succeeds, receipt.txt generated.

---

## 10. Viva Questions & Answers

**Q1: What is abstraction? Where is it used?**  
A: Abstraction hides implementation details and shows only essential features. `Vehicle` is an abstract class with abstract methods `calculateBaseRent()` and `displayDetails()` that child classes must implement.

**Q2: What is polymorphism? Give an example from this project.**  
A: Polymorphism means one interface, many forms. In `VehicleRentalGUI.createVehicle()`, we use `Vehicle vehicle = new Car(...)` or `new Bike(...)` or `new PremiumVehicle(...)`. Calling `vehicle.displayDetails()` executes the correct overridden method at runtime.

**Q3: How does Java achieve multiple inheritance?**  
A: Java does not allow multiple class inheritance but allows a class to implement multiple interfaces. `PremiumVehicle extends Car implements Insured, GPS` demonstrates this.

**Q4: What is method overloading?**  
A: Same method name, different parameters. `calculateTotal(Vehicle, int)` and `calculateTotal(Vehicle, int, double)` are overloaded methods in `VehicleRentalGUI`.

**Q5: What is the difference between Thread class and Runnable?**  
A: `Thread` is a class; `Runnable` is an interface. We use `SystemClockThread implements Runnable` and pass it to `new Thread(systemClock).start()` for cleaner design.

**Q6: Why use PreparedStatement instead of Statement?**  
A: `PreparedStatement` prevents SQL injection, pre-compiles SQL, and makes parameter binding cleaner with `setString()`, `setInt()`, etc.

**Q7: What is a custom exception? When is InvalidDaysException thrown?**  
A: A user-defined exception extending `Exception`. `InvalidDaysException` is thrown when rental days are zero or negative.

**Q8: Explain try-catch-finally in your project.**  
A: In `calculateRent()`, try block parses input and calculates rent, catch handles `NumberFormatException` and `InvalidDaysException`, finally logs the operation regardless of success or failure.

**Q9: Why must Swing updates run on EDT?**  
A: Swing is not thread-safe. `SystemClockThread` uses `SwingUtilities.invokeLater()` to update the clock label safely on the Event Dispatch Thread.

**Q10: What is the role of super keyword?**  
A: `super()` calls the parent class constructor. In `Car` and `Bike`, `super(vehicleId, vehicleName, rentPerDay)` initializes fields defined in `Vehicle`.

**Q11: What file is created by File I/O?**  
A: `receipt.txt` is created by `ReceiptGenerator` using `FileWriter` and `BufferedWriter` when user clicks "Print Receipt".

**Q12: What is JDBC URL for SQLite?**  
A: `jdbc:sqlite:vehicle_rental.db` – connects to (or creates) a local SQLite database file.

---

## 11. Conclusion

The Vehicle Rental Portal successfully integrates all Java lab syllabus topics into one practical desktop application. It uses clean OOP design, proper exception handling, multithreading for a live clock, Swing for the GUI, JDBC for data persistence, and File I/O for receipt generation — making it ideal for lab submission and viva demonstration.

---

*End of Project Report*
