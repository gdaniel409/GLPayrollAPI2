package com.gdaniel.glpayroll.userarea.employee.dto;

import java.time.LocalDateTime;
import com.gdaniel.glpayroll.adminarea.employeestatus.dto.EmployeeStatusDto;
import com.gdaniel.glpayroll.adminarea.ratetype.dto.RateTypeDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDto {

        private String title;

        // @JsonProperty("employeeId")
        private long id;

        private long companyId;
        private String employeeNumber;

        private String firstName;
        private String lastName;
        private String middleName;
        private LocalDateTime dateHired;
        private LocalDateTime dateTerminated;
        private String email;
        private String ssn;
        private String telephoneLandline;
        private String telephoneCell;
        private double rate;
        private RateTypeDto rateType;
        private EmployeeStatusDto employeeStatus;
        private int documentCount;
        private long userId;
}
