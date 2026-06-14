package benefit;

import java.util.Scanner;

public class AuthService {
    private MemberRepository memberDB;
    private User currentUser;

    public AuthService(MemberRepository memberDB) {
        this.memberDB = memberDB;
        this.currentUser = null;
    }

    // 이메일 형식 검증
    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w._%+\\-]+@[\\w.\\-]+\\.[a-zA-Z]{2,}$");
    }

    // 비밀번호 검증: 4자 이상
    private boolean isValidPassword(String password) {
        return password != null && password.length() >= 4;
    }

    public boolean register(String email, String password, String name) {
        if (!isValidEmail(email)) {
            System.out.println("유효하지 않은 이메일 형식입니다. (예: user@example.com)");
            return false;
        }
        if (!isValidPassword(password)) {
            System.out.println("비밀번호는 4자 이상이어야 합니다.");
            return false;
        }
        if (name == null || name.trim().isEmpty()) {
            System.out.println("이름(닉네임)을 입력해주세요.");
            return false;
        }
        if (memberDB.findByEmail(email) != null) {
            System.out.println("이미 사용 중인 이메일입니다.");
            return false;
        }
        memberDB.save(new User(email, password, name, "user"));
        System.out.println("회원가입이 완료되었습니다!");
        return true;
    }

    public User login(String email, String password) {
        User u = memberDB.findByEmail(email);
        if (u != null && u.getPassword().equals(password)) {
            currentUser = u;
            System.out.println("로그인 성공! 환영합니다, " + u.getName() + "님.");
            return u;
        }
        System.out.println("이메일 또는 비밀번호가 올바르지 않습니다.");
        return null;
    }

    public void logout() {
        if (currentUser != null) {
            System.out.println(currentUser.getName() + "님 로그아웃 되었습니다.");
            currentUser = null;
        }
    }

    public User loginFlow(Scanner sc) {
        System.out.println("\n===== 로그인 =====");
        System.out.print("이메일: ");
        String email = sc.nextLine().trim();
        System.out.print("비밀번호: ");
        String password = sc.nextLine().trim();
        return login(email, password);
    }

    public void registerFlow(Scanner sc) {
        System.out.println("\n===== 회원가입 =====");

        // 이메일 입력 및 형식 검증
        String email;
        while (true) {
            System.out.print("이메일: ");
            email = sc.nextLine().trim();
            if (!isValidEmail(email)) {
                System.out.println("유효하지 않은 이메일 형식입니다. (예: user@example.com)");
                continue;
            }
            break;
        }

        // 비밀번호 입력 및 검증
        String password;
        while (true) {
            System.out.print("비밀번호 (4자 이상): ");
            password = sc.nextLine().trim();
            if (!isValidPassword(password)) {
                System.out.println("비밀번호는 4자 이상이어야 합니다.");
                continue;
            }
            System.out.print("비밀번호 확인: ");
            String passwordConfirm = sc.nextLine().trim();
            if (!password.equals(passwordConfirm)) {
                System.out.println("비밀번호가 일치하지 않습니다.");
                continue;
            }
            break;
        }

        // 이름 입력
        String name;
        while (true) {
            System.out.print("이름(닉네임): ");
            name = sc.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("이름(닉네임)을 입력해주세요.");
                continue;
            }
            break;
        }

        register(email, password, name);
    }
}
