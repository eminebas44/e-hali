import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import "./KategoriSayfasi.css";

/* LOGO */
import logo from "../assets/hali-logo.png";

/* GÖRSEL İMPORTLARI */
import ipek1 from "../assets/ipek1.png"; import ipek2 from "../assets/ipek2.png";
import ipek3 from "../assets/ipek3.png"; import ipek4 from "../assets/ipek4.png";
import ipek5 from "../assets/ipek5.png";

import bunyan1 from "../assets/bunyan1.png"; import bunyan2 from "../assets/bunyan2.png";
import bunyan3 from "../assets/bunyan3.png"; import bunyan4 from "../assets/bunyan4.png";
import bunyan5 from "../assets/bunyan5.png";

import yun1 from "../assets/yun1.png"; import yun2 from "../assets/yun2.png";
import yun3 from "../assets/yun3.png"; import yun4 from "../assets/yun4.png";
import yun5 from "../assets/yun5.png";

import kasmir1 from "../assets/kasmir1.png"; import kasmir2 from "../assets/kasmir2.png";
import kasmir3 from "../assets/kasmir3.png"; import kasmir4 from "../assets/kasmir4.png";
import kasmir5 from "../assets/kasmir5.png";

import modern1 from "../assets/modern1.png"; import modern2 from "../assets/modern2.png";
import modern3 from "../assets/modern3.png"; import modern4 from "../assets/modern4.png";
import modern5 from "../assets/modern5.png";

import antik1 from "../assets/antik1.png"; import antik2 from "../assets/antik2.png";
import antik3 from "../assets/antik3.png"; import antik4 from "../assets/antik4.png";
import antik5 from "../assets/antik5.png";

/* KİLİM VE VINTAGE GÖRSELLERİ */
import kilim1 from "../assets/kilim1.png"; import kilim2 from "../assets/kilim2.png";
import kilim3 from "../assets/kilim3.png"; import kilim4 from "../assets/kilim4.png";
import kilim5 from "../assets/kilim5.png";

import vintage1 from "../assets/vintage1.png"; import vintage2 from "../assets/vintage2.png";
import vintage3 from "../assets/vintage3.png"; import vintage4 from "../assets/vintage4.png";
import vintage5 from "../assets/vintage5.png";

interface Hali {
    haliId: number; tur: string; fiyat: number; malzeme: string;
    en: number; boy: number; stokAdedi: number; puan?: number;
    millet?: string; dugumSayisi?: string; uretimYili?: number;
    havYuksekligi?: string; agirlik?: string;
}

export default function KategoriSayfasi() {
    const { kategoriAdi } = useParams<{ kategoriAdi: string }>();
    const navigate = useNavigate();
    const [halilar, setHalilar] = useState<Hali[]>([]);
    const [loading, setLoading] = useState<boolean>(true);

    const normalizedKat = kategoriAdi?.toLowerCase()
        .replace(/i̇/g, "i").replace(/ı/g, "i").replace(/ü/g, "u")
        .replace(/ö/g, "o").replace(/ş/g, "s").replace(/ğ/g, "g")
        .replace(/ç/g, "c").replace(/[\s-]/g, "") || "";

    useEffect(() => {
        if (!normalizedKat) return;
        setLoading(true);

        fetch(`http://localhost:8383/api/halilar/kategori/${normalizedKat}`)
            .then(res => res.json())
            .then((data: Hali[]) => {
                if (Array.isArray(data) && data.length > 0) {
                    setHalilar(data);
                } else {
                    loadFallback(normalizedKat);
                }
                setLoading(false);
            })
            .catch(() => {
                loadFallback(normalizedKat);
                setLoading(false);
            });
    }, [normalizedKat]);

    const loadFallback = (kat: string) => {
        const dataMap: Record<string, Hali[]> = {
            ipek: [
                { haliId: 101, tur: "Saray Serisi Hereke", fiyat: 82000, malzeme: "Saf İpek", en: 120, boy: 180, stokAdedi: 2, puan: 5.0, millet: "Türkiye", dugumSayisi: "1.2M/m²", uretimYili: 2023, havYuksekligi: "4mm", agirlik: "2.5kg/m²" },
                { haliId: 102, tur: "Hereke Saf İpek", fiyat: 55000, malzeme: "Saf İpek", en: 120, boy: 180, stokAdedi: 2, puan: 4.9, millet: "Türkiye", dugumSayisi: "1M/m²", uretimYili: 2023, havYuksekligi: "4mm", agirlik: "2.5kg/m²" },
                { haliId: 103, tur: "Zümrüt Miras", fiyat: 52000, malzeme: "İpek", en: 160, boy: 240, stokAdedi: 4, puan: 4.9, millet: "Türkiye", dugumSayisi: "1M/m²", uretimYili: 2021, havYuksekligi: "4mm", agirlik: "2.5kg/m²" },
                { haliId: 104, tur: "Özel Dokuma İpek", fiyat: 45000, malzeme: "İpek", en: 150, boy: 230, stokAdedi: 5, puan: 4.8, millet: "İran", dugumSayisi: "800K/m²", uretimYili: 2024, havYuksekligi: "3mm", agirlik: "2.2kg/m²" },
                { haliId: 105, tur: "Saray Koleksiyonu", fiyat: 60000, malzeme: "İpek", en: 100, boy: 150, stokAdedi: 3, puan: 4.7, millet: "Türkiye", dugumSayisi: "950K/m²", uretimYili: 2023, havYuksekligi: "4mm", agirlik: "2.4kg/m²" }
            ],
            bunyan: [
                { haliId: 201, tur: "Geleneksel Kayseri", fiyat: 22000, malzeme: "Yün & Pamuk", en: 170, boy: 240, stokAdedi: 4, puan: 5, millet: "Türkiye", dugumSayisi: "400K/m²", uretimYili: 2024, havYuksekligi: "7mm", agirlik: "3.2kg/m²" },
                { haliId: 202, tur: "Bünyan Klasik", fiyat: 18500, malzeme: "Yün", en: 150, boy: 230, stokAdedi: 2, puan: 4.8, millet: "Türkiye", dugumSayisi: "350K/m²", uretimYili: 2023, havYuksekligi: "6mm", agirlik: "3.0kg/m²" },
                { haliId: 203, tur: "Anadolu Motifli", fiyat: 25000, malzeme: "Yün", en: 200, boy: 300, stokAdedi: 3, puan: 4.9, millet: "Türkiye", dugumSayisi: "450K/m²", uretimYili: 2024, havYuksekligi: "8mm", agirlik: "3.5kg/m²" },
                { haliId: 204, tur: "Kök Boyalı Bünyan", fiyat: 31000, malzeme: "Saf Yün", en: 120, boy: 180, stokAdedi: 5, puan: 4.7, millet: "Türkiye", dugumSayisi: "500K/m²", uretimYili: 2022, havYuksekligi: "7mm", agirlik: "3.1kg/m²" },
                { haliId: 205, tur: "Saray Tipi Bünyan", fiyat: 28000, malzeme: "Yün", en: 160, boy: 240, stokAdedi: 1, puan: 5.0, millet: "Türkiye", dugumSayisi: "420K/m²", uretimYili: 2023, havYuksekligi: "7mm", agirlik: "3.3kg/m²" }
            ],
            yun: [
                { haliId: 301, tur: "Uşak Yün Halı", fiyat: 15000, malzeme: "Anadolu Yünü", en: 120, boy: 180, stokAdedi: 6, puan: 4.9, millet: "Türkiye", dugumSayisi: "300K/m²", uretimYili: 2024, havYuksekligi: "9mm", agirlik: "3.8kg/m²" },
                { haliId: 302, tur: "Modern Geometrik", fiyat: 12500, malzeme: "Yün", en: 160, boy: 230, stokAdedi: 3, puan: 4.7, millet: "Türkiye", dugumSayisi: "250K/m²", uretimYili: 2023, havYuksekligi: "10mm", agirlik: "4.0kg/m²" },
                { haliId: 303, tur: "Yumuşak Dokulu Uşak", fiyat: 17000, malzeme: "Saf Yün", en: 200, boy: 300, stokAdedi: 2, puan: 5.0, millet: "Türkiye", dugumSayisi: "320K/m²", uretimYili: 2024, havYuksekligi: "9mm", agirlik: "3.9kg/m²" },
                { haliId: 304, tur: "Anadolu Klasik Yün", fiyat: 14000, malzeme: "Yün", en: 150, boy: 230, stokAdedi: 4, puan: 4.8, millet: "Türkiye", dugumSayisi: "280K/m²", uretimYili: 2023, havYuksekligi: "8mm", agirlik: "3.7kg/m²" },
                { haliId: 305, tur: "Saray Tipi Yün", fiyat: 19500, malzeme: "Halis Yün", en: 200, boy: 300, stokAdedi: 3, puan: 4.9, millet: "Türkiye", dugumSayisi: "350K/m²", uretimYili: 2024, havYuksekligi: "9mm", agirlik: "4.1kg/m²" }
            ],
            kasmir: [
                { haliId: 401, tur: "Saf Kaşmir Dokuma", fiyat: 95000, malzeme: "Premium Kaşmir", en: 160, boy: 240, stokAdedi: 2, puan: 5, millet: "Türkiye", dugumSayisi: "1.5M/m²", uretimYili: 2025, havYuksekligi: "5mm", agirlik: "2.1kg/m²" },
                { haliId: 402, tur: "Premium Kaşmir Seri", fiyat: 78000, malzeme: "Kaşmir", en: 140, boy: 200, stokAdedi: 1, puan: 4.9, millet: "Türkiye", dugumSayisi: "1.2M/m²", uretimYili: 2024, havYuksekligi: "4mm", agirlik: "2.0kg/m²" },
                { haliId: 403, tur: "Modern Minimalist", fiyat: 88000, malzeme: "Saf Kaşmir", en: 180, boy: 280, stokAdedi: 3, puan: 5.0, millet: "Türkiye", dugumSayisi: "1.4M/m²", uretimYili: 2025, havYuksekligi: "5mm", agirlik: "2.2kg/m²" },
                { haliId: 404, tur: "Etnik Kaşmir Miras", fiyat: 72000, malzeme: "Premium Kaşmir", en: 120, boy: 180, stokAdedi: 4, puan: 4.8, millet: "Türkiye", dugumSayisi: "1.1M/m²", uretimYili: 2023, havYuksekligi: "4mm", agirlik: "1.9kg/m²" },
                { haliId: 405, tur: "Kaşmir Dokunuş", fiyat: 85000, malzeme: "Saf Kaşmir", en: 200, boy: 300, stokAdedi: 2, puan: 4.9, millet: "Türkiye", dugumSayisi: "1.3M/m²", uretimYili: 2024, havYuksekligi: "5mm", agirlik: "2.3kg/m²" }
            ],
            modern: [
                { haliId: 501, tur: "Modern Geometrik", fiyat: 12500, malzeme: "Akrilik & Yün", en: 160, boy: 230, stokAdedi: 3, puan: 4.7, millet: "Türkiye", dugumSayisi: "250K/m²", uretimYili: 2023, havYuksekligi: "10mm", agirlik: "4.0kg/m²" },
                { haliId: 502, tur: "Minimalist Gri Çizgi", fiyat: 9800, malzeme: "Polyester", en: 120, boy: 180, stokAdedi: 5, puan: 4.5, millet: "Türkiye", dugumSayisi: "200K/m²", uretimYili: 2024, havYuksekligi: "8mm", agirlik: "3.5kg/m²" },
                { haliId: 503, tur: "Soyut Sanat Serisi", fiyat: 15400, malzeme: "Akrilik", en: 200, boy: 300, stokAdedi: 2, puan: 4.9, millet: "Türkiye", dugumSayisi: "300K/m²", uretimYili: 2024, havYuksekligi: "11mm", agirlik: "4.2kg/m²" },
                { haliId: 504, tur: "Viyana Modern Dokuma", fiyat: 11200, malzeme: "Yün Karışımı", en: 140, boy: 200, stokAdedi: 4, puan: 4.6, millet: "Türkiye", dugumSayisi: "240K/m²", uretimYili: 2023, havYuksekligi: "9mm", agirlik: "3.8kg/m²" },
                { haliId: 505, tur: "Nordic Loft Tasarım", fiyat: 13900, malzeme: "Pamuk & Akrilik", en: 160, boy: 240, stokAdedi: 6, puan: 4.8, millet: "Türkiye", dugumSayisi: "270K/m²", uretimYili: 2024, havYuksekligi: "10mm", agirlik: "3.9kg/m²" }
            ],
            antik: [
                { haliId: 601, tur: "Antik Anadolu Mirası", fiyat: 35000, malzeme: "Eskitme El Dokuma Yün", en: 170, boy: 250, stokAdedi: 2, puan: 5.0, millet: "Türkiye", dugumSayisi: "550K/m²", uretimYili: 1980, havYuksekligi: "6mm", agirlik: "3.4kg/m²" },
                { haliId: 602, tur: "Osmanlı Motifli Antik", fiyat: 42000, malzeme: "Kök Boyalı Yün", en: 200, boy: 300, stokAdedi: 1, puan: 4.9, millet: "Türkiye", dugumSayisi: "600K/m²", uretimYili: 1975, havYuksekligi: "5mm", agirlik: "3.6kg/m²" },
                { haliId: 603, tur: "Vintage Dokuma", fiyat: 28000, malzeme: "Eskitme Yün", en: 150, boy: 230, stokAdedi: 3, puan: 4.8, millet: "Türkiye", dugumSayisi: "480K/m²", uretimYili: 1990, havYuksekligi: "7mm", agirlik: "3.2kg/m²" },
                { haliId: 604, tur: "Selçuklu Ruhu", fiyat: 39500, malzeme: "Saf Yün", en: 180, boy: 280, stokAdedi: 2, puan: 5.0, millet: "Türkiye", dugumSayisi: "520K/m²", uretimYili: 1985, havYuksekligi: "6mm", agirlik: "3.5kg/m²" },
                { haliId: 605, tur: "Eski İstanbul Serisi", fiyat: 45000, malzeme: "Kök Boyalı Yün & İpek", en: 200, boy: 300, stokAdedi: 1, puan: 4.9, millet: "Türkiye", dugumSayisi: "700K/m²", uretimYili: 1970, havYuksekligi: "5mm", agirlik: "3.3kg/m²" }
            ],
            kilim: [
                { haliId: 701, tur: "Eşme Motifli Kilim", fiyat: 8500, malzeme: "Saf Yün", en: 80, boy: 150, stokAdedi: 12, puan: 4.8, millet: "Türkiye", uretimYili: 2024, havYuksekligi: "Düz Dokuma", agirlik: "1.2kg/m²" },
                { haliId: 702, tur: "Anadolu Mirası Cicim", fiyat: 6200, malzeme: "Kök Boya Yün", en: 100, boy: 200, stokAdedi: 8, puan: 4.9, millet: "Türkiye", uretimYili: 2023, havYuksekligi: "Düz Dokuma", agirlik: "1.3kg/m²" },
                { haliId: 703, tur: "Geometrik Modern Kilim", fiyat: 5400, malzeme: "Pamuk & Yün", en: 120, boy: 180, stokAdedi: 15, puan: 4.7, millet: "Türkiye", uretimYili: 2024, havYuksekligi: "Düz Dokuma", agirlik: "1.1kg/m²" },
                { haliId: 704, tur: "Etnik Dokuma Serisi", fiyat: 7800, malzeme: "Yün", en: 140, boy: 220, stokAdedi: 5, puan: 4.6, millet: "Türkiye", uretimYili: 2023, havYuksekligi: "Düz Dokuma", agirlik: "1.4kg/m²" },
                { haliId: 705, tur: "Saray Tipi Kilim", fiyat: 9200, malzeme: "Premium Yün", en: 160, boy: 240, stokAdedi: 3, puan: 5.0, millet: "Türkiye", uretimYili: 2024, havYuksekligi: "Düz Dokuma", agirlik: "1.5kg/m²" }
            ],
            vintage: [
                { haliId: 801, tur: "Pastel Yıkanmış Vintage", fiyat: 22500, malzeme: "Eskitme Yün", en: 170, boy: 240, stokAdedi: 4, puan: 4.9, millet: "Türkiye", dugumSayisi: "400K/m²", uretimYili: 2022, havYuksekligi: "5mm", agirlik: "2.8kg/m²" },
                { haliId: 802, tur: "Retro Boho Koleksiyonu", fiyat: 18400, malzeme: "Pamuk Karışımı", en: 160, boy: 230, stokAdedi: 6, puan: 4.7, millet: "Türkiye", dugumSayisi: "350K/m²", uretimYili: 2023, havYuksekligi: "7mm", agirlik: "3.0kg/m²" },
                { haliId: 803, tur: "Yüzyıllık Görünüm", fiyat: 28000, malzeme: "Yün", en: 200, boy: 300, stokAdedi: 2, puan: 5.0, millet: "Türkiye", dugumSayisi: "450K/m²", uretimYili: 2024, havYuksekligi: "6mm", agirlik: "3.1kg/m²" },
                { haliId: 804, tur: "Nostalji Ruhu", fiyat: 15900, malzeme: "Yün", en: 120, boy: 180, stokAdedi: 7, puan: 4.5, millet: "Türkiye", dugumSayisi: "380K/m²", uretimYili: 2023, havYuksekligi: "6mm", agirlik: "2.9kg/m²" },
                { haliId: 805, tur: "Modern Vintage Dokunuş", fiyat: 24000, malzeme: "Premium Eskitme Yün", en: 200, boy: 300, stokAdedi: 3, puan: 4.8, millet: "Türkiye", dugumSayisi: "420K/m²", uretimYili: 2024, havYuksekligi: "5mm", agirlik: "3.2kg/m²" }
            ]
        };
        setHalilar(dataMap[kat] || dataMap.ipek);
    };

    const getProductImage = (index: number) => {
        const pools: Record<string, string[]> = {
            ipek: [ipek1, ipek2, ipek3, ipek4, ipek5],
            bunyan: [bunyan1, bunyan2, bunyan3, bunyan4, bunyan5],
            yun: [yun1, yun2, yun3, yun4, yun5],
            kasmir: [kasmir1, kasmir2, kasmir3, kasmir4, kasmir5],
            modern: [modern1, modern2, modern3, modern4, modern5],
            antik: [antik1, antik2, antik3, antik4, antik5],
            kilim: [kilim1, kilim2, kilim3, kilim4, kilim5],
            vintage: [vintage1, vintage2, vintage3, vintage4, vintage5]
        };

        const foundKey = Object.keys(pools).find(key => normalizedKat.includes(key));
        const currentPool = foundKey ? pools[foundKey] : pools.ipek;

        return currentPool[index % currentPool.length];
    };

    const formatPrice = (price: number) => {
        return new Intl.NumberFormat("tr-TR", {
            style: "currency",
            currency: "TRY",
            maximumFractionDigits: 0
        }).format(price);
    };

    if (loading) return <div className="loading-screen">Koleksiyon Hazırlanıyor...</div>;

    return (
        <div className="premium-catalog-container">
            <header className="brand-header">
                <div className="brand-wrap" onClick={() => navigate("/")}>
                    <img src={logo} alt="Carpyet" className="brand-icon" />
                    <div className="brand-info">
                        <span className="name-main">CARPYET</span>
                        <span className="name-sub">HANDMADE LUXURY</span>
                    </div>
                </div>
                <button className="nav-back-btn" onClick={() => navigate("/")}>MAĞAZAYA DÖN</button>
            </header>

            <section className="title-block">
                <div className="line-dec"></div>
                <h1 className="main-cat-title">
                    {kategoriAdi?.toUpperCase()} <span>KOLEKSİYONU</span>
                </h1>
                <p className="sub-cat-text">
                    {normalizedKat.includes("modern") && "Minimalist Çizgilerle Geleceğin Dokusu"}
                    {normalizedKat.includes("ipek") && "Eşsiz Zanaat, Kusursuz İpek"}
                    {normalizedKat.includes("yun") && "Doğal Sıcaklık ve Yumuşak Doku"}
                    {normalizedKat.includes("bunyan") && "Anadolu'nun Kadim Motifleri"}
                    {normalizedKat.includes("kasmir") && "Premium Kaşmir Liflerinin Zarafeti"}
                    {normalizedKat.includes("antik") && "Yüzyıllık Desenlerin Sadık Bir Yeniden Yorumu"}
                    {normalizedKat.includes("kilim") && "Etnik Dokunuşlarla Yaşam Alanlarınıza Renk Katın"}
                    {normalizedKat.includes("vintage") && "Zamanın Eskitemediği Klasiklerin Modern Yorumu"}
                </p>
            </section>

            <div className="catalog-grid">
                {halilar.map((hali, index) => (
                    <div key={`${normalizedKat}-${hali.haliId}-${index}`} className="catalog-card">
                        <div className="catalog-img-side">
                            <img
                                src={getProductImage(index)}
                                alt={hali.tur}
                                key={`img-${normalizedKat}-${hali.haliId}-${index}`}
                            />
                            <div className="floating-tags">
                                <span className="puan-tag">⭐ {hali.puan}</span>
                                <button className="wish-btn">❤</button>
                            </div>
                        </div>
                        <div className="catalog-info-side">
                            <div className="info-top">
                                <span className="cat-tag">EL DOKUMASI</span>
                                <span className="stock-tag">{hali.stokAdedi} Stok</span>
                            </div>
                            <h2 className="hali-name-text">{hali.tur}</h2>
                            <p className="origin-text">{hali.millet} • {hali.uretimYili}</p>
                            <div className="tech-specs">
                                <div className="spec-unit"><span>Boyut</span><strong>{hali.en}x{hali.boy}</strong></div>
                                <div className="spec-unit"><span>Düğüm</span><strong>{hali.dugumSayisi || "Düz Dokuma"}</strong></div>
                                <div className="spec-unit"><span>Hav</span><strong>{hali.havYuksekligi || "İnce"}</strong></div>
                                <div className="spec-unit"><span>Ağırlık</span><strong>{hali.agirlik}</strong></div>
                            </div>
                            <div className="info-bottom">
                                <div className="price-wrap">
                                    <span className="price">{formatPrice(hali.fiyat)}</span>
                                </div>
                                <button className="add-to-cart-lux">SEPETE EKLE</button>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}