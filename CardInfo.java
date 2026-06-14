package benefit;

import java.util.ArrayList;
import java.util.List;

public class CardInfo {
    private String cardId;
    private String cardName;
    private String company;
    private int annualFee;
    private int minPerformance;
    private List<BenefitCondition> benefits;

    public CardInfo(String cardId, String cardName, String company, int annualFee, int minPerformance) {
        this.cardId = cardId;
        this.cardName = cardName;
        this.company = company;
        this.annualFee = annualFee;
        this.minPerformance = minPerformance;
        this.benefits = new ArrayList<>();
    }

    public void addBenefit(BenefitCondition bc) {
        benefits.add(bc);
    }

    public List<BenefitCondition> getBenefits() { return benefits; }
    public String getCardId() { return cardId; }
    public String getCardName() { return cardName; }
    public String getCompany() { return company; }
    public int getAnnualFee() { return annualFee; }
    public int getMinPerformance() { return minPerformance; }

    public void displayDetail() {
        System.out.println("\n========== 카드 상세 정보 ==========");
        System.out.println("카드명   : " + cardName);
        System.out.println("카드사   : " + company);
        System.out.printf("연회비   : %,d원%n", annualFee);
        String perf = minPerformance == 0 ? "조건 없음" : String.format("%,d원 이상", minPerformance);
        System.out.println("전월 실적: " + perf);
        System.out.println("-------- 혜택 조건 --------");
        if (benefits.isEmpty()) {
            System.out.println("등록된 혜택 없음");
        } else {
            for (BenefitCondition bc : benefits) {
                String limit = bc.getMonthlyLimit() >= 9999999
                        ? "한도 없음"
                        : String.format("%,d원", bc.getMonthlyLimit());
                System.out.printf("[%s] 할인율: %.1f%%, 적립률: %.1f%%, 월한도: %s%n",
                        bc.getCategoryKorean(),
                        bc.getDiscountRate() * 100,
                        bc.getCashbackRate() * 100,
                        limit);
            }
        }
        System.out.println("==================================");
    }

    // 파일 저장용
    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append(cardId).append(";").append(cardName).append(";")
          .append(company).append(";").append(annualFee).append(";").append(minPerformance);
        for (BenefitCondition bc : benefits) {
            sb.append(";BC:").append(bc.toFileString());
        }
        return sb.toString();
    }

    public static CardInfo fromFileString(String line) {
        String[] parts = line.split(";");
        if (parts.length < 5) return null;
        CardInfo card = new CardInfo(parts[0], parts[1], parts[2],
                Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));
        for (int i = 5; i < parts.length; i++) {
            if (parts[i].startsWith("BC:")) {
                BenefitCondition bc = BenefitCondition.fromFileString(parts[i].substring(3));
                if (bc != null) card.addBenefit(bc);
            }
        }
        return card;
    }
}
