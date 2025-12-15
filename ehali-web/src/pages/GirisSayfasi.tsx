import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { FaUser, FaLock, FaSignInAlt } from 'react-icons/fa'; // İkonlar
import logo from '../assets/hali-logo.png'; // LOGOYU BURAYA ÇAĞIRIYORUZ (Yolunu kontrol et!)
import './GirisSayfasi.css';

const GirisSayfasi: React.FC = () => {
    const [activeTab, setActiveTab] = useState<'user' | 'admin'>('user');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleLogin = (e: React.FormEvent) => {
        e.preventDefault();
        if (activeTab === 'admin') {
            if (username === 'admin' && password === '1234') {
                navigate('/admin-panel');
            } else {
                alert('Hatalı Yönetici Bilgisi!');
            }
        } else {
            if (username && password) {
                navigate('/home');
            } else {
                alert('Lütfen bilgileri doldurun.');
            }
        }
    };

    return (
        <div className="login-body">
            <div className="login-container">
                {/* Logo ve Başlık Bölümü */}
                <div className="logo-section">
                    <img src={logo} alt="Carpyet Logo" className="logo-img" />
                    <h1 className="brand-name">Carpyet</h1>
                    <p className="slogan">Gelenekten geleceğe Carpyet</p>
                </div>

                {/* Sekmeler */}
                <div className="toggle-container">
                    <button
                        className={`toggle-btn ${activeTab === 'user' ? 'active' : ''}`}
                        onClick={() => setActiveTab('user')}
                    >
                        Müşteri
                    </button>
                    <button
                        className={`toggle-btn ${activeTab === 'admin' ? 'active' : ''}`}
                        onClick={() => setActiveTab('admin')}
                    >
                        Yönetici
                    </button>
                </div>

                {/* Form */}
                <form onSubmit={handleLogin}>
                    <div className="input-group">
                        <FaUser /> {/* Kullanıcı İkonu */}
                        <input
                            type="text"
                            placeholder={activeTab === 'admin' ? "Kullanıcı Adı" : "E-posta Adresi"}
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                        />
                    </div>
                    <div className="input-group">
                        <FaLock /> {/* Kilit İkonu */}
                        <input
                            type="password"
                            placeholder="Şifre"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>
                    <button type="submit" className="btn-giris">
                        <FaSignInAlt /> Giriş Yap
                    </button>
                </form>
            </div>
        </div>
    );
};

export default GirisSayfasi;