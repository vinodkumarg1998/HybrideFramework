package utilities;



import java.io.FileInputStream;
import java.io.FileOutputStream;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


public class Excelfileutil {
	Workbook wb;
	//Constructor for reading excel path 
	public Excelfileutil(String Excellpath) throws Throwable {
		FileInputStream fi = new FileInputStream(Excellpath);
		wb= WorkbookFactory.create(fi);
	}
	// count number of rows in a sheet
	public int rowcount(String sheetname) {
		return wb.getSheet(sheetname).getLastRowNum();
	}
	// methods for reading cell data
	public String getcellData(String sheetname, int row , int column) {
		String data;
		if(wb.getSheet(sheetname).getRow(row).getCell(column).getCellType()==CellType.NUMERIC) {
			int cellData= (int)wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();
			data= String.valueOf(cellData);}
		else{
			data = wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();}
		return data; }
	// method for set cell data
	public void setcellData(String sheetname, int row, int colomn, String status, String WriteExcellpath ) throws Throwable {
		// get sheet from workBook
		Sheet ws = wb.getSheet(sheetname);
		// get row from sheet
		Row rowNum = ws.getRow(row);
		// create cell in row
		Cell cell = rowNum.createCell(colomn);
		// write status 
		cell.setCellValue(status);
		if (status.equalsIgnoreCase("pass")) {
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			// colour with green
			font.setColor(IndexedColors.GREEN.getIndex());
			font.setBold(true);
			style.setFont(font);
			ws.getRow(row).getCell(colomn).setCellStyle(style);}
		else if (status.equalsIgnoreCase("fail")) {
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			// colour with red
			font.setColor(IndexedColors.RED.getIndex());
			font.setBold(true);
			style.setFont(font);
			ws.getRow(row).getCell(colomn).setCellStyle(style);}
		else if (status.equalsIgnoreCase("Blocked")) {
			CellStyle style =wb.createCellStyle();
			Font font= wb.createFont();
			// colour with Blue
			font.setColor(IndexedColors.BLUE.getIndex());
			font.setBold(true);
			style.setFont(font);
			ws.getRow(row).getCell(colomn).setCellStyle(style);}
		FileOutputStream fo = new FileOutputStream(WriteExcellpath);
		wb.write(fo);}
	// to test the above method are working are not we can test by the below methods by calling  
	public static void main(String[] args) throws Throwable {
		Excelfileutil xl= new Excelfileutil("D:/sample.xlsx");
		int rc= xl.rowcount("emp");
		System.out.println(rc);
		for(int i=1;i<=rc;i++) {
		String fname= xl.getcellData("emp", i, 0);
		String mname= xl.getcellData("emp", i, 1);
		String lname= xl.getcellData("emp", i, 2);
		String eid= xl.getcellData("emp", i, 3);
		System.out.println(fname+" "+mname+"  "+lname+"  "+eid);
		xl.setcellData("emp", i, 4, "pass", "D:/Results.xlsx");}}
	}
	




