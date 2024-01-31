package it.unimib.sportq.model;

public abstract class Result {
    private Result() {
    }

    public boolean isSuccess() {
        if (this instanceof UserResponseSuccess || this instanceof StadioResponseSuccess) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Class that represents a successful action during the interaction
     * with a Web Service or a local database.
     */
    public static final class UserResponseSuccess extends Result {
        private final User user;
        public UserResponseSuccess(User user) {
            this.user = user;
        }

        public User getData() {
            return user;
        }
    }

    public static final class StadioResponseSuccess extends Result {
        private final StadioResponse stadioResponse;
        public StadioResponseSuccess(StadioResponse stadioResponse) {this.stadioResponse =  stadioResponse;}
        public StadioResponse getData() {
            return stadioResponse;
        }
    }

    /**
     * Class that represents an error occurred during the interaction
     * with a Web Service or a local database.
     */
    public static final class Error extends Result {
        private final String message;
        public Error(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }
    }
}
