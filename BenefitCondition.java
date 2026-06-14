package benefit;

public class BenefitCondition {
    private String category;
    private double discountRate;
    private double cashbackRate;
    private int monthlyLimit;

    public BenefitCondition(String category, double discountRate, double cashbackRate, int monthlyLimit) {
        this.category = category;
        this.discountRate = discountRate;
        this.cashbackRate = cashbackRate;
        this.monthlyLimit = monthlyLimit;
    }

    public int calculateBenefit(int spent) {
        double totalRate = discountRate + cashbackRate;
        int benefit = (int)(spent * totalRate);
        return Math.min(benefit, monthlyLimit);
    }

    public String getCategory() { return category; }
    public double getDiscountRate() { return discountRate; }
    public double getCashbackRate() { return cashbackRate; }
    public int getMonthlyLimit() { return monthlyLimit; }

    public String getCategoryKorean() {
        switch (category) {
            case "food":        return "외식";
            case "shopping":    return "쇼핑";
            case "telecom":     return "통신";
            case "ott":         return "OTT";
            case "fuel":        return "주유";
            case "convenience": return "편의점";
            default:            return category;
        }
    }

    // 파일 저장용
    public String toFileString() {
        return category + "|" + discountRate + "|" + cashbackRate + "|" + monthlyLimit;
    }

    public static BenefitCondition fromFileString(String s) {
        String[] p = s.split("\\|");
        if (p.length < 4) return null;
        return new BenefitCondition(p[0], Double.parseDouble(p[1]), Double.parseDouble(p[2]), Integer.parseInt(p[3]));
    }
}
