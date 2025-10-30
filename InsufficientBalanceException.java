/**
 * Yetersiz bakiye durumunda fırlatılan özel exception sınıfı.
 * Para çekme işlemlerinde bakiye yetersiz olduğunda kullanılır.
 */
public class InsufficientBalanceException extends Exception {
    
    /**
     * Varsayılan constructor
     */
    public InsufficientBalanceException() {
        super("Yetersiz bakiye!");
    }
    
    /**
     * Özel mesaj ile exception oluşturur
     * 
     * @param message Hata mesajı
     */
    public InsufficientBalanceException(String message) {
        super(message);
    }
    
    /**
     * Detaylı hata mesajı ile exception oluşturur
     * 
     * @param currentBalance Mevcut bakiye
     * @param requestedAmount İstenen tutar
     */
    public InsufficientBalanceException(double currentBalance, double requestedAmount) {
        super(String.format("Yetersiz bakiye! Mevcut: %.2f TL, İstenen: %.2f TL", 
                          currentBalance, requestedAmount));
    }
}
