public class Converter {

    public static String convertCurrency(double inputQuantity,String a, String b,String path ){
        if(a == b){
            return  "-";
        }
        double s = Double.parseDouble(readfile.fileInfor(path).get(a).get(b));
        double result = inputQuantity* s;
        String resultStr = String.valueOf(result);
        return resultStr;
    }
}
