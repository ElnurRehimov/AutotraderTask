package az.ingress.autoTraderTask.service.impl;

import az.ingress.autoTraderTask.dto.CarReqDto;
import az.ingress.autoTraderTask.dto.CarResponseDto;
import az.ingress.autoTraderTask.entity.CarEntity;
import az.ingress.autoTraderTask.exception.CarNotFoundException;
import az.ingress.autoTraderTask.repository.CarRepository;
import az.ingress.autoTraderTask.service.CarService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepo;
    private final ModelMapper modelMapper;
    private final EntityManager em;


    public List<CarResponseDto> getAllCars() {
        List<CarEntity> carEntities = carRepo.findAll();
        Type listType = new TypeToken<List<CarResponseDto>>() {
        }.getType();

        return modelMapper.map(carEntities, listType);
    }

    public CarResponseDto getCar(Long id) {

        return modelMapper.map(carRepo.findById(id)
                        .orElseThrow(()->new CarNotFoundException("Exception.car.not-found","CarEntity not found")),
                CarResponseDto.class);
    }

    public CarResponseDto createCar(CarReqDto dto) {
        CarEntity save = carRepo.save(modelMapper.map(dto, CarEntity.class));
        return modelMapper.map(save, CarResponseDto.class);
    }

    public CarResponseDto update(Long id, CarReqDto dto) {
        carRepo.findById(id).orElseThrow(()->new CarNotFoundException("Exception.carEntity.not-found","CarEntity not found"));
        CarEntity carEntity = modelMapper.map(dto, CarEntity.class);
        carEntity.setId(id);
        CarEntity save = carRepo.save(carEntity);
        return modelMapper.map(save, CarResponseDto.class);
    }

    public void deleteCar(Long id) {
        carRepo.findById(id).
                orElseThrow(()->new CarNotFoundException("Exception.car.not-found","CarEntity not found"));

        carRepo.deleteById(id);

    }

    public List<CarResponseDto> searchByAllParam(Map<String,String> map) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CarEntity> cq = cb.createQuery(CarEntity.class);
        Root<CarEntity> carRoot = cq.from(CarEntity.class);
        List<Predicate> predicates = new ArrayList<>();

        //

        for(String key : map.keySet()) {
            if (map.get(key) != null) {
                predicates.add(cb.equal(carRoot.get(map.get(key)), map.get(key)));
            }
        }




//        if (map.get("brand") != null) {
//            predicates.add(cb.equal(carRoot.get("brand"), map.get("brand")));
//        }
//
//        if (map.get("model") != null) {
//            predicates.add(cb.equal(carRoot.get("model"), map.get("model")));
//        }
//        if (map.get("vinNumber") != null) {
//            predicates.add(cb.equal(carRoot.get("vinNumber"), map.get("vinNumber")));
//        }
//        if (map.get("buildDate") != null) {
//            predicates.add(cb.equal(carRoot.get("buildDate"), map.get("buildDate")));
//        }
//        if (map.get("color") != null) {
//            predicates.add(cb.equal(carRoot.get("color"), map.get("color")));
//        }
//        if (map.get("engineName") != null) {
//            predicates.add(cb.equal(carRoot.get("engineName"), map.get("engineName")));
//        }
//        if (map.get("enginePower") != null) {
//            predicates.add(cb.equal(carRoot.get("enginePower"), map.get("enginePower")));
//        }
//        if (map.get("kilometrage") != null) {
//            predicates.add(cb.equal(carRoot.get("kilometrage"), map.get("kilometrage")));
//        }
//        if (map.get("gearbox") != null) {
//            predicates.add(cb.equal(carRoot.get("gearbox"), map.get("gearbox")));
//        }
//        if (map.get("fuelType") != null) {
//            predicates.add(cb.equal(carRoot.get("fuelType"), map.get("fuelType")));
//        }



        cq.where(predicates.toArray(new Predicate[0]));
        TypedQuery<CarEntity> query = em.createQuery(cq);
        List<CarEntity> resultList = query.getResultList();
        Type listType = new TypeToken<List<CarResponseDto>>() {
        }.getType();


        return modelMapper.map(resultList, listType);


    }

}