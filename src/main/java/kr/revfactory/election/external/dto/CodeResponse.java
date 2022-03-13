package kr.revfactory.election.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CodeResponse<T> {

    @JsonProperty("jsonResult")
    private JsonResult<T> jsonResult;


    @Getter
    @NoArgsConstructor
    public static class JsonResult<T> {
        @JsonProperty("header")
        private JsonHeader jsonHeader;

        @JsonProperty("body")
        private List<T> body;
    }

    @Getter
    @NoArgsConstructor
    public static class JsonHeader {
        @JsonProperty("errorCode")
        private String errorCode;

        @JsonProperty("errorMessage")
        private String errorMessage;

        @JsonProperty("result")
        private String result;
    }
}

