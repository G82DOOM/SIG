import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;

public class main {
   static InvoiceTable invoiceTableInstance= new InvoiceTable();
   static String InvoiceHeaderDir,InvoiceLineDir;

    static InvoiceItemTable invoiceItemTableinstance=new InvoiceItemTable();
                                                                 //get invoice data
    static String[][] getCsvInvoiceData(){
        String[][] temp= invoiceTableInstance.getInvoiceData();
        return temp;
    }
                                                                     //get invoice item data
    static String[][] getCsvInvoiceItemData(String filter){
        String[][] temp= invoiceItemTableinstance.getInvoiceItemData(filter);
        return temp;
    }
                                                                    //remove an invoice
    void removeInvoice(int row){
        invoiceTableInstance.removeInvoice(row);
    }
    void createInvoice(String[] tempInvoice){
        invoiceTableInstance.createInvoice(tempInvoice);
    }
    void saveInvoice(String[][] tempString,String temp){
        invoiceItemTableinstance.invoiceSaveChanges(tempString,temp);
    }
    void saveToFile(String HeadDir,String LineDir){
        fileManager fm =new fileManager();

        fm.saveToFile(invoiceTableInstance,invoiceItemTableinstance,HeadDir,LineDir);
    }
   // void loadFile(String )

    void loadFile(String HeadDir,String LineDir){

        InvoiceHeaderDir= HeadDir;
        InvoiceLineDir=LineDir;
        invoiceTableInstance.removeALl();
        invoiceItemTableinstance.removeALl();
        invoiceItemTableinstance.fillInvoiceItemTable(InvoiceLineDir);
        invoiceTableInstance.fillInvoiceTable(InvoiceHeaderDir,invoiceItemTableinstance);

    }
    String checkExtention(String temp){
        fileManager fm =new fileManager();

        return fm.getFileExtension(temp);
    }
    boolean fileFound(String dir){
        fileManager fm =new fileManager();

        return fm.FileNotFoundException(dir);

    }





    public static void main(String[] arg){
        InvoiceHeaderDir= "InvoiceHeader.csv";
        InvoiceLineDir="InvoiceLine.csv";

        invoiceItemTableinstance.fillInvoiceItemTable(InvoiceLineDir);
        invoiceTableInstance.fillInvoiceTable(InvoiceHeaderDir,invoiceItemTableinstance);
        GUI gui=new GUI();
        gui.setVisible(true);
        gui.pack();




    }
}
