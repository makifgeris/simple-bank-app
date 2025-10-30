# Basit Banka Hesap Simülasyonu

##  Proje Hakkında

Bu proje, Java konsol tabanlı bir banka hesap simülasyon sistemidir. Nesneye yönelik programlama (OOP) prensiplerini kullanarak temel banka işlemlerini gerçekleştirir.

##  Özellikler

- ✅ Müşteri ekleme ve listeleme
- ✅ Hesap açma (Vadesiz ve Vadeli)
- ✅ Para yatırma ve çekme işlemleri
- ✅ Bakiye görüntüleme
- ✅ İşlem geçmişi takibi
- ✅ Hesap kapatma
- ✅ Vadeli hesap için vade kontrolü ve erken çekim cezası
- ✅ Özel hata yönetimi (Custom Exceptions)
- ✅ Input validasyonu

##  Kullanılan OOP Kavramları

### 1. Kapsülleme (Encapsulation)
- `Customer` ve `Account` sınıflarında tüm alanlar `private` olarak tanımlanmış
- Getter/Setter metodları ile kontrollü erişim sağlanmış
- Veri gizliliği ve güvenliği korunmuş

### 2. Kalıtım (Inheritance)
- `Account` abstract sınıfından `DemandAccount` ve `TimeDepositAccount` türetilmiş
- Ortak özellikler üst sınıfta, özel özellikler alt sınıflarda tanımlanmış
- Kod tekrarı önlenmiş ve yeniden kullanılabilirlik artırılmış

### 3. Çok Biçimlilik (Polymorphism)
- `deposit()` ve `withdraw()` metodları override edilmiş
- `Account` referansı ile farklı hesap tipleri yönetilmiş
- Runtime'da doğru metodun çağrılması sağlanmış (Dynamic Binding)

### 4. Soyutlama (Abstraction)
- `Account` abstract class ile ortak davranışlar tanımlanmış
- Alt sınıflar kendi implementasyonlarını sağlamış
- Detaylar gizlenmiş, sadece gerekli arayüz sunulmuş

##  Nasıl Çalıştırılır

### Gereksinimler
- Java JDK 8 veya üzeri
- Konsol/Terminal erişimi

### Derleme
```bash
javac -encoding UTF-8 -d bin src/*.java
```

### Çalıştırma
```bash
java -cp bin BankApp
```

##  Proje Yapısı

```
bank-account-simulation/
├── src/
│   ├── BankApp.java                       # Ana uygulama ve menü sistemi
│   ├── Bank.java                          # Banka yönetim sistemi
│   ├── Customer.java                      # Müşteri sınıfı (Kapsülleme)
│   ├── Account.java                       # Abstract hesap sınıfı (Soyutlama)
│   ├── DemandAccount.java                 # Vadesiz hesap (Kalıtım)
│   ├── TimeDepositAccount.java            # Vadeli hesap (Polimorfizm)
│   ├── Transaction.java                   # İşlem kaydı sınıfı
│   ├── TransactionType.java               # İşlem tipi enum
│   ├── ValidationUtil.java                # Validasyon yardımcı sınıfı
│   ├── InsufficientBalanceException.java  # Yetersiz bakiye hatası
│   ├── AccountNotFoundException.java      # Hesap bulunamadı hatası
│   └── InvalidTransactionException.java   # Geçersiz işlem hatası
├── bin/                                   # Derlenmiş .class dosyaları
├── proje_takimi.txt                       # Proje ekibi bilgileri
└── README.md                              # Bu dosya
```

##  Menü Sistemi

```
═══════════════════════════════════════
         ANA MENÜ
═══════════════════════════════════════
1. Yeni Müşteri Ekle
2. Müşteri Listele
3. Hesap Aç
4. Para Yatır
5. Para Çek
6. Bakiye Görüntüle
7. İşlem Geçmişi
8. Hesap Kapat
9. Çıkış
═══════════════════════════════════════
```

##  Kullanım Örnekleri

### 1. Yeni Müşteri Ekleme
```
Seçiminiz: 1
Ad: Ahmet
Soyad: Yılmaz
✓ BAŞARILI: Müşteri başarıyla eklendi!
Müşteri ID: C1
```

### 2. Hesap Açma
```
Seçiminiz: 3
Müşteri ID: C1
Hesap Tipi:
1. Vadesiz Hesap
2. Vadeli Hesap (12 ay, %10 faiz)
Seçiminiz: 1
✓ BAŞARILI: Hesap başarıyla açıldı!
Hesap No: A1
```

### 3. Para Yatırma
```
Seçiminiz: 4
Hesap No: A1
Yatırılacak Tutar (TL): 1000
✓ BAŞARILI: Para yatırma işlemi başarılı!
Güncel Bakiye: 1000.00 TL
```

### 4. Para Çekme
```
Seçiminiz: 5
Hesap No: A1
Çekilecek Tutar (TL): 500
✓ BAŞARILI: Para çekme işlemi başarılı!
Güncel Bakiye: 500.00 TL
```

### 5. İşlem Geçmişi Görüntüleme
```
Seçiminiz: 7
Hesap No: A1

═══════════════════════════════════════
İŞLEM GEÇMİŞİ - Hesap: A1
═══════════════════════════════════════
2024-01-15 10:30:45 | PARA_YATIRMA | 1000.00 TL
2024-01-15 10:35:20 | PARA_CEKME   | 500.00 TL
═══════════════════════════════════════
```

##  Hata Yönetimi

Sistem aşağıdaki hata durumlarını custom exception'lar ile yönetir:

| Hata Türü | Açıklama | Exception Sınıfı |
|-----------|----------|------------------|
| ❌ Yetersiz bakiye | Hesapta yeterli bakiye yok | `InsufficientBalanceException` |
| ❌ Hesap bulunamadı | Geçersiz hesap numarası | `AccountNotFoundException` |
| ❌ Geçersiz işlem | Negatif veya sıfır tutar | `InvalidTransactionException` |
| ❌ Hesap kapatma hatası | Bakiyesi olan hesap kapatılamaz | `InvalidTransactionException` |

##  Vadeli Hesap Özellikleri

- **Vade Süresi:** 12 ay
- **Faiz Oranı:** %10 yıllık
- **Erken Çekim Cezası:** %5
- **Faiz Hesaplama:** Vade sonunda otomatik

### Vadeli Hesap Örnek Hesaplama
```
Ana Para: 1000 TL
Vade: 12 ay
Faiz Oranı: %10
─────────────────────────
Vade Sonunda: 1100 TL
Erken Çekimde: 950 TL (1000 - %5 ceza)
```

##  Güvenlik ve Validasyon

- Tüm girişler `ValidationUtil` sınıfı ile kontrol edilir
- Negatif ve sıfır değerler reddedilir
- Null kontrolü yapılır
- Tutarlar 2 ondalık basamak ile sınırlandırılır

##  Eğitim Amaçlı Özellikler

Bu proje aşağıdaki konuları öğrenmek için tasarlanmıştır:

1. **OOP Prensipleri:** Encapsulation, Inheritance, Polymorphism, Abstraction
2. **Exception Handling:** Custom exception sınıfları ve try-catch blokları
3. **Collections Framework:** ArrayList kullanımı
4. **Date/Time API:** LocalDateTime ile tarih/saat işlemleri
5. **Enum Types:** TransactionType enum kullanımı
6. **Input Validation:** Kullanıcı girişi kontrolü
7. **Console I/O:** Scanner ile konsol işlemleri


##  Katkıda Bulunma

Bu proje eğitim amaçlıdır. Önerilerinizi ve katkılarınızı bekliyoruz!

