import java.util.ArrayList;
import java.util.List;

/**
 * Banka müşterisini temsil eden sınıf.
 * KAPSÜLLEME (ENCAPSULATION) örneği: Tüm field'lar private, erişim getter/setter ile sağlanır.
 */
public class Customer {
    
    // KAPSÜLLEME: Tüm alanlar private olarak tanımlanmış
    private String customerId;
    private String firstName;
    private String lastName;
    private List<Account> accounts;
    
    /**
     * Customer constructor
     * 
     * @param customerId Müşteri numarası
     * @param firstName Müşteri adı
     * @param lastName Müşteri soyadı
     */
    public Customer(String customerId, String firstName, String lastName) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accounts = new ArrayList<>();
    }
    
    // KAPSÜLLEME: Getter metodları ile kontrollü okuma erişimi
    
    /**
     * Müşteri ID'sini döndürür
     * 
     * @return Müşteri ID'si
     */
    public String getCustomerId() {
        return customerId;
    }
    
    /**
     * Müşteri adını döndürür
     * 
     * @return Müşteri adı
     */
    public String getFirstName() {
        return firstName;
    }
    
    /**
     * Müşteri soyadını döndürür
     * 
     * @return Müşteri soyadı
     */
    public String getLastName() {
        return lastName;
    }
    
    /**
     * Müşterinin hesap listesini döndürür
     * 
     * @return Hesap listesi
     */
    public List<Account> getAccounts() {
        return accounts;
    }
    
    // KAPSÜLLEME: Setter metodları ile kontrollü yazma erişimi
    
    /**
     * Müşteri adını günceller
     * 
     * @param firstName Yeni müşteri adı
     */
    public void setFirstName(String firstName) {
        if (ValidationUtil.isValidString(firstName)) {
            this.firstName = firstName;
        } else {
            ValidationUtil.displayError("Geçersiz ad!");
        }
    }
    
    /**
     * Müşteri soyadını günceller
     * 
     * @param lastName Yeni müşteri soyadı
     */
    public void setLastName(String lastName) {
        if (ValidationUtil.isValidString(lastName)) {
            this.lastName = lastName;
        } else {
            ValidationUtil.displayError("Geçersiz soyad!");
        }
    }
    
    /**
     * Müşteriye yeni hesap ekler
     * 
     * @param account Eklenecek hesap
     */
    public void addAccount(Account account) {
        if (account != null) {
            accounts.add(account);
        }
    }
    
    /**
     * Müşteriden hesap kaldırır
     * 
     * @param account Kaldırılacak hesap
     */
    public void removeAccount(Account account) {
        if (account != null) {
            accounts.remove(account);
        }
    }
    
    /**
     * Müşterinin tam adını döndürür
     * 
     * @return Ad ve soyad birleşimi
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    /**
     * Müşteri bilgilerini formatlanmış şekilde konsola yazdırır
     */
    public void displayCustomerInfo() {
        System.out.println("═════════════════════════════════════════════");
        System.out.println("Müşteri ID    : " + customerId);
        System.out.println("Ad Soyad      : " + getFullName());
        System.out.println("Hesap Sayısı  : " + accounts.size());
        
        if (!accounts.isEmpty()) {
            System.out.println("\nHesaplar:");
            for (Account account : accounts) {
                System.out.println("  - " + account.getAccountType() + 
                                 " (" + account.getAccountNo() + "): " + 
                                 String.format("%.2f TL", account.getBalance()));
            }
        }
        System.out.println("═════════════════════════════════════════════");
    }
}
