package benefit;

import java.util.List;
import java.util.Map;

public class RecommendResult {
    private CardInfo card;
    private int estimatedMonthlyBenefit;
    private double pickingRate;
    private Map<String, Integer> benefitBreakdown;

    public RecommendResult(CardInfo card, int estimatedMonthlyBenefit, double pickingRate, Map<String, Integer> benefitBreakdown) {
        this.card = card;
        this.estimatedMonthlyBenefit = estimatedMonthlyBenefit;
        this.pickingRate = pickingRate;
        this.benefitBreakdown = benefitBreakdown;
    }

    public CardInfo getCard() { return card; }
    public int getEstimatedMonthlyBenefit() { return estimatedMonthlyBenefit; }
    public double getPickingRate() { return pickingRate; }
    public Map<String, Integer> getBenefitBreakdown() { return benefitBreakdown; }

    public void displaySummary(int rank) {
        System.out.printf("%d. [%s] %s | 예상 월 혜택: %,d원 | 피킹률: %.2f%% | 연회비: %,d원%n",
                rank,
                card.getCompany(),
                card.getCardName(),
                estimatedMonthlyBenefit,
                pickingRate * 100,
                card.getAnnualFee());
        if (!benefitBreakdown.isEmpty()) {
            StringBuilder sb = new StringBuilder("   └ ");
            boolean first = true;
            for (Map.Entry<String, Integer> entry : benefitBreakdown.entrySet()) {
                if (!first) sb.append(" | ");
                sb.append(entry.getKey()).append(": ").append(String.format("%,d원", entry.getValue()));
                first = false;
            }
            System.out.println(sb.toString());
        }
    }

    public static void displayList(List<RecommendResult> results) {
        System.out.println("\n========== 카드 추천 결과 ==========");
        if (results.isEmpty()) {
            System.out.println("조건을 충족하는 카드가 없습니다.");
            System.out.println("지출 금액이 전월 실적 조건보다 낮을 수 있습니다.");
            return;
        }
        for (int i = 0; i < results.size(); i++) {
            results.get(i).displaySummary(i + 1);
        }
        System.out.println("==================================");
    }
}
