
import ma.projet.beans.Employe;
import ma.projet.beans.Salle;
import ma.projet.beans.Service;
import ma.projet.employe.EmployeService;
import ma.projet.service.MachineService;
import ma.projet.service.SalleService;
import ma.projet.service.ServiceService;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author LACHGAR
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SalleService ss = new SalleService();
        //création des Salles
        ss.create(new Salle("I", "Informatique"));
        ss.create(new Salle("A", "Arabe"));
        ss.create(new Salle("C", "Comptailité"));
        //La liste des salles
        for (Salle s : ss.getAll()) {
            System.out.println("" + s.getCode());
        }
        
        ServiceService ss1=new ServiceService();
        EmployeService es=new EmployeService();
       
       
       /* for (Employe es1 : es.getAll()) {
            if(es1.getChef()== null){
                System.out.print("le cheef est "+es1.getNom() );
                System.out.println(es1.getEmployees());    
            }
        }*/
      
    }
}
