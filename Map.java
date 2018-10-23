package HanStegoV2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Map {

    private File f;
    private File lookupTable;
    private int key = 0;
    private int mapsize;
    private String[][] map;

    public Map() {

    }

    public File selectLookupTable(int key) {
        switch (this.key) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                f = new File("map" + this.key + ".txt");
                break;
            default:
                f = new File("map.txt");
                break;
        }
        return f;
    }

    public void generateMap(int key) throws FileNotFoundException, IOException {
        this.key = key;
        lookupTable = selectLookupTable(this.key);
        try (
            BufferedReader br = new BufferedReader(new FileReader(lookupTable))){
            map = new String[100][2];
            String scannedStr;
            int r = 0;
            int c = 0;
            while ((scannedStr = br.readLine()) != null) {
                Scanner input;
                input = new Scanner(scannedStr).useDelimiter("HS");

                while (input.hasNext()) {
                    map[r][c] = input.next();
                    c++;
                    map[r][c] = input.next();
                    r++;
                    c = 0;
                    mapsize++;
                }
            }
            br.close();
        }
    }

    public String[][] getMap() {
        return map;
    }

    public int getMapSize() {
        return mapsize;
    }

    public int getInputKey() {
        return key;
    }

    public File getFile() {
        return f;
    }
}
