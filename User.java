package benefit;

public class User {
    private String email;
    private String password;
    private String name;
    private String role;
    private SpendingData spendingData;

    public User(String email, String password, String name, String role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.spendingData = null;
    }

    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public String getRole() { return role; }
    public SpendingData getSpendingData() { return spendingData; }
    public void setSpendingData(SpendingData spendingData) { this.spendingData = spendingData; }

    // 파일 저장용 직렬화
    public String toFileString() {
        return email + "," + password + "," + name + "," + role;
    }

    public static User fromFileString(String line) {
        String[] parts = line.split(",", 4);
        if (parts.length < 4) return null;
        return new User(parts[0], parts[1], parts[2], parts[3]);
    }
}
