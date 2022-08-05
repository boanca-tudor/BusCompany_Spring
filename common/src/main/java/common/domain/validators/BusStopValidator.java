package common.domain.validators;

import common.domain.BusStop;
import common.domain.exceptions.ValidatorException;
import org.javatuples.Pair;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class BusStopValidator implements Validator<BusStop>{
    @Override
    public void validate(BusStop entity) throws ValidatorException {
//        Predicate<Long> idIsNullOrNegative = (l) -> (l == null || l < 0);
//        final Pair<Boolean, String> resultId =
//                new Pair<>(idIsNullOrNegative.test(entity.getId()), "Components of Id must not be null or negative!\n");

        Predicate<String> modelNameIsNull = (s) -> (s == null || s.equals("")); // why is this a thing
        final Pair<Boolean, String> resultStopTime =
                new Pair<>(modelNameIsNull.test(entity.getStopTime()), "Bus Stop must have a non-null and non empty stop time!\n");

        List<Pair<Boolean, String>> testList = new ArrayList<>();

//        testList.add(resultId);
        testList.add(resultStopTime);

        testList.stream()
                .filter((p) -> p.getValue0().equals(true))
                .map(Pair::getValue1)
                .reduce(String::concat)
                .ifPresent((msg) -> {throw new ValidatorException(msg);});

    }
}
