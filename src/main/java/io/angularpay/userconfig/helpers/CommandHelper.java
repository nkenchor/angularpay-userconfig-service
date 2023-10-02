package io.angularpay.userconfig.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.angularpay.userconfig.adapters.outbound.MongoAdapter;
import io.angularpay.userconfig.configurations.AngularPayConfiguration;
import io.angularpay.userconfig.domain.PeerLookup;
import io.angularpay.userconfig.domain.ServiceConfiguration;
import io.angularpay.userconfig.domain.UserConfiguration;
import io.angularpay.userconfig.exceptions.CommandException;
import io.angularpay.userconfig.exceptions.ErrorCode;
import io.angularpay.userconfig.models.TreeReferenceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static io.angularpay.userconfig.exceptions.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CommandHelper {

    private final MongoAdapter mongoAdapter;
    private final ObjectMapper mapper;
    private final AngularPayConfiguration configuration;

    public TreeReferenceResponse executeAcid(Supplier<TreeReferenceResponse> supplier) {
        int maxRetry = this.configuration.getMaxUpdateRetry();
        OptimisticLockingFailureException optimisticLockingFailureException;
        int counter = 0;
        //noinspection ConstantConditions
        do {
            try {
                return supplier.get();
            } catch (OptimisticLockingFailureException exception) {
                if (counter++ >= maxRetry) throw exception;
                optimisticLockingFailureException = exception;
            }
        }
        while (Objects.nonNull(optimisticLockingFailureException));
        throw optimisticLockingFailureException;
    }

    public String getRequestOwner(String requestReference) {
        UserConfiguration found = this.mongoAdapter.findRequestByReference(requestReference).orElseThrow(
                () -> commandException(HttpStatus.NOT_FOUND, REQUEST_NOT_FOUND)
        );
        return found.getReference();
    }

    public <T> TreeReferenceResponse updateProperty(UserConfiguration userConfiguration, Supplier<T> getter, Consumer<T> setter) {
        setter.accept(getter.get());
        UserConfiguration response = this.mongoAdapter.updateRequest(userConfiguration);
        return TreeReferenceResponse.builder().userReference(response.getReference()).build();
    }

    public <T> void addItemToCollection(UserConfiguration userConfiguration, T newProperty, Supplier<List<T>> collectionGetter, Consumer<List<T>> collectionSetter) {
        if (CollectionUtils.isEmpty(collectionGetter.get())) {
            collectionSetter.accept(new ArrayList<>());
        }
        collectionGetter.get().add(newProperty);
        this.mongoAdapter.updateRequest(userConfiguration);
    }

    public <T> String toJsonString(T t) throws JsonProcessingException {
        return this.mapper.writeValueAsString(t);
    }

    public static UserConfiguration getRequestByReferenceOrThrow(MongoAdapter mongoAdapter, String requestReference) {
        return mongoAdapter.findRequestByReference(requestReference).orElseThrow(
                () -> commandException(HttpStatus.NOT_FOUND, REQUEST_NOT_FOUND)
        );
    }

    public static UserConfiguration getRequestByEmailOrThrow(MongoAdapter mongoAdapter, String email) {
        return mongoAdapter.findByEmail(email).orElseThrow(
                () -> commandException(HttpStatus.NOT_FOUND, REQUEST_NOT_FOUND)
        );
    }

    public static UserConfiguration getRequestByPhoneOrThrow(MongoAdapter mongoAdapter, String phone) {
        return mongoAdapter.findByPhone(phone).orElseThrow(
                () -> commandException(HttpStatus.NOT_FOUND, REQUEST_NOT_FOUND)
        );
    }

    public static PeerLookup getPeerLookupByReferenceOrThrow(MongoAdapter mongoAdapter, String reference) {
        return mongoAdapter.findPeerLookupByReference(reference).orElseThrow(
                () -> commandException(HttpStatus.NOT_FOUND, REQUEST_NOT_FOUND)
        );
    }

    private static CommandException commandException(HttpStatus status, ErrorCode errorCode) {
        return CommandException.builder()
                .status(status)
                .errorCode(errorCode)
                .message(errorCode.getDefaultMessage())
                .build();
    }

    public static void validateNotExistOrThrow(MongoAdapter mongoAdapter, String onboardingReference) {
        mongoAdapter.findRequestByOnboardingReference(onboardingReference).ifPresent(
                (x) -> {
                    throw commandException(HttpStatus.CONFLICT, DUPLICATE_REQUEST_ERROR);
                }
        );
    }

    public static void validRequestStatusOrThrow(UserConfiguration found) {
        if (!found.isEnabled()) {
            throw commandException(HttpStatus.UNPROCESSABLE_ENTITY, USER_DISABLED_ERROR);
        }
    }

    public static void validateUniqueServiceConfigurationOrThrow(String serviceReference, UserConfiguration found) {
        if (found.getServiceConfiguration().getSubscriptions().stream().anyMatch(x -> serviceReference.equalsIgnoreCase(x.getServiceReference()))) {
            throw commandException(HttpStatus.UNPROCESSABLE_ENTITY, DUPLICATE_ERROR);
        }
    }

    public static void validateUniqueNewsfeedPreferenceOrThrow(String serviceReference, UserConfiguration found) {
        if (found.getNewsfeedPreferences().stream().anyMatch(x -> serviceReference.equalsIgnoreCase(x.getServiceReference()))) {
            throw commandException(HttpStatus.UNPROCESSABLE_ENTITY, DUPLICATE_ERROR);
        }
    }

    public static void initServiceConfigurationIfNecessary(UserConfiguration found) {
        if (Objects.isNull(found.getServiceConfiguration())) {
            found.setServiceConfiguration(ServiceConfiguration.builder().subscriptions(new ArrayList<>()).build());
        }
        if (CollectionUtils.isEmpty(found.getServiceConfiguration().getSubscriptions())) {
            found.getServiceConfiguration().setSubscriptions(new ArrayList<>());
        }
    }
}
