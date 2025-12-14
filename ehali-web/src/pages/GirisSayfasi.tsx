// Başlangıçta bu import'u aç:
import { useState } from 'react';
import { Form, Input, Button, Card, Typography, message } from 'antd';
import { UserOutlined, LockOutlined, LoginOutlined, TruckOutlined } from '@ant-design/icons';
import api from '../services/api';
import { useNavigate } from 'react-router-dom'; // <-- useNavigate'i aktif ettik!

const { Title, Text } = Typography;

const GirisSayfasi = () => {
    const navigate = useNavigate(); // <-- navigate tanımladık
    const [loading, setLoading] = useState(false);

    const onFinish = async (values: any) => {
        setLoading(true);
        try {
            const response = await api.post('/auth/giris', values);

            message.success({ content: response.data.message || 'Giriş Başarılı!', style: { marginTop: '20vh' } });
            localStorage.setItem('token', response.data.token);

            setTimeout(() => {
                navigate('/anasayfa'); // <-- Yönlendirme şimdi çalışacak!
            }, 500);

        } catch (error: any) {
            // Hata mesajını backend'den alabiliriz veya sabit gösterebiliriz
            const errorMessage = error.response?.data || 'Giriş başarısız! Bilgilerinizi kontrol edin.';
            message.error({ content: errorMessage, style: { marginTop: '20vh' } });
        } finally {
            setLoading(false);
        }
    };

    // ... (Geri kalan HTML kısmı aynı kalacak) ...
    // HTML kodunun üst kısmı, return() ile başlayan yeri kontrol et:

    return (
        <div style={{
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            minHeight: '100vh',
            background: 'linear-gradient(135deg, #2b5876 0%, #4e4376 100%)',
            padding: '20px',
        }}>
            <Card
                style={{
                    width: '100%',
                    maxWidth: 420,
                    borderRadius: 20,
                    boxShadow: '0 15px 35px rgba(0,0,0,0.2)',
                    overflow: 'hidden'
                }}
                bordered={false}
                bodyStyle={{ padding: '40px 30px' }}
            >
                <div style={{ textAlign: 'center', marginBottom: 40 }}>
                    <div style={{
                        display: 'inline-flex',
                        justifyContent: 'center',
                        alignItems: 'center',
                        width: 70,
                        height: 70,
                        backgroundColor: 'rgba(78, 67, 118, 0.1)',
                        borderRadius: '50%',
                        marginBottom: 15
                    }}>
                        <TruckOutlined style={{ fontSize: 36, color: '#4e4376' }} />
                    </div>
                    <Title level={2} style={{ margin: '10px 0 5px', color: '#333', fontWeight: 700 }}>E-Hali</Title>
                    <Text type="secondary" style={{ fontSize: 16 }}>Lojistik Yönetim Paneli</Text>
                </div>

                <Form name="login" onFinish={onFinish} layout="vertical" size="large">
                    <Form.Item name="email" rules={[{ required: true, message: 'Lütfen E-Posta girin!' }, { type: 'email', message: 'Geçerli bir e-posta değil!' }]}>
                        <Input prefix={<UserOutlined style={{ color: '#aaa' }} />} placeholder="E-Posta Adresi" style={{ borderRadius: 8 }} />
                    </Form.Item>

                    <Form.Item name="sifre" rules={[{ required: true, message: 'Şifre boş olamaz!' }]}>
                        <Input.Password prefix={<LockOutlined style={{ color: '#aaa' }} />} placeholder="Şifre" style={{ borderRadius: 8 }} />
                    </Form.Item>

                    <Form.Item style={{ marginBottom: 10 }}>
                        <Button type="primary" htmlType="submit" block loading={loading} icon={<LoginOutlined />}
                                style={{
                                    height: 50,
                                    borderRadius: 8,
                                    backgroundColor: '#4e4376',
                                    borderColor: '#4e4376',
                                    fontSize: 18,
                                    fontWeight: 600,
                                    boxShadow: '0 4px 15px rgba(78, 67, 118, 0.3)'
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