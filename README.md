Shopping System 🛍️
Overview 📌

The Shopping System is a simple, user-friendly platform designed to allow customers to browse and shop for products. It offers key features such as viewing available products, adding items to the shopping cart, searching for specific products, and managing the cart. This system uses Java for its core functionality and SQL for storing and retrieving product data.

It provides a streamlined shopping experience for users while also ensuring that the shopping process remains efficient and secure.

Features 🌟
Product Management 🏷️
View Products: Customers can view a list of available products, which includes details such as name, category, and price.
Add Products to Cart: Customers can add products they wish to purchase into their shopping cart. 🛒
Search for Products: The system allows users to search for products by name, making it easy to find what they are looking for. 🔍
Total Price Calculation: Once products are added to the cart, the system automatically calculates the total price of all items in the cart. 💵
Cart Management 📋
View Cart: Customers can view the items they’ve added to their cart, checking product details, quantities, and prices.
Proceed to Checkout: While not explicitly mentioned in the original description, a typical feature can be assumed here, where the user can proceed to purchase.
Security and Error Handling 🔐
Secure SQL Queries: The system uses Prepared Statements to protect against SQL injection, ensuring safe database interactions.
Error Handling: The system is designed to gracefully handle any user input errors, prompting users to correct mistakes when necessary. 🚫
How to Use 🚀
Run the Java program.
Choose an option from the menu:
View Products 🛍️
Add a Product to the Cart ➕
Exit the System 🚪
Search for a Product 🔍
View Your Cart 📋
Follow the instructions on the screen and complete your shopping process.
Enjoy shopping! 🎉
Requirements 🛠️
Java 8 or higher ☕
Postgres Database 💾
JDBC Driver 🔗
Installation 📥
To install the system, clone the repository using the following command:

bash
git clone https://github.com/Inabat-00/EndtermProject.git
Database Schema 🏗️
The system utilizes three tables in the database:

customers (customer_id, name, email)
products (product_id, name, category, price)
cart (id, product_id, customer_id, quantity)
Author 👨‍💻
Developed by: Abuka Inabat Zhan Miras

