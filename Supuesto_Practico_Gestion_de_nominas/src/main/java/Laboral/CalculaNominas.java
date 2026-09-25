package Laboral;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CalculaNominas {

    private static final String FICHERO_TEXTO = "empleados.txt";
    private static final String FICHERO_NUEVOS = "empleadosNuevos.txt";

    /**
     * 5. Metodo principal con el menu de opciones interactivo
     */
    public static void main(String[] args) {
        GestionDB gestionDB = new GestionDB();
        GestionAltas gestionAltas = new GestionAltas();
        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        System.out.println("=== SISTEMA DE GESTIÓN DE NÓMINAS ===");

        while (!salir) {
            System.out.println("\n-------------------------------------------");
            System.out.println("MENÚ PRINCIPAL");
            System.out.println("1. Mostrar información de todos los empleados (BD)");
            System.out.println("2. Mostrar salario de un empleado por DNI (BD)");
            System.out.println("3. Modificar datos de un empleado (Submenú BD)");
            System.out.println("4. Recalcular y actualizar el sueldo de un empleado (BD)");
            System.out.println("5. Recalcular y actualizar los sueldos de TODOS los empleados (BD)");
            System.out.println("6. Realizar copia de seguridad (Backup BD -> Fichero Texto)");
            System.out.println("7. Cargar/Alta por lotes desde fichero '" + FICHERO_NUEVOS + "'");
            System.out.println("8. Salir");
            System.out.print("Seleccione una opción: ");

            String opcion = sc.nextLine();

            try {
                switch (opcion) {
                    case "1":
                        /**
                         * 5.1 Mostrar la informacion existente en la base de datos de todos los empleados
                         */
                        List<Empleados> todos = gestionDB.obtenerTodosEmpleados();
                        if (todos.isEmpty()) {
                            System.out.println("No hay empleados registrados en la base de datos.");
                        } else {
                            System.out.println("\n--- LISTA DE EMPLEADOS ---");
                            for (Empleados emp : todos) {
                                emp.imprime();
                            }
                        }
                        break;

                    case "2":
                        /**
                         * 5.2 Mostrar el salario existente en la base de datos de un empleado por su dni
                         */
                        System.out.print("Introduce el DNI del empleado: ");
                        String dniSalario = sc.nextLine().trim();
                        Integer salario = gestionDB.obtenerSueldoPorDni(dniSalario);
                        if (salario != null) {
                            System.out.println("El salario del empleado con DNI " + dniSalario + " es: " + salario + " €");
                        } else {
                            System.out.println("No se encontró ningún registro para el DNI: " + dniSalario);
                        }
                        break;

                    case "3":
                        /**
                         * 5.3 Submenu que permite modificar todos los datos de los empleados almacenados en la base de datos
                         */
                        System.out.print("Introduce el DNI del empleado a modificar: ");
                        String dniModif = sc.nextLine().trim();
                        Empleados empMod = gestionDB.obtenerEmpleadoPorDni(dniModif);

                        if (empMod == null) {
                            System.out.println("Empleado no encontrado.");
                            break;
                        }

                        System.out.println("\n--- MODIFICAR EMPLEADO: " + empMod.nombre + " ---");
                        System.out.println("1. Cambiar Nombre");
                        System.out.println("2. Cambiar Sexo");
                        System.out.println("3. Cambiar Categoría");
                        System.out.println("4. Incrementar un año trabajado");
                        System.out.print("Seleccione qué desea modificar: ");
                        String opSub = sc.nextLine();

                        switch (opSub) {
                            case "1":
                                System.out.print("Nuevo Nombre: ");
                                empMod.nombre = sc.nextLine().trim();
                                break;
                            case "2":
                                System.out.print("Nuevo Sexo (M/F): ");
                                empMod.sexo = sc.nextLine().trim().charAt(0);
                                break;
                            case "3":
                                System.out.print("Nueva Categoría (1-10): ");
                                int nuevaCat = Integer.parseInt(sc.nextLine().trim());
                                empMod.setCategoria(nuevaCat);
                                break;
                            case "4":
                                empMod.incrAnyo();
                                System.out.println("Años incrementados a: " + empMod.anyos);
                                break;
                            default:
                                System.out.println("Opción no válida.");
                                continue;
                        }

                        gestionDB.guardarOActualizarEmpleado(empMod);
                        System.out.println("Empleado actualizado correctamente. Sueldo recalculado automáticamente.");
                        break;

                    case "4":
                        /**
                         * 5.4 Recalcular y actualizar el sueldo de un empleado
                         */
                        System.out.print("Introduce el DNI del empleado a recalcular: ");
                        String dniRec = sc.nextLine().trim();
                        boolean exito = gestionDB.recalcularSueldoEmpleado(dniRec);
                        if (exito) {
                            System.out.println("Sueldo recalculado y actualizado con éxito.");
                        } else {
                            System.out.println("No se pudo recalcular: Empleado no encontrado.");
                        }
                        break;

                    case "5":
                        /**
                         * 5.5 Recalcular y actualizar los sueldos de todos los empleados
                         */
                        gestionDB.recalcularTodosSueldos();
                        System.out.println("Se han recalculado y actualizado los sueldos de TODOS los empleados.");
                        break;

                    case "6":
                        /**
                         * 5.6 Realizar una copia de seguridad de la base de datos en fichero de texto
                         */
                        List<Empleados> listaBackup = gestionDB.obtenerTodosEmpleados();
                        GestionFicheros.escribirEmpleadosTexto(listaBackup, FICHERO_TEXTO);
                        System.out.println("Copia de seguridad realizada correctamente en " + FICHERO_TEXTO);
                        break;

                    case "7":
                        /**
                         * Alta por lotes utilizando la sobrecarga de altaEmpleado
                         */
                        File fileNuevos = new File(FICHERO_NUEVOS);
                        gestionAltas.altaEmpleado(fileNuevos);
                        break;

                    case "8":
                        salir = true;
                        System.out.println("Saliendo de la aplicación...");
                        break;

                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            } catch (SQLException e) {
                System.err.println("Error en la Base de Datos: " + e.getMessage());
            } catch (DatosNoCorrectosException e) {
                System.err.println("Error en los datos del empleado: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.err.println("Error de formato numérico: Introduce un valor entero válido.");
            } catch (Exception e) {
                System.err.println("Ha ocurrido un error inesperado: " + e.getMessage());
            }
        }

        sc.close();
    }
}