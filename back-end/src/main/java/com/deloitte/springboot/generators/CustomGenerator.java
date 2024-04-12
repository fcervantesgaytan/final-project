package com.deloitte.springboot.generators;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.deloitte.springboot.entities.OrderHistory;
import com.deloitte.springboot.entities.Product;
import com.deloitte.springboot.entities.Role;
import com.deloitte.springboot.entities.User;
import com.deloitte.springboot.entities.Wishlist;

public class CustomGenerator implements IdentifierGenerator {
    private final int NUMERIC_SIZE_BEING_USED_IN_DB = 1_000_000_000;

    /**
     * Generates an ID based on specific logic for each class.
     * If more classes are expected to use this generator it it
     * recommended to generate the ID using the value of an uniquie
     * column. If that's not possible it can be done with the current
     * time. Refer to Wishlist ID generation for example.
     * 
     * @param object The object for which the id is being generated.
     * @return A BigDecimal with the generated id.
     */
    public BigDecimal generateIdBasedOnEntity(Object object) {
        String fieldToGenerateId = null;
        
        if (object instanceof User) {
            fieldToGenerateId = ((User) object).getEmail();
        } else if (object instanceof Product) {
            fieldToGenerateId = ((Product) object).getName();
        } else if (object instanceof OrderHistory) {
            fieldToGenerateId = ((OrderHistory) object).getOrderDate().toString();  
        } else if (object instanceof Role) {
            fieldToGenerateId = ((Role) object).getName();
        } else if (object instanceof Wishlist) {
            fieldToGenerateId = (new Timestamp(new Date().getTime())).toString();
        }

        return BigDecimal.valueOf(Objects.hash(fieldToGenerateId) % NUMERIC_SIZE_BEING_USED_IN_DB);
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        return generateIdBasedOnEntity(object);
    }
}
