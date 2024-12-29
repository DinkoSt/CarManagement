package fmi.plovdiv.carmanagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.YearMonth;

@Getter
@Setter
public class MonthlyRequestsReportDto {
    private YearMonth yearMonth;
    private Integer requests;
}