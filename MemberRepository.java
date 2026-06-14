package benefit;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MemberRepository {
    private List<User> members;
    private static final String FILE_PATH = "members.txt";

    public MemberRepository() {
        members = new ArrayList<>();
        loadFromFile();
        // 관리자 계정이 없으면 기본 생성
        if (findByEmail("admin@benefit.com") == null) {
            members.add(new User("admin@benefit.com", "admin1234", "관리자", "admin"));
            saveToFile();
        }
    }

    public void save(User u) {
        members.add(u);
        saveToFile();
    }

    public User findByEmail(String email) {
        for (User u : members) {
            if (u.getEmail().equals(email)) return u;
        }
        return null;
    }

    public List<User> findAll() {
        return new ArrayList<>(members);
    }

    public boolean delete(String email) {
        boolean removed = members.removeIf(u -> u.getEmail().equals(email));
        if (removed) saveToFile();
        return removed;
    }

    public void update(User u) {
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).getEmail().equals(u.getEmail())) {
                members.set(i, u);
                saveToFile();
                return;
            }
        }
    }

    private void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (User u : members) {
                pw.println(u.toFileString());
            }
        } catch (IOException e) {
            System.out.println("회원 데이터 저장 오류: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        File f = new File(FILE_PATH);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                User u = User.fromFileString(line);
                if (u != null) members.add(u);
            }
        } catch (IOException e) {
            System.out.println("회원 데이터 로드 오류: " + e.getMessage());
        }
    }
}
