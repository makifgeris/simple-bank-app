import java.util.Scanner;
import java.util.List;

/**
 * Banka Hesap Simülasyonu ana uygulama sınıfı.
 * Konsol tabanlı menü sistemi ile kullanıcı etkileşimi sağlar.
 */
public class BankApp {
    
    private Bank bank;
    private Scanner scanner;
    
    /**
     * BankApp constructor
     * Bank ve Scanner nesnelerini initialize eder
     */
    public BankApp() {
        this.bank = new Bank();
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Uygulamanın giriş noktası
     * 
     * @param args Komut satırı argümanları
     */
    public static void main(String[] args) {
        BankApp app = new BankApp();
        app.run();
    }
    
    /**
     * Ana uygulama döngüsü
     * Menü gösterir ve kullanıcı seçimlerini işler
     */
    public void run() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║     BANKA HESAP SİMÜLASYONU         ║");
        System.out.println("╚═══════════════════════════════════════╝\n");
        
        while (true) {
            displayMainMenu();
            
            try {
                System.out.print("Seçiminiz: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Buffer temizle
                
                if (choice == 9) {
                    System.out.println("\n╔═══════════════════════════════════════╗");
                    System.out.println("║  Çıkış yapılıyor... Güle güle!       ║");
                    System.out.println("╚═══════════════════════════════════════╝\n");
                    break;
                }
                
                handleUserChoice(choice);
                
            } catch (Exception e) {
                scanner.nextLine(); // Buffer temizle
                ValidationUtil.displayError("Geçersiz giriş! Lütfen bir sayı girin.");
            }
            
            System.out.println("\nDevam etmek için Enter'a basın...");
            scanner.nextLine();
        }
        
        scanner.close();
    }
    
    /**
     * Ana menüyü konsola yazdırır
     */
    private void displayMainMenu() {
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("         ANA MENÜ");
        System.out.println("═══════════════════════════════════════");
        System.out.println("1. Yeni Müşteri Ekle");
        System.out.println("2. Müşteri Listele");
        System.out.println("3. Hesap Aç");
        System.out.println("4. Para Yatır");
        System.out.println("5. Para Çek");
        System.out.println("6. Bakiye Görüntüle");
        System.out.println("7. İşlem Geçmişi");
        System.out.println("8. Hesap Kapat");
        System.out.println("9. Çıkış");
        System.out.println("═══════════════════════════════════════");
    }
    
    /**
     * Kullanıcı seçimini ilgili metoda yönlendirir
     * 
     * @param choice Kullanıcının seçimi
     */
    private void handleUserChoice(int choice) {
        switch (choice) {
            case 1:
                handleAddCustomer();
                break;
            case 2:
                handleListCustomers();
                break;
            case 3:
                handleOpenAccount();
                break;
            case 4:
                handleDeposit();
                break;
            case 5:
                handleWithdraw();
                break;
            case 6:
                handleViewBalance();
                break;
            case 7:
                handleViewTransactionHistory();
                break;
            case 8:
                handleCloseAccount();
                break;
            default:
                ValidationUtil.displayError("Geçersiz seçim! Lütfen 1-9 arası bir sayı girin.");
        }
    }

    /**
     * Yeni müşteri ekleme işlemini gerçekleştirir
     */
    private void handleAddCustomer() {
        System.out.println("\n--- YENİ MÜŞTERİ EKLE ---");
        
        try {
            System.out.print("Ad: ");
            String firstName = scanner.nextLine();
            
            System.out.print("Soyad: ");
            String lastName = scanner.nextLine();
            
            if (!ValidationUtil.isValidString(firstName) || !ValidationUtil.isValidString(lastName)) {
                ValidationUtil.displayError("Ad ve soyad boş olamaz!");
                return;
            }
            
            Customer customer = bank.addCustomer(firstName, lastName);
            ValidationUtil.displaySuccess("Müşteri başarıyla eklendi!");
            System.out.println("Müşteri ID: " + customer.getCustomerId());
            System.out.println("Ad Soyad: " + customer.getFullName());
            
        } catch (Exception e) {
            ValidationUtil.displayError("Müşteri eklenirken hata oluştu: " + e.getMessage());
        }
    }
    
    /**
     * Tüm müşterileri listeler
     */
    private void handleListCustomers() {
        System.out.println("\n--- MÜŞTERİ LİSTESİ ---");
        
        List<Customer> customers = bank.getAllCustomers();
        
        if (customers.isEmpty()) {
            ValidationUtil.displayWarning("Sistemde kayıtlı müşteri bulunmamaktadır.");
            return;
        }
        
        System.out.println("\nToplam Müşteri Sayısı: " + customers.size() + "\n");
        
        for (Customer customer : customers) {
            customer.displayCustomerInfo();
            System.out.println();
        }
    }
    
    /**
     * Yeni hesap açma işlemini gerçekleştirir
     * POLİMORFİZM: Account referansı ile farklı hesap tipleri oluşturulur
     */
    private void handleOpenAccount() {
        System.out.println("\n--- HESAP AÇ ---");
        
        try {
            System.out.print("Müşteri ID: ");
            String customerId = scanner.nextLine();
            
            Customer customer = bank.findCustomer(customerId);
            if (customer == null) {
                ValidationUtil.displayError("Müşteri bulunamadı!");
                return;
            }
            
            System.out.println("\nHesap Tipi:");
            System.out.println("1. Vadesiz Hesap");
            System.out.println("2. Vadeli Hesap (12 ay, %10 faiz)");
            System.out.print("Seçiminiz: ");
            String accountType = scanner.nextLine();
            
            if (!ValidationUtil.isValidAccountType(accountType)) {
                ValidationUtil.displayError("Geçersiz hesap tipi!");
                return;
            }
            
            // POLİMORFİZM: Account referansı döner, gerçek tip runtime'da belirlenir
            Account account = bank.createAccount(customer, accountType);
            
            if (account == null) {
                ValidationUtil.displayError("Hesap oluşturulamadı!");
                return;
            }
            
            ValidationUtil.displaySuccess("Hesap başarıyla açıldı!");
            System.out.println("Hesap Tipi: " + account.getAccountType());
            System.out.println("Hesap No: " + account.getAccountNo());
            System.out.println("Hesap Sahibi: " + customer.getFullName());
            
        } catch (Exception e) {
            ValidationUtil.displayError("Hesap açılırken hata oluştu: " + e.getMessage());
        }
    }
    
    /**
     * Para yatırma işlemini gerçekleştirir
     * POLİMORFİZM: Account.deposit() metodu çağrılır
     */
    private void handleDeposit() {
        System.out.println("\n--- PARA YATIR ---");
        
        try {
            System.out.print("Hesap No: ");
            String accountNo = scanner.nextLine();
            
            Account account = bank.findAccount(accountNo);
            if (account == null) {
                ValidationUtil.displayError("Hesap bulunamadı!");
                return;
            }
            
            System.out.print("Yatırılacak Tutar (TL): ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // Buffer temizle
            
            if (!ValidationUtil.isValidAmount(amount)) {
                ValidationUtil.displayError("Geçersiz tutar! Tutar sıfırdan büyük olmalıdır.");
                return;
            }
            
            // POLİMORFİZM: Hangi sınıfın deposit() metodu çağrılacak runtime'da belirlenir
            account.deposit(amount);
            
            ValidationUtil.displaySuccess("Para yatırma işlemi başarılı!");
            System.out.println("Yatırılan Tutar: " + String.format("%.2f TL", amount));
            System.out.println("Güncel Bakiye: " + String.format("%.2f TL", account.getBalance()));
            
        } catch (InvalidTransactionException e) {
            ValidationUtil.displayError(e.getMessage());
        } catch (Exception e) {
            ValidationUtil.displayError("Para yatırılırken hata oluştu: " + e.getMessage());
        }
    }
    
    /**
     * Para çekme işlemini gerçekleştirir
     * POLİMORFİZM: Account.withdraw() metodu çağrılır
     * Farklı hesap tipleri farklı davranış gösterir
     */
    private void handleWithdraw() {
        System.out.println("\n--- PARA ÇEK ---");
        
        try {
            System.out.print("Hesap No: ");
            String accountNo = scanner.nextLine();
            
            Account account = bank.findAccount(accountNo);
            if (account == null) {
                ValidationUtil.displayError("Hesap bulunamadı!");
                return;
            }
            
            System.out.println("Mevcut Bakiye: " + String.format("%.2f TL", account.getBalance()));
            System.out.print("Çekilecek Tutar (TL): ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // Buffer temizle
            
            if (!ValidationUtil.isValidAmount(amount)) {
                ValidationUtil.displayError("Geçersiz tutar! Tutar sıfırdan büyük olmalıdır.");
                return;
            }
            
            if (!ValidationUtil.hasSufficientBalance(account, amount)) {
                ValidationUtil.displayError("Yetersiz bakiye!");
                return;
            }
            
            // POLİMORFİZM: DemandAccount ve TimeDepositAccount farklı davranır
            // TimeDepositAccount vade kontrolü yapar ve ceza uygulayabilir
            account.withdraw(amount);
            
            ValidationUtil.displaySuccess("Para çekme işlemi başarılı!");
            System.out.println("Çekilen Tutar: " + String.format("%.2f TL", amount));
            System.out.println("Güncel Bakiye: " + String.format("%.2f TL", account.getBalance()));
            
        } catch (InsufficientBalanceException e) {
            ValidationUtil.displayError(e.getMessage());
        } catch (InvalidTransactionException e) {
            ValidationUtil.displayError(e.getMessage());
        } catch (Exception e) {
            ValidationUtil.displayError("Para çekilirken hata oluştu: " + e.getMessage());
        }
    }
    
    /**
     * Hesap bakiyesini görüntüler
     */
    private void handleViewBalance() {
        System.out.println("\n--- BAKİYE GÖRÜNTÜLE ---");
        
        try {
            System.out.print("Hesap No: ");
            String accountNo = scanner.nextLine();
            
            Account account = bank.findAccount(accountNo);
            if (account == null) {
                ValidationUtil.displayError("Hesap bulunamadı!");
                return;
            }
            
            System.out.println();
            account.displayAccountInfo();
            
        } catch (Exception e) {
            ValidationUtil.displayError("Bakiye görüntülenirken hata oluştu: " + e.getMessage());
        }
    }
    
    /**
     * Hesap işlem geçmişini görüntüler
     */
    private void handleViewTransactionHistory() {
        System.out.println("\n--- İŞLEM GEÇMİŞİ ---");
        
        try {
            System.out.print("Hesap No: ");
            String accountNo = scanner.nextLine();
            
            Account account = bank.findAccount(accountNo);
            if (account == null) {
                ValidationUtil.displayError("Hesap bulunamadı!");
                return;
            }
            
            List<Transaction> transactions = account.getTransactions();
            
            if (transactions.isEmpty()) {
                ValidationUtil.displayWarning("Bu hesapta henüz işlem bulunmamaktadır.");
                return;
            }
            
            System.out.println("\nHesap: " + account.getAccountType() + " (" + accountNo + ")");
            System.out.println("Hesap Sahibi: " + account.getOwner().getFullName());
            System.out.println("\nToplam İşlem Sayısı: " + transactions.size() + "\n");
            
            for (Transaction transaction : transactions) {
                transaction.displayTransaction();
            }
            
        } catch (Exception e) {
            ValidationUtil.displayError("İşlem geçmişi görüntülenirken hata oluştu: " + e.getMessage());
        }
    }
    
    /**
     * Hesap kapatma işlemini gerçekleştirir
     */
    private void handleCloseAccount() {
        System.out.println("\n--- HESAP KAPAT ---");
        
        try {
            System.out.print("Hesap No: ");
            String accountNo = scanner.nextLine();
            
            Account account = bank.findAccount(accountNo);
            if (account == null) {
                ValidationUtil.displayError("Hesap bulunamadı!");
                return;
            }
            
            if (account.getBalance() > 0) {
                ValidationUtil.displayWarning("Hesap bakiyesi sıfır değil!");
                System.out.println("Mevcut Bakiye: " + String.format("%.2f TL", account.getBalance()));
                System.out.println("Lütfen önce tüm parayı çekin.");
                return;
            }
            
            System.out.print("Hesabı kapatmak istediğinizden emin misiniz? (E/H): ");
            String confirm = scanner.nextLine();
            
            if (!confirm.equalsIgnoreCase("E")) {
                System.out.println("İşlem iptal edildi.");
                return;
            }
            
            bank.closeAccount(accountNo);
            
            ValidationUtil.displaySuccess("Hesap başarıyla kapatıldı!");
            System.out.println("Kapatılan Hesap: " + accountNo);
            
        } catch (AccountNotFoundException e) {
            ValidationUtil.displayError(e.getMessage());
        } catch (Exception e) {
            ValidationUtil.displayError("Hesap kapatılırken hata oluştu: " + e.getMessage());
        }
    }
}
