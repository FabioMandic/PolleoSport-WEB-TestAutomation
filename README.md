# PolleoSport-WEB-TestAutomation
Ovaj repozitorij sadrži okvir (framework) za automatsko testiranje web trgovine Polleo Sport. Projekt je izrađen kao završni zadatak temeljen na tehnikama i alatima obrađenim kroz laboratorijske vježbe.

Cilj projekta je demonstrirati primjenu Selenium WebDrivera i TestNG-a unutar Page Object Model (POM) arhitekture za robusno i održivo testiranje web aplikacija.

🛠 Korišteni alati i tehnologije
Projekt je izgrađen koristeći standardni stack za automatizaciju web testiranja:

Jezik: Java (JDK 1.8+)

Build alat: Apache Maven

Testni okvir: TestNG

Web Automatizacija: Selenium WebDriver

IDE: IntelliJ IDEA

Implementirane tehnike
Kako bi se osigurala kvaliteta, stabilnost i skalabilnost koda, implementirane su sljedeće napredne tehnike:

Page Object Model (POM):

Logika interakcije sa stranicom odvojena je od samih testova.

Svaka stranica (npr. HomePage, ProductPage, CartPage) ima svoju klasu u paketu pages.

Smanjena dupliciranost koda i olakšano održavanje.

Explicit Waits (Pametna čekanja):

Umjesto nestabilnih Thread.sleep, korištene su WebDriverWait i ExpectedConditions naredbe.

Sustav pametno čeka da elementi postanu klikabilni ili vidljivi prije akcije.

Cross-Browser Testing:

Implementiran DriverFactory koji omogućuje pokretanje testova na Google Chrome i Mozilla Firefox preglednicima putem parametara.

Objektno Orijentirano Programiranje (OOP):

Nasljeđivanje (Inheritance): Sve Page klase nasljeđuju BasePage, a testne klase BaseTest.

Enkapsulacija: Web elementi su privatni i dostupni samo kroz javne metode.

Optimizacija brzine izvođenja:

Korištenje @BeforeClass i @AfterClass anotacija kako bi se preglednik otvorio samo jednom za cijeli set testova, čime je vrijeme izvršavanja smanjeno za 75%.

Headless Mode:

Podrška za pokretanje testova bez grafičkog sučelja (korisno za CI/CD integraciju).

📂 Struktura projekta
Plaintext
src/test/java
├── base
│   └── BaseTest.java       # Postavke drivera (Setup/Teardown)
├── pages
│   ├── BasePage.java       # Zajedničke metode za sve stranice
│   ├── HomePage.java       # Lokatori i metode za naslovnicu
│   ├── CartPage.java       # Logika košarice
│   ├── ProductPage.java    # Logika stranice proizvoda
│   └── ...
├── tests
│   └── PolleoTests.java    # Izvršni testni slučajevi
└── utils
    ├── DriverFactory.java  # Tvornica za Chrome/Firefox drivere
    └── Waits.java          # Wrapper klasa za eksplicitna čekanja
    
✅ Izvršeni testni slučajevi
Projekt sadrži ukupno 8 automatiziranih testova koji pokrivaju ključne funkcionalnosti:

Validacija naslova: Provjera da se Home Page ispravno učitao.

Pretraživanje (Search): Testiranje tražilice s pojmom "protein".

URL Validacija: Provjera rutiranja nakon pretrage pojma "kreatin".

Navigacija (Proteini): Otvaranje kategorije "Proteini" iz glavnog izbornika.

Navigacija (Vitamini): Otvaranje kategorije "Vitamini i zdravlje".

Linkovi u zaglavlju: Provjera funkcionalnosti linka "Poslovnice".

Linkovi u zaglavlju: Provjera funkcionalnosti linka "Povrati".

Dodavanje u košaricu (E2E): Kompletan tok: Pretraga -> Odabir proizvoda -> Dodavanje u košaricu -> Provjera stanja košarice.

💻 Kako pokrenuti testove
Projekt koristi Maven za upravljanje ovisnostima i pokretanje testova. Nije potrebno ručno skidati .jar datoteke.

1. Standardno pokretanje (Chrome)
Otvorite terminal u korijenskom direktoriju projekta i upišite:

Bash
mvn clean test
2. Pokretanje na Firefox pregledniku
Bash
mvn clean test -Dbrowser=firefox
3. Pokretanje u "Headless" modu (Brže, bez GUI-a)
Bash
mvn clean test -Dheadless=true
📊 Izvještaji (Reporting)
Nakon izvršavanja testova, Maven i TestNG automatski generiraju izvještaje o rezultatima.

Izvještaj možete pronaći na putanji: target/surefire-reports/emailable-report.html

Otvorite tu datoteku u web pregledniku za detaljan pregled prolaznosti testova (Passed/Failed/Skipped).
