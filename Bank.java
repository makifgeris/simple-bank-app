import java.util.*;

/**
 * Banka yönetim sistemi sınıfı.
 * Müşteri ve hesap işlemlerini merkezi olarak yönetir.
 * POLİMORFİZM: Account referansı ile farklı hesap tiplerini yönetir.
 */
public class Bank {
    
    private List<Customer> customers;
    private Map<String, Account> accounts;
    private int nextCustomerId;
    private int nextAccountId;
    
    /**
     * Bank constructor
     * Listeleri initialize eder ve ID sayaçlarını başlatır
     */
    public Bank() {
        this.customers = new ArrayList<>();
        this.accounts = new HashMap<>();
        this.nextCustomerId = 1;
        this.nextAccountId = 1;
    }
    
    /**
     * Yeni müşteri ekler
     * 
     * @param firstName Müşteri adı
     * @param lastName Müşteri soyadı
     * @return Oluşturulan Customer nesnesi
     */
    public Customer addCustomer(String firstName, String lastName) {
        String customerId = generateCustomerId();
        Customer customer = new Customer(customerId, firstName, lastName);
        customers.add(customer);
        return customer;
    }
    
    /**
     * Müşteri ID'sine göre müşteri bulur
     * 
     * @param customerId Aranacak müşteri ID'si
     * @return Bulunan müşteri, bulunamazsa null
     */
    public Customer findCustomer(String customerId) {
        for (Customer customer : customers) {
            if (customer.getCustomerId().equals(customerId)) {
                return customer;
            }
        }
        return null;
    }
    
    /**
     * Tüm müşterileri döndürür
     * 
     * @return Müşteri listesi
     */
    public List<Customer> getAllCustomers() {
        return customers;
    }
    
    /**
     * Yeni hesap oluşturur
     * POLİMORFİZM: Account referansı döndürür, gerçek tip runtime'da belirlenir
     * 
     * @param customer Hesap sahibi müşteri
     * @param accountType Hesap tipi ("vadesiz"/"1" veya "vadeli"/"2")
     * @return Oluşturulan Account nesnesi (DemandAccount veya TimeDepositAccount)
     */
    public Account createAccount(Customer customer, String accountType) {
        String accountNo = generateAccountNo();
        Account account;
        
        // POLİMORFİZM: Hesap tipine göre farklı sınıf instance'ı oluşturulur
        if (accountType.equals("vadesiz") || accountType.equals("1")) {
            account = new DemandAccount(accountNo, customer);
        } else if (accountType.equals("vadeli") || accountType.equals("2")) {
            // Vadeli hesap için varsayılan: 12 ay, %10 faiz
            account = new TimeDepositAccount(accountNo, customer, 12, 0.10);
        } else {
            return null;
        }
        
        // Hesabı sisteme kaydet
        accounts.put(accountNo, account);
        customer.addAccount(account);
        
        return account;
    }
    
    /**
     * Hesap numarasına göre hesap bulur
     * 
     * @param accountNo Aranacak hesap numarası
     * @return Bulunan hesap, bulunamazsa null
     */
    public Account findAccount(String accountNo) {
        return accounts.get(accountNo);
    }
    
    /**
     * Hesap kapatır
     * 
     * @param accountNo Kapatılacak hesap numarası
     * @return İşlem başarılıysa true
     * @throws Exception Bakiye sıfır değilse veya hesap bulunamazsa
     */
    public boolean closeAccount(String accountNo) throws Exception {
        Account account = findAccount(accountNo);
        
        if (account == null) {
            throw new AccountNotFoundException(accountNo, true);
        }
        
        if (account.getBalance() > 0) {
            throw new Exception("Hesap bakiyesi sıfır olmalıdır! Mevcut bakiye: " + 
                              String.format("%.2f TL", account.getBalance()));
        }
        
        // Hesabı sistemden kaldır
        accounts.remove(accountNo);
        account.getOwner().removeAccount(account);
        
        return true;
    }
    
    /**
     * Benzersiz müşteri ID'si oluşturur
     * 
     * @return "C" + sayı formatında müşteri ID'si
     */
    private String generateCustomerId() {
        return "C" + (nextCustomerId++);
    }
    
    /**
     * Benzersiz hesap numarası oluşturur
     * 
     * @return "A" + sayı formatında hesap numarası
     */
    private String generateAccountNo() {
        return "A" + (nextAccountId++);
    }
}
