package de.hda.fbi.efussgaengerzone.domain.model.shop;


public enum VideoMessenger {
    WHATSAPP("WhatsApp"),
    ZOOM("Zoom"),
    FACETIME("Facetime"),
    GOOGLEDUO("Google Duo"),
    TELEGRAM("Telegram"),
    SKYPE("Skype");

    public final String label;

    VideoMessenger(String label) {
        this.label = label;
    }
}
