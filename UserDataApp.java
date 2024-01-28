package finaltaskException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UserDataApp {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Введите данные через пробел: Фамилия Имя Отчество датарождения(dd.mm.yyyy) номертелефона пол(f/m)");
            String input = scanner.nextLine();

            saveUserDetails(input);
            scanner.close();
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void saveUserDetails(String input) throws IOException {
        String[] parts = input.split(" ");

        if (parts.length != 6) {
            throw new IllegalArgumentException("Неверное количество данных");
        }

        String surname = parts[0];
        String firstName = parts[1];
        String middleName = parts[2];
        String dobStr = parts[3];
        String phoneNumberStr = parts[4];
        String genderStr = parts[5];

        // Проверяем формат даты рождения
        LocalDate dob;
        try {
            dob = LocalDate.parse(dobStr, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (Exception e) {
            System.out.println("Ошибка: неверный формат даты рождения");
            return;
        }

        // Проверяем формат номера телефона
        long phoneNumber;
        try {
            phoneNumber = Long.parseLong(phoneNumberStr);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: неверный формат номера телефона");
            return;
        }

        // Проверяем пол
        char gender;
        if (genderStr.length() != 1 || (genderStr.charAt(0) != 'f' && genderStr.charAt(0) != 'm')) {
            System.out.println("Ошибка: неверный формат пола");
            return;
        }
        gender = genderStr.charAt(0);

        // Формируем строку данных
        String userDetails = surname + " " + firstName + " " + middleName + " " + dob + " " + phoneNumber + " " + gender;

        // Записываем данные в файл 
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(surname + ".txt", true);
            bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(userDetails);
            bufferedWriter.newLine();
        } finally {
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (fileWriter != null) {
                fileWriter.close();
            }
        }

        System.out.println("Данные успешно сохранены.");
    }
}
