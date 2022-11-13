import java.util.*;
// /fill - random filling, /add - add student, /show - list of students, /remove - remove student, /exit - exit

public class Main {
    public static void main(String[] args) {
        ArrayList<Student> data = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        String command;
        welcome();

        do {
            System.out.print("Введите команду: ");
            command = in.next();
            switch (command) {
                case "/fill" -> fill(in, data);
                case "/add" -> add(in, data);
                case "/show" -> show(data);
                case "/set_mark" -> setMark(in, data);
                case "/pick_student" -> pickStudent(in, data);
                case "/remove" -> remove(in, data);
                case "/clear" -> clear(data);
                case "/help" -> welcome();
                case "/exit" -> {}
                default -> System.out.println("Ошибка! Команда не найдена!");
            }
        } while (!command.equals("/exit"));
        System.out.println("До скорых встреч!");
    }

    private static void welcome() {
        System.out.println("""
                Добро пожаловать! Список команд:
                1. /fill - случайным образом заполняет список студентов (работает только для пустого списка);
                2. /add - добавить студента в список;
                3. /show - показать список студентов;
                4. /set_mark - выставить студенту оценку за ответ;
                5. /pick_student - случайный выбор студента для ответа;
                5. /remove - убрать студента из списка;
                6. /clear - полностью очистить список студентов;
                7. /help - показать список команд;
                8. /exit - выход.
                """);
    }

    private static void fill(Scanner in, ArrayList<Student> data) {
        ArrayList<String> names = new ArrayList<>(Arrays.asList("Иван", "Павел", "Андрей", "Евгений", "Мария", "Анастасия", "Юлия", "Елизавета"));
        ArrayList<String> surnames = new ArrayList<>(Arrays.asList("Иванов", "Дмитриев", "Макаров", "Пономарёв", "Миронов", "Антонов", "Чирков"));
        Random rnd = new Random();

        if (!data.isEmpty()) {
            System.out.println("Ошибка! Список не пуст. Заполнение случайными значениями возможно только для пустого списка.");
            return;
        }

        boolean correctValue = true;
        byte amount = 0;
        do {
            System.out.print("Введите желаемое число студентов: ");
            if (in.hasNextByte()) {
                amount = in.nextByte();
                correctValue = true;
            } else {
                in.next();
            }
            if (amount < 1 || amount > 30) {
                correctValue = false;
            }
            if (!correctValue) {
                System.out.println("Некорректное число студентов. Убедитесь, что Вы ввели число в диапазоне [1, 30].");
            }
        } while (!correctValue);

        for (byte i = 0; i < amount; ++i) {
            String name = names.get(rnd.nextInt(names.size()));
            String surname = surnames.get(rnd.nextInt(surnames.size()));
            boolean isFemale = (name.equals("Мария") || name.equals("Анастасия") || name.equals("Юлия") || name.equals("Елизавета"));
            if (isFemale) {
                surname += 'а';
            }
            data.add(new Student(name, surname));
        }

        if (amount % 10 == 1) {
            System.out.println(amount + " студент добавлен в список!\n");
        } else if (amount % 10 > 1 && amount % 10 < 5) {
            System.out.println(amount + " студента добавлены в список!\n");
        } else {
            System.out.println(amount + " студентов добавлены в список!\n");
        }
    }

    private static void add(Scanner in, ArrayList<Student> data) {
        if (data.size() == 30) {
            System.out.println("Ошибка! Максимальная длина списка студентов - 30.");
            return;
        }
        try {
            System.out.print("Введите имя: ");
            String name = in.next();
            System.out.print("Введите фамилию: ");
            String surname = in.next();

            if (name.isEmpty() || surname.isEmpty()) {
                throw new IllegalArgumentException("Некорректные значения!\n");
            }

            data.add(new Student(name, surname));
            System.out.println("Студент " + name + " " + surname + " добавлен в список.\n");
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + "\n");
        }
    }

    private static void show(ArrayList<Student> data) {
        if (data.isEmpty()) {
            System.out.println("Список пуст.");
        } else {
            System.out.println("Список студентов:");
            for (int i = 0; i < data.size(); ++i) {
                Student st = data.get(i);
                System.out.println(i + 1 + ". " + st.getName() + " " + st.getSurname() + "; Присутствие: " + st.getAttendance() + "; Оценка: " + st.getMark() + "; Оценка выставлена: " + st.getMarkIsChanged());
            }
        }
        System.out.println();
    }

    private static void setMark(Scanner in, ArrayList<Student> data) {
        show(data);
        if (data.isEmpty()) {
            return;
        }

        boolean correctIndex;
        byte index = -1;
        do {
            System.out.print("Введите номер студента, которому хотите выставить оценку: ");
            if (in.hasNextByte()) {
                index = in.nextByte();
                --index;
                correctIndex = true;
            } else {
                in.next();
                correctIndex = false;
            }
            if (index < 0 || index > data.size() - 1) {
                System.out.println("Ошибка! Номер студента некорректен. Убедитесь, что он лежит в диапазоне [1; " + data.size() + "].");
                correctIndex = false;
            }
            if (correctIndex) {
                Student st = data.get(index);
                if (st.getMarkIsChanged()) {
                    System.out.println("Ошибка! Этому суденту уже поставлена оценка.");
                } else {
                    ask_student(in, st);
                }
            }
        } while (!correctIndex);
        System.out.println();
    }

    private static void ask_student(Scanner in, Student st) {
        boolean correctMark;
        boolean correctAttendance;
        byte mark = 0;
        do {
            System.out.print("Студент " + st.getName() + " " + st.getSurname() + " был(-а) на паре? \nЕсли да - веедите \"y\", иначе - \"n\": ");
            String attendance;
            attendance = in.next();
            if (attendance.equals("y")) {
                correctAttendance = true;
                st.setAttendanceIsChanged(true);
                st.setAttendance(true);
            } else if (attendance.equals("n")) {
                correctAttendance = true;
                st.setAttendanceIsChanged(true);
                st.setAttendance(false);
            } else {
                correctAttendance = false;
                System.out.println("Ошибка! Введите \"y\", если студент был на паре, иначе \"n\".");
            }
            if (correctAttendance) {
                if (!st.getAttendance()) {
                    System.out.println("Студент " + st.getName() + " " + st.getSurname() + " не был(-а) на паре. Его оценка - 0.");
                    st.setMarkIsChanged(true);
                } else {
                    do {
                        System.out.print("Введите оценку для студента " + st.getName() + " " + st.getSurname() + ": ");
                        if (in.hasNextByte()) {
                            mark = in.nextByte();
                            correctMark = true;
                        } else {
                            in.next();
                            correctMark = false;
                        }
                        try {
                            st.setMark(mark);
                            if (correctMark) {
                                System.out.println("Оценка " + mark + " студенту " + st.getName() + " " + st.getSurname() + " была выставлена!");
                                st.setMarkIsChanged(true);
                            } else {
                                System.out.println("Ошибка! Некорректная оценка!");
                            }
                        } catch (IllegalArgumentException iae) {
                            System.out.println(iae.getMessage());
                            correctMark = false;
                        }
                    } while (!correctMark);
                }
            }
        } while (!correctAttendance);
    }

    private static void pickStudent(Scanner in, ArrayList<Student> data) {
        if (data.isEmpty()) {
            System.out.println("Ошибка! Список пуст.");
            return;
        }

        if (allHasAnswered(data)) {
            System.out.println("Ошибка! Все студенты ответили и получили оценки.");
            return;
        }

        Random rnd = new Random();
        boolean hasAnswered = true;
        Student st = data.get(0);
        while (hasAnswered) {
            st = data.get(rnd.nextInt(data.size()));
            hasAnswered = st.getMarkIsChanged();
        }
        ask_student(in, st);
        System.out.println();
    }

    private static boolean allHasAnswered(ArrayList<Student> data) {
        for (Student st : data) {
            if (!st.getMarkIsChanged()) {
                return false;
            }
        }
        return true;
    }

    private static void remove(Scanner in, ArrayList<Student> data) {
        show(data);
        if (data.isEmpty()) {
            return;
        }
        byte index = -1;
        boolean correctIndex = true;
        do {
            System.out.print("Выберите номер студента, которого хотите удалить из списка: ");
            if (in.hasNextByte()) {
                index = in.nextByte();
                --index;
                correctIndex = true;
            } else {
                in.next();
                correctIndex = false;
            }
            if (index < 0 || index > data.size() - 1) {
                System.out.println("Ошибка! Номер студента некорректен. Убедитесь, что он лежит в диапазоне [1; " + data.size() + "].");
                correctIndex = false;
            }
        } while (!correctIndex);
        Student st = data.get(index);
        data.remove(index);
        System.out.println("Студент " + st.getName() + " " + st.getSurname() + " был удален из списка.");
        System.out.println();
    }

    private static void clear(ArrayList<Student> data) {
        if (data.isEmpty()) {
            System.out.println("Список пуст.");
        } else {
            data.clear();
            System.out.println("Список очищен!");
        }
        System.out.println();
    }
}
