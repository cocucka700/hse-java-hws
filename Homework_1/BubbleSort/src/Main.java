public class Main {
    public static void main(String[] args) {
        int[] data = {2, 6, 4, 1, 3, 5, 0, 7};
        System.out.print("Исходный массив: [");
        for (int i = 0; i < data.length; ++i) {
            if (i != data.length - 1) {
                System.out.print(data[i] + ", ");
            } else {
                System.out.println(data[i] + "]");
            }
        }

        int[] modified = bubbleSort(data);
        System.out.print("Отсортированный массив: [");
        for (int i = 0; i < modified.length; ++i) {
            if (i != modified.length - 1) {
                System.out.print(modified[i] + ", ");
            } else {
                System.out.println(modified[i] + "]");
            }
        }
    }

    static int[] bubbleSort(int[] data) {
        for (int i = 0; i < data.length - 1; ++i) {
            for (int j = i + 1; j < data.length; ++j) {
                if (data[i] > data[j]) {
                    int temp = data[i];
                    data[i] = data[j];
                    data[j] = temp;
                }
            }
        }
        return data;
    }
}
