public enum Mart {

    B2B(9);

    private final Integer martId;

    Mart(final Integer martId) {
        this.martId = martId;
    }

    public Integer getMartId() {
        return martId;
    }

    public static boolean contains(String value) {
        for (Mart source : values()) {
            if (source.name().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static Mart fromMartIdValue(Integer martId) {
        for (Mart v : Mart.values()) {
            if (v.getMartId().equals(martId)) {
                return v;
            }
        }

        return null;
    }

}
