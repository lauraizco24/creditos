package ar.com.ada.creditos.managers;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import ar.com.ada.creditos.entities.Cliente;
import ar.com.ada.creditos.entities.Prestamo;
import ar.com.ada.creditos.entities.reportes.*;

public class PrestamoManager {

    protected SessionFactory sessionFactory;

    public void setup() {

        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure() // configures settings
                                                                                                  // from
                                                                                                  // hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception ex) {
            StandardServiceRegistryBuilder.destroy(registry);
            throw ex;
        }

    }

    public void exit() {
        sessionFactory.close();
    }

    public void create(Prestamo prestamo) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(prestamo);

        session.getTransaction().commit();
        session.close();
    }

    public Prestamo read(int prestamoid) {
        Session session = sessionFactory.openSession();

        Prestamo prestamo = session.get(Prestamo.class, prestamoid);

        session.close();

        return prestamo;
    }

    public void update(Prestamo prestamo) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(prestamo);

        session.getTransaction().commit();
        session.close();
    }

    public void delete(Prestamo prestamo) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(prestamo);

        session.getTransaction().commit();
        session.close();
    }

    public List<Prestamo> buscarTodosPrestamos() {

        Session session = sessionFactory.openSession();

        /// NUNCA HARCODEAR SQLs nativos en la aplicacion.
        // ESTO es solo para nivel educativo
        Query query = session.createNativeQuery("SELECT * FROM prestamo", Prestamo.class);

        List<Prestamo> todosLosPrestamos = query.getResultList();

        return todosLosPrestamos;

    }

    public List<ReportePrestamoPorCliente> generarReportePrestamoCliente(int clienteid) {

        Session session = sessionFactory.openSession();

        Query queryReportesPorCliente = session.createNativeQuery(
                "SELECT c.cliente_id, c.nombre, count(*) cantidad, max(p.importe) maximo, sum(p.importe)  total FROM cliente c inner join prestamo p on c.cliente_id = p.cliente_id WHERE c.cliente_id = ? group by c.cliente_id, c.nombre",
                ReportePrestamoPorCliente.class);
        queryReportesPorCliente.setParameter(1, clienteid);

        List<ReportePrestamoPorCliente> ReportePrestamosXCliente = queryReportesPorCliente.getResultList();

        return ReportePrestamosXCliente;


    }

    public List<ReporteDePrestamos> generarReporteDePrestamos(){

        Session session = sessionFactory.openSession();

        Query queryReportePrestamos = session.createNativeQuery("Select count(*) cantidad, sum(p.importe) total From prestamo p",ReporteDePrestamos.class);
        
        List<ReporteDePrestamos> reporteDePrestamos = queryReportePrestamos.getResultList();

        return reporteDePrestamos;
        
    }

    public Prestamo buscarPorIdPrestamo(int idDelPrestamo) {

        Session session = sessionFactory.openSession();

        // SQL Injection vulnerability exposed.
        // Deberia traer solo aquella del nombre y con esto demostrarmos que trae todas
        // si pasamos
        // como nombre: "' or '1'='1"
        Query query = session.createNativeQuery("SELECT * FROM prestamo where prestamo_id = '" + idDelPrestamo + "'", Prestamo.class);

        Prestamo prestamoPorID = (Prestamo) query.getSingleResult();

        return prestamoPorID;

    }

}