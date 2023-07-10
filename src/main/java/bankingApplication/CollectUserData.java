package bankingApplication;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CollectUserData {
    Scanner scanner = new Scanner(System.in);
    Users user = new Users();
    private final Repository repository;
    private static Lock lock = new ReentrantLock();
    private boolean isUserLoggedIn = false;
    private double amount;


    public CollectUserData() {
        this.repository = new Repository();
        this.lock = new ReentrantLock();
    }

    public void start() {
        String menuChoice = this.getInfo("""
                Welcome to the SGT Bank!
                Please note that we only accept users from the following countries:
                Latvia, Estonia, Lithuania, Sweden, Finland, and Denmark.
                If you already have an account in the SGT Bank, choose \"Log In\".
                For new users, choose \"Register\"
                1 - Log In
                2 - Register
                3 - Exit
                """);

        switch (menuChoice) {
            case "1":
                logIn();
                break;
            case "2":
                this.createUser();
                break;
            case "3":
                logout();
                System.exit(0);
                break;
            default:
                displayMessage("Please choose an option from the menu");
                this.start();
                break;
        }
    }

    public void bankMenu(){
        String again, choice;

        choice = getInfo("""
                Please choose a transaction.
                1 - Deposit money
                2 - Withdraw money
                3 - View account balance
                4 - View and manage user data
                5 - Return to the Main menu
                6 - Quit and log out
                """);
        switch (choice.toString()){
            case "1":
                this.depositFunds(amount);
                break;
            case "2":
                this.withdrawFunds(amount);
                break;
            case "3":
                this.showBalance(user);
                break;
            case "4":
                this.verifyData();
                break;
            case "5":
                this.start();
            case "6":
                logout();
                System.exit(0);
            default:
                this.bankMenu();
                break;
        }
        again = getInfo("Do you want to continue? yes/no ");
        do{
            this.bankMenu();
        } while (again.equals("yes"));
    }

    private void createUser() {
        try {
            Users user = this.collectData();
            String result = this.repository.addUser(user);
            displayMessage(result);
        } catch (Exception exception) {
            displayMessage("Error: " + exception.getMessage());
        } finally {

            verifyData();
        }
    }

    private void verifyData() {
        viewUserData();
        boolean isCorrect = this.getInfo("Is your personal data correct and complete? yes/no:").equalsIgnoreCase("yes");

        if ((isCorrect && !isUserLoggedIn)) {
            newPassword();
            displayMessage("""
            Please proceed to the log-in page.
            \n--------------------------------""");
            logIn();
        } else if (isCorrect && isUserLoggedIn){
                this.bankMenu();
        } else if (!isCorrect) {
            this.viewUserData();
            this.findAndUpdate();
        } else {
            displayMessage("Wrong input.");
            this.verifyData();
        }
    }

    private Users collectData() {

        user.setName(this.getInfo("Please enter your personal data!\n" +
                "Full name:"));

        user.setIDNo(this.getInfo("Personal ID number:"));

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dob = LocalDate.parse((getInfo("Date of birth in DD-MM-YYYY format:")), dateFormatter);
        user.setDateofBirth(dob);

        Gender gender = Gender.valueOf(this.getInfo("Gender: MALE, FEMALE, or OTHER:").toUpperCase().trim());
        user.setSex(gender);

        user.setAddress(this.getInfo("Full address:"));

        Country country = Country.valueOf(this.getInfo("Choose your country of residence: " +
                "LATVIA, ESTONIA, LITHUANIA, SWEDEN, FINLAND, or DENMARK").toUpperCase().trim());
        user.setCountry(country);

        String countryCode = getCountryCode(country);
        user.setCountryCode(countryCode);

        user.setPhone(countryCode + getInfo("Phone number: "));

        Currency currency = Currency.valueOf(this.getInfo("Choose your main currency: " +
                "EUR, DKK, or SEK").toUpperCase().trim());
        user.setCurrency(currency);

        user.setAccountNo(generateBankAccount());

        return user;
    }

    public String getCountryCode(Country country) {
        String countryCode = "";

        switch (country.toString()) {
            case "LATVIA":
                countryCode = "+371 ";
                break;
            case "ESTONIA":
                countryCode = "+372 ";
                break;
            case "LITHUANIA":
                countryCode = "+370 ";
                break;
            case "SWEDEN":
                countryCode = "+46 ";
                break;
            case "FINLAND":
                countryCode = "+358 ";
                break;
            case "DENMARK":
                countryCode = "+45 ";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + country);
        }
        return countryCode.toString();
    }

    private String generateBankAccount() {
        Users user = new Users();
        int min = 1, max = 1234567890, range = max - min;
        int randomAccount = (int) (Math.random() * range) + min;
        String accountNo = "SGT3502LV" + randomAccount;
        user.setAccountNo(accountNo);
        return accountNo;
    }
    public void viewUserData() {
        ArrayList<Users> users = this.repository.getAllUsers();
        for (Users currentUser : users) {
            currentUser.toString();
        }
        displayMessage("Please verify your personal data: " +
                users.toString());
    }
    public Users findAndUpdate() {

        String choice = this.getInfo("Please correct the missing data." +
                "\nChoose the field to update:" +
                "\n1 - Name" +
                "\n2 - Personal ID No." +
                "\n3 - Date of birth" +
                "\n4 - Gender" +
                "\n5 - Address" +
                "\n6 - Country" +
                "\n7 - Phone" +
                "\n8 - Currency");

        switch (choice) {
            case "1":
                String newName = getInfo("Enter new full name: ");
                user.setName(newName);
                break;
            case "2":
                String newID = getInfo("Enter new personalID: ");
                user.setIDNo(newID);
                break;
            case "3":
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate newDoB = LocalDate.parse((getInfo("Enter new date of birth, DD-MM-YYYY: ")), dateFormatter);
                user.setDateofBirth(newDoB);
                break;
            case "4":
                Gender newGender = Gender.valueOf(this.getInfo("Update gender: MALE, FEMALE, or OTHER:").toUpperCase().trim());
                user.setSex(newGender);
                break;
            case "5":
                String newAddress = getInfo("Enter new address: ");
                user.setAddress(newAddress);
                break;
            case "6":
                Country newCountry = Country.valueOf(this.getInfo("Choose country: LATVIA, LITHUANIA, ESTONIA, FINLAND, SWEDEN, or DENMARK").toUpperCase().trim());
                user.setCountry(newCountry);
                break;
            case "7":
                String newPhone = getInfo("Enter new phone number: " + getCountryCode(user.getCountry()));
                user.setPhone(getCountryCode(user.getCountry()) + newPhone);
                break;
            case "8":
                Currency newCurrency = Currency.valueOf(this.getInfo("Choose currency: EUR, DKK, or SEK").toUpperCase().trim());
                user.setCurrency(newCurrency);
                break;
            default:
                displayMessage("Wrong menu choice.");
                this.findAndUpdate();
                break;
        }

        if (this.getInfo("Do you want to update any other fields? yes/no:").equalsIgnoreCase("yes")) {
            this.findAndUpdate();
        } else {
            verifyData();
        }

        return user;
    }

    private String newPassword() {
        String newpassword = getInfo("""
                Please enter your password.
                Minimum length: 8 characters.
                Requirements: at least one UPPERCASE letter and one number.
                Password: """);
        //hide in console
        String confirmPassword = getInfo("Confirm the password: ");
        if (passwordValidator(newpassword, confirmPassword)) {
            displayMessage("Password is valid.");
            user.setPassword(newpassword);
        } else {
            while (!passwordValidator(newpassword, confirmPassword)) {
                displayMessage("Password is invalid/passwords do not match");
                this.newPassword();
                break;
            }
        }
        return newpassword;
    }

    public static boolean passwordValidator(String newpassword, String confirmPassword) {
       String pattern = "^(?=.*[A-Z])(?=.*[0-9]).{8,}$";
       Pattern regex = Pattern.compile(pattern);
       Matcher matcher = regex.matcher(newpassword);
       return matcher.matches() && newpassword.equals(confirmPassword);
    }

    public void logIn() {
        String password, username;
        username = getInfo("Enter your personal ID No.: ");
        password = getInfo("Enter your password: ");
        boolean match = (username.equals(user.getIDNo()) && password.equals(user.getPassword()));

        if (match) {
            isUserLoggedIn = true;
            displayMessage("Login successful, " + user.getName() + "!");
            bankMenu();
        } else if (!attemptLogin()){
            displayMessage("Another user is logged in the system. Please try later.\n----------------\n");
            start();
        } else if (!match){
            boolean yes = this.getInfo("Invalid personal ID No. or password. Try again? Yes/No: ").equalsIgnoreCase("yes");
            if (yes){
                do {
                    this.logIn();
                } while (yes);
            } if (!yes) {
                displayMessage("Seems that a user with this ID No. is not registered yet. Proceed to registration.");
                createUser();
                verifyData();
                this.logIn();
                    while(!match){
                        this.logIn();
                        isUserLoggedIn = false;
                        if(match){
                            isUserLoggedIn = true;
                            break;
                        }
                    }
            }
       } else {
               start();
    }
}

    public boolean attemptLogin(){
        if (lock.tryLock()) {
            if (!isUserLoggedIn) {
                isUserLoggedIn = true;
                lock.unlock();
                return true;
            }
            lock.unlock();
        }
        return false;
    }
    public void logout(){
        if(isUserLoggedIn){
            lock.lock();
            isUserLoggedIn = false;
            lock.unlock();
            for (int i = 0; i < 30; i++) {
                System.out.println();
            }
                displayMessage("Good bye! User logged out.");
            start();
        }
    }

    private void debit(double amount) {
        this.amount = amount;
        double balance = user.getBalance();
        if (amount <= 0) {
            String choice = getInfo("Amount must be positive. Try again? yes/no");
            switch (choice.toString()) {
                case "yes":
                    double updateBalance = Double.parseDouble(getInfo("Amount: "));
                    if (updateBalance > 0) {
                        user.setBalance(updateBalance);
                    }
                    break;
                case "no":
                    bankMenu();
                    break;
            }
        } else {
            displayMessage("Transaction successful.");
            double newbalance = balance + amount;
            user.setBalance(newbalance);
        }
    }

    private void credit(double amount) {
        this.amount = amount;
        double balance = user.getBalance();
        double newBalance;
        if (amount > balance) {
            displayMessage("Insufficient funds for this transaction. ");
            showBalance(user);
                        return;
        } else {
            displayMessage("Transaction successful.");
            balance -= amount;
            user.setBalance(balance);
        }
    }

    private void depositFunds(double amount) {
        amount = Double.parseDouble(getInfo("Please enter the amount to withdraw: ").replace(",", "."));
        debit(amount);
    }

    private void withdrawFunds(double amount){
        amount = Double.parseDouble(getInfo("Please enter the amount to withdraw: ").replace(",", "."));
        credit(amount);
    }

    private void showBalance(Users user) {
        double balance = user.getBalance();
        displayMessage("Current balance: " + balance + " " + user.getCurrency());
    }

    public void displayMessage(String message){
        System.out.println(message);
    }

    public String getInfo(String message){
        System.out.println(message);
        return scanner.nextLine();
    }

}
