package bankingApplication;
import java.util.ArrayList;

public class Repository {

    private ArrayList<Users> users = new ArrayList<>();
    public String addUser(Users user) throws Exception{


        if(user.getName().isEmpty())
            throw new Exception("Please enter your full name!");

        if(user.getIDNo().isEmpty())
        throw new Exception("Please enter your personal ID number:");

        if(user.getDateofBirth() == null)
        throw new Exception("Please enter your date of birth:");

        if(user.getSex() == null)
            throw new Exception("Please select your gender:");

        if(user.getAddress().isEmpty())
        throw new Exception("Please enter your full address:");

        if(user.getCountry() == null)
        throw new Exception("Please select your country of residence:");

        if(user.getPhone().isEmpty())
        throw new Exception("Please enter your phone number:");

        this.users.add(user);
        return "User " + user.getName() + " added successfully";
    }

    public boolean userExists(String username){
        ArrayList<Users> users = new ArrayList<>();

        for(Users currentUser: this.users)
            if(currentUser.getIDNo().trim().contains(username)) {
            }
        return true;
    }
    public ArrayList<Users> getAllUsers(){
        return this.users;
    }


}
