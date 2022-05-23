package pl.bispol.mesbispolserver.report;

public enum ReportWorkShift {
    FIRST {
        @Override
        public String toString() {
            return "I";
        }
    }, SECOND {
        @Override
        public String toString() {
            return "II";
        }
    }, THIRD {
        @Override
        public String toString() {
            return "III";
        }
    };

    public abstract String toString();
}
