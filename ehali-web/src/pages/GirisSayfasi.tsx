import { useState } from 'react';
import { Form, Input, Button, Card, Typography, message } from 'antd';
import { UserOutlined, LockOutlined, LoginOutlined } from '@ant-design/icons';
import api from '../services/api';
import { useNavigate } from 'react-router-dom';

// Logo dosyanızı doğru yoldan import edin.
import HaliLogo from '../assets/hali-logo.png';

const { Title, Text } = Typography;

// YENİ KOYU BORDO RENK PALETİ TANIMLAMALARI
const DEEP_MARSALA = '#5D0A1C'; // En koyu bordo (Arkaplanın başlangıcı, Buton)
const WARM_BORDEAUX = '#7E1C38'; // Sıcak bordo (Arkaplanın sonu)
const ACCENT_COLOR = '#943D49'; // Vurgu rengi (İkonlar)

const GirisSayfasi = () => {
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);

    const onFinish = async (values: any) => {
        setLoading(true);
        try {
            const response = await api.post('/auth/giris', values);

            message.success({ content: response.data.message || 'Giriş Başarılı!', style: { marginTop: '20vh' } });
            localStorage.setItem('token', response.data.token);

            setTimeout(() => {
                navigate('/anasayfa');
            }, 500);

        } catch (error: any) {
            const errorMessage = error.response?.data || 'Giriş başarısız! Bilgilerinizi kontrol edin.';
            message.error({ content: errorMessage, style: { marginTop: '20vh' } });
        } finally {
            setLoading(false);
        }
    };

    // HTML GÖVDE KISMI
    return (
        <div style={{
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            minHeight: '100vh',
            // ARKA PLAN GÜNCELLENDİ (Koyu Marsala Tonları)
            background: `linear-gradient(135deg, ${DEEP_MARSALA} 0%, ${WARM_BORDEAUX} 100%)`,
            padding: '20px',
        }}>
            <Card
                style={{
                    width: '100%',
                    maxWidth: 450,
                    borderRadius: 15,
                    boxShadow: '0 15px 35px rgba(0,0,0,0.3)',
                    overflow: 'hidden'
                }}
                styles={{ body: { padding: '45px 35px' } }}
                variant="default"
            >
                <div style={{ textAlign: 'center', marginBottom: 40 }}>
                    {/* LOGO ÇEVRESİNDEKİ DAİRE */}
                    <div style={{
                        display: 'inline-flex',
                        justifyContent: 'center',
                        alignItems: 'center',
                        width: 80,
                        height: 80,
                        // Daire arkaplanı, ana rengin hafif şeffaf tonu
                        backgroundColor: 'rgba(93, 10, 28, 0.1)',
                        borderRadius: '50%',
                        marginBottom: 10,
                        overflow: 'hidden'
                    }}>
                        {/* LOGO KODU */}
                        <img
                            src={HaliLogo}
                            alt="E-Hali Logo"
                            style={{
                                width: 80,
                                height: 80,
                                objectFit: 'cover'
                            }}
                        />
                    </div>

                    {/* BAŞLIK */}
                    <Title level={1} style={{ margin: '10px 0 0', color: DEEP_MARSALA, fontWeight: 800 }}>E-Halı</Title>

                    {/* SLOGAN (BELİRGİN STİL) */}
                    <Text
                        style={{
                            fontSize: 18,
                            color: '#444',
                            fontWeight: 500,
                            marginTop: 5,
                            display: 'block'
                        }}>
                        Gelenekten geleceğe e-Halı
                    </Text>
                </div>

                {/* GİRİŞ FORMU */}
                <Form name="login" onFinish={onFinish} layout="vertical" size="large">
                    <Form.Item name="email" rules={[{ required: true, message: 'Lütfen E-Posta girin!' }, { type: 'email', message: 'Geçerli bir e-posta değil!' }]}>
                        <Input
                            prefix={<UserOutlined style={{ color: ACCENT_COLOR }} />} // İkon rengi güncellendi
                            placeholder="E-Posta Adresi"
                            style={{ borderRadius: 8 }}
                        />
                    </Form.Item>

                    <Form.Item name="sifre" rules={[{ required: true, message: 'Şifre boş olamaz!' }]}>
                        <Input.Password
                            prefix={<LockOutlined style={{ color: ACCENT_COLOR }} />} // İkon rengi güncellendi
                            placeholder="Şifre"
                            style={{ borderRadius: 8 }}
                        />
                    </Form.Item>

                    <Form.Item style={{ marginBottom: 10, marginTop: 30 }}>
                        <Button type="primary" htmlType="submit" block loading={loading} icon={<LoginOutlined />}
                                style={{
                                    height: 55,
                                    borderRadius: 10,
                                    backgroundColor: DEEP_MARSALA, // Buton rengi koyu bordo
                                    borderColor: DEEP_MARSALA,
                                    fontSize: 18,
                                    fontWeight: 600,
                                    boxShadow: `0 6px 15px rgba(93, 10, 28, 0.4)`
                                }}>
                            Giriş Yap
                        </Button>
                    </Form.Item>
                </Form>
            </Card>
        </div>
    );
};

export default GirisSayfasi;