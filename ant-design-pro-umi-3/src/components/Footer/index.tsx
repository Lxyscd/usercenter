import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';
const Footer: React.FC = () => {
  const defaultMessage = 'XY';
  const currentYear = new Date().getFullYear();
  return (
    <DefaultFooter
      copyright={`${currentYear} ${defaultMessage}`}
      links={[
        {
          key: 'Google',
          title: 'Google',
          href: 'https://www.google.com.hk/index.html',
          blankTarget: true,
        },
        {
          key: 'github',
          title: <GithubOutlined />,
          href: 'https://github.com/Lxyscd',
          blankTarget: true,
        },
        {
          key: 'CSDN',
          title: 'CSDN',
          href: 'https://www.csdn.net/',
          blankTarget: true,
        },
      ]}
    />
  );
};
export default Footer;
