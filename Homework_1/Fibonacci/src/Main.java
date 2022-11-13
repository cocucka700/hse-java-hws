import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите количество чисел Фибоначчи: ");
        try {
            int num = in.nextInt();
            fibonacci(num);
        }
        catch (InputMismatchException ex) {
            System.out.println("Вы ввели некорректное число.");
        }
    }

    public static void fibonacci(int number) {
        if (number < 1) {
            System.out.print("Минимальное количество чисел для работы программы: 1");
        } else if (number == 1) {
            System.out.println("Первое число Фибоначчи равно 0");
        } else {
            int[] data = new int[number];
            data[0] = 0;
            data[1] = 1;
            System.out.print("F(" + number + ") = [");
            System.out.print(data[0] + ", " + data[1]);
            for (int i = 2; i < number; ++i) {
                data[i] = data[i - 1] + data[i - 2];
                System.out.print(", " + data[i]);
            }
            System.out.print("]");
        }
    }
}
