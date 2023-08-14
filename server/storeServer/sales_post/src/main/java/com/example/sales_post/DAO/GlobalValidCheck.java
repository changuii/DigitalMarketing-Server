package com.example.sales_post.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintViolation;
import javax.validation.*;
import java.util.Set;

@Component
public class GlobalValidCheck<Entity> {

    private final Validator validator;

    public GlobalValidCheck(@Autowired Validator validator) {
        this.validator = validator;
    }

    public String validCheck(Entity entity) // 제네릭 타입으로 모든 Entity타입을 받을 수 있도록 함
    {
        // 유효성 검사 이후 발생한 모든 violations(위반)내용을 violations변수에 저장함
        Set<ConstraintViolation<Entity>> violations = validator.validate(entity);

        if (violations.isEmpty()) {// 오류 내용이 없다면 성공
            return "success";
        } else {// 오류 내용이 있다면 errorMessage에 저장하고 출력
            StringBuilder errorMessage = new StringBuilder();
            for (ConstraintViolation<Entity> violation : violations) {
                errorMessage.append(violation.getPropertyPath()).append(": ").append(violation.getMessage());
            }
//            return new ValidationException(String.valueOf(errorMessage)).toString();
//            return new ValidationFailedException(String.valueOf(errorMessage)).toString();

            return errorMessage.toString();
        }
    }


}
