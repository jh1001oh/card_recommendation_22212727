package benefit;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CardDB cardDB = new CardDB();
        cardDB.loadAll();

        MemberRepository memberRepository = new MemberRepository();
        AuthService authService = new AuthService(memberRepository);
        RecommendEngine recommendEngine = new RecommendEngine(cardDB);

        Scanner sc = new Scanner(System.in);

        System.out.println("=================================");
        System.out.println("    BeneFIT에 오신 것을 환영합니다!    ");
        System.out.println("=================================");

        while (true) {
            System.out.println("\n1. 로그인");
            System.out.println("2. 회원가입");
            System.out.println("0. 종료");
            System.out.print("선택: ");
            String input = sc.nextLine().trim();

            if (input.equals("0")) {
                System.out.println("프로그램을 종료합니다.");
                break;
            } else if (input.equals("1")) {
                User loggedIn = authService.loginFlow(sc);
                if (loggedIn != null) {
                    SpendingData saved = SpendingData.loadFromFile(loggedIn.getEmail());
                    if (saved != null) {
                        loggedIn.setSpendingData(saved);
                        System.out.println("이전 지출 정보를 불러왔습니다.");
                    }
                    if (loggedIn.getRole().equals("admin")) {
                        Administrator admin = new Administrator(
                                loggedIn.getEmail(), loggedIn.getPassword(), loggedIn.getName(), cardDB);
                        admin.adminMenu(sc);
                        authService.logout(); // 관리자 로그아웃도 처리
                    } else {
                        userMenu(sc, loggedIn, recommendEngine);
                        authService.logout();
                    }
                }
            } else if (input.equals("2")) {
                authService.registerFlow(sc);
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
        sc.close();
    }

    private static void userMenu(Scanner sc, User user, RecommendEngine engine) {
        while (true) {
            System.out.println("\n========= 메인 메뉴 =========");
            System.out.println("안녕하세요, " + user.getName() + "님!");
            System.out.println("1. 지출 정보 입력/수정");
            System.out.println("2. 카드 추천 결과 조회");
            System.out.println("3. 카드 상세 정보 확인");
            System.out.println("4. 카드 비교");
            System.out.println("0. 로그아웃");
            System.out.print("선택: ");
            String input = sc.nextLine().trim();

            if (input.equals("0")) {
                System.out.println("로그아웃 되었습니다.");
                break;
            } else if (input.equals("1")) {
                SpendingData sd = SpendingData.inputFlow(sc, user.getSpendingData());
                user.setSpendingData(sd);
                sd.saveToFile(user.getEmail());
                System.out.println("지출 정보가 저장되었습니다.");
                System.out.print("\n계속하려면 엔터를 누르세요...");
                sc.nextLine();
            } else if (input.equals("2")) {
                if (user.getSpendingData() == null) {
                    System.out.println("먼저 지출 정보를 입력해주세요.");
                } else {
                    showRecommendMenu(sc, user, engine);
                }
            } else if (input.equals("3")) {
                if (user.getSpendingData() == null) {
                    System.out.println("먼저 지출 정보를 입력해주세요.");
                } else {
                    List<RecommendResult> results = engine.recommend(user.getSpendingData(), 5);
                    showCardDetail(sc, results);
                }
            } else if (input.equals("4")) {
                if (user.getSpendingData() == null) {
                    System.out.println("먼저 지출 정보를 입력해주세요.");
                } else {
                    List<RecommendResult> results = engine.recommend(user.getSpendingData(), 5);
                    RecommendResult.displayList(results);
                    String next = CompareResult.compareFlow(sc, results, user.getSpendingData());
                    if (next.equals("exit")) {
                        System.out.println("로그아웃 되었습니다.");
                        return;
                    }
                }
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }

    private static void showRecommendMenu(Scanner sc, User user, RecommendEngine engine) {
        while (true) {
            List<RecommendResult> results = engine.recommend(user.getSpendingData(), 5);
            RecommendResult.displayList(results);

            System.out.println("\n1. 카드 상세 정보 보기");
            System.out.println("2. 카드 비교");
            System.out.println("0. 메인 메뉴로");
            System.out.print("선택: ");
            String sel = sc.nextLine().trim();

            if (sel.equals("0")) {
                break;
            } else if (sel.equals("1")) {
                showCardDetail(sc, results);
            } else if (sel.equals("2")) {
                String next = CompareResult.compareFlow(sc, results, user.getSpendingData());
                if (next.equals("exit")) {
                    System.out.println("로그아웃 되었습니다.");
                    return;
                }
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }

    private static void showCardDetail(Scanner sc, List<RecommendResult> results) {
        RecommendResult.displayList(results);
        System.out.print("상세 정보를 볼 카드 번호 선택 (0: 돌아가기): ");
        String sel = sc.nextLine().trim();
        if (!sel.equals("0")) {
            try {
                int idx = Integer.parseInt(sel) - 1;
                if (idx >= 0 && idx < results.size()) {
                    results.get(idx).getCard().displayDetail();
                } else {
                    System.out.println("올바른 번호를 입력해주세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("올바른 번호를 입력해주세요.");
            }
        }
        System.out.print("\n계속하려면 엔터를 누르세요...");
        sc.nextLine();
    }
}
