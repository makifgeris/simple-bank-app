/**
 * Hesap bulunamadığında fırlatılan özel exception sınıfı.
 * Geçersiz hesap numarası ile işlem yapılmaya çalışıldığında kullanılır.
 */
public class AccountNotFoundException extends Exception {
    
    /**
     * Varsayılan constructor
     */
    public AccountNotFoundException() {
        super("Hesap bulunamadı!");
    }
    
    /**
     * Özel mesaj ile exception oluşturur
     * 
     * @param message Hata mesajı
     */
    public AccountNotFoundException(String message) {
        super(message);
    }
    
    /**
     * Hesap numarası ile detaylı hata mesajı oluşturur
     * 
     * @param accountNo Bulunamayan hesap numarası
     */
    public AccountNotFoundException(String accountNo, boolean withAccountNo) {
        super(String.format("Hesap bulunamadı: %s", accountNo));
    }
}
