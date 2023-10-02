package io.angularpay.userconfig.adapters.outbound;

import io.angularpay.userconfig.domain.PeerLookup;
import io.angularpay.userconfig.domain.UserConfiguration;
import io.angularpay.userconfig.ports.outbound.PersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MongoAdapter implements PersistencePort {

    private final UserConfigRepository userConfigRepository;
    private final PeerLookupRepository peerLookupRepository;

    @Override
    public UserConfiguration createRequest(UserConfiguration request) {
        request.setCreatedOn(Instant.now().truncatedTo(ChronoUnit.SECONDS).toString());
        request.setLastModified(Instant.now().truncatedTo(ChronoUnit.SECONDS).toString());
        return userConfigRepository.save(request);
    }

    @Override
    public UserConfiguration updateRequest(UserConfiguration request) {
        request.setLastModified(Instant.now().truncatedTo(ChronoUnit.SECONDS).toString());
        return userConfigRepository.save(request);
    }

    @Override
    public void deleteRequest(UserConfiguration request) {
        userConfigRepository.delete(request);
    }

    @Override
    public Optional<UserConfiguration> findRequestByReference(String reference) {
        return userConfigRepository.findByReference(reference);
    }

    @Override
    public Optional<UserConfiguration> findByEmail(String email) {
        return userConfigRepository.findByUserProfile_Email(email);
    }

    @Override
    public Optional<UserConfiguration> findByPhone(String phone) {
        return userConfigRepository.findByUserProfile_Phone(phone);
    }

    @Override
    public Optional<UserConfiguration> findRequestByOnboardingReference(String onboardingReference) {
        return userConfigRepository.findByOnboardingReference(onboardingReference);
    }

    @Override
    public Page<UserConfiguration> listRequests(Pageable pageable) {
        return userConfigRepository.findAll(pageable);
    }

    @Override
    public PeerLookup createPeerLookup(PeerLookup peerLookup) {
        peerLookup.setCreatedOn(Instant.now().truncatedTo(ChronoUnit.SECONDS).toString());
        peerLookup.setLastModified(Instant.now().truncatedTo(ChronoUnit.SECONDS).toString());
        return peerLookupRepository.save(peerLookup);
    }

    @Override
    public Optional<PeerLookup> findPeerLookupByReference(String reference) {
        return peerLookupRepository.findByReference(reference);
    }

    @Override
    public Optional<PeerLookup> findPeerLookupByUserReference(String userReference) {
        return peerLookupRepository.findByUserReference(userReference);
    }

    @Override
    public void deletePeerLookup(PeerLookup peerLookup) {
        peerLookupRepository.delete(peerLookup);
    }
}
 