Shopping System 🛍️

Description 📌

The Shopping System is a simple Java-based console application that allows customers to browse and purchase products. The system interacts with a PostgreSQL database to store and retrieve product and customer data. This project provides a user-friendly interface for viewing available products, adding items to a shopping cart, searching for products, and viewing the cart.

Features 🌟

✔️ User-friendly menu with emoji support 😃
✔️ Displays product details including name, category, and price 💰
✔️ Allows customers to add products to a shopping cart 🛒
✔️ Enables searching for products by name 🔎
✔️ Calculates the total price of products in the cart 💵
✔️ Secure SQL queries using Prepared Statements 🔐
✔️ Handles invalid user inputs and errors gracefully 🚫

How to Use 🚀

Run the Java program.

Select an option from the menu:

1️⃣ View Products 🏷️ – Displays a list of available products.

2️⃣ Add a Product to the Cart ➕ – Allows customers to add a product to their cart.

3️⃣ Exit the System 🚪 – Closes the application.

4️⃣ Search for a Product 🔍 – Find a product by its name.

5️⃣ View Your Cart 📋 – Displays the items added to the cart along with the total price.

Follow the on-screen instructions to interact with the system.

Enjoy shopping! 🎉

Requirements 🛠️

To run the Shopping System, ensure that you have the following installed:

Java 8 or higher ☕

PostgreSQL Database 💾

JDBC Driver 🔗 (For Java-PostgreSQL communication)

Installation 📥

To install and run the Shopping System, follow these steps:

Clone the repository:

git clone https://github.com/Inabat-00/EndtermProject.git

Navigate to the project folder:

cd EndtermProject

Configure the PostgreSQL database (see Database Schema below).

Compile and run the Java program:

javac Main.java
java Main

Database Schema 🏗️

The Shopping System uses a PostgreSQL database with the following tables:

1️⃣ Customers Table (customers)

Stores customer information.

CREATE TABLE customers (
    customer_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL
);

2️⃣ Products Table (products)

Stores available products.

CREATE TABLE products (
    product_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(100),
    price DECIMAL(10,2) NOT NULL
);

3️⃣ Cart Table (cart)

Stores products added to a customer's shopping cart.

CREATE TABLE cart (
    id SERIAL PRIMARY KEY,
    product_id INT REFERENCES products(product_id) ON DELETE CASCADE,
    customer_id INT REFERENCES customers(customer_id) ON DELETE CASCADE,
    quantity INT NOT NULL
);

Author 👨‍💻

This project was developed by:

Abuka 🎯

Inabat 🎯

Zhan 🎯

Miras 🎯

Future Enhancements 🔮

Implement admin features for product management (CRUD operations).

Add user authentication for secure login and registration.

Create a GUI version using JavaFX or a web-based interface.

Enhance the search functionality with filtering and sorting options.

Happy Coding! 🚀

