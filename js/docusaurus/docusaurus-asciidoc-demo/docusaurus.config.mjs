// @ts-check
import { createRequire } from 'module';
import remarkPlantuml from './remark-plantuml.mjs';

const require = createRequire(import.meta.url);

const lightCodeTheme = require('prism-react-renderer').themes.github;
const darkCodeTheme = require('prism-react-renderer').themes.dracula;

/** @type {import('@docusaurus/types').Config} */
const config = {
  title: 'Docusaurus AsciiDoc Demo',
  tagline: '使用 AsciiDoc 编写文档的 Docusaurus 示例',
  favicon: 'img/favicon.ico',

  url: 'https://btpka3.github.io',
  baseUrl: '/js/docusaurus/docusaurus-asciidoc-demo/',
  trailingSlash: false,

  organizationName: 'btpka3',
  projectName: 'docusaurus-asciidoc-demo',
  deploymentBranch: 'gh-pages',

  onBrokenLinks: 'throw',

  markdown: {
    mermaid: true,
  },

  i18n: {
    defaultLocale: 'zh-CN',
    locales: ['zh-CN', 'en'],
    localeConfigs: {
      'zh-CN': { label: '中文' },
      en: { label: 'English' },
    },
  },

  presets: [
    [
      'classic',
      /** @type {import('@docusaurus/preset-classic').Options} */
      ({
        docs: {
          sidebarPath: './sidebars.mjs',
          editUrl: 'https://github.com/btpka3/btpka3.github.com/tree/main/js/docusaurus/docusaurus-asciidoc-demo/',
          include: ['**/*.{md,mdx,adoc}'],
          remarkPlugins: [remarkPlantuml],
        },
        blog: false,
        theme: {
          customCss: './src/css/custom.css',
        },
      }),
    ],
  ],

  themes: [
    '@docusaurus/theme-mermaid',
    ['@easyops-cn/docusaurus-search-local', {
      hashed: true,
      language: ['en', 'zh'],
      highlightSearchTermsOnTargetPage: true,
      explicitSearchResultPath: true,
    }],
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
          {
            type: 'docsVersionDropdown',
            position: 'right',
          },
          {
            type: 'localeDropdown',
            position: 'right',
          },
        ],
      },
      footer: {
        style: 'dark',
        copyright: `Copyright © ${new Date().getFullYear()} btpka3. Built with Docusaurus & AsciiDoc.`,
      },
      colorMode: {
        defaultMode: 'light',
        disableSwitch: false,
        respectPrefersColorScheme: true,
      },
      prism: {
        theme: lightCodeTheme,
        darkTheme: darkCodeTheme,
        additionalLanguages: ['java', 'shell-session'],
      },
      docs: {
        sidebar: {
          hideable: true,
          autoCollapseCategories: true,
        },
      },
      tableOfContents: {
        minHeadingLevel: 2,
        maxHeadingLevel: 4,
      },
      mermaid: {
        theme: { light: 'neutral', dark: 'dark' },
      },
    }),
};

export default config;
