package yanglifan.learn.algorithm;

public class Decimal2Binary {
    public static String solution(int decimalNumber) {
        if (decimalNumber == 0) return "0";

        StringBuilder stringBuilder = new StringBuilder();
        while (decimalNumber != 0) {
            int remainder = decimalNumber % 2;
            stringBuilder.append(remainder);
            decimalNumber = decimalNumber / 2;
        }
        return stringBuilder.reverse().toString();
    }
}
