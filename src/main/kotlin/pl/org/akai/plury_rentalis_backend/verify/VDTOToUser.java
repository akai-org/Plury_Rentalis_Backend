package pl.org.akai.plury_rentalis_backend.verify;

public class VDTOToUser {
    public static User toUser(VerifyDTO verifyDTO) {
        return new User(null, verifyDTO.getEmail());
    }
}
