public class Invoice {

    int invoiceNumber;
    String customerName;
    String invoiceDate;
    Float total;





    int colSize=4;

    public void setColSize(int colSize) {
        this.colSize = colSize;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public void setTotal(Float total) {
        this.total = total;
    }
    public int getInvoiceNumber() {
        return invoiceNumber;
    }



    public String getCustomerName() {
        return customerName;
    }



    public String getInvoiceDate() {
        return invoiceDate;
    }



    public Float getTotal() {
        return total;
    }
    public int getColSize() {
        return colSize;
    }


}

