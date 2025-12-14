import { Layout, Menu, theme, Button, Space, Typography } from 'antd';
import { UserOutlined, DashboardOutlined, LogoutOutlined, TruckOutlined, ShoppingCartOutlined } from '@ant-design/icons';
import React from 'react';

const { Header, Content, Sider } = Layout;
const { Title } = Typography;

const Anasayfa = () => {
    const {
        token: { colorBgContainer, borderRadiusLG },
    } = theme.useToken();

    // Çıkış (Logout) işlemi
    const handleLogout = () => {
        localStorage.removeItem('token'); // Tokeni siliyoruz
        window.location.href = '/'; // Giriş sayfasına yönlendiriyoruz
    };

    return (
        <Layout style={{ minHeight: '100vh' }}>
            <Header style={{ display: 'flex', alignItems: 'center', background: '#4e4376', padding: '0 24px' }}>
                <div style={{ color: 'white', fontSize: '24px', fontWeight: 'bold', display: 'flex', alignItems: 'center' }}>
                    <TruckOutlined style={{ marginRight: 10 }} />
                    E-Hali Yönetim
                </div>
                <div style={{ flexGrow: 1 }} />
                <Space>
                    <Button type="text" style={{ color: 'white' }} icon={<LogoutOutlined />} onClick={handleLogout}>
                        Çıkış Yap
                    </Button>
                </Space>
            </Header>

            <Layout>
                <Sider width={200} style={{ background: colorBgContainer }}>
                    <Menu
                        mode="inline"
                        defaultSelectedKeys={['1']}
                        style={{ height: '100%', borderRight: 0 }}
                        items={[
                            { key: '1', icon: <DashboardOutlined />, label: 'Dashboard' },
                            { key: '2', icon: <ShoppingCartOutlined />, label: 'Sipariş Yönetimi' },
                            { key: '3', icon: <UserOutlined />, label: 'Müşteriler' },
                        ]}
                    />
                </Sider>

                <Layout style={{ padding: '0 24px 24px' }}>
                    <Content
                        style={{
                            padding: 24,
                            margin: '16px 0 0',
                            minHeight: 280,
                            background: colorBgContainer,
                            borderRadius: borderRadiusLG,
                        }}
                    >
                        <Title level={3}>👋 Yönetim Paneline Hoş Geldiniz!</Title>
                        <p>Soldaki menüden işlemlerinizi seçebilirsiniz.</p>
                        {/* Burada Dashboard bilgileri yer alacak */}
                    </Content>
                </Layout>
            </Layout>
        </Layout>
    );
};

export default Anasayfa;