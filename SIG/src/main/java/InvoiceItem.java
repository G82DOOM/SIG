public class InvoiceItem {



    int invoiceItemNumber;
    int count;
    String Item_Name;
    Float Item_Price;
    Float ItemTotal;
    int colSize=5;

    public void setInvoiceItemNumber(int invoiceItemNumber) {
        this.invoiceItemNumber = invoiceItemNumber;
    }

    public void setColSize(int colSize) {
        this.colSize = colSize;
    }



    public void setCount(int count) {
        this.count = count;
    }

    public void setItem_Price(Float item_Price) {
        Item_Price = item_Price;
    }

    public void setItemTotal(Float itemTotal) {
        ItemTotal = itemTotal;
    }
    public void setItem_Name(String item_Name) {
        Item_Name = item_Name;
    }
    public int getCount() {
        return count;
    }






    public Float getItem_Price() {
        return Item_Price;
    }





    public Float getItemTotal() {
        return ItemTotal;
    }




    public String getItem_Name() {
        return Item_Name;
    }

    public int getColSize() {
        return colSize;
    }
    public int getInvoiceItemNumber() {
        return invoiceItemNumber;
    }




}
