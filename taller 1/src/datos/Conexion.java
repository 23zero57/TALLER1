package datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {
    private static final String URL = "jdbc:sqlite:productos.db";

    public static Connection conectar() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
            crearTablaSiNoExiste(conn);
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }
        return conn;
    }

    private static void crearTablaSiNoExiste(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS producto (" +
                     "codigo TEXT PRIMARY KEY, " +
                     "nombre TEXT NOT NULL, " +
                     "descripcion TEXT, " +
                     "precio REAL NOT NULL, " +
                     "cantidad INTEGER NOT NULL)";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error al crear la tabla: " + e.getMessage());
        }
    }
}