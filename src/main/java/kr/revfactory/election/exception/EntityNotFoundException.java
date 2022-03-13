package kr.revfactory.election.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(Class<?> clazz, String id) {
        super(String.format("Not Found Entity %s : %s", clazz.getName(), id));
    }

}
