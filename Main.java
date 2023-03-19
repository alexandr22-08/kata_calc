import java.util.Scanner;

public class Main {
    static char[] OPERATIONS = new char[] {'+', '-', '*', '/'};
    static String[] ROMAN_NUMBERS = {"O", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X",
            "XI", "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX",
            "XXI", "XXII", "XXIII", "XXIV", "XXV", "XXVI", "XXVII", "XXVIII", "XXIX",
            "XXX", "XXXI", "XXXII", "XXXIII", "XXXIV", "XXXV", "XXXVI", "XXXVII", "XXXVIII", "XXXIX", "XL",
            "XLI", "XLII", "XLIII", "XLIV", "XLV", "XLVI", "XLVII", "XLVIII", "XLIX",
            "L", "LI", "LII", "LIII", "LIV", "LV", "LVI", "LVII", "LVIII", "LIX", "LX",
            "LXI", "LXII", "LXIII", "LXIV", "LXV", "LXVI", "LXVII", "LXVIII", "LXIX", "LXX",
            "LXXI", "LXXII", "LXXIII", "LXXIV", "LXXV", "LXXVI", "LXXVII", "LXXVIII", "LXXIX", "LXXX",
            "LXXXI", "LXXXII", "LXXXIII", "LXXXIV", "LXXXV", "LXXXVI", "LXXXVII", "LXXXVIII", "LXXXIX", "XC",
            "XCI", "XCII", "XCIII", "XCIV", "XCV", "XCVI", "XCVII", "XCVIII", "XCIX", "C"};

    public static void main(String[] args) throws Exception {
        System.out.println("Введите выражение в формате  А + B, с пробелом До и После знака операции");
        Scanner s = new Scanner(System.in);
        String input = s.nextLine();
        String result = calc(input);
        System.out.println(result);
    }
    static String convertArabicToRoman(int numArabian) {
        return ROMAN_NUMBERS[numArabian];
    }
    public static String calc(String input) throws Exception {
        Integer[] params = parseParams(input);
        int result = calcExp(params);

        if (params[3] == 0) { // если арабская система, то возвращаем сразу ответ
            return String.valueOf(result);
        }
        if (result < 0) {
            throw new Exception("throws Exception //т.к. в римской системе нет отрицательных чисел");
        }
        if (result == 0) {
            throw new Exception("throws Exception //т.к. в римской системе только положительные числа");
        }
        return convertArabicToRoman(result);
    }

    static int calcExp(Integer[] params) throws Exception {
        switch (OPERATIONS[params[2]]) {
            case '+':
                return params[0] + params[1];
            case '-':
                return params[0] - params[1];
            case '*':
                return params[0] * params[1];
            case '/':
                return params[0] / params[1];
        }
        throw new Exception("throws Exception //т.к. неизвестный оператор");
    }

    static Integer[] parseParams(String data) throws Exception {
        String[] params = data.split(" ");

        if (getMathOperationIndex(data) == -1) {
            throw new Exception("throws Exception //т.к. строка не является математической операцией");
        }
        if (params.length != 3) {
            throw new Exception("throws Exception //т.к. формат математической операции " +
                    "не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }
        int operationIndex = getMathOperationIndex(params[1]);
        if (operationIndex == -1) {
            throw new Exception("throws Exception //т.к. формат математической операции " +
                    "не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }

        Integer firstArabic = toArabic(params[0]);
        Integer secondArabic = toArabic(params[2]);
        Integer firstRoman = toRoman(params[0]);
        Integer secondRoman = toRoman(params[2]);

        if (firstArabic == null && firstRoman == null || secondArabic == null && secondRoman == null) {
            throw new Exception("throws Exception //т.к. ошибка в формате числа");
        }
        boolean isIncorrectArabic = isIncorrectNumberFormat(firstArabic, secondArabic);
        boolean isIncorrectRoman = isIncorrectNumberFormat(firstRoman, secondRoman);

        if (isIncorrectArabic && isIncorrectRoman) {
            throw new Exception("throws Exception //т.к. используются одновременно разные системы счисления");
        }
        if (firstArabic == null) {
            if (!isValueBoundsCorrect(firstRoman) || !isValueBoundsCorrect(secondRoman)) {
                throw new Exception("throws Exception //т.к. границы значений входных чисел некорректны");
            }
            return new Integer[]{firstRoman, secondRoman, operationIndex, 1};
        } else {
            if (!isValueBoundsCorrect(firstArabic) || !isValueBoundsCorrect(secondArabic)) {
                throw new Exception("throws Exception //т.к. границы значений входных чисел некорректны");
            }
            return new Integer[]{firstArabic, secondArabic, operationIndex, 0};
        }
    }

    static boolean isValueBoundsCorrect(Integer number) {
        return number >= 1 && number <= 10;
    }

    static int getMathOperationIndex(String exp) {
        for (int i = 0; i < OPERATIONS.length; i++) {
            if (exp.contains("" + OPERATIONS[i])) {
                return i;
            }
        }
        return -1;
    }
    static boolean isIncorrectNumberFormat(Integer first, Integer second) {
        return first == null && second != null || first != null && second == null;
    }
    static Integer toArabic(String number) {
        if (number.equals("10")) {
            return 10;
        }
        if (number.length() != 1) {
            return null;
        }
        if (number.charAt(0) >= '0' && number.charAt(0) <= '9') {
            return Character.getNumericValue(number.charAt(0));
        }
        return null;
    }
    static Integer toRoman(String number) {
        for (int i = 0; i < ROMAN_NUMBERS.length; i++) {
            if (number.equals(ROMAN_NUMBERS[i])) {
                return i;
            }
        }
        return null;
    }
}
