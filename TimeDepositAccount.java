import java.time.LocalDate;

/**
 * Vadeli hesap sınıfı.
 * KALITIM (INHERITANCE) örneği: Account sınıfından türetilmiştir.
 * ÇOK BİÇİMLİLİK (POLYMORPHISM) örneği: withdraw() metodu farklı davranış gösterir.
 * Vade kontrolü yapar ve erken çekim durumunda ceza uygular.
 */
public class TimeDepositAccount extends Account {
    
    private LocalDate maturityDate;      // Vade tarihi
    private double interestRate;         // Faiz oranı
    private static final double EARLY_WITHDRAWAL_PENALTY = 0.05; // %5 erken çekim cezası
    
    /**
     * TimeDepositAccount constructor
     * KALITIM: super() ile üst sınıf constructor'ı çağrılır
     * 
     * @param accountNo Hesap numarası
     * @param owner Hesap sahibi
     * @param months Vade süresi (ay cinsinden)
     * @param interestRate Yıllık faiz oranı (örn: 0.10 = %10)
     */
    public TimeDepositAccount(String accountNo, Customer owner, int months, double interestRate) {
        super(accountNo, owner);
        this.maturityDate = LocalDate.now().plusMonths(months);
        this.interestRate = interestRate;
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
     * Vadeli hesap için özel kurallar: Vade kontrolü ve erken çekim cezası
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
        
        // Vade kontrolü - Vade dolmadıysa ceza uygula
        if (!isMatured()) {
            double penalty = applyPenalty(amount);
            ValidationUtil.displayWarning(
                String.format("Vade dolmadan para çekiyorsunuz! Ceza: %.2f TL", penalty)
            );
            
            // Ceza işlemi kaydı
            balance -= penalty;
            Transaction penaltyTransaction = new Transaction(
                TransactionType.PENALTY,
                penalty,
                balance,
                "Erken çekim cezası (%5)"
            );
            addTransaction(penaltyTransaction);
        } else {
            // Vade dolmuşsa faiz ekle
            double interest = calculateInterest();
            if (interest > 0) {
                balance += interest;
                Transaction interestTransaction = new Transaction(
                    TransactionType.INTEREST,
                    interest,
                    balance,
                    String.format("Vade sonu faizi (%%%d)", (int)(interestRate * 100))
                );
                addTransaction(interestTransaction);
                ValidationUtil.displaySuccess(
                    String.format("Vade sonu faizi eklendi: %.2f TL", interest)
                );
            }
        }
        
        // Bakiyeyi güncelle
        balance -= amount;
        
        // Para çekme işlem kaydı
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
     * @return "Vadeli Hesap"
     */
    @Override
    public String getAccountType() {
        return "Vadeli Hesap";
    }
    
    /**
     * Vadenin dolup dolmadığını kontrol eder
     * 
     * @return Vade dolmuşsa true, dolmamışsa false
     */
    public boolean isMatured() {
        return LocalDate.now().isAfter(maturityDate) || 
               LocalDate.now().isEqual(maturityDate);
    }
    
    /**
     * Faiz hesaplar
     * 
     * @return Hesaplanan faiz tutarı
     */
    public double calculateInterest() {
        if (isMatured()) {
            return balance * interestRate;
        }
        return 0.0;
    }
    
    /**
     * Erken çekim cezası uygular
     * 
     * @param amount Çekilecek tutar
     * @return Ceza tutarı
     */
    private double applyPenalty(double amount) {
        return amount * EARLY_WITHDRAWAL_PENALTY;
    }
    
    /**
     * Vade tarihini döndürür
     * 
     * @return Vade tarihi
     */
    public LocalDate getMaturityDate() {
        return maturityDate;
    }
    
    /**
     * Faiz oranını döndürür
     * 
     * @return Faiz oranı
     */
    public double getInterestRate() {
        return interestRate;
    }
    
    /**
     * Hesap bilgilerini formatlanmış şekilde gösterir
     * Override: Vadeli hesaba özel bilgiler eklenir
     */
    @Override
    public void displayAccountInfo() {
        super.displayAccountInfo();
        System.out.println("Vade Tarihi   : " + maturityDate);
        System.out.println("Faiz Oranı    : %" + (int)(interestRate * 100));
        System.out.println("Vade Durumu   : " + (isMatured() ? "Dolmuş" : "Dolmamış"));
        if (!isMatured()) {
            System.out.println("Erken Çekim   : %5 ceza uygulanır");
        }
        System.out.println("═════════════════════════════════════════════");
    }
}
