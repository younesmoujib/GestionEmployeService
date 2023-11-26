/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.projet.domaine;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import ma.projet.beans.Employe;
import ma.projet.beans.Service;
import ma.projet.employe.EmployeService;
import ma.projet.service.ServiceService;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartModel;
import org.primefaces.model.chart.ChartSeries;

/**
 *
 * @author hp
 */


@ManagedBean(name="employeBean")
public class EmployeBean {
    private Employe employe ;
    private EmployeService employe1;
    private Employe chef;

    public static ChartModel getBarModel() {
        return barModel;
    }

    
    private Service service;
    private static ChartModel barModel;

    public Employe getSelectedChef() {
        return selectedChef;
    }

    public void setSelectedChef(Employe selectedChef) {
        this.selectedChef = selectedChef;
    }
    private ServiceService serviceService; 
    private Employe selectedChef;
    
    
    private List<Employe> employes;

    public EmployeBean() {
        employe=new Employe();
        employe = new Employe();
        service = new Service();
        chef = new Employe();
        employe.setChef(chef);
        employe.setService(service);
        employe1=new EmployeService();
       serviceService=new ServiceService();
    }

    public List<Employe> getEmployes() {
        if(employes == null){
            employes=employe1.getAll();
        }
        return employes;
    }

    public void setEmployes(List<Employe> employes) {
        this.employes = employes;
    }
    
    public  String onDeleteAction(){
        employe.setService(null);
        employe1.delete(employe);
        return null;
    }
    public void onCancel(RowEditEvent event) {
    }
    public void onEdit(RowEditEvent event) {
        try  {
        employe = (Employe) event.getObject();
        Service s = serviceService.getById(this.employe.getService().getId());
    
        employe.setService(s);
        employe.getService().setCode(s.getCode());
        employe1.update(employe);
    } catch (Exception e) {
       
            e.printStackTrace();
    }
        
    }

    public String onCreateAction() {
        if (employe.getChef() != null) {
            Employe chef = employe1.getById(employe.getChef().getId());
           
            employe.setChef(chef);
        } else {
            employe.setChef(null);
        }
        chef.setChief(true);
       employe1.create(employe);
        employe = new Employe();
        service = new Service();
        chef = new Employe();
        employe.setChef(null);
        employe.setService(null);
        return null;
    }


    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }
     public ChartModel initBarModel() {
        CartesianChartModel model = new CartesianChartModel();
        ChartSeries emp = new ChartSeries();
        emp.setLabel("employe by services");
        model.setAnimate(true);
        for (Object[] m :employe1.nbremployebyservice()) {
            System.out.println(m[1]);
            emp.set(m[1], Integer.parseInt(m[0].toString()));
        }
        model.addSeries(emp);

        return model;
    }
       public void handleFileUpload(FileUploadEvent event) {
        try {
            InputStream inputStream = event.getFile().getInputstream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            byte[] fileContent = outputStream.toByteArray();
            System.out.println("fileContent: " + Arrays.toString(fileContent));
            employe.setImage(fileContent);
            outputStream.close();
            inputStream.close();

            FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (IOException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error uploading file");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    
  
    public ByteArrayInputStream getImageStream(byte[] imageBytes) {
        if (null != imageBytes) {
            ByteArrayInputStream b = new ByteArrayInputStream(imageBytes);
            int byteRead;
            while ((byteRead = b.read()) != -1) {
                System.out.print((char) byteRead);
            }
            return b;
        } else {
            return null;
        }

    }
    
    public String getImageBase64(byte[] imageBytes) {
    if (imageBytes != null && imageBytes.length > 0) {
        return Base64.getEncoder().encodeToString(imageBytes);
    }
    return "";
}
     
    
    
}
