package benefit;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CardDB {
    private List<CardInfo> cards;
    private static final String FILE_PATH = "cards.txt";

    public CardDB() {
        cards = new ArrayList<>();
    }

    public void loadAll() {
        File f = new File(FILE_PATH);
        if (!f.exists()) {
            loadDefaultCards();
            saveToFile();
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                CardInfo card = CardInfo.fromFileString(line);
                if (card != null) cards.add(card);
            }
        } catch (IOException e) {
            System.out.println("카드 데이터 로드 오류: " + e.getMessage());
        }
        if (cards.isEmpty()) {
            loadDefaultCards();
            saveToFile();
        }
    }

    public List<CardInfo> findAll() { return new ArrayList<>(cards); }

    public CardInfo findById(String cardId) {
        for (CardInfo c : cards) {
            if (c.getCardId().equals(cardId)) return c;
        }
        return null;
    }

    public void save(CardInfo c) {
        cards.add(c);
        saveToFile();
    }

    public void update(CardInfo c) {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getCardId().equals(c.getCardId())) {
                cards.set(i, c);
                saveToFile();
                return;
            }
        }
    }

    public boolean delete(String cardId) {
        boolean removed = cards.removeIf(c -> c.getCardId().equals(cardId));
        if (removed) saveToFile();
        return removed;
    }

    private void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (CardInfo c : cards) {
                pw.println(c.toFileString());
            }
        } catch (IOException e) {
            System.out.println("카드 데이터 저장 오류: " + e.getMessage());
        }
    }

    private void loadDefaultCards() {

        // 신한카드 Mr.Life
        // 출처: 카드고릴라 (card-gorilla.com/card/detail/13)
        // 연회비: 15,000원 / 전월실적: 30만원 이상
        // 공과금(통신요금) 10% 할인 - 전월실적 30~50만원 구간 월 한도 3,000원
        // 편의점·식음료 10% 할인 - 전월실적 30~50만원 구간 월 한도 각 10,000원
        // → 전월실적 30만원(최소 충족) 구간 한도 적용
        CardInfo c1 = new CardInfo("C001", "신한카드 Mr.Life", "신한카드", 15000, 300000);
        c1.addBenefit(new BenefitCondition("telecom",     0.10, 0.00, 3000));
        c1.addBenefit(new BenefitCondition("convenience", 0.10, 0.00, 10000));
        c1.addBenefit(new BenefitCondition("food",        0.10, 0.00, 10000));
        cards.add(c1);

        // KB국민 My WE:SH 카드
        // 출처: 카드고릴라 (card-gorilla.com/card/detail/2441)
        // 연회비: 15,000원 / 전월실적: 40만원 이상
        // 음식점·편의점 10% 할인 - 월 한도 5,000원 (건당 2,500원 한도)
        // 이동통신요금(SKT/KT/LGU+) 10% 할인 - 월 한도 5,000원 (건당 2,500원 한도)
        // OTT(넷플릭스/유튜브프리미엄 등) 30% 할인 - 월 한도 별도 없음
        CardInfo c2 = new CardInfo("C002", "KB국민 My WE:SH", "KB국민카드", 15000, 400000);
        c2.addBenefit(new BenefitCondition("food",        0.10, 0.00, 5000));
        c2.addBenefit(new BenefitCondition("convenience", 0.10, 0.00, 5000));
        c2.addBenefit(new BenefitCondition("telecom",     0.10, 0.00, 5000));
        c2.addBenefit(new BenefitCondition("ott",         0.30, 0.00, 10000));
        cards.add(c2);

        // 카드의정석 EVERY DISCOUNT (우리카드)
        // 출처: 카드고릴라 (card-gorilla.com/card/detail/2719), 우리카드 공식
        // 연회비: 12,000원 / 전월실적: 없음(기본 0.8%), 40만원 이상 시 간편결제 2% 추가
        // 전 가맹점 0.8% 기본 청구할인 (한도 없음)
        // 간편결제(우리WON페이/네이버페이/카카오페이) 2% 추가 (전월실적 40만원 이상 시)
        CardInfo c3 = new CardInfo("C003", "카드의정석 EVERY DISCOUNT", "우리카드", 12000, 0);
        c3.addBenefit(new BenefitCondition("food",        0.008, 0.00, 9999999));
        c3.addBenefit(new BenefitCondition("shopping",    0.008, 0.00, 9999999));
        c3.addBenefit(new BenefitCondition("convenience", 0.008, 0.00, 9999999));
        c3.addBenefit(new BenefitCondition("telecom",     0.008, 0.00, 9999999));
        c3.addBenefit(new BenefitCondition("ott",         0.008, 0.00, 9999999));
        c3.addBenefit(new BenefitCondition("fuel",        0.008, 0.00, 9999999));
        cards.add(c3);

        // 현대카드 ZERO Edition3 (할인형)
        // 출처: 카드고릴라 (card-gorilla.com/card/detail/2646), 뱅크샐러드
        // 연회비: 15,000원 / 전월실적: 없음
        // 전 가맹점 0.8% 할인 (한도 없음)
        CardInfo c4 = new CardInfo("C004", "현대카드 ZERO Edition3", "현대카드", 15000, 0);
        c4.addBenefit(new BenefitCondition("food",        0.008, 0.00, 9999999));
        c4.addBenefit(new BenefitCondition("convenience", 0.008, 0.00, 9999999));
        c4.addBenefit(new BenefitCondition("shopping",    0.008, 0.00, 9999999));
        c4.addBenefit(new BenefitCondition("fuel",        0.008, 0.00, 9999999));
        c4.addBenefit(new BenefitCondition("telecom",     0.008, 0.00, 9999999));
        c4.addBenefit(new BenefitCondition("ott",         0.008, 0.00, 9999999));
        cards.add(c4);

        // 롯데카드 LOCA LIKIT 1.2
        // 출처: 카드고릴라 (card-gorilla.com), 롯데카드 공식
        // 연회비: 10,000원 / 전월실적: 없음
        // 국내외 전 가맹점 1.2% 결제일 할인 (한도 없음)
        // 온라인 가맹점 1.5% 결제일 할인 (한도 없음)
        CardInfo c5 = new CardInfo("C005", "롯데카드 LOCA LIKIT 1.2", "롯데카드", 10000, 0);
        c5.addBenefit(new BenefitCondition("food",        0.012, 0.00, 9999999));
        c5.addBenefit(new BenefitCondition("shopping",    0.015, 0.00, 9999999)); // 온라인 1.5%
        c5.addBenefit(new BenefitCondition("convenience", 0.012, 0.00, 9999999));
        c5.addBenefit(new BenefitCondition("fuel",        0.012, 0.00, 9999999));
        c5.addBenefit(new BenefitCondition("ott",         0.015, 0.00, 9999999)); // 온라인 1.5%
        c5.addBenefit(new BenefitCondition("telecom",     0.012, 0.00, 9999999));
        cards.add(c5);

        // 하나카드 Any PLUS
        // 출처: 카드고릴라 (card-gorilla.com/card/detail/252)
        // 연회비: 15,000원 / 전월실적: 없음
        // 국내 오프라인 전 가맹점 0.7% 청구할인 (한도 없음)
        // 국내 온라인 가맹점 1.7% 청구할인 (월 한도 10만원)
        CardInfo c6 = new CardInfo("C006", "하나카드 Any PLUS", "하나카드", 15000, 0);
        c6.addBenefit(new BenefitCondition("food",        0.007, 0.00, 9999999));
        c6.addBenefit(new BenefitCondition("shopping",    0.017, 0.00, 100000));
        c6.addBenefit(new BenefitCondition("convenience", 0.007, 0.00, 9999999));
        c6.addBenefit(new BenefitCondition("fuel",        0.007, 0.00, 9999999));
        c6.addBenefit(new BenefitCondition("ott",         0.017, 0.00, 100000));
        c6.addBenefit(new BenefitCondition("telecom",     0.007, 0.00, 9999999));
        cards.add(c6);
    }
}
