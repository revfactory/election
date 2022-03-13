package kr.revfactory.election.domain.report.enums;

import lombok.Getter;

@Getter
public enum ReportType {
    VOTE(0, ""),
    TOTAL(0, "합계"),
    SUB_TOTAL(1, "소계"),
    MAIL_SHIP(0, "거소·선상투표"),
    OVERSEA(0, "재외투표"),
    EMBASSY(0, "재외투표(공관)"),
    EARLY_OUTER(0, "관외사전투표"),
    EARLY_INTER(1, "관내사전투표"),
    WRONG(0, "잘못 투입·구분된 투표지")
    ;

    private final int index;
    private final String name;

    ReportType(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public boolean isMatch(String text) {
        if (name.isEmpty()) {
            return false;
        }
        return name.equals(text);
    }

    public boolean needVillage() {
        return (this == ReportType.SUB_TOTAL ||
                this == ReportType.EARLY_INTER ||
                this == ReportType.VOTE);
    }
}
