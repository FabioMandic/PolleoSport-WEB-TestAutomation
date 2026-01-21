# Polleo Sport - Web Test Automation Framework

Dobrodošli u repozitorij projekta za automatsko testiranje web trgovine [Polleo Sport](https://polleosport.hr/). 
Ovaj projekt je izrađen kao završni rad u sklopu kolegija, temeljen na znanjima i tehnikama stečenim kroz laboratorijske vježbe.

Cilj projekta je demonstrirati robustan okvir (framework) za automatizaciju testiranja koristeći **Selenium WebDriver**, **Java** programski jezik i **TestNG** okvir, organiziran prema **Page Object Model (POM)** arhitekturi.

---

## 🔗 Korištene tehnologije i alati

Projekt koristi standardni industrijski stack alata koji su obrađeni u vježbama:

* **Programski jezik:** Java JDK 1.8+
* **Web Automatizacija:** Selenium WebDriver
* **Testni Framework:** TestNG
* **Build & Dependency Management:** Apache Maven
* **IDE:** IntelliJ IDEA
* **Version Control:** Git & GitHub

---

## 🔗 Implementirane napredne tehnike (Extra Features)

Kako bi se osigurala maksimalna kvaliteta koda i skalabilnost, u projekt su ugrađene sljedeće napredne tehnike:

### 1. Page Object Model (POM)
Odvajanje logike testova od logike stranice.
* **`pages` paket:** Sadrži klase (`HomePage`, `ProductPage`, `CartPage`) koje reprezentiraju web stranice i njihove elemente.
* **`tests` paket:** Sadrži samo testne metode i asercije, čineći testove čitljivima i lakima za održavanje.

### 2. Napredna sinkronizacija (Waits)
Umjesto nestabilnih `Thread.sleep` metoda, implementirana su **Eksplicitna čekanja (Explicit Waits)**.
* Korištenje `WebDriverWait` i `ExpectedConditions` klasa osigurava da test čeka točno onoliko koliko je potrebno da element postane klikabilan ili vidljiv.

### 3. Cross-Browser Testing
Implementiran je **`DriverFactory`** uzorak koji omogućuje pokretanje testova na različitim preglednicima promjenom samo jednog parametra:
* Google Chrome
* Mozilla Firefox

### 4. Objektno Orijentirano Programiranje (OOP)
* **Nasljeđivanje (Inheritance):** Sve Page klase nasljeđuju `BasePage` (zajedničke metode), a testne klase nasljeđuju `BaseTest` (setup/teardown logika).
* **Enkapsulacija:** Web elementi su privatni (`private By locator`) i dostupni samo putem javnih metoda.

### 5. Reporting (Izvještavanje)
Integracija s **Maven Surefire Pluginom** omogućuje automatsko generiranje HTML izvještaja o rezultatima testiranja nakon svakog pokretanja.

### 6. Optimizacija performansi
Korištenje `@BeforeClass` i `@AfterClass` anotacija iz TestNG-a osigurava da se preglednik podiže samo jednom po testnoj klasi, umjesto za svaki test zasebno, čime je vrijeme izvođenja drastično smanjeno.

---

## 📂 Struktura projekta

```text
src/test/java
├── base
│   └── BaseTest.java       # Inicijalizacija Drivera, Setup i Teardown metode
├── pages
│   ├── BasePage.java       # Wrapper metode i zajednička logika
│   ├── HomePage.java       # Lokatori i akcije za naslovnicu
│   ├── SearchPage.java     # Logika pretraživanja
│   ├── ProductPage.java    # Interakcija s proizvodom
│   └── CartPage.java       # Upravljanje košaricom
├── tests
│   └── PolleoTests.java    # Izvršni testni scenariji
└── utils
    ├── DriverFactory.java  # Upravljanje instancama preglednika (Chrome/Firefox)
    └── Waits.java          # Pomoćna klasa za pametna čekanja

```
## ✅ Popis Testnih Slučajeva (Test Cases)

Implementirano je više od 5 testnih scenarija koji pokrivaju ključne funkcionalnosti web shopa temeljenih na principima testiranja crne kutije:

* **Homepage Title Validation:** Provjera ispravnosti naslova stranice.
* **Search Functionality:** Provjera vraća li tražilica rezultate za pojam "protein".
* **Routing Validation:** Provjera mijenja li se URL ispravno nakon pretrage.
* **Category Navigation (Proteini):** Navigacija kroz glavni izbornik do kategorije Proteini.
* **Category Navigation (Vitamini):** Navigacija do kategorije Vitamini.
* **Header Links:** Provjera funkcionalnosti linkova "Poslovnice" i "Povrati".
* **Add to Cart (E2E):** Kompletan proces dodavanja proizvoda u košaricu iz rezultata pretrage.

---

## 🔗 Kako pokrenuti projekt

1. Kloniraj repozitorij
2. Otvori projekt: Pokreni IntelliJ IDEA, odaberi File > Open i označi mapu projekta.
3. Učitaj ovisnosti: Desni klik na pom.xml → Maven > Reload Project (kako bi se preuzele sve biblioteke i driveri).
4. Pokreni testove:
Preko Terminala: Upiši naredbu mvn test
