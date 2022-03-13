package kr.revfactory.election.external;

import kr.revfactory.election.domain.Region.City;
import kr.revfactory.election.external.dto.CityCode;
import kr.revfactory.election.external.dto.CodeResponse;
import kr.revfactory.election.external.dto.TownCode;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CodeCrawler {

    private final RestTemplate restTemplate;

    private static final String CITY_CODE_URL = "http://info.nec.go.kr/bizcommon/selectbox/selectbox_cityCodeBySgJson.json";
    private static final String TOWN_CODE_URL = "http://info.nec.go.kr/bizcommon/selectbox/selectbox_townCodeJson.json";
    private static final String ELECTION_ID = "0020220309";
    private static final String ELECTION_CODE = "1";

    public List<CityCode> getCities() {
        MultiValueMap<String, String> forms= new LinkedMultiValueMap<>();
        forms.add("electionId", ELECTION_ID);
        forms.add("electionCode", ELECTION_CODE);

        ResponseEntity<CodeResponse<CityCode>> responseEntity = restTemplate.exchange(
                CITY_CODE_URL,
                HttpMethod.POST,
                new HttpEntity<>(forms, getHeader()),
                new ParameterizedTypeReference<>() {}
        );

        return Optional.of(responseEntity)
                .filter(response -> response.getStatusCode() == HttpStatus.OK)
                .map(ResponseEntity::getBody)
                .map(CodeResponse::getJsonResult)
                .map(CodeResponse.JsonResult::getBody)
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public List<TownCode> getTowns(City city) {
        MultiValueMap<String, String> forms = new LinkedMultiValueMap<>();
        forms.add("electionId", ELECTION_ID);
        forms.add("cityCode", city.getCode());

        ResponseEntity<CodeResponse<TownCode>> responseEntity = restTemplate.exchange(
                TOWN_CODE_URL,
                HttpMethod.POST,
                new HttpEntity<>(forms, getHeader()),
                new ParameterizedTypeReference<>() {}
        );

        return Optional.of(responseEntity)
                .filter(response -> response.getStatusCode() == HttpStatus.OK)
                .map(ResponseEntity::getBody)
                .map(CodeResponse::getJsonResult)
                .map(CodeResponse.JsonResult::getBody)
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private HttpHeaders getHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        headers.add("Host", "info.nec.go.kr");
        headers.add("Origin", "http://info.nec.go.kr");
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("Referer", "http://info.nec.go.kr/electioninfo/electionInfo_report.xhtml");
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36");
        return headers;
    }
}

