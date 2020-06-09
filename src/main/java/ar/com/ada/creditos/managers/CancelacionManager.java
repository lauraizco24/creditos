package ar.com.ada.creditos.managers;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import javax.persistence.Query;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import ar.com.ada.creditos.entities.Cancelacion;
import ar.com.ada.creditos.entities.Cliente;
import ar.com.ada.creditos.entities.Prestamo;
import ar.com.ada.creditos.entities.reportes.*;

public class CancelacionManager {

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

    public void create(Cancelacion cancelacion) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(cancelacion);

        session.getTransaction().commit();
        session.close();
    }

    public Cancelacion read(int cancelacionid) {
        Session session = sessionFactory.openSession();

        Cancelacion cancelacion = session.get(Cancelacion.class, cancelacionid);

        session.close();

        return cancelacion;
    }

    public void update(Cancelacion cancelacion) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(cancelacion);

        session.getTransaction().commit();
        session.close();
    }

    public void delete(Cancelacion cancelacion) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(cancelacion);

        session.getTransaction().commit();
        session.close();
    }

    public Cancelacion buscarPorIdDeCancelacion(int idDeCancelacion) {

        Session session = sessionFactory.openSession();

        // SQL Injection vulnerability exposed.
        // Deberia traer solo aquella del nombre y con esto demostrarmos que trae todas
        // si pasamos
        // como nombre: "' or '1'='1"
        Query query = session.createNativeQuery(
                "SELECT * FROM cancelacion where cancelacion_id = '" + idDeCancelacion + "'", Cancelacion.class);

        Cancelacion cancelacionPorID = (Cancelacion) query.getSingleResult();

        return cancelacionPorID;

    }

    public Boolean eliminacionLogica(int idDeCancelacion) {
        Session session = sessionFactory.openSession();

        Query query = session.createNativeQuery("Update cancelacion SET eliminado = 1 Where cancelacion_id = ?",
                Cancelacion.class);
        query.setParameter(1, idDeCancelacion);

        session.beginTransaction();
        int modificados = query.executeUpdate();
        if (modificados == 1) {
            session.getTransaction().commit();
            session.close();
            return true;
        } else {
            session.getTransaction().rollback();
            session.close();
            return false;
        }


    }

    public List<ReporteCancelaciones> generarReporteDeCancelaciones(){

        Session session = sessionFactory.openSession();

        Query queryReporteCancelaciones = session.createNativeQuery("Select * From cancelacion c",ReporteCancelaciones.class);
        
        List<ReporteCancelaciones> reporteDeCancelaciones = queryReporteCancelaciones.getResultList();

        

        return reporteDeCancelaciones;
        
    }
    



}