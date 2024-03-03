package huydpham.Model;

public enum Wood {
    INDIAN_ROSEWOOD, BRAZILIAN_ROSEWOOD, MAHOGANY, MAPLE, COCOBOLO, CEDAR, ADIRONDACK, ALDER, SITKA;

    public String toString() {
        return switch (this) {
            case INDIAN_ROSEWOOD -> "Indian_Rosewood";
            case BRAZILIAN_ROSEWOOD -> "Brazilian_Rosewood";
            case MAHOGANY -> "Mahogany";
            case MAPLE -> "Maple";
            case COCOBOLO -> "Cocobolo";
            case CEDAR -> "Cedar";
            case ADIRONDACK -> "Adirondack";
            case ALDER -> "Alder";
            case SITKA -> "Sitka";
            default -> "unspecified";
        };
    }
}
