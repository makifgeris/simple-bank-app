import java.util.ArrayList;
import java.util.List;

/**
 * Tüm hesap tiplerinin temel sınıfı.
 * SOYUTLAMA (ABSTRACTION) örneği: Abstract class ile ortak davranışlar tanımlanır.
 * Alt sınıflar (DemandAccount, TimeDepositAccount) kendi özel davranışlarını implement eder.
 */
public abstract class Account {
    
    // SOYUTLAMA: Protected field'lar - alt sınıflar erişebilir
    protected String accountNo;
    protected Customer owner;
    protected double balance;
    protected List<Transaction> transactions;
    
    /**
     * Account constructor
     * 
     * @param accountNo Hesap numarası
     * @param owner Hesap sahibi müşteri
     */
    public Account(String accountNo, Customer owner) {
        this.accountNo = accountNo;
        this.owner = owner;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }
    
    // Concrete metodlar - tüm hesap tipleri için ortak
    
    /**
     * Hesap numarasını döndürür
     * 
     * @return Hesap numarası
     */
    public String getAccountNo() {
        return accountNo;
    }
    
    /**
     * Hesap bakiyesini döndürür
     * 
     * @return Hesap bakiyesi
     */
    public double getBalance() {
        return balance;
    }
    
    /**
     * Hesap sahibini döndürür
     * 
     * @return Hesap sahibi müşteri
     */
    public Customer getOwner() {
        return owner;
    }
    
    /**
     * İşlem geçmişini döndürür
     * 
     * @return İşlem listesi
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }
    
    /**
     * İşlem kaydı ekler
     * 
     * @param transaction Eklenecek işlem
     */
    protected void addTransaction(Transaction transaction) {
        if (transaction != null) {
            transactions.add(transaction);
        }
    }
    
    /**
     * Hesap bilgilerini formatlanmış şekilde gösterir
     * Template Method Pattern: Genel yapı burada, detaylar alt sınıflarda
     */
    public void displayAccountInfo() {
        System.out.println("═════════════════════════════════════════════");
        System.out.println("Hesap Tipi    : " + getAccountType());
        System.out.println("Hesap No      : " + accountNo);
        System.out.println("Hesap Sahibi  : " + owner.getFullName());
        System.out.println("Bakiye        : " + String.format("%.2f TL", balance));
        System.out.println("İşlem Sayısı  : " + transactions.size());
        System.out.println("═════════════════════════════════════════════");
    }
    
    // SOYUTLAMA: Abstract metodlar - alt sınıflar implement etmek zorunda
    
    /**
     * Para yatırma işlemi
     * Her hesap tipi kendi kurallarına göre implement eder
     * 
     * @param amount Yatırılacak tutar
     * @throws InvalidTransactionException Geçersiz işlem durumunda
     */
    public abstract void deposit(double amount) throws InvalidTransactionException;
    
    /**
     * Para çekme işlemi
     * Her hesap tipi kendi kurallarına göre implement eder (ÇOK BİÇİMLİLİK)
     * 
     * @param amount Çekilecek tutar
     * @return İşlem başarılıysa true, değilse false
     * @throws InsufficientBalanceException Yetersiz bakiye durumunda
     * @throws InvalidTransactionException Geçersiz işlem durumunda
     */
    public abstract boolean withdraw(double amount) 
        throws InsufficientBalanceException, InvalidTransactionException;
    
    /**
     * Hesap tipini döndürür
     * Her hesap tipi kendi adını döndürür
     * 
     * @return Hesap tipi adı
     */
    public abstract String getAccountType();
}
