import java.util.Scanner;

class UserAuthSystem {
    private static final int MAX_USERS = 15;
    private static final String[] FORBIDDEN_PASSWORDS = {"admin", "pass", "password", "qwerty", "ytrewq"};
    private String[] usernames = new String[MAX_USERS];
    private String[] passwords = new String[MAX_USERS];

    public void addUser(String username, String password) {
        if (username.length() < 5 || username.contains(" ")) {
            throw new IllegalArgumentException("Ім’я користувача має містити не менше 5 символів і не містити пробілів");
        }
        if (!validatePassword(password)) {
            throw new IllegalArgumentException("Пароль не відповідає вимогам безпеки");
        }
        for (int i = 0; i < MAX_USERS; i++) {
            if (usernames[i] == null) {
                usernames[i] = username;
                passwords[i] = password;
                System.out.println("Користувач доданий успішно");
                return;
            }
        }
        throw new IllegalStateException("Досягнуто максимальну кількість користувачів");
    }

    public void deleteUser(String username) {
        for (int i = 0; i < MAX_USERS; i++) {
            if (username.equals(usernames[i])) {
                usernames[i] = null;
                passwords[i] = null;
                System.out.println("Користувач видалений успішно");
                return;
            }
        }
        throw new IllegalArgumentException("Користувач не знайдений");
    }

    public void authenticate(String username, String password) {
        for (int i = 0; i < MAX_USERS; i++) {
            if (username.equals(usernames[i]) && password.equals(passwords[i])) {
                System.out.println("Аутентифікація пройшла успішно");
                return;
            }
        }
        throw new SecurityException("Невірне ім’я користувача або пароль");
    }

    private boolean validatePassword(String password) {
        if (password.length() < 10 || password.contains(" ")) {
            return false;
        }
        for (String forbidden : FORBIDDEN_PASSWORDS) {
            if (password.toLowerCase().contains(forbidden)) {
                return false;
            }
        }
        boolean hasSpecial = false;
        int digitCount = 0;
        
        for (char c : password.toCharArray()) {
            if ((c >= '0' && c <= '9')) {
                digitCount++;
            } else if ("!@#$%^&*()-_=+".indexOf(c) >= 0) {
                hasSpecial = true;
            }
        }
        return hasSpecial && digitCount >= 3;
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1 - Додати користувача\n2 - Видалити користувача\n3 - Аутентифікація\n4 - Вийти");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice == 1) {
                    System.out.print("Введіть ім’я: ");
                    String username = scanner.nextLine();
                    System.out.print("Введіть пароль: ");
                    String password = scanner.nextLine();
                    addUser(username, password);
                } else if (choice == 2) {
                    System.out.print("Введіть ім’я користувача для видалення: ");
                    String username = scanner.nextLine();
                    deleteUser(username);
                } else if (choice == 3) {
                    System.out.print("Введіть ім’я користувача: ");
                    String username = scanner.nextLine();
                    System.out.print("Введіть пароль: ");
                    String password = scanner.nextLine();
                    authenticate(username, password);
                } else if (choice == 4) {
                    break;
                } else {
                    System.out.println("Невірний вибір, спробуйте ще раз");
                }
            } catch (NumberFormatException e) {
                System.out.println("Помилка: введіть число");
            } catch (IllegalArgumentException | IllegalStateException | SecurityException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        UserAuthSystem authSystem = new UserAuthSystem();
        authSystem.menu();
    }
}
