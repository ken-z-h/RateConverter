
import java.io.*;
import java.util.*;
public class PopCWriter {
    private String path = "src/main/resources/PopularCurrencies.csv";
//    private ArrayList<String> PopCurc;

    public Integer PopWriter(ArrayList<String> PopCurrencies) throws Exception{
        File writeFile = new File(path);
        try{
            BufferedWriter writeText = new BufferedWriter(new FileWriter(writeFile));
            for(int i = 0; i <= 3; i++){
                writeText.write("currency"+ i + "," + PopCurrencies.get(i));
                writeText.newLine();
            }
            writeText.flush();
            writeText.close();
            return 0;
        }catch(IOException | NullPointerException | IndexOutOfBoundsException e){
            System.out.println("File Read Error");
            return -1;
        }
    }

    public void setPath(String s) {
        this.path = s;
    }

//    public void setPopCurc(ArrayList<String> PopCurrencies){
//        this.PopCurc = PopCurrencies;
//    }
}
