package org.pt.flightbooking.data.dto.mapper;

import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.config.Configuration;
import org.pt.flightbooking.data.model.FlightRecord;
import org.pt.flightbooking.domain.entities.FlightRecordEntity;

public interface FlightRecordEntityMapper {

  static Optional<FlightRecord> convertEntityToModel(final FlightRecordEntity entity) {

    final ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);

    modelMapper.createTypeMap(FlightRecordEntity.class, FlightRecord.class)
        .addMappings(mapper -> {
          mapper.map(src -> src.getCurrency(), FlightRecord::setCurrency);
          mapper.map(src -> src.getFlyTo(), FlightRecord::setFlyTo);
          mapper.map(src -> src.getDateFrom(), FlightRecord::setDateFrom);
          mapper.map(src -> src.getDateTo(), FlightRecord::setDateTo);
        });

    return Optional.of(modelMapper.map(entity, FlightRecord.class));
  }

  public static Iterable<FlightRecordEntity> convertModelToEntity(final Iterable<FlightRecord> recordEntities) {

    final ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration()
        .setFieldMatchingEnabled(true)
        .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);

    modelMapper.createTypeMap(FlightRecord.class, FlightRecordEntity.class)
        .addMappings(mapper -> {
          mapper.map(src -> src.getCurrency(), FlightRecordEntity::setCurrency);
          mapper.map(src -> src.getFlyTo(), FlightRecordEntity::setFlyTo);
          mapper.map(src -> src.getDateFrom(), FlightRecordEntity::setDateFrom);
          mapper.map(src -> src.getDateTo(), FlightRecordEntity::setDateTo);
        });

    return modelMapper.map(recordEntities, new TypeToken<Iterable<FlightRecordEntity>>(){}.getType());
  }

}
