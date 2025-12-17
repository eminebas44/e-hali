import { BrowserRouter, Routes, Route } from "react-router-dom";
import GirisSayfasi from "./pages/GirisSayfasi";
import KayitOlSayfasi from "./pages/KayitOlSayfasi";
import MusteriAnasayfasi from "./pages/MusteriAnasayfasi";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                {/* Giriş Sayfası (Açılış) */}
                <Route path="/" element={<GirisSayfasi />} />

                {/* Kayıt Ol Sayfası */}
                <Route path="/kayit-ol" element={<KayitOlSayfasi />} />

                {/* Müşteri Ana Sayfası */}
                <Route path="/musteri-anasayfa" element={<MusteriAnasayfasi />} />
                // Routes kısmının içine, diğerlerinin yanına ekle:
                <Route path="/seller-panel" element={<div style={{padding: '50px', textAlign: 'center'}}><h1>Satıcı Paneli Çok Yakında!</h1></div>} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;