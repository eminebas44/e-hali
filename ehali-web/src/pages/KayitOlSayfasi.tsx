import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { FaUser, FaEnvelope, FaLock, FaPhone, FaStore, FaArrowRight, FaUserPlus } from "react-icons/fa";

/* GÖRSEL VE LOGO İMPORTLARI */
import arkaplanEhali from "../assets/arkaplan-ehali.png";
import logo from "../assets/hali-logo.png";

import "./KayitOlSayfasi.css";

export default function KayitOlSayfasi() {
    const navigate = useNavigate();
    const [aktifRol, setAktifRol] = useState<"MUSTERI" | "SATICI">("MUSTERI");
    const [formData, setFormData] = useState({ ad: "", soyad: "", email: "", sifre: "", telefon: "", adres: "" });
    const [hata, setHata] = useState<string>("");
    const [loading, setLoading] = useState(false);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleRegister = async (e: React.FormEvent) => {
        e.preventDefault();
        if (loading) return;
        setHata("");
        setLoading(true);

        // 400 Bad Request hatasını önlemek için temiz payload yapısı
        const payload = {
            ad: formData.ad,
            soyad: formData.soyad,
            email: formData.email,
            sifre: formData.sifre,
            rol: aktifRol,
            telefon: aktifRol === "SATICI" ? formData.telefon : "",
            adres: aktifRol === "MUSTERI" ? formData.adres : ""
        };

        try {
            await axios.post("http://localhost:8383/api/auth/register", payload);
            alert(`${aktifRol === "MUSTERI" ? "Müşteri" : "Satıcı"} kaydı başarılı! Giriş sayfasına yönlendiriliyorsunuz.`);
            navigate("/giris");
        } catch (error: any) {
            console.error("Kayıt Hatası Detayı:", error.response?.data);
            setHata(error.response?.data?.message || "Kayıt sırasında bir hata oluştu. Bilgilerinizi kontrol edin.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="register-body">
            <div className="main-card-premium">

                {/* SOL PANEL - Logonun Eklendiği Bölüm */}
                <div className="welcome-section-full">
                    <img src={arkaplanEhali} alt="Arka Plan" className="side-image-cover" />
                    <div className="welcome-overlay">
                        <div className="brand-logo-circle">
                            <img src={logo} alt="Carpyet Logo" className="brand-icon-img" />
                        </div>
                        <h1 className="brand-name-overlay">Carpyet</h1>
                        <p className="brand-tagline">Gelenekten geleceğe uzanan eşsiz dokumalar.</p>
                        <button type="button" className="btn-shop-preview" onClick={() => navigate("/")}>
                            Mağazaya Göz At <FaArrowRight />
                        </button>
                    </div>
                </div>

                {/* SAĞ PANEL - Form Alanı */}
                <div className="form-section-modern">
                    <div className="form-container-premium">
                        <div className="form-header">
                            <h2 className="form-title-modern">Yeni Hesap Oluştur</h2>
                        </div>

                        <div className="toggle-tabs-modern">
                            <button
                                type="button"
                                className={`tab-btn-modern ${aktifRol === "MUSTERI" ? "active" : ""}`}
                                onClick={() => setAktifRol("MUSTERI")}
                            >
                                <span className="icon-span"><FaUser /></span> Müşteri
                            </button>
                            <button
                                type="button"
                                className={`tab-btn-modern ${aktifRol === "SATICI" ? "active" : ""}`}
                                onClick={() => setAktifRol("SATICI")}
                            >
                                <span className="icon-span"><FaStore /></span> Satıcı
                            </button>
                        </div>

                        <form onSubmit={handleRegister} className="stagger-form">
                            {hata && <div className="error-box-modern">{hata}</div>}

                            <div className="row-inputs-modern">
                                <div className="input-group-modern half">
                                    <input type="text" name="ad" placeholder="Ad" value={formData.ad} onChange={handleChange} required />
                                </div>
                                <div className="input-group-modern half">
                                    <input type="text" name="soyad" placeholder="Soyad" value={formData.soyad} onChange={handleChange} required />
                                </div>
                            </div>

                            <div className="input-group-modern">
                                <span className="icon-span"><FaEnvelope /></span>
                                <input type="email" name="email" placeholder="E-posta Adresi" value={formData.email} onChange={handleChange} required />
                            </div>

                            {aktifRol === "SATICI" ? (
                                <div className="input-group-modern">
                                    <span className="icon-span"><FaPhone /></span>
                                    <input type="tel" name="telefon" placeholder="Telefon (05XX...)" value={formData.telefon} onChange={handleChange} required />
                                </div>
                            ) : (
                                <div className="input-group-modern">
                                    <span className="icon-span"><FaUserPlus /></span>
                                    <input type="text" name="adres" placeholder="Teslimat Adresi" value={formData.adres} onChange={handleChange} required />
                                </div>
                            )}

                            <div className="input-group-modern">
                                <span className="icon-span"><FaLock /></span>
                                <input type="password" name="sifre" placeholder="Şifre" value={formData.sifre} onChange={handleChange} required />
                            </div>

                            <button type="submit" className="submit-btn-premium" disabled={loading}>
                                {loading ? "İşleniyor..." : "Kayıt Ol"}
                            </button>
                        </form>

                        <div className="register-footer-modern">
                            Zaten hesabın var mı? <span className="red-login-text" onClick={() => navigate("/giris")}>Giriş Yapın</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}