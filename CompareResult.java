package benefit;

import java.util.*;

public class CompareResult {
    private List<CardInfo> selectedCards;
    private Map<String, Integer> comparedData;

    public CompareResult(List<CardInfo> cards, SpendingData sd) {
        this.selectedCards = cards;
        this.comparedData = new LinkedHashMap<>();
        for (CardInfo card : cards) {
            int total = 0;
            for (BenefitCondition bc : card.getBenefits()) {
                total += bc.calculateBenefit(sd.getAmount(bc.getCategory()));
            }
            comparedData.put(card.getCardName(), total);
        }
    }

    public void display(SpendingData sd) {
        String[] categories = {"food", "shopping", "telecom", "ott", "fuel", "convenience"};
        String[] catKorean   = {"외식",  "쇼핑",      "통신",     "OTT",  "주유",  "편의점"};

        System.out.println("\n========== 카드 비교 ==========");

        // 헤더
        System.out.printf("%-12s", "항목");
        for (CardInfo card : selectedCards) {
            System.out.printf("%-20s", card.getCardName());
        }
        System.out.println();
        System.out.println("-".repeat(12 + 20 * selectedCards.size()));

        // 연회비
        System.out.printf("%-12s", "연회비");
        for (CardInfo card : selectedCards) {
            System.out.printf("%-20s", String.format("%,d원", card.getAnnualFee()));
        }
        System.out.println();

        // 전월실적
        System.out.printf("%-12s", "전월실적");
        for (CardInfo card : selectedCards) {
            String perf = card.getMinPerformance() == 0
                    ? "조건 없음"
                    : String.format("%,d원↑", card.getMinPerformance());
            System.out.printf("%-20s", perf);
        }
        System.out.println();

        // 카테고리별 혜택
        for (int i = 0; i < categories.length; i++) {
            System.out.printf("%-12s", catKorean[i] + "혜택");
            for (CardInfo card : selectedCards) {
                int benefit = 0;
                for (BenefitCondition bc : card.getBenefits()) {
                    if (bc.getCategory().equals(categories[i])) {
                        benefit += bc.calculateBenefit(sd.getAmount(bc.getCategory()));
                    }
                }
                System.out.printf("%-20s", benefit > 0 ? String.format("%,d원", benefit) : "-");
            }
            System.out.println();
        }

        // 총 예상 혜택
        System.out.println("-".repeat(12 + 20 * selectedCards.size()));
        System.out.printf("%-12s", "총 예상혜택");
        for (CardInfo card : selectedCards) {
            System.out.printf("%-20s", String.format("%,d원", comparedData.getOrDefault(card.getCardName(), 0)));
        }
        System.out.println();
        System.out.println("==============================");
    }

    public Map<String, Integer> getComparedData() { return comparedData; }

    // Use Case #6: 비교 후 다시 선택 / 추천화면 / 종료 옵션 포함
    // 반환값: "compare" = 다시 비교, "recommend" = 추천화면, "exit" = 종료
    public static String compareFlow(Scanner sc, List<RecommendResult> results, SpendingData sd) {
        if (results.isEmpty()) {
            System.out.println("비교할 카드가 없습니다.");
            return "recommend";
        }

        while (true) {
            System.out.print("비교할 카드 번호 입력 (2~3개, 쉼표 구분, 예: 1,2,3): ");
            String input = sc.nextLine().trim();
            String[] tokens = input.split(",");
            if (tokens.length < 2 || tokens.length > 3) {
                System.out.println("2개 이상 3개 이하로 선택해주세요.");
                continue;
            }
            List<CardInfo> selected = new ArrayList<>();
            boolean valid = true;
            for (String token : tokens) {
                try {
                    int idx = Integer.parseInt(token.trim()) - 1;
                    if (idx < 0 || idx >= results.size()) {
                        System.out.println("올바른 번호를 입력해주세요.");
                        valid = false;
                        break;
                    }
                    selected.add(results.get(idx).getCard());
                } catch (NumberFormatException e) {
                    System.out.println("숫자를 입력해주세요.");
                    valid = false;
                    break;
                }
            }
            if (!valid) continue;

            CompareResult cr = new CompareResult(selected, sd);
            cr.display(sd);

            // 비교 후 선택 옵션 (Use Case #6 Step 3)
            System.out.println("\n1. 다시 선택");
            System.out.println("2. 추천 화면으로");
            System.out.println("0. 종료");
            System.out.print("선택: ");
            String next = sc.nextLine().trim();
            if (next.equals("1")) {
                continue;  // 다시 비교
            } else if (next.equals("0")) {
                return "exit";
            } else {
                return "recommend";
            }
        }
    }
}
