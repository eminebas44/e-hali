import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { FaUser, FaLock, FaStore, FaUserShield } from 'react-icons/fa';
import logo from '../assets/hali-logo.png'; // Logonu kontrol et
import './GirisSayfasi.css';

// Backend Adresi
const API_BASE_URL = "http://localhost:8383/api";

const GirisSayfasi: React.FC = () => {
    // Sekmeler: 'customer' -> USER rolü, 'seller' -> SATICI rolü
    const [activeTab, setActiveTab] = useState<'customer' | 'seller'>('customer');

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState(false);

    const navigate = useNavigate();

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault();
        setError(null);

        if (!email || !password) {
            setError('Lütfen tüm alanları doldurun.');
            return;
        }

        setLoading(true);

        try {
            // Backend'e giriş isteği
            const response = await axios.post(`${API_BASE_URL}/auth/login`, {
                email: email,
                password: password
            });

            console.log("Giriş Başarılı:", response.data);

            if (response.data.token) {
                // Token'ı kaydet
                localStorage.setItem('userToken', response.data.token);

                // Seçili sekmeye göre rolü kaydet (İleride kullanmak için)
                const userRole = activeTab === 'customer' ? 'USER' : 'SATICI';
                localStorage.setItem('userRole', userRole);

                setLoading(false);

                // YÖNLENDİRME MANTIĞI
                if (activeTab === 'customer') {
                    navigate('/musteri-anasayfa');
                } else {
                    // Satıcı ise satıcı paneline git (Henüz yapmadıysan burası /musteri-anasayfa kalabilir)
                    navigate('/satici-panel');
                }
            } else {
                setError("Token alınamadı.");
                setLoading(false);
            }

        } catch (err: any) {
            setLoading(false);
            console.error("Giriş Hatası:", err);

            if (err.response) {
                if (err.response.status === 401 || err.response.status === 403) {
                    setError('E-posta veya şifre hatalı!');
                } else if (err.response.status === 404) {
                    setError('Böyle bir kullanıcı bulunamadı.');
                } else {
                    setError('Sunucu hatası. Lütfen daha sonra tekrar deneyin.');
                }
            } else if (err.code === "ERR_NETWORK") {
                setError('Sunucuya ulaşılamıyor. Backend çalışıyor mu?');
            } else {
                setError('Beklenmedik bir hata oluştu.');
            }
        }
    };

    // Admin girişi ayrı bir sayfaya yönlendiriyor
    const goToAdmin = () => { navigate('/admin-giris'); };

    return (
        <div className="login-body">
            <div className="login-container">
                <div className="logo-section">
                    <img src={logo} alt="Carpyet Logo" className="logo-img" />
                    <h1 className="brand-name">Carpyet</h1>
                    <p className="slogan">Gelenekten geleceğe Carpyet</p>
                </div>

                {/* Sekme Geçişleri (Müşteri / Satıcı) */}
                <div className="toggle-container">
                    <button
                        className={`toggle-btn ${activeTab === 'customer' ? 'active' : ''}`}
                        onClick={() => setActiveTab('customer')}
                    >
                        <FaUser style={{marginRight: '8px'}}/> Müşteri
                    </button>
                    <button
                        className={`toggle-btn ${activeTab === 'seller' ? 'active' : ''}`}
                        onClick={() => setActiveTab('seller')}
                    >
                        <FaStore style={{marginRight: '8px'}}/> Satıcı
                    </button>
                </div>

                <form onSubmit={handleLogin}>
                    {error && (
                        <div style={{
                            color: 'white',
                            backgroundColor: '#ff4757',
                            padding: '10px',
                            borderRadius: '8px',
                            marginBottom: '15px',
                            textAlign: 'center',
                            fontSize: '0.9rem'
                        }}>
                            {error}
                        </div>
                    )}

                    <div className="input-group">
                        <FaUser className="input-icon" />
                        <input
                            type="email"
                            placeholder="E-posta Adresi"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                        />
                    </div>
                    <div className="input-group">
                        <FaLock className="input-icon" />
                        <input
                            type="password"
                            placeholder="Şifre"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />
                    </div>

                    <button type="submit" className="btn-giris" disabled={loading}>
                        {loading ? 'Giriş Yapılıyor...' : 'Giriş Yap'}
                    </button>
                </form>

                {/* --- DÜZELTİLEN KISIM BURASI --- */}
                <div className="footer-text">
                    Hesabın yok mu?
                    <span
                        className="register-link"
                        onClick={() => navigate('/kayit-ol')} /* BURASI ARTIK DOĞRU */
                    >
                         Kayıt Ol
                    </span>
                </div>
            </div>

            {/* Yönetici Girişi Butonu */}
            <div className="admin-access-btn" onClick={goToAdmin}>
                <FaUserShield style={{marginRight:'5px'}} /> Yönetici Girişi
            </div>
        </div>
    );
};

export default GirisSayfasi;