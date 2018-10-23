
package HanStegoV2;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Embed extends Steganography{

    private String[][] bitToUnicode; 
    private String[]splitSecret;
    private String[]stegoTextArr; 
    private String[]splitCover; 
    
    private String secret;
    private String cover;
    private String bits;
    private String noPrintUnicode;
    private String stegoText; 

    private int secretSize;
    private int capacity; 


    private int stegoTextSize; 
   
    private boolean valid; 
             
    
    public Embed(){
        
    }
    
    public Embed(String cover, String secret, int key) throws IOException {
        //set values 
        this.cover=cover; 
        this.secret=secret; 
        this.key=key; 
        secretSize = this.secret.length();
        capacity = this.cover.length()-2;
        stegoTextSize = this.cover.length()*2-1;
    }
    
    public boolean checkCapacity(){
        return capacity >= secretSize;
    }
    
    /* ============= EMBEDDING PROCESS ================ */ 
    public String generateStegoText() throws IOException{
        
        super.defineMap(key); 
        
        splitSecret = secret.split("");
        bitToUnicode = new String[splitSecret.length][2]; 
        
//        for(int i=0; i<secretSize; i++){
//                secretToBit[i][0] = splitSecret[i];
//                for(int j=0; j<mapSize; j++){
//                    if(secretToBit[i][0].equals(map[j][0])){
//                        secretToBit[i][1] = map[j][1];
//                    }
//                }
//        }
//        
        
        for(int i=0; i<splitSecret.length; i++){
            for(int j=0; j<mapSize; j++){
                if(splitSecret[i].equals(map[j][0])){
                    bitToUnicode[i][0] = map[j][1]; 
                }
            }
        }

        for(int i=0; i<bitToUnicode.length; i++){
            bits=bitToUnicode[i][0];
            noPrintUnicode="";
            for(int j=0; j<bits.length(); j+=2){
                switch (bits.substring(j,j+2)) {
                        case "00":
                        noPrintUnicode = noPrintUnicode+zero;
                        break;
                        case "01":
                        noPrintUnicode = noPrintUnicode+one;
                        break;
                        case "10":
                        noPrintUnicode = noPrintUnicode+two;
                        break;
                        case "11":
                        noPrintUnicode = noPrintUnicode+three;
                        break;
                        default:
                        break;
                }
            }
            bitToUnicode[i][1] = noPrintUnicode;
        }
        
        //Stores characters of cover text in EVEN number of array
        
        
        stegoTextArr = new String[stegoTextSize];
        splitCover = cover.split("");
        int count = 0;
        for(int i=0; i<stegoTextSize; i+=2){
            stegoTextArr[i]= splitCover[count];
            count++; 
        }
        
        for(int i=0; i<bitToUnicode.length;i++){
            stegoTextArr[i*2+1] = bitToUnicode[i][1];
        }
        
        stegoTextArr[secret.length()*2+1] = stop; 


        //To generate output (String) of the stego-text 
        stegoText="";
        for(int i=0; i<stegoTextSize; i++){
            if(stegoTextArr[i]==null) {
            }
            else {
                stegoText=stegoText+stegoTextArr[i];
            }
        }
        return stegoText;
    }      

    public String getStegoText() {
        return stegoText;
    }
    
    
    /* ========== DISPLAY PROPERTIES =========== */ 
    public int showCapacity(String str) {
        capacity = str.length()-2; 
        if(capacity>0)
            return capacity;
        else 
            return 0; 
    }
    
    
}
