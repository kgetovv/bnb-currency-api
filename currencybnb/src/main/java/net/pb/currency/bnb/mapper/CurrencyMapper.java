package net.pb.currency.bnb.mapper;

import net.pb.currency.bnb.domain.Currency;
import net.pb.currency.bnb.dto.CurrencyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CurrencyMapper {

    CurrencyDTO toDTO(Currency currency);

    Currency toEntity(CurrencyDTO currencyDTO);
}
