package com.delivery.trizi.trizi.infra.mongo;

import com.delivery.trizi.trizi.utils.Listeners;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@AllArgsConstructor
public class MongoInfra {
    public ValidatingMongoEventListener validatingMongoEventListener(LocalValidatorFactoryBean validator) {
        return new ValidatingMongoEventListener(validator);
    }

    public Listeners orderModelListener(MongoOperations mongoOperations) {
        return new Listeners(mongoOperations);
    }
}
