package com.otorael.Capture_info.Model;

import jakarta.persistence.*;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 *     renaming the table as information-captured in the database.
 * </p>
 *
 */
@Entity
@Table(name = "information_captured")
public class InformationModel {
    /**
     *
     * <p>
     *     Auto incrementing the ID
     * </p>
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String X_Forwarded_For;
    private String X_Forwarded_Proto;

    /**
     *
     *
     * @param id takes unique id of a given captured data/information
     * @param x_Forwarded_For Get client IP address from X-Forwarded-For or RemoteAddr
     * @param x_Forwarded_Proto Get X-Forwarded-Proto (HTTP or HTTPS)
     *
     */
    public InformationModel(Long id, String x_Forwarded_For, String x_Forwarded_Proto) {
        this.id = id;
        X_Forwarded_For = x_Forwarded_For;
        X_Forwarded_Proto = x_Forwarded_Proto;
    }

    public InformationModel() {
    }

    public Long getId() {
        return id;
    }

    /**
     *
     * @param id setting ID INTO THE DATABASE
     */
    public void setId(Long id) {
        this.id = id;
    }

    public String getX_Forwarded_For() {
        return X_Forwarded_For;
    }

    /**
     *
     * @param x_Forwarded_For SETTING INTO THE DATABASE IP ADDRESS
     */
    public void setX_Forwarded_For(String x_Forwarded_For) {
        X_Forwarded_For = x_Forwarded_For;
    }

    public String getX_Forwarded_Proto() {
        return X_Forwarded_Proto;
    }

    /**
     *
     *
     * @param x_Forwarded_Proto SETTING INTO THE DATABASE PROTOCOL i.e http or https
     */
    public void setX_Forwarded_Proto(String x_Forwarded_Proto) {
        X_Forwarded_Proto = x_Forwarded_Proto;
    }
}
