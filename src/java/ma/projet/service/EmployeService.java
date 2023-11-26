/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.projet.employe;

import java.util.ArrayList;
import java.util.List;
import ma.projet.beans.Employe;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

/**
 *
 * @author hp
 */
public class EmployeService implements IDao<Employe>{

    @Override
    public boolean create(Employe o) {
       Session session  = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(o);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public boolean update(Employe o) {
        Session session  = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(o);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public boolean delete(Employe o) {
        Session session  = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(o);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public Employe getById(int id) {
       Employe employe=null;
       Session session  = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        employe  = (Employe) session.get(Employe.class, id);
        session.getTransaction().commit();
        return employe;
    }

    @Override
    public List<Employe> getAll() {
          List <Employe> employes = null;
        Session session  = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        employes  = session.createQuery("from Employe").list();
        session.getTransaction().commit();
        return employes;
    }
    public List<Object[]> nbremployebyservice(){
        List<Object[]> emp = null;
        Session session  = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
emp  = session.createQuery("select count(e.id), s.code from Employe e right join e.service s group by s.code").list();
        session.getTransaction().commit();
        return emp;
    }
    
    public List<Employe> getEmployeesForChef(Employe chef) {

        List<Employe> employes = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        employes = session.createQuery("FROM Employe e WHERE e.chef = :chef").setParameter("chef", chef).list();
        session.getTransaction().commit();
        return employes;

    }
    
}
