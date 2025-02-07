import { HeartTwoTone, SmileTwoTone } from '@ant-design/icons';
import { PageHeaderWrapper } from '@ant-design/pro-components';
import { Alert, Card, Typography } from 'antd';
import React from 'react';
const Admin: React.FC = ({
                              children
                         }) => {

  return (
    <PageHeaderWrapper content={' 这个页面只有 admin 权限才能查看'}>
      {children}
    </PageHeaderWrapper>
  );
};
export default Admin;
