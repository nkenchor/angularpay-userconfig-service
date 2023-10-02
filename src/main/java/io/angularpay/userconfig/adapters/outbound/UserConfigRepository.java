package io.angularpay.userconfig.adapters.outbound;

import io.angularpay.userconfig.domain.UserConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserConfigRepository extends MongoRepository<UserConfiguration, String> {

    Optional<UserConfiguration> findByReference(String reference);
    Optional<UserConfiguration> findByUserProfile_Email(String email);
    Optional<UserConfiguration> findByUserProfile_Phone(String phone);
    Optional<UserConfiguration> findByOnboardingReference(String onboardingReference);
    Page<UserConfiguration> findAll(Pageable pageable);
}
