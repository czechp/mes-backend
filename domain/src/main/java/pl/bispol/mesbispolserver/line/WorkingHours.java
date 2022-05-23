package pl.bispol.mesbispolserver.line;

public enum WorkingHours {
    HOURS8 {
        @Override
        public float getHoursAmount() {
            return 8.0f;
        }
    },
    HOURS12 {
        @Override
        public float getHoursAmount() {
            return 12.0f;
        }
    };

    public abstract float getHoursAmount();
}
