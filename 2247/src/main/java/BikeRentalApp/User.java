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
        if(validateBike(bike) && this.bike == null) {
            this.bike = bike;
        }
    }

    public Bike returnBike() {
        if(this.bike != null){
            Bike tmpBike = this.bike;
            this.bike = null;
            return tmpBike;
        } else {
            throw new IllegalArgumentException("Du kan ikke fjerne og returnere en sykkel om du ikke har en");
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
        if(bike instanceof Bike) {
            return true;
        } else {
            throw new IllegalArgumentException("Ikke gyldig sykkel");
        }
    }
}
