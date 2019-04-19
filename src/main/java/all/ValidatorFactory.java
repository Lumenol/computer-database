package all;

import com.excilys.cdb.validator.Validator;

public interface ValidatorFactory<T> {
    Validator<T> get(T dto);
}
