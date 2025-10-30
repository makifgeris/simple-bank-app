/**
 * Vadesiz hesap sınıfı.
 * KALITIM (INHERITANCE) örneği: Account sınıfından türetilmiştir.
 * ÇOK BİÇİMLİLİK (POLYMORPHISM) örneği: deposit() ve withdraw() metodları override edilmiştir.
 */
public class DemandAccount extends Account {
    
    // Minimum bakiye limiti
    private static final double MINIMUM_BALANCE = 0.0;
    
    /**
     * DemandAccount constructor
     * KALITIM: super() ile üst sınıf constructor'ı çağrılır
     * 
     * @param accountNo Hesap numarası
     * @param owner Hesap sahibi
     */
    public DemandAccount(String accountNo, Customer owner) {
        super(accountNo, owner); // Üst sınıf constructor'ı çağrılır
    }
    
    /**
     * Para yatırma işlemi
     * ÇOK BİÇİMLİLİK: Account sınıfındaki abstract metod override edilir
     * 
     * @param amount Yatırılacak tutar
     * @throws InvalidTransactionException Geçersiz tutar durumunda
     */
    @Override
    public void deposit(double amount) throws InvalidTransactionException {
        // Tutar validasyonu
        if (!ValidationUtil.isValidAmount(amount)) {
            throw new InvalidTransactionException("Para yatırma", 
                "Tutar sıfırdan büyük olmalıdır");
        }
        
        // Bakiyeyi güncelle
        balance += amount;
        
        // İşlem kaydı oluştur ve ekle
        Transaction transaction = new Transaction(
            TransactionType.DEPOSIT, 
            amount, 
            balance
        );
        addTransaction(transaction);
    }
    
    /**
     * Para çekme işlemi
     * ÇOK BİÇİMLİLİK: Account sınıfındaki abstract metod override edilir
     * Vadesiz hesap için özel kurallar uygulanır
     * 
     * @param amount Çekilecek tutar
     * @return İşlem başarılıysa true
     * @throws InsufficientBalanceException Yetersiz bakiye durumunda
     * @throws InvalidTransactionException Geçersiz tutar durumunda
     */
    @Override
    public boolean withdraw(double amount) 
            throws InsufficientBalanceException, InvalidTransactionException {
        
        // Tutar validasyonu
        if (!ValidationUtil.isValidAmount(amount)) {
            throw new InvalidTransactionException("Para çekme", 
                "Tutar sıfırdan büyük olmalıdır");
        }
        
        // Bakiye kontrolü
        if (balance < amount) {
            throw new InsufficientBalanceException(balance, amount);
        }
        
        // Bakiyeyi güncelle
        balance -= amount;
        
        // İşlem kaydı oluştur ve ekle
        Transaction transaction = new Transaction(
            TransactionType.WITHDRAWAL, 
            amount, 
            balance
        );
        addTransaction(transaction);
        
        return true;
    }
    
    /**
     * Hesap tipini döndürür
     * ÇOK BİÇİMLİLİK: Account sınıfındaki abstract metod override edilir
     * 
     * @return "Vadesiz Hesap"
     */
    @Override
    public String getAccountType() {
        return "Vadesiz Hesap";
    }
}
