/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartdia;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class Documento {

    Documento(String url) throws JRException{
        EnlaceDatos db = new EnlaceDatos("localhost", "smarthdia", "root", "toor");
        
        JasperReport reporte = JasperCompileManager.compileReport(url);
        JasperPrint print = JasperFillManager.fillReport(reporte, null, db.getCnx());
        JasperViewer viewer = new JasperViewer(print, false);
        viewer.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        viewer.setVisible(true);
    }
}
