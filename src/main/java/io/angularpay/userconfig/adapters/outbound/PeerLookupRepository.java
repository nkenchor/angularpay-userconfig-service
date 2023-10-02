package io.angularpay.userconfig.adapters.outbound;

import io.angularpay.userconfig.domain.PeerLookup;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PeerLookupRepository extends MongoRepository<PeerLookup, String> {

    Optional<PeerLookup> findByReference(String reference);
    Optional<PeerLookup> findByUserReference(String reference);
}
