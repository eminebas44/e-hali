import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { FaUser, FaEnvelope, FaLock, FaPhone, FaStore } from "react-icons/fa";
import logo from "../assets/hali-logo.png";
import "./KayitOlSayfasi.css";

export default function KayitOlSayfasi() {
    const navigate = useNavigate();

    const [aktifRol, setAktifRol] = useState<"USER" | "SATICI">("USER");
    const [formData, setFormData] = useState({
        ad: "",
        soyad: "",
        email: "",
        sifre: "",
        telefon: ""
    });
    const [hata, setHata] = useState<any>(""); // Nesne gelme ihtimaline karşı tip esnetildi
    const [loading, setLoading] = useState(false);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleRegister = async (e: React.FormEvent) => {
        e.preventDefault();
        if (loading) return;

        setHata("");
        setLoading(true);

        const gonderilecekVeri = { ...formData, rol: aktifRol };

        try {
            await axios.post("http://localhost:8383/api/auth/register", gonderilecekVeri);
            alert(`${aktifRol === "USER" ? "Müşteri" : "Satıcı"} kaydı başarılı! Giriş yapabilirsiniz.`);
            navigate("/");
        } catch (error: any) {
            console.error("Kayıt Hatası:", error);

            // --- BEYAZ EKRAN HATASINI ÇÖZEN DÜZENLEME ---
            const backendMesaji = error.response?.data;

            if (error.response && error.response.status === 400) {
                // Eğer mesaj bir nesne ise içindeki 'error' alanını al, yoksa stringe çevir
                const gorunurHata = typeof backendMesaji === 'object'
                    ? (backendMesaji.error || JSON.stringify(backendMesaji))
                    : backendMesaji;

                setHata(gorunurHata || "Bu e-posta adresi zaten kullanılıyor.");
            } else {
                setHata("Kayıt işlemi başarısız. Lütfen bilgileri kontrol edin.");
            }
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="register-body">
            <div className="register-card">

                <div className="register-header">
                    <p className="welcome-script">Merhaba!</p>
                    <img src={logo} alt="Carpyet Logo" className="brand-logo-img" />
                    <h1 className="brand-title">Carpyet</h1>
                    <p className="brand-slogan">Gelenekten Geleceğe Dokunuş</p>
                </div>

                <div className="role-toggle-container">
                    <button
                        type="button"
                        className={`role-btn ${aktifRol === "USER" ? "active" : ""}`}
                        onClick={() => setAktifRol("USER")}
                    >
                        <FaUser /> Müşteri
                    </button>
                    <button
                        type="button"
                        className={`role-btn ${aktifRol === "SATICI" ? "active" : ""}`}
                        onClick={() => setAktifRol("SATICI")}
                    >
                        <FaStore /> Satıcı
                    </button>
                </div>

                <form onSubmit={handleRegister}>
                    <div className="row-inputs">
                        <div className="col-half input-wrapper">
                            <FaUser className="input-icon" />
                            <input
                                type="text" name="ad"
                                className="custom-input"
                                placeholder="Ad"
                                value={formData.ad} onChange={handleChange} required
                            />
                        </div>
                        <div className="col-half input-wrapper">
                            <input
                                type="text" name="soyad"
                                className="custom-input"
                                placeholder="Soyad"
                                style={{ paddingLeft: '15px' }}
                                value={formData.soyad} onChange={handleChange} required
                            />
                        </div>
                    </div>

                    <div className="input-wrapper">
                        <FaEnvelope className="input-icon" />
                        <input
                            type="email" name="email"
                            className="custom-input"
                            placeholder="E-Posta Adresi"
                            value={formData.email} onChange={handleChange} required
                        />
                    </div>

                    {aktifRol === "SATICI" && (
                        <div className="input-wrapper">
                            <FaPhone className="input-icon" />
                            <input
                                type="tel" name="telefon"
                                className="custom-input"
                                placeholder="Telefon Numarası (05XX...)"
                                value={formData.telefon} onChange={handleChange} required
                            />
                        </div>
                    )}

                    <div className="input-wrapper">
                        <FaLock className="input-icon" />
                        <input
                            type="password" name="sifre"
                            className="custom-input"
                            placeholder="Güçlü Bir Şifre"
                            value={formData.sifre} onChange={handleChange} required
                        />
                    </div>

                    {/* Hata gösterimi - Artık nesne gelse bile patlamaz */}
                    {hata && <div className="error-box">{hata.toString()}</div>}

                    <button
                        type="submit"
                        className="submit-btn"
                        disabled={loading}
                    >
                        {loading ? "İşleniyor..." : "KAYIT OL"}
                    </button>
                </form>

                <div className="footer-link">
                    Zaten hesabın var mı?
                    <span className="login-redirect" onClick={() => navigate("/")}>
                        Giriş Yap
                    </span>
                </div>

            </div>
        </div>
    );
}