# Ledger Application (Java)

## Overview

The **Ledger Application** is a console-based Java program that allows users to track financial transactions such as deposits and payments. Transactions are stored in a CSV file and can be viewed, filtered, and searched using several reporting options.

This program demonstrates key Java concepts including:

* File input/output (I/O)
* Object-oriented programming
* Collections (`ArrayList`)
* Date and time handling
* Input validation
* Console formatting
* Search and filtering algorithms

The application reads transaction data from a file at startup and allows users to interact with the ledger through a menu-driven interface.

---

## Features

### 1. Home Screen

The Home Screen provides the main navigation for the application.

Options include:

* **Add Deposit**
* **Make Payment**
* **Access Ledger**
* **Exit Application**

Users can add financial transactions that are automatically saved to the transactions file.

---

### 2. Deposits

Deposits represent money added to the account.

When adding a deposit, the user is prompted to enter:

* Description
* Vendor
* Amount

The program automatically records:

* Current date
* Current time

Deposits are stored as **positive values**.

---

### 3. Payments

Payments represent money leaving the account.

The user enters:

* Description
* Vendor
* Amount

Payments are automatically converted into **negative values** to distinguish them from deposits.

---

### 4. Ledger Screen

The Ledger screen allows users to view transaction data.

Available options:

| Option | Function                 |
| ------ | ------------------------ |
| A      | Display all transactions |
| D      | Display deposits only    |
| P      | Display payments only    |
| R      | Open reports screen      |
| H      | Return to home screen    |

Transactions are displayed in a **tabular format** for easier readability.

---

### 5. Reports Screen

The Reports screen provides filtering and search functionality.

Available reports:

| Option | Report                      |
| ------ | --------------------------- |
| A      | Month-to-date transactions  |
| B      | Previous month transactions |
| C      | Year-to-date transactions   |
| D      | Previous year transactions  |
| E      | Search by vendor            |
| F      | Custom search               |

---

### 6. Custom Search

The custom search feature allows the user to filter transactions using multiple criteria.

Users may search by:

* Start Date
* End Date
* Description
* Vendor
* Minimum Amount
* Maximum Amount

Users may skip any field by pressing **Enter**.

The program only filters by fields that contain input.

Example searches:

* All Amazon purchases
* Payments between $10 and $200
* Transactions between two dates
* Description keyword searches

Partial text matching is supported.

Example:

```
Vendor search: amazon
```

Matches:

```
Amazon
Amazon Inc
Amazon Marketplace
```

---

## Data Storage

Transactions are stored in a file named:

```
transactions.csv
```

Each transaction is stored in the following format:

```
DATE|TIME|DESCRIPTION|VENDOR|AMOUNT
```

Example:

```
2026-04-01|12:30:15|Groceries|Walmart|-45.67
2026-04-02|09:12:44|Salary|Employer|1500.00
```

---

## Transaction Display Format

Transactions are displayed in a formatted table:

```
DATE         TIME       DESCRIPTION              VENDOR              AMOUNT
----------------------------------------------------------------------------------------
2026-04-02   09:12:44   Salary                   Employer           1500.00
2026-04-01   12:30:15   Groceries                Walmart            -45.67
```

### Color Coding

The program uses ANSI colors to visually distinguish transactions:

| Type     | Color |
| -------- | ----- |
| Deposits | Green |
| Payments | Red   |

---

## Technologies Used

* **Java**
* Java Collections (`ArrayList`)
* File I/O (`BufferedReader`, `BufferedWriter`)
* Java Time API (`LocalDate`, `LocalTime`)
* Console formatting (`printf`)
* ANSI color codes

---

## Program Structure

### Main Components

**Program.java**

Controls the application workflow and contains methods for:

* Menu navigation
* Transaction loading
* Transaction saving
* Reporting
* Searching
* Display formatting

**Transaction.java**

Represents an individual transaction object containing:

* Date
* Time
* Description
* Vendor
* Amount

---

## How the Program Works

### 1. Startup

When the program starts:

1. The transactions file is loaded
2. Transactions are stored in an `ArrayList`
3. The Home Screen is displayed

---

### 2. Adding Transactions

When a user adds a deposit or payment:

1. The program creates a new `Transaction` object
2. The object is added to the `transactions` list
3. The transaction is written to the CSV file

---

### 3. Viewing Transactions

Transactions are printed using formatted output to align with table headers.

---

### 4. Searching

The custom search feature loops through all transactions and checks if each transaction matches the user's criteria.

If a transaction satisfies all conditions, it is displayed.

---

## Example Program Flow

```
HOME SCREEN
(D) Add deposit
(P) Make payment
(L) Access ledger
(X) Exit
```

User selects:

```
L
```

Ledger menu appears.

User selects:

```
R
```

Reports screen appears.

User selects:

```
F
```

Custom search prompts appear.

---

## Error Handling

The program includes basic error handling for:

* File loading errors
* File writing errors
* Invalid search input
* Empty search fields

---

## Possible Future Improvements

Potential enhancements for this application include:

* Input validation improvements
* Transaction editing
* Transaction deletion
* Sorting options
* Balance calculations
* Graphical User Interface (GUI)
* Database storage instead of CSV files
* Exporting reports

---
## Author
Michael okeke
mokeke@my.yearupunited.org

