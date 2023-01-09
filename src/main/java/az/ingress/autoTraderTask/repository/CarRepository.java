package az.ingress.autoTraderTask.repository;

import az.ingress.autoTraderTask.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface CarRepository extends JpaRepository<CarEntity,Long> , JpaSpecificationExecutor {


}
