package com.example.xpresspay.dto.request;

import com.example.xpresspay.dto.request.Airtime;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class VTURequest {
    private String requestId;
    private String uniqueCode;
    private Airtime details;
}
