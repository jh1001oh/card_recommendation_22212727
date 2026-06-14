package benefit;

import java.util.List;
import java.util.Scanner;

public class Administrator extends User {
    private String adminEmail;
    private CardDB cardDB;

    public Administrator(String email, String password, String name, CardDB cardDB) {
        super(email, password, name, "admin");
        this.adminEmail = email;
        this.cardDB = cardDB;
    }

    public void addCard(CardInfo c) {
        cardDB.save(c);
        System.out.println("카드가 추가되었습니다: " + c.getCardName());
    }

    public void updateCard(CardInfo c) {
        cardDB.update(c);
        System.out.println("카드 정보가 수정되었습니다: " + c.getCardName());
    }

    public boolean deleteCard(String cardId) {
        boolean result = cardDB.delete(cardId);
        if (result) System.out.println("카드가 삭제되었습니다.");
        else System.out.println("해당 카드를 찾을 수 없습니다.");
        return result;
    }

    public void listAllCards() {
        List<CardInfo> cards = cardDB.findAll();
        if (cards.isEmpty()) {
            System.out.println("등록된 카드가 없습니다.");
            return;
        }
        System.out.println("\n========= 전체 카드 목록 =========");
        for (int i = 0; i < cards.size(); i++) {
            CardInfo c = cards.get(i);
            System.out.printf("%d. [%s] %s (연회비: %,d원, 전월실적: %,d원)%n",
                    i + 1, c.getCardId(), c.getCardName(), c.getAnnualFee(), c.getMinPerformance());
        }
    }

    public void adminMenu(Scanner sc) {
        while (true) {
            System.out.println("\n===== 관리자 메뉴 =====");
            System.out.println("1. 전체 카드 목록 조회");
            System.out.println("2. 새 카드 추가");
            System.out.println("3. 카드 수정");
            System.out.println("4. 카드 삭제");
            System.out.println("0. 로그아웃");
            System.out.print("선택: ");
            String input = sc.nextLine().trim();

            if (input.equals("0")) {
                System.out.println("로그아웃 되었습니다.");
                break;
            } else if (input.equals("1")) {
                listAllCards();
            } else if (input.equals("2")) {
                addCardFlow(sc);
            } else if (input.equals("3")) {
                updateCardFlow(sc);
            } else if (input.equals("4")) {
                deleteCardFlow(sc);
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }

    private void addCardFlow(Scanner sc) {
        try {
            System.out.print("카드 ID: ");
            String id = sc.nextLine().trim();
            System.out.print("카드명: ");
            String name = sc.nextLine().trim();
            System.out.print("카드사: ");
            String company = sc.nextLine().trim();
            System.out.print("연회비(원): ");
            int annualFee = Integer.parseInt(sc.nextLine().trim());
            System.out.print("전월 실적 조건(원): ");
            int minPerf = Integer.parseInt(sc.nextLine().trim());

            CardInfo card = new CardInfo(id, name, company, annualFee, minPerf);

            System.out.println("혜택 추가 (카테고리: food/shopping/telecom/ott/fuel/convenience)");
            while (true) {
                System.out.print("혜택 추가? (y/n): ");
                if (!sc.nextLine().trim().equalsIgnoreCase("y")) break;
                System.out.print("카테고리: ");
                String cat = sc.nextLine().trim();
                System.out.print("할인율 (예: 0.05): ");
                double discount = Double.parseDouble(sc.nextLine().trim());
                System.out.print("적립률 (예: 0.02): ");
                double cashback = Double.parseDouble(sc.nextLine().trim());
                System.out.print("월 한도(원): ");
                int limit = Integer.parseInt(sc.nextLine().trim());
                card.addBenefit(new BenefitCondition(cat, discount, cashback, limit));
            }
            addCard(card);
        } catch (Exception e) {
            System.out.println("입력 오류: " + e.getMessage());
        }
    }

    private void updateCardFlow(Scanner sc) {
        listAllCards();
        System.out.print("수정할 카드 ID: ");
        String id = sc.nextLine().trim();
        CardInfo existing = cardDB.findById(id);
        if (existing == null) {
            System.out.println("해당 카드를 찾을 수 없습니다.");
            return;
        }
        try {
            System.out.print("새 카드명 (현재: " + existing.getCardName() + "): ");
            String name = sc.nextLine().trim();
            System.out.print("새 카드사 (현재: " + existing.getCompany() + "): ");
            String company = sc.nextLine().trim();
            System.out.print("새 연회비 (현재: " + existing.getAnnualFee() + "): ");
            int annualFee = Integer.parseInt(sc.nextLine().trim());
            System.out.print("새 전월 실적 (현재: " + existing.getMinPerformance() + "): ");
            int minPerf = Integer.parseInt(sc.nextLine().trim());
            CardInfo updated = new CardInfo(id, name, company, annualFee, minPerf);
            updateCard(updated);
        } catch (Exception e) {
            System.out.println("입력 오류: " + e.getMessage());
        }
    }

    private void deleteCardFlow(Scanner sc) {
        listAllCards();
        System.out.print("삭제할 카드 ID: ");
        String id = sc.nextLine().trim();
        deleteCard(id);
    }

    public String getAdminEmail() { return adminEmail; }
}
