package BikeRentalApp;

public class User {
    
    private String username;
    private String password;
    private Bike bike;

    public User (String username, String password) {
        if(validateUsername(username)) {
            this.username = username;
        }
        if(validatePassword(password)){
            this.password = password;
        }
        this.bike = null;
    }
    
    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public Bike getBike() {
        return this.bike;
    }

    public void setBike(Bike bike) {
        if(validateBike(bike)) {
            this.bike = bike;
        }
    }

    public Bike removeBike() {
        if(this.bike =! null){
            return this.bike;
            this.bike = null;
        }
    }

    private boolean validateUsername(String username) {
        // TODO
        if(!username.isEmpty()) {
            return true;
        } else {
            throw new IllegalArgumentException("Ikke gyldig brukernavn");
        }
    }

    private boolean validatePassword(String password) {
        // TODO
        if(!password.isEmpty()) {
            return true;
        } else {
            throw new IllegalArgumentException("Ikke gyldig passord");
        }
    }

    private boolean validateBike(Bike bike) {
        // TODO
        if(bike.instanceOf(Bike)) {
            return true;
        } else {
            throw new IllegalArgumentException("Ikke gyldig sykkel");
        }
    }
}
