package az.ingress.autoTraderTask.unit.controller;

import az.ingress.autoTraderTask.controlller.CarController;
import az.ingress.autoTraderTask.dto.CarReqDto;
import az.ingress.autoTraderTask.dto.CarResponseDto;
import az.ingress.autoTraderTask.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@WebMvcTest(CarController.class)

public class CarControllerTest {

    private static final String MAIN_URL= "/v1/cars";
    private static final String DUMMY_ID= "1";
    private static CarResponseDto carResponseDto;
    private  static List<CarResponseDto> carResponseDtoList=new ArrayList<>();
    private static CarReqDto carReqDto;

    private ObjectMapper objectMapper=new ObjectMapper();

    @BeforeAll
    static void setUp(){
        carResponseDto= CarResponseDto.builder().
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

        carResponseDtoList.add(carResponseDto);

        carReqDto= CarReqDto.builder()
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


    }

    @MockBean
    CarService carService;
    
    @Autowired
    MockMvc mockMvc;


    @Test
    public  void whenGetThenOk() throws Exception {

        when(carService.getCar(1L)).thenReturn(carResponseDto);

        
       mockMvc.perform(get(MAIN_URL + '/'+ DUMMY_ID)
               .contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andDo(print())
       .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(carResponseDto)));

    }


    @Test
    public  void whenGetAllThenOk() throws Exception {

        when(carService.getAllCars()).thenReturn(carResponseDtoList);


        mockMvc.perform(get(MAIN_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(carResponseDtoList)));

    }

    @Test
    public  void whenCreateThenGet() throws Exception {

        when(carService.createCar(carReqDto)).thenReturn(carResponseDto);


        mockMvc.perform(post(MAIN_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(carReqDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(carResponseDto)));
    }

    @Test
    public  void whenUpdateThenGet() throws Exception {

        when(carService.update(1L,carReqDto)).thenReturn(carResponseDto);


        mockMvc.perform(put(MAIN_URL + '/'+ DUMMY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(carReqDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(carResponseDto)));
    }

    @Test
    public  void whenDeleteThenOk() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete(MAIN_URL + '/' + DUMMY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());

    }


}
