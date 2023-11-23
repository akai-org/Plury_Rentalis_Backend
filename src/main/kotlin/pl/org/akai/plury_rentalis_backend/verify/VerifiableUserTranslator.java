package pl.org.akai.plury_rentalis_backend.verify;

public class VerifiableUserTranslator {
    public static User toUser(Verifiable verifiable) {
        return new User(null, verifiable.getEmail());
    }
}
