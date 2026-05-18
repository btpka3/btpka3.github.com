// @ts-check
// Note: type annotations allow type checking and IDE autocompletion
import { createRequire } from 'module';
const require = createRequire(import.meta.url);

const lightCodeTheme = require('prism-react-renderer').themes.github;
const darkCodeTheme = require('prism-react-renderer').themes.dracula;

/** @type {import('@docusaurus/types').Config} */
const config = {
  title: 'Docusaurus AsciiDoc Demo',
  tagline: '使用 AsciiDoc 编写文档的 Docusaurus 示例',
  favicon: 'img/favicon.ico',

  url: 'https://btpka3.github.io',
  baseUrl: '/js/docusaurus/docusaurus-asciidos-demo/',
  trailingSlash: false,

  organizationName: 'btpka3',
  projectName: 'docusaurus-asciidos-demo',
  deploymentBranch: 'gh-pages',

  onBrokenLinks: 'throw',

  i18n: {
    defaultLocale: 'zh-CN',
    locales: ['zh-CN'],
  },

  presets: [
    [
      'classic',
      /** @type {import('@docusaurus/preset-classic').Options} */
      ({
        docs: {
          sidebarPath: './sidebars.mjs',
          editUrl: 'https://github.com/btpka3/btpka3.github.com/tree/main/js/docusaurus/docusaurus-asciidos-demo/',
          include: ['**/*.{md,mdx,adoc}'],
        },
        blog: false,
        theme: {
          customCss: './src/css/custom.css',
        },
      }),
    ],
  ],

  plugins: [
    '@asciidoc-js/docusaurus-asciidoc',
  ],

  themeConfig:
    /** @type {import('@docusaurus/preset-classic').ThemeConfig} */
    ({
      navbar: {
        title: 'AsciiDoc Demo',
        logo: {
          alt: 'AsciiDoc Logo',
          src: 'img/logo.svg',
        },
        items: [
          {
            type: 'docSidebar',
            sidebarId: 'tutorialSidebar',
            position: 'left',
            label: '文档',
          },
        ],
      },
      footer: {
        style: 'dark',
        copyright: `Copyright © ${new Date().getFullYear()} btpka3. Built with Docusaurus & AsciiDoc.`,
      },
      prism: {
        theme: lightCodeTheme,
        darkTheme: darkCodeTheme,
      },
    }),
};

export default config;
