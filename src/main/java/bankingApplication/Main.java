package bankingApplication;

public class Main {
    public static void main(String[] args) {

        Users user = new Users();

        CollectUserData collectUserData = new CollectUserData();

        collectUserData.start();
        collectUserData.bankMenu();






    }

}
