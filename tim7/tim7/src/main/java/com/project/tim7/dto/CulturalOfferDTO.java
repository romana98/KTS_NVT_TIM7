package com.project.tim7.dto;
import com.sun.istack.NotNull;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;


public class CulturalOfferDTO {

    private int id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @PastOrPresent
    private Date startDate;

    @PastOrPresent
    private Date endDate;

    @NotNull
    private int subcategory;

    @NotNull
    private int location;

    public CulturalOfferDTO(int id, @NotBlank String name,
                            @NotBlank String description,
                            @PastOrPresent Date startDate,
                            @PastOrPresent Date endDate,
                            int subcategory,
                            int location) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.subcategory = subcategory;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(int subcategory) {
        this.subcategory = subcategory;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "CulturalOfferDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", subcategory=" + subcategory +
                ", location=" + location +
                '}';
    }
}