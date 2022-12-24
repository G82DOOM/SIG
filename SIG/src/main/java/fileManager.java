import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class fileManager {
    boolean FileNotFoundException(String dir){
        Scanner sc;
        try {
            sc = new Scanner(new File(dir));
        } catch (FileNotFoundException e) {
           return true;
        }
        return false;
    }

    String getFileExtension(String fullName) {
        if(fullName==null||fullName.isEmpty())return "";
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
    int rowCount(String dir) {
        int row = 0;
        Scanner sc;
        try {
            sc = new Scanner(new File(dir));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<String> csvIterator = new ArrayList<String>();
        //parsing a CSV file into the constructor of Scanner class
        sc.useDelimiter(",");
        int i = 0;
        while (sc.hasNextLine()) {
            csvIterator.add(sc.nextLine());
            String[] checker = csvIterator.get(i).split(",");
            i++;

            row++;

        }

        return row;
    }

    int colCount(String dir, int row) {
        int col = 0;
        Scanner sc;
        try {
            sc = new Scanner(new File(dir));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        //parsing a CSV file into the constructor of Scanner class
        sc.useDelimiter(",");
        List<String> csvIterator = new ArrayList<String>();
        csvIterator.add(sc.nextLine());
        String[] checker = csvIterator.get(0).split(",");
        col = checker.length;


        //inorder to get the number of cols we must divide them by rows

        return col;
    }

    String[][] readCsvFile(String dir) {

        Scanner sc;
        try {
            sc = new Scanner(new File(dir));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        //parsing a CSV file into the constructor of Scanner class
        sc.useDelimiter(",");

        //setting comma as delimiter pattern
        //counting rows and coulmns to initilaize them in the string array
        int row = rowCount(dir), col = (colCount(dir, row));


        String[][] schedule = new String[row][col];
        List<String> tempList = new ArrayList<String>();


        //exctracting data

        while (sc.hasNextLine()) {
            tempList.add(sc.nextLine());
        }

        String[] temp = new String[col];
        //converting to 2d string array
        for (int k = 0; k < row; k++) {
            temp = tempList.get(k).split(",");

            for (int j = 0; j < col; j++) {
                schedule[k][j] = temp[j];
            }


        }

        sc.close();
        return schedule;
    }

    //commits data to file
    void saveToFile(InvoiceTable invoiceTable, InvoiceItemTable invoiceItemTable, String headDir, String lineDir) {    // create FileWriter object with file as parameter

        File InvoiceHeadFile = new File(headDir);
        File InvoiceItemFile = new File(lineDir);
        try {
                                                                          // invoiceHead file
            FileWriter outputHeadfile = new FileWriter(InvoiceHeadFile);


            CSVWriter writerHead = new CSVWriter(outputHeadfile);
            String[][] tempInvoice=invoiceTable.getInvoiceData();
            for (int i=0;i<tempInvoice.length;i++){

                    writerHead.writeNext(tempInvoice[i],false);

            }

            writerHead.close();

                                                                                            //invoiceLine file
            FileWriter outputItemFile = new FileWriter(InvoiceItemFile);
            CSVWriter writerItem = new CSVWriter(outputItemFile);
            String[][] tempItem=invoiceItemTable.getALLInvoiceItemData();
            for (int i=0;i<tempItem.length;i++){

                writerItem.writeNext(tempItem[i],false);

            }
            // closing writer connection
            writerItem.close();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }
}
