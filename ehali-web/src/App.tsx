import { BrowserRouter, Routes, Route, useLocation } from "react-router-dom";
import GirisSayfasi from "./pages/GirisSayfasi";
import KayitOlSayfasi from "./pages/KayitOlSayfasi";
import MusteriAnasayfasi from "./pages/MusteriAnasayfasi";
import KategoriSayfasi from "./pages/KategoriSayfasi";

// Rota değişimini takip etmek için yardımcı bir bileşen
function KategoriRouteWrapper() {
    const location = useLocation();
    // location.pathname her değiştiğinde (örn: /kategori/ipek -> /kategori/modern)
    // KategoriSayfasi'na yeni bir "key" vererek onu sıfırdan render ettiriyoruz.
    return <KategoriSayfasi key={location.pathname} />;
}

export default function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<MusteriAnasayfasi />} />
                <Route path="/giris" element={<GirisSayfasi />} />
                <Route path="/kayit-ol" element={<KayitOlSayfasi />} />

                {/* Burada direkt bileşeni vermek yerine Wrapper kullanıyoruz.
                   Bu, "Modern" sayfasında "İpek" görsellerinin takılı kalmasını engelleyen kesin çözümdür.
                */}
                <Route path="/kategori/:kategoriAdi" element={<KategoriRouteWrapper />} />

                <Route path="/sepet" element={<div>Sepet Sayfası Çok Yakında!</div>} />
                <Route path="/favoriler" element={<div>Favoriler Sayfası Çok Yakında!</div>} />
            </Routes>
        </BrowserRouter>
    );
}