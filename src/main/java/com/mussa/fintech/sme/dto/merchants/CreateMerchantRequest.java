package com.mussa.fintech.sme.dto.merchants;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateMerchantRequest {

    @NotBlank(message = "Business name is required")
    @Size(max = 150, message = "Business name must not exceed 150 characters")
    private String businessName;
    @Size(max = 100, message = "Business category must not exceed 100 characters")
    private String businessCategory;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^\\+?2507\\d{8}$",
            message = "Phone number must be a valid Rwanda mobile number (e.g. 078xxxxxxx or +25072xxxxxxx or or +25073xxxxxxx)"
    )
    private String phoneNumber;
    @Email(message = "Email must be a valid email address")
    @Size(max = 150, message = "Email must not exceed 150 characters")
    private String email;
    @Size(max = 255, message = "Address text must not exceed 255 characters")
    private String addressText;
    @Size(max = 100, message = "District name must not exceed 100 characters")
    private String district;
    @Size(max = 100, message = "Sector name must not exceed 100 characters")
    private String sector;
    @Size(max = 100, message = "Cell name must not exceed 100 characters")
    private String cell;
    @Size(max = 100, message = "Village name must not exceed 100 characters")
    private String village;
}

