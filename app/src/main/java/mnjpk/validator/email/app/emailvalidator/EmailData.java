package mnjpk.validator.email.app.emailvalidator;


/**
 * Created by manojkulkarni on 3/21/18.
 */

public class EmailData {

    private String result;

    private String reason;

    private String validEmail;

    private String email;

    private String user;

    private String domain;


    public String getResult() {
        if (result == null){
            return "No Result!";
        }
        else {
            return "The email is "+result+".";
        }
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getReason() {
        if (reason.equals("accepted_email")) {
            return "Accepted Email!";
        }
        else if (reason.equals("rejected_email")){
            return "Rejected Email!";
        }
        else{
            return  "No reason";
        }
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getValidEmail() {
        if (validEmail == null){
            return "The email is valid!";
        }
        else {
            return validEmail;
        }
    }

    public void setValidEmail(String validEmail) {
        this.validEmail = validEmail;
    }

    public String getEmail() {
        if (email == null){
            return "No email!";
        }
        else {
            return email;
        }
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser() {
        if (user == null){
            return "No User!";
        }
        else {
            return user;
        }
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDomain() {
        if (domain == null){
            return "No Domain!";
        }
        else {
            return domain;
        }
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }


    public EmailData(String result, String reason, String validEmail, String email, String user, String domain){

        this.setResult(result);
        this.setReason(reason);
        this.setValidEmail(validEmail);
        this.setEmail(email);
        this.setUser(user);
        this.setDomain(domain);

    }


}
