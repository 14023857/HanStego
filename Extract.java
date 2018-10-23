
package HanStegoV2;

import java.io.IOException;

public class Extract extends Steganography{
    
    private String[] splitStegoText; 
    
    private String stegoText; 
    private String bitStream; 
    private String secret; 

    
    public Extract(){
        
    }
    
    public Extract(String stegoText, int key){
        this.stegoText=stegoText; 
        this.key=key; 
    }
    
    public String generateSecret() throws IOException{
        super.defineMap(key); 
          
        //To decode secret message
        bitStream=""; 
        splitStegoText = stegoText.split("");
        String[][] bitToSecret = new String[stegoText.length()][2]; 
        int index=0; 
        
        for(int i=0; i<splitStegoText.length; i++){
 
            if(splitStegoText[i].equals(zero)){
                    bitStream+="00";  
            }
            else if (splitStegoText[i].equals(one)){
                    bitStream+="01";
            }
            else if (splitStegoText[i].equals(two)){
                    bitStream+="10";
            }
            else if (splitStegoText[i].equals(three)){
                    bitStream+="11";
            }
            else if(splitStegoText[i].equals(stop)){
                    break; 
            }            
            
            //Check if consecutive character is candidate character to determine if bit string is complete 
            if(i<splitStegoText.length-1){                              //To prevent array out of bound 
                if(splitStegoText[i+1].equals(zero)|| 
                        splitStegoText[i+1].equals(one)||
                        splitStegoText[i+1].equals(two)||
                        splitStegoText[i+1].equals(three)||
                        splitStegoText[i+1].equals(stop)) 
                {
                     //Continue to form bitStream 
                }
                else { 
                    //bitStream is complete 
                    if(!"".equals(bitStream)){
                        bitToSecret[index][0]=bitStream; 
                        index++; 
                        bitStream=""; 
                    }
                }
            } 
            
        }
        
        secret=""; 
        for(int i=0; i<index; i++){
            for(int j=0; j<mapSize; j++){
                if(bitToSecret[i][0].equals(map[j][1])){
                    bitToSecret[i][1] = map[j][0]; 
                    secret+=map[j][0]; 
                }
            }
        }
        return secret; 
    }
    
    
}
