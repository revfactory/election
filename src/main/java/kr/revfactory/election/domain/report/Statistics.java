package kr.revfactory.election.domain.report;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@ToString
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Statistics {
    private int electorCount;       // 선거인수
    private int voteCount;          // 투표수
    private int totalCount;         // 계
    private int invalidityCount;    // 무효투표수
    private int abstentionCount;    // 기권수

    @Builder
    private Statistics(int electorCount, int voteCount, int totalCount, int invalidityCount, int abstentionCount) {
        this.electorCount = electorCount;
        this.voteCount = voteCount;
        this.totalCount = totalCount;
        this.invalidityCount = invalidityCount;
        this.abstentionCount = abstentionCount;
    }
}
