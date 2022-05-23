package pl.bispol.mesbispolserver;

public class Statements {
    public static final String USER_NOT_EXISTS = "Użytkownik nie istnieje";
    public static final String PASSWORD_ARE_NOT_EQUAL = "Hasła nie są identyczne";
    public static final String EMAIL_DO_NOT_MATCH_TO_USER = "Ten adres email nie jest przypisany do tego użytkownika";
    public static final String INVALID_USERNAME_OR_PASSWORD = "Nieprawidłowy login lub hasło";
    public static final String RFID_ID_ALREADY_ASSIGNED = "Number karty został już przypisany do istniejącego użytkownika";
    public static final String USER_ALREADY_EXISTS = "Taki użytkownik już istnieje";
    public static final String PRODUCT_NOT_EXISTS = "Taki produkt nie istnieje";
    public static final String PRODUCT_PROPERTY_NOT_EXISTS = "Taka właściwość produktu nie istnieje";
    public static final String LINE_NOT_EXISTS = "Taka linia nie istnieje";
    public static final String PRODUCT_TYPE_AND_LINE_NOT_EQUALS = "Produkt musi być w tej samej kategorii co produkująca go linia";
    public static final String PRODUCT_EFFICIENT_NOT_EXISTS = "Wydajność dla tego produktu nie istnieje";
    public static final String PRODUCT_HAS_NO_LINE = "Ten produkt nie jest przypisany do tej linii";
    public static final String OPEN_REPORT_DOES_NOT_EXIST = "Brak otwartego raportu dla tej linii";
    public static final String REPORT_ALREADY_EXISTS = "Istnieje otwarty raport - zamknij i spróbuj ponownie";
    public static final String OPERATOR_NOT_EXISTS = "Nie wykryto karty użytkownika. Zbliż kartę do czytnika";
    public static final String LINE_HAS_NO_PRODUCT = "Aby utworzyć raport musisz wybrać produkt";
    public static final String THERE_IS_NO_EFFICIENT_FOR_LINE = "Brak wydajności dla tej linii";
    public static final String ACTIVE_REPORT_EXISTS = "Nie można zmienić produktu podczas otwratego raportu";
    public static final String REPORT_DOES_NOT_EXIST = "Raport nie istnieje";
    public static final String QUALITY_CONTROL_NOT_EXISTS = "Inspekcja jakości nie istnieje";
    public static final String DOWN_TIME_NOT_EXISTS = "Taki przestój produkcyjny nie istnieje";
    public static final String OPEN_DOWNTIME_ALREADY_EXISTS = "Istnieje już otwarty przestój produkcyjny";
    public static final String BREAKDOWN_NOT_EXISTS = "Taka awaria nie istnieje";
    public static final String USER_HAVE_TO_BE_MAINTENANCE = "Aby zaakceptować awarie musisz być pracownikiem UR";
    public static final String RAW_MATERIAL_NOT_FOUND = "Taki surowiec nie istnieje";
    public static final String USED_RAW_MATERIAL_DOES_NOT_EXIST = "Taki surowiec nie istnieje";


    Statements() {
    }
}
