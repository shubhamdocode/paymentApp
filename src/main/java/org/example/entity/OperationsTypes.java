package org.example.entity;

import lombok.Data;
import org.example.utils.OperationType;
import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
public class OperationsTypes implements Serializable {

    private static final long serialVersionUID = 0x62A6DA99AABDA8A8L;

    @Column(name="OperationType_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer operationTypeId;
    @Column (name="Description")
    @Enumerated(EnumType.STRING)
    private OperationType description;


    public Integer getOperationTypeIdId() {
        return operationTypeId;
    }

    public OperationType getDescription() {
        return description;
    }

    public OperationsTypes() {
    }

    public OperationsTypes(OperationType description) {
       this.description=description;
    }
}
