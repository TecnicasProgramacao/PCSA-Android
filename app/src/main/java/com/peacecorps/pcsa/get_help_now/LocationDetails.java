package com.peacecorps.pcsa.get_help_now;

/**
 * Holds contact information for a particular location
 *
 * @author Buddhiprabha Erabadda
 * @since 07-08-2015
 */
public class LocationDetails {
    private String volunteerLocationName;    // name of the current location of the volunteer
    private String peaceCorpsMedicalOfficerContact;     //contact number of Peace Corps Medical Officer
    private String safetyAndSecurityContact;      //contact number of Safety and Security Manager
    private String sexualAssaultResponseLiasonContact;     //contact number of Sexual Assault Response Liason

    public LocationDetails(final String volunteerLocationName, final String peaceCorpsMedicalOfficerContact,
                           final String safetyAndSecurityContact,
                           final String sexualAssaultResponseLiasonContact) {

        this.volunteerLocationName = this.volunteerLocationName;
        this.peaceCorpsMedicalOfficerContact = peaceCorpsMedicalOfficerContact;
        this.safetyAndSecurityContact = safetyAndSecurityContact;
        this.sexualAssaultResponseLiasonContact = sexualAssaultResponseLiasonContact;

    }

    /**
     * @return name of the current location of the volunteer
     */
    public final String getVolunteerLocationName() {
        return volunteerLocationName;
    }

    /**
     * @param volunteerLocationName set name of the current location of the volunteer
     */
    public final void setVolunteerLocationName(final String volunteerLocationName) {
        this.volunteerLocationName = volunteerLocationName;
    }

    /**
     * @return contact number of Peace Corps Medical Officer
     */
    public final String getPeaceCorpsMedicalOfficerContact() {
        return peaceCorpsMedicalOfficerContact;
    }

    /**
     * @param peaceCorpsMedicalOfficerContact set contact number of Peace Corps Medical Officer
     */
    public final void setPeaceCorpsMedicalOfficerContact(final String peaceCorpsMedicalOfficerContact) {
        this.peaceCorpsMedicalOfficerContact = peaceCorpsMedicalOfficerContact;
    }

    /**
     * @return contact number of Safety and Security Manager
     */
    public final String getSafetyAndSecurityContact() {
        return safetyAndSecurityContact;
    }

    /**
     * @param safetyAndSecurityContact set contact number of Safety and Security Manager
     */
    public final void setSafetyAndSecurityContact(final String safetyAndSecurityContact) {
        this.safetyAndSecurityContact = safetyAndSecurityContact;
    }

    /**
     * @return contact number of Sexual Assault Response Liason
     */
    public final  String getSexualAssaultResponseLiasonContact() {
        return sexualAssaultResponseLiasonContact;
    }

    /**
     * @param sexualAssaultResponseLiasonContact set contact number of Sexual Assault Response Liason
     */
    public final void setSexualAssaultResponseLiasonContact(final String sexualAssaultResponseLiasonContact) {
        this.sexualAssaultResponseLiasonContact = sexualAssaultResponseLiasonContact;
    }
}
