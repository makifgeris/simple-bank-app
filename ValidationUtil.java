/**
 * Girdi validasyonu ve konsol mesajları için yardımcı sınıf.
 * Tüm metodlar static olarak tanımlanmıştır.
 */
public class ValidationUtil {
    
    // ANSI renk kodları (konsol renklendirme için)
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    
    /**
     * Tutar geçerliliğini kontrol eder
     * 
     * @param amount Kontrol edilecek tutar
     * @return Tutar pozitifse true, değilse false
     */
    public static boolean isValidAmount(double amount) {
        return amount > 0;
    }
    
    /**
     * String geçerliliğini kontrol eder (null ve boş kontrolü)
     * 
     * @param input Kontrol edilecek string
     * @return String geçerliyse true, değilse false
     */
    public static boolean isValidString(String input) {
        return input != null && !input.trim().isEmpty();
    }
    
    /**
     * Hesap tipi geçerliliğini kontrol eder
     * 
     * @param type Kontrol edilecek hesap tipi
     * @return "vadesiz" veya "vadeli" ise true, değilse false
     */
    public static boolean isValidAccountType(String type) {
        if (type == null) return false;
        String lowerType = type.toLowerCase().trim();
        return lowerType.equals("vadesiz") || lowerType.equals("vadeli") ||
               lowerType.equals("1") || lowerType.equals("2");
    }
    
    /**
     * Hesapta yeterli bakiye olup olmadığını kontrol eder
     * 
     * @param account Kontrol edilecek hesap
     * @param amount İstenen tutar
     * @return Bakiye yeterliyse true, değilse false
     */
    public static boolean hasSufficientBalance(Account account, double amount) {
        if (account == null) return false;
        return account.getBalance() >= amount;
    }
    
    /**
     * Kırmızı renkli hata mesajı gösterir
     * 
     * @param message Gösterilecek hata mesajı
     */
    public static void displayError(String message) {
        System.out.println(RED + "❌ HATA: " + message + RESET);
    }
    
    /**
     * Yeşil renkli başarı mesajı gösterir
     * 
     * @param message Gösterilecek başarı mesajı
     */
    public static void displaySuccess(String message) {
        System.out.println(GREEN + "✓ BAŞARILI: " + message + RESET);
    }
    
    /**
     * Sarı renkli uyarı mesajı gösterir
     * 
     * @param message Gösterilecek uyarı mesajı
     */
    public static void displayWarning(String message) {
        System.out.println(YELLOW + "⚠ UYARI: " + message + RESET);
    }
}