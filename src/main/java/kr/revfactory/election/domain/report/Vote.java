package kr.revfactory.election.domain.report;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@ToString
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote {
    private int candidate1;
    private int candidate2;
    private int candidate3;
    private int candidate4;
    private int candidate5;
    private int candidate6;
    private int candidate7;
    private int candidate8;
    private int candidate9;
    private int candidate10;
    private int candidate11;
    private int candidate12;

    @Builder
    private Vote(int candidate1, int candidate2, int candidate3, int candidate4, int candidate5,
                 int candidate6, int candidate7, int candidate8, int candidate9, int candidate10,
                 int candidate11, int candidate12) {
        this.candidate1 = candidate1;
        this.candidate2 = candidate2;
        this.candidate3 = candidate3;
        this.candidate4 = candidate4;
        this.candidate5 = candidate5;
        this.candidate6 = candidate6;
        this.candidate7 = candidate7;
        this.candidate8 = candidate8;
        this.candidate9 = candidate9;
        this.candidate10 = candidate10;
        this.candidate11 = candidate11;
        this.candidate12 = candidate12;
    }
}
