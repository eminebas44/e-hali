import { useRoutes } from 'react-router-dom';
import GirisSayfasi from "./pages/GirisSayfasi";
import Anasayfa from "./pages/Anasayfa";
import './App.css';

// Tüm sayfaların listesi
const AppRoutes = () => {
    let routes = useRoutes([
        { path: "/", element: <GirisSayfasi /> },      // Anasayfa (kök adres) Giriş sayfasını gösterir
        { path: "/anasayfa", element: <Anasayfa /> },  // /anasayfa adresi Anasayfayı gösterir
        { path: "*", element: <h1>404 | Sayfa Bulunamadı</h1> } // Geri kalan tüm adresler 404
    ]);
    return routes;
};


function App() {
    return (
        <div className="App">
            <AppRoutes />
        </div>
    )
}

export default App;