package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.utils.OperationType;

@Data
public class RequestParam {
    @JsonProperty("document_number")
    private Integer documentNumber;
    @JsonProperty("account_id")
    private Integer accountId;
    @JsonProperty("operation_type_id")
    private Short operationType;
    @JsonProperty("amount")
    private  Float amount;
}
