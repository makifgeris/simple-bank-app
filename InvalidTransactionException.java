/**
 * Geçersiz işlem durumunda fırlatılan özel exception sınıfı.
 * Hatalı para yatırma/çekme işlemlerinde kullanılır.
 */
public class InvalidTransactionException extends Exception {
    
    /**
     * Varsayılan constructor
     */
    public InvalidTransactionException() {
        super("Geçersiz işlem!");
    }
    
    /**
     * Özel mesaj ile exception oluşturur
     * 
     * @param message Hata mesajı
     */
    public InvalidTransactionException(String message) {
        super(message);
    }
    
    /**
     * İşlem tipi ve sebep ile detaylı hata mesajı oluşturur
     * 
     * @param transactionType İşlem tipi (yatırma/çekme)
     * @param reason Geçersizlik sebebi
     */
    public InvalidTransactionException(String transactionType, String reason) {
        super(String.format("Geçersiz %s işlemi: %s", transactionType, reason));
    }
}
