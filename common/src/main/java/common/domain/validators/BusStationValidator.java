package common.domain.validators;
import common.domain.exceptions.ValidatorException;
import org.javatuples.Pair;
import common.domain.BusStation;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class BusStationValidator implements Validator<BusStation>{
    @Override
    public void validate(BusStation entity) throws ValidatorException {
        Predicate<Long> idIsNull = (l)->(l == null || l < 0);
        boolean idNotValid = idIsNull.test(entity.getId());
        String idIsNotValidMessage = "Id must not be null and greater than 0! ";

        Predicate<String> nameIsNull = (s) -> (s == null || s.equals(""));
        boolean nameNotValid = nameIsNull.test(entity.getName());
        String nameNotValidMessage = "BusStation must have a name! ";

        List<Pair<Boolean, String>> checkList = Arrays.asList(new Pair<>(idNotValid, idIsNotValidMessage), new Pair<>(nameNotValid, nameNotValidMessage));

        checkList.stream()
                .filter(Pair::getValue0)
                .map(Pair::getValue1)
                .reduce(String::concat)
                .ifPresent(s -> {throw new ValidatorException(s);});

    }
}
