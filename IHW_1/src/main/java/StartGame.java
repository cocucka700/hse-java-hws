import GameMods.*;
import java.util.Scanner;

public class StartGame {
    public static void Start() {
        showWelcomeMessage();
        Scanner in = new Scanner(System.in);
        String command = "";
        do {
            System.out.print("Введите команду: ");
            command = in.next();
            switch (command) {
                case "/botgame" -> BotGame.start(in);
                //case "/pvp" -> PVP.start();
                case "/exit" -> System.out.println("До скорых встреч!");
                default -> System.out.println("Команда не найдена!");
            }
        } while (!command.equals("/exit"));
    }

    private static void showWelcomeMessage() {
        System.out.println("""
            Добро пожаловать в игру Реверси.
            Список доступных команд:
            1. /botgame - игра с компьютером;
            2. /pvp - игра с другом;
            3. /exit - выход
            """);
    }
}
