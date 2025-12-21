import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./MusteriAnasayfasi.css";

// Assets importları
import imgLogo from "../assets/hali-logo.png";
import imgIpek from "../assets/ipek-hali.png"; // İpek görselleriyle uyumlu olması için güncellendi
import imgKasmir from "../assets/kasmir-hali.png";
import imgYun from "../assets/yun-hali.png";
import imgBunyan from "../assets/Bünyan (Kayseri) Halıları.png";
import imgModern from "../assets/modern-hali.png";
import imgAntik from "../assets/antikanadolumiras-hali.png";
import imgKilim from "../assets/etnikkilim-hali.png";
import imgVintage from "../assets/vintageeskitme-hali.png";

const mockHalilar = [
    {
        id: 1, ad: "Saray Serisi Hereke", kategori: "İpek", resim: imgIpek,
        icerik: "Saf ipek kozalarından elde edilen ipliklerle dokunmuştur. Işığın geliş açısına göre renk değiştiren bu eser, zarafetin zirvesidir.",
        kullanimAlani: "Lüks salonlar ve protokol odaları.",
        materyal: "%100 El Dokuma İpek"
    },
    {
        id: 2, ad: "Geleneksel Kayseri", kategori: "Bünyan", resim: imgBunyan,
        icerik: "Anadolu'nun kadim motiflerini sunan bu halı, doğal kök boyalı yünlerden üretilmiştir. Nesiller boyu eskimeyen bir mirastır.",
        kullanimAlani: "Oturma odaları ve otantik alanlar.",
        materyal: "Doğal Yün & Pamuk"
    },
    {
        id: 3, ad: "Uşak Yün Halı", kategori: "Yün", resim: imgYun,
        icerik: "Geleneksel Türk düğümü ile dokunmuş, yüksek havlı ve yumuşak dokulu bir eserdir. Isı yalıtımı ve konforu maksimize eder.",
        kullanimAlani: "Yatak odaları ve aile odaları.",
        materyal: "Halis Anadolu Yünü"
    },
    {
        id: 4, ad: "Saf Kaşmir Dokuma", kategori: "Kaşmir", resim: imgKasmir,
        icerik: "Premium kaşmir liflerinden üretilen bu seri, dokunma duyunuza hitap eden eşsiz bir yumuşaklığa sahiptir.",
        kullanimAlani: "Modern minimalist salonlar.",
        materyal: "Yüksek Kalite Kaşmir"
    },
    {
        id: 5, ad: "Modern Geometrik", kategori: "Modern", resim: imgModern,
        icerik: "Minimalist çizgilerle geleneksel dokuma sanatını birleştiren bu seri, modern evlerin enerjisini değiştirmek için tasarlandı.",
        kullanimAlani: "Modern daireler ve ofisler.",
        materyal: "Akrilik & Yün Karışımı"
    },
    {
        id: 6, ad: "Antik Anadolu Mirası", kategori: "Antik", resim: imgAntik,
        icerik: "Yüzyıllık desenlerin sadık bir yeniden yorumu. Eskitilmiş dokusuyla tarihin yaşanmışlığını mekanlarınıza taşır.",
        kullanimAlani: "Klasik ve vintage dekorasyonlar.",
        materyal: "Eskitme El Dokuma Yün"
    },
    {
        id: 7, ad: "Etnik Kilim Serisi", kategori: "Kilim", resim: imgKilim,
        icerik: "İnce dokusu ve canlı renkleriyle dinamik bir atmosfer sağlar. Pratik kullanımı ve kültürel derinliğiyle öne çıkar.",
        kullanimAlani: "Yazlık evler, mutfak ve koridorlar.",
        materyal: "Atkı ve Çözgü Pamuk/Yün"
    },
    {
        id: 8, ad: "Vintage Eskitme", kategori: "Vintage", resim: imgVintage,
        icerik: "Modern renk paletiyle harmanlanmış klasik desenler. Retro sevenler için tasarlanmış, karakteristik bir parça.",
        kullanimAlani: "Loft daireler ve stüdyolar.",
        materyal: "Özel Yıkama Doğal Elyaf"
    }
];

const kategoriler = ["Tümü", "İpek", "Bünyan", "Yün", "Kaşmir", "Modern", "Antik", "Kilim", "Vintage"];

export default function MusteriAnasayfasi() {
    const navigate = useNavigate();
    const [seciliKategori, setSeciliKategori] = useState("Tümü");
    const token = localStorage.getItem("userToken");

    const filtrelenmisUrunler = mockHalilar.filter((hali) => {
        return seciliKategori === "Tümü" || hali.kategori === seciliKategori;
    });

    return (
        <div className="anasayfa-wrapper">
            {/* NAVBAR */}
            <nav className="navbar">
                <div className="nav-brand" onClick={() => navigate("/")}>
                    <img src={imgLogo} alt="Logo" className="nav-logo" />
                    <span className="brand-text">Carpyet</span>
                </div>

                <div className="nav-actions">
                    <div className="nav-item" onClick={() => navigate("/favoriler")}>
                        <span className="nav-icon">❤️</span>
                        <small>Favoriler</small>
                    </div>

                    <div className="nav-item" onClick={() => navigate("/sepet")}>
                        <span className="nav-icon">🛒</span>
                        <small>Sepet</small>
                    </div>

                    <div className="auth-buttons">
                        {!token ? (
                            <>
                                <button className="login-text-btn" onClick={() => navigate("/giris")}>Giriş Yap</button>
                                <button className="register-fill-btn" onClick={() => navigate("/kayit-ol")}>Kayıt Ol</button>
                            </>
                        ) : (
                            <button className="logout-btn" onClick={() => { localStorage.clear(); navigate("/giris"); }}>Çıkış Yap</button>
                        )}
                    </div>
                </div>
            </nav>

            <header className="hero-section">
                <div className="hero-overlay">
                    <h1>Zanaatın Hikayesi</h1>
                    <p>Halılarımız yüzyıllık birer mirastır.</p>
                </div>
            </header>

            <div className="categories-container">
                <div className="categories-bar">
                    {kategoriler.map(kat => (
                        <button
                            key={kat}
                            className={`cat-btn ${seciliKategori === kat ? "active" : ""}`}
                            onClick={() => setSeciliKategori(kat)}
                        >
                            {kat}
                        </button>
                    ))}
                </div>
            </div>

            <section className="products-grid-container">
                {filtrelenmisUrunler.map((hali) => (
                    <div key={hali.id} className="compact-product-card">
                        <div className="card-image-side">
                            <img src={hali.resim} alt={hali.ad} className="hali-img" />
                        </div>

                        <div className="card-info-side">
                            <span className="hali-kat-tag">{hali.kategori} Koleksiyonu</span>
                            <h3 className="hali-name">{hali.ad}</h3>
                            <p className="hali-desc">{hali.icerik}</p>

                            <div className="hali-meta">
                                <div><strong>Materyal:</strong> {hali.materyal}</div>
                                <div><strong>Alan:</strong> {hali.kullanimAlani}</div>
                            </div>

                            {/* KOLEKSİYONU GÖR BUTONU */}
                            <button
                                className="coll-btn"
                                onClick={() => navigate(`/kategori/${hali.kategori.toLowerCase()}`)}
                            >
                                Koleksiyonu Gör →
                            </button>
                        </div>
                    </div>
                ))}
            </section>
        </div>
    );
}