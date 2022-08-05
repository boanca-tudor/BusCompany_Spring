package common.domain.validators;

import common.domain.Bus;
import common.domain.exceptions.ValidatorException;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class BusValidator implements Validator<Bus> {
    @Override
    public void validate(Bus entity) throws ValidatorException {

        Predicate<Long> idIsNullOrNegative = (l) -> (l == null || l < 0);
        final Pair<Boolean, String> resultId =
                new Pair<>(idIsNullOrNegative.test(entity.getId()), "Id must not be null or negative!\n");

        Predicate<String> modelNameIsNull = (s) -> (s == null || s.equals(""));
        final Pair<Boolean, String> resultModelName =
                new Pair<>(modelNameIsNull.test(entity.getModelName()), "Bus must have a non-null and non-empty ModelName!\n");

        List<Pair<Boolean, String>> testList = new ArrayList<>();

        testList.add(resultId);
        testList.add(resultModelName);

        testList.stream()
                .filter((p) -> p.getValue0().equals(true))
                .map(Pair::getValue1)
                .reduce(String::concat)
                .ifPresent((msg) -> {throw new ValidatorException(msg);});
    }
}
