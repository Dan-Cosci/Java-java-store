package Objects;

public class User {

    private int id;
    private String username;
    private String password;
    private String email;
    private String status;

    // Empty constructor
    public User() {
    }

    // Full constructor
    public User(int id, String username, String password, String email, String status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.status = status;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
    
    public String getStatus(){
        return status;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setStatus(String status){
        this.status = status;
    }

    // toString method
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

