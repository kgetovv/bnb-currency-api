package net.pb.currency.bnb.dto;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "ROWSET")
@XmlAccessorType(XmlAccessType.FIELD)
public class CurrencyWrapperDTO {

    @XmlElement(name = "ROW")
    private List<CurrencyDTO> currencyDTOList;
}
