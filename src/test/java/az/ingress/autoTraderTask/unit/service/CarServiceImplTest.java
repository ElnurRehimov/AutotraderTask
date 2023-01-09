package az.ingress.autoTraderTask.unit.service;

import az.ingress.autoTraderTask.dto.CarReqDto;
import az.ingress.autoTraderTask.dto.CarResponseDto;
import az.ingress.autoTraderTask.entity.CarEntity;
import az.ingress.autoTraderTask.exception.CarNotFoundException;
import az.ingress.autoTraderTask.repository.CarRepository;
import az.ingress.autoTraderTask.service.impl.CarServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class CarServiceImplTest {

    @Mock
    CarRepository carRepository;

    @InjectMocks
    CarServiceImpl carService;

    @Spy
    ModelMapper modelMapper;

   private static CarResponseDto carResponse;
   private static CarEntity carEntity;
   private static CarReqDto carReqDto;

    @BeforeAll
     static void setUp(){
        carResponse= CarResponseDto.builder().
                id( 1L)
                .brand("KIA")
                .model("Optima")
                .color("Red")
                .additionInfo("dsc")
                .buildDate("02/01/03")
                .engineName("v8")
                .enginePower("1.8")
                .fuelType("benzin")
                .gearbox("mexaniki")
                .vinNumber("12312")
                .price((long)2400000)
                .kilometrage((long)15000)
                .build();


         carEntity = CarEntity.builder().
                id(1L)
                .brand("KIA")
                .model("Optima")
                .color("Red")
                .additionInfo("dsc")
                .buildDate("02/01/03")
                .engineName("v8")
                .enginePower("1.8")
                .fuelType("benzin")
                .gearbox("mexaniki")
                .vinNumber("12312")
                .price((long) 2400000)
                .kilometrage((long) 15000)
                .build();

        carReqDto = CarReqDto.builder()
                .brand("KIA")
                .model("Optima")
                .color("Red")
                .additionInfo("dsc")
                .buildDate("02/01/03")
                .engineName("v8")
                .enginePower("1.8")
                .fuelType("benzin")
                .gearbox("mexaniki")
                .vinNumber("12312")
                .price((long) 2400000)
                .kilometrage((long) 15000)
                .build();


    }

    @Test
    public void shouldReturnAllCars(){
        List<CarResponseDto> carResponseDtoList=new ArrayList<>();
        carResponseDtoList.add(carResponse);
        CarEntity carEntity= modelMapper.map(carResponse, CarEntity.class);
        List<CarEntity> listType=new ArrayList<>();
        listType.add(carEntity);
        given(carRepository.findAll()).willReturn(listType);

        assertEquals(carResponseDtoList,carService.getAllCars());

        verify(carRepository,times(1)).findAll();

    }

    @Test
    public void whenGivenId_shouldReturnCreate_ifFound(){

        when(carRepository.findById(carEntity.getId())).thenReturn(Optional.of(carEntity));
        CarResponseDto car = carService.getCar(carEntity.getId());

        assertThat(car).isEqualTo(carResponse);
        verify(carRepository,atLeast(1)).findById(carEntity.getId());
        verify(modelMapper,atLeast(1)).map(carEntity,CarResponseDto.class);

    }

    @Test
    public void whenDerivedExceptionThrown_thenAssertionSucceds() {
        Exception exception = assertThrows(CarNotFoundException.class, () -> {
            when(carRepository.findById(anyLong())).thenReturn(Optional.empty());
            carService.getCar(carEntity.getId());
        });

        String expectedMessage = "CarEntity not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(carRepository,atLeast(1)).findById(anyLong());

    }

    @Test
    public void whenGivenId_shouldCreateCar_ifFound(){

        when(carRepository.save(modelMapper.map(carReqDto,CarEntity.class))).thenReturn(carEntity);

       assertThat(carService.createCar(carReqDto)).isEqualTo(carResponse);

       verify(carRepository,atLeast(1)).save(modelMapper.map(carReqDto,CarEntity.class));


    }
    @Test
    public void whenGivenId_shouldUpdateCar_ifFound(){

        when(carRepository.findById(carEntity.getId())).thenReturn(Optional.of(carEntity));
        when(carRepository.save(carEntity)).thenReturn(carEntity);

        assertThat(carService.update(carEntity.getId(),carReqDto)).isEqualTo(carResponse);

        verify(carRepository,atLeast(1)).findById(carEntity.getId());
        verify(carRepository,atLeast(1)).save(carEntity);

    }
    @Test
    public void should_throw_exception_when_car_doesnt_exist(){

        when(carRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        assertThatThrownBy(()->carService.update(1L,carReqDto))
                .isInstanceOf(CarNotFoundException.class)
                .hasMessage("CarEntity not found");
        verify(carRepository,atLeast(1)).findById(anyLong());

    }

    @Test
    public void whenGivenId_shouldDeleteCar_ifFound(){

        when(carRepository.findById(carEntity.getId())).thenReturn(Optional.of(carEntity));

        carService.deleteCar(carEntity.getId());

        verify(carRepository,atLeast(1)).findById(carEntity.getId());
        verify(carRepository,atLeast(1)).deleteById(carEntity.getId());

    }
    @Test
    public void whenInvalidDeleteId_should_throw_exception(){
        when(carRepository.findById(carEntity.getId())).thenReturn(Optional.ofNullable(null));
        assertThatThrownBy(()->carService.deleteCar(carEntity.getId()))
                .isInstanceOf(CarNotFoundException.class)
                .hasMessage("CarEntity not found");

    }






}
