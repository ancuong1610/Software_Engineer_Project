package de.hda.fbi.efussgaengerzone.domain.usecase;

import de.hda.fbi.efussgaengerzone.domain.model.owner.Owner;
import de.hda.fbi.efussgaengerzone.domain.model.owner.OwnerRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OwnerRegistration {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(OwnerRegistration.class);
    private final OwnerRepository ownerRepository;

    public OwnerRegistration(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public Owner findByEmail(String email) {
        return ownerRepository.find(email);
    }

    public Owner register(Owner owner) throws InsecurePasswordException {
        return null;
    }

    public boolean login(String username, String password) {
        log.debug("Login user {}", username);
        Set<Owner> owners = ownerRepository.findPredicate(
                owner -> owner.email().equals(username) && owner.password().equals(password));
        return !owners.isEmpty();
    }

    @SuppressWarnings("squid:S2629")
    public void updateOwner(String oldEmail, String newEmail, String newName) {

    }
}
