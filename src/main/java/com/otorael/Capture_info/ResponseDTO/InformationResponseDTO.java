package com.otorael.Capture_info.ResponseDTO;

public class InformationResponseDTO {

    private String notification;
    private String X_Forwarded_For;
    private String X_Forwarded_Proto;
    private String message;

    /**
     *
     * @param notification THROWS quick success notifications
     * @param x_Forwarded_For Get client IP address from X-Forwarded-For or RemoteAddress
     * @param x_Forwarded_Proto Get X-Forwarded-Proto (HTTP or HTTPS)
     * @param message Shows description message for the response
     *
     */
    public InformationResponseDTO(
            String notification,
            String x_Forwarded_For,
            String x_Forwarded_Proto,
            String message)
    {
        this.notification = notification;
        X_Forwarded_For = x_Forwarded_For;
        X_Forwarded_Proto = x_Forwarded_Proto;
        this.message = message;
    }

    public String getNotification() {
        return notification;
    }

    /**
     *
     *
     * @param notification THROWS quick success notifications
     *
     */
    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getX_Forwarded_For() {
        return X_Forwarded_For;
    }

    /**
     *
     *
     * @param x_Forwarded_For Get client IP address from X-Forwarded-For or RemoteAddress
     *
     */
    public void setX_Forwarded_For(String x_Forwarded_For) {
        X_Forwarded_For = x_Forwarded_For;
    }

    public String getX_Forwarded_Proto() {
        return X_Forwarded_Proto;
    }

    /**
     *
     * @param x_Forwarded_Proto SETTING INTO THE DATABASE PROTOCOL i.e http or https
     *
     */
    public void setX_Forwarded_Proto(String x_Forwarded_Proto) {
        X_Forwarded_Proto = x_Forwarded_Proto;
    }

    public String getMessage() {
        return message;
    }

    /**
     *
     *
     * @param message Shows description message for the response
     *
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
