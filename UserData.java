import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class UserData {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println(
                    "Введите данные через пробел: фамилия, имя, отчество, дата рождения, номер телефона, пол (f/m):");
            String input = scanner.nextLine();
            String[] parts = input.split(" ");

            if (parts.length != 6) {
                int errorCode = 1;
                System.out.printf("Ошибка: вы ввели %s данных, чем требуется. Код ошибки: %d%n",
                        (parts.length < 6 ? "меньше" : "больше"), errorCode);
                System.exit(errorCode);
            }

            try {
                String name = parts[0];
                String surname = parts[1];
                String patronymic = parts[2];
                String birthDate = parts[3];
                long phoneNumber = 0; // Объявляем переменную здесь с начальным значением
                if (parts[4].startsWith("+")) {
                    System.out.println("Номер телефона не должен начинаться с '+'.");
                    return;
                } else {
                    phoneNumber = Long.parseLong(parts[4]);
                }
                char gender = parts[5].charAt(0);

                if (!name.matches("[A-Za-zА-Яа-я-]+") || !surname.matches("[A-Za-zА-Яа-я-]+")
                        || !patronymic.matches("[A-Za-zА-Яа-я-]+")) {
                    throw new IllegalArgumentException("Фамилия, имя и отчество должны содержать только буквы.");
                }

                if (!birthDate.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
                    throw new IllegalArgumentException("Дата рождения должна быть в формате dd.mm.yyyy");
                }

                if (gender != 'f' && gender != 'm') {
                    throw new IllegalArgumentException("Пол должен быть указан как 'f' - женский или 'm' - мужской.");
                }

                writeToFile(name, surname, patronymic, birthDate, phoneNumber, gender);

                System.out.println("Данные успешно записаны в файл.");

            } catch (NumberFormatException e) {
                System.out.println(
                        "Номер телефона должен быть целым беззнаковым числом и не выходить за пределы диапазона.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void writeToFile(String name, String surname, String patronymic, String birthDate, long phoneNumber,
            char gender) throws IOException {
        String fileName = name + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(
                    String.format("%s %s %s %s %d %c%n", name, surname, patronymic, birthDate, phoneNumber, gender));
        }
    }
}
