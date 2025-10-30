import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Banka işlemlerini (para yatırma/çekme) temsil eden sınıf.
 * Her işlem için tarih, tip, tutar ve işlem sonrası bakiye bilgilerini saklar.
 */
public class Transaction {
    
    private final String transactionId;
    private final LocalDateTime timestamp;
    private final TransactionType type;
    private final double amount;
    private final double balanceAfter;
    private String description;
    
    /**
     * Transaction constructor
     * 
     * @param type İşlem tipi (DEPOSIT, WITHDRAWAL, INTEREST, PENALTY)
     * @param amount İşlem tutarı
     * @param balanceAfter İşlem sonrası bakiye
     */
    public Transaction(TransactionType type, double amount, double balanceAfter) {
        this.transactionId = generateTransactionId();
        this.timestamp = LocalDateTime.now();
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.description = type.getDisplayName();
    }
    
    /**
     * Açıklama ile Transaction constructor
     * 
     * @param type İşlem tipi
     * @param amount İşlem tutarı
     * @param balanceAfter İşlem sonrası bakiye
     * @param description İşlem açıklaması
     */
    public Transaction(TransactionType type, double amount, double balanceAfter, String description) {
        this.transactionId = generateTransactionId();
        this.timestamp = LocalDateTime.now();
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.description = description;
    }
    
    /**
     * Benzersiz işlem ID'si oluşturur
     * 
     * @return Benzersiz işlem ID'si
     */
    private String generateTransactionId() {
        return "TXN" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    /**
     * İşlem ID'sini döndürür
     * 
     * @return İşlem ID'si
     */
    public String getTransactionId() {
        return transactionId;
    }
    
    /**
     * İşlem zamanını döndürür
     * 
     * @return İşlem zamanı
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    /**
     * İşlem tipini döndürür
     * 
     * @return İşlem tipi
     */
    public TransactionType getType() {
        return type;
    }
    
    /**
     * İşlem tutarını döndürür
     * 
     * @return İşlem tutarı
     */
    public double getAmount() {
        return amount;
    }
    
    /**
     * İşlem sonrası bakiyeyi döndürür
     * 
     * @return İşlem sonrası bakiye
     */
    public double getBalanceAfter() {
        return balanceAfter;
    }
    
    /**
     * İşlem açıklamasını döndürür
     * 
     * @return İşlem açıklaması
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * İşlem bilgilerini formatlanmış şekilde konsola yazdırır
     */
    public void displayTransaction() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDate = timestamp.format(formatter);
        
        System.out.println("─────────────────────────────────────────────");
        System.out.println("İşlem ID      : " + transactionId);
        System.out.println("Tarih         : " + formattedDate);
        System.out.println("İşlem Tipi    : " + type.getDisplayName());
        System.out.println("Tutar         : " + String.format("%.2f TL", amount));
        System.out.println("Sonraki Bakiye: " + String.format("%.2f TL", balanceAfter));
        if (description != null && !description.equals(type.getDisplayName())) {
            System.out.println("Açıklama      : " + description);
        }
    }
}
