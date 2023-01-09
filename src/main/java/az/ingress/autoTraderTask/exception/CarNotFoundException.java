package az.ingress.autoTraderTask.exception;

import lombok.Getter;

@Getter
public class CarNotFoundException extends RuntimeException{
    private String code;
    private String message;

    public CarNotFoundException(String code, String message){
        super(message);
        this.code=code;
        this.message=message;
    }

}
