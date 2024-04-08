package net.pb.currency.bnb.domain;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@DynamicUpdate
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="curr_id", nullable = false)
    private Long id;

    @Column(name = "curr_gold")
    private Integer goldEquivalent;

    @Column(name = "curr_date")
    private String currencyDate;

    @Column(name = "curr_name")
    private String currencyName;

    @Column(name = "curr_code")
    private String currencyCode;

    @Column(name = "curr_ratio")
    private Integer ratio;

    @Column(name = "curr_reverse_rate")
    private Double reverseRate;

    @Column(name = "curr_rate")
    private Double rate;

    @Column(name = "curr_date_s2")
    private String currencyDateS2;

}
