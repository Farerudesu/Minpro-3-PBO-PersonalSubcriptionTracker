# Minpro-3-PBO-PersonalSubscriptionTracker

```
+------------------------------------------+
| Nama : Muhammad Fahriel                  |
| NIM  : 2509116050                        |
| Tema : Personal Subscription Tracker     |
+------------------------------------------+
```

Aplikasi berbasis terminal (CLI) menggunakan bahasa Java untuk mencatat, mengelola, dan memantau pengeluaran biaya langganan digital pribadi. Proyek ini merupakan pengembangan dari **Mini Project 2** menuju **Mini Project 3** dengan menerapkan **Abstraction** (`abstract class` dan `abstract method`), **Polymorphism** (`overriding` dan `overloading`), **Interface** sebagai nilai tambah, serta mempertahankan prinsip **Encapsulation**, **Inheritance**, **Validasi Input**, dan arsitektur **MVC**.

---

## Daftar Isi

1. [Deskripsi Singkat Program](#1-deskripsi-singkat-program)
2. [Penjelasan Struktur Package](#2-penjelasan-struktur-package)
3. [Penjelasan Alur Program](#3-penjelasan-alur-program)
4. [Penjelasan Penerapan Encapsulation dan Inheritance](#4-penjelasan-penerapan-encapsulation-dan-inheritance)
5. [Penjelasan Penerapan Polymorphism dan Abstraction](#5-penjelasan-penerapan-polymorphism-dan-abstraction)
   - [A. Polymorphism (Overriding & Overloading)](#a-polymorphism-overriding--overloading)
   - [B. Abstraction (Abstract Class & Abstract Method)](#b-abstraction-abstract-class--abstract-method)
   - [C. Keyword `final`](#c-keyword-final)
6. [Penjelasan Letak Nilai Tambah](#6-penjelasan-letak-nilai-tambah)
   - [A. Interface `DapatDidiskon`](#a-interface-dapatdidiskon--modeldapatdidiskonjava)
   - [B. ID Subscription Otomatis](#b-id-subscription-otomatis--controllerlangganancontrollerjava)
7. [Mekanisme Validasi Input & Standar Kode](#7-mekanisme-validasi-input--standar-kode)

---

## 1. Deskripsi Singkat Program

**Personal Subscription Tracker** adalah aplikasi yang membantu pengguna mencatat dan mengawasi pengeluaran langganan digital berbayar (seperti Netflix, Spotify, Google One, Microsoft 365, dll).

Fitur utama aplikasi:

- **Kategorisasi Langganan**: Membedakan langganan hiburan (Streaming) dan langganan kerja/cloud (Produktivitas).
- **Manajemen Data (CRUD)**: Menambah, melihat, mengedit, dan menghapus data langganan secara dinamis menggunakan `ArrayList`, dengan ID dibuat otomatis (`SUB01`, `SUB02`, dst).
- **Ringkasan Finansial**: Menghitung total biaya bulanan seluruh langganan aktif beserta rincian per kategori.
- **Simulasi & Proyeksi Tahunan**: Menghitung proyeksi pengeluaran tahunan lewat `abstract method` yang polimorfik, lengkap dengan nominal diskon dari `interface`.

---

## 2. Penjelasan Struktur Package

Proyek menerapkan arsitektur **MVC (Model, View, Controller)** dengan struktur package sebagai berikut:

```
com.pbo.fareru.minpro.pbo
├── model/                          <- MODEL: data & logika bisnis
│   ├── Langganan.java              <- abstract class (superclass)
│   ├── LanggananStreaming.java     <- subclass + implements DapatDidiskon
│   ├── LanggananProduktivitas.java <- subclass + implements DapatDidiskon
│   ├── DapatDidiskon.java          <- interface (nilai tambah)
│   ├── Layanan.java
│   └── MetodePembayaran.java
├── view/
│   └── LanggananView.java          <- VIEW: tampilan terminal
├── controller/
│   ├── LanggananController.java    <- CONTROLLER: alur & menu program
│   └── InputValidator.java         <- validasi input anti-crash
└── personalsubscriptiontracker/
    └── Main.java                   <- entry point program
```

- **Model**: menyimpan data langganan dan seluruh aturan perhitungannya. Tidak tahu-menahu soal tampilan.
- **View**: hanya mengurus tampilan ke terminal (header, garis, format Rupiah, pesan).
- **Controller**: mengatur alur menu, membaca input, memanggil Model, lalu meminta View menampilkannya.

---

## 3. Penjelasan Alur Program

```
[Start Program] -> [Inisialisasi Controller & View] -> [Isi Dummy Data ke ArrayList]
       |
       v
+-------------------------------+
|       TAMPIL MENU UTAMA       | <-------------+
+-------------------------------+               |
| 1. Tampilkan Langganan (Read) |               |
| 2. Tambah Langganan (Create)  |               |
| 3. Edit Langganan (Update)    |               |
| 4. Hapus Langganan (Delete)   |               |
| 5. Ringkasan Pengeluaran      |               |
| 6. Simulasi Estimasi Biaya    |               |
| 7. Keluar                     |               |
+-------------------------------+               |
       |                                        |
       +---> Pilih 1-6 -> Proses Fitur --------->
       |
       +---> Pilih 7   -> Tampilkan Pesan Selesai -> [Program Berhenti]
```

1. **Aplikasi Dimulai (`Main.java`)**: Objek `LanggananView` dan `LanggananController` dibuat. Konstruktor Controller langsung mengisi **4 data dummy awal** ke dalam `ArrayList`.
2. **Menampilkan Menu Utama**: Program menampilkan 7 opsi menu melalui terminal.
3. **Eksekusi Fitur**:
   - **Menu 1 (Read)**: Menampilkan seluruh data dalam bentuk kartu detail atau tabel ringkas.
   - **Menu 2 (Create)**: ID dibuat **otomatis** (`SUB05`, `SUB06`, ...); pengguna memilih tipe lalu mengisi atributnya.
   - **Menu 3 (Update)**: Mengubah atribut via setter berdasarkan ID.
   - **Menu 4 (Delete)**: Hapus dengan konfirmasi opsi kedua (`y/n`).
   - **Menu 5 (Ringkasan)**: Total tagihan bulanan langganan aktif per kategori.
   - **Menu 6 (Simulasi)**: Proyeksi biaya N bulan (polimorfik via `overloading`/`overriding`), dilanjutkan **Proyeksi Tahunan** yang memanggil `abstract method` `hitungBiayaTahunan()` dan `interface` `hitungDiskon()` untuk tiap langganan aktif.
   - **Menu 7 (Keluar)**: Mengakhiri program.
4. **Perulangan**: Setelah tiap aksi, program kembali ke Menu Utama hingga pengguna memilih menu 7.

---

## 4. Penjelasan Penerapan Encapsulation dan Inheritance

### A. Encapsulation & Access Modifier

- **Access Modifier `private`**: Semua atribut di kelas model (`idSubscription`, `layanan`, `hargaBulanan`, `tanggalTagihan`, `status`, dll) berstatus `private`.
- **Access Modifier `public`**: Getter, setter, konstruktor, dan method operasional berstatus `public`.
- **Access Modifier `protected`**: Konstanta `BATAS_BULAN_DISKON` berstatus `protected` agar dapat diakses langsung oleh subclass tanpa setter.
- **Validasi pada Setter**: Setter menyaring nilai agar objek selalu valid (harga tidak minus, tanggal 1–31, string tidak kosong).
- **Keyword `final`**: Konstanta `protected static final int BATAS_BULAN_DISKON = 12` tidak dapat diubah nilainya setelah diinisialisasi.

```
// Contoh penerapan di Langganan.java (abstract class)
public abstract class Langganan {
    protected static final int BATAS_BULAN_DISKON = 12; // final: nilai tetap

    private String idSubscription;   // private: hanya via getter/setter
    private double hargaBulanan;
    ...
}
```

### B. Inheritance (Pewarisan)

Struktur **1 Superclass (abstract)** dan **2 Subclass**:

```
                  +-----------------------+
                  |       Langganan       |  <- abstract class
                  +-----------------------+
                              ▲
                              │ extends
              ┌───────────────┴───────────────┐
              │                               │
 +-------------------------+     +--------------------------+
 |   LanggananStreaming    |     |  LanggananProduktivitas  |
 +-------------------------+     +--------------------------+
 | - kualitasResolusi      |     | - kapasitasStorage       |
 | - batasLayar            |     | - lisensiUser            |
 +-------------------------+     +--------------------------+
```

Kedua subclass mewarisi seluruh atribut dan method concrete milik `Langganan`, lalu menambahkan atribut dan perilaku khususnya masing-masing.

---

## 5. Penjelasan Penerapan Polymorphism dan Abstraction

### A. Polymorphism

**1. Method Overriding (Dynamic Polymorphism)**

Method yang sama dipanggil lewat referensi tipe parent, tetapi kode yang berjalan mengikuti tipe objek sebenarnya:

```
// Di Langganan.java (abstract): hanya deklarasi (WHAT TO DO)
public abstract double hitungBiayaTahunan();

// Di LanggananStreaming.java: implementasi versi streaming (HOW TO DO)
@Override
public double hitungBiayaTahunan() {
    return getHargaBulanan() * BATAS_BULAN_DISKON * 0.95; // diskon 5%
}

// Di LanggananProduktivitas.java: implementasi versi produktivitas
@Override
public double hitungBiayaTahunan() {
    return getHargaBulanan() * BATAS_BULAN_DISKON * 0.90; // diskon 10%
}
```

Overriding lain yang dipertahankan dari Minpro 2: `tampilkanDetail()`, `getTipeLangganan()`, dan `hitungEstimasiBiaya(int bulan)` (diskon tahunan 5% vs 10%).

Pemanggilan polimorfik di Controller (menu 6):

```
for (Langganan sub : listLangganan) {
    double tahunan = sub.hitungBiayaTahunan(); // otomatis versi subclass-nya
    ...
}
```

**2. Method Overloading (Static Polymorphism)**

Method bernama sama dengan daftar parameter berbeda di `Langganan.java`:

```
// Overloading 1: tampilkanDetail()
public void tampilkanDetail() { ... }              // versi lengkap
public void tampilkanDetail(boolean ringkas) { ... } // versi ringkas satu baris

// Overloading 2: hitungEstimasiBiaya()
public double hitungEstimasiBiaya(int bulan) { ... }
public double hitungEstimasiBiaya(int bulan, double diskonPersen) { ... }
```

### B. Abstraction (Abstract Class & Abstract Method)

Abstraksi menyembunyikan detail implementasi dan hanya menampilkan fungsionalitas esensial: memisahkan **WHAT TO DO** (rancangan aturan) dari **HOW TO DO** (detail pengerjaan).

1. **Abstract Class**: `Langganan` dideklarasikan `abstract` karena ia adalah konsep umum yang belum jelas wujudnya - yang konkret hanyalah `LanggananStreaming` dan `LanggananProduktivitas`. Akibatnya `new Langganan(...)` langsung **dilarang** oleh compiler; objek hanya boleh dibuat lewat subclass.

2. **Abstract Method**: `hitungBiayaTahunan()` dan `getTipeLangganan()` dideklarasikan tanpa body di `Langganan`. Setiap subclass **wajib** mengimplementasikannya dengan caranya sendiri (lihat contoh overriding di atas). Ini menyeragamkan kontrak: semua tipe langganan pasti bisa dihitung biaya tahunannya, walau rumusnya berbeda.

### C. Keyword `final`

- `protected static final int BATAS_BULAN_DISKON = 12`: konstanta ambang diskon tahunan - `static` (milik class, tidak perlu objek), `final` (nilainya tidak dapat diubah), `protected` (dapat diakses subclass).
- Konstanta ini menggantikan angka magis `12` yang sebelumnya tersebar di method `hitungEstimasiBiaya` kedua subclass.

---

## 6. Penjelasan Letak Nilai Tambah

### A. Interface `DapatDidiskon` - `model/DapatDidiskon.java`

Interface mendefinisikan kontrak perilaku `hitungDiskon(int bulan)` yang dapat dipakai oleh class mana pun, tanpa harus menjadi anak `Langganan`:

```
// Interface: kontrak perilaku (WHAT TO DO)
public interface DapatDidiskon {
    double hitungDiskon(int bulan);
}

// Diimplementasikan kedua subclass (HOW TO DO):
// - LanggananStreaming    : diskon 5%  jika bulan >= BATAS_BULAN_DISKON
// - LanggananProduktivitas: diskon 10% jika bulan >= BATAS_BULAN_DISKON
```

Perbedaan dengan abstract class: abstract class untuk hierarki "adalah" (`LanggananStreaming` *adalah* `Langganan`), sedangkan interface untuk kemampuan "dapat" (`LanggananStreaming` *dapat* didiskon) yang bisa ditempel ke hierarki mana pun.

Penggunaan di Controller (menu 6, blok Proyeksi Tahunan):

```java
if (sub instanceof DapatDidiskon) {
    diskonTahunan = ((DapatDidiskon) sub).hitungDiskon(12);
}
```

### B. ID Subscription Otomatis - `controller/LanggananController.java`

Method `generateIdOtomatis()` membaca ID terbesar yang ada (`SUB01`–`SUB04` dst) lalu menghasilkan ID berikutnya (`SUB05`, ...). Pengguna tidak lagi menginput ID manual saat tambah data - sesuai masukan pada penilaian Minpro 2.

---

## 7. Mekanisme Validasi Input & Standar Kode

### Validasi Input

- Seluruh input pengguna melewati `InputValidator` (`bacaInt`, `bacaDouble`, `bacaString`, `bacaKonfirmasi`) sehingga input yang salah tidak membuat program crash.
- Setter di kelas model memvalidasi ulang setiap perubahan nilai (harga ≥ 0, tanggal 1–31, ID unik dan berformat `SUBxx`).

### Konsistensi Penamaan Method (camelCase)

Seluruh method menggunakan camelCase yang deskriptif dan konsisten: `hitungBiayaTahunan`, `tampilkanDetail`, `generateIdOtomatis`, `cariLanggananById`, `menuSimulasiEstimasi`.
