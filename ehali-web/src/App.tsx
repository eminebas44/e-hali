// src/App.tsx
import { Routes, Route } from 'react-router-dom';
import GirisSayfasi from "./pages/GirisSayfasi";

// Geçici bileşenler (Hata almamak için)
const HomePage = () => <h1 style={{color:'black', textAlign:'center', marginTop:'50px'}}>Anasayfa (Müşteri)</h1>;
const AdminPanel = () => <h1 style={{color:'black', textAlign:'center', marginTop:'50px'}}>Admin Paneli</h1>;

function App() {
    return (
        // BrowserRouter'ı BURADAN SİLDİK (Çünkü main.tsx'te var)
        <Routes>
            {/* Giriş Sayfası Rotası - Anasayfa olarak ayarladık */}
            <Route path="/" element={<GirisSayfasi />} />

            {/* /giris de çalışsın istersek */}
            <Route path="/giris" element={<GirisSayfasi />} />

            {/* Diğer sayfalar */}
            <Route path="/home" element={<HomePage />} />
            <Route path="/admin-panel" element={<AdminPanel />} />
        </Routes>
    );
}

export default App;