package benefit;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class SpendingData {
    private int food;
    private int shopping;
    private int telecom;
    private int ott;
    private int fuel;
    private int convenience;

    public SpendingData() {}

    public void setCategory(String category, int amount) {
        switch (category) {
            case "food":         food = amount; break;
            case "shopping":     shopping = amount; break;
            case "telecom":      telecom = amount; break;
            case "ott":          ott = amount; break;
            case "fuel":         fuel = amount; break;
            case "convenience":  convenience = amount; break;
        }
    }

    public int getAmount(String category) {
        switch (category) {
            case "food":        return food;
            case "shopping":    return shopping;
            case "telecom":     return telecom;
            case "ott":         return ott;
            case "fuel":        return fuel;
            case "convenience": return convenience;
            default:            return 0;
        }
    }

    public int getTotalMonthly() {
        return food + shopping + telecom + ott + fuel + convenience;
    }

    public Map<String, Integer> toMap() {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("food", food);
        map.put("shopping", shopping);
        map.put("telecom", telecom);
        map.put("ott", ott);
        map.put("fuel", fuel);
        map.put("convenience", convenience);
        return map;
    }

    public void saveToFile(String email) {
        String filePath = "spending_" + email.replace("@", "_").replace(".", "_") + ".txt";
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            pw.println(food + "," + shopping + "," + telecom + "," + ott + "," + fuel + "," + convenience);
        } catch (IOException e) {
            System.out.println("지출 데이터 저장 오류: " + e.getMessage());
        }
    }

    public static SpendingData loadFromFile(String email) {
        String filePath = "spending_" + email.replace("@", "_").replace(".", "_") + ".txt";
        File f = new File(filePath);
        if (!f.exists()) return null;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line = br.readLine();
            if (line == null) return null;
            String[] parts = line.split(",");
            if (parts.length < 6) return null;
            SpendingData sd = new SpendingData();
            sd.food        = Integer.parseInt(parts[0]);
            sd.shopping    = Integer.parseInt(parts[1]);
            sd.telecom     = Integer.parseInt(parts[2]);
            sd.ott         = Integer.parseInt(parts[3]);
            sd.fuel        = Integer.parseInt(parts[4]);
            sd.convenience = Integer.parseInt(parts[5]);
            return sd;
        } catch (IOException e) {
            System.out.println("지출 데이터 로드 오류: " + e.getMessage());
            return null;
        }
    }

    // 반복문으로 변경해서 무한루프 방지
    public static SpendingData inputFlow(Scanner sc, SpendingData existing) {
        String[][] categories = {
            {"food",        "외식"},
            {"shopping",    "쇼핑"},
            {"telecom",     "통신"},
            {"ott",         "OTT"},
            {"fuel",        "주유"},
            {"convenience", "편의점"}
        };

        boolean isEdit = (existing != null);

        while (true) {
            SpendingData sd = new SpendingData();

            if (isEdit) {
                System.out.println("\n===== 지출 정보 수정 =====");
                System.out.println("(엔터만 누르면 기존 값 유지, 숫자 입력 시 변경)");
            } else {
                System.out.println("\n===== 월평균 지출 입력 =====");
                System.out.println("(사용하지 않는 카테고리는 0 입력)");
            }

            for (String[] cat : categories) {
                int currentVal = isEdit ? existing.getAmount(cat[0]) : 0;
                while (true) {
                    try {
                        if (isEdit) {
                            System.out.printf("%-10s (현재: %,d원, 새 금액): ", cat[1], currentVal);
                        } else {
                            System.out.printf("%-10s (원): ", cat[1]);
                        }
                        String input = sc.nextLine().trim();

                        if (isEdit && input.isEmpty()) {
                            sd.setCategory(cat[0], currentVal);
                            break;
                        }

                        int amount = Integer.parseInt(input);
                        if (amount < 0) {
                            System.out.println("0 이상의 금액을 입력해주세요.");
                            continue;
                        }
                        sd.setCategory(cat[0], amount);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("숫자를 입력해주세요.");
                    }
                }
            }

            if (sd.getTotalMonthly() == 0) {
                System.out.println("최소 하나 이상의 카테고리에 지출을 입력해야 합니다. 다시 입력합니다.");
                // 재귀 대신 반복 - 수정모드여도 처음부터 다시 입력
                isEdit = false;
                existing = null;
                continue;
            }

            System.out.printf("총 월 지출: %,d원%n", sd.getTotalMonthly());
            return sd;
        }
    }

    public static SpendingData inputFlow(Scanner sc) {
        return inputFlow(sc, null);
    }
}
