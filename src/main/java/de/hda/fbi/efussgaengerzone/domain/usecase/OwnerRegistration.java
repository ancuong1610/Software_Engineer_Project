package de.hda.fbi.efussgaengerzone.domain.usecase;

import de.hda.fbi.efussgaengerzone.domain.model.owner.Owner;
import de.hda.fbi.efussgaengerzone.domain.model.owner.OwnerRepository;
import de.hda.fbi.efussgaengerzone.domain.usecase.abstractions.SecurePasswordValidator;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OwnerRegistration {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(OwnerRegistration.class);
    private final OwnerRepository ownerRepository;
    private final ShopOrganization shopOrganization;
    private final ShopBrowsing shopBrowsing;
    private final SecurePasswordValidator passwordValidator;

    public OwnerRegistration(OwnerRepository ownerRepository, ShopOrganization shopOrganization, ShopBrowsing shopBrowsing, SecurePasswordValidator passwordValidator) {
        this.ownerRepository = ownerRepository;
        this.shopOrganization = shopOrganization;
        this.shopBrowsing = shopBrowsing;
        this.passwordValidator = passwordValidator;
    }

    public Owner findByEmail(String email) {
        return ownerRepository.find(email);
    }

    // zu implementieren in Praktikum 4
    // Austausch der Passwortvalidierung-Implementierung in Praktikum 7 (ohne Änderungen an dieser Methode)
    public Owner register(Owner owner) throws InsecurePasswordException {
        log.debug("Create owner {}", owner);
        // zu implementieren in Praktikum 5
        // TODO: Für P5 würde ich keine Code Änderung an dieser Stelle erwarten, sonst würde der Austausch der Interface implementierung wenig Sinn ergeben.
        if (!passwordValidator.isPasswordSecure(owner.password())) {
            throw new InsecurePasswordException();
        }

        if (ownerRepository.find(owner.email()) != null) {
            throw new OwnerAlreadyExistsException(owner.email());
        }

        ownerRepository.save(owner);
        return owner;
    }

    public boolean login(String username, String password) {
        log.debug("Login user {}", username);
        Set<Owner> owners = ownerRepository.findPredicate(
                owner -> owner.email().equals(username) && owner.password().equals(password));
        return !owners.isEmpty();
    }

    @SuppressWarnings("squid:S2629")
    public void updateOwner(String oldEmail, String newEmail, String newName) throws OwnerNotFoundException {
        Owner owner = ownerRepository.find(oldEmail);
        if (owner == null) {
            throw new OwnerNotFoundException(oldEmail);
        }

        if (!oldEmail.equals(newEmail)) {
            log.debug("error ");
            var shop = shopBrowsing.getShopByOwner(oldEmail);
            log.debug("error 2 ", oldEmail, newEmail, shop.id());
            shopOrganization.updateShopEmail(shop.id(), newEmail);
        }

        ownerRepository.save(new Owner(
                newName,
                newEmail,
                owner.password()
        ));
    }


}