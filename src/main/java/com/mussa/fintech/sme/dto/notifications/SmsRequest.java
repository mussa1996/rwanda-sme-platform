package com.mussa.fintech.sme.dto.notifications;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SmsRequest {


    @NotBlank(message = "Recipient phone number is required")
    @Pattern(
            regexp = "^\\+?2507\\d{8}$",
            message = "Recipient must be a valid Rwanda mobile number (e.g. 078xxxxxxx or +25072xxxxxxx or +25073xxxxxxx)"
    )
    private String recipient;

    @NotBlank(message = "Message content is required")
    @Size(
            max = 500,
            message = "Message content must not exceed 500 characters"
    )
    private String message;
}

