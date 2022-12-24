import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceItemTable {
    List<InvoiceItem> invoiceItemList= new ArrayList<InvoiceItem>();

    void fillInvoiceItemTable(String dir){
        fileManager fm=new fileManager();

        String[][] temp=fm.readCsvFile(dir);

        for(int i=0;i< temp.length;i++) {
            InvoiceItem e = new InvoiceItem();
            e.setInvoiceItemNumber(Integer.parseInt(temp[i][0]));

            e.setItem_Name(temp[i][1]);
            e.setItem_Price(Float.valueOf(temp[i][2]));
            e.setCount(Integer.parseInt(temp[i][3]));
            e.setItemTotal(e.getItem_Price()*e.getCount());
            invoiceItemList.add(e);


        }
    }
                                                        //count the rows according to the filter
    int rowCounter(String filter)  {
        int rowcount=0;
        for(int i=0;i<invoiceItemList.size();i++){
            if(invoiceItemList.get(i).getInvoiceItemNumber()!=Integer.parseInt(filter))continue;
             rowcount++;
        }
        return  rowcount;
    }
    String[][] getInvoiceItemData(String filter){

        int rowCount= invoiceItemList.size(), current=0; //current is an iterator for the temp string
        String[][] temp= new String[rowCount][invoiceItemList.get(0).getColSize()];
        float Total=0;

        for(int i=0;i<invoiceItemList.size();i++){

                                                            // filters according to row selected from invoice table
            if(invoiceItemList.get(i).getInvoiceItemNumber()!=Integer.parseInt(filter))continue;

            temp[current][0]= String.valueOf(invoiceItemList.get(i).getInvoiceItemNumber());
            temp[current][1]=invoiceItemList.get(i).getItem_Name();
            temp[current][2]= String.valueOf(invoiceItemList.get(i).getItem_Price());
            temp[current][3]= String.valueOf(invoiceItemList.get(i).getCount());
            temp[current][4]= String.valueOf(invoiceItemList.get(i).getItemTotal());
            current++;


        }





        return temp;
    }
    String[][] getALLInvoiceItemData(){
        int rowCount= invoiceItemList.size();
        String[][] temp= new String[rowCount][invoiceItemList.get(0).getColSize()];
        System.out.println(rowCount);
        for(int i=0;i<invoiceItemList.size();i++){
            temp[i][0]= String.valueOf(invoiceItemList.get(i).getInvoiceItemNumber());
            temp[i][1]=invoiceItemList.get(i).getItem_Name();
            temp[i][2]= String.valueOf(invoiceItemList.get(i).getItem_Price());
            temp[i][3]= String.valueOf(invoiceItemList.get(i).getCount());
            temp[i][4]= String.valueOf(invoiceItemList.get(i).getItemTotal());



        }
        return temp;


    }
    void invoiceSaveChanges(String[][] temp,String filter){
        int sizeofList=invoiceItemList.size();
        List<InvoiceItem> tempItemList= new ArrayList<InvoiceItem>();

        for(int i=0;i<sizeofList;i++){

            if(invoiceItemList.get(i).getInvoiceItemNumber()==Integer.parseInt(filter))continue;
            tempItemList.add(invoiceItemList.get(i));
        }

                                                                            //adds the new data
        for (int i=0;i<temp.length;i++){
            InvoiceItem e = new InvoiceItem();
            if(temp[i][0]==null||temp[i][0].isEmpty())continue;
            e.setInvoiceItemNumber(Integer.parseInt(temp[i][0]));
            e.setItem_Name(temp[i][1]);
            e.setItem_Price(Float.valueOf(temp[i][2]));
            e.setCount(Integer.parseInt(temp[i][3]));
            e.setItemTotal(Float.valueOf(temp[i][4]));
            tempItemList.add(e);
        }
        invoiceItemList=tempItemList;


    }
                        //clears the table

    void removeALl(){
        invoiceItemList.clear();
    }

    float getTotalInvoice(int ID){
        float total=0;
        for(int i=0;i<invoiceItemList.size();i++){
            if(invoiceItemList.get(i).getInvoiceItemNumber()!=ID)continue;
            total+=invoiceItemList.get(i).getItemTotal();
        }
        return total;
    }
}
