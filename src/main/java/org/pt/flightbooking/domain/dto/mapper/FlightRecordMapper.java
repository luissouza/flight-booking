package org.pt.flightbooking.domain.dto.mapper;

import org.pt.flightbooking.domain.dto.response.FlightRecordDto;
import org.pt.flightbooking.data.model.FlightRecord;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.config.Configuration;
import java.util.List;
import java.util.Optional;

public interface FlightRecordMapper {
    static Optional<List<FlightRecordDto>> convertToListDto(final Iterable<FlightRecord> records) {

        final ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);

        modelMapper.createTypeMap(FlightRecord.class, FlightRecordDto.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getCurrency(), FlightRecordDto::setCurrency);
                    mapper.map(src -> src.getFlyTo(), FlightRecordDto::setFlyTo);
                    mapper.map(src -> src.getDateFrom(), FlightRecordDto::setDateFrom);
                    mapper.map(src -> src.getDateTo(), FlightRecordDto::setDateTo);
                });

        return Optional.ofNullable(modelMapper.map(records, new TypeToken<List<FlightRecordDto>>(){}.getType()));
    }

}
