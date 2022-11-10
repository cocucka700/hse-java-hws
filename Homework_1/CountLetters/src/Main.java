import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите строку: ");
        String string = in.next();
        System.out.print(printLetterCount(string));
    }

    static String printLetterCount (String string) {
        int vowelsCounter = 0, consonantsCounter = 0, unknownCounter = 0;
        ArrayList<Character> vowels = new ArrayList<>(Arrays.asList('a', 'e', 'o', 'i', 'y', 'u'));
        vowels.addAll(Arrays.asList('а', 'и', 'е', 'ё', 'у', 'ы', 'о', 'э', 'ю', 'я'));
        int size = vowels.size();
        for (int i = 0; i < size; ++i) {
            vowels.add(Character.toUpperCase(vowels.get(i)));
        }

        for (int i = 0; i < string.length(); ++i) {
            boolean latin = (string.charAt(i) >= 'A' && string.charAt(i) <= 'Z') || (string.charAt(i) >= 'a' && string.charAt(i) <= 'z');
            boolean cyrillic = (string.charAt(i) >= 'А' && string.charAt(i) <= 'Я') || (string.charAt(i) >= 'а' && string.charAt(i) <= 'я');
            if (latin || cyrillic) {
                if (vowels.contains(string.charAt(i))) {
                    vowelsCounter++;
                } else {
                    consonantsCounter++;
                }
            } else {
                unknownCounter++;
            }
        }
        return "Согласных: " + consonantsCounter + "; Гласных: " + vowelsCounter + "; Неопознанных: " + unknownCounter;
    }
}
