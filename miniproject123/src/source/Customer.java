package source;
class Customer implements User {
    private String username;
    private String password;
    private String name;
    private String email;
    private String mobile;

    public Customer(String username, String password, String name, String email, String mobile) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getMobile() {
        return mobile;
    }

    // Getters and other methods as before
}






