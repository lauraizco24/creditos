package ar.com.ada.creditos;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import ar.com.ada.creditos.entities.*;
import ar.com.ada.creditos.excepciones.*;
import ar.com.ada.creditos.managers.*;
import ar.com.ada.creditos.services.CancelacionService;
import ar.com.ada.creditos.services.ReporteService;

public class ABM {

    public static Scanner Teclado = new Scanner(System.in);

    protected ClienteManager ABMCliente = new ClienteManager();
    protected PrestamoManager ABMPrestamo = new PrestamoManager();
    protected CancelacionManager ABMCancelacion = new CancelacionManager();
    protected ReporteService reporteService = new ReporteService(ABMPrestamo);
    protected ReporteService reporteServiceC = new ReporteService(ABMCancelacion);
    protected CancelacionService cancelacionService = new CancelacionService(ABMCancelacion, ABMPrestamo);

    public void iniciar() throws Exception {

        try {

            ABMCliente.setup();
            ABMPrestamo.setup();
            ABMCancelacion.setup();

            printOpciones();

            int opcion = Teclado.nextInt();
            Teclado.nextLine();

            while (opcion > 0) {

                switch (opcion) {
                    case 1:

                        try {
                            alta();
                        } catch (ClienteDNIException exdni) {
                            System.out.println("Error en el DNI. Indique uno valido");
                        }
                        break;

                    case 2:
                        baja();
                        break;

                    case 3:
                        modifica();
                        break;

                    case 4:
                        listar();
                        break;

                    case 5:
                        listarPorNombre();
                        break;
                    case 6:
                        listarPrestamos();
                        break;
                    case 7:
                        nuevoPrestamo();
                        break;
                    case 8:
                        totalPrestamosPorCliente();
                        break;
                    case 9:
                        totalPrestamos();
                        break;
                    case 10:
                        nuevaCancelacion();
                        break;
                    case 11:
                        eliminarCancelacion();
                        break;

                    case 12:
                        listarCancelaciones();
                        break;
                    case 13:
                        listarCancelacionesEliminadas();
                        break;
                    case 14:
                        reporteCancelacionesPorCliente();
                        break;

                    default:
                        System.out.println("La opcion no es correcta.");
                        break;
                }

                printOpciones();

                opcion = Teclado.nextInt();
                Teclado.nextLine();
            }

            // Hago un safe exit del manager
            ABMCliente.exit();

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Que lindo mi sistema,se rompio mi sistema");
            throw e;
        } finally {
            System.out.println("Saliendo del sistema, bye bye...");

        }

    }

    public void alta() throws Exception {
        Cliente cliente = new Cliente();
        System.out.println("Ingrese el nombre:");
        cliente.setNombre(Teclado.nextLine());
        System.out.println("Ingrese el DNI:");
        cliente.setDni(Teclado.nextInt());
        Teclado.nextLine();
        System.out.println("Ingrese la domicilio:");
        cliente.setDomicilio(Teclado.nextLine());

        System.out.println("Ingrese el Domicilio alternativo(OPCIONAL):");

        String domAlternativo = Teclado.nextLine();

        if (domAlternativo != null)
            cliente.setDomicilioAlternativo(domAlternativo);

        /*
         * Prestamo prestamo = new Prestamo(); BigDecimal importePrestamo = new
         * BigDecimal(5000); prestamo.setImporte(importePrestamo); // Forma 1
         * prestamo.setFecha(new Date());// Fecha actual de la maquina forma 2
         * prestamo.setCuotas(10);// Le vamos a dar 10 cuotas por defecto
         * prestamo.setFechaAlta(new Date());// Esta fecha la genera la maquina
         * prestamo.setCliente(cliente);
         */

        ABMCliente.create(cliente);

        /*
         * Si concateno el OBJETO directamente, me trae todo lo que este en el metodo
         * toString() mi recomendacion es NO usarlo para imprimir cosas en pantallas, si
         * no para loguear info Lo mejor es usar:
         * System.out.println("Cliente generada con exito.  " + cliente.getClienteId);
         */

        System.out.println("Cliente generada con exito.  " + cliente);

    }

    public void baja() {
        System.out.println("Ingrese el nombre:");
        String nombre = Teclado.nextLine();
        System.out.println("Ingrese el ID de Cliente:");
        int id = Teclado.nextInt();
        Teclado.nextLine();
        Cliente clienteEncontrado = ABMCliente.read(id);

        if (clienteEncontrado == null) {
            System.out.println("Cliente no encontrado.");

        } else {

            try {

                ABMCliente.delete(clienteEncontrado);
                System.out
                        .println("El registro del cliente " + clienteEncontrado.getClienteId() + " ha sido eliminado.");
            } catch (Exception e) {
                System.out.println("Ocurrio un error al eliminar una cliente. Error: " + e.getCause());
            }

        }
    }

    public void bajaPorDNI() {
        System.out.println("Ingrese el nombre:");
        String nombre = Teclado.nextLine();
        System.out.println("Ingrese el DNI de Cliente:");
        String dni = Teclado.nextLine();
        Cliente clienteEncontrado = ABMCliente.readByDNI(dni);

        if (clienteEncontrado == null) {
            System.out.println("Cliente no encontrado.");

        } else {
            ABMCliente.delete(clienteEncontrado);
            System.out.println("El registro del DNI " + clienteEncontrado.getDni() + " ha sido eliminado.");
        }
    }

    public void modifica() throws Exception {
        // System.out.println("Ingrese el nombre de la cliente a modificar:");
        // String n = Teclado.nextLine();

        System.out.println("Ingrese el ID de la cliente a modificar:");
        int id = Teclado.nextInt();
        Teclado.nextLine();
        Cliente clienteEncontrado = ABMCliente.read(id);

        if (clienteEncontrado != null) {

            // RECOMENDACION NO USAR toString(), esto solo es a nivel educativo.
            System.out.println(clienteEncontrado.toString() + " seleccionado para modificacion.");

            System.out.println(
                    "Elija qué dato de la cliente desea modificar: \n1: nombre, \n2: DNI, \n3: domicilio, \n4: domicilio alternativo");
            int selecper = Teclado.nextInt();

            switch (selecper) {
                case 1:
                    System.out.println("Ingrese el nuevo nombre:");
                    Teclado.nextLine();
                    clienteEncontrado.setNombre(Teclado.nextLine());

                    break;
                case 2:
                    System.out.println("Ingrese el nuevo DNI:");
                    Teclado.nextLine();
                    clienteEncontrado.setDni(Teclado.nextInt());
                    Teclado.nextLine();

                    break;
                case 3:
                    System.out.println("Ingrese el nuevo domicilio:");
                    Teclado.nextLine();
                    clienteEncontrado.setDomicilio(Teclado.nextLine());

                    break;
                case 4:
                    System.out.println("Ingrese el nuevo domicilio alternativo:");
                    Teclado.nextLine();
                    clienteEncontrado.setDomicilioAlternativo(Teclado.nextLine());

                    break;

                default:
                    break;
            }

            // Teclado.nextLine();

            ABMCliente.update(clienteEncontrado);

            System.out.println("El registro de " + clienteEncontrado.getNombre() + " ha sido modificado.");

        } else {
            System.out.println("Cliente no encontrado.");
        }

    }

    public void listar() {

        List<Cliente> todos = ABMCliente.buscarTodos();
        for (Cliente c : todos) {
            mostrarCliente(c);
        }
    }

    public void listarPorNombre() {

        System.out.println("Ingrese el nombre:");
        String nombre = Teclado.nextLine();

        List<Cliente> clientes = ABMCliente.buscarPor(nombre);
        for (Cliente cliente : clientes) {
            mostrarCliente(cliente);
        }
    }

    public void listarPrestamos() {

        List<Prestamo> todos = ABMPrestamo.buscarTodosPrestamos();
        for (Prestamo p : todos) {
            mostrarPrestamo(p);
        }
    }

    public void mostrarCliente(Cliente cliente) {

        System.out.print("Id: " + cliente.getClienteId() + " Nombre: " + cliente.getNombre() + " DNI: "
                + cliente.getDni() + " Domicilio: " + cliente.getDomicilio());

        if (cliente.getDomicilioAlternativo() != null)
            System.out.println(" Alternativo: " + cliente.getDomicilioAlternativo());
        else
            System.out.println();
    }

    public void mostrarPrestamo(Prestamo prestamo) {

        System.out.print("Id: " + prestamo.getPrestamoId() + " Importe: " + prestamo.getImporte() + " Cuotas: "
                + prestamo.getCuotas() + " Fecha: " + prestamo.getFechaAlta());
        System.out.println("/n");

    }

    public void nuevoPrestamo() {
        cargarUnPrestamo();
    }

    public void cargarUnPrestamo() {
        Prestamo prestamo = new Prestamo();
        System.out.println("Inserte el id del Cliente: ");
        int idDeCliente = Teclado.nextInt();
        Teclado.nextLine();
        prestamo.setCliente(ABMCliente.buscarPorIdCliente(idDeCliente));
        System.out.println("Inserte el importe del prestamo: ");
        BigDecimal importe = Teclado.nextBigDecimal();
        Teclado.nextLine();
        prestamo.setImporte(importe);
        System.out.println("Inserte la cantidad de cuotas: ");
        int cuotas = Teclado.nextInt();
        Teclado.nextLine();
        prestamo.setCuotas(cuotas);
        /*
         * System.out.println("Inserte la fecha del prestamo: ");
         * System.out.println("Inserte el Año : "); int AnioAlta = Teclado.nextInt();
         * Teclado.nextLine(); System.out.println("Inserte el mes : "); int mesAlta =
         * Teclado.nextInt(); Teclado.nextLine();
         * System.out.println("Inserte el Dia : "); int diaAlta = Teclado.nextInt();
         * Teclado.nextLine(); prestamo.setFecha(new Date(AnioAlta,mesAlta,diaAlta));
         */
        prestamo.setFechaAlta(new Date());
        System.out.println("Introduzca la fecha con formato dd/mm/yyyy");
        String fecha = Teclado.nextLine();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date testDate = null;
        String date = fecha;
        try {
            testDate = df.parse(date);
            System.out.println("Ahora hemos creado un objeto date con la fecha indicada, " + testDate);
        } catch (Exception e) {
            System.out.println("invalid format");
        }

        if (!df.format(testDate).equals(date)) {
            System.out.println("invalid date!!");
        } else {
            System.out.println("valid date");
        }
        prestamo.setFecha(testDate);

        ABMPrestamo.create(prestamo);
        System.out.println("Prestamo creado con exito");
        System.out.println("Id del Prestamo: " + prestamo.getPrestamoId() + " " + "Importe del Prestamo: "
                + prestamo.getImporte() + " " + "Id del Cliente: " + prestamo.getCliente().getClienteId() + " "
                + "DNI del Cliente : " + prestamo.getCliente().getDni());

    }

    public void totalPrestamosPorCliente() {
        System.out.println("Ingrese el Id Del Cliente: ");
        int clienteid = Teclado.nextInt();
        Teclado.nextLine();
        reporteService.imprimirReportePorCliente(clienteid);

    }

    public void totalPrestamos() {
        reporteService.imprimirReportePrestamosTotal();

    }

    public void nuevaCancelacion() {
        System.out.println("Ingrese el id del Prestamo a pagar: ");
        int prestamoId = Teclado.nextInt();
        Teclado.nextLine();

        System.out.println("Ingrese el Importe de la cuota: ");
        BigDecimal importe = Teclado.nextBigDecimal();
        Teclado.nextLine();

        System.out.println("Ingrese la cuota a pagar: ");
        int cuota = Teclado.nextInt();
        Teclado.nextLine();

        System.out.println("Ingrese la fecha de Cancelacion en el formato dd/mm/yyyy");
        String fechaCancelacion = Teclado.nextLine();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date testDate = null;
        String date = fechaCancelacion;
        try {
            testDate = df.parse(date);
            System.out.println("");
        } catch (Exception e) {
            System.out.println("invalid format");
        }

        if (!df.format(testDate).equals(date)) {
            System.out.println("invalid date!!");
        } else {
            System.out.println("valid date");
        }

        cancelacionService.generarImprimirCancelacion(prestamoId, importe, cuota, testDate);

    }

    public void eliminarCancelacion() {

        System.out.println("Ingrese el id de la cancelacion: ");
        int cancelacionId = Teclado.nextInt();
        Teclado.nextLine();

        cancelacionService.eliminarUnaCancelacion(cancelacionId);
    }

    public void listarCancelaciones() {

        reporteServiceC.imprimirReporteCancelaciones();

    }

    public void listarCancelacionesEliminadas() {

        reporteServiceC.listarCancelacionesEliminadas();

    }

    public void reporteCancelacionesPorCliente() {
        

    }

    public static void printOpciones() {
        System.out.println("=======================================");
        System.out.println("");
        System.out.println("1. Para agregar un cliente.");
        System.out.println("2. Para eliminar un cliente.");
        System.out.println("3. Para modificar un cliente.");
        System.out.println("4. Para ver el listado.");
        System.out.println("5. Buscar un cliente por nombre especifico(SQL Injection)).");
        System.out.println("6. Listado de Prestamos");
        System.out.println("7. Agregar un prestamo");
        System.out.println("8. Reporte de Prestamos por Cliente");
        System.out.println("9. Reporte Total de Prestamos y cantidad Total de Dinero Prestado");
        System.out.println("10.Cargar una nueva cancelacion");
        System.out.println("11. Eliminar una Cancelacion");
        System.out.println("12. Imprimir Reporte de Cancelaciones(TOTAL)");
        System.out.println("13. Reporte de Cancelaciones Eliminadas");
        System.out.println("14. Reporte de Cancelaciones por Cliente");
        System.out.println("0. Para terminar.");
        System.out.println("");
        System.out.println("=======================================");
    }
}