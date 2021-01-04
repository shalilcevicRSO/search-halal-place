package com.selma.halal.food.project.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "search_place_metadata")
@NamedQueries(value =
        {
                @NamedQuery(name = "SearchPlaceMetadataEntity.getAll",
                            query = "SELECT place FROM SearchPlaceMetadataEntity place")
        }
)
public class SearchPlaceMetadataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
