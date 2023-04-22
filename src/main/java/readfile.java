
import java.io.BufferedReader;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class readfile {
    private static HashMap<String, HashMap<String, String>> finalsites;
    private static String line;
    private static String[] headitem;
    private static String headLine;


    public static  HashMap<String, HashMap<String, String>>  fileInfor(String path) {
        try {
            BufferedReader reader = null;
            finalsites = new HashMap<String, HashMap<String, String>>();
            reader = new BufferedReader(new FileReader(path));
            headLine = reader.readLine();
            //get the first headline
            headitem = headLine.split(",");

            while ((line = reader.readLine()) != null) {
                String item[] = line.split(",");
                ArrayList<Map.Entry<String, String>> spCurrency = new ArrayList<Map.Entry<String, String>>();
                HashMap<String, String> sites = new HashMap<String, String>();

                for (int i = 1; i < headitem.length; i++) {
                    sites.put(headitem[i], item[i]);
                    //Map.Entry<String, String> entry1 = new AbstractMap.SimpleEntry<>(headitem[i], item[i]);
                    //store pairs into sites
                    //spCurrency.add(entry1);
                }
                finalsites.put(item[0], sites);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalsites;
    }


    public static ArrayList<String>  popularCurrency(String path) {
        ArrayList<String> spCurrency = new ArrayList<String>();
        try {
            BufferedReader reader = null;
            reader = new BufferedReader(new FileReader(path));
            while ((line = reader.readLine()) != null) {
                String item[] = line.split(",");
                System.out.println(item[1]);
                spCurrency.add(item[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return spCurrency;
    }
}
