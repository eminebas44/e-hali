import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './GirisSayfasi.css'; // CSS dosyasını buraya bağlıyoruz

const GirisSayfasi: React.FC = () => {
    // Sekme kontrolü (user veya admin)
    const [activeTab, setActiveTab] = useState<'user' | 'admin'>('user');

    // Input verileri
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const navigate = useNavigate();

    const handleLogin = (e: React.FormEvent) => {
        e.preventDefault();

        if (activeTab === 'admin') {
            // --- ADMIN KONTROLÜ ---
            if (username === 'admin' && password === '1234') {
                alert('Admin paneline yönlendiriliyorsunuz...');
                navigate('/admin-panel');
            } else {
                alert('Hatalı Yönetici Bilgisi!');
            }
        }
        else {
            // --- MÜŞTERİ KONTROLÜ ---
            if (username && password) {
                alert('Giriş başarılı! Anasayfaya yönlendiriliyorsunuz...');
                navigate('/');
            } else {
                alert('Lütfen tüm alanları doldurun.');
            }
        }
    };

    return (
        <div className="login-body">
            <div className="login-container">

                {/* Logo Bölümü */}
                <div className="logo">
                    <h1>e-Halı</h1>
                    <p className="slogan">Gelenekten Geleceğe</p>
                </div>

                {/* Sekme Değiştirme (Toggle) */}
                <div className="toggle-container">
                    <button
                        className={`toggle-btn ${activeTab === 'user' ? 'active' : ''}`}
                        onClick={() => setActiveTab('user')}
                    >
                        Müşteri Girişi
                    </button>
                    <button
                        className={`toggle-btn ${activeTab === 'admin' ? 'active' : ''}`}
                        onClick={() => setActiveTab('admin')}
                    >
                        Admin Girişi
                    </button>
                </div>

                {/* Form Alanı */}
                <form onSubmit={handleLogin}>
                    <div className="input-group">
                        <input
                            type="text"
                            placeholder={activeTab === 'admin' ? "Admin Kullanıcı Adı" : "E-posta Adresi"}
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                        />
                    </div>

                    <div className="input-group">
                        <input
                            type="password"
                            placeholder="Şifre"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>

                    <button type="submit" className="btn-giris">
                        {activeTab === 'admin' ? 'YÖNETİCİ GİRİŞİ' : 'GİRİŞ YAP'}
                    </button>
                </form>
            </div>
        </div>
    );
};

export default GirisSayfasi;