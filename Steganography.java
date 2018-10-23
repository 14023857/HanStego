
package HanStegoV2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class Steganography {
    
    protected String zero = "\u200C";     //00
    protected String one = "\u200D";      //01
    protected String two = "\u200E";      //10
    protected String three = "\u200F";    //11
    protected String stop = "\u200B";
    
    protected int mapSize; 
    protected int key; 
    
    protected String[][] map; 
    
    protected String importedContent; 
    protected String exportContent; 
    
    public Steganography(){
        
    }

    public void defineMap(int inputKey) throws IOException{
        Map m = new Map(); 
        m.generateMap(inputKey);
        map = m.getMap(); 
        mapSize = m.getMapSize(); 
    }
    
    
    
    /*=========================================== EXTERNAL FILE I/O ================================================================*/
    
    public File openFile(JPanel jp) {
        JFileChooser fc = new JFileChooser(); 
        FileFilter filter1 = new FileNameExtensionFilter("MSWord Documents", "doc", "docx");
        FileFilter filter2 = new FileNameExtensionFilter("Text Files", "txt", "text");
        fc.addChoosableFileFilter(filter1);
        fc.addChoosableFileFilter(filter2);
        fc.showOpenDialog(jp);
        File file= fc.getSelectedFile(); 
        return file; 
    } 
    
    public String saveFile(JPanel jp){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save File as");   
        int userSelection;
        userSelection = fileChooser.showSaveDialog(jp);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            return fileToSave.getAbsolutePath();
        } 
        else return null; 
    } 
    
    private String getFileExtension(File file){
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0){
            return fileName.substring(fileName.lastIndexOf(".")+1); 
        } 
        else { 
            return "";
        }  
    }
    
    public String importTxt(File f){
        importedContent=null; 
        try {
                try (BufferedReader brcover = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UNICODE"))) {
                    importedContent = brcover.readLine(); 
                } 
            } catch (FileNotFoundException ex) {
                Logger.getLogger(GUIFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(GUIFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(GUIFrame.class.getName()).log(Level.SEVERE, null, ex);
            } 
        return importedContent;
    }
    
    public void exportTxt(String path, String content){
        File fileDir = new File(path);
        exportContent = content; 
            try {
                try (Writer out = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(fileDir+".txt"), "UNICODE"))) {
                    out.write(exportContent);
                    out.flush(); 
                }
            } catch (UnsupportedEncodingException | FileNotFoundException ex) {
                Logger.getLogger(GUIFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(GUIFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public String importDocx(File f){
        String content=""; 
        try {
                XWPFDocument docx = new XWPFDocument(new FileInputStream(f));
                XWPFWordExtractor wordExtract = new XWPFWordExtractor(docx);
                content = wordExtract.getText();      
            } catch (FileNotFoundException ex) {
                Logger.getLogger(GUIFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(GUIFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        return content.trim();  //Without trim(), default will exist blank spaces that will cause buffer overflow 
    }
    
    public void exportDocx(String path, String content) throws FileNotFoundException, IOException{ 
            XWPFDocument document = new XWPFDocument();   
            XWPFParagraph tmpParagraph = document.createParagraph();   
            XWPFRun tmpRun = tmpParagraph.createRun();   
            tmpRun.setText(content);     
            try (FileOutputStream fos = new FileOutputStream(new File(path+ ".docx"))) {
                document.write(fos);
            }    
    }
    
    public String importContent(JPanel jp){
        File f = openFile(jp); 
        String extension = getFileExtension(f); 
            switch (extension) {
                case "txt":
                    return importTxt(f);
                case "docx":
                    return importDocx(f); 
                default:
                    return null;
            }
    }
    
    /*=================== DISPLAY PROPERTIES ====================*/
     public int showNumSecChar(String str){
        int numChar = str.length(); 
        if(numChar>0)
            return numChar;
        else
            return 0;  
    }
    
    public int showFileSize(String str) throws UnsupportedEncodingException{
        byte[] fsize; 
        fsize = str.getBytes();  
        return fsize.length; 
    }
        
}
