import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RateHistory implements Selector{

    private String csv;//
    private BufferedReader br;
    private String line;//
    private String csvSplit;//
    private Scanner scanner;
    private HashMap<String,String[]> CatalogMap;//
    private String date;//
    private boolean ApproachStart;//
    public ArrayList<Double> RateCalList;//
    private String eL;
    private String[] message;

    public RateHistory(){
        this.csv = "src/main/resources/History.csv";
        this.br = null;
        this.line = "";
        this.csvSplit = ",";
        this.scanner = new Scanner(System.in);
        this.CatalogMap = new HashMap<String, String[]>();
        this.date = "";
        this.ApproachStart = false;
        this.RateCalList = new ArrayList<Double>();
        this.eL = "^(^(\\d{4}|\\d{2})(\\-|\\/|\\.)\\d{1,2}\\3\\d{1,2}$)|(^\\d{4}Y\\d{1,2}M\\d{1,2}D$)$ ";
    }


    @Override
    public double[] doOperation(String start_date, String currency1, String currency2, String end_date) {


        try{
            br = new BufferedReader(new FileReader(csv));//


            /*
            读取文件部分，主要又三个if判断句组成，判断是否开始，是否在选定日期之间，是否结束
             */
            while((line = br.readLine()) != null){//

                message = line.split(csvSplit,2);//
                Pattern p = Pattern.compile(eL);
                Matcher m = p.matcher(message[0]);
                boolean b = m.matches();

                if(b){
                    date = message[0];//
                    CatalogMap.put(message[0],message[1].split(csvSplit));//
                }

                if(message[0].equals(end_date)){//
                    ApproachStart = false;//
                    ReadOneDate(currency1,currency2);//
                    break;
                }

                if(message[0].equals(start_date)){//
                    ApproachStart = true;
                    ReadOneDate(currency1,currency2);
                }

                if(ApproachStart){//
                    ReadOneDate(currency1,currency2);
                }

            }
            /*
            计算数值同时打印
             */
            System.out.println(this.RateCalList);
            double ave = calculateAVE();
            double min = min();
            double max = max();
            double median = median();
            double sd = sd();
            double[] list ={ave,min,max,median,sd};
            System.out.println("average: "+ave+" minimum: "+min+" maximum: "+max+" median: "+median+" standard deviation: "+sd+".");
            return list;

        } catch(IOException | NullPointerException e){
            System.out.println("File Read Error");
            return null;
        }
    }

    /*
    ReadOneDate的作用就是为了，在指针定位到一个日期后，在这个日期表格内找到需要的两个货币之间的汇率
     */

    private void ReadOneDate(String currency1, String currency2) throws IOException {
        int i = 0;

        if(message[0].equals(currency1)){//\
            String[] RateList = message[1].split(csvSplit);//\
            for(String x: CatalogMap.get(date)){//\
                if(x.equals(currency2)){//\
                    break;
                }
                i++;
            }
            System.out.println(date +"->"+ currency1+"->"+currency2+": "+ RateList[i]);
            RateCalList.add(Double.parseDouble(RateList[i]));//
        }

    }

    private double calculateAVE(){
        double sum = 0;
        for(double x: RateCalList){
            sum+=x;
        }
        double ave = sum/RateCalList.size();
        return ave;
    }

    private double min(){
        return Collections.min(RateCalList);
    }

    private double max(){
        return Collections.max(RateCalList);
    }

    private double median() {
        double s = 0;
        Collections.sort(RateCalList);
        int size = RateCalList.size();
        if (size % 2 != 1) {
            s = (RateCalList.get(size / 2 - 1) + RateCalList.get(size / 2)) / 2;

        } else {
            s = RateCalList.get((size - 1) / 2);
        }
        return s;
    }

    private double sd(){
        double dsum=0;
        double average = calculateAVE();
        for(int i = 0; i < RateCalList.size(); ++i)
        {
            double s= RateCalList.get(i) - average;
            dsum  += Math.pow(s,2);
        }
        double dStdDev =  Math.sqrt(dsum / RateCalList.size());
        return dStdDev;
    }


    public void SetCSV(String s) {
        this.csv = s;
    }
}
