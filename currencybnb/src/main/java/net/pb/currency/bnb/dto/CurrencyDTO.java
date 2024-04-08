package net.pb.currency.bnb.dto;


import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement(name = "ROW")
@XmlAccessorType(XmlAccessType.FIELD)
public class CurrencyDTO {

    @XmlElement(name = "GOLD")
    private Integer goldEquivalent;

    @XmlElement(name = "CURR_DATE")
    private String currencyDate;

    @XmlElement(name = "NAME_")
    private String currencyName;

    @XmlElement(name = "CODE")
    private String currencyCode;

    @XmlElement(name = "RATIO")
    private Integer ratio;

    @XmlElement(name = "REVERSERATE")
    private Double reverseRate;

    @XmlElement(name = "RATE")
    private Double rate;

    @XmlElement(name = "S2_CURR_DATE")
    private String currencyDateS2;
}
