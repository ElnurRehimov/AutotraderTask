package az.ingress.autoTraderTask.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarReqDto {



    private  String brand;
    private  String model;
    private  String vinNumber;
    private  String buildDate;
    private  String color ;
    private  String engineName;
    private  String enginePower;
    private  Long price;
    private  Long kilometrage;
    private  String gearbox;
    private  String fuelType;
    private  String additionInfo;
}
