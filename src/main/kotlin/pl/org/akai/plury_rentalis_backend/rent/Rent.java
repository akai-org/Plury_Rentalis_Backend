package pl.org.akai.plury_rentalis_backend.rent;

public record Rent(
        String uuid,
        String userName,
        String rentableName,
        String rentableUUID,
        String rentableType
) {
    public Rent(RentDetails rentDetails) {
        this(
                rentDetails.getUuid(),
                rentDetails.getRenter() != null ? rentDetails.getRenter().getName() : null,
                rentDetails.getRentable().getName(),
                rentDetails.getRentable().getUuid(),
                rentDetails.getRentable().getType().name()
        );
    }
}
