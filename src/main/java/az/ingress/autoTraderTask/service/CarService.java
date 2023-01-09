package az.ingress.autoTraderTask.service;

import az.ingress.autoTraderTask.dto.CarReqDto;
import az.ingress.autoTraderTask.dto.CarResponseDto;

import java.util.List;
import java.util.Map;


public interface CarService {

     List<CarResponseDto> getAllCars();

     CarResponseDto getCar(Long id);

     CarResponseDto createCar(CarReqDto dto);

     CarResponseDto update(Long id, CarReqDto dto);

     void  deleteCar(Long id);

     List<CarResponseDto> searchByAllParam(Map<String,String> map);


}
