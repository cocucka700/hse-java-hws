import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите число, для которого необходимо подсчитать факториал: ");
        try {
            int num = in.nextInt();
            Factorial(num);
        }
        catch (InputMismatchException ex) {
            System.out.println("Вы ввели некорректное число.");
        }
    }

    public static void Factorial(int number) {
        if (number < 0) {
            System.out.print("Число не должно быть меньше нуля.");
        } else if (number == 0) {
            System.out.print("0! = 1");
        } else {
            int result = 1;
            for (int i = 1; i <= number; ++i) {
                result *= i;
            }
            System.out.print(number + "! = " + result);
        }
    }
}
