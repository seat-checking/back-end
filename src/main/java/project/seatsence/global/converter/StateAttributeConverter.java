package project.seatsence.global.converter;

import static project.seatsence.global.entity.BaseTimeAndStateEntity.State;

import javax.persistence.AttributeConverter;
import project.seatsence.global.entity.BaseTimeAndStateEntity;

public class StateAttributeConverter
        implements AttributeConverter<BaseTimeAndStateEntity.State, Integer> {

    @Override
    public Integer convertToDatabaseColumn(State state) {
        Integer databaseData = null;
        if (State.ACTICE.equals(state)) {
            databaseData = 1;
        } else if (State.INACTIVE.equals(state)) {
            databaseData = 0;
        }
        return databaseData;
    }

    @Override
    public State convertToEntityAttribute(Integer code) {
        State entityAttribute = null;
        if (1 == code) {
            entityAttribute = State.ACTICE;
        } else if (0 == code) {
            entityAttribute = State.INACTIVE;
        }
        return entityAttribute;
    }
}
