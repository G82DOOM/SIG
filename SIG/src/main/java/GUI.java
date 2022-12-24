import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class GUI extends JFrame  {
    JPanel leftPanel;
    JPanel rightPanel;
    JPanel createAndDeletePanel;
    JPanel datepanel;
    JPanel customerNamePanel;
    JPanel saveAndCancelPanel;
    JMenuBar myMenubar;
    JMenu fileMenu;
    JMenuItem saveFileMenuItem;
    JMenuItem loadFileMenuItem;

    JTable invoiceTable;

    String[] invoiceTableCols={"No","Date","Customer","Total"};
    String[][] invoiceTableData={};



    JButton creatInvoice;

    JButton deleteInvoice;
                                            //detailed customer labels
    JLabel invoiceNumber,invoiceTotal,invoiceDate,invoiceCustomerName;


    JTextField invoiceDateTxt,customerNameTxt;

    JTable invoiceItems;
    String[] invoiceItemsCols={"No.","Item Name","Item Price","Count","ItemTotal"};
    String[][] invoiceItemsData={};

    JButton saveChange;

    JButton cancelChange;
    JLabel l;
    main mainInstance=new main();
    int selectedRowIndex;

                                                                            //fills table with data


    void fillTable(String[][] csvData,DefaultTableModel model){
        for(int i=0;i<csvData.length;i++){
            String[] temp= new String[csvData[0].length];
            for(int j=0;j<csvData[0].length;j++) {

                temp[j]= csvData[i][j];
            }
            model.addRow(temp);

        }
            model.addRow(new  String[]{"","","","",""});


    }
                                                                                            //displaying invoiceItem data
    void DisplayInvoiceDetailedData(int rowIndex){
        DefaultTableModel model = (DefaultTableModel)invoiceTable.getModel();
         invoiceNumber.setText("invoice Number "+(String)model.getValueAt(rowIndex,0));
         invoiceDateTxt.setText((String) model.getValueAt(rowIndex,1));

         customerNameTxt.setText((String) model.getValueAt(rowIndex,2));

         invoiceTotal.setText("Invoice Total "+(String)model.getValueAt(rowIndex,3));

    }
                                                                                            //selecting an invoice to its items
    void invoiceTableSelectedRow(MouseEvent evt) {
        DefaultTableModel ItemsTablemodel = (DefaultTableModel)invoiceItems.getModel();
                                                                                                     // get the selected row index
         selectedRowIndex = invoiceTable.getSelectedRow();
                                                                                                            //return if selected row is empty
        String checker= (String) invoiceTable.getValueAt(selectedRowIndex,0);
        if(checker.isEmpty())return ;
        DisplayInvoiceDetailedData(selectedRowIndex);

        String[][] csvData= main.getCsvInvoiceItemData((String)invoiceTable.getValueAt(selectedRowIndex,0));
        ItemsTablemodel.setRowCount(0);
        fillTable(csvData,ItemsTablemodel);




    }
                                                                                                    //delete invoice
    private void deleteInvoice(int selectedRow) {
        if(selectedRow==-1)return;
        DefaultTableModel model = (DefaultTableModel)invoiceTable.getModel();
        model.removeRow(selectedRow);
        mainInstance.removeInvoice(selectedRow);


    }

                                                                                            //creates a new invoice
    private void createNewInvoice(){

        String[] tempString=new String[invoiceTable.getColumnCount()];

        for (int i=0;i<invoiceTable.getColumnCount();i++){

            tempString[i]= (String) invoiceTable.getValueAt(invoiceTable.getRowCount()-1,i);
            if(i==1&&! Pattern.matches("..-..-....",  tempString[i])){
                JOptionPane.showMessageDialog(this, "Wrong Date Format");
                return;
            }
            if(tempString[i].isEmpty()){
                JOptionPane.showMessageDialog(this, "There is nothing to create");
                return;
            }
        }

        mainInstance.createInvoice(tempString);
        DefaultTableModel InoviceTablemodel = (DefaultTableModel)invoiceTable.getModel();
        InoviceTablemodel.setRowCount(0);
        String[][] csvData= mainInstance.getCsvInvoiceData();
        fillTable(csvData,InoviceTablemodel);


    }
                                                                                    //save Invoice Item changes
    void saveInvoiceItems(){
        String[][] tempString=new String[invoiceItems.getRowCount()][invoiceItems.getColumnCount()];
        for(int i=0;i<invoiceItems.getRowCount();i++){
            for(int j=0;j<invoiceItems.getColumnCount();j++){

                    tempString[i][j]= (String) invoiceItems.getValueAt(i,j);

            }
        }

        mainInstance.saveInvoice(tempString,tempString[0][0]);
        DefaultTableModel ItemsTableModel = (DefaultTableModel)invoiceItems.getModel();
        ItemsTableModel.setRowCount(0);
        fillTable(tempString,ItemsTableModel);
        //ItemsTableModel.addRow(new  String[]{"","","","",""});




    }
    void cancelInvoiceItems(){
        DefaultTableModel ItemsTablemodel = (DefaultTableModel)invoiceItems.getModel();
        String[][] csvData= main.getCsvInvoiceItemData((String)invoiceTable.getValueAt(selectedRowIndex,0));
        ItemsTablemodel.setRowCount(0);
        fillTable(csvData,ItemsTablemodel);
        //ItemsTablemodel.addRow(new  String[]{"","","","",""});



    }
                            //choosing file directory
    String chooseFolder(){
        String dir="";
        JFileChooser fileChooser=new JFileChooser();
        fileChooser.setDialogTitle("File Location");
        int checker= fileChooser.showOpenDialog(GUI.this);

        if(checker==JFileChooser.APPROVE_OPTION){
            dir=fileChooser.getSelectedFile().toString();
        }

        return dir;
    }
    void saveToFile(){
        String headDir=chooseFolder();
        System.out.println(headDir);
        if(headDir.isEmpty())return;

        String lineDir=chooseFolder();
        System.out.println(lineDir);
        if(lineDir.isEmpty())return;

        mainInstance.saveToFile(headDir,lineDir);

    }
    void loadFile(){

        String headDir=chooseFolder();
        String lineDir=chooseFolder();
        if(headDir.isEmpty()||lineDir.isEmpty())return;
        if(mainInstance.fileFound(headDir)||mainInstance.fileFound(lineDir)){
            JOptionPane.showMessageDialog(this, "File not found");
            return;
        }
        if(mainInstance.checkExtention(headDir)!="csv"||mainInstance.checkExtention(lineDir)!="csv"){
            JOptionPane.showMessageDialog(this, "Wrong file extention");
            return;
        }






        System.out.println( );
        mainInstance.loadFile(headDir,lineDir);
        String[][] csvData= mainInstance.getCsvInvoiceData();
        DefaultTableModel modelInvoiceTable = (DefaultTableModel) invoiceTable.getModel();
        modelInvoiceTable.setRowCount(0);
        fillTable(csvData,modelInvoiceTable);
    }






     GUI(){

          invoiceNumber=new JLabel();
          invoiceTotal=new JLabel();
          invoiceDate=new JLabel();
          invoiceCustomerName=new JLabel();


         leftPanel=new JPanel();

                                                         // menu bar to save and load to file
         saveFileMenuItem=new JMenuItem("save file");
         ActionListener saveFileMenuActionListner=new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 saveToFile();
             }
         };
         saveFileMenuItem.addActionListener(saveFileMenuActionListner);
         loadFileMenuItem=new JMenuItem("load file");
         ActionListener loadFileMenuActionListner=new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 loadFile();
             }
         };
         loadFileMenuItem.addActionListener(loadFileMenuActionListner);
         fileMenu=new JMenu("File");
         fileMenu.add(saveFileMenuItem);
         fileMenu.add(loadFileMenuItem);
         myMenubar=new JMenuBar();
         myMenubar.add(fileMenu);
         setJMenuBar(myMenubar);
                                                        // invoice table gui
         leftPanel.add(new JLabel("Invoice Table"));
         invoiceTable = new JTable(new DefaultTableModel(invoiceTableData,invoiceTableCols));
         DefaultTableModel modelInvoiceTable = (DefaultTableModel) invoiceTable.getModel();

         String[][] csvData= mainInstance.getCsvInvoiceData();
         fillTable(csvData,modelInvoiceTable);
         //modelInvoiceTable.addRow(new  String[]{"", "", "", ""});
         invoiceTable.setRowSelectionAllowed(true);

         invoiceTable.addMouseListener(new java.awt.event.MouseAdapter() {
             public void mouseClicked(java.awt.event.MouseEvent evt) {
                 invoiceTableSelectedRow(evt);
             }
         });

         leftPanel.add(new JScrollPane(invoiceTable));



                                                    //create invoice button
         createAndDeletePanel=new JPanel();
         creatInvoice=new JButton("Create  Invoice");

         createAndDeletePanel.add(creatInvoice);
         ActionListener createListener=new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {

                 createNewInvoice();

             }
         };
         creatInvoice.addActionListener(createListener);

                                                    //delete invoice button
         deleteInvoice=new JButton("Delete  Invoice");

         ActionListener deleteListener=new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {



                         deleteInvoice(selectedRowIndex);





             }
         };

         deleteInvoice.addActionListener(deleteListener);
         createAndDeletePanel.add(deleteInvoice);
         leftPanel.add(createAndDeletePanel);

         rightPanel=new JPanel();

                                                    //invoice number
         rightPanel.add(invoiceNumber);
                                                    //invoice date

         invoiceDate.setText("Date");
         invoiceDateTxt=new JTextField();
         rightPanel.add(invoiceDate);
         rightPanel.add(invoiceDateTxt);
                                                    //customer name

         invoiceCustomerName.setText("Customer name");

         customerNameTxt=new JTextField();

         rightPanel.add(invoiceCustomerName);
         rightPanel.add(customerNameTxt);
                                                    //invoice total

         rightPanel.add(invoiceTotal);
                                                    //invoice items table
         rightPanel.add(new JLabel("Invoice Items"));
         invoiceItems = new JTable(new DefaultTableModel(invoiceItemsData,invoiceItemsCols));
         rightPanel.add(new JScrollPane(invoiceItems));
                                                    //save change
         saveAndCancelPanel=new JPanel();
         saveChange=new JButton("Save");
         ActionListener saveListener=new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 saveInvoiceItems();
             }
         };
         saveChange.addActionListener(saveListener);
         saveAndCancelPanel.add(saveChange);

                                                    //cancel change
         cancelChange=new JButton("Cancel");
         ActionListener cancelListener=new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 cancelInvoiceItems();
             }
         };
         cancelChange.addActionListener(cancelListener);
         saveAndCancelPanel.add(cancelChange);
         rightPanel.add(saveAndCancelPanel);
                                                    //layout
         leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
         rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));
         add(leftPanel);
         add(rightPanel);
         setDefaultCloseOperation(EXIT_ON_CLOSE);

         setLayout(new GridLayout(1,2));

    }




}
