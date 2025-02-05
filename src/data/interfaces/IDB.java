package src.data.interfaces;

import java.sql.Connection;
import java.sql.SQLException; // Нужно для исключений SQL

public interface IDB {
    Connection getConnection() throws SQLException;
}
