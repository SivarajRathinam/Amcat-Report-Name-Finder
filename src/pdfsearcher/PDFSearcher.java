package pdfsearcher;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.Iterator;
import org.apache.pdfbox.util.*;
import org.apache.pdfbox.cos.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdfparser.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
public class PDFSearcher {

    public static void readXLSFile() throws IOException
	{
		InputStream ExcelFileToRead = new FileInputStream("D:/01.xls");
		HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

		HSSFSheet sheet=wb.getSheetAt(0);
		HSSFRow row; 
		HSSFCell cell;

		Iterator rows = sheet.rowIterator();

		while (rows.hasNext())
		{
			row=(HSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			
			while (cells.hasNext())
			{
				cell=(HSSFCell) cells.next();
		
                            switch (cell.getCellType()) {
                                case HSSFCell.CELL_TYPE_STRING:
                                    readfile(cell.getStringCellValue());
                                    break;
                                //case HSSFCell.CELL_TYPE_NUMERIC:
                                  //  System.out.print(cell.getNumericCellValue()+" ");
                                    //break;
                            //U Can Handel Boolean, Formula, Errors
                                default:
                                    break;
                            }
			}
			System.out.println();
		}
	
	}
    
    public static void readfile(String s) throws IOException{
        //Scanner reader = new Scanner(System.in);
        String path = "D:/mca";
        String path1 = "D:/mca1";
        //QueryParser qparser;
        COSDocument doc;
        PDDocument pd = null;
        //System.out.println("Enter the name : ");
        String in = s;
        PDFTextStripper file = null;
        PDFParser parser;
        File folder = new File(path);
        String files[] = folder.list();
        for(int i = 0; i < files.length; i++)
        {
            folder = new File(path+"/"+files[i]);
            //System.out.println(path+"/"+files[i]);
            try
            {
         parser=new PDFParser(new FileInputStream(folder));
         parser.parse();
         doc=parser.getDocument();
         file=new PDFTextStripper();
         pd=new PDDocument(doc);
         file.setStartPage(6);
         file.setEndPage(6);
        // StringBuilder sb = null;
         //sb=null;
         //sb.append(file.getText(pd));
         String txt=file.getText(pd);
         if(txt.toLowerCase().contains(in.toLowerCase()))
         {            //System.out.println(sb.toString());
             System.out.println(path+"/"+files[i]+"\n");
             //File src=new File(path+"/"+files[i]);
             //File dest=new File(path1+"/"+s);
             copy(path+"/"+files[i],path1+"/"+s);
             //i = files.length;
         }
            }
            catch(Exception e)
            {
                        System.out.print(e+"\n");
            }
            finally{
                pd.close();
            }
        }       
    }
    
    public static void main(String[] args) throws IOException {
        
        
        readXLSFile();
        
    }
    
    
     public static void copy ( String src,  String tar)  
        throws IOException {  
         int i=1;
         File source=new File(src);
         File target=new File(tar+".pdf");
         while(target.exists())
         {
            //tar=tar.substring(0,tar.length()-1);
             target=new File(tar + i +".pdf");
             i++;
         }
        FileChannel sourceChannel = null;  
        FileChannel targetChannel =null;  
        try {  
            sourceChannel =new FileInputStream(source).getChannel();  
            targetChannel=  new FileOutputStream(target).getChannel();  
        targetChannel.transferFrom(sourceChannel, 0,  
        sourceChannel.size());  
        }  
        finally {  
        targetChannel.close();  
        sourceChannel.close();  
        }  
        }  
}
