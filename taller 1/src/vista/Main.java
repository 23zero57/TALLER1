package vista;

import datos.ProductoDAO;
import modelo.Producto;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProductoDAO dao = new ProductoDAO();
        int opcion = 0;

        do {
            System.out.println("\n--- SISTEMA DE GESTIÓN DE PRODUCTOS ---");
            System.out.println("1. Registrar producto");
            System.out.println("2. Listar productos");
            System.out.println("3. Actualizar producto");
            System.out.println("4. Eliminar producto");
            System.out.println("5. Salir");
            
            opcion = leerEnteroSeguro(scanner, "Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    System.out.print("Código: ");
                    String cod = scanner.nextLine();
                    System.out.print("Nombre: ");
                    String nom = scanner.nextLine();
                    System.out.print("Descripción: ");
                    String desc = scanner.nextLine();
                    
                    double precio = leerDoubleSeguro(scanner, "Precio: ");
                    int cant = leerEnteroSeguro(scanner, "Cantidad: ");

                    if (precio < 0 || cant < 0) {
                        System.out.println("¡Error! El precio y la cantidad no pueden ser números negativos.");
                    } else {
                        if (dao.registrar(new Producto(cod, nom, desc, precio, cant))) {
                            System.out.println("¡Producto registrado con éxito!");
                        }
                    }
                    break;

                case 2:
                    List<Producto> productos = dao.consultarTodos();
                    System.out.println("\n--- LISTA DE PRODUCTOS ---");
                    if (productos.isEmpty()) {
                        System.out.println("No hay productos registrados en este momento.");
                    } else {
                        for (Producto p : productos) {
                            System.out.println(p);
                        }
                    }
                    break;

                case 3:
                    System.out.print("Ingrese el código del producto a actualizar: ");
                    String codAct = scanner.nextLine();
                    
                    double nuevoPrecio = leerDoubleSeguro(scanner, "Nuevo precio: ");
                    int nuevaCant = leerEnteroSeguro(scanner, "Nueva cantidad: ");
                    
                    System.out.print("Nueva descripción: ");
                    String nuevaDesc = scanner.nextLine();

                    if (nuevoPrecio < 0 || nuevaCant < 0) {
                        System.out.println("¡Error! Los valores numéricos no pueden ser negativos.");
                    } else {
                        if (dao.actualizar(codAct, nuevoPrecio, nuevaCant, nuevaDesc)) {
                            System.out.println("¡Producto actualizado correctamente!");
                        } else {
                            System.out.println("No se encontró ningún producto registrado con ese código.");
                        }
                    }
                    break;

                case 4:
                    System.out.print("Ingrese el código del producto a eliminar: ");
                    String codEli = scanner.nextLine();
                    System.out.print("¿Está seguro de eliminarlo? (s/n): ");
                    String confirmar = scanner.nextLine();
                    if (confirmar.equalsIgnoreCase("s")) {
                        if (dao.eliminar(codEli)) {
                            System.out.println("¡Producto eliminado del sistema!");
                        } else {
                            System.out.println("No se encontró un producto con ese código.");
                        }
                    } else {
                        System.out.println("Operación cancelada.");
                    }
                    break;

                case 5:
                    System.out.println("Saliendo del sistema... ¡Hasta luego!");
                    break;

                default:
                    System.out.println("¡Opción no válida! Por favor elija un número entre 1 y 5.");
            }
        } while (opcion != 5);
        scanner.close();
    }

    // --- MÉTODOS AUXILIARES PARA EVITAR QUE EL PROGRAMA SE CAIGA CON TEXTO ---

    private static int leerEnteroSeguro(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            if (scanner.hasNextInt()) {
                int valor = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer de saltos de línea
                return valor;
            } else {
                System.out.println("¡Advertencia! No puedes ingresar texto en este campo numérico. Inténtalo de nuevo.");
                scanner.next(); // Descarta el valor erróneo para liberar el scanner
            }
        }
    }

    private static double leerDoubleSeguro(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            if (scanner.hasNextDouble()) {
                double valor = scanner.nextDouble();
                scanner.nextLine(); // Limpiar el buffer
                return valor;
            } else {
                System.out.println(" ¡Advertencia! Ingresaste texto en lugar de un precio válido. Por favor, introduce solo números.");
                scanner.next(); // Descarta el valor erróneo
            }
        }
    }
}