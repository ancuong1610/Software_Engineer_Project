package de.hda.fbi.efussgaengerzone.views.model;

import de.hda.fbi.efussgaengerzone.domain.model.owner.Owner;

public record OwnerDto(String name, String email) {
    public static OwnerDto fromOwner(Owner owner) {
        return new OwnerDto(owner.name(), owner.email());
    }
}
