# 🛍️ Shopping System

## 📌 Overview

The **Shopping System** is a user-friendly Java-based shopping platform that allows customers to browse and shop for products efficiently. This system enables users to **view products, add items to the cart, search for products, leave reviews, and manage their cart** while ensuring a seamless and secure shopping experience.

The system is built with **Java** for backend logic and **PostgreSQL** for storing and retrieving product data. The database is securely managed using **JDBC** to facilitate fast and reliable data transactions.

---

## 🌟 Features

### 🏷️ **Product Management**
✔ **View Products:** Customers can see a list of available products, including details such as name, category, and price.  
✔ **Add Products to Cart:** Customers can add products they wish to purchase into their shopping cart. 🛒  
✔ **Search for Products:** Users can search for products by name, making it easy to find specific items. 🔍  
✔ **Total Price Calculation:** The system automatically calculates the total price of all items in the cart. 💵  

### 📋 **Cart Management**
✔ **View Cart:** Customers can review the items in their cart, checking product details, quantities, and total prices.  
✔ **Remove Items from Cart:** Users can remove unwanted items from their cart before proceeding.  
✔ **Proceed to Checkout:** While not implemented in this version, a future feature can include checkout and payment processing.  

### ⭐ **Product Reviews**
✔ **Leave a Review:** Customers can leave reviews for products they have purchased. 📝  
✔ **View Product Reviews:** Users can read reviews left by other customers for specific products.  
✔ **Average Rating Calculation:** Each product displays an average rating based on customer feedback. ⭐  

### 🔐 **Security and Error Handling**
✔ **Secure SQL Queries:** The system uses **Prepared Statements** to prevent **SQL injection** attacks. 🔒  
✔ **Error Handling:** The system is designed to handle input errors gracefully, prompting users to correct mistakes. 🚫  
✔ **Validations:** Ensures that ratings are between 1 and 5, and prevents invalid inputs for product selections.  

---

## 🚀 How to Use

1. **Run the Java program.**
2. **Choose an option from the menu:**
   - `View Products 🛍️`
   - `Add a Product to the Cart ➕`
   - `Search for a Product 🔍`
   - `View Your Cart 📋`
   - `Leave a Review 📝`
   - `View Product Reviews ⭐`
   - `Exit the System 🚪`
3. **Follow the instructions displayed on the screen.**
4. **Enjoy your shopping experience! 🎉**

---

## 🛠️ Requirements

To run this project, ensure you have the following installed:

- **Java 8 or higher** ☕
- **PostgreSQL Database** 💾
- **JDBC Driver** 🔗 (Ensure your PostgreSQL JDBC driver is configured)

---

## 📥 Installation Guide

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Inabat-00/EndtermProject.git
