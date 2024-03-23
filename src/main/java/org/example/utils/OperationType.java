package org.example.utils;
public enum OperationType {
    Normal_Purchase(1,"Normal Purchase"),
    Purchase_With_Installments(2,"Purchase with installments"),
    Withdrawal(3,"Withdrawal"),
    Credit_Voucher(4,"Credit Voucher");

    private final int code;
    private final String description;

    private OperationType(int code,String desc) {
        this.code = code;
        this.description=desc;
    }

    public static boolean checkValidOperationType(Short opnType){
        for(OperationType c : OperationType.values()){
             if (c.getCode()==opnType){
                 return true;
             }
        }
        return false;
    }

    public static OperationType getValidOperationTypeCode(Short opnType){
        for(OperationType c : OperationType.values()){
            if (c.getCode()==opnType){
                return c;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }
    public String getDescription(){
        return description;
    }

}
