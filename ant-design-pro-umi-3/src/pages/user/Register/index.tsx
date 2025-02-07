import Footer from '@/components/Footer';
import { register} from '@/services/ant-design-pro/api';
import {
  LockOutlined,
  UserOutlined,

} from '@ant-design/icons';
import {
  LoginForm,

  ProFormText,
} from '@ant-design/pro-components';
import { message, Tabs } from 'antd';
import React, { useState } from 'react';
import { history } from 'umi';
import styles from './index.less';
// @ts-ignore
import {SYSTEM_LOGO} from '@/constants';


const Register: React.FC = () => {
  const [type, setType] = useState<string>('account');

  const handleSubmit = async (values: API.RegisterParams) => {
    //校验

    const{ userPassword,checkPassword } = values;
    if(userPassword !== checkPassword){
      message.error('两次输入密码不一致');
      return;
    }


    try {
      // 登录
      const res = await register(values);

      if (res.code===0 && res.data>0) {
        const defaultLoginSuccessMessage = '注册成功！';
        message.success(defaultLoginSuccessMessage);

        /** 此方法会跳转到 redirect 参数所在的位置 */
        if (!history) return;
        const { query } = history.location;
        const { redirect } = query as {
          redirect: string;
        };
        history.push('/user/login?redirect=' + redirect);
        return;
      }else{
        throw new Error(res.description);
      }


    } catch (error: any) {
      const defaultLoginFailureMessage = '注册失败，请重试！';
      message.error(error.message ?? defaultLoginFailureMessage);
    }
  };
  return (
    <div className={styles.container}>
      <div className={styles.content}>
        <LoginForm
          submitter={{
            searchConfig: {
              submitText:'注册'
            }
          }}
          logo={<img alt="logo" src={SYSTEM_LOGO} />}
          title="XYPLANET"
          subTitle={'XYPLANET'}
          initialValues={{
            autoLogin: true,
          }}

          onFinish={async (values) => {
            await handleSubmit(values as API.RegisterParams);
          }}
        >
          <Tabs activeKey={type} onChange={setType}>
            <Tabs.TabPane key="account" tab={'账户密码注册'} />

          </Tabs>


          {type === 'account' && (
            <>
              <ProFormText
                name="userAccount"
                fieldProps={{
                  size: 'large',
                  prefix: <UserOutlined className={styles.prefixIcon} />,
                }}
                placeholder={'输入用户名'}
                rules={[
                  {
                    required: true,
                    message: '用户名是必填项！',
                  },
                ]}
              />
              <ProFormText.Password
                name="userPassword"
                fieldProps={{
                  size: 'large',
                  prefix: <LockOutlined className={styles.prefixIcon} />,
                }}
                placeholder={'输入密码'}
                rules={[
                  {
                    required: true,
                    message: '密码是必填项！',
                  },
                  {
                    min: 8,
                    type:'string',
                    message: '密码长度最小值为8！',
                  },
                ]}
              />
              <ProFormText.Password
                name="checkPassword"
                fieldProps={{
                  size: 'large',
                  prefix: <LockOutlined className={styles.prefixIcon} />,
                }}
                placeholder={'再次输入密码'}
                rules={[
                  {
                    required: true,
                    message: '密码是必填项！',
                  },
                  {
                    min: 8,
                    type:'string',
                    message: '密码长度最小值为8！',
                  },
                ]}
              />
              <ProFormText
                name="planetCode"
                fieldProps={{
                  size: 'large',
                  prefix: <UserOutlined className={styles.prefixIcon} />,
                }}
                placeholder={'输入编号'}
                rules={[
                  {
                    required: true,
                    message: '必填项！',
                  },
                ]}
              />
            </>
          )}




        </LoginForm>
      </div>
      <Footer />
    </div>
  );
};
export default Register;
