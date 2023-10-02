package io.angularpay.userconfig.ports.outbound;

import io.angularpay.userconfig.domain.PeerLookup;
import io.angularpay.userconfig.domain.UserConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PersistencePort {
    UserConfiguration createRequest(UserConfiguration request);
    UserConfiguration updateRequest(UserConfiguration request);
    void deleteRequest(UserConfiguration request);
    Optional<UserConfiguration> findRequestByReference(String reference);
    Optional<UserConfiguration> findByEmail(String email);
    Optional<UserConfiguration> findByPhone(String phone);
    Optional<UserConfiguration> findRequestByOnboardingReference(String onboardingReference);
    Page<UserConfiguration> listRequests(Pageable pageable);

    PeerLookup createPeerLookup(PeerLookup peerLookup);
    Optional<PeerLookup> findPeerLookupByReference(String reference);
    Optional<PeerLookup> findPeerLookupByUserReference(String userReference);
    void deletePeerLookup(PeerLookup peerLookup);
}
