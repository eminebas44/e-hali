import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import "./Koleksiyonlar.css";

import ipek1 from "../assets/ipek-hali.png";
import ipek2 from "../assets/ipek2.png";
import ipek3 from "../assets/ipek3.png";

export default function IpekKoleksiyonu() {
    const navigate = useNavigate();
    const [halilar, setHalilar] = useState<any[]>([]);
    const ipekGorseller = [ipek1, ipek2, ipek3, ipek2, ipek3];

    useEffect(() => {
        fetch("http://localhost:8383/api/halilar/kategori/İpek")
            .then(res => res.json())
            .then(data => setHalilar(data))
            .catch(err => console.error(err));
    }, []);

    return (
        <div className="special-coll-wrapper">
            <button onClick={() => navigate("/")} className="back-nav">← Anasayfa</button>
            <header className="coll-header">
                <h1>İPEK Koleksiyonu</h1>
                <p>Zarafetin ve işçiliğin buluştuğu en nadide parçalar.</p>
            </header>
            <div className="double-grid">
                {halilar.map((hali, index) => (
                    <div key={hali.haliId} className="hali-special-card">
                        <div className="hali-img-box">
                            <img src={ipekGorseller[index % ipekGorseller.length]} alt="İpek Halı" />
                            <div className="fav-star">★</div>
                        </div>
                        <div className="hali-info-box">
                            <h2>{hali.tur}</h2>
                            <p><strong>Malzeme:</strong> {hali.malzeme}</p>
                            <p><strong>Ölçü:</strong> {hali.en}x{hali.boy} cm</p>
                            <div className="hali-price-row">
                                <span>{hali.fiyat} ₺</span>
                                <button className="add-cart-btn">Sepete Ekle</button>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}