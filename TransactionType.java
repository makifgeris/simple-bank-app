/**
 * İşlem tiplerini tanımlayan enum sınıfı.
 * Para yatırma, çekme, faiz ve ceza işlemlerini temsil eder.
 */
public enum TransactionType {
    DEPOSIT("Para Yatırma"),
    WITHDRAWAL("Para Çekme"),
    INTEREST("Faiz"),
    PENALTY("Ceza");
    
    private final String displayName;
    
    /**
     * TransactionType constructor
     * 
     * @param displayName İşlem tipinin görünen adı
     */
    TransactionType(String displayName) {
        this.displayName = displayName;
    }
    
    /**
     * İşlem tipinin görünen adını döndürür
     * 
     * @return İşlem tipinin Türkçe adı
     */
    public String getDisplayName() {
        return displayName;
    }
}
