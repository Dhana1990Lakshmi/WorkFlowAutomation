package api.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;

public class XLUtility {

	public FileInputStream fis;
	public FileOutputStream fos;
	public XSSFWorkbook wb;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;
	public CellStyle style;
	String path;
	
	public XLUtility(String path){
		this.path=path;
	}
	public int getRow(String sheetname) throws IOException {
		fis=new FileInputStream(path);
		wb= new XSSFWorkbook(fis);
		sheet=wb.getSheet(sheetname);
		int rowcount= sheet.getLastRowNum();
		wb.close();
		fis.close();		
		return rowcount;
		
	}
	public int getCellCount(String sheetname,int rownum) throws IOException {
		fis=new FileInputStream(path);
		wb= new XSSFWorkbook(fis);
		sheet=wb.getSheet(sheetname);
		row=sheet.getRow(rownum);
		int cellcount= row.getLastCellNum();
		wb.close();
		fis.close();		
		return cellcount;
		
	}
	
	public String getCellData(String sheetname,int rownum, int colnum) throws IOException {
		
		fis=new FileInputStream(path);
		wb= new XSSFWorkbook(fis);
		sheet=wb.getSheet(sheetname);
		row=sheet.getRow(rownum);
		cell=row.getCell(colnum);
		
		DataFormatter df= new DataFormatter();
		String data;
		try {
			data=df.formatCellValue(cell);
		}catch(Exception E) {
			data="";
		}
		
		wb.close();
		fis.close();	
		
		return data;		
	}
	
	public void setCellData(String sheetname,int rownum, int colnum, String data) throws IOException{
		File xlFile=new File(path);
		if(!xlFile.exists()) {
			wb= new XSSFWorkbook();
			fos=new FileOutputStream(path);
			wb.write(fos);
		}
		fis=new FileInputStream(path);
		wb=new XSSFWorkbook(fis);
		
		if(wb.getSheetIndex(sheet)==-1) 
			wb.createSheet(sheetname);
		sheet=wb.getSheet(sheetname);
		
		if(sheet.getRow(rownum)==null)
			sheet.createRow(rownum);
		row=sheet.getRow(rownum);
		
		cell= row.createCell(colnum);
		cell.setCellValue(data);
	
		fos=new FileOutputStream(path);
		wb.write(fos);
		wb.close();
		fis.close();
		fos.close();
	}
}
