import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { FaUser, FaLock, FaStore, FaUserShield, FaArrowRight } from 'react-icons/fa';
import logo from '../assets/hali-logo.png';
import './GirisSayfasi.css';

const API_BASE_URL = "http://localhost:8383/api";

const GirisSayfasi: React.FC = () => {
    const [activeTab, setActiveTab] = useState<'customer' | 'seller'>('customer');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault();
        setError(null);
        setLoading(true);

        try {
            const response = await axios.post(`${API_BASE_URL}/auth/login`, {
                email: email,
                password: password
            });

            if (response.data.token) {
                localStorage.setItem('userToken', response.data.token);
                // Backend'den gelen kullanıcı ismini de kaydedelim (Navbar'da kullanmak için)
                localStorage.setItem('userName', response.data.ad || "Kullanıcı");

                navigate("/"); // <--- DEĞİŞİKLİK BURADA: Girişten sonra Anasayfa'ya gider
            }
        } catch (err: any) {
            setError('E-posta veya şifre hatalı!');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="login-body">
            <div className="main-card">
                <div className="welcome-section">
                    <div className="welcome-content">
                        <div className="logo-wrapper">
                            <img src={logo} alt="Carpyet Logo" className="welcome-logo-circular" />
                        </div>
                        <h1 className="brand-name-large">Carpyet</h1>
                        <p className="welcome-text">Gelenekten geleceğe uzanan eşsiz dokumalar.</p>
                        <button className="btn-learn-more" onClick={() => navigate('/')}>
                            Mağazaya Göz At <FaArrowRight style={{marginLeft: '10px'}}/>
                        </button>
                    </div>
                </div>

                <div className="form-section">
                    <div className="form-container">
                        <h2 className="login-title">Giriş Yap</h2>
                        <form onSubmit={handleLogin}>
                            {error && <div className="error-msg">{error}</div>}
                            <div className="input-group">
                                <FaUser className="input-icon" />
                                <input type="email" placeholder="E-posta Adresi" value={email} onChange={(e) => setEmail(e.target.value)} required />
                            </div>
                            <div className="input-group">
                                <FaLock className="input-icon" />
                                <input type="password" placeholder="Şifre" value={password} onChange={(e) => setPassword(e.target.value)} required />
                            </div>
                            <button type="submit" className="btn-giris" disabled={loading}>
                                {loading ? 'Yükleniyor...' : 'Giriş Yap'}
                            </button>
                        </form>
                        <div className="footer-text">
                            Hesabın yok mu? <span className="register-link" onClick={() => navigate('/kayit-ol')}> Kayıt Ol</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default GirisSayfasi;