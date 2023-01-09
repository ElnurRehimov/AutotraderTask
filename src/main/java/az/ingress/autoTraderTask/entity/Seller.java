package az.ingress.autoTraderTask.entity;

import lombok.Data;
import org.apache.tomcat.jni.Address;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "sellers")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

  @Column(nullable = false)
    private String name;

    private String city;

    @Column(nullable = false)
    private Long phoneNumber;

   @OneToMany(mappedBy = "seller")
    private List<CarEntity> carEntities;


}
