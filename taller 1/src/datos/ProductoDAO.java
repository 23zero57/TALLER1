package datos;

import modelo.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public boolean registrar(Producto p) {
        String sql = "INSERT INTO producto(codigo, nombre, descripcion, precio, cantidad) VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, p.getCodigo());
            pstmt.setString(2, p.getNombre());
            pstmt.setString(3, p.getDescripcion());
            pstmt.setDouble(4, p.getPrecio());
            pstmt.setInt(5, p.getCantidad());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al registrar producto: " + e.getMessage());
            return false;
        }
    }

    public List<Producto> consultarTodos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM producto";
        try (Connection conn = Conexion.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Producto(
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    rs.getInt("cantidad")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar productos: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizar(String codigo, double precio, int cantidad, String descripcion) {
        String sql = "UPDATE producto SET precio = ?, cantidad = ?, descripcion = ? WHERE codigo = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, precio);
            pstmt.setInt(2, cantidad);
            pstmt.setString(3, descripcion);
            pstmt.setString(4, codigo);
            int filas = pstmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(String codigo) {
        String sql = "DELETE FROM producto WHERE codigo = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, codigo);
            int filas = pstmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
            return false;
        }
    }
}