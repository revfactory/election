package kr.revfactory.election.external;

import kr.revfactory.election.domain.Region.Town;
import kr.revfactory.election.domain.report.Report;
import kr.revfactory.election.domain.report.Statistics;
import kr.revfactory.election.domain.report.Vote;
import kr.revfactory.election.domain.report.enums.ReportType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kr.revfactory.election.utilty.NumberUtils.toInt;

@Service
public class ReportCrawler {

    private static final String REPORT_URL = "http://info.nec.go.kr/electioninfo/electionInfo_report.xhtml";
    private static final Map<String, String> data;
    static {
        data = new HashMap<>();
        data.put("electionId", "0020220309");
        data.put("requestURI", "/WEB-INF/jsp/electioninfo/0020220309/vc/vccp08.jsp");
        data.put("topMenuId", "VC");
        data.put("secondMenuId", "VCCP08");
        data.put("menuId", "VCCP08");
        data.put("statementId", "VCCP08_#1");
        data.put("electionCode", "1");
        data.put("sggCityCode", "-1");
        data.put("townCodeFromSgg", "-1");
        data.put("sggTownCode", "-1");
        data.put("checkCityCode", "-1");
        data.put("x", "64");
        data.put("y", "24");

        data.put("cityCode", "-1");
        data.put("townCode", "-1");
    }

    public List<Report> getReports(Town town) {
        List<Report> reports = new ArrayList<>();
        try {
            data.put("cityCode", town.getCity().getCode());
            data.put("townCode", town.getCode());

            Document doc = Jsoup.connect(REPORT_URL)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                    .header("Host", "info.nec.go.kr")
                    .header("Origin", "http://info.nec.go.kr")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Referer", "http://info.nec.go.kr/electioninfo/electionInfo_report.xhtml")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36")
                    .data(data)
                    .post();

            Element table = doc.getElementById("table01");
            if (table == null) {
                return reports;
            }

            Element tbody = table.getElementsByTag("tbody").get(0);
            String currentVillageName = null;

            for (Element tableRow : tbody.children()) {
                Statistics statistics = parseStatistics(tableRow);
                Vote vote = parseVote(tableRow);
                ReportType reportType = parseType(tableRow);

                if (reportType == ReportType.SUB_TOTAL) {
                    currentVillageName = parseVillage(tableRow);
                }

                String sectionName = null;
                if (reportType == ReportType.VOTE){
                    sectionName = parseSection(tableRow);
                }

                Report report = Report.of(reportType, town, currentVillageName, sectionName, statistics, vote);
                reports.add(report);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return reports;
    }

    private String parseVillage(Element tableRow) {
        return tableRow.child(0).html();
    }

    private String parseSection(Element tableRow) {
        return tableRow.child(1).html();
    }

    private ReportType parseType(Element tableRow) {
        for(ReportType reportType: ReportType.values()) {
            String targetText = tableRow.child(reportType.getIndex()).html();
            if (reportType.isMatch(targetText)) {
                return reportType;
            }
        }
        return ReportType.VOTE;
    }

    private Statistics parseStatistics(Element tableRow) {
        return Statistics.builder()
                .electorCount(toInt(tableRow.child(2).html()))
                .voteCount(toInt(tableRow.child(3).html()))
                .totalCount(toInt(tableRow.child(16).html()))
                .invalidityCount(toInt(tableRow.child(17).html()))
                .abstentionCount(toInt(tableRow.child(18).html()))
                .build();
    }

    private Vote parseVote(Element tableRow) {
        return Vote.builder()
                .candidate1(toInt(tableRow.child(4).html()))
                .candidate2(toInt(tableRow.child(5).html()))
                .candidate3(toInt(tableRow.child(6).html()))
                .candidate4(toInt(tableRow.child(7).html()))
                .candidate5(toInt(tableRow.child(8).html()))
                .candidate6(toInt(tableRow.child(9).html()))
                .candidate7(toInt(tableRow.child(10).html()))
                .candidate8(toInt(tableRow.child(11).html()))
                .candidate9(toInt(tableRow.child(12).html()))
                .candidate10(toInt(tableRow.child(13).html()))
                .candidate11(toInt(tableRow.child(14).html()))
                .candidate12(toInt(tableRow.child(15).html()))
                .build();
    }



}
