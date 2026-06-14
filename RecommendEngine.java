package benefit;

import java.util.*;

public class RecommendEngine {
    private CardDB cardDB;

    public RecommendEngine(CardDB cardDB) {
        this.cardDB = cardDB;
    }

    public List<RecommendResult> recommend(SpendingData sd, int topN) {
        List<CardInfo> allCards = cardDB.findAll();
        List<RecommendResult> results = new ArrayList<>();

        for (CardInfo card : allCards) {
            if (!isEligible(card, sd)) continue;
            int totalBenefit = calcTotalBenefit(card, sd);
            Map<String, Integer> breakdown = calcBreakdown(card, sd);
            double pickingRate = sd.getTotalMonthly() > 0
                    ? (double) totalBenefit / sd.getTotalMonthly()
                    : 0;
            results.add(new RecommendResult(card, totalBenefit, pickingRate, breakdown));
        }

        results.sort((a, b) -> b.getEstimatedMonthlyBenefit() - a.getEstimatedMonthlyBenefit());

        return results.size() <= topN ? results : results.subList(0, topN);
    }

    private int calcTotalBenefit(CardInfo card, SpendingData sd) {
        int total = 0;
        for (BenefitCondition bc : card.getBenefits()) {
            int spent = sd.getAmount(bc.getCategory());
            total += bc.calculateBenefit(spent);
        }
        return total;
    }

    private Map<String, Integer> calcBreakdown(CardInfo card, SpendingData sd) {
        Map<String, Integer> breakdown = new LinkedHashMap<>();
        for (BenefitCondition bc : card.getBenefits()) {
            int spent = sd.getAmount(bc.getCategory());
            int benefit = bc.calculateBenefit(spent);
            if (benefit > 0) {
                breakdown.put(bc.getCategoryKorean(), benefit);
            }
        }
        return breakdown;
    }

    private boolean isEligible(CardInfo card, SpendingData sd) {
        return sd.getTotalMonthly() >= card.getMinPerformance();
    }
}
