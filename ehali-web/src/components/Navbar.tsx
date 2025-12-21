import { Link, useNavigate } from "react-router-dom";
import imgLogo from "../assets/hali-logo.png"; // Logo yolunu kontrol et

const Navbar = () => {
    const navigate = useNavigate();
    const token = localStorage.getItem('userToken');
    const userName = localStorage.getItem('userEmail')?.split('@')[0]; // Email'den isim alma

    const handleLogout = () => {
        localStorage.clear();
        navigate("/giris");
    };

    return (
        <nav className="main-navbar">
            <div className="nav-left">
                <Link to="/" className="nav-brand">
                    <img src={imgLogo} alt="Logo" className="nav-logo" />
                    <span className="brand-text">Carpyet</span>
                </Link>

                {/* Kategori Menüsü */}
                <div className="nav-dropdown">
                    <button className="drop-btn">Kategoriler ▼</button>
                    <div className="dropdown-menu">
                        <Link to="/kategori/ipek">İpek Halılar</Link>
                        <Link to="/kategori/yun">Yün Halılar</Link>
                        <Link to="/kategori/modern">Modern Halılar</Link>
                        <Link to="/kategori/kasmir">Kaşmir Serisi</Link>
                    </div>
                </div>
            </div>

            <div className="nav-center">
                <div className="search-box">
                    <input type="text" placeholder="İlmek ilmek sanat ara..." />
                    <button>🔍</button>
                </div>
            </div>

            <div className="nav-right">
                <Link to="/favoriler" className="nav-icon-link">
                    <span className="icon">❤️</span>
                    <small>Favorilerim</small>
                </Link>

                <Link to="/sepet" className="nav-icon-link">
                    <span className="icon">🛒</span>
                    <small>Sepetim</small>
                </Link>

                {token ? (
                    <div className="user-section">
                        <div className="user-label">
                            <small>Hoş Geldin</small>
                            <span>{userName}</span>
                        </div>
                        <button onClick={handleLogout} className="btn-logout">Çıkış</button>
                    </div>
                ) : (
                    <div className="auth-buttons">
                        <Link to="/giris" className="btn-login-text">Giriş</Link>
                        <Link to="/kayit-ol" className="btn-register">Kayıt Ol</Link>
                    </div>
                )}
            </div>
        </nav>
    );
};

export default Navbar;