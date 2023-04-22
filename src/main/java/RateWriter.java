import java.io.*;
import java.security.Key;
import java.util.*;

import javax.sound.sampled.Line;
public class RateWriter {
    private HashMap<String, ArrayList<String>> newRate;
    private ArrayList<String> CurrencyList;
    private int Keysize;
    private String path = "src/main/resources/InitialExchangeRate.csv";
    private String History = "src/main/resources/History.csv";

    public void rateWriter() throws Exception{
        try{
            ArrayList<String[]> List = new ArrayList<>();
            BufferedReader textFile = new BufferedReader(new FileReader(path));
            FileOutputStream historyFileWriter = new FileOutputStream(History, true);
            String LineData = "";
            Integer Linnum = 0;
            while ((LineData = textFile.readLine())!=null){
                List.add(LineData.split(","));
                historyFileWriter.write(LineData.getBytes());
                historyFileWriter.write("\n".getBytes());
                Linnum += 1;
            }
            historyFileWriter.flush();
            textFile.close();
            historyFileWriter.close();
            File writeFile = new File(path);
            BufferedWriter writeText = new BufferedWriter(new FileWriter(writeFile));
            this.Keysize = this.CurrencyList.size();
            // System.out.println(Keysize);
            for(int i = 0; i <= Keysize-1; i++){
                // System.out.println(this.CurrencyList.get(i));
                if (i != Keysize-1){
                    writeText.write(this.CurrencyList.get(i) + ",");
                }else{
                    writeText.write(this.CurrencyList.get(i));
                }
                writeText.flush();
            }
            writeText.newLine();
            for(int i = 1; i < this.Keysize; i++){
                String key = this.CurrencyList.get(i);
                writeText.write(key + ",");
                // System.out.println(i);
                for (int x = 0; x < this.Keysize-1; x++){
                    ArrayList<String> value = this.newRate.get(key);
                    // System.out.println(value.get(x));
                    if (x != this.Keysize-2){
                        writeText.write(value.get(x) + ",");
                    }
                    else{
                        writeText.write(value.get(x));
                    }
                    writeText.flush();
                }
                writeText.newLine();
                writeText.flush();
            }
            // writeText.write(";");
            writeText.flush();
            writeText.close();
        }catch(IOException | NullPointerException e){
            System.out.println("File write Error");
        }
    }

    public int rateEditer(String Currency, ArrayList<String>editRateList, String date){
        // System.out.println(Currency);
        ArrayList<String> target = new ArrayList<>();
        target.add(Currency);
        for(int y = 1; y < editRateList.size()+1; y++){
            target.add(editRateList.get(y-1));
        }
        ArrayList<String[]> List = new ArrayList<>();
        try{
            BufferedReader textFile = new BufferedReader(new FileReader(path));
            FileOutputStream historyFileWriter = new FileOutputStream(History);
            String LineData = "";
            Integer Linnum = 1;
            Integer targetNum = -1;
            while ((LineData = textFile.readLine())!=null){
                List.add(LineData.split(","));
                historyFileWriter.write(LineData.getBytes());
                historyFileWriter.write("\n".getBytes());
                if(LineData.startsWith(Currency)){
                    targetNum = Linnum;
                }
                historyFileWriter.flush();
                Linnum += 1;
            }
            textFile.close();
            historyFileWriter.close();
            // System.out.println("targetNum: "+targetNum);
            BufferedWriter textFileWriter = new BufferedWriter(new FileWriter(path));
            for (int i = 0; i < List.size(); i++){
                if (i == 0){
                    textFileWriter.write(date + ",");
                    for (int x = 1; x < List.get(i).length; x++){
                        if (x != List.get(i).length -1){
                            textFileWriter.write(List.get(i)[x] + ",");
                        }
                        else{
                            textFileWriter.write(List.get(i)[x]);
                            textFileWriter.newLine();
                        }
                    }
                    i++;
                }
                if (i == targetNum){
                    for(int x = 0; x < target.size(); x++){
                        if (x != target.size()-1){
                            textFileWriter.write(target.get(x) + ",");
                        }
                        else{
                            textFileWriter.write(target.get(x));
                            textFileWriter.newLine();
                        }
                    }
                }
                else{
                    for (int x = 0; x < List.get(i).length; x++){
                        if (x == targetNum){
                            if (x != List.get(i).length -1){
                                // System.out.println(editRateList.get(i-1));
                                textFileWriter.write(String.format("%.2f", (1/Double.parseDouble(editRateList.get(i-1)))) + ",");
                            }
                            else{
                                // System.out.println(editRateList.get(i-1) + i);
                                textFileWriter.write(String.format("%.2f", (1/Double.parseDouble(editRateList.get(i-1)))));
                                textFileWriter.newLine();
                            }
                        }
                        else{
                            if (x != List.get(i).length -1){
                                textFileWriter.write(List.get(i)[x] + ",");
                            }
                            else{
                                textFileWriter.write(List.get(i)[x]);
                                textFileWriter.newLine();
                            }
                        }
                    }
                }
                textFileWriter.flush();
            }
            // if (isNew){
            //     textFileWriter.write(Currency + ",");
            //     for (int z = 0; z < editRateList.size(); z++){
            //         if (z != editRateList.size()-1){
            //             textFileWriter.write(editRateList.get(z) + ",");
            //         }else{
            //             textFileWriter.write(editRateList.get(z));
            //         }

            //     }
            // }
            textFileWriter.flush();
            textFileWriter.close();
        }catch(IOException | NullPointerException e){
            System.out.println("File Read Error");
        }
        return 0;

    }


    public void setRateCurc(HashMap<String, ArrayList<String>> newRates){
        this.newRate = newRates;
    }
    public void setCurcList(ArrayList<String> newCurrencies){
        this.CurrencyList = newCurrencies;
    }

    public void setpath(String s) {
        this.path = s;
    }

    public void setHistory_write_path(String s) {
    this.History = s;
    }
    // public void setEditRateList(ArrayList<String> ChangedRate){
    //     this.editRateList = ChangedRate;
    // }

}