package com.capstone.alerrt_app.classes.data_objects;

public class AgencyDO {
    private String agencyID,agencyCaption,agencyDescription,agencyContactNumber,agencyLocation,agencyStatus,agencyImage,agencyAvailability;

    public AgencyDO(String agencyID, String agencyCaption, String agencyDescription, String agencyContactNumber, String agencyLocation, String agencyStatus, String agencyImage, String agencyAvailability) {
        this.agencyID = agencyID;
        this.agencyCaption = agencyCaption;
        this.agencyDescription = agencyDescription;
        this.agencyContactNumber = agencyContactNumber;
        this.agencyLocation = agencyLocation;
        this.agencyStatus = agencyStatus;
        this.agencyImage = agencyImage;
        this.agencyAvailability = agencyAvailability;
    }

    public String getAgencyID() {
        return agencyID;
    }

    public String getAgencyCaption() {
        return agencyCaption;
    }

    public String getAgencyDescription() {
        return agencyDescription;
    }

    public String getAgencyContactNumber() {
        return agencyContactNumber;
    }

    public String getAgencyLocation() {
        return agencyLocation;
    }

    public String getAgencyStatus() {
        return agencyStatus;
    }

    public String getAgencyImage() {
        return agencyImage;
    }

    public String getAgencyAvailability() {
        return agencyAvailability;
    }
}
