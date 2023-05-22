
import java.io.*;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class SellerTableModel extends AbstractTableModel implements Serializable {

    ArrayList<SoldProduct> soldProducts;
    String[] header = {"No", "Name", "Price", "Amount", "Total"};

    public SellerTableModel() {
        soldProducts = new ArrayList<SoldProduct>();
        initDatas();
    }

    //กำหนดค่าเริ่มต้นให้กับข้อมูล
    private void initDatas() {

    }

    @Override
    public String getColumnName(int columnId) {
        //return ชื่อของแต่ละ column ที่ต้องการแสดงใน table
        return header[columnId];
    }

    @Override
    public int getRowCount() {
        //return จำนวนแถวข้อมูลทั้งหมด
        return soldProducts.size();
    }

    @Override
    public int getColumnCount() {
        //return จำนวนของ column
        return header.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (soldProducts.isEmpty()) {
            return null;
        } else {
            SoldProduct s = soldProducts.get(rowIndex);
            //if you have more field should specify more case here
            switch (columnIndex) {
                case 0:
                    return s.getNo();
                case 1: // id first
                    return s.getName();
                case 2:
                    return s.getPrice();
                case 3:
                    return s.getAmount();
                case 4:
                    return s.getTotal();

                default:
                    return null;
            }
        }
    }

    public boolean checkCode(String code, ArrayList<SoldProduct> list) {
        int i = 0;
        while (i < list.size()) {
            if (code.equals((list.get(i).getCode()))) {
                return true;
            }
            i += 1;
        }
        return false;
    }

    public void printArray() {
        for (Product info : soldProducts) {
            System.out.println(info.toString());
        }
    }
}
