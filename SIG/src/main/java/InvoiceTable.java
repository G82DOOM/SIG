import java.util.ArrayList;
import java.util.List;

public class InvoiceTable {
    List<Invoice> invoiceTable=new ArrayList<Invoice>();

    void  fillInvoiceTable(String dir,InvoiceItemTable Item){
        fileManager fm=new fileManager();

        String[][] temp=fm.readCsvFile(dir);

        for(int i=0;i< temp.length;i++){
            Invoice e=new Invoice();
            System.out.println(temp[i][0]);
            e.setInvoiceNumber(Integer.parseInt(temp[i][0].trim()));
            e.setInvoiceDate(temp[i][1]);
            e.setCustomerName(temp[i][2]);
            e.setTotal(Item.getTotalInvoice(e.invoiceNumber));
            invoiceTable.add(e);


        }

    }
    String[][] getInvoiceData(){

        String[][] temp= new String[invoiceTable.size()][invoiceTable.get(0).getColSize()];

        for(int i=0;i< invoiceTable.size();i++){
            temp[i][0]= String.valueOf(invoiceTable.get(i).getInvoiceNumber());
            temp[i][1]=invoiceTable.get(i).getInvoiceDate();
            temp[i][2]=invoiceTable.get(i).getCustomerName();
            temp[i][3]= String.valueOf(invoiceTable.get(i).getTotal());

        }


        return temp;
    }
    void removeInvoice(int row){
        invoiceTable.remove(row);

    }
    void createInvoice(String[] tempInvoice){
        Invoice e=new Invoice();

        e.setInvoiceNumber(Integer.parseInt(tempInvoice[0]));
        e.setInvoiceDate(tempInvoice[1]);
        e.setCustomerName(tempInvoice[2]);
        e.setTotal(Float.valueOf(tempInvoice[3]));
        invoiceTable.add(e);
    }
                            //clears the table
    void removeALl(){
        invoiceTable.clear();
    }
}
