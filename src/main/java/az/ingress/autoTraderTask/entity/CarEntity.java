package az.ingress.autoTraderTask.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;



import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@Builder
@Table(name ="cars")

public class CarEntity {
    public CarEntity(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "seller_id", referencedColumnName = "id")
    private Seller seller;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private  String model;

    @Column(nullable = false, unique = true)
    private  String vinNumber;



    @Column(nullable = false)
    private  String buildDate;

    @Column(nullable = false)
    private  String color ;

    @Column(nullable = false)
    private String engineName;

    @Column(nullable = false)
    private String enginePower;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private  Long kilometrage;

    @Column(nullable = false)
    private String gearbox;

    @Column(nullable = false)
    private String fuelType;


    @Column(nullable = false)
    private String additionInfo;





}
