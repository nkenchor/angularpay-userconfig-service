package io.angularpay.userconfig.domain.commands;

public interface ResourceReferenceCommand<T, R> {

    R map(T referenceResponse);
}
