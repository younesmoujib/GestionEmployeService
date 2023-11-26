/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.projet.domaine;

/**
 *
 * @author hp
 */
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import ma.projet.beans.Employe;
import ma.projet.beans.Service;
import ma.projet.employe.EmployeService;
import ma.projet.service.ServiceService;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@Named("treeBasicView")
@ViewScoped
public class BasicView implements Serializable {

    private TreeNode root;
    private ServiceService serviceService;
    private EmployeService employeService;

    @PostConstruct
    public void init() {
        root = new DefaultTreeNode("Root", null);
        serviceService = new ServiceService();
        employeService = new EmployeService();

        List<Service> serviceList = serviceService.getAll();

        for (Service service : serviceList) {
            TreeNode serviceNode = new DefaultTreeNode(new NodeData("service", service), root);

            List<Employe> chefs = serviceService.getChiefsForService(service);

            for (Employe chef : chefs) {
                TreeNode chefNode = new DefaultTreeNode(new NodeData("chef", chef), serviceNode);

                List<Employe> employees = employeService.getEmployeesForChef(chef);

                for (Employe employee : employees) {
                    new DefaultTreeNode(new NodeData("employe", employee.getNom()), chefNode);
                }
            }
        }
    }

    public TreeNode getRoot() {
        return root;
    }

    public class NodeData implements Serializable {
        private String type;
        private Object data;

        public NodeData(String type, Object data) {
            this.type = type;
            this.data = data;
        }

        public String getType() {
            return type;
        }

        public Object getData() {
            return data;
        }
    }
}
