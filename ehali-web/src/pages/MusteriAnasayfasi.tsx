import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./MusteriAnasayfasi.css"; // Senin CSS dosyanı buraya bağlıyoruz

// --- RESİMLERİ IMPORT EDİYORUZ ---
// (Dosya yolları ekran görüntüsündeki assets klasörüne göredir)
import imgIpek from "../assets/ipek-hali.png";
import imgKasmir from "../assets/kasmir-hali.png";
import imgYun from "../assets/yun-hali.png";
import imgModern from "../assets/modern-hali.png";
import imgBunyan from "../assets/Bünyan (Kayseri) Halıları.png";
import imgLogo from "../assets/hali-logo.png";

// --- SAHTE VERİ (Resimler artık gerçek) ---
const mockHalilar = [
    { id: 1, ad: "Saray Serisi Hereke", kategori: "İpek", fiyat: 35000, resim: imgIpek, puan: 5.0 },
    { id: 2, ad: "Geleneksel Kayseri", kategori: "Bünyan", fiyat: 18500, resim: imgBunyan, puan: 4.8 },
    { id: 3, ad: "Uşak Yün Halı", kategori: "Yün", fiyat: 12000, resim: imgYun, puan: 4.9 },
    { id: 4, ad: "Saf Kaşmir Dokuma", kategori: "Kaşmir", fiyat: 25000, resim: imgKasmir, puan: 5.0 },
    { id: 5, ad: "Modern Salon Halısı", kategori: "Modern", fiyat: 8500, resim: imgModern, puan: 4.5 },
    { id: 6, ad: "Antik Yörük Kilimi", kategori: "Yün", fiyat: 4500, resim: imgYun, puan: 4.7 }, // Yün görselini tekrar kullandık örnek için
];

const kategoriler = ["Tümü", "İpek", "Bünyan", "Yün", "Kaşmir", "Modern"];

export default function MusteriAnasayfasi() {
    const navigate = useNavigate();
    const [kullaniciAdi, setKullaniciAdi] = useState("");
    const [seciliKategori, setSeciliKategori] = useState("Tümü");
    const [aramaMetni, setAramaMetni] = useState("");

    useEffect(() => {
        const email = localStorage.getItem("userEmail");
        if (email) {
            // Emailden @ işaretinden öncesini alıp isim yapalım (örn: emine)
            setKullaniciAdi(email.split('@')[0]);
        }
    }, []);

    const cikisYap = () => {
        localStorage.removeItem("token");
        localStorage.removeItem("userEmail");
        navigate("/");
    };

    // --- FİLTRELEME MANTIĞI ---
    const filtrelenmisUrunler = mockHalilar.filter((hali) => {
        const kategoriUyumu = seciliKategori === "Tümü" || hali.kategori === seciliKategori;
        const aramaUyumu = hali.ad.toLowerCase().includes(aramaMetni.toLowerCase());
        return kategoriUyumu && aramaUyumu;
    });

    return (
        <div>
            {/* --- NAVBAR --- */}
            <nav className="navbar">
                <div className="nav-brand">
                    <img src={imgLogo} alt="Logo" className="nav-logo" />
                    <span className="brand-text">E-Halı Dünyası</span>
                </div>

                <div className="search-bar-container">
                    <i className="fas fa-search search-icon"></i> {/* FontAwesome varsa ikon çıkar */}
                    <input
                        type="text"
                        className="search-input"
                        placeholder="Halı ara... (Örn: Hereke, İpek)"
                        value={aramaMetni}
                        onChange={(e) => setAramaMetni(e.target.value)}
                    />
                </div>

                <div className="nav-actions">
                    <div className="user-profile">
                        <span>Merhaba, {kullaniciAdi || "Misafir"}</span>
                    </div>
                    <div className="nav-item">
                        🛒 <span className="nav-badge">0</span>
                    </div>
                    <div className="nav-item" onClick={cikisYap} style={{color: '#d63031', fontSize: '1rem'}}>
                        Çıkış
                    </div>
                </div>
            </nav>

            {/* --- HERO SECTION (Banner) --- */}
            <header className="hero-section">
                <div className="hero-overlay">
                    <h1>Anadolu'nun İlmekleri</h1>
                    <p>Evinize Tarih, Zemine Sanat Seriyoruz.</p>
                    <button className="hero-btn" onClick={() => document.getElementById('urunler').scrollIntoView({behavior: 'smooth'})}>
                        Koleksiyonu Keşfet
                    </button>
                </div>
            </header>

            {/* --- KATEGORİLER --- */}
            <div className="categories-bar">
                {kategoriler.map((kat) => (
                    <button
                        key={kat}
                        className={`cat-btn ${seciliKategori === kat ? "active" : ""}`}
                        onClick={() => setSeciliKategori(kat)}
                    >
                        {kat}
                    </button>
                ))}
            </div>

            {/* --- ÜRÜN LİSTESİ --- */}
            <section className="products-section" id="urunler">
                <h2 className="section-title">
                    {seciliKategori === "Tümü" ? "Öne Çıkan Koleksiyonlar" : `${seciliKategori} Halıları`}
                </h2>

                {filtrelenmisUrunler.length === 0 ? (
                    <div className="no-product">Aradığınız kriterlere uygun ürün bulunamadı.</div>
                ) : (
                    <div className="products-grid">
                        {filtrelenmisUrunler.map((hali) => (
                            <div key={hali.id} className="product-card">
                                <button className="fav-btn">♥</button>

                                <div className="card-image-wrapper">
                                    <img src={hali.resim} alt={hali.ad} className="product-image" />
                                </div>

                                <div className="card-info">
                                    <div className="card-header">
                                        <h3>{hali.ad}</h3>
                                        <div className="rating">★ {hali.puan}</div>
                                    </div>

                                    <p className="category-text">{hali.kategori} Serisi</p>

                                    <div className="card-footer">
                                        <span className="price">{hali.fiyat.toLocaleString()} ₺</span>
                                        <button className="add-cart-btn">Sepete Ekle</button>
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>
                )}
            </section>

            {/* --- FOOTER --- */}
            <footer className="footer">
                <p>&copy; 2025 E-Halı Dünyası. Tüm hakları saklıdır. | Geleneksel El Dokuma Sanatı</p>
            </footer>
        </div>
    );
}