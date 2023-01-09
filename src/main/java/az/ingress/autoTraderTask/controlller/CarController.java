package az.ingress.autoTraderTask.controlller;

import az.ingress.autoTraderTask.dto.CarReqDto;
import az.ingress.autoTraderTask.dto.CarResponseDto;
import az.ingress.autoTraderTask.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/cars")

public class CarController {


    private final CarService carService;


    @GetMapping()
    public ResponseEntity<List<CarResponseDto>> getAll(){
        return  ResponseEntity.status(HttpStatus.OK).body(carService.getAllCars());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponseDto> get(@PathVariable Long id){
        return  ResponseEntity.status(HttpStatus.OK).body(carService.getCar(id));
    }

    @PostMapping
    public ResponseEntity<CarResponseDto>  create (@RequestBody  CarReqDto dto){
        CarResponseDto car=carService.createCar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(car);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarResponseDto>  update(@PathVariable Long id, @RequestBody CarReqDto dto){
        CarResponseDto carResDto = carService.update(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(carResDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        carService.deleteCar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @GetMapping(value = {"/search/", "/search"})
    public ResponseEntity<List<CarResponseDto>> search(@RequestParam Map<String,String> allRequestParams){
        List<CarResponseDto> dtoList = carService.searchByAllParam(allRequestParams);
        return ResponseEntity.status(HttpStatus.OK).body(dtoList);

    }
}
