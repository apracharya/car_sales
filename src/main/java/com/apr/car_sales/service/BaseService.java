package com.apr.car_sales.service;

import com.apr.car_sales.exception.ResourceNotFoundException;

import java.util.Optional;
import java.util.function.Function;

public class BaseService {
    protected <T> T findEntityByIdOrThrow(String resourceName, long id,
                                          Function<Long, Optional<T>> findFunction
    ) throws ResourceNotFoundException {
        return findFunction.apply(id).orElseThrow(() -> new ResourceNotFoundException(resourceName, "id", id));
    }
}
