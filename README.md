# Team Project Repository

## Repository Structure

```plaintext
venue-and-you/
│── src/                                  # Our marketing team code
│   ├── marketing/                        # Marketing package @hanna worked on.
│   │   ├── features/                     # Our marketing-specific features
│   │   ├── implementations/              # Implementations of interfaces given to us
│   │   │   ├── from_operations/          # Implementation of Operations team’s interface
│   │   │   ├── from_box_office/          # Implementation of Box Office team’s interface
│   ├── tests/
│   ├── README.md
│── interfaces/                          # Interface definitions
│   ├── marketing_to_operations/         # Our interface for Operations team
│   ├── marketing_to_boxoffice/          # Our interface for Box Office team
│   ├── OperationsAPI/         # The interface Operations gives us
│   ├── BoxOfficeAPI/          # The interface Box Office gives us
│   ├── README.md
│── docs/                                # Documentation
│── README.md                            # General project overview
│── CONTRIBUTING.md                      # Contribution guidelines
```

# 🎟️ Marketing Booking System

The **Marketing Booking System** is a Java Swing-based desktop application that allows users to manage room and seat bookings for a venue. It supports both single and group bookings with features like real-time seat availability, database persistence, and administrative tools.

---

## 🚀 Features

- **Single & Group Bookings**  
  Book individual seats or reserve rooms for group events.

- **Interactive Seat Selection**  
  Dynamic seat maps for the Main Hall and Small Hall, reflecting real-time availability.

- **Database Integration (MySQL)**  
  All booking data is saved and loaded using a MySQL database via JDBC.

- **Booking Overview Panel**  
  View all confirmed bookings, with options to delete records from both the UI and database.

- **Live Clock**  
  Displays the current system time within the user interface.

---

## 🗂️ Project Structure

/src ├── marketing/ │ ├── BookingAppSwing.java │ ├── controller/ │ ├── db/ │ ├── model/ │ ├── service/ │ └── util/ └── MarketingPage.java

markdown
Copy
Edit

- `MarketingPage.java` – Main dashboard UI with navigation and embedded panels
- `BookingAppSwing.java` – Handles all booking logic, seat selection, and data operations
- `db/` – Database helper classes
- `model/` – Data models for bookings
- `service/` – Logic for managing bookings
- `controller/` & `util/` – Optional business logic and helpers

---

## 🛠️ Technologies Used

- Java (JDK 17+)
- Java Swing
- MySQL
- JDBC
- MVC Design Principles

---

## ⚙️ Setup & Installation

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/your-repo-name.git
cd your-repo-name
2. Configure the Database
Set up a MySQL database.

Create the required tables:

single_bookings

group_bookings

Update your database credentials in MySQLDatabaseHelper.java.

3. Run the Application
Open the project in an IDE (e.g., IntelliJ, Eclipse).

Run MarketingPage.java or integrate BookingAppSwing.java accordingly.

🌱 Future Improvements
Add authentication and user roles

Export bookings to CSV or PDF

Implement booking rescheduling

Add filters/search to booking tables

Improve UI responsiveness and accessibility

👤 Author
Sujit Bhatta
```
