package pl.org.akai.plury_rentalis_backend.verify;

public class VerifyDTOUserTranslator {
    public static User toUser(VerifyDTO verifyDTO) {
        return new User(null, verifyDTO.getEmail());
    }
}
